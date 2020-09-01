package com.witaction.common.utils

import android.content.Context
import android.content.pm.ApplicationInfo
import android.util.Log
import java.util.*


private var isLogEnabled = true // 默认开启

private const val defaultTag = "zone" // log默认的 tag

private const val TAG_CONTENT_PRINT = "%s.%s:%d"

/**
 * 获得当前的 堆栈
 *
 * @return
 */
fun getCurrentStackTraceElement(): StackTraceElement {
    return Thread.currentThread().stackTrace[4]
}

/**
 * 设置 debug是否启用 根据 判断 是否 为上线模式 android:debuggable
 * 打包后变为false，没打包前为true
 *
 *
 * 要在application中 首先进行调用此方法 对 isLogEnabled 进行赋值
 *
 * @param context
 * @return
 */
fun setDebugable(context: Context) {
    try {
        val info: ApplicationInfo = context.getApplicationInfo()
        isLogEnabled = info.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
    } catch (e: Exception) { // 友盟上报错误日志
    }
}

/**
 * 获取是否DEBUG模式
 *
 * @return
 */
fun isDebugable(): Boolean {
    return isLogEnabled
}

/**
 * 打印的log信息 类名.方法名:行数--->msg
 *
 * @param trace
 * @return
 */
fun getContent(trace: StackTraceElement): String {
    return java.lang.String.format(
        Locale.CHINA, TAG_CONTENT_PRINT, trace.className, trace.methodName,
        trace.lineNumber
    )
}

/**
 * debug
 *
 * @param tag
 * @param msg
 */
fun logd(tag: String?, msg: String, tr: Throwable?) {
    if (isLogEnabled) {
        Log.d(tag, getContent(getCurrentStackTraceElement()) + "--->" + msg, tr)
    }
}

/**
 * debug
 *
 * @param tag
 * @param msg
 */
fun d(tag: String?, msg: String) {
    if (isLogEnabled) { // getContent(getCurrentStackTraceElement())
        Log.d(tag, "--->$msg")
    }
}

/**
 * debug
 *
 * @param msg
 */
fun logd(msg: String) {
    if (isLogEnabled) {
        Log.d(defaultTag, getContent(getCurrentStackTraceElement()) + "--->" + msg)
    }
}

/**
 * error
 *
 * @param tag
 * @param msg
 */
fun loge(tag: String?, msg: String, tr: Throwable?) {
    if (isLogEnabled) {
        Log.e(tag, getContent(getCurrentStackTraceElement()) + "--->" + msg, tr)
    }
}

/**
 * error
 *
 * @param tag
 * @param msg
 */
fun loge(tag: String?, msg: String) {
    if (isLogEnabled) {
        Log.e(tag, getContent(getCurrentStackTraceElement()) + "--->" + msg)
    }
}

/**
 * error
 *
 * @param msg
 */
fun loge(msg: String) {
    if (isLogEnabled) {
        Log.e(defaultTag, getContent(getCurrentStackTraceElement()) + "--->" + msg)
    }
}

/**
 * info
 *
 * @param tag
 * @param msg
 */
fun logi(tag: String?, msg: String, tr: Throwable?) {
    if (isLogEnabled) {
        Log.i(tag, getContent(getCurrentStackTraceElement()) + "--->" + msg, tr)
    }
}

/**
 * info
 *
 * @param tag
 * @param msg
 */
fun logi(tag: String?, msg: String) {
    if (isLogEnabled) {
        Log.i(tag, getContent(getCurrentStackTraceElement()) + "--->" + msg)
    }
}

/**
 * info
 *
 * @param msg
 */
fun logi(msg: String) {
    if (isLogEnabled) {
        Log.i(defaultTag, getContent(getCurrentStackTraceElement()) + "--->" + msg)
    }
}

/**
 * verbose
 *
 * @param tag
 * @param msg
 */
fun logv(tag: String?, msg: String, tr: Throwable?) {
    if (isLogEnabled) {
        Log.v(tag, getContent(getCurrentStackTraceElement()) + "--->" + msg, tr)
    }
}

/**
 * verbose
 *
 * @param tag
 * @param msg
 */
fun logv(tag: String?, msg: String) {
    if (isLogEnabled) {
        Log.v(tag, getContent(getCurrentStackTraceElement()) + "--->" + msg)
    }
}

/**
 * verbose
 *
 * @param msg
 */
fun logv(msg: String) {
    if (isLogEnabled) {
        Log.v(defaultTag, getContent(getCurrentStackTraceElement()) + "--->" + msg)
    }
}

/**
 * warn
 *
 * @param tag
 * @param msg
 */
fun logw(tag: String?, msg: String, tr: Throwable?) {
    if (isLogEnabled) {
        Log.w(tag, getContent(getCurrentStackTraceElement()) + "--->" + msg, tr)
    }
}

/**
 * warn
 *
 * @param tag
 * @param msg
 */
fun logw(tag: String?, msg: String) {
    if (isLogEnabled) {
        Log.w(tag, getContent(getCurrentStackTraceElement()) + "--->" + msg)
    }
}

/**
 * warn
 *
 * @param msg
 */
fun logw(msg: String) {
    if (isLogEnabled) {
        Log.w(defaultTag, getContent(getCurrentStackTraceElement()) + "--->" + msg)
    }
}