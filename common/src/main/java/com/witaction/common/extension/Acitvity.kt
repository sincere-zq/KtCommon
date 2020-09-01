package com.witaction.common.extension

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.witaction.common.R

inline fun <reified T : Activity> Activity.open(pBundle: Bundle? = null) {
    val intent = Intent(this, T::class.java)
    if (pBundle != null) {
        intent.putExtras(pBundle)
    }
    startActivity(intent)
    overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out)
}

inline fun <reified T : Activity> Activity.open(requestCode: Int, pBundle: Bundle? = null) {
    val intent = Intent(this, T::class.java)
    if (pBundle != null) {
        intent.putExtras(pBundle)
    }
    startActivityForResult(intent, requestCode)
    overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out)
}

fun Activity.open(pAction: String?, pBundle: Bundle? = null) {
    val intent = Intent(pAction)
    if (pBundle != null) {
        intent.putExtras(pBundle)
    }
    startActivity(intent)
    overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out)
}