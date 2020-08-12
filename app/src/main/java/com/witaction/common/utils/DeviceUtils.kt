package com.witaction.common.utils

import android.app.Activity
import android.content.Context
import android.hardware.Camera
import android.hardware.Camera.CameraInfo
import android.os.Environment
import android.util.DisplayMetrics
import android.view.Surface
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import java.lang.reflect.Field

/**
 * 设备相关工具类
 */
fun hasSdCard(): Boolean {
    return Environment.getExternalStorageState() ==
            Environment.MEDIA_MOUNTED
}

fun openKeyBord(context: Context) {
    val imm =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED)
}

fun openKeyBoard(context: Context, view: View?) {
    val imm =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
}

fun hideKeyBoard(context: Context, view: View) {
    val imm =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun hideKeyBoard(activity: Activity) {
    val imm =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
}

/**
 * 获取屏幕宽
 *
 * @param context
 * @return
 */
fun getScreenWidth(context: Context): Int {
    val wm = context
        .getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val outMetrics = DisplayMetrics()
    wm.defaultDisplay.getMetrics(outMetrics)
    return outMetrics.widthPixels
}

/**
 * 获取屏幕高
 *
 * @param context
 * @return
 */
fun getScreenHeight(context: Context): Int {
    val wm = context
        .getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val outMetrics = DisplayMetrics()
    wm.defaultDisplay.getMetrics(outMetrics)
    return outMetrics.heightPixels
}

/**
 * 方法，用于根据手机方向获得相机预览画面旋转的角度
 */
fun getPreviewDegree(activity: Activity, cameraID: Int): Int {
    val info = CameraInfo()
    Camera.getCameraInfo(cameraID, info)
    val rotation = activity.windowManager.defaultDisplay
        .rotation
    var degree = 0
    when (rotation) {
        Surface.ROTATION_0 -> degree = 0
        Surface.ROTATION_90 -> degree = 90
        Surface.ROTATION_180 -> degree = 180
        Surface.ROTATION_270 -> degree = 270
    }
    var result: Int
    if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
        result = (info.orientation + degree) % 360
        result = (360 - result) % 360
    } else {
        result = (info.orientation - degree + 360) % 360
    }
    return result
}

/**
 * 获取状态栏高度
 *
 * @param context
 * @return
 */
fun getStatusBarHeight(context: Context): Int {
    var c: Class<*>? = null
    var obj: Any? = null
    var field: Field? = null
    var x = 0
    var sbar = 0
    try {
        c = Class.forName("com.android.internal.R\$dimen")
        obj = c.newInstance()
        field = c.getField("status_bar_height")
        x = field[obj].toString().toInt()
        sbar = context.resources.getDimensionPixelSize(x)
    } catch (e1: Exception) {
        e1.printStackTrace()
    }
    return sbar
}

/**
 * 隐藏或者显示状态栏
 *
 * @param activity
 * @param hide
 */
fun setHideStatusBar(activity: Activity, hide: Boolean) {
    if (hide) {
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    } else {
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}