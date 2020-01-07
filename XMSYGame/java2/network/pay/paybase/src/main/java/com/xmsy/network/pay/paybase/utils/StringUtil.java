package com.xmsy.network.pay.paybase.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * str处理工具
 * 
 * @author Administrator
 *
 */
public class StringUtil {

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
}
