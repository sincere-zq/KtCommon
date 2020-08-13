package com.witaction.netty;

import com.witaction.netty.callback.NettyCallBack;
import com.witaction.netty.callback.NettyService;
import com.witaction.netty.config.NettyConstant;
import com.witaction.netty.config.NettyStatus;
import com.witaction.netty.config.SocketConfig;
import com.witaction.netty.utils.PacketCache;

import java.nio.ByteBuffer;

import edu.dbke.socket.cp.Packet;
import edu.dbke.socket.cp.util.LogUtils;
import edu.dbke.socket.cp.util.ThreadPoolProxyFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Netty管理工具
 * 在多处添加收到消息回调可以使用NettClient.addReceiveMsgListener();
 */

public class NettyClient implements NettyService {
    private static NettyClient sInstance;
    private Channel channel = null;
    private EventLoopGroup group = null;
    private Bootstrap bootstrap = null;
    public ClientChannelHandler handler;
    private NettyCallBack callBack;
    private SocketConfig config;
    private NettyStatus status;

    private NettyClient() {
    }

    public static NettyClient getInstance() {
        if (sInstance == null) {
            synchronized (NettyClient.class) {
                if (sInstance == null) {
                    sInstance = new NettyClient();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void init(SocketConfig config, NettyCallBack callBack) {
        //初始化之前先断网一次，防止重复连接多次
        disconnect();
        this.callBack = callBack;
        this.config = config;
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        handler = new ClientChannelHandler(this);
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline(); //用来绑定事件的Handler
                        //如果DELAY_HEART_BEAT内write()方法未被调用则触发一次userEventTrigger()方法，实现客户端每DELAY_HEART_BEAT秒向服务端发送一次消息（心跳包）
                        pipeline.addLast(new IdleStateHandler(NettyConstant.DELAY_HEART_BEAT + 10, NettyConstant.DELAY_HEART_BEAT, NettyConstant.DELAY_HEART_BEAT + 20));
                        pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(NettyConstant.CHANNEL_MAX_SIZE, 7, 4, 4, 0));//消息头偏移
                        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                        pipeline.addLast(handler);
                    }
                });
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000);
    }

    public void reset() {
        callBack = null;
        config = null;
        handler = null;
        channel = null;
        bootstrap = null;
        group = null;
    }

    @Override
    public void connect() {
        if (config == null) {
            throw new IllegalArgumentException("you must call init() method before connect");
        }
        try {
            ChannelFuture channelFuture = bootstrap.connect(config.getHost(), config.getPort()).sync();
            //建立异步连接  //生成channel;
            channel = channelFuture.channel();
        } catch (Exception e) {
            if (callBack != null) {
                callBack.onDisconnected();
            }
            LogUtils.d("catch InterruptedException，connect failed");
//            e.printStackTrace();
            handler.reConnectSocket();
        }
    }

    @Override
    public void disconnect() {
        if (handler != null)
            handler.setForceClosed(true);
        if (channel != null)
            channel.disconnect();
        if (group != null)
            group.shutdownGracefully();

        reset();
    }

    @Override
    public void reconnect() {
//        LogUtils.toastShow("自动执行重连功能...");
        ThreadPoolProxyFactory.getThreadPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    if (handler != null) {
                        handler.setStartFrom(NettyCallBack.CONNECT_TYPE_RECON_AUTO);
                        connect();
                    }
                }
            }
        });
    }

    /**
     * 手动执行重连操作
     */
    public void callReconnect() {
        LogUtils.e("手动执行重连功能...");
        handler.setStartFrom(NettyCallBack.CONNECT_TYPE_RECON_USER);
        connect();
    }

    @Override
    public void reSend() {
        if (PacketCache.getCache() != null) {
            for (Packet<?> packet : PacketCache.sPacketCache) {
                sendMsg(packet);
            }
            PacketCache.clear();
        }
    }

    /**
     * 可实现多处收到消息回调
     *
     * @param listener
     */
    public void addReceiveMsgListener(NettyCallBack.OnReceiveMsgListener listener) {
        if (handler != null) {
            handler.onReceiveMsgListeners.add(listener);
        }
    }

    @Override
    public synchronized boolean sendMsg(ByteBuffer byteBuffer) {
        if (!checkChannel()) {
            return false;
        }
        ByteBuf byteBuf = null;
        //Netty不支持ByteBuffer,需要转化成ByteBuf
        byteBuf = Unpooled.copiedBuffer(byteBuffer); //
        ChannelFuture f = channel.writeAndFlush(byteBuf);
        LogUtils.e("发送成功");
        return true;
    }

    public synchronized boolean sendMsg(Packet packet) {
        LogUtils.e("发送数据包:" + packet.toString());
        return sendMsg(packet.writeObject());
    }

    public synchronized boolean sendMsgSync(Packet msg) {
        LogUtils.e("发送数据包:" + msg.toString());
        return sendMsg(msg.writeObject());
    }

    /**
     * 会检测发送是否成功
     *
     * @param packet
     * @param reSend 发送失败时是否加入失败队列，在socket重连时重新请求
     * @return
     */
    @Deprecated
    public boolean sendMsg(Packet packet, boolean reSend) {
        if (!reSend) {
            return sendMsgSync(packet);
        } else {
            if (!sendMsgSync(packet) && config.isReSendData()) {//当发送消息并且支持失败重发时，放入缓存；
                PacketCache.putPacket(packet);
                LogUtils.e("发送消息失败，放入缓存");
                return false;
            }
            return true;
        }
    }


    /**
     * 检查channel是否合法
     *
     * @return false-不合法
     */
    public boolean checkChannel() {
        if (channel == null) {
            LogUtils.e("channel is null");
            return false;
        }
        if (!channel.isOpen()) {
            LogUtils.e("channel is closed");
            return false;
        }
        return true;
    }

    public NettyCallBack getCallBack() {
        return callBack;
    }

    public SocketConfig getConfig() {
        return config;
    }

}
