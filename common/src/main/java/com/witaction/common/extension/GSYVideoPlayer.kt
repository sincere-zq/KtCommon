package com.witaction.common.extension

import android.content.Context
import android.widget.ImageView
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer

fun GSYVideoPlayer.startPlay(
    videoUrl: String,
    cover: String,
    title: String,
    TAG: String,
    context: Context,
    showFull: () -> Unit,
    switchTitleBarVisible: () -> Unit,
    videoCallPlayBack: GSYSampleCallBack?
) {
    //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
    fullscreenButton.setOnClickListener { showFull() }
    //防止错位设置
    playTag = TAG
    //音频焦点冲突时是否释放
    isReleaseWhenLossAudio = false
    //增加封面
    val imageView = ImageView(context)
    imageView.scaleType = ImageView.ScaleType.CENTER_CROP
    imageView.load(cover)
    thumbImageView = imageView
    thumbImageView.setOnClickListener { switchTitleBarVisible() }
    //是否开启自动旋转
    isRotateViewAuto = false
    //是否需要全屏锁定屏幕功能
    isNeedLockFull = true
    //是否可以滑动调整
    setIsTouchWiget(true)
    //设置触摸显示控制ui的消失时间
    dismissControlTime = 5000
    //设置播放过程中的回调
    videoCallPlayBack?.let {
        setVideoAllCallBack(it)
    }
    //设置播放URL
    setUp(videoUrl, false, title)
    //开始播放
    startPlayLogic()
}

fun GSYVideoPlayer.startAutoPlay(
    context: Context,
    position: Int,
    playUrl: String,
    coverUrl: String,
    playTag: String,
    callBack: GSYSampleCallBack? = null
) {
    //防止错位设置
    setPlayTag(playTag)
    //设置播放位置防止错位
    setPlayPosition(position)
    //音频焦点冲突时是否释放
    setReleaseWhenLossAudio(false)
    //设置循环播放
    setLooping(true)
    //增加封面
    val cover = ImageView(context)
    cover.scaleType = ImageView.ScaleType.CENTER_CROP
    cover.load(coverUrl, 4f)
    cover.parent?.run { removeView(cover) }
    setThumbImageView(cover)
    //设置播放过程中的回调
    setVideoAllCallBack(callBack)
    //设置播放URL
    setUp(playUrl, false, null)
}
