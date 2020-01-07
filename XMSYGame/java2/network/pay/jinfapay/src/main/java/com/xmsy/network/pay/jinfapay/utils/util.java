package com.xmsy.network.pay.jinfapay.utils;

import java.security.MessageDigest;

public class util {
	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	
	//签名生成
	public static String paySign(String pay_order,String order_id,String price,String pay_type,String qrcode_url,String apikey){
		StringBuilder stringb = new StringBuilder();
		stringb.append("pay_order=").append(pay_order).append("&")
				.append("order_id=").append(order_id).append("&")
				.append("price=").append(price).append("&")
				.append("pay_type=").append(pay_type).append("&")
				.append("qrcode_url=").append(qrcode_url).append("&")
				.append("code=").append("00").append("&")
				.append("key=").append(apikey);
		String sign = MD5(stringb.toString(), "utf-8");
		System.out.println("金发同步返回签名前："+stringb.toString()+"，签名后："+sign);
		return sign;
	}
	
	public static String querySign(String pay_order,String order_id,String price,String code,String timestamp,String apikey){
		StringBuilder stringb = new StringBuilder();
		stringb.append("pay_order=").append(pay_order).append("&")
				.append("order_id=").append(order_id).append("&")
				.append("price=").append(price).append("&")
				.append("code=").append(code).append("&")
				.append("timestamp=").append(timestamp).append("&")
				.append("key=").append(apikey);
		String sign = MD5(stringb.toString(), "utf-8");
		System.out.println("金发同步返回签名前："+stringb.toString()+"，签名后："+sign);
		return sign;
	}
	/**
	 * 32位md5加密算法
	 * @param s
	 * @param encoding
	 * @return
	 */
	public final static String MD5(String s, String encoding) { 
		try {
			byte[] btInput = s.getBytes(encoding);
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = HEX_DIGITS[byte0 >>> 4 & 0xf];
				str[k++] = HEX_DIGITS[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}	
			
	/**
	 * Unicode转中文
	 * @param unicode
	 * @return
	 */
	public static String unicodeToCn(String unicode) {
        /** 以 \ u 分割，因为java注释也能识别unicode，因此中间加了一个空格*/
        String[] strs = unicode.split("\\\\u");
        String returnStr = "";
        // 由于unicode字符串以 \ u 开头，因此分割出的第一个字符是""。
        for (int i = 1; i < strs.length; i++) {
          returnStr += (char) Integer.valueOf(strs[i], 16).intValue();
        }
        return returnStr;
    }
}
