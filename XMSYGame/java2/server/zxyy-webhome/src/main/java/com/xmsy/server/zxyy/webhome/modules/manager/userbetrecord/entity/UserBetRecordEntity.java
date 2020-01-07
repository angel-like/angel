package com.xmsy.server.zxyy.webhome.modules.manager.userbetrecord.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户每日有效下注
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-06-08 10:51:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_bet_record")
public class UserBetRecordEntity extends BaseEntity<UserBetRecordEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 会员账号
	 */
	private String userAccount;
	/**
	 * 有效下注
	 */
	private Long betCoins;
	/**
	 * 历史有效下注，在充值时的有效下注
	 */
	private Long oldBet;
	/**
	 * 是否GM会员
	 */
	private Boolean gmUser;
}
