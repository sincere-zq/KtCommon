package com.witaction.common.extension

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment

fun Fragment.open(pClass: Class<*>?, pBundle: Bundle? = null) {
    val intent = Intent(activity, pClass)
    if (pBundle != null) {
        intent.putExtras(pBundle)
    }
    activity?.startActivity(intent)
    activity?.overridePendingTransition(
        android.R.anim.slide_in_left,
        android.R.anim.slide_out_right
    )
}

fun Fragment.open(pClass: Class<*>?, pBundle: Bundle? = null, requestCode: Int) {
    val intent = Intent(context, pClass)
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