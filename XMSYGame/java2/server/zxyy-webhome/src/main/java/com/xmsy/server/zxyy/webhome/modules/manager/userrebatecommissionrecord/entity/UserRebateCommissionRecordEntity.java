package com.xmsy.server.zxyy.webhome.modules.manager.userrebatecommissionrecord.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户佣金返利记录
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-06-12 14:38:44
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_rebate_commission_record")
public class UserRebateCommissionRecordEntity extends BaseEntity<UserRebateCommissionRecordEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 用户账号
	 */
	private String userAccount;
	/**
	 * 佣金
	 */
	private BigDecimal commission;
	/**
	 * 供应佣金的用户ID
	 */
	private Long provideUserId;
	/**
	 * 供应佣金的用户账号
	 */
	private String provideUserAccount;
	/**
	 * 记录日期
	 */
	private Date recordDate;
}
