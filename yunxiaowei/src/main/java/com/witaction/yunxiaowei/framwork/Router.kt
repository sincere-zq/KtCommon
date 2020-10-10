package com.witaction.yunxiaowei.framwork

import android.app.Activity
import android.os.Bundle
import com.witaction.common.extension.open
import com.witaction.yunxiaowei.FunctionMenu
import com.witaction.yunxiaowei.ui.main.home.classaffairs.ClassAffairActivity
import com.witaction.yunxiaowei.ui.main.home.classlist.ClassListActivity

object Router {
    fun open(activity: Activity, funcMenu: FunctionMenu) {
        when (funcMenu.sn) {
            FuncMenuConfig.TeacherMenu.YUN_CT_BUSINESS -> {
                //班级事务
                val bundle = Bundle()
                bundle.putInt(ClassListActivity.AUTH_FILTER, 1)
                bundle.putSerializable(
                    ClassListActivity.CLASS_ACTIVITY,
                    ClassAffairActivity::class.java
                )
                activity.open<ClassListActivity>(bundle)
            }
            FuncMenuConfig.TeacherMenu.YUN_CT_TEACH_EYE -> {
                //教学之眼
            }
            FuncMenuConfig.TeacherMenu.YUN_CT_ACTIVITY_ORDER -> {
                //活动预约
            }
            FuncMenuConfig.TeacherMenu.YUN_SB_APARTMENT -> {
                //公寓管理
            }
            FuncMenuConfig.TeacherMenu.YUN_CT_APARTMENT -> {
                //学生公寓
            }
            FuncMenuConfig.TeacherMenu.YUN_SB_BACKDORM_STA -> {
                //归寝态势
            }
            FuncMenuConfig.TeacherMenu.YUN_CT_PRINCIPAL_EYE -> {
                //校长之眼
            }
            FuncMenuConfig.TeacherMenu.YUN_CT_TEMPERATURE -> {
                //体温管理
            }
            FuncMenuConfig.TeacherMenu.YUN_CT_GRADE -> {
                //成绩管理
            }
            FuncMenuConfig.TeacherMenu.MON_SITE_QR_BZF -> {
                //门禁扫码
            }
            FuncMenuConfig.TeacherMenu.COME_APPOINT_BZF -> {
                //访客预约
            }


            FuncMenuConfig.ParentMenu.YUN_PA_CHILD_MANAGER -> {
                //孩子管理
            }
            FuncMenuConfig.ParentMenu.YUN_PA_CHILD_COURSE_SELECT -> {
                //校本选课
            }
            FuncMenuConfig.ParentMenu.YUN_PA_FACE_REGISTER -> {
                //人脸采集
            }
            FuncMenuConfig.ParentMenu.YUN_PA_CHILD_RESULT -> {
                //孩子成绩
            }
            FuncMenuConfig.ParentMenu.YUN_PA_PAY -> {
                //缴费
            }
        }
    }
}