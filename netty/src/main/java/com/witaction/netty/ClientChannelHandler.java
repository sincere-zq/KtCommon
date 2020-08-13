package com.witaction.netty;

import android.os.Handler;
import android.os.Message;

import com.witaction.netty.callback.NettyCallBack;
import com.witaction.netty.config.NettyConstant;
import com.witaction.netty.config.SocketConfig;
import com.witaction.netty.utils.PacketFactory;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import edu.dbke.socket.cp.Packet;
import edu.dbke.socket.cp.ProtocolType;
import edu.dbke.socket.cp.packet.EmptyPacket;
import edu.dbke.socket.cp.util.LogUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

/**
 * Socket收到数据的回调类,处理packet
 */
@ChannelHandler.Sharable
public class ClientChannelHandler extends ChannelHandlerAdapter {
    private NettyClient nettyClient;
    public NettyCallBack<Packet> callBack;
    private SocketConfig config;
    private int startFrom;//Netty启动来源
    private int connectTimes;//已尝试重连接次数
    private boolean forceClosed = false;//是否主动关闭连接,退出程序时调用；
    public ArrayList<NettyCallBack.OnReceiveMsgListener> onReceiveMsgListeners;

    public ClientChannelHandler(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
        callBack = nettyClient.getCallBack();
        config = nettyClient.getConfig();
        onReceiveMsgListeners = new ArrayList<>();
        startFrom = NettyCallBack.CONNECT_TYPE_START;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        LogUtils.e("Heart beat...");
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                LogUtils.e("read idle");
                ctx.close();
            } else if (event.state() == IdleState.WRITER_IDLE) {
                LogUtils.e("write idle");
            } else if (event.state() == IdleState.ALL_IDLE) {
                ctx.close();
                LogUtils.e("all idle");
            }
        }
        LogUtils.d("触发心跳包");
        nettyClient.sendMsg(new EmptyPacket(ProtocolType.SERVER_STATUS_ECHO));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        try {
            LogUtils.e("---------------packet start----------------------");
            LogUtils.e("协议头：" + buf.readInt() + ",版本号为：" + buf.readByte());
            LogUtils.e(",协议号为：" + buf.readShort() + "的数据\n" + "收到数据长度为：" + buf.readInt());
//            ByteUtil.printBytes(buf.array());
            Packet<?> packet = getPacket(ByteBuffer.wrap(buf.array()));//解析包
            //如果解析结果为空就直接返回
            if (packet == null) {
                return;
            }
            LogUtils.e("解析包结果:" + packet.toString());
            LogUtils.e("----------------packet end---------------------");
            if (packet.type == ProtocolType.SERVER_STATUS_ECHO) {
                //heart-beat ,do nothing.
            } else {
                dispatchData(packet);
            }
        } catch (Exception e) {
            LogUtils.e("catch read exception");
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.safeRelease(buf);
            ctx.flush();
        }
    }

    private Packet<?> getPacket(ByteBuffer byteBuffer) {
        byteBuffer.getInt();
        byteBuffer.get();
        short type = byteBuffer.getShort();
        byteBuffer.clear();
        return PacketFactory.getPacket(type, byteBuffer);
    }

    /**
     * 分发数据
     */
    private void dispatchData(Packet<?> packet) {
        if (packet == null) {
            LogUtils.e("getPacket from factory failed");
            return;
        }

        if (callBack != null) {
            callBack.onReceiveMsg(packet);
        }

        if (!onReceiveMsgListeners.isEmpty()) {
            for (NettyCallBack.OnReceiveMsgListener listener : onReceiveMsgListeners) {
                listener.onReceiveMsg(packet);
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        if (config.isReSendData()) {
            nettyClient.reSend();
        }
        if (callBack != null) {
            callBack.onConnect(startFrom);
        }

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        if (callBack != null) {
            callBack.onDisconnected();
        }
        reConnectSocket();
        //TODO 改动2   ctx.close();
        ctx.close();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            nettyClient.reconnect();
            connectTimes++;
        }
    };

    public void reConnectSocket() {
        //重连机制
        if (config.isSupportReconnect() && !forceClosed) {
            if (config.getReconnectTime() == NettyConstant.CONNECT_FOREVER || connectTimes <= config.getReconnectTime()) {
                handler.sendEmptyMessageDelayed(0x123, config.getConnectDelay());
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        //TODO 改动1  ctx.close();
        ctx.close();
        if (callBack != null) {
            callBack.onException(cause);
        }
    }

    public int getStartFrom() {
        return startFrom;
    }

    public void setStartFrom(int startFrom) {
        this.startFrom = startFrom;
    }

    public void setForceClosed(boolean forceClosed) {
        this.forceClosed = forceClosed;
    }


}
