package cn.cloudwalk.libproject.util;


import android.util.Log;

/**
 * 日志辅助类,用于调试打印输入日志({@code DEBUG, VERBOSE, INFO, WARN, ERROR}).
 * <p>
 * <font color="#ff8800">日志级别设置:</font>
 * <ol>
 * <li>adb shell</li>
 * <li>setprop log.tag.TAG DEBUG</li>
 * </ol>
 * Created by Cuke Pie on 13-6-24.
 */
public class LogUtils {
	private static final boolean isLOGD = false;
	private static final boolean isLOGI = false;
	private static final boolean isLOGV = false;
	private static final boolean isLOGE = false;
	private static final boolean isLOGW = false;
	private static final String LOG_PREFIX = "cw_";
	private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
	private static final int MAX_LOG_LENGTH = 23;

	public static String makeLogTag(String str) {
		if (str.length() > MAX_LOG_LENGTH - LOG_PREFIX_LENGTH) {
			return LOG_PREFIX
					+ str.substring(0, MAX_LOG_LENGTH - LOG_PREFIX_LENGTH - 1);
		}
		return LOG_PREFIX + str;
	}

	/**
	 * 注意:使用混淆工具来混淆类名,最好不要只用此方法.
	 */
	public static String makeLogTag(Class cls) {
		return makeLogTag(cls.getSimpleName());
	}

	public static void LOGD(final String tag, String message) {
		// 根据日志的级别判断是否打印日志
		if (isLOGD) Log.d(tag, message);
		
	}

	

	public static void LOGV(final String tag, String message) {

		if (isLOGV)Log.v(tag, message);

	}



	public static void LOGI(String tag, String message) {
		if (isLOGI)Log.i(tag, message);
	}

	

	public static void LOGW(String tag, String message) {
		if (isLOGW)Log.w(tag, message);
	}

	

	public static void LOGE(String tag, String message) {
		if (isLOGE)Log.e(tag, message);
	}

	

	private LogUtils() {
	}
}
