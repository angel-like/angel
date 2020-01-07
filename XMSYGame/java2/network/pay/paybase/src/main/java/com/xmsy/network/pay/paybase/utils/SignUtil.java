package com.xmsy.network.pay.paybase.utils;

import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.util.StringUtils;

/**
 * .签名工具
 *
 * @author aleng
 * @date 2018年12月3日
 * @version 1.0
 */
public class SignUtil {

	public static String generateOrderId() {
		String keyup_prefix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String keyup_append = String.valueOf(new Random().nextInt(899999) + 100000);
		String pay_orderid = keyup_prefix + keyup_append;// 订单号
		return pay_orderid;
	}

	public static String generateTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	/**
	 * 按照key的字母默认排序asc
	 *
	 * @param map
	 *            需要排序的map
	 * @return 生成结果string
	 */
	public static String sortSign(TreeMap<String, String> map) {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String value = entry.getValue();
			if (!StringUtils.isEmpty(value)) {
				sb.append(String.format("%s=%s", entry.getKey(), value));
				sb.append("&");
			}
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 按照key的字母默认排序asc
	 *
	 * @param map
	 *            需要排序的map
	 * @return 生成结果string
	 */
	public static String sortSignTreeMap(TreeMap<String, Object> map) {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			Object value = entry.getValue();
			if (null != value && !StringUtils.isEmpty(value.toString())) {
				sb.append(String.format("%s=%s", entry.getKey(), value));
				sb.append("&");
			}
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
	/**
	 * 生成签名
	 * @param map
	 * @return
	 */
	public static String getSign(Map<String, Object> map) {

		String result = "";
		try {
			List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(map.entrySet());
			// 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
			Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {

				public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
					return (o1.getKey()).toString().compareTo(o2.getKey());
				}
			});

			// 构造签名键值对的格式
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, Object> item : infoIds) {
				if (item.getKey() != null || item.getKey() != "") {
					String key = item.getKey();
					if (item.getValue()!=null && !"".equals(item.getValue().toString())) {
						sb.append(key).append("=").append(item.getValue()).append("&");
					}
				}

			}
//			sb.append(PropertyManager.getProperty("SIGNKEY"));
			result = sb.toString();
			//进行MD5加密
//			result = DigestUtils.md5Hex(result).toUpperCase();
		} catch (Exception e) {
			return null;
		}
		return result;


	}
}
