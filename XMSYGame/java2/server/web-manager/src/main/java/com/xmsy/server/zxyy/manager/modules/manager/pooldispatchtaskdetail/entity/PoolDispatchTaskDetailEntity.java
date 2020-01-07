package com.xmsy.server.zxyy.manager.modules.manager.pooldispatchtaskdetail.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 派奖奖项明细表
 * 
 * @author lpp
 * @email xxxxx
 * @date 2019-01-21 09:46:00
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("pool_dispatch_task_detail")
public class PoolDispatchTaskDetailEntity extends BaseEntity<PoolDispatchTaskDetailEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 奖项名称
	 */
    private String title;
			/**
	 * 人数
	 */
    private Integer num;
			/**
	 * 比例
	 */
    private BigDecimal rate;
			/**
	 * 任务ID
	 */
    private Long taskId;
			/**
	 * 指定派奖人id
	 */
    private String userids;
			/**
	 * 层级id集合
	 */
    private String hierarchyIds;
					}
