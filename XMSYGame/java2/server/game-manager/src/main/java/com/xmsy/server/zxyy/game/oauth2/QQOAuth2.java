package com.xmsy.server.zxyy.game.oauth2;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.hutool.http.HttpUtil;

@Service("qqOAuth2")
public class QQOAuth2 extends OAuth2 {

	private static final Logger LOG = LoggerFactory.getLogger(QQOAuth2.class);

	private static final String CLIENT_ID = "wxxxxxxxxxxxxxx";
	private static final String CLIENT_SERCRET = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private static final String REDIRECT_URI = "http://www.thinkool.com.cn/oauth/signin";
	/*----------------------------Oauth接口--------------------------------------*/

	@Override
	public String getAccessToken(String code, String clientId, String clientSercret) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("client_id", CLIENT_ID);
		paramMap.put("client_secret", CLIENT_SERCRET);
		paramMap.put("grant_type", "authorization_code");
		paramMap.put("code", code);
		paramMap.put("redirect_uri", REDIRECT_URI);
		//
		String url = "https://graph.qq.com/oauth2.0/token";
		// "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code&"+
		// "appid="+client_ID+
		// "&secret="+client_SERCRET+
		// "&code="+code+
		// "&state="+"wechat"+"&redirect_uri="+redirect_URI;
		try {
			// access_token & openid
			return HttpUtil.get(url, paramMap);
		} catch (Exception e) {
			LOG.error("[QQOAuth2]->getAccessToken: code={}", code, e);
		}
		return null;
	}

	public String getOpenId(String access_token) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("access_token", access_token);
		//
		String url = "https://graph.qq.com/oauth2.0/me";
		// "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code&"+
		// "appid="+client_ID+
		// "&secret="+client_SERCRET+
		// "&code="+code+
		// "&state="+"wechat"+"&redirect_uri="+redirect_URI;
		try {
			// access_token & openid
			return HttpUtil.get(url, paramMap);
		} catch (Exception e) {
			LOG.error("[QQOAuth2]->getOpenId: access_token={}", access_token, e);
		}
		return null;
	}

	@Override
	public String getUser(String access_token, String openid, String clientId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("access_token", access_token);
		paramMap.put("openid", openid);
		paramMap.put("oauth_consumer_key", clientId);
		String getuser = "https://graph.qq.com/user/get_user_info";
		// String getuser = "https://api.weixin.qq.com/sns/userinfo?"+
		// "access_token="+access_token+
		// "&openid="+oid;
		try {
			// access_token & openid
			return HttpUtil.get(getuser, paramMap);
		} catch (Exception e) {
			LOG.error("[QQOAuth2]->getUser:access_token={},openId={}", access_token, openid, e);
		}
		return null;
	}

	@Override
	public boolean verificationAccessToken(String access_token, String openid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String refreshAccessToken(String refresh_token, String appid) {
		// TODO Auto-generated method stub
		return null;
	}

}
