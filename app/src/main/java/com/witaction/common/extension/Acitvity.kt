package com.witaction.common.extension

import android.app.Activity
import android.content.Intent
import android.os.Bundle

fun Activity.open(pClass: Class<*>?, pBundle: Bundle? = null) {
    val intent = Intent(this, pClass)
    if (pBundle != null) {
        intent.putExtras(pBundle)
    }
    startActivity(intent)
    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
}

fun Activity.open(pClass: Class<*>?, pBundle: Bundle? = null, requestCode: Int) {
    val intent = Intent(this, pClass)
    if (pBundle != null) {
        intent.putExtras(pBundle)
    }
    startActivityForResult(intent, requestCode)
    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
}

fun Activity.open(pAction: String?, pBundle: Bundle? = null) {
    val intent = Intent(pAction)
    if (pBundle != null) {
        intent.putExtras(pBundle)
    }
    startActivity(intent)
    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
}
