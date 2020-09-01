package com.witaction.campusdefender

interface AppConfig {
    companion object {
        /**
         * DES加密key值
         */
        const val DES_KEY = "dbke6413"
        /**
         * DES加密偏移量
         */
        const val DES_IV = "64133146"
        val HOME_TITLES = arrayOf("首页", "消息", "我的")
        val HOME_ICONS = intArrayOf(
            R.drawable.selector_home_selector,
            R.drawable.selector_message_selector,
            R.drawable.selector_my_selector
        )
    }
}