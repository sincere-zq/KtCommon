package com.witaction.common.utils

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import java.lang.reflect.Field
import java.util.*

/**
 * 为TabLayout设置动画
 */
/**
 * 设置每个TabLayout的自定义View
 * 注意：TabLayout和Viewpager配合使用的时候必须先mViewPager.setAdapter（），再初始化该方法，然后addOnTabSelectedListener；因为adapter刷新会让mCustomViewView空，
 */
fun setCustomViews(
    context: Context,
    tabLayout: TabLayout,
    titleList: List<String>
) {
    val mSelectedTabPosition = tabLayout.selectedTabPosition
    for (i in titleList.indices) {
        val mTab = Objects.requireNonNull(tabLayout.getTabAt(i))
            ?.setCustomView(getTabView(context, titleList, i, mSelectedTabPosition))
        if (i == mSelectedTabPosition) {
            if (mTab != null) {
                changeTabSelect(mTab)
            }
        } else {
            if (mTab != null) {
                changeTabNormal(mTab)
            }
        }
    }
}

/**
 * 提供TabLayout的View
 * 根据index返回不同的View
 * 主意：默认选中的View要返回选中状态的样式
 */
private fun getTabView(
    context: Context,
    titleList: List<String>,
    index: Int,
    mSelectedTabPosition: Int
): View? { //自定义View布局
//        View view = LayoutInflater.from(context).inflate(R.layout.item_place, null);
//        TextView title = view.findViewById(R.id.title);
//        title.setText(titleList.get(index));
//        title.setSelected(index == mSelectedTabPosition);
//        return view;
    return null
}

/**
 * 改变TabLayout的View到选中状态
 * 使用属性动画改编Tab中View的状态
 */
fun changeTabSelect(tab: TabLayout.Tab) {
    var view = tab.customView
    if (view == null) {
        view = tab.view
    }
    val anim = ObjectAnimator
        .ofFloat(view, "", 1.0f, 1.3f)
        .setDuration(200)
    anim.start()
    val finalView: View = view
    anim.addUpdateListener { animation: ValueAnimator ->
        val cVal = animation.animatedValue as Float
        finalView.alpha = 0.5f + (cVal - 1f) * (0.5f / 0.1f)
        finalView.scaleX = cVal
        finalView.scaleY = cVal
    }
}

/**
 * 改变TabLayout的View到未选中状态
 */
fun changeTabNormal(tab: TabLayout.Tab) {
    var view = tab.customView
    if (view == null) {
        view = tab.view
    }
    val anim = ObjectAnimator
        .ofFloat(view, "", 1.3f, 1.0f)
        .setDuration(200)
    anim.start()
    val finalView: View = view
    anim.addUpdateListener { animation: ValueAnimator ->
        val cVal = animation.animatedValue as Float
        finalView.alpha = 1f - (1f - cVal) * (0.5f / 0.1f)
        finalView.scaleX = cVal
        finalView.scaleY = cVal
    }
}

/**
 * 改变tablayout指示器的宽度
 *
 * @param tabLayout
 * @param margin
 */
fun changeTabIndicatorWidth(tabLayout: TabLayout, margin: Int) {
    tabLayout.post {
        try {
            val mTabStripField =
                tabLayout.javaClass.getDeclaredField("mTabStrip")
            mTabStripField.isAccessible = true
            val mTabStrip = mTabStripField[tabLayout] as LinearLayout
            val dp10 = if (margin == 0) 50 else margin
            for (i in 0 until mTabStrip.childCount) {
                val tabView = mTabStrip.getChildAt(i)
                val mTextViewField =
                    tabView.javaClass.getDeclaredField("mTextView")
                mTextViewField.isAccessible = true
                val mTextView = mTextViewField[tabView] as TextView
                tabView.setPadding(0, 0, 0, 0)
                var width = 0
                width = mTextView.width
                if (width == 0) {
                    mTextView.measure(0, 0)
                    width = mTextView.measuredWidth
                }
                val params =
                    tabView.layoutParams as LinearLayout.LayoutParams
                params.width = width
                params.leftMargin = dp10
                params.rightMargin = dp10
                tabView.layoutParams = params
                tabView.invalidate()
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}

fun changeTabIndicatorWidth(tabLayout: TabLayout) {
    changeTabIndicatorWidth(tabLayout, 0)
}

/**
 * 设置tablayout指示器的宽度
 *
 * @param tabs
 * @param leftDip
 * @param rightDip
 */
fun setIndicatorWidth(tabs: TabLayout, leftDip: Int, rightDip: Int) {
    val tabLayout: Class<*> = tabs.javaClass
    var tabStrip: Field? = null
    try {
        tabStrip = tabLayout.getDeclaredField("mTabStrip")
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    }
    assert(tabStrip != null)
    tabStrip!!.isAccessible = true
    var llTab: LinearLayout? = null
    try {
        llTab = tabStrip[tabs] as LinearLayout
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    }
    val left = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        leftDip.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()
    val right = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        rightDip.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()
    assert(llTab != null)
    for (i in 0 until llTab!!.childCount) {
        val child = llTab.getChildAt(i)
        child.setPadding(0, 0, 0, 0)
        val params =
            LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1F)
        params.leftMargin = left
        params.rightMargin = right
        child.layoutParams = params
        child.invalidate()
    }
}