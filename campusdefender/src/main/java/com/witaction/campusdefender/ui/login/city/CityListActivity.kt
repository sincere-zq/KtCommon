package com.witaction.campusdefender.ui.login.city

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.lifecycle.observe
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.witaction.campusdefender.databinding.ActivityCityListBinding
import com.witaction.campusdefender.ui.login.plat.PlatActivity
import com.witaction.common.base.BVMActivity
import com.witaction.common.utils.toast
import com.witaction.common.widget.DividerDecoration
import com.witaction.common.widget.HeaderView

/**
 * 城市列表
 */
class CityListActivity : BVMActivity<ActivityCityListBinding, CityViewModel>(),
    OnItemClickListener, HeaderView.HeaderListener {

    private lateinit var adapter: CityAdapter

    override fun vmbinding(): Class<CityViewModel> = CityViewModel::class.java

    override fun viewbinding(): ActivityCityListBinding =
        ActivityCityListBinding.inflate(layoutInflater)

    override fun initView() {
        vb.headerView.setHeaderListener(this)

        adapter = CityAdapter()
        vb.recyclerview.addItemDecoration(DividerDecoration(this))
        vb.recyclerview.adapter = adapter

        adapter.setOnItemClickListener(this)
    }

    override fun initData() {
        vm.cityList.observe(this) {
            if (it.code == 0) {
                adapter.setList(it.datas)
            } else {
                toast(it.msg)
            }
        }
        vm.getCityList()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val city = this.adapter.data.get(position)
        val intent = Intent()
        intent.putExtra(PlatActivity.SELECTED_CITY, city)
        setResult(Activity.RESULT_OK, intent)
        onBackPressed()
    }

    override fun onRightSecondClick(view: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSubTitleClick(view: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
