package com.witaction.yunxiaowei.ui.main.home.classaffairs.studentleave

import com.witaction.common.base.BActivity
import com.witaction.yunxiaowei.databinding.ActivityStudentLeaveRecordBinding

class StudentLeaveRecordActivity : BActivity<ActivityStudentLeaveRecordBinding>() {
    companion object {
        const val CLASS_ID = "CLASS_ID"
    }

    override fun viewbinding(): ActivityStudentLeaveRecordBinding = ActivityStudentLeaveRecordBinding.inflate(layoutInflater)

    override fun initView() {
        vb.viewPager.adapter =
            MyFragmentPagerAdapter(supportFragmentManager, listOf(WaitExamineLeaveFragment(), WaitRevokeLeaveFragment()), listOf("待审核", "待销假"))
        vb.tabLayout.setupWithViewPager(vb.viewPager)
    }

    override fun initData() {
    }


}

