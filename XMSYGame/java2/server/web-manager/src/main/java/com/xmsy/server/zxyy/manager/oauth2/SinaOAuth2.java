package com.xmsy.server.zxyy.manager.oauth2;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.hutool.http.HttpUtil;

@Service("sinaOAuth2")
public class SinaOAuth2 extends OAuth2 {

	private static final Logger LOG = LoggerFactory.getLogger(SinaOAuth2.class);

	private static final String CLIENT_ID = "wxxxxxxxxxxxxxx";
	private static final String CLIENT_SERCRET = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private static final String REDIRECT_URI = "http://www.thinkool.com.cn/oauth/signin";
	/*----------------------------Oauth接口--------------------------------------*/

	@Override
	public String getAccessToken(String code) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("client_id", CLIENT_ID);
		paramMap.put("client_secret", CLIENT_SERCRET);
		paramMap.put("grant_type", "authorization_code");
		paramMap.put("code", code);
		paramMap.put("redirect_uri", REDIRECT_URI);
		//
		String url = "https://api.weibo.com/oauth2/access_token";
		// "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code&"+
		// "appid="+client_ID+
		// "&secret="+client_SERCRET+
		// "&code="+code+
		// "&state="+"wechat"+"&redirect_uri="+redirect_URI;
		try {
			// access_token & openid
			return HttpUtil.post(url, paramMap);
		} catch (Exception e) {
			LOG.error("[SinaOAuth2]->getAccessToken: code={}", code, e);
		}
		return null;
	}

	@Override
	public String getUser(String access_token, String openid) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("access_token", access_token);
		String getuser = "https://api.weibo.com/oauth2/get_token_info";
		// String getuser = "https://api.weixin.qq.com/sns/userinfo?"+
		// "access_token="+access_token+
		// "&openid="+oid;
		try {
			// access_token & openid
			return HttpUtil.post(getuser, paramMap);
		} catch (Exception e) {
			LOG.error("[SinaOAuth2]->getUser:access_token={},openid={}", access_token, openid, e);
		}
		return null;
	}

	@Override
	public boolean verificationAccessToken(String access_token, String openid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String refreshAccessToken(String refresh_token) {
		// TODO Auto-generated method stub
		return null;
	}
}
