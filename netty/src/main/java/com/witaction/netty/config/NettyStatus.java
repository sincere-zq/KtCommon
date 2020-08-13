package com.witaction.netty.config;

/**
 * Created by Zhangs on 2017/4/24.
 */

public enum NettyStatus {
    CONNECTED(1), //已连接
    RECONNECTED(2),//重连上
    CONNECTING(3), //连接中
    DISCONNECTED(-1);//已断线
    private int status;

    NettyStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
