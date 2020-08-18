package com.witaction.common.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.witaction.common.R

class DividerDecoration constructor(
    mContext: Context,
    private var mOrientation: Int,
    dividerResId: Int,
    private var mMarginLeft: Int,
    private var mMarginRight: Int,
    private var mIsShowLastLine: Boolean
) : RecyclerView.ItemDecoration() {

    private val mDivider: Drawable = mContext.resources.getDrawable(dividerResId)

    constructor(mContext: Context) : this(
        mContext,
        LinearLayoutManager.VERTICAL,
        R.drawable.shape_rv_divider,
        0,
        0,
        false
    )

    /**
     * 使用默认分割线
     */
    constructor(
        mContext: Context,
        mOrientation: Int
    ) : this(mContext, mOrientation, R.drawable.shape_rv_divider, 0, 0, false)

    /**
     * 使用指定分割线
     */
    constructor(
        mContext: Context,
        mOrientation: Int,
        dividerResId: Int
    ) : this(mContext, mOrientation, dividerResId, 0, 0, false)

    /**
     * 使用指定分割线
     */
    constructor(
        mContext: Context,
        mOrientation: Int,
        dividerResId: Int,
        mIsShowLastLine: Boolean
    ) : this(mContext, mOrientation, dividerResId, 0, 0, mIsShowLastLine)

    /**
     * 指定是否显示最后一条分割线
     */
    constructor(
        mContext: Context,
        mOrientation: Int,
        mIsShowLastLine: Boolean
    ) : this(mContext, mOrientation, R.drawable.shape_rv_divider, 0, 0, mIsShowLastLine)

    /**
     * 使用默认分割线,指定左边距（单位：dp）
     */
    constructor(
        mContext: Context,
        mOrientation: Int,
        mMarginLeft: Int,
        mMarginRight: Int
    ) : this(mContext, mOrientation, R.drawable.shape_rv_divider, mMarginLeft, mMarginRight, false)

    override fun onDraw(
        c: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            drawVerticalLine(c, parent)
        } else {
            drawHorizontalLine(c, parent)
        }
    }

    //画横线, 这里的parent其实是显示在屏幕显示的这部分
    private fun drawHorizontalLine(
        c: Canvas,
        parent: RecyclerView
    ) {
        val left = parent.paddingLeft + mMarginLeft
        val right = parent.width - parent.paddingRight - mMarginRight
        var childCount = parent.childCount
        if (!mIsShowLastLine) {
            childCount--
        }
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            //获得child的布局信息
            val params =
                child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider.intrinsicHeight
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }

    //画竖线
    private fun drawVerticalLine(
        c: Canvas,
        parent: RecyclerView
    ) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom
        var childCount = parent.childCount
        if (!mIsShowLastLine) {
            childCount--
        }
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            //获得child的布局信息
            val params =
                child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin
            val right = left + mDivider.intrinsicWidth
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }

    //由于Divider也有长宽高，每一个Item需要向下或者向右偏移
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (mOrientation == LinearLayoutManager.VERTICAL) { //画横线，就是往下偏移一个分割线的高度
            outRect[0, 0, 0] = mDivider.intrinsicHeight
        } else { //画竖线，就是往右偏移一个分割线的宽度
            outRect[0, 0, mDivider.intrinsicWidth] = 0
        }
    }
}