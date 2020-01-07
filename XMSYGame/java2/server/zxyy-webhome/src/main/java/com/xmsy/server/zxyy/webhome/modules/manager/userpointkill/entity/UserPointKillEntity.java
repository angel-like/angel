package com.xmsy.server.zxyy.webhome.modules.manager.userpointkill.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 点杀名单
 * 
 * @author aye
 * @email xxxxx
 * @date 2019-11-22 11:27:03
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("user_point_kill")
public class UserPointKillEntity extends BaseEntity<UserPointKillEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 会员ID
	 */
    private Long userId;
			/**
	 * 会员账号
	 */
    private String userAccount;
			/**
	 * 点杀概率
	 */
    private BigDecimal pointKillRate;
			/**
	 * 解除退出金额
	 */
    private Long removeAmount;
    /**
	 * 剩余解除退出金额
	 */
	private Long remainAmount;
	/**
	 * 点杀操作人
	 */
	private String sysUserPointKill;
	/**
	 * 点杀备注
	 */
	private String pointKillRemake;
			/**
	 * 解除点杀操作人
	 */
    private String sysUserAccount;
    /**
	 * 操作时间
	 */
	private Date operationTime;
	/**
	 * 状态(0.正常,1:点杀）
	 */
	private Boolean pointKillEnable;
			/**
	 * 备注
	 */
    private String remake;
	}
