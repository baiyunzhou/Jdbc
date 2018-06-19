package com.zby.meta;

public class StringUtil {

	/**
	 * 
	 * @param str
	 * @return
	 * @description 字符串首字符变为大写
	 */
	public static String upperFirst(String str) {
		str = str.substring(0, 1).toUpperCase() + str.substring(1);
		return str;
	}

	/**
	 * 
	 * @param str
	 * @return
	 * @description 把以下划线分割的字符串改为驼峰命名
	 */
	public static String switchToCamelCase(String str) {
		String[] split = str.split("_");
		if (split.length < 2) {
			return upperFirst(str);
		}
		StringBuilder sb = new StringBuilder();
		for (String string : split) {
			sb.append(upperFirst(string));
		}
		return sb.toString();
	}
}
