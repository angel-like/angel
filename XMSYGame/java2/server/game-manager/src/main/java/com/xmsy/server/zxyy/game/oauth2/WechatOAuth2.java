package com.xmsy.server.zxyy.game.oauth2;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.define.constant.ResultDef;

import cn.hutool.http.HttpUtil;

@Service("wechatOAuth2")
public class WechatOAuth2 extends OAuth2 {

	private static final Logger LOG = LoggerFactory.getLogger(WechatOAuth2.class);

	/*----------------------------Oauth接口--------------------------------------*/

	@Override
	public String getAccessToken(String code, String appid, String appSercret) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("appid", appid);
		paramMap.put("secret", appSercret);
		paramMap.put("grant_type", "authorization_code");
		paramMap.put("code", code);
		// paramMap.put("redirect_uri", REDIRECT_URI);
		//

		String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
		// "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code&"+
		// "appid="+client_ID+
		// "&secret="+client_SERCRET+
		// "&code="+code+
		// "&state="+"wechat"+"&redirect_uri="+redirect_URI;
		try {
			// access_token & openid
			return HttpUtil.get(url, paramMap);
		} catch (Exception e) {
			LOG.error("[WechatOAuth2]->getAccessToken: code={}", code, e);
		}
		return null;
	}

	@Override
	public String getUser(String access_token, String openid, String clientId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("access_token", access_token);
		paramMap.put("openid", openid);
		String url = "https://api.weixin.qq.com/sns/userinfo";
		// String getuser = "https://api.weixin.qq.com/sns/userinfo?"+
		// "access_token="+access_token+
		// "&openid="+oid;
		try { // access_token & openid
			return HttpUtil.get(url, paramMap);
		} catch (Exception e) {
			LOG.error("[WechatOAuth2]->getUser:access_token={},openid={}", access_token, openid, e);
		}
		return null;
	}

	@Override
	public boolean verificationAccessToken(String access_token, String openid) {
		String url = "https://api.weixin.qq.com/sns/auth";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("access_token", access_token);
		paramMap.put("openid", openid);
		try {
			JSONObject response = JSON.parseObject(HttpUtil.get(url, paramMap));
			if (ResultDef.OAUTH_SUCCESS == response.getIntValue("errcode")) {
				return true;
			}
		} catch (Exception e) {
			LOG.error("[WechatOAuth2]->verificationAccessToken:access_token={},openid={}", access_token, openid, e);
		}
		return false;
	}

	@Override
	public String refreshAccessToken(String refresh_token, String appid) {
		String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("refresh_token", refresh_token);
		paramMap.put("appid", appid);
		paramMap.put("grant_type", refresh_token);
		try {
			return HttpUtil.get(url, paramMap);
		} catch (Exception e) {
			LOG.error("[WechatOAuth2]->refreshAccessToken:refresh_token={},appid={}", refresh_token, appid, e);
		}
		return null;
	}
}
