package com.witaction.common.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import androidx.core.content.edit
import com.witaction.common.base.BApp
import java.util.*

/**
 * 应用程序全局的通用工具类，功能比较单一，经常被复用的功能，应该封装到此工具类当中，从而给全局代码提供方面的操作。
 */
object GlobalUtil {
    /**
     * 批量设置控件点击事件。
     *
     * @param v 点击的控件
     * @param block 处理点击事件回调代码块
     */
    fun setOnClickListener(vararg v: View?, block: View.() -> Unit) {
        val listener = View.OnClickListener { it.block() }
        v.forEach { it?.setOnClickListener(listener) }
    }

    /**
     * 获取SharedPreferences实例。
     */
    val sharedPreferences: SharedPreferences = BApp.context.getSharedPreferences(
        GlobalUtil.appPackage + "_preferences",
        Context.MODE_PRIVATE
    )

    /**
     * 设置界面背景透明度0-1  主要用于POP弹出时 ,背景界面变色
     *
     * @param activity
     * @param bgAlpha
     */
    fun backgroundAlpha(activity: Activity, bgAlpha: Float) {
        val lp = activity.window.attributes
        lp.alpha = bgAlpha //0.0-1.0
        activity.window.attributes = lp
    }


    /**
     * 获取当前应用程序的包名。
     *
     * @return 当前应用程序的包名。
     */
    val appPackage: String
        get() = BApp.context.packageName

    /**
     * 获取当前应用程序的名称。
     * @return 当前应用程序的名称。
     */
    val appName: String
        get() = BApp.context.resources.getString(BApp.context.applicationInfo.labelRes)

    /**
     * 获取当前应用程序的版本名。
     * @return 当前应用程序的版本名。
     */
    val appVersionName: String
        get() = BApp.context.packageManager.getPackageInfo(appPackage, 0).versionName

    /**
     * 获取当前应用程序的版本号。
     * @return 当前应用程序的版本号。
     */
    val appVersionCode: Long
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            BApp.context.packageManager.getPackageInfo(appPackage, 0).longVersionCode
        } else {
            BApp.context.packageManager.getPackageInfo(appPackage, 0).versionCode.toLong()
        }

    /**
     * 获取开眼应用程序的版本名。
     * @return 开眼当前应用程序的版本名。
     */
    val eyepetizerVersionName: String
        get() = "6.3.01"

    /**
     * 获取开眼应用程序的版本号。
     * @return 开眼当前应用程序的版本号。
     */
    val eyepetizerVersionCode: Long
        get() = 6030012

    /**
     * 获取设备的的型号，如果无法获取到，则返回Unknown。
     * @return 设备型号。
     */
    val deviceModel: String
        get() {
            var deviceModel = Build.MODEL
            if (TextUtils.isEmpty(deviceModel)) {
                deviceModel = "unknown"
            }
            return deviceModel
        }

    /**
     * 获取设备的品牌，如果无法获取到，则返回Unknown。
     * @return 设备品牌，全部转换为小写格式。
     */
    val deviceBrand: String
        get() {
            var deviceBrand = Build.BRAND
            if (TextUtils.isEmpty(deviceBrand)) {
                deviceBrand = "unknown"
            }
            return deviceBrand.toLowerCase(Locale.getDefault())
        }

    private var deviceSerial: String? = null

    /**
     * 获取设备的序列号。如果无法获取到设备的序列号，则会生成一个随机的UUID来作为设备的序列号，UUID生成之后会存入缓存，
     * 下次获取设备序列号的时候会优先从缓存中读取。
     * @return 设备的序列号。
     */
    @SuppressLint("HardwareIds")
    fun getDeviceSerial(): String {
        if (deviceSerial == null) {
            var deviceId: String? = null
            val appChannel = getApplicationMetaData("APP_CHANNEL")
            if ("google" != appChannel || "samsung" != appChannel) {
                try {
                    deviceId = Settings.Secure.getString(
                        BApp.context.contentResolver,
                        Settings.Secure.ANDROID_ID
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                if (!TextUtils.isEmpty(deviceId) && deviceId!!.length < 255) {
                    deviceSerial = deviceId
                    return deviceSerial.toString()
                }
            }
            var uuid = sharedPreferences.getString("uuid", "")
            if (!TextUtils.isEmpty(uuid)) {
                deviceSerial = uuid
                return deviceSerial.toString()
            }
            uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase(Locale.getDefault())
            sharedPreferences.edit { putString("uuid", uuid) }
            deviceSerial = uuid
            return deviceSerial.toString()
        } else {
            return deviceSerial.toString()
        }
    }

    /**
     * 获取资源文件中定义的字符串。
     *
     * @param resId
     * 字符串资源id
     * @return 字符串资源id对应的字符串内容。
     */
    fun getString(resId: Int): String {
        return BApp.context.resources.getString(resId)
    }

    /**
     * 获取资源文件中的颜色码
     */
    fun getColor(resId: Int): Int {
        return BApp.context.resources.getColor(resId)
    }

    /**
     * 获取资源文件中定义的字符串。
     *
     * @param resId
     * 字符串资源id
     * @return 字符串资源id对应的字符串内容。
     */
    fun getDimension(resId: Int): Int {
        return BApp.context.resources.getDimensionPixelOffset(resId)
    }

    /**
     * 获取资源文件drawable
     */
    fun getDrawable(resId: Int): Drawable {
        return BApp.context.resources.getDrawable(resId)
    }

    /**
     * 获取指定资源名的资源id。
     *
     * @param name
     * 资源名
     * @param type
     * 资源类型
     * @return 指定资源名的资源id。
     */
    fun getResourceId(name: String, type: String): Int {
        return BApp.context.resources.getIdentifier(name, type, appPackage)
    }

    /**
     * 获取AndroidManifest.xml文件中，<application>标签下的meta-data值。
     *
     * @param key
     *  <application>标签下的meta-data健
     */
    fun getApplicationMetaData(key: String): String? {
        var applicationInfo: ApplicationInfo? = null
        try {
            applicationInfo = BApp.context.packageManager.getApplicationInfo(
                appPackage,
                PackageManager.GET_META_DATA
            )
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (applicationInfo == null) return ""
        return applicationInfo.metaData.getString(key)
    }

    /**
     * 判断某个应用是否安装。
     * @param packageName
     * 要检查是否安装的应用包名
     * @return 安装返回true，否则返回false。
     */
    fun isInstalled(packageName: String): Boolean {
        val packageInfo: PackageInfo? = try {
            BApp.context.packageManager.getPackageInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
        return packageInfo != null
    }

    /**
     * 获取当前应用程序的图标。
     */
    fun getAppIcon(): Drawable {
        val packageManager = BApp.context.packageManager
        val applicationInfo = packageManager.getApplicationInfo(appPackage, 0)
        return packageManager.getApplicationIcon(applicationInfo)
    }

    /**
     * 判断手机是否安装了QQ。
     */
    fun isQQInstalled() = isInstalled("com.tencent.mobileqq")

    /**
     * 判断手机是否安装了微信。
     */
    fun isWechatInstalled() = isInstalled("com.tencent.mm")

    /**
     * 判断手机是否安装了微博。
     * */
    fun isWeiboInstalled() = isInstalled("com.sina.weibo")

    /**
     * 获取图片名称获取图片的资源id的方法
     */
    fun getResourceByName(ctx: Context, imageName: String): Int {
        return ctx.resources.getIdentifier(imageName, "mipmap", ctx.packageName)
    }
}