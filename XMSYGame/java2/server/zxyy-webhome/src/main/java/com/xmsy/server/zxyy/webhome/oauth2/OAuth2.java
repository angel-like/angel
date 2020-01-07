package com.xmsy.server.zxyy.webhome.oauth2;

/**
 * .第三方登入
 * 
 * @author aleng
 * @since 2018年12月29日
 */
public abstract class OAuth2 {

	/**
	 * 获取access_token
	 * 
	 * @param code
	 * @param clientId
	 * @param clientSercret
	 * @return
	 */
	public abstract String getAccessToken(String code);

	/**
	 * 获取用户信息
	 * 
	 * @param access_token
	 * @param openId
	 * @return
	 */
	public abstract String getUser(String access_token, String openid);

	/**
	 * access_token校验
	 * 
	 * @param access_token
	 * @param openid
	 * @return
	 */
	public abstract boolean verificationAccessToken(String access_token, String openid);

	/**
	 * 更新access_token
	 * 
	 * @param refresh_token
	 * @param appid
	 * @return
	 */
	public abstract String refreshAccessToken(String refresh_tokend);

}
