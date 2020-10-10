package cn.cloudwalk.libproject.util;

public class StringUtil {
	private static final int PAD_LIMIT = 8192;
	/**
	 * 右边填充空字符
	 * @param str
	 * @param size 位数
	 * @return 填充后的字符串
	 */
	 public static String rightPad(String str, int size) {
	        return rightPad(str, size, ' ');
	    }
	   public static String rightPad(String str, int size, char padChar) {
	        if (str == null) {
	            return null;
	        }
	        int pads = size - str.length();
	        if (pads <= 0) {
	            return str; // returns original String when possible
	        }
	        if (pads > PAD_LIMIT) {
	            return rightPad(str, size, String.valueOf(padChar));
	        }
	        return str.concat(padding(pads, padChar));
	    }
	   public static boolean isEmpty(String str) {
	        return str == null || str.length() == 0;
	    }
	   public static String rightPad(String str, int size, String padStr) {
	        if (str == null) {
	            return null;
	        }
	        if (isEmpty(padStr)) {
	            padStr = " ";
	        }
	        int padLen = padStr.length();
	        int strLen = str.length();
	        int pads = size - strLen;
	        if (pads <= 0) {
	            return str; // returns original String when possible
	        }
	        if (padLen == 1 && pads <= PAD_LIMIT) {
	            return rightPad(str, size, padStr.charAt(0));
	        }

	        if (pads == padLen) {
	            return str.concat(padStr);
	        } else if (pads < padLen) {
	            return str.concat(padStr.substring(0, pads));
	        } else {
	            char[] padding = new char[pads];
	            char[] padChars = padStr.toCharArray();
	            for (int i = 0; i < pads; i++) {
	                padding[i] = padChars[i % padLen];
	            }
	            return str.concat(new String(padding));
	        }
	    }
	   private static String padding(int repeat, char padChar) throws IndexOutOfBoundsException {
	        if (repeat < 0) {
	            throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
	        }
	        final char[] buf = new char[repeat];
	        for (int i = 0; i < buf.length; i++) {
	            buf[i] = padChar;
	        }
	        return new String(buf);
	    }
}