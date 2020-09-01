package com.witaction.common.utils

import android.util.TypedValue
import com.witaction.common.base.BApp

object DensityUtils {
    /**
     * dp转px
     *
     * @param context
     * @return
     */
    fun dp2px(dpVal: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpVal, BApp.context.resources.displayMetrics
        )
    }

    /**
     * sp转px
     *
     * @param context
     * @return
     */
    fun sp2px(spVal: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            spVal, BApp.context.resources.displayMetrics
        )
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    fun px2dp(pxVal: Float): Float {
        val scale = BApp.context.resources.displayMetrics.density
        return pxVal / scale
    }

    /**
     * px转sp
     *
     * @param pxVal
     * @return
     */
    fun px2sp(pxVal: Float): Float {
        return pxVal / BApp.context.resources.displayMetrics.scaledDensity
    }

    /**
     * 获取屏幕宽值。
     */
    val screenWidth
        get() = BApp.context.resources.displayMetrics.widthPixels

    /**
     * 获取屏幕高值。
     */
    val screenHeight
        get() = BApp.context.resources.displayMetrics.heightPixels

    /**
     * 获取屏幕像素：对获取的宽高进行拼接。例：1080X2340。
     */
    fun screenPixel(): String {
        BApp.context.resources.displayMetrics.run {
            return "${widthPixels}X${heightPixels}"
        }
    }
}