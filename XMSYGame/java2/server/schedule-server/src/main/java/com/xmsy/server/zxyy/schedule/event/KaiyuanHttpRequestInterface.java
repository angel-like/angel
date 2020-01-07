package com.xmsy.server.zxyy.schedule.event;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.utils.EncryptUtil;
import com.xmsy.server.zxyy.schedule.utils.HallUrlConstant;
import lombok.extern.slf4j.Slf4j;


/**
 * 请求开元的接口
 * 
 * @author adu
 * @date 2019-02-26
 */
 @Slf4j
public class KaiyuanHttpRequestInterface {

	/**
	 * 请求开元api
	 */
	public static JSONObject sendToKaiyuan(String sb,String handler) throws  Exception {
		String param = EncryptUtil.AESEncrypt(sb, HallUrlConstant.getDESKEY());

		Long timestamp =System.currentTimeMillis();
		String key = EncryptUtil.MD5(HallUrlConstant.getAGENT()+ timestamp +HallUrlConstant.getMD5KEY());


		String url = HallUrlConstant.getKyUrl();

        StringBuilder sb1 =  new StringBuilder(url).append(handler);
        sb1.append("?agent="+HallUrlConstant.getAGENT());
        sb1.append("&timestamp=" + timestamp);
        sb1.append("&param="+param);
        sb1.append("&key="+key);
		System.out.println(sb1.toString());

		String result = HttpRequest.get(sb1.toString()).timeout(30000).execute().body();

		 log.info("[gameLogin] returnObj {}", result);
		return JSON.parseObject(result);
	}

	public static JSONObject sendToKaiyuan(String sb) throws  Exception {
		String param = EncryptUtil.AESEncrypt(sb,HallUrlConstant.getDESKEY());

		Long timestamp =System.currentTimeMillis();
		String key = EncryptUtil.MD5(HallUrlConstant.getAGENT()+ timestamp +HallUrlConstant.getMD5KEY());


		String url = HallUrlConstant.getRECORDURL();
//		Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 1080));
		StringBuilder sb1 =  new StringBuilder(url);
		sb1.append("?agent="+HallUrlConstant.getAGENT());
		sb1.append("&timestamp=" + timestamp);
		sb1.append("&param="+param);
		sb1.append("&key="+key);
		System.out.println(sb1.toString());

		String result = HttpRequest.get(sb1.toString()).timeout(30000)
				.execute()
				.body();

		log.info("[gamerecord] returnObj {}", result);
		return JSON.parseObject(result);
	}
}
