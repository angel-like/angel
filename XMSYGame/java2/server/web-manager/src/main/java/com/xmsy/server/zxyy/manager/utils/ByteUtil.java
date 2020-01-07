package com.xmsy.server.zxyy.manager.utils;

/**
 * .字节码工具
 * 
 * @author
 *
 */
public class ByteUtil {

	// 字节数组改成字符
	public static String byteArrayToString(byte[] b) {
		StringBuilder stringBuilder = new StringBuilder();
		for (byte temp : b) {
			stringBuilder.append(temp);
			stringBuilder.append(",");
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		return stringBuilder.toString();
	}

	// int转二进制字符串
	public static final String toBin(int num) {
		char[] chs = new char[Integer.SIZE];
		for (int i = 0; i < Integer.SIZE; i++) {
			chs[Integer.SIZE - 1 - i] = (char) ((num >> i & 1) + '0');
		}
		return new String(chs);
	}

	// 获取二进制高9位
	public static final String highEight(int num) {
		char[] chs = new char[Integer.SIZE];
		for (int i = 0; i < Integer.SIZE; i++) {
			chs[Integer.SIZE - 1 - i] = (char) ((num >> i & 1) + '0');
		}
		return new String(chs).substring(0, 9);
	}

	// 获取二进制中9位
	public static final String middleEight(int num) {
		char[] chs = new char[Integer.SIZE];
		for (int i = 0; i < Integer.SIZE; i++) {
			chs[Integer.SIZE - 1 - i] = (char) ((num >> i & 1) + '0');
		}
		return new String(chs).substring(9, 18);
	}

	// 获取二进制低14位
	public static final String lowFourteen(int num) {
		char[] chs = new char[Integer.SIZE];
		for (int i = 0; i < Integer.SIZE; i++) {
			chs[Integer.SIZE - 1 - i] = (char) ((num >> i & 1) + '0');
		}
		return new String(chs).substring(18);
	}

	// 获取由高9位，中9位低14位拼成的二进制转成的十进制数
	public static final int totalBin(int high, int middle, int low) {
		return Integer.parseInt(highEight(high) + middleEight(middle) + lowFourteen(low), 2);
	}

	// 二进制字符串转10进制
	public static final int toInt(String binaryString) {
		return Integer.parseInt(binaryString, 2);
	}

	public static void main(String[] args) {
		// System.out.println(toBin(25182212));
		// System.out.println(middleEight(3));
		// System.out.println(lowFourteen(8));
		// System.out.println(totalBin(1, 3, 8));
		System.out.println(toInt(highEight(25198601)));
		System.out.println(toInt(middleEight(25198601)));
		System.out.println(toInt(lowFourteen(25198601)));
	}

}
