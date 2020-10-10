package cn.cloudwalk.libproject.util;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;

import android.util.Log;

public class TestUtil {
	private static int num;
	private static int count;
	private static long time;

	private static long time1;
	private static long start_time;

	;

	// 两次间隔
	/**
	 * 计算两次间间隔
	 */
	public static void timeCount() {
		count++;
		if (count % 2 == 1)
			time = System.currentTimeMillis();

		if (count % 2 == 0)
			Log.e("test", System.currentTimeMillis() - time + "----------");
	}

	// 间隔时间
	/**
	 * 计算间隔开始
	 */
	public static void timeStart() {
		time1 = System.currentTimeMillis();

	}

	/**
	 * 计算间隔结束
	 */
	public static void timeEnd(String flag) {
		Log.e(flag, System.currentTimeMillis() - time1 + "----------");
	}

	// 每秒次数
	/**
	 * 计算一秒钟调用多少次
	 * 
	 * @param logFlag
	 */
	public static void time1sCount(String logFlag) {
		if (num != 0) {

			if ((System.currentTimeMillis() - start_time) <= 1000) {

				num++;
				Log.e(logFlag, num + "");
			} else {
				Log.e(logFlag, "============================");
				num = 0;
			}
		} else {
			start_time = System.currentTimeMillis();
			num++;
			Log.e(logFlag, num + "");
		}
	}

	public static void time1sCount2(String logFlag) {
		Log.e(logFlag, "-------" + num + "-------");
	}

	// 存1帧YUV420p
	static int count1 = 0;

	public static void save1FrameYUV(byte[] frame, String filePath) throws Exception {
		count1++;
		if (count1 % 10 == 0) {
			ByteArrayInputStream fis = new ByteArrayInputStream(frame);
			FileOutputStream fos = new FileOutputStream(filePath + "/frame" + count1);
			byte[] b = new byte[1024000];
			int n;
			while ((n = fis.read(b)) != -1) {
				fos.write(b, 0, n);
			}
			fis.close();
			fos.close();
		}

	}

}

