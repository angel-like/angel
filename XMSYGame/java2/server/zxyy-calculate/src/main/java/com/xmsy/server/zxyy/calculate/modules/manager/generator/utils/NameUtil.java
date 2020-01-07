package com.xmsy.server.zxyy.calculate.modules.manager.generator.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 名称规则转换
 * 
 * @author Administrator
 *
 */
public class NameUtil {

	/**
	 * 下划线转驼峰
	 * 
	 * @param name
	 * @return
	 */
	public static String getNameHump(String name) {
		if (StringUtils.isEmpty(name)) {
			return "";
		}
		String str = name.toLowerCase();
		final StringBuffer sb = new StringBuffer();
		Pattern p = Pattern.compile("_(\\w)");
		Matcher m = p.matcher(str);
		while (m.find()) {
			m.appendReplacement(sb, m.group(1).toUpperCase());
		}
		m.appendTail(sb);
		return sb.toString();
	}

}
