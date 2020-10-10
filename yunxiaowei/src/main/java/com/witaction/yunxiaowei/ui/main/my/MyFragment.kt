package com.witaction.yunxiaowei.ui.main.my

import android.view.LayoutInflater
import androidx.lifecycle.observe
import com.eyepetizer.android.extension.gone
import com.witaction.common.base.BVMFragment
import com.witaction.common.extension.load
import com.witaction.common.extension.open
import com.witaction.common.utils.GlobalUtil
import com.witaction.common.utils.toast
import com.witaction.common.widget.ConfirmDialog
import com.witaction.yunxiaowei.R
import com.witaction.yunxiaowei.databinding.FragmentMyBinding
import com.witaction.yunxiaowei.framwork.AppConfig
import com.witaction.yunxiaowei.framwork.LocalRepository
import com.witaction.yunxiaowei.framwork.MsgEvent
import com.witaction.yunxiaowei.ui.login.LoginActivity
import com.witaction.yunxiaowei.ui.main.my.accountset.AccountSetActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 我的
 */
class MyFragment : BVMFragment<FragmentMyBinding, MyViewModel>() {
    override fun vmbinding(): Class<MyViewModel> = MyViewModel::class.java

    override fun viewbinding(layoutInflater: LayoutInflater): FragmentMyBinding =
        FragmentMyBinding.inflate(layoutInflater)

    override fun initView() {
        EventBus.getDefault().register(this)
        GlobalUtil.setOnClickListener(
            vb.rlUserInfo,
            vb.tvAccountSet,
            vb.tvSwitchIdentity,
            vb.tvLoginOut
        ) {
            when (this) {
                vb.rlUserInfo, vb.tvAccountSet -> {
                    open<AccountSetActivity>()
                }
                vb.tvSwitchIdentity -> {
                    switchIdentity()
                }
                vb.tvLoginOut -> {
                    loginOut()
                }
            }
        }
    }

    override fun initData() {
        vm.userInfo.observe(this) {
            vb.imgAvator.load(it.avatarUrl) {
                error(R.mipmap.icon_home_placeholder)
            }
            vb.tvSwitchIdentity.gone(it.showSwitchBtn == 1)
            vb.tvIdentity.text = when (it.userType) {
                AppConfig.STUDENT -> GlobalUtil.getString(R.string.student)
                AppConfig.TEACHER -> GlobalUtil.getString(R.string.teacher)
                else -> GlobalUtil.getString(R.string.parent)
            }
        }

        vm.switchIdentityResult.observe(this) {
            hideLoading()
            if (it.isSuccess()) {
                val userInfo = vm.userInfo.value
                userInfo?.run {
                    when (userType) {
                        AppConfig.TEACHER -> userType = AppConfig.PARENT
                        AppConfig.PARENT -> userType = AppConfig.TEACHER
                    }
                    LocalRepository.saveUserInfo(this)
                    vm.userInfo.value = this
                    EventBus.getDefault().post(MsgEvent.RefreshHomeDataEvent(true))
                }
            } else {
                toast(it.msg)
            }
        }
    }

    /**
     * 登出
     */
    private fun loginOut() {
        activity?.let {
            ConfirmDialog.build(it) {
                message { GlobalUtil.getString(R.string.confirm_to_login_out) }
                cancelListener {
                    { it1 ->
                        it1.dismiss()
                    }
                }
                confirmListener {
                    { it1 ->
                        it1.dismiss()
                        LocalRepository.loginOut()
                        open<LoginActivity>()
                        it.finish()
                    }
                }
            }.show()
        }
    }

    /**
     * 切换身份
     */
    private fun switchIdentity() {
        activity?.let {
            ConfirmDialog.build(it) {
                message {
                    when (vm.userInfo.value?.userType) {
                        AppConfig.TEACHER -> GlobalUtil.getString(R.string.confirm_switch_to_parent)
                        else -> GlobalUtil.getString(R.string.confirm_switch_to_teacher)
                    }
                }
                cancelListener {
                    { it1 ->
                        it1.dismiss()
                    }
                }
                confirmListener {
                    { it1 ->
                        it1.dismiss()
                        showLoading()
                        vm.switchIdentity()
                    }
                }
            }.show()
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
