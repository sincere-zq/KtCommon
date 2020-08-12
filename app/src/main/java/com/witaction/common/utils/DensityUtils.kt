package com.witaction.common.utils

import android.content.Context
import android.util.TypedValue

private fun DensityUtils() { /* cannot be instantiated */
    throw UnsupportedOperationException("cannot be instantiated")
}

/**
 * dp转px
 *
 * @param context
 * @return
 */
fun dp2px(context: Context, dpVal: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dpVal, context.resources.displayMetrics
    )
}

/**
 * sp转px
 *
 * @param context
 * @return
 */
fun sp2px(context: Context, spVal: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        spVal, context.resources.displayMetrics
    )
}

/**
 * px转dp
 *
 * @param context
 * @param pxVal
 * @return
 */
fun px2dp(context: Context, pxVal: Float): Float {
    val scale = context.resources.displayMetrics.density
    return pxVal / scale
}

/**
 * px转sp
 *
 * @param pxVal
 * @return
 */
fun px2sp(context: Context, pxVal: Float): Float {
    return pxVal / context.resources.displayMetrics.scaledDensity
}