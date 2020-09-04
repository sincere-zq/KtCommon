package com.witaction.campusdefender.ui.main.my.account.updatename

import android.app.Activity
import android.view.View
import androidx.lifecycle.observe
import com.witaction.campusdefender.R
import com.witaction.campusdefender.databinding.ActivityUpdateNameBinding
import com.witaction.campusdefender.ui.ServiceLocalReponsitory
import com.witaction.common.base.BVMActivity
import com.witaction.common.utils.toast
import com.witaction.common.widget.HeaderView

/**
 * 修改名称
 */
class UpdateNameActivity : BVMActivity<ActivityUpdateNameBinding, UpdateNameViewModel>(),
    HeaderView.HeaderListener {
    override fun vmbinding(): Class<UpdateNameViewModel> = UpdateNameViewModel::class.java

    override fun viewbinding(): ActivityUpdateNameBinding =
        ActivityUpdateNameBinding.inflate(layoutInflater)

    override fun initView() {
        vb.headerView.setHeaderListener(this)
    }

    override fun initData() {
        vm.user.observe(this) {
            it?.let { it1 ->
                vb.etName.setText(it1.name)
            }
        }
        vm.updateNameResult.observe(this) {
            hideLoading()
            if (it.isSuccess()) {
                toast(R.string.update_success)
                vm.user.value?.let { it1 ->
                    it1.name = vb.etName.text.toString()
                    ServiceLocalReponsitory.saveUser(it1)
                    setResult(Activity.RESULT_OK)
                    onBackPressed()
                }
            } else {
                toast(it.msg)
            }
        }
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
        if (vb.etName.text.isNullOrEmpty()) {
            toast(R.string.please_input_real_name)
            return
        }
        showLoading()
        vm.updateName(vb.etName.text.toString())
    }

}
