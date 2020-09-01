package com.witaction.common.utils

import android.widget.Toast
import com.witaction.common.base.BApp

fun toast(content: String?) {
    Toast.makeText(BApp.context, content, Toast.LENGTH_SHORT).show()
}

fun toast(contentRes: Int) {
    Toast.makeText(BApp.context, contentRes, Toast.LENGTH_SHORT).show()
}

//fun showFailure(message: String, view: LoadingView) {
//    toast(view.context, message)
//    view.setLoadText(message)
//    view.visibility = View.VISIBLE
//}
//
//fun showFailure(currentPage: Int, message: String, view: LoadingView) {
//    toast(view.context, message)
//    if (currentPage == 0 || TextUtils.equals(BService.NET_ERROR, message)) {
//        view.setLoadText(message)
//        view.visibility = View.VISIBLE
//    }
//}