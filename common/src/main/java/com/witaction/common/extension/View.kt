package com.eyepetizer.android.extension

import android.content.res.TypedArray
import android.os.Build
import android.view.View
import android.view.animation.AlphaAnimation
import com.witaction.common.R

/**
 * 显示view
 */
fun View?.visible() {
    this?.visibility = View.VISIBLE
}

/**
 * 显示view，带有渐显动画效果。
 *
 * @param duration 毫秒，动画持续时长，默认500毫秒。
 */
fun View?.visibleAlphaAnimation(duration: Long = 500L) {
    this?.visibility = View.VISIBLE
    this?.startAnimation(AlphaAnimation(0f, 1f).apply {
        this.duration = duration
        fillAfter = true
    })
}

fun View.setTouchFeedBack() {
    context?.let {
        if (Build.VERSION.SDK_INT >= 21) { //5.0以上系统判断
            val attrs = intArrayOf(R.attr.selectableItemBackgroundBorderless)
            val typedArray: TypedArray = it.theme.obtainStyledAttributes(attrs)
            val resourceId = typedArray.getResourceId(0, 0)
            setBackgroundResource(resourceId)
        }
    }
}

/**
 * 隐藏view
 */
fun View?.gone() {
    this?.visibility = View.GONE
}

fun View.gone(show: Boolean) {
    this.visibility = if (show) View.VISIBLE else View.GONE
}

/**
 * 隐藏view，带有渐隐动画效果。
 *
 * @param duration 毫秒，动画持续时长，默认500毫秒。
 */
fun View?.goneAlphaAnimation(duration: Long = 500L) {
    this?.visibility = View.GONE
    this?.startAnimation(AlphaAnimation(1f, 0f).apply {
        this.duration = duration
        fillAfter = true
    })
}

/**
 * 占位隐藏view
 */
fun View?.invisible() {
    this?.visibility = View.INVISIBLE
}

/**
 * 占位隐藏view，带有渐隐动画效果。
 *
 * @param duration 毫秒，动画持续时长，默认500毫秒。
 */
fun View?.invisibleAlphaAnimation(duration: Long = 500L) {
    this?.visibility = View.INVISIBLE
    this?.startAnimation(AlphaAnimation(1f, 0f).apply {
        this.duration = duration
        fillAfter = true
    })
}