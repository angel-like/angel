package com.xmsy.server.zxyy.calculate.modules.manager.fortuneactivitymanager.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import com.xmsy.server.zxyy.calculate.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 天降财神活动配置
 *
 * @author ahui
 * @email sunlightcs@gmail.com
 * @date 2019-12-12 11:38:04
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("fortune_activi_config")
public class FortuneActiviConfigEntity extends BaseEntity<FortuneActiviConfigEntity> {
	private static final long serialVersionUID = 1L;


	/**
	 * 活动名称
	 */
	private String name;
	/**
	 * 活动详情：活动规则介绍
	 */
	private String detail;
	/**
	 * 开启时间：活动开启时间，精确到时分秒00：00：00。修改开启时间时，若开启时间没有大于等于当前时间，则按当前时间设置
	 */
	private Date startTime;
	/**
	 * 结束时间：活动结束时间，精确到时分秒00：00：00。活动结束前1小时，发送全服邮件提醒玩家：“活动还有1小时结束，未使用的红包将被清空，请及时使用！”
	 */
	private Date endTime;
	/**
	 * 奖励上限：红包奖励的最高金额
	 */
	private Long maxnumReward;
	/**
	 * 奖励下限：红包奖励的最低金额，值需大于等于0.01
	 */
	private Long minnumReward;

}
