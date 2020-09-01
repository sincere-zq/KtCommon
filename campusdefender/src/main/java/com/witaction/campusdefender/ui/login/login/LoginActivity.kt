package com.witaction.campusdefender.ui.login.login

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.lifecycle.observe
import com.eyepetizer.android.extension.gone
import com.eyepetizer.android.extension.visible
import com.witaction.campusdefender.R
import com.witaction.campusdefender.databinding.ActivityLoginBinding
import com.witaction.campusdefender.ui.LocalReponsitory
import com.witaction.campusdefender.ui.ServiceReponsitory
import com.witaction.campusdefender.ui.login.plat.PlatActivity
import com.witaction.campusdefender.ui.main.MainActivity
import com.witaction.common.base.BVMActivity
import com.witaction.common.extension.afterTextChanged
import com.witaction.common.extension.open
import com.witaction.common.utils.CountDownUtil
import com.witaction.common.utils.GlobalUtil
import com.witaction.common.utils.toast
import com.witaction.common.widget.HeaderView

/**
 * 登录页
 */
class LoginActivity : BVMActivity<ActivityLoginBinding, LoginViewModel>(),
    HeaderView.HeaderListener {
    companion object {
        const val REQUEST_CODE = 0x110
        const val LOGIN_BY_ACCOUNT = 0
        const val LOGIN_BY_CODE = 1
    }

    //倒计时
    private var countDownUtil: CountDownUtil? = null

    override fun vmbinding(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun viewbinding(): ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)

    override fun initView() {
        vb.headerView.setHeaderListener(this)
        GlobalUtil.setOnClickListener(
            vb.tvUserAgreement,
            vb.tvGetCode,
            vb.btnLogin
        ) {
            when (this) {
                vb.tvUserAgreement -> {
                }
                vb.tvGetCode -> {
                    countDown()
                }
                vb.btnLogin -> {
                    login()
                }
            }
        }
        vb.etAccount.afterTextChanged {
            vb.btnLogin.isEnabled = it.isNotEmpty() && vb.etPwd.text.toString().isNotEmpty()
        }
        vb.etPwd.afterTextChanged {
            vb.btnLogin.isEnabled = it.isNotEmpty() && vb.etAccount.text.toString().isNotEmpty()
        }

        vb.etPhone.afterTextChanged {
            vb.btnLogin.isEnabled = it.isNotEmpty() && vb.etCode.text.toString().isNotEmpty()
            if (vb.tvGetCode.isEnabled) {
                vb.tvGetCode.gone(it.isNotEmpty() && it.length == 11)
            }
        }
        vb.etCode.afterTextChanged {
            vb.btnLogin.isEnabled = it.isNotEmpty() && vb.etPhone.text.toString().isNotEmpty()
        }
    }

    override fun initData() {
        vm.plat.observe(this) {
            it.let {
                vb.headerView.setSubTitle(it.name)
                if (it.modIPAddr.endsWith("/")) {
                    ServiceReponsitory.initApi("http://${it.modIPAddr}")
                } else {
                    ServiceReponsitory.initApi("http://${it.modIPAddr}/")
                }
            }
        }
        vm.loginType.observe(this) {
            it?.let {
                when (it) {
                    LOGIN_BY_ACCOUNT -> {
                        vb.headerView.setRightText(GlobalUtil.getString(R.string.login_by_code))
                        vb.llLoginByAccount.visible()
                        vb.llLoginByCode.gone()
                    }
                    LOGIN_BY_CODE -> {
                        vb.headerView.setRightText(GlobalUtil.getString(R.string.login_by_account))
                        vb.llLoginByAccount.gone()
                        vb.llLoginByCode.visible()
                    }
                }
                vb.etAccount.setText("")
                vb.etPwd.setText("")
                vb.etPhone.setText("")
                vb.etCode.setText("")
                countDownUtil?.cancel()
                vb.tvGetCode.setTextColor(GlobalUtil.getColor(R.color.green))
                vb.tvGetCode.isEnabled = true
                vb.tvGetCode.text = GlobalUtil.getString(R.string.get_code)
            }
        }
        vm.phoneValidateResult.observe(this) {
            hideLoading()
            if (it.isSuccess()) {
                toast(R.string.send_success)
            } else {
                toast(it.msg)
            }
        }
        vm.user.observe(this) {
            hideLoading()
            if (it.isSuccess()) {
                val user = it.getSimpleData()
                user?.let { it1 ->
                    it1.schoolId = LocalReponsitory.getPlat()?.sysID.toString()
                    LocalReponsitory.saveUser(it1)
                    open<MainActivity>()
                    finish()
                }
            } else {
                toast(it.msg)
            }
        }
        vm.plat.value = LocalReponsitory.getPlat()
    }

    /**
     * 登录入口
     */
    private fun login() {
        when (vm.loginType.value) {
            LOGIN_BY_ACCOUNT -> loginByAccount()
            LOGIN_BY_CODE -> loginByCode()
        }
    }

    /**
     * 账号密码登录
     */
    private fun loginByAccount() {
        if (vb.etAccount.text.isNullOrEmpty()) {
            toast(R.string.please_input_account)
            return
        }
        if (vb.etPwd.text.isNullOrEmpty()) {
            toast(R.string.please_input_password)
            return
        }
        showLoading()
        vm.loginByAccount(vb.etAccount.text.toString(), vb.etPwd.text.toString())
    }

    /**
     * 验证码登录
     */
    private fun loginByCode() {
        if (vb.etPhone.text.isNullOrEmpty()) {
            toast(R.string.please_input_phone)
            return
        }
        if (vb.etCode.text.isNullOrEmpty()) {
            toast(R.string.please_input_code)
            return
        }
        showLoading()
        vm.loginByCode(vb.etPhone.text.toString(), vb.etCode.text.toString())
    }

    /**
     * 发送验证码
     */
    private fun sendCode() {
        if (vb.etPhone.text.isNullOrEmpty()) {
            toast(R.string.please_input_phone)
            return
        }
        showLoading()
        vm.accountPhoneValidate(vb.etPhone.text.toString())
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
        sendCode()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            vm.plat.value = LocalReponsitory.getPlat()
        }
    }

    override fun onRightSecondClick(view: View?) {
    }

    override fun onSubTitleClick(view: View?) {
        open<PlatActivity>(REQUEST_CODE)
    }

    override fun onLeftClick(view: View?) {
        onBackPressed()
    }

    override fun onRightClick(view: View?) {
    }

    override fun onRightTextClick(view: View?) {
        when (vm.loginType.value) {
            LOGIN_BY_CODE -> vm.loginType.value = LOGIN_BY_ACCOUNT
            LOGIN_BY_ACCOUNT -> vm.loginType.value = LOGIN_BY_CODE
        }
    }
}
