package com.witaction.common.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File

fun getVersionCode(context: Context): Int {
    var versionCode = 0
    try { //获取软件版本号，对应AndroidManifest.xml下android:versionCode
        versionCode =
            context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return versionCode
}

fun getVersionName(context: Context): String {
    var versionName = ""
    try { //获取软件版本号，对应AndroidManifest.xml下android:versionCode
        versionName =
            context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return versionName
}

fun install(context: Context, filePath: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    val file = File(filePath)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        //大于7.0使用此方法
        val apkUri: Uri =
            FileProvider.getUriForFile(
                context,
                context.packageName + ".fileProvider",
                file
            )
        ///-----ide文件提供者名
        //添加这一句表示对目标应用临时授权该Uri所代表的文件
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
    } else {
        //小于7.0就简单了
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
    }
    context.startActivity(intent)
}