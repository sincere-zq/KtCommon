package com.witaction.websocket;

/**
 *  线程池工厂类
 */
public class ThreadPoolProxyFactory {
    private static ThreadPoolProxy mNormalThreadPoolProxy;

    private ThreadPoolProxyFactory() {

    }

    /**
     * 得到普通线程池代理对象mNormalThreadPoolProxy
     */
    public static synchronized ThreadPoolProxy getThreadPoolProxy() {

        if (mNormalThreadPoolProxy == null) {
            mNormalThreadPoolProxy = new ThreadPoolProxy(20, 20);
        }
        return mNormalThreadPoolProxy;
    }
}

