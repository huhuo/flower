package com.huhuo.integration.util;

public class CheckUtils {

	/**
	 * obj 是否为null值
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj) {
		return obj == null;
	}
	
	/**
	 * objs是否全为null值
	 * @param objs
	 * @return
	 */
	public static boolean isAllNull(Object... objs) {
		boolean isAllNull = true;
		for(Object obj : objs) {
			isAllNull = isAllNull && isNull(obj);
		}
		return isAllNull;
	}
	
}
