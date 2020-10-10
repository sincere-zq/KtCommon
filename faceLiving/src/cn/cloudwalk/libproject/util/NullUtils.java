/**
 * NullUtils.java
 * 
 * Create Version: 1.0
 * Create Date: 2012-4-24
 */
package cn.cloudwalk.libproject.util;

import java.util.Collection;
import java.util.Map;


public class NullUtils {
	
	public static Boolean isNull(Object obj) {
		return null == obj;
	}

	
	public static Boolean isNotNull(Object obj) {
		return null != obj;
	}

	
	public static Boolean isEmpty(String str) {
		return isNull(str) || str.trim().length() == 0;
	}

	
	public static Boolean isNotEmpty(String str) {
		return isNotNull(str) && str.trim().length() > 0;
	}

	
	public static Boolean isEmpty(Collection<?> collection) {
		return isNull(collection) || collection.size() == 0;
	}

	
	public static Boolean isNotEmpty(Collection<?> collection) {
		return isNotNull(collection) && collection.size() > 0;
	}

	
	public static Boolean isEmpty(Object[] arrary) {
		return isNull(arrary) || arrary.length == 0;
	}

	
	public static Boolean isNotEmpty(Object[] array) {
		return isNotNull(array) && array.length > 0;
	}

	public static Boolean isNotEmpty(byte[] arrary) {
		return null != arrary && arrary.length > 0;
	}
	public static Boolean isEmpty(Map<?, ?> map) {
		return isNull(map) || map.isEmpty();
	}

	public static Boolean isNotEmpty(Map<?, ?> map) {
		return isNotNull(map) && !map.isEmpty();
	}
}
