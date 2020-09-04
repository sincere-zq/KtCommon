package com.witaction.campusdefender.ui.main.home

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.witaction.campusdefender.AppConfig
import com.witaction.campusdefender.R
import com.witaction.campusdefender.ui.TransAlert

/**
 * 事务提醒
 */
class TransAlertAdapter(transList: MutableList<TransAlert>) :
    BaseQuickAdapter<TransAlert, BaseViewHolder>(R.layout.item_trans_alert, transList) {
    override fun convert(holder: BaseViewHolder, item: TransAlert) {
        holder.setText(R.id.tv_tans_type, item.pushMessageTypeStr)
            .setText(R.id.tv_tans_content, item.content)
            .setText(R.id.tv_time, item.createTime)
        showIcon(holder, item.pushMessageType)
    }

    /**
     * 根据极光推送type，显示对应的图标
     */
    private fun showIcon(helper: BaseViewHolder, type: Int) {
        if (type >= 2500 && type <= 2599) { //老师端OA推送type范围
            helper.setImageResource(R.id.img_tag, R.mipmap.ic_oa_notice)
            return
        }
        when (type) {
            AppConfig.MessageConfig.TEACHER_SCHOOL_NOTICE ->  //学校通知公告
                helper.setImageResource(R.id.img_tag, R.mipmap.ic_school_notice)
            AppConfig.MessageConfig.TEACHER_APPROVAL ->  //教师审批
                helper.setImageResource(R.id.img_tag, R.mipmap.ic_teacher_approval)
            AppConfig.MessageConfig.TEACHER_STUDENT_LEAVE ->  //学生请假
                helper.setImageResource(R.id.img_tag, R.mipmap.ic_student_leave)
            AppConfig.MessageConfig.TEACHER_SCHOOL_BUS ->  //校车任务
                helper.setImageResource(R.id.img_tag, R.mipmap.ic_school_bus)
            AppConfig.MessageConfig.TEACHER_PARENT_OUT_IN ->  //家长出入
                helper.setImageResource(R.id.img_tag, R.mipmap.ic_parent_out_in_affair)
            AppConfig.MessageConfig.TEACHER_PARENT_MEETING ->  //家长会
                helper.setImageResource(R.id.img_tag, R.mipmap.ic_parent_meeting_affair)
            AppConfig.MessageConfig.PARENT_CLASS_NOTICE ->  //班级通知公告
                helper.setImageResource(R.id.img_tag, R.mipmap.ic_class_notice)
            AppConfig.MessageConfig.PARENT_HOME_WORK ->  //孩子作业管理
                helper.setImageResource(R.id.img_tag, R.mipmap.ic_home_work)
            AppConfig.MessageConfig.PARENT_CHILD_RESULT ->  //孩子成绩
                helper.setImageResource(R.id.img_tag, R.mipmap.icon_grad_notice)
            AppConfig.MessageConfig.PARENT_SCHOOL_IN_OUT ->  //校门出入
                helper.setImageResource(R.id.img_tag, R.mipmap.ic_school_gate_record)
            AppConfig.MessageConfig.PARENT_APARTMENT_IN_OUT ->  //公寓出入
                helper.setImageResource(R.id.img_tag, R.mipmap.ic_apartment_gate_record)
            AppConfig.MessageConfig.PARENT_CHILD_LEAVE ->  //孩子请假
                helper.setImageResource(R.id.img_tag, R.mipmap.ic_student_leave)
            AppConfig.MessageConfig.PARENT_SCHOOL_BUS ->  //校车接送
                helper.setImageResource(R.id.img_tag, R.mipmap.ic_school_bus)
            AppConfig.MessageConfig.PARENT_COURESE ->  //选课
                helper.setImageResource(R.id.img_tag, R.mipmap.ic_course)
            AppConfig.MessageConfig.PARENT_OUT_IN_SCHOOL ->  //家长出入校
                helper.setImageResource(R.id.img_tag, R.mipmap.ic_parent_out_in_affair)
            AppConfig.MessageConfig.PARENT_PARENT_MEETING ->  //家长会
                helper.setImageResource(R.id.img_tag, R.mipmap.ic_parent_meeting_affair)
            else -> helper.setImageResource(R.id.img_tag, R.mipmap.ic_default)
        }
    }
}