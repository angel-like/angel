package com.xmsy.server.zxyy.game.oauth2;

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
	public abstract String getAccessToken(String code, String appid, String appSercret);

	/**
	 * 获取用户信息
	 * 
	 * @param access_token
	 * @param openId
	 * @param clientId
	 * @return
	 */
	public abstract String getUser(String access_token, String openId, String appid);

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
	 * @param refresh_token
	 * @param appid
	 * @return
	 */
	public abstract String refreshAccessToken(String refresh_token, String appid);

}
