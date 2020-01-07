package com.xmsy.server.zxyy.manager.modules.manager.userinfoold.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户信息表
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-06-22 15:56:39
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("user_info_old")
public class UserInfoOldEntity extends BaseEntity<UserInfoOldEntity> {
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
	 * 用户id（唯一）
	 */
    private Long userId;
			/**
	 * 支付宝账号
	 */
    private String alipayAccount;
			/**
	 * 用户推送id
	 */
    private String jpushRegId;
	}
