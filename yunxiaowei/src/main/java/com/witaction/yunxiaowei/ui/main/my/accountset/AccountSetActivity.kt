package com.witaction.yunxiaowei.ui.main.my.accountset

import android.net.Uri
import androidx.lifecycle.observe
import com.witaction.common.base.BVMActivity
import com.witaction.common.extension.load
import com.witaction.common.extension.open
import com.witaction.common.utils.GlobalUtil
import com.witaction.common.utils.PhotoUtils
import com.witaction.common.utils.toast
import com.witaction.common.widget.TakePickturePopupwindow
import com.witaction.yunxiaowei.R
import com.witaction.yunxiaowei.databinding.ActivityAccountSetBinding
import com.witaction.yunxiaowei.framwork.AppConfig
import com.witaction.yunxiaowei.framwork.LocalRepository
import com.witaction.yunxiaowei.framwork.MsgEvent
import com.witaction.yunxiaowei.ui.main.my.accountset.collectface.CollectFaceActivity
import com.witaction.yunxiaowei.ui.main.my.accountset.realname.RealNameActivity
import com.witaction.yunxiaowei.ui.main.my.accountset.resetphone.ResetPhoneActivity
import com.witaction.yunxiaowei.ui.main.my.accountset.resetpwd.ResetPwdActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class AccountSetActivity : BVMActivity<ActivityAccountSetBinding, AccountSetViewMode>() {

    override fun vmbinding(): Class<AccountSetViewMode> = AccountSetViewMode::class.java

    override fun viewbinding(): ActivityAccountSetBinding =
        ActivityAccountSetBinding.inflate(layoutInflater)

    override fun initView() {
        EventBus.getDefault().register(this)
        vb.headerView.setHeaderListener(this)
        GlobalUtil.setOnClickListener(
            vb.rlHeadAvator,
            vb.llPersonName,
            vb.tvPersonFace,
            vb.tvChangePwd,
            vb.tvChangePhone
        ) {
            when (this) {
                vb.rlHeadAvator -> {
                    TakePickturePopupwindow(
                        this@AccountSetActivity,
                        { takePhotoFromAlbum() },
                        { takePhotoFromCamera() }
                    ).show(vb.headerView)
                }
                vb.llPersonName -> {
                    open<RealNameActivity>()
                }
                vb.tvPersonFace -> {
                    open<CollectFaceActivity>()
                }
                vb.tvChangePwd -> {
                    open<ResetPwdActivity>()
                }
                vb.tvChangePhone -> {
                    open<ResetPhoneActivity>()
                }
            }
        }
    }

    override fun initData() {
        vm.userInfo.observe(this) {
            it.run {
                vb.imgAvator.load(avatarUrl) {
                    error(R.mipmap.icon_home_placeholder)
                }
                vb.tvPersonName.text = name
            }
        }

        vm.updateAvatarResult.observe(this) {
            hideLoading()
            if (it.isSuccess()) {
                vm.userInfo.value?.run {
                    avatarUrl = it.getSimpleData()?.avatarUrl ?: ""
                    LocalRepository.saveUserInfo(this)
                }
                EventBus.getDefault()
                    .post(MsgEvent.UpdateUserInfoEvent(true))
            } else {
                toast(it.msg)
            }
        }
    }

    /**
     * 从相册获取照片
     */
    private fun takePhotoFromAlbum() {
        PhotoUtils.select(this) { uri, success, msg ->
            if (success) {
                if (uri != null) {
                    crop(uri)
                }
            } else {
                toast(msg)
            }
        }
    }

    /**
     * 从相机获取照片
     */
    private fun takePhotoFromCamera() {
        PhotoUtils.camera(this) { uri, success, msg ->
            if (success) {
                uri?.let {
                    crop(it)
                }
            } else {
                toast(msg)
            }
        }
    }

    /**
     * 裁剪
     */
    private fun crop(uri: Uri) {
        PhotoUtils.crop(this).apply {
            setAspect(1, 1)
            setOutput(AppConfig.HEAD_AVATAR, AppConfig.HEAD_AVATAR)
        }.build(uri) { cropUri, success, msg ->
            if (success) {
                cropUri?.let {
                    vb.imgAvator.load(it) {
                        error(R.mipmap.icon_home_placeholder)
                    }
                    updateAvatar(it)
                }
            } else {
                toast(msg)
            }
        }
    }

    /**
     * 上传头像
     */
    private fun updateAvatar(uri: Uri) {
        showLoading()
        GlobalScope.launch(Dispatchers.IO) {
            val content = PhotoUtils.getBitmapBase64FromUri(uri, this@AccountSetActivity)
            if (content != null) {
                vm.updateAvatar(content)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun updatedAvatar(event: MsgEvent.UpdateUserInfoEvent) {
        event.run {
            if (isUpdated) {
                vm.userInfo.value = LocalRepository.getUserInfo()
            }
        }
    }
}
