package com.zhixing.entity.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * 描述: 版本号获取工具类
 *
 * @author : YiCH
 * @date : 2018/9/7 0007.
 */
public class VersionUtils {
    /**
     * 获取当前本地apk的版本
     *
     * @param mContext 上下文
     * @return  版本号
     */
    public static int getVersionCode(Context mContext) {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return 版本名称
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }


}
