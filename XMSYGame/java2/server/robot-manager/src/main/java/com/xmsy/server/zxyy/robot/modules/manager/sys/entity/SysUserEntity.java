package com.xmsy.server.zxyy.robot.modules.manager.sys.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.robot.common.validator.group.AddGroup;
import com.xmsy.server.zxyy.robot.common.validator.group.UpdateGroup;

import lombok.Data;

/**
 * 系统用户
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年9月18日 上午9:28:55
 */
@Data
@TableName("sys_user")
public class SysUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@TableId
	private Long userId;

	/**
	 * 用户名
	 */
	@NotBlank(message = "用户名不能为空", groups = { AddGroup.class, UpdateGroup.class })
	private String username;

	/**
	 * 密码
	 */
	@NotBlank(message = "密码不能为空", groups = AddGroup.class)
	private String password;

	/**
	 * 盐
	 */
	private String salt;

	/**
	 * 邮箱
	 */
	@NotBlank(message = "邮箱不能为空", groups = { AddGroup.class, UpdateGroup.class })
	@Email(message = "邮箱格式不正确", groups = { AddGroup.class, UpdateGroup.class })
	private String email;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 状态 0：禁用 1：正常
	 */
	private Integer status;

	/**
	 * 角色ID列表
	 */
	@TableField(exist = false)
	private List<Long> roleIdList;

	/**
	 * 创建者ID
	 */
	private Long createUserId;

	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 角色ID字符串拼接
	 */
	private String roleIds;

	/**
	 * 代理商秘钥
	 */
	private String token;
}
