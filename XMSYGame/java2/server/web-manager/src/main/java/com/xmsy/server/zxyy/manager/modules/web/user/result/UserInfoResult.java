package com.xmsy.server.zxyy.manager.modules.web.user.result;

import lombok.Data;

/**
 * .用户密码信息
 * 
 * @author Administrator
 *
 */
@Data
public class UserInfoResult {

	// 用户ID
	private Long userId;
	// 真实姓名
	private String userName;
	// 账号
	private String account;
	// 银行名称
    private String bankName;
    // 银行卡号
    private String bankCard;
    // 阿里账号
    private String alipayAccount;
    // 银行卡开户名
    private String bankAccountName;
    // 银行卡开户地址
    private String bankAddress;

}
