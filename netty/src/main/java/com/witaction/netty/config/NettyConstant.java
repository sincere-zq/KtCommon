package com.witaction.netty.config;


public class NettyConstant {
//    public static final String HOST = "";
//    public static final int PORT = 6413;

    public static final boolean RECONNECTED = true;//是否支持断线自动重连
    public static final long DELAY_CONNECT = 10000; //重连延时
    public static final int CONNECT_FOREVER = -100;
    public static final int RECONNECTED_TIME = CONNECT_FOREVER;  //设置重连次数； CONNECT_FOREVER永久重连
    public static final boolean RESEND_DATA = false;//支持失败重发；
    public static final int DELAY_HEART_BEAT = 20;//心跳延迟,单位秒
    public static final int CHANNEL_MAX_SIZE = 10 * 1024 * 1024;//发送数据最大长度（10m）
}
