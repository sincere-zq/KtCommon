package com.witaction.netty.view;


import com.witaction.netty.callback.NettyNetCallback;
import com.witaction.netty.view.NettyReqView;
import com.witaction.netty.view.NettyRspView;
import com.zhixing.entity.view.IHandleCardView;
import com.zhixing.entity.view.IHandleFaceView;
import com.zhixing.entity.view.IHandleFingerView;

/**
 * 初始化操作
 */
public interface IInitHanlde {
    IHandleFaceView initFacePresenter();

    IHandleCardView initCardPresenter();

    IHandleFingerView initFingerPresenter();

    NettyReqView initNettyReqPresenter();

    NettyRspView initNettyRspPresenter();

    NettyNetCallback initNettyCallBack();
}
