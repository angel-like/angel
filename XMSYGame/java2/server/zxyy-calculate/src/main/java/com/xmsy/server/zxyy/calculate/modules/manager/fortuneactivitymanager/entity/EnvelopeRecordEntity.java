package com.xmsy.server.zxyy.calculate.modules.manager.fortuneactivitymanager.entity;


import com.baomidou.mybatisplus.annotations.TableName;

import com.xmsy.server.zxyy.calculate.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 天降财神红包记录
 *
 * @author ahui
 * @email sunlightcs@gmail.com
 * @date 2019-12-12 11:38:04
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("envelope_record")
public class EnvelopeRecordEntity extends BaseEntity<EnvelopeRecordEntity> {
	private static final long serialVersionUID = 1L;


	/**
	 * 玩家id
	 */
	private Long userId;
	/**
	 * 玩家名称
	 */
	private String userAccount;
	/**
	 * 开启时间
	 */
	private Date openTime;
	/**
	 * 开启数量
	 */
	private Integer openNum;

	/**
	 * 获得的金币：玩家一次性消耗红包个数获得的金币数
	 */
	private Long receiveCoin;
	/**
	 * 开启前金币：玩家开启红包前自身携带的金币
	 */
	private Long beforeOpenCoin;
	/**
	 * 开启后金币：玩家开启红包后自身携带的金币
	 */
	private Long afterOpenCoin;
	/**
	 * 是否开启 0-未开启  1-已开启
	 */
	private Integer status;
	/**
	 * 天降财神 活动id
	 */
	private Long activityId;
	/**
	 * 天降财神 事件id
	 */
	private Long eventId;
}
