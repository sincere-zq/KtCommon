package com.witaction.campusdefender

interface AppConfig {
    companion object {
        /**
         * DES加密key值
         */
        const val DES_KEY = "dbke6413"
        /**
         * DES加密偏移量
         */
        const val DES_IV = "64133146"
        val HOME_TITLES = arrayOf("首页", "消息", "我的")
        val HOME_ICONS = intArrayOf(
            R.drawable.selector_home_selector,
            R.drawable.selector_message_selector,
            R.drawable.selector_my_selector
        )
        //首页菜单一行个数
        const val HOME_MENU_NUM = 4
        //首页事务提醒条数
        const val HOME_TRANS_NUM = 5
        //人员头像大小
        const val HEAD_AVATOR = 300
        //人脸高度
        const val FACE_IMAGE_HEIGHT = 320
        //人脸宽度
        const val FACE_IMAGE_WIDTH = 240
    }

    interface PersonConfig {
        companion object {
            const val STUDENT = 1
            const val TEACHER = 2
            const val PARENT = 3
        }
    }

    interface LoadingConfig {
        companion object {
            const val LOADING = 0
            const val NET_ERROR = 1
            const val EMPTY_DATA = 2
            const val COMPLETE = 3
        }
    }

    interface MessageConfig {
        companion object {
            /**
             * 教师端
             */
            //学校通知公告
            const val TEACHER_SCHOOL_NOTICE = 2010
            //教师审批
            const val TEACHER_APPROVAL = 2020
            //学生请假
            const val TEACHER_STUDENT_LEAVE = 2030
            //校车接送任务
            const val TEACHER_SCHOOL_BUS = 2040
            // 家长出入审批
            const val TEACHER_PARENT_OUT_IN = 2050
            //家长会教师通知
            const val TEACHER_PARENT_MEETING = 2060
            //oa 公文办理
            const val TEACHER_OA_OFFICIALDOC = 2500
            //oa 传阅件
            const val TEACHER_OA_PASSANDREADDOC = 2501
            //oa 公告通知
            const val TEACHER_OA_INFORMNOTICE = 2504
            //oa 规章制度
            const val TEACHER_OA_RULE = 2506
            //oa 个人日程
            const val TEACHER_OA_SCHEDULE = 2507
            //oa 待办审批
            const val TEACHER_OA_APPROVAL = 2508
            //oa 公文档案
            const val TEACHER_OA_OFFICIALDOCARCHIVE = 2509
            //oa 审批档案
            const val TEACHER_OA_APPROVALARCHIVE = 2510
            //oa 阅处件
            const val TEACHER_OA_READANDDEAL = 2511
            //oa 领导日程
            const val TEACHER_OA_LEADER_SCHEDULE = 2514
            //oa 在办事项（拟办备忘））
            const val TEACHER_OA_WORKMEMO = 2519
            //oa 站内信
            const val TEACHER_OA_LETTER = 2520
            //oa 会议
            const val TEACHER_OA_MEETING = 2522

            /**
             * 家长端：
             */
            //班级通知公告
            const val PARENT_CLASS_NOTICE = 3010
            //孩子作业
            const val PARENT_HOME_WORK = 3020
            //校门出入
            const val PARENT_SCHOOL_IN_OUT = 3030
            //公寓出入
            const val PARENT_APARTMENT_IN_OUT = 3040
            //孩子请假
            const val PARENT_CHILD_LEAVE = 3050
            //校车接送
            const val PARENT_SCHOOL_BUS = 3060
            //选课
            const val PARENT_COURESE = 3070
            //孩子成绩
            const val PARENT_CHILD_RESULT = 3080
            //家长出入
            const val PARENT_OUT_IN_SCHOOL = 3090
            // 家长会家长通知
            const val PARENT_PARENT_MEETING = 3100
            // 亲子通话
            const val PARENT_QINZI_TONGHUA = 3120
            //话机识别
            const val PARENT_HUAJI_SHIBIE = 3130
        }
    }
}