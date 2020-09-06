package com.witaction.yunxiaowei.framwork;

public interface NetStateConfig {
    //刷新
    int REFRESHING = 0x110;
    //加载中
    int LOADINGING = 0x111;
    //空数据
    int EMPTYDATA = 0x112;
    //网络断开
    int NETERROR = 0x113;
    //加载完成
    int FINISHED = 0x114;
}
