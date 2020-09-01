package com.witaction.campusdefender.ui.login.splash

import android.os.Bundle
import com.witaction.campusdefender.R
import com.witaction.campusdefender.databinding.ActivitySplashBinding
import com.witaction.campusdefender.ui.LocalReponsitory
import com.witaction.campusdefender.ui.ServiceReponsitory
import com.witaction.campusdefender.ui.login.login.LoginActivity
import com.witaction.campusdefender.ui.login.plat.PlatActivity
import com.witaction.campusdefender.ui.main.MainActivity
import com.witaction.common.base.BSplashActivity
import com.witaction.common.extension.open
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 欢迎页
 */
class SplashActivity : BSplashActivity<ActivitySplashBinding>() {

    private val splashJob by lazy { Job() }
    private val splashJobTime = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun viewbinding(): ActivitySplashBinding =
        ActivitySplashBinding.inflate(layoutInflater)

    override fun initView() {
        CoroutineScope(splashJob).launch {
            delay(splashJobTime)
            val plat = LocalReponsitory.getPlat()
            if (plat == null) {
                open<PlatActivity>()
            } else {
                val user = LocalReponsitory.getUser()
                if (user == null) {
                    open<LoginActivity>()
                } else {
                    if (plat.modIPAddr.endsWith("/")) {
                        ServiceReponsitory.initApi("http://${plat.modIPAddr}")
                    } else {
                        ServiceReponsitory.initApi("http://${plat.modIPAddr}/")
                    }
                    open<MainActivity>()
                }
            }
            finish()
        }
    }

    override fun initData() {

    }

    override fun onDestroy() {
        super.onDestroy()
        splashJob.cancel()
    }
}