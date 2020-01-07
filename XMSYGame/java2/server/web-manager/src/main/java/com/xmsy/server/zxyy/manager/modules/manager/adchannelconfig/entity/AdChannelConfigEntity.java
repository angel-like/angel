package com.xmsy.server.zxyy.manager.modules.manager.adchannelconfig.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 渠道配置表
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-07-02 17:05:09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ad_channel_config")
public class AdChannelConfigEntity extends BaseEntity<AdChannelConfigEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 渠道名称
	 */
	private String channelName;
	/**
	 * 渠道代码
	 */
	private String channelCode;
	/**
	 * 渠道地址
	 */
	private String channelAddress;
	/**
	 * 发布地址
	 */
	private String publishUrl;
	/**
	 * 备注
	 */
	private String remake;
	/**
	 * ios下载地址
	 */
	@TableField(exist = false)
	private String iosDownLoadUrl;
	/**
	 * 安卓下载地址
	 */
	@TableField(exist = false)
	private String androidDownLoadUrl;
	/**
	 * ios下载地址
	 */
	@TableField(exist = false)
	private String iosUrl;
	/**
	 * 安卓下载地址
	 */
	@TableField(exist = false)
	private String androidUrl;
	/**
	 * 用户名
	 */
	@TableField(exist = false)
	private String user;
	/**
	 * 密码
	 */
	@TableField(exist = false)
	private String pwd;
	/**
	 * 登录地址
	 */
	@TableField(exist = false)
	private String loginUrl;
	/**
	 * 微信分享图片url
	 */
	@TableField(exist = false)
	private String shareWeixinUrl;
}
