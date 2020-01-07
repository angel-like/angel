package com.xmsy.common.util.numbertools;

/**
 * 数字转换攻击类
 * 
 * @author aleng
 *
 */
public class NumberConvert {

	/**
	 * 正数转负数
	 * 
	 * @param a
	 * @return
	 */
	public static Long resert(Long number) {
		if (null == number) {
			return 0L;
		}
		return (~number + 1);
	};

}
