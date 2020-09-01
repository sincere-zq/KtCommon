package com.witaction.campusdefender.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.witaction.campusdefender.R
import com.witaction.common.widget.DividerDecoration

/**
 * 登录方式
 */
class LoginTypePopupwindow(
    val context: Context?,
    val listener: OnItemClickListener
) : PopupWindow(context) {
    private lateinit var adapter: LoginTypeAdapter

    init {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.popup_login_type, null)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        contentView = view
        isFocusable = true
        initAdapter(recyclerView)
    }

    private fun initAdapter(recyclerView: RecyclerView) {
        adapter = LoginTypeAdapter()
        adapter.setOnItemClickListener(listener)
        recyclerView.addItemDecoration(DividerDecoration(recyclerView.context))
        recyclerView.adapter = adapter
        val loginTypeArray = recyclerView.context.resources.getStringArray(R.array.loginTypeArray)
        for (type in loginTypeArray) {
            adapter.addData(type)
        }
    }

    private inner class LoginTypeAdapter :
        BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_login_type) {
        override fun convert(holder: BaseViewHolder, item: String) {
            holder.setText(R.id.tv_login_type, item)
        }
    }
}