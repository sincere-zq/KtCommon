package com.witaction.common.widget

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.gyf.immersionbar.ImmersionBar
import com.witaction.common.databinding.PopupTakePicturesBinding
import com.witaction.common.utils.GlobalUtil

/**
 * 选择照片弹窗
 */
class TakePickturePopupwindow(
    val context: Context,
    val albumListener: AlbumListener,
    val cameraListener: CameraListener
) : PopupWindow(context) {
    private lateinit var binding: PopupTakePicturesBinding

    init {
        init(context)
    }

    private fun init(context: Context?) {
        binding = PopupTakePicturesBinding.inflate(LayoutInflater.from(context))
        contentView = binding.root
        isFocusable = true
        isClippingEnabled = false
        setBackgroundDrawable(ColorDrawable(0))
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        width = ViewGroup.LayoutParams.MATCH_PARENT
        GlobalUtil.setOnClickListener(
            binding.llRoot,
            binding.tvAlbum,
            binding.tvCamera,
            binding.tvCancle
        ) {
            when (this) {
                binding.llRoot -> {
                    dismiss()
                }
                binding.tvAlbum -> {
                    dismiss()
                    albumListener(this@TakePickturePopupwindow)
                }
                binding.tvCamera -> {
                    dismiss()
                    cameraListener(this@TakePickturePopupwindow)
                }
                binding.tvCancle -> {
                    dismiss()
                }
            }
        }
        setOnDismissListener {
            ImmersionBar.with(context as Activity)
                .statusBarDarkFont(true, 0.2f)   //状态栏字体是深色，不写默认为亮色
                .init()
            GlobalUtil.backgroundAlpha(context as Activity, 1f)
        }
    }

    fun show(view: View) {
        //设置界面背景透明度
        //设置界面背景透明度
        GlobalUtil.backgroundAlpha(context as Activity, 0.8f)
        ImmersionBar.with(context as Activity)
            .statusBarDarkFont(false, 0.2f)   //状态栏字体是深色，不写默认为亮色
            .init()
        showAtLocation(view, Gravity.BOTTOM, 0, 0)
    }
}

private typealias AlbumListener = (popup: PopupWindow) -> Unit
private typealias CameraListener = (popup: PopupWindow) -> Unit