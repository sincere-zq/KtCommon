package com.witaction.common.extension

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment

inline fun <reified T : Activity> Fragment.open(pBundle: Bundle? = null) {
    val intent = Intent(activity, T::class.java)
    if (pBundle != null) {
        intent.putExtras(pBundle)
    }
    activity?.startActivity(intent)
    activity?.overridePendingTransition(
        android.R.anim.slide_in_left,
        android.R.anim.slide_out_right
    )
}

inline fun <reified T : Activity> Fragment.open(pBundle: Bundle? = null, requestCode: Int) {
    val intent = Intent(context, T::class.java)
    if (pBundle != null) {
        intent.putExtras(pBundle)
    }
    activity?.startActivityForResult(intent, requestCode)
    activity?.overridePendingTransition(
        android.R.anim.slide_in_left,
        android.R.anim.slide_out_right
    )
}

fun Fragment.open(pAction: String?, pBundle: Bundle? = null) {
    val intent = Intent(pAction)
    if (pBundle != null) {
        intent.putExtras(pBundle)
    }
    activity?.startActivity(intent)
    activity?.overridePendingTransition(
        android.R.anim.slide_in_left,
        android.R.anim.slide_out_right
    )
}