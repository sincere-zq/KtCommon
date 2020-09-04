package com.witaction.common.extension

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.witaction.common.R

inline fun <reified T : Activity> Fragment.open(pBundle: Bundle? = null) {
    val intent = Intent(activity, T::class.java)
    if (pBundle != null) {
        intent.putExtras(pBundle)
    }
    activity?.startActivity(intent)
    activity?.overridePendingTransition(
        R.anim.slide_in_right, R.anim.fade_out
    )
}

inline fun <reified T : Activity> Fragment.open(requestCode: Int, pBundle: Bundle? = null) {
    val intent = Intent(context, T::class.java)
    if (pBundle != null) {
        intent.putExtras(pBundle)
    }
    activity?.startActivityForResult(intent, requestCode)
    activity?.overridePendingTransition(
        R.anim.slide_in_right, R.anim.fade_out
    )
}

fun Fragment.open(pAction: String?, pBundle: Bundle? = null) {
    val intent = Intent(pAction)
    if (pBundle != null) {
        intent.putExtras(pBundle)
    }
    activity?.startActivity(intent)
    activity?.overridePendingTransition(
        R.anim.slide_in_right, R.anim.fade_out
    )
}