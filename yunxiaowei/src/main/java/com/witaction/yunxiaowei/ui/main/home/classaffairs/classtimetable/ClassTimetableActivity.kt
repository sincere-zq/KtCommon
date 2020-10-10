package com.witaction.yunxiaowei.ui.main.home.classaffairs.classtimetable

import androidx.lifecycle.observe
import com.witaction.common.base.BVMActivity
import com.witaction.common.widget.DividerDecoration
import com.witaction.yunxiaowei.databinding.ActivityClassTimetableBinding

/**
 * 班级课表
 */
class ClassTimetableActivity : BVMActivity<ActivityClassTimetableBinding, ClassTimetableViewModel>() {
    companion object {
        //从家长端过来的
        const val FROM_TYPE = "FROM_TYPE"
        //ID
        const val ID = "ID"

        const val TYPE_CLASS = 0x110
        const val TYPE_CHILD = 0x111
    }

    private val adapter by lazy { ClassTimetableAdapter() }

    override fun vmbinding(): Class<ClassTimetableViewModel> = ClassTimetableViewModel::class.java

    override fun viewbinding(): ActivityClassTimetableBinding = ActivityClassTimetableBinding.inflate(layoutInflater)

    override fun initView() {
        vb.headerView.setHeaderListener(this)
        vb.recyclerview.addItemDecoration(DividerDecoration(this))
        vb.recyclerview.adapter = adapter
    }

    override fun initData() {
        vm.fromType = intent.getIntExtra(FROM_TYPE, TYPE_CLASS)
        vm.id.value = intent.getStringExtra(ID)

        vm.timetableResult.observe(this) {
            if (it.isSuccess()) {
                if (it.data.isEmpty() || it.data[0].courseList.isEmpty()) {
                    adapter.setEmptyView(getEmptyView { vm.getClassTimetable() })
                } else {
                    vb.headerView.setTitle(it.data[0].className + it.data[0].semeter + "-课表")
                    vm.generateDatas(it.data[0])
                }
            } else {
                adapter.setEmptyView(getNetErrorView(it.msg) { vm.getClassTimetable() })
            }
        }
        vm.timetableDatas.observe(this) {
            adapter.setList(it)
        }

        adapter.setEmptyView(getLoadingView())
        vm.getClassTimetable()
    }
}
