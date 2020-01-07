package com.xmsy.server.zxyy.webhome.modules.web.login.entity;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.xmsy.server.zxyy.webhome.common.validator.group.AddGroup;

import lombok.Data;


/**
 * 注册
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
@Data
public class PhoneRegisterEntity  {
	
	/**
	 * 账号名称
	 */
	@NotBlank(message = "账号不能为空", groups = AddGroup.class)
    private String account;
			/**
	 * 登陆密码
	 */
    @NotBlank(message = "登陆密码不能为空", groups = AddGroup.class)
    private String loginPassWord;

	/**
	 * 确认密码
	 */
    @NotBlank(message = "确认密码不能为空", groups = AddGroup.class)
	private String verificationWord;
   
    /**
	 * 取款密码
	 */
    @NotBlank(message = "取款密码不能为空", groups = AddGroup.class)
	private String takeMoneyPassWord;
    /**
	 * 真实姓名
	 */
    @NotBlank(message = "真实姓名不能为空", groups = AddGroup.class)
	private String userName;
    /**
	 * 手机验证码
	 */
    @NotBlank(message = "手机验证码不能为空", groups = AddGroup.class)
	private String code;
	/**
	 * 邀请码
	 */
	private String invitationCode;
	/**
	 * 设备码
	 */
	private String registerDeviceCode;
	/**
	 * ip
	 */
	private String registerIp;
	/**
	 * 设备类型
	 */
	private String deviceType;
	
	
		
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
	* 性别
	* 0=false=女
	* 1=true=男
	*/
	private Integer sex;
	
	/**
	 * 版本号
	 */
	private Long hallId;
	
	/**
	 * 版本号
	 */
	private String edition;
	/**
	 * 推送id
	 */
	private String jpushRegId;
	
	}
