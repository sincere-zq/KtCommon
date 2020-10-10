package com.witaction.yunxiaowei.framwork;

/**
 * 功能菜单
 */
public interface FuncMenuConfig {

    interface TeacherMenu {
        //班级事务
        String YUN_CT_BUSINESS = "YUN_CT_BUSINESS";
        //教学之眼
        String YUN_CT_TEACH_EYE = "YUN_CT_TEACH_EYE";
        //活动预约
        String YUN_CT_ACTIVITY_ORDER = "YUN_CT_ACTIVITY_ORDER";
        //公寓管理
        String YUN_SB_APARTMENT = "YUN_SB_APARTMENT";
        //学生公寓
        String YUN_CT_APARTMENT = "YUN_CT_APARTMENT";
        //归寝态势
        String YUN_SB_BACKDORM_STA = "YUN_SB_BACKDORM_STA";
        //校长之眼
        String YUN_CT_PRINCIPAL_EYE = "YUN_CT_PRINCIPAL_EYE";
        //体温管理
        String YUN_CT_TEMPERATURE = "YUN_CT_TEMPERATURE";
        //成绩管理
        String YUN_CT_GRADE = "YUN_CT_GRADE";
        //门禁扫码
        String MON_SITE_QR_BZF = "monsiteqr_bzf";
        //访客预约
        String COME_APPOINT_BZF = "comeappoint_bzf";
    }

    interface ParentMenu {
        //孩子管理
        String YUN_PA_CHILD_MANAGER = "YUN_PA_CHILD_MANAGER";
        //校本选课
        String YUN_PA_CHILD_COURSE_SELECT = "YUN_PA_CHILD_COURSE_SELECT";
        //人脸采集
        String YUN_PA_FACE_REGISTER = "YUN_PA_FACE_REGISTER";
        //孩子成绩
        String YUN_PA_CHILD_RESULT = "YUN_PA_CHILD_RESULT";
        //缴费
        String YUN_PA_PAY = "YUN_PA_PAY";
    }

}
