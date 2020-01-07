package com.xmsy.server.zxyy.manager.modules.manager.userblacklist.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户白名单表
 * 
 * @author lzy
 * @email xxxxx
 * @date 2019-01-15 10:50:25
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("user_blacklist")
public class UserBlacklistEntity extends BaseEntity<UserBlacklistEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 用户id
	 */
    private Long userId;
			/**
	 * 用户账号
	 */
    private String userAccount;
			/**
	 * 黑白名单0:黑名单
	 */
    private Boolean type;
			/**
	 * 备注
	 */
    private String remark;
	}
