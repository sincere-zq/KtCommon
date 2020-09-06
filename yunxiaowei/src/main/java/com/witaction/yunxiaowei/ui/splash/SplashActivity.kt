package com.witaction.yunxiaowei.ui.splash

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.witaction.common.base.BSplashActivity
import com.witaction.common.extension.open
import com.witaction.plat.ui.plat.PlatActivity
import com.witaction.yunxiaowei.databinding.ActivitySplashBinding
import com.witaction.yunxiaowei.framwork.AppConfig
import com.witaction.yunxiaowei.framwork.ServerRepository
import com.witaction.yunxiaowei.ui.login.LoginActivity
import com.witaction.yunxiaowei.ui.main.MainActivity

/**
 * 欢迎页
 */
class SplashActivity : BSplashActivity<ActivitySplashBinding>() {
    private val vm: SplashModel by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(
            application
        ).create(SplashModel::class.java)
    }

    override fun viewbinding(): ActivitySplashBinding =
        ActivitySplashBinding.inflate(layoutInflater)

    override fun initView() {

    }

    override fun initData() {
        val plat = vm.plat
        if (plat == null) {
            val bundle = Bundle()
            bundle.putSerializable(PlatActivity.CLASS_ACTIVITY, LoginActivity::class.java)
            open<PlatActivity>(bundle)
            finish()
        } else {
            if (plat.modIPAddr.endsWith("/")) {
                ServerRepository.initApi("${AppConfig.APP_SCHEME}${plat.modIPAddr}")
            } else {
                ServerRepository.initApi("${AppConfig.APP_SCHEME}${plat.modIPAddr}/")
            }
            if (vm.localLoginResult == null) {
                open<LoginActivity>()
                finish()
            } else {
                vm.refreshToken()
            }
        }
        vm.loginResult.observe(this) {
            if (it.isSuccess()) {
                it.getSimpleData()?.let {
                    open<MainActivity>()
                    finish()
                }
            } else {
                open<LoginActivity>()
                finish()
            }
        }
    }
}