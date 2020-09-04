package com.witaction.campusdefender.ui

import com.google.gson.Gson
import com.witaction.common.extension.edit
import com.witaction.common.extension.getList
import com.witaction.common.extension.getObject
import com.witaction.common.utils.GlobalUtil

/**
 * 本地存储
 */
object ServiceLocalReponsitory {
    private const val USER = "USER"
    private const val ALL_MENU = "ALL_MENU"
    private const val MENU_INFO = "MENU_INFO"
    private val gson = Gson()

    /**
     * 保存用户信息
     */
    fun saveUser(user: User) {
        GlobalUtil.sharedPreferences.edit {
            putString(USER, gson.toJson(user))
        }
    }

    /**
     * 获取用户信息
     */
    fun getUser(): User? {
        val platString = GlobalUtil.sharedPreferences.getString(USER, "")
        return platString?.let { gson.getObject<User>(it) }
    }

    /**
     * 保存老师所有菜单
     */
    fun saveMenu(menuList: MutableList<TeacherMenuSection>) {
        GlobalUtil.sharedPreferences.edit {
            putString(ALL_MENU, gson.toJson(menuList))
        }
    }

    /**
     * 获取老师所有菜单
     */
    fun getMenu(): MutableList<TeacherMenuSection>? {
        val menuString = GlobalUtil.sharedPreferences.getString(ALL_MENU, "")
        return menuString?.let { gson.getList(it) }
    }

    /**
     * 保存老师菜单信息
     */
    fun saveMenuInfo(menuInfo: TeacherMenuResult) {
        GlobalUtil.sharedPreferences.edit {
            putString(MENU_INFO, gson.toJson(menuInfo))
        }
    }

    /**
     * 获取老师自菜单信息
     */
    fun getMenuInfo(): TeacherMenuResult? {
        val menuString = GlobalUtil.sharedPreferences.getString(MENU_INFO, "")
        return menuString?.let { gson.getObject(it) }
    }


    /**
     * 登出
     */
    fun loginOut() {
        GlobalUtil.sharedPreferences.edit {
            remove(USER)
        }
    }
}