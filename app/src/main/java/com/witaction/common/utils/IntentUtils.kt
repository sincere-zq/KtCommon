package com.witaction.common.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 * 跳转
 */
/**
 * 通过类名启动Activity
 *
 * @param pClass
 */
fun openActivity(
    context: Context,
    pClass: Class<*>?
) {
    openActivity(context, pClass, null)
}

/**
 * 通过类名启动Activity，并且含有Bundle数据
 *
 * @param pClass
 * @param pBundle
 */
fun openActivity(
    context: Context,
    pClass: Class<*>?,
    pBundle: Bundle?
) {
    val intent = Intent(context, pClass)
    if (pBundle != null) {
        intent.putExtras(pBundle)
    }
    context.startActivity(intent)
}

/**
 * 通过类名启动Activity
 */
fun openActivityForResult(
    context: Activity,
    pClass: Class<*>?,
    requestCode: Int
) {
    openActivityForResult(context, pClass, null, requestCode)
}

/**
 * 通过类名启动Activity，并含有bundle数据，
 */
fun openActivityForResult(
    context: Activity,
    pClass: Class<*>?,
    pBundle: Bundle?,
    requestCode: Int
) {
    val intent = Intent(context, pClass)
    if (pBundle != null) {
        intent.putExtras(pBundle)
    }
    context.startActivityForResult(intent, requestCode)
}

/**
 * 通过Action启动Activity
 *
 * @param pAction
 */
fun openActivity(context: Context, pAction: String?) {
    openActivity(context, pAction, null)
}

/**
 * 通过Action启动Activity，并且含有Bundle数据
 *
 * @param pAction
 * @param pBundle
 */
fun openActivity(
    context: Context,
    pAction: String?,
    pBundle: Bundle?
) {
    val intent = Intent(pAction)
    if (pBundle != null) {
        intent.putExtras(pBundle)
    }
    context.startActivity(intent)
}