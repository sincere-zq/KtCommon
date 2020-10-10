package com.witaction.yunxiaowei.ui.main.home.classaffairs

import android.os.Bundle
import android.view.View
import com.eyepetizer.android.extension.gone
import com.witaction.common.base.BActivity
import com.witaction.common.extension.open
import com.witaction.common.utils.GlobalUtil
import com.witaction.yunxiaowei.ClassBean
import com.witaction.yunxiaowei.databinding.ActivityClassAffairBinding
import com.witaction.yunxiaowei.ui.main.home.classaffairs.classtimetable.ClassTimetableActivity
import com.witaction.yunxiaowei.ui.main.home.classaffairs.rouster.StudentRosterActivity
import com.witaction.yunxiaowei.ui.main.home.classaffairs.studentleave.StudentLeaveRecordActivity
import com.witaction.yunxiaowei.ui.main.home.classlist.ClassListActivity

class ClassAffairActivity : BActivity<ActivityClassAffairBinding>() {
    override fun viewbinding(): ActivityClassAffairBinding =
        ActivityClassAffairBinding.inflate(layoutInflater)

    override fun initView() {
        val classInfo = intent.getSerializableExtra(ClassListActivity.CLASS_INFO) as ClassBean
        classInfo.run {
            vb.headerView.setTitle(name)
            vb.tvStudentLeave.gone(isClassTeacher)
            vb.tvTempManage.gone(isClassTeacher)
            vb.tvSelectCourseManage.gone(isClassTeacher)
            vb.tvSelectCourseStatistic.gone(isClassTeacher)
        }
        vb.headerView.setHeaderListener(this)
        GlobalUtil.setOnClickListener(
            vb.tvStuRoster,
            vb.tvClassTimetable,
            vb.tvStudentLeave,
            vb.tvStudentLeaveRecord,
            vb.tvTempManage,
            vb.tvSelectCourseManage,
            vb.tvSelectCourseStatistic,
            vb.tvInvitationParent,
            vb.tvOpenParentMeeting
        ) {
            jump(this, classInfo)
        }
    }

    override fun initData() {

    }

    private fun jump(view: View, classInfo: ClassBean) {
        when (view) {
            vb.tvStuRoster -> {
                val bundle = Bundle()
                bundle.putSerializable(ClassListActivity.CLASS_INFO, classInfo)
                open<StudentRosterActivity>(bundle)
            }
            vb.tvClassTimetable -> {
                val bundle = Bundle()
                bundle.putInt(ClassTimetableActivity.FROM_TYPE, ClassTimetableActivity.TYPE_CLASS)
                bundle.putString(ClassTimetableActivity.ID, classInfo.id)
                open<ClassTimetableActivity>(bundle)
            }
            vb.tvStudentLeave -> {
            }
            vb.tvStudentLeaveRecord -> {
                val bundle = Bundle()
                bundle.putString(StudentLeaveRecordActivity.CLASS_ID, classInfo.id)
                open<StudentLeaveRecordActivity>(bundle)
            }
            vb.tvTempManage -> {
            }
            vb.tvSelectCourseManage -> {
            }
            vb.tvSelectCourseStatistic -> {
            }
            vb.tvInvitationParent -> {
            }
            vb.tvOpenParentMeeting -> {
            }
        }
    }

}
