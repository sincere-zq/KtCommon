package com.witaction.campusdefender.ui.main.my.switchidentity

import android.view.View
import com.witaction.campusdefender.AppConfig
import com.witaction.campusdefender.databinding.ActivitySwitchIdentityBinding
import com.witaction.campusdefender.ui.ServiceLocalReponsitory
import com.witaction.campusdefender.ui.main.MainActivity
import com.witaction.common.base.AppManager
import com.witaction.common.base.BActivity
import com.witaction.common.extension.open
import com.witaction.common.widget.HeaderView

/**
 * 切换身份
 */
class SwitchIdentityActivity : BActivity<ActivitySwitchIdentityBinding>(),
    HeaderView.HeaderListener {

    private val user by lazy { ServiceLocalReponsitory.getUser() }

    override fun viewbinding(): ActivitySwitchIdentityBinding =
        ActivitySwitchIdentityBinding.inflate(layoutInflater)

    override fun initView() {
        vb.headerView.setHeaderListener(this)
        user?.let {
            when (it.selectType) {
                AppConfig.PersonConfig.PARENT -> vb.rbParent.isChecked = true
                AppConfig.PersonConfig.TEACHER -> vb.rbTeacher.isChecked = true
            }
        }
    }

    override fun initData() {
    }

    override fun onRightSecondClick(view: View?) {
    }

    override fun onSubTitleClick(view: View?) {
    }

    override fun onLeftClick(view: View?) {
        onBackPressed()
    }

    override fun onRightClick(view: View?) {
    }

    override fun onRightTextClick(view: View?) {
        val selectedType =
            if (vb.rbParent.isChecked) AppConfig.PersonConfig.PARENT else AppConfig.PersonConfig.TEACHER
        user?.let {
            if (it.selectType != selectedType) {
                it.selectType = selectedType
                ServiceLocalReponsitory.saveUser(it)
                AppManager.getInstance().killOthersActivity(this)
                open<MainActivity>()
                finish()
            } else {
                onBackPressed()
            }
        }
    }
}
