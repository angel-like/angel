package com.xmsy.server.zxyy.webhome.modules.app.login.param;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 注册
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RegisterParams{

	/**
	 * 账号名称
	 */
	@NotBlank(message = "账号不能为空")
	private String account;
	/**
	 * 登陆密码
	 */
	@NotBlank(message = "登陆密码不能为空")
	private String loginPassWord;

	/**
	 * 确认密码
	 */
	@NotBlank(message = "确认密码不能为空")
	private String verificationWord;

	/**
	 * 取款密码
	 */
	private String takeMoneyPassWord;
	/**
	 * 真实姓名
	 */
	private String userName;
	/**
	 * ip
	 */
	private String registerIp;
	/**
	 * ip
	 */
	private String registerAddress;

	/*
	 * userInfo
	 */
	/**
	 * 头像
	 */
	private String portrait;
	/**
	 * 身份证号码
	 */
	private String userIdentity;
	/**
	 * 电话
	 */
	private String userPhone;
	/**
	 * 电子邮箱
	 */
	private String userEmail;
	/**
	 * 邮编
	 */
	private String postCode;
	/**
	 * 地址
	 */
	private String userAddress;
	/**
	 * QQ
	 */
	private String userQq;
	/**
	 * 生日
	 */
	private Date userBirthday;
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 银行开户地址
	 */
	private String bankAddress;
	/**
	 * 银行卡号
	 */
	private String bankCard;
	/**
	 * 银行卡开户名
	 */
	private String bankAccountName;
	/**
	 * 性别 0=false=女 1=true=男
	 */
	private Integer sex;


	
	
	/**
	 * 短信验证码
	 */
	private String code;
	
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * unionType
	 */
	private String unionType;
	/**
	 * openId
	 */
	private String openId;
	/**
	 * unionId
	 */
	private String unionId;

	private  BaseLoginParam commonParam;

}
