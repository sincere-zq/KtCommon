package com.witaction.yunxiaowei.framwork;

import com.witaction.yunxiaowei.R;

/**
 * app配置
 */
public interface AppConfig {
    //url地址前缀
    String APP_SCHEME = "http://app.";
    String[] HOME_TITLES = {"首页", "消息", "我的"};
    int[] HOME_ICONS = {
            R.drawable.selector_home_selector,
            R.drawable.selector_message_selector,
            R.drawable.selector_my_selector};
    //首页每行菜单个数
    int HOME_MENU_NUM = 4;
    int STUDENT = 1;
    int TEACHER = 2;
    int PARENT = 3;
}
