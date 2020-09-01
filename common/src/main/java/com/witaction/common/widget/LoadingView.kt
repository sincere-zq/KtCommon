package com.witaction.common.widget

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.witaction.common.R
import com.witaction.common.databinding.ViewLoadingBinding

/**
 * 加载View
 */
class LoadingView : FrameLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, attrStyle: Int) :
            super(context, attributeSet, attrStyle)

    private val binding: ViewLoadingBinding =
        ViewLoadingBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        start()
    }

    /**
     * 开始
     */
    fun start() {
        if (visibility == View.GONE) {
            visibility = View.VISIBLE
        }
        binding.imgLoading.setBackgroundResource(R.drawable.anim_loading)
        (binding.imgLoading.background as AnimationDrawable).start()
    }

    /**
     * 隐藏
     */
    fun hide() {
        if (visibility == View.VISIBLE) {
            visibility = View.GONE
        }
    }

    /**
     * 显示错误信息
     */
    fun error(msg: String?, errorImgResourse: Int, block: View.() -> Unit) {
        if (visibility == View.GONE) {
            visibility = View.VISIBLE
        }
        binding.imgLoading.setBackgroundResource(errorImgResourse)
        binding.tvLoading.text = msg
        setOnClickListener { block() }
    }
}