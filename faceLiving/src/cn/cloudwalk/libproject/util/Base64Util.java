package cn.cloudwalk.libproject.util;

import android.util.Base64;

public class Base64Util {

	private Base64Util() {
	}

	public static String encode(byte[] data) {
		return Base64.encodeToString(data, Base64.DEFAULT);
	}

	public static byte[] decode(String str) {
		return Base64.decode(str, Base64.DEFAULT);
	}
}

