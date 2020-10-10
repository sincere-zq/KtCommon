package com.witaction.yunxiaowei.ui.main.home.classaffairs.rouster

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.witaction.common.extension.load
import com.witaction.common.extension.open
import com.witaction.yunxiaowei.ClassBean
import com.witaction.yunxiaowei.R
import com.witaction.yunxiaowei.StudentRoster
import com.witaction.yunxiaowei.ui.main.home.classaffairs.addstatus.AddStudentStatusActivity
import com.witaction.yunxiaowei.ui.main.my.accountset.collectface.CollectFaceActivity

class StudentRosterAdapter(val classInfo: ClassBean) : BaseQuickAdapter<StudentRoster, BaseViewHolder>(R.layout.item_student_roster) {

    override fun convert(holder: BaseViewHolder, item: StudentRoster) {
        holder.setText(R.id.tv_stu_name, item.name)
            .setText(R.id.tv_stu_no, item.studentNo)
            .setGone(R.id.tv_stu_status_manage, !classInfo.isClassTeacher || item.identityNoValid == 1)
            .setGone(R.id.tv_stu_face_manage, !classInfo.isClassTeacher)
        holder.getView<ImageView>(R.id.img_avatar).load(item.avatarUrl) {
            error(R.mipmap.icon_home_placeholder)
        }
        holder.getView<TextView>(R.id.tv_stu_face_manage).setOnClickListener {
            val bundle = Bundle()
            bundle.putString(CollectFaceActivity.PERSON_ID, item.id)
            (context as Activity).open<CollectFaceActivity>(bundle)
        }
        holder.getView<TextView>(R.id.tv_stu_status_manage).setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(AddStudentStatusActivity.STUDENT_INFO, item)
            (context as Activity).open<AddStudentStatusActivity>(bundle)
        }
    }
}