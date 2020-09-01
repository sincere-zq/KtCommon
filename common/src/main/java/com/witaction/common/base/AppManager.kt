package com.witaction.common.base

import android.app.Activity
import android.os.Process
import java.util.*

/**
 * 管理Activity
 */
class AppManager private constructor() {
    private val mActivityStack = Stack<Activity>()

    private object SingletonHolder {
        val instance = AppManager()
    }

    companion object {
        @JvmStatic
        fun getInstance(): AppManager = SingletonHolder.instance
    }

    @Synchronized
    fun activityNumber(): Int {
        return mActivityStack.size
    }

    /**
     * 添加Activity到堆栈
     */
    @Synchronized
    fun addActivity(activity: Activity?) {
        mActivityStack.add(activity)
    }

    /**
     * 获取栈顶Activity（堆栈中最后一个压入的）
     */
    @Synchronized
    fun getTopActivity(): Activity? {
        return mActivityStack.lastElement()
    }

    /**
     * 结束栈顶Activity（堆栈中最后一个压入的）
     */
    @Synchronized
    fun killTopActivity() {
        val activity: Activity = mActivityStack.lastElement()
        killActivity(activity)
    }

    /**
     * 结束指定的Activity
     */
    @Synchronized
    fun killActivity(activity: Activity?) {
        if (activity != null) {
            mActivityStack.remove(activity)
            activity.finish()
        }
    }

    /**
     * 结束指定类名的Activity
     */
    @Synchronized
    fun killActivity(vararg calsses: Class<*>) {
        if (mActivityStack.isEmpty()) return
        val activities: MutableList<Activity> = ArrayList()
        for (cls in calsses) {
            for (activity in mActivityStack) {
                if (activity.javaClass == cls) {
                    activities.add(activity)
                }
            }
        }
        for (activity in activities) {
            killActivity(activity)
        }
    }

    /**
     * 结束所有Activity
     */
    @Synchronized
    fun killAllActivity() {
        if (mActivityStack.isEmpty()) {
            return
        }
        var i = 0
        val size: Int = mActivityStack.size
        while (i < size) {
            if (null != mActivityStack.get(i)) {
                mActivityStack.get(i).finish()
            }
            i++
        }
        mActivityStack.clear()
    }

    /**
     * 结束除了当前的其他所有Activity
     *
     * @param activity
     */
    @Synchronized
    fun killOthersActivity(activity: Activity?) {
        if (activity == null) {
            return
        }
        var i = 0
        val size: Int = mActivityStack.size
        while (i < size) {
            if (null != mActivityStack.get(i) && activity !== mActivityStack.get(
                    i
                )
            ) {
                mActivityStack.get(i).finish()
            }
            i++
        }
        mActivityStack.clear()
        mActivityStack.add(activity)
    }

    /**
     * 判断Activity是否存在
     *
     * @param className
     * @return
     */
    @Synchronized
    fun existActivity(className: String): Boolean {
        val activity = getActivityByName(className)
        return activity != null && !activity.isFinishing
    }

    /**
     * 根据名字查找Activity
     *
     * @param className
     * @return
     */
    @Synchronized
    fun getActivityByName(className: String): Activity? {
        var activity: Activity? = null
        var i = 0
        val size: Int = mActivityStack.size
        while (i < size) {
            if (null != mActivityStack.get(i)) {
                if (mActivityStack.get(i).javaClass.getName() == className) {
                    activity = mActivityStack.get(i)
                }
            }
            i++
        }
        return activity
    }

    /**
     * 删除并结束掉Activity
     *
     * @param activity
     */
    @Synchronized
    fun finishActivity(activity: Activity?) {
        var pos = -1
        if (activity != null) {
            var i = 0
            val size: Int = mActivityStack.size
            while (i < size) {
                if (null != mActivityStack.get(i)) {
                    if (activity === mActivityStack.get(i)) {
                        pos = i
                        activity.finish()
                    }
                }
                i++
            }
            if (pos != -1) {
                mActivityStack.removeAt(pos)
            }
        }
    }

    /**
     * 从栈里删除activity
     *
     * @param activity
     */
    @Synchronized
    fun remove(activity: Activity?) {
        if (activity != null) {
            mActivityStack.remove(activity)
            if (mActivityStack.size == 0) {
                appExit()
            }
        }
    }

    /**
     * 退出应用程序
     */
    @Synchronized
    fun appExit() {
        killAllActivity()
        System.gc()
        Process.killProcess(Process.myPid())
        System.exit(0)
    }
}