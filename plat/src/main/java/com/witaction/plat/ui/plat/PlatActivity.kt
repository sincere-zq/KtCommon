package com.witaction.plat.ui.plat

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.lifecycle.observe
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.permissionx.guolindev.PermissionX
import com.permissionx.guolindev.callback.RequestCallback
import com.witaction.common.base.AppManager
import com.witaction.common.base.BVMActivity
import com.witaction.common.extension.open
import com.witaction.common.utils.GlobalUtil
import com.witaction.common.utils.toast
import com.witaction.common.widget.DividerDecoration
import com.witaction.common.widget.HeaderView
import com.witaction.plat.City
import com.witaction.plat.PlatLocalReponsitory
import com.witaction.plat.R
import com.witaction.plat.databinding.ActivityPlatBinding
import com.witaction.plat.ui.city.CityListActivity

/**
 * 平台选择
 */
class PlatActivity : BVMActivity<ActivityPlatBinding, PlatViewModel>(), RequestCallback,
    OnItemClickListener, HeaderView.HeaderListener {

    companion object {
        private const val REQUEST_CODE = 0x110
        const val SELECTED_CITY = "SELECTED_CITY"
        //要跳转的登录页
        const val CLASS_ACTIVITY = "CLASS_ACTIVITY"
    }

    private lateinit var adapter: PlatAdapter

    override fun vmbinding(): Class<PlatViewModel> = PlatViewModel::class.java

    override fun viewbinding(): ActivityPlatBinding = ActivityPlatBinding.inflate(layoutInflater)

    override fun initView() {
        vb.headerView.setHeaderListener(this)
        adapter = PlatAdapter()
        vb.recyclerview.addItemDecoration(DividerDecoration(this))
        vb.recyclerview.adapter = adapter
        adapter.setOnItemClickListener(this)
    }

    override fun initData() {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .request(this)
        vm.platList.observe(this) {
            if (it.code == 0) {
                adapter.setList(it.datas)
            } else {
                toast(it.msg)
            }
        }
        vm.city.observe(this) {
            if (it == null) {
                vb.headerView.setSubTitle(GlobalUtil.getString(R.string.please_select_city))
            } else {
                vb.headerView.setSubTitle(it.cityName)
                vm.getPlatList(it.cityCode)
            }
        }
    }

    override fun onResult(
        allGranted: Boolean,
        grantedList: MutableList<String>?,
        deniedList: MutableList<String>?
    ) {
        if (allGranted) {
            realInitData()
        } else {
            toast(R.string.refused_permission)
            finish()
        }
    }

    private fun realInitData() {
        val city = PlatLocalReponsitory.getCity()
        vm.city.value = city
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val city = data.getSerializableExtra(SELECTED_CITY) as City
            city.let {
                vm.city.value = it
            }
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val plat = this.adapter.data.get(position)
        PlatLocalReponsitory.savePlat(plat)
        vm.city.value?.let {
            PlatLocalReponsitory.saveCity(it)
        }
        if (AppManager.getInstance().activityNumber() > 1) {
            setResult(Activity.RESULT_OK)
            onBackPressed()
        } else {
            val classT = intent.getSerializableExtra(CLASS_ACTIVITY) as Class<*>
            val intent = Intent(this, classT)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out)
            finish()
        }
    }

    override fun onRightSecondClick(view: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSubTitleClick(view: View?) {
        open<CityListActivity>(REQUEST_CODE)
    }

    override fun onLeftClick(view: View?) {
        onBackPressed()
    }

    override fun onRightClick(view: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRightTextClick(view: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
