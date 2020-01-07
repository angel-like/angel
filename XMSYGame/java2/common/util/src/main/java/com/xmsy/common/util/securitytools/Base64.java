package com.xmsy.common.util.securitytools;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Base64 {
	/**
	 * the example.
	 * 
	 * @param args
	 * @throws UnsupportedEncodingException
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		// String data = "0123456abcdef中文123abc";
		// byte[] result = Base64.encode(data.getBytes());
		// String str = "+/\n\r";
		// System.out.println(URLEncoder.encode(str, "UTF-8"));
		// System.out.println(data);
		// System.out.println(new String(result));
		// System.out.println(new String(Base64.decode(new String(result))));
		System.out.println(byteArrayToString(Base64.decode("BgIDBAoD")));
	}

	public static String byteArrayToString(byte[] b) {
		StringBuilder stringBuilder = new StringBuilder();
		for (byte temp : b) {
			stringBuilder.append(temp);
			stringBuilder.append(",");
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		return stringBuilder.toString();
	}

	/**
	 * encode characters table.
	 */
	private static final byte[] ENC_TAB = { (byte) 'A', (byte) 'B', (byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F',
			(byte) 'G', (byte) 'H', (byte) 'I', (byte) 'J', (byte) 'K', (byte) 'L', (byte) 'M', (byte) 'N', (byte) 'O',
			(byte) 'P', (byte) 'Q', (byte) 'R', (byte) 'S', (byte) 'T', (byte) 'U', (byte) 'V', (byte) 'W', (byte) 'X',
			(byte) 'Y', (byte) 'Z', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f', (byte) 'g',
			(byte) 'h', (byte) 'i', (byte) 'j', (byte) 'k', (byte) 'l', (byte) 'm', (byte) 'n', (byte) 'o', (byte) 'p',
			(byte) 'q', (byte) 'r', (byte) 's', (byte) 't', (byte) 'u', (byte) 'v', (byte) 'w', (byte) 'x', (byte) 'y',
			(byte) 'z', (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7',
			(byte) '8', (byte) '9', (byte) '+', (byte) '/' };

	/**
	 * decode characters table.
	 */
	private static final byte[] DEC_TAB;
	static {
		DEC_TAB = new byte[128];
		for (int i = 0; i < 128; i++) {
			DEC_TAB[i] = (byte) -1;
		}
		for (int i = 'A'; i <= 'Z'; i++) {
			DEC_TAB[i] = (byte) (i - 'A');
		}
		for (int i = 'a'; i <= 'z'; i++) {
			DEC_TAB[i] = (byte) (i - 'a' + 26);
		}
		for (int i = '0'; i <= '9'; i++) {
			DEC_TAB[i] = (byte) (i - '0' + 52);
		}
		DEC_TAB['+'] = 62;
		DEC_TAB['/'] = 63;
	}

	/**
	 * encode byte array data to base 64 hex string data.
	 * 
	 * @param data
	 *            byte array data.
	 * @return base 64 hex string data. use new String("UTF-8") to get the String
	 *         format.
	 */
	public static byte[] encode(byte[] data) {
		byte[] bytes;
		int modulus = data.length % 3;
		if (modulus == 0) {
			bytes = new byte[(4 * data.length) / 3];
		} else {
			bytes = new byte[4 * ((data.length / 3) + 1)];
		}
		int dataLength = (data.length - modulus);
		int a1;
		int a2;
		int a3;
		for (int i = 0, j = 0; i < dataLength; i += 3, j += 4) {
			a1 = data[i] & 0xff;
			a2 = data[i + 1] & 0xff;
			a3 = data[i + 2] & 0xff;
			bytes[j] = ENC_TAB[(a1 >>> 2) & 0x3f];
			bytes[j + 1] = ENC_TAB[((a1 << 4) | (a2 >>> 4)) & 0x3f];
			bytes[j + 2] = ENC_TAB[((a2 << 2) | (a3 >>> 6)) & 0x3f];
			bytes[j + 3] = ENC_TAB[a3 & 0x3f];
		}
		int b1;
		int b2;
		int b3;
		int d1;
		int d2;
		switch (modulus) {
		case 0: /* nothing left to do */
			break;
		case 1:
			d1 = data[data.length - 1] & 0xff;
			b1 = (d1 >>> 2) & 0x3f;
			b2 = (d1 << 4) & 0x3f;
			bytes[bytes.length - 4] = ENC_TAB[b1];
			bytes[bytes.length - 3] = ENC_TAB[b2];
			bytes[bytes.length - 2] = (byte) '=';
			bytes[bytes.length - 1] = (byte) '=';
			break;
		case 2:
			d1 = data[data.length - 2] & 0xff;
			d2 = data[data.length - 1] & 0xff;
			b1 = (d1 >>> 2) & 0x3f;
			b2 = ((d1 << 4) | (d2 >>> 4)) & 0x3f;
			b3 = (d2 << 2) & 0x3f;
			bytes[bytes.length - 4] = ENC_TAB[b1];
			bytes[bytes.length - 3] = ENC_TAB[b2];
			bytes[bytes.length - 2] = ENC_TAB[b3];
			bytes[bytes.length - 1] = (byte) '=';
			break;
		}
		return bytes;
	}

	/**
	 * decode base 64 string data to byte data array.
	 * 
	 * @param data
	 *            base 64 string data.
	 * @return byte data array.
	 */
	public static byte[] decode(byte[] data) {
		byte[] bytes;
		byte b1;
		byte b2;
		byte b3;
		byte b4;
		data = discardNonBase64Bytes(data);
		if (data[data.length - 2] == '=') {
			bytes = new byte[(((data.length / 4) - 1) * 3) + 1];
		} else if (data[data.length - 1] == '=') {
			bytes = new byte[(((data.length / 4) - 1) * 3) + 2];
		} else {
			bytes = new byte[((data.length / 4) * 3)];
		}
		for (int i = 0, j = 0; i < (data.length - 4); i += 4, j += 3) {
			b1 = DEC_TAB[data[i]];
			b2 = DEC_TAB[data[i + 1]];
			b3 = DEC_TAB[data[i + 2]];
			b4 = DEC_TAB[data[i + 3]];
			bytes[j] = (byte) ((b1 << 2) | (b2 >> 4));
			bytes[j + 1] = (byte) ((b2 << 4) | (b3 >> 2));
			bytes[j + 2] = (byte) ((b3 << 6) | b4);
		}
		if (data[data.length - 2] == '=') {
			b1 = DEC_TAB[data[data.length - 4]];
			b2 = DEC_TAB[data[data.length - 3]];
			bytes[bytes.length - 1] = (byte) ((b1 << 2) | (b2 >> 4));
		} else if (data[data.length - 1] == '=') {
			b1 = DEC_TAB[data[data.length - 4]];
			b2 = DEC_TAB[data[data.length - 3]];
			b3 = DEC_TAB[data[data.length - 2]];
			bytes[bytes.length - 2] = (byte) ((b1 << 2) | (b2 >> 4));
			bytes[bytes.length - 1] = (byte) ((b2 << 4) | (b3 >> 2));
		} else {
			b1 = DEC_TAB[data[data.length - 4]];
			b2 = DEC_TAB[data[data.length - 3]];
			b3 = DEC_TAB[data[data.length - 2]];
			b4 = DEC_TAB[data[data.length - 1]];
			bytes[bytes.length - 3] = (byte) ((b1 << 2) | (b2 >> 4));
			bytes[bytes.length - 2] = (byte) ((b2 << 4) | (b3 >> 2));
			bytes[bytes.length - 1] = (byte) ((b3 << 6) | b4);
		}
		return bytes;
	}

	/**
	 * decode base 64 string to byte array data.
	 * 
	 * @param data
	 *            base 64 string.
	 * @return byte array data.
	 */
	public static byte[] decode(String data) {
		byte[] bytes;
		byte b1;
		byte b2;
		byte b3;
		byte b4;
		data = discardNonBase64Chars(data);
		if (data.charAt(data.length() - 2) == '=') {
			bytes = new byte[(((data.length() / 4) - 1) * 3) + 1];
		} else if (data.charAt(data.length() - 1) == '=') {
			bytes = new byte[(((data.length() / 4) - 1) * 3) + 2];
		} else {
			bytes = new byte[((data.length() / 4) * 3)];
		}
		for (int i = 0, j = 0; i < (data.length() - 4); i += 4, j += 3) {
			b1 = DEC_TAB[data.charAt(i)];
			b2 = DEC_TAB[data.charAt(i + 1)];
			b3 = DEC_TAB[data.charAt(i + 2)];
			b4 = DEC_TAB[data.charAt(i + 3)];
			bytes[j] = (byte) ((b1 << 2) | (b2 >> 4));
			bytes[j + 1] = (byte) ((b2 << 4) | (b3 >> 2));
			bytes[j + 2] = (byte) ((b3 << 6) | b4);
		}
		if (data.charAt(data.length() - 2) == '=') {
			b1 = DEC_TAB[data.charAt(data.length() - 4)];
			b2 = DEC_TAB[data.charAt(data.length() - 3)];
			bytes[bytes.length - 1] = (byte) ((b1 << 2) | (b2 >> 4));
		} else if (data.charAt(data.length() - 1) == '=') {
			b1 = DEC_TAB[data.charAt(data.length() - 4)];
			b2 = DEC_TAB[data.charAt(data.length() - 3)];
			b3 = DEC_TAB[data.charAt(data.length() - 2)];
			bytes[bytes.length - 2] = (byte) ((b1 << 2) | (b2 >> 4));
			bytes[bytes.length - 1] = (byte) ((b2 << 4) | (b3 >> 2));
		} else {
			b1 = DEC_TAB[data.charAt(data.length() - 4)];
			b2 = DEC_TAB[data.charAt(data.length() - 3)];
			b3 = DEC_TAB[data.charAt(data.length() - 2)];
			b4 = DEC_TAB[data.charAt(data.length() - 1)];
			bytes[bytes.length - 3] = (byte) ((b1 << 2) | (b2 >> 4));
			bytes[bytes.length - 2] = (byte) ((b2 << 4) | (b3 >> 2));
			bytes[bytes.length - 1] = (byte) ((b3 << 6) | b4);
		}
		return bytes;
	}

	private static byte[] discardNonBase64Bytes(byte[] data) {
		byte[] temp = new byte[data.length];
		int bytesCopied = 0;
		for (int i = 0; i < data.length; i++) {
			if (isValidBase64Byte(data[i])) {
				temp[bytesCopied++] = data[i];
			}
		}
		byte[] newData = new byte[bytesCopied];
		System.arraycopy(temp, 0, newData, 0, bytesCopied);
		return newData;
	}

	private static String discardNonBase64Chars(String data) {
		StringBuffer sb = new StringBuffer();
		int length = data.length();
		for (int i = 0; i < length; i++) {
			if (isValidBase64Byte((byte) (data.charAt(i)))) {
				sb.append(data.charAt(i));
			}
		}
		return sb.toString();
	}

	private static boolean isValidBase64Byte(byte b) {
		if (b == '=') {
			return true;
		} else if ((b < 0) || (b >= 128)) {
			return false;
		} else if (DEC_TAB[b] == -1) {
			return false;
		}
		return true;
	}

	public static String encodeReplaceRN(byte[] bytes) {

		byte[] buf = encode(bytes);
		try {
			String base64String = new String(buf, "UTF-8");
			Pattern p = Pattern.compile("\r|\n|\0");
			Matcher m = p.matcher(base64String);
			base64String = m.replaceAll("");
			return base64String;
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public static byte[] decodeReplaceRN(String base64String) {
		Pattern p = Pattern.compile("\r|\n|\0");
		Matcher m = p.matcher(base64String);
		base64String = m.replaceAll("");
		return decode(base64String);
	}
}
