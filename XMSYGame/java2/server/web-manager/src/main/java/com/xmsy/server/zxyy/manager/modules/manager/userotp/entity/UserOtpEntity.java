package com.xmsy.server.zxyy.manager.modules.manager.userotp.entity;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户动态码
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-06 10:55:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_otp")
public class UserOtpEntity {
	/**
	 * otp秘钥
	 */
	@TableId(type = IdType.AUTO)
	@TableField("id")
	private Long id;

	// 数据版本（乐观锁实现）
	@Version
	@JSONField(serialize = false)
	@TableField(value = "version")
	private Integer version;

	// 添加时间
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private Date createTime;

	// 修改时间
	@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
	@JSONField(serialize = false)
	private Date updateTime;

	@NotEmpty(message = "otp秘钥不能为空")
	private String otpSecret;
	/**
	 * 用户名
	 */
	@NotEmpty(message = "用户名不能为空")
	private String username;
	/**
	 * otp绑定的ip
	 */
	@NotEmpty(message = "绑定ip不能为空")
	private String bindIp;
}
