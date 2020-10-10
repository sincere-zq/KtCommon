package com.witaction.yunxiaowei.ui.main.my.accountset.resetpwd

import android.text.TextUtils
import androidx.lifecycle.observe
import com.witaction.common.base.BVMActivity
import com.witaction.common.extension.afterTextChanged
import com.witaction.common.utils.CountDownUtil
import com.witaction.common.utils.GlobalUtil
import com.witaction.common.utils.toast
import com.witaction.common.widget.ConfirmDialog
import com.witaction.yunxiaowei.R
import com.witaction.yunxiaowei.databinding.ActivityResetPwdBinding

/**
 * 初始化密码
 */
class ResetPwdActivity : BVMActivity<ActivityResetPwdBinding, ResetPwdViewModel>() {
    private var countDownUtil: CountDownUtil? = null

    override fun vmbinding(): Class<ResetPwdViewModel> = ResetPwdViewModel::class.java

    override fun viewbinding(): ActivityResetPwdBinding =
        ActivityResetPwdBinding.inflate(layoutInflater)

    override fun initView() {
        vb.headerView.setHeaderListener(this)
        vb.btnCommit.setOnClickListener { confirmCommit() }
        vb.tvGetCode.setOnClickListener { countDown() }
        vb.etPwd.afterTextChanged {
            vb.btnCommit.isEnabled =
                it.isNotEmpty() && !vb.etConfirmPwd.text.isNullOrEmpty() && !vb.etCode.text.isNullOrEmpty()
        }
        vb.etConfirmPwd.afterTextChanged {
            vb.btnCommit.isEnabled =
                it.isNotEmpty() && !vb.etPwd.text.isNullOrEmpty() && !vb.etCode.text.isNullOrEmpty()
        }
        vb.etCode.afterTextChanged {
            vb.btnCommit.isEnabled =
                it.isNotEmpty() && !vb.etPwd.text.isNullOrEmpty() && !vb.etConfirmPwd.text.isNullOrEmpty()
        }
    }

    override fun initData() {
        vm.sendCodeResult.observe(this) {
            hideLoading()
            if (it.isSuccess()) {
                toast(R.string.send_success)
            } else {
                toast(it.msg)
                countDownUtil?.cancel()
                vb.tvGetCode.setTextColor(GlobalUtil.getColor(R.color.green))
                vb.tvGetCode.isEnabled = true
                vb.tvGetCode.text = GlobalUtil.getString(R.string.get_code)
            }
        }
        vm.resetPwdResult.observe(this) {
            hideLoading()
            if (it.isSuccess()) {
                toast(R.string.reset_pwd_success)
                onBackPressed()
            } else {
                toast(it.msg)
            }
        }
    }

    private fun confirmCommit() {
        if (!TextUtils.equals(vb.etPwd.text.toString(), vb.etConfirmPwd.text.toString())) {
            toast(R.string.two_passwords_are_inconsistent)
            return
        }
        ConfirmDialog.build(this) {
            message { GlobalUtil.getString(R.string.confirm_commit) }
            cancelListener { { it.dismiss() } }
            confirmListener {
                {
                    it.dismiss()
                    showLoading()
                    vm.resetPwd(vb.etConfirmPwd.text.toString(), vb.etCode.text.toString())
                }
            }
        }.show()
    }

    /**
     * 开始倒计时
     */
    private fun countDown() {
        vb.tvGetCode.setTextColor(GlobalUtil.getColor(R.color.rgb_e0e0e0))
        vb.tvGetCode.isEnabled = false
        if (countDownUtil == null) {
            countDownUtil = CountDownUtil.getCountDownTimer()
                // 倒计时总时间
                .setMillisInFuture(60_000)
                // 每隔多久回调一次onTick
                .setCountDownInterval(1000)
                // 每回调一次onTick执行
                .setTickDelegate {
                    vb.tvGetCode.text = "重新获取 $it S"
                }.setFinishDelegate {
                    vb.tvGetCode.setTextColor(GlobalUtil.getColor(R.color.green))
                    vb.tvGetCode.isEnabled = true
                    vb.tvGetCode.text = GlobalUtil.getString(R.string.get_code)
                }
        }
        countDownUtil?.start()
        showLoading()
        vm.sendCode()
    }

    override fun onStop() {
        super.onStop()
        countDownUtil?.let {
            it.cancel()
            vb.tvGetCode.setTextColor(GlobalUtil.getColor(R.color.green))
            vb.tvGetCode.isEnabled = true
            vb.tvGetCode.text = GlobalUtil.getString(R.string.get_code)
        }
    }
}
