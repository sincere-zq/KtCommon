package com.witaction.websocket;


import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池代理类，替线程池做一些操作，即可以暴露一些线程池本有的方法，
 * 也可以写一些对线程池的拓展方法。
 */
public class ThreadPoolProxy {

    private ThreadPoolExecutor mThreadPoolExecutor;
    private int mCorePoolSize;
    private int mMaximumPoolSize;

    public ThreadPoolProxy(int corePoolSize, int maximumPoolSize) {
        mCorePoolSize = corePoolSize;
        mMaximumPoolSize = maximumPoolSize;
        initThreadPoolExecutor();
    }

    /**
     * @des 初始化线程池
     */
    private void initThreadPoolExecutor() {
        if (mThreadPoolExecutor == null || mThreadPoolExecutor.isShutdown() || mThreadPoolExecutor.isTerminated()) {
            synchronized (ThreadPoolProxy.class) {
                if (mThreadPoolExecutor == null || mThreadPoolExecutor.isShutdown() || mThreadPoolExecutor.isTerminated()) {
                    //这里不保持时间
                    long keepAliveTime = 3000;
                    //毫秒
                    TimeUnit unit = TimeUnit.MILLISECONDS;
//                    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
                    BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();
                    //默认线程工厂
                    ThreadFactory threadFactory = Executors.defaultThreadFactory();
                    //这里不对异常进行处理
                    RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
                    mThreadPoolExecutor = new ThreadPoolExecutor(
                            //线程池核心数
                            mCorePoolSize,
                            //最大线程数
                            mMaximumPoolSize,
                            keepAliveTime,
                            unit,
                            workQueue,
                            threadFactory,
                            handler
                    );
                }
            }
        }
    }

    /**
     * @param task
     * @return 得到异步执行完成之后的结果
     * @des 提交任务
     */
    public Future<?> submit(Runnable task) {
        initThreadPoolExecutor();
        if (mThreadPoolExecutor != null) {
            return mThreadPoolExecutor.submit(task);
        } else {
            return null;
        }
    }

    /**
     * @param task
     * @des 执行任务
     */
    public void execute(Runnable task) {
        initThreadPoolExecutor();
        Log.d("CorePoolSize数量", String.valueOf(mThreadPoolExecutor.getCorePoolSize()));
        Log.d("ActiveCount数量", String.valueOf(mThreadPoolExecutor.getActiveCount()));
        if (mThreadPoolExecutor != null) {
            mThreadPoolExecutor.execute(task);
        }
    }

    /**
     * @param task
     * @des 移除任务
     */
    public void remove(Runnable task) {
        if (mThreadPoolExecutor != null) {
            mThreadPoolExecutor.remove(task);
        }

    }

    /**
     * 线程池关闭
     */
    public void shutdown() {
        if (mThreadPoolExecutor != null) {
            mThreadPoolExecutor.shutdown();
            mThreadPoolExecutor = null;
        }
    }

}
