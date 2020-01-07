package com.xmsy.common.bean.message;

/**
 * 注册消息
 * 
 * @author Administrator
 *
 */
public class RegisterMessage extends BaseMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 用户账号
	 */
	private String userAccount;
	/**
	 * 用户登陆token
	 */
	private String token;
	/**
	 * 用户id
	 */
	private String ip;
	/**
	 * ip地址
	 */
	private String ipAddress;
	/**
	 * 设备码
	 */
	private String registerDeviceCode;
	/**
	 * 设备类型
	 */
	private String deviceType;
	/**
	 * 登入状态
	 */
	private String loginStatus = "success";
	/**
	 * 大厅id
	 */
	private Long hallId;
	/**
	 * 邀请码
	 */
	private String invitationCode;
	/**
	 * 版本号
	 */
	private String edition;

	public RegisterMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RegisterMessage(Long userId, String userAccount, String ip, String deviceType, String deviceCode,
			String invitationCode, Long hallId, String edition) {
		super();
		this.userId = userId;
		this.userAccount = userAccount;
		this.ip = ip;
		this.deviceType = deviceType;
		this.registerDeviceCode = deviceCode;
		this.invitationCode = invitationCode;
		this.hallId = hallId;
		this.edition = edition;
	}

	public RegisterMessage(Long userId, String userAccount, String ip, String deviceType, String invitationCode,
			Long hallId) {
		super();
		this.userId = userId;
		this.userAccount = userAccount;
		this.ip = ip;
		this.deviceType = deviceType;
		this.invitationCode = invitationCode;
		this.hallId = hallId;

	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRegisterDeviceCode() {
		return registerDeviceCode;
	}

	public void setRegisterDeviceCode(String registerDeviceCode) {
		this.registerDeviceCode = registerDeviceCode;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public Long getHallId() {
		return hallId;
	}

	public void setHallId(Long hallId) {
		this.hallId = hallId;
	}

	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Override
	public String toString() {
		return "RegisterMessage [userId=" + userId + ", userAccount=" + userAccount + ", token=" + token + ", ip=" + ip
				+ ", ipAddress=" + ipAddress + ", registerDeviceCode=" + registerDeviceCode + ", deviceType="
				+ deviceType + ", loginStatus=" + loginStatus + ", hallId=" + hallId + ", invitationCode="
				+ invitationCode + ", edition=" + edition + "]";
	}


}
