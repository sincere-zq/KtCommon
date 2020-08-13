package com.witaction.netty.callback;

/**
 * Socket的被动回调处理，默认在创建线程回调；
 */

public interface NettyCallBack<T> {

    //连接建立的来源
    int CONNECT_TYPE_START = 11; //来自自启动
    int CONNECT_TYPE_RECON_AUTO = 12;//来自自动重连
    int CONNECT_TYPE_RECON_USER = 13;//来自用户调用重连

    /**
     * 网络连接回调
     *
     * @param comefrom 是否来自重连
     */
    void onConnect(int comefrom);

    /**
     * 收到消息
     *
     * @param data
     */
    void onReceiveMsg(T data);

    /**
     * 连接断开
     */
    void onDisconnected();

    /**
     * 抛出异常,一般是由于网络问题导致socket连接中断
     *
     * @param e
     */
    void onException(Throwable e);

    /**
     * 设置单独的消息接收回调事件，在多个地方需要使用接收消息的时候
     */
    interface OnReceiveMsgListener<T> {
        void onReceiveMsg(T data);
    }
}
