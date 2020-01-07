package com.xmsy.server.zxyy.calculate.modules.manager.userinfo.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.calculate.base.BaseEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.user.param.UserOperater;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户基本信息表
 * 
 * @author lpp
 * @email xxxxx
 * @date 2019-01-15 10:49:21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_info")
public class UserInfoEntity extends BaseEntity<UserInfoEntity> {
	private static final long serialVersionUID = 1L;
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
	 * 是否在中国注册(0.否,1:是）
	 */
	private Boolean userAddressStatus;
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
	 * 阿里账号
	 */
	private String alipayAccount;
	/**
	 * 银行卡开户名
	 */
	private String bankAccountName;
	/**
	 * 用户id（唯一）
	 */
	private Long userId;

	/**
	 * 用户推送id
	 */
	private String jpushRegId;

	/**
	 * 操作内容
	 */
	@TableField(exist = false)
	private UserOperater userOperater;
}
