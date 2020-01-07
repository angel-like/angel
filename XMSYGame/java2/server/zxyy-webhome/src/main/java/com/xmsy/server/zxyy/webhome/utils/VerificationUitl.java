package com.xmsy.server.zxyy.webhome.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * .密码验证，数字加字母（6-12位）
 * 
 * @author xiaoliu
 * @since 2017年8月20日
 */
public class VerificationUitl {

	/**
	 * 手机号检验
	 * 
	 * @param cardNum
	 * @return
	 */
	public static Boolean phoneVerification(String phone) {
		if (null == phone || phone.length() != 11) {
			return false;
		}
		String regex = "[0-9]*";
		Matcher m = Pattern.compile(regex).matcher(phone);
		return m.matches();
	}
	/**
	 * 手机号隐藏4位显示
	 * @param phone
	 * @return
	 */
	public static String phoneNoShow(String phone) {
		if (null == phone || phone.length() != 11) {
			return "";
		}
		return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
	}
	
	/**
	 * 银行卡号隐藏8位显示
	 * @param bankCartNo
	 * @return
	 */
	public static String bankCartNoShow(String cardNo) {
		 if(StringUtils.isBlank(cardNo)) {
	          return cardNo;
	      }

        int length = cardNo.length();
        int beforeLength = 4;
        int afterLength = 4;
        //替换字符串，当前使用“*”
        String replaceSymbol = "*";
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<length; i++) {
            if(i < beforeLength || i >= (length - afterLength)) {
                sb.append(cardNo.charAt(i));
            } else {
                sb.append(replaceSymbol);
            }
        }

        return sb.toString();
	}


	/**
	 * 银行卡号隐藏8位显示
	 * @param bankCartNo
	 * @return
	 */
	public static String accountNoShow(String account) {
		if(StringUtils.isBlank(account)) {
			return account;
		}

		int length = account.length();
//		int beforeLength = 1;
//		int afterLength = 4;
		//替换字符串，当前使用“*”
		String replaceSymbol = "*";
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<length; i++) {
			if(i > 4) {
				sb.append(account.charAt(i));
			} else {
				sb.append(replaceSymbol);
			}
		}

		return sb.toString();
	}

	/**
	 * 银行卡号检验
	 * 
	 * @param cardNum
	 * @return
	 */
	public static Boolean bankCardVerification(String cardNum) {
		if (null == cardNum || cardNum.length() < 16 || cardNum.length() > 19) {
			return false;
		}
		String regex = "[0-9]*";
		Matcher m = Pattern.compile(regex).matcher(cardNum);
		return m.matches();
	}

	/**
	 * 真实姓名校验
	 * 
	 * @param cardNum
	 * @return
	 */
	public static Boolean userNameVerification(String userName) {
		if (null == userName || userName.length() < 2 || userName.length() > 20) {
			return false;
		}
		String regex = "^[A-Za-z\\u4e00-\\u9fa5]+$";
		Matcher m = Pattern.compile(regex).matcher(userName);
		return m.matches();
	}



//	/**
//	 * 真实姓名校验
//	 *
//	 * @param cardNum
//	 * @return
//	 */
//	public static Boolean nickNameVerification(String nickName) {
//		byte[] bytesStr = null;
//		try {
//			bytesStr = nickName.getBytes("gbk");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//			throw new RRException(ErrorCode.UserErrorCode.NICK_NAME_ERRO.getErrMsg(),
//					ErrorCode.UserErrorCode.NICK_NAME_ERRO.getCode());
//		}
//		if (null == nickName || bytesStr.length < 6 || bytesStr.length > 12) {
//			return false;
//		}
//		String regex = "^[A-Za-z\\u4e00-\\u9fa5]+$";
//		Matcher m = Pattern.compile(regex).matcher(nickName);
//		return m.matches();
//	}
	/**
	 * 支付宝账号校验
	 * 
	 * @param cardNum
	 * @return
	 */
	public static Boolean alipayAccountVerification(String alipayAccount) {
		if (null == alipayAccount || alipayAccount.length() < 2 || alipayAccount.length() > 30) {
			return false;
		}
		// 手机号
		String phoneRegex = "^1[3|4|5|8|6|7|9|2][0-9]\\d{8}$";
		Matcher phoneMatcher = Pattern.compile(phoneRegex).matcher(alipayAccount);
		if (!phoneMatcher.matches()) {
			String emialRegex = "\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}";
			Matcher emialMatcher = Pattern.compile(emialRegex).matcher(alipayAccount);
			return emialMatcher.matches();
		} else {
			return true;
		}
	}

	/**
	 * 用户头像校验
	 * 
	 * @param cardNum
	 * @return
	 */
	public static Boolean portraitVerification(String portrait) {
		if (null == portrait || portrait.length() < 2 || portrait.length() > 10) {
			return false;
		}
		return true;
	}

	public static boolean Verification(String o, String validationRule) {
		Matcher m = Pattern.compile(validationRule).matcher(o);
		return m.matches();
	}

    // public static void main(String[] args) {
	// System.out.println(userNameVerification("啊啊aaaa"));
	// }

	/**
	 * 验证用户名，支持中英文（包括全角字符）、数字、下划线和减号 （全角及汉字算两位）,长度为4-20位,中文按二位计数
	 *
	 * @param userName
	 * @return
	 */
	public static boolean nickNameVerification(String nickName) {
		String validateStr = "^[\\w\\-－＿[０-９]\u4e00-\u9fa5\uFF21-\uFF3A\uFF41-\uFF5A]+$";
		boolean rs = false;
		rs = matcher(validateStr, nickName);
		if (rs) {
			int strLenth = getStrLength(nickName);
			if (strLenth < 4 || strLenth > 12) {
				rs = false;
			}
		}
		return rs;
	}

	/**
	 * 获取字符串的长度，对双字符（包括汉字）按两位计数
	 *
	 * @param value
	 * @return
	 */
	public static int getStrLength(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		for (int i = 0; i < value.length(); i++) {
			String temp = value.substring(i, i + 1);
			if (temp.matches(chinese)) {
				valueLength += 2;
			} else {
				valueLength += 1;
			}
		}
		return valueLength;
	}

	private static boolean matcher(String reg, String string) {
		boolean tem = false;
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(string);
		tem = matcher.matches();
		return tem;
	}

	public static void main(String[] args) {
		String str = "半角最代码zu1";
		String st = "Ａ-ｄｑ_!!！！去符号标号！ノチセたのひちぬ！当然。!!..**半角最代码zuidaima.com";

		System.out.println(nickNameVerification(str));
		System.out.println(st.replaceAll("[\\pP&&[^-_]]", ""));
		System.out.println(st.replaceAll("[\\w\\-一-龥Ａ-Ｚａ-ｚ]", ""));
	}

}
