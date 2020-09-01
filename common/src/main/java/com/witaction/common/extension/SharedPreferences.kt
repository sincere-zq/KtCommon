package com.witaction.common.extension

import android.content.SharedPreferences

/**
 * 简化SharedPreferences调用。
 *
 * @param action 拥有SharedPreferences.Editor对象上下文的回调代码块
 */
fun SharedPreferences.edit(action: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    action(editor)
    editor.apply()
}