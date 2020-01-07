package com.xmsy.server.zxyy.webhome.modules.app.login.param;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 第三方注册参数
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-07-16 10:00:00
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ThirdRegisterParams {

	/**
	 * 账号名称
	 */
	@NotBlank(message = "账号不能为空")
	private String account;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * unionType
	 */
	@NotBlank(message = "平台类型不能为空")
	private String unionType;
	/**
	 * openId
	 */
	@NotBlank(message = "openId不能为空")
	private String openId;
	/**
	 * unionId
	 */
	private String unionId;
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
	@NotNull(message = "性别不能为空")
	private Integer sex;

	
	/**
	 * 短信验证码
	 */
	@NotBlank(message = "验证码不能为空")
	private String code;

	private  BaseLoginParam commonParam;

}
