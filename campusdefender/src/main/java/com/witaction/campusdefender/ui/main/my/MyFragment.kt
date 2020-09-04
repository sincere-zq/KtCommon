package com.witaction.campusdefender.ui.main.my

import android.view.LayoutInflater
import com.eyepetizer.android.extension.gone
import com.witaction.campusdefender.AppConfig
import com.witaction.campusdefender.R
import com.witaction.campusdefender.databinding.FragmentMyBinding
import com.witaction.campusdefender.event.EventBusMsg
import com.witaction.campusdefender.ui.ServiceLocalReponsitory
import com.witaction.campusdefender.ui.login.login.LoginActivity
import com.witaction.campusdefender.ui.main.my.account.AccountSetActivity
import com.witaction.campusdefender.ui.main.my.switchidentity.SwitchIdentityActivity
import com.witaction.common.base.BFragment
import com.witaction.common.extension.load
import com.witaction.common.extension.open
import com.witaction.campusdefender.ui.User
import com.witaction.common.utils.GlobalUtil
import com.witaction.common.widget.ConfirmDialog
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 个人中心
 */
class MyFragment : BFragment<FragmentMyBinding>() {

    private var user: User? = null

    override fun viewbinding(layoutInflater: LayoutInflater): FragmentMyBinding =
        FragmentMyBinding.inflate(layoutInflater)

    override fun initView() {
        EventBus.getDefault().register(this)
        GlobalUtil.setOnClickListener(
            vb.rlUserInfo,
            vb.tvSwitchIdentity,
            vb.tvAccountSet,
            vb.tvLoginOut
        ) {
            when (this) {
                vb.rlUserInfo -> {
                    open<AccountSetActivity>()
                }
                vb.tvSwitchIdentity -> {
                    open<SwitchIdentityActivity>()
                }
                vb.tvAccountSet -> {
                    open<AccountSetActivity>()
                }
                vb.tvLoginOut -> {
                    loginOut()
                }
            }
        }
    }

    override fun initData() {
        user = ServiceLocalReponsitory.getUser()
        user?.let {
            vb.tvSwitchIdentity.gone(it.type == AppConfig.PersonConfig.TEACHER)
            vb.tvIdentity.text = when (it.selectType) {
                AppConfig.PersonConfig.STUDENT -> GlobalUtil.getString(R.string.student)
                AppConfig.PersonConfig.TEACHER -> GlobalUtil.getString(R.string.teacher)
                else -> GlobalUtil.getString(R.string.parent)
            }
            vb.imgAvator.load(it.avatarUrl) {
                error(R.mipmap.icon_home_placeholder)
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
                confirmListener {
                    { it1 ->
                        it1.dismiss()
                        ServiceLocalReponsitory.loginOut()
                        open<LoginActivity>()
                        it.finish()
                    }
                }
                cancelListener {
                    { it1 ->
                        it1.dismiss()
                    }
                }
            }.show()
        }
    }

    /**
     * 用户信息改变
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun userInfoChanged(msg: EventBusMsg.UserInfoMsg) {
        msg.let {
            if (it.changed) {
                initData()
            }
        }
    }
}
