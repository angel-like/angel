package com.xmsy.server.zxyy.webhome.modules.manager.userlogin.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户登陆记录表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-21 16:01:51
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_login")
public class UserLoginEntity extends BaseEntity<UserLoginEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 上次登入时间
	 */
	private Date lastLoginTime;
	/**
	 * 上次登入大厅id
	 */
	private Long lastHallId;
	/**
	 * 大厅id
	 */
	private Long hallId;
	/**
	 * 上次登入ip
	 */
	private String lastIp;
	/**
	 * 登陆ip
	 */
	private String ip;
	/**
	 * 上次登入设备号
	 */
	private String lastDeviceCode;
	/**
	 * 机器码
	 */
	private String deviceCode;
	/**
	 * 上次登入设备类型
	 */
	private String lastDeviceType;
	/**
	 * 设备类型
	 */
	private String deviceType;
	/**
	 * 地区
	 */
	private String region;
	/**
	 * 上次登入国家
	 */
	private String lastNation;
	/**
	 * 国家
	 */
	private String nation;
	/**
	 * 上次登入地理位置
	 */
	private String lastIpAddress;
	/**
	 * 地理位置
	 */
	private String ipAddress;
	/**
	 * 域名
	 */
	private String domain;
	/**
	 * 上次登入版本号
	 */
	private String lastEdition;
	/**
	 * 版本号
	 */
	private String edition;
	/**
	 * 浏览器版本
	 */
	private String browser;
	/**
	 * 登陆状态
	 */
	private String loginStatus;
	/**
	 * 登陆时token
	 */
	private String token;
	/**
	 * 登陆账号
	 */
	@TableField(exist = false)
	private String account;
	/**
	 * 开始时间
	 */
	@TableField(exist = false)
	private String startTime;
	/**
	 * 结尾时间
	 */
	@TableField(exist = false)
	private String endTime;
}
