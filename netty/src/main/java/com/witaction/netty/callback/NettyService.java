package com.witaction.netty.callback;

import com.witaction.netty.config.SocketConfig;

import java.nio.ByteBuffer;

/**
 * Created by Zhangs on 2017/3/9.
 * netty工具的主动操作接口；
 */

public interface NettyService {
    /**
     * 初始化连接
     *
     * @param config
     * @param callBack
     */
    void init(SocketConfig config, NettyCallBack callBack);

    /**
     * 建立连接，成功后会回调到NettCallBack
     */
    void connect();

    /**
     * 发送消息
     *
     * @param byteBuffer
     * @return 发送是否成功；
     */
    boolean sendMsg(ByteBuffer byteBuffer);

    /**
     * 断开连接
     */
    void disconnect();

    /**
     * 重连
     */
    void reconnect();

    /**
     * 重发未处理的请求,目前只考虑重发一次，若再失败则不处理；
     */
    @Deprecated
    void reSend();

}
