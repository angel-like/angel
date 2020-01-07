package com.xmsy.server.zxyy.webhome.oauth2;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

/**
 * .第三方认证工具类
 * 
 * @author aleng
 * @since 2018年12月29日
 */
@Component
public class OAuth2Utils {

	public static final String WECHAT = "1";
	public static final String QQ = "2";
	public static final String SINA = "3";

	@Resource
	private QQOAuth2 qqOAuth2;
	@Resource
	private SinaOAuth2 sinaOAuth2;
	@Resource
	private WechatOAuth2 wechatOAuth2;

	public OAuth2 getOAuth2(String type) {
		switch (type) {
		case QQ:
			return qqOAuth2;
		case SINA:
			return sinaOAuth2;
		case WECHAT:
			return wechatOAuth2;
		}
		return null;
	}

}
