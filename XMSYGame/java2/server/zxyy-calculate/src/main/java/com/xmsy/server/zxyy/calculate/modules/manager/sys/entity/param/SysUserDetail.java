package com.xmsy.server.zxyy.calculate.modules.manager.sys.entity.param;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 系统用户
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年9月18日 上午9:28:55
 */
@Data
public class SysUserDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 邮箱
	 */
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
	 * 角色ids(逗号分隔符分开)
	 */
	private String roleIds;
	/**
	 * 角色id
	 */
	private Long roleId;
	/**
	 * 角色名称列表
	 */
	private String roleNames;

	/**
	 * 创建者ID
	 */
	private Long createUserId;

	/**
	 * 创建时间
	 */
	private Date createTime;
}
