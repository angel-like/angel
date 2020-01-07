package com.xmsy.server.zxyy.manager.modules.manager.fortuneactivitymanager.entity;



import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 红包任务后台配置
 *
 * @author ahui
 * @email sunlightcs@gmail.com
 * @date 2019-12-12 11:38:04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("envelope_task_config")
public class EnvelopeTaskConfigEntity extends BaseEntity<EnvelopeTaskConfigEntity> {
	private static final long serialVersionUID = 1L;


	/**
	 * 事件触发红包奖励的事件方法
	 */
	private String eventCode;
	/**
	 * 事件名称
	 */
	private String eventName;
	/**
	 * 活动id
	 */
	private Long activityId;
	/**
	 * 事件参数值 达到多少值才算完成这一次事件，默认0，值需大于等于0
	 */
	private Integer eventParam;

	/**
	 * 奖励数量 完成事件时奖励的红包个数，整数，默认1，值需大于等于1
	 */
	private Integer rewards;

}
