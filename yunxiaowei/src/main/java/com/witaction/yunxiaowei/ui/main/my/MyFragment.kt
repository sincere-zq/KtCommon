package com.witaction.yunxiaowei.ui.main.my

import android.view.LayoutInflater
import androidx.lifecycle.observe
import com.witaction.common.base.BVMFragment
import com.witaction.common.extension.load
import com.witaction.common.extension.open
import com.witaction.common.utils.GlobalUtil
import com.witaction.common.widget.ConfirmDialog
import com.witaction.yunxiaowei.R
import com.witaction.yunxiaowei.databinding.FragmentMyBinding
import com.witaction.yunxiaowei.framwork.AppConfig
import com.witaction.yunxiaowei.framwork.LocalRepository
import com.witaction.yunxiaowei.ui.login.LoginActivity

/**
 * 我的
 */
class MyFragment : BVMFragment<FragmentMyBinding, MyViewModel>() {
    override fun vmbinding(): Class<MyViewModel> = MyViewModel::class.java

    override fun viewbinding(layoutInflater: LayoutInflater): FragmentMyBinding =
        FragmentMyBinding.inflate(layoutInflater)

    override fun initView() {
        GlobalUtil.setOnClickListener(vb.rlUserInfo, vb.tvSwitchIdentity, vb.tvLoginOut) {
            when (this) {
                vb.rlUserInfo -> {
                }
                vb.tvSwitchIdentity -> {
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
            vb.tvIdentity.text = when (it.userType) {
                AppConfig.STUDENT -> GlobalUtil.getString(R.string.student)
                AppConfig.TEACHER -> GlobalUtil.getString(R.string.teacher)
                else -> GlobalUtil.getString(R.string.parent)
            }
        }
    }

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

}
