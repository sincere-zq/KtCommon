package com.witaction.netty.view;


import com.witaction.netty.callback.NettyNetCallback;

/**
 * 初始化操作
 */
public interface IInitHanlde {

    NettyReqView initNettyReqPresenter();

    NettyRspView initNettyRspPresenter();

    NettyNetCallback initNettyCallBack();
}
