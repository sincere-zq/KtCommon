package com.witaction.yunxiaowei.ui.main.home.classaffairs.rouster

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.observe
import com.witaction.common.base.BVMActivity
import com.witaction.common.utils.GlobalUtil
import com.witaction.common.widget.DividerDecoration
import com.witaction.yunxiaowei.ClassBean
import com.witaction.yunxiaowei.R
import com.witaction.yunxiaowei.StudentRoster
import com.witaction.yunxiaowei.databinding.ActivityStudentRosterBinding
import com.witaction.yunxiaowei.ui.main.home.classlist.ClassListActivity

/**
 * 学生花名册
 */
class StudentRosterActivity : BVMActivity<ActivityStudentRosterBinding, StudentRosterViewModel>(), SearchView.OnQueryTextListener {

    private lateinit var adapter: StudentRosterAdapter

    override fun vmbinding(): Class<StudentRosterViewModel> = StudentRosterViewModel::class.java

    override fun viewbinding(): ActivityStudentRosterBinding =
        ActivityStudentRosterBinding.inflate(layoutInflater)

    override fun initView() {
        val classInfo = intent.getSerializableExtra(ClassListActivity.CLASS_INFO) as ClassBean
        vm.classInfo.value = classInfo
        adapter = StudentRosterAdapter(classInfo)
        vb.headerView.setHeaderListener(this)
        vb.recyclerview.addItemDecoration(DividerDecoration(this))
        vb.recyclerview.adapter = adapter
        vb.searchView.setOnQueryTextListener(this)
    }

    override fun initData() {
        vm.classInfo.observe(this) {
            vb.headerView.setTitle(it.name + "-${GlobalUtil.getString(R.string.student_roster)}")
        }
        vm.studentRoster.observe(this) {
            if (it.isSuccess()) {
                adapter.setList(it.data)
                if (it.data.isEmpty()) {
                    adapter.setEmptyView(getEmptyView { vm.getStudentRoster() })
                }
            } else {
                adapter.setEmptyView(getNetErrorView(it.msg) { vm.getStudentRoster() })
            }
        }
        adapter.setEmptyView(getLoadingView())
        vm.getStudentRoster()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.run {
            vm.studentRoster.value?.run {
                val result = mutableListOf<StudentRoster>()
                for (roster in data) {
                    if (roster.name.contains(newText)) {
                        result.add(roster)
                    }
                }
                if (result.size == 0) {
                    adapter.setEmptyView(getEmptyView { })
                }
                adapter.setList(result)
            }
        }
        return true
    }

    override fun onRestart() {
        super.onRestart()
        vm.getStudentRoster()
    }
}
