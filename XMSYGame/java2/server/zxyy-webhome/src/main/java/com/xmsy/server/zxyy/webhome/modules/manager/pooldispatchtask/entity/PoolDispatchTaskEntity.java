package com.xmsy.server.zxyy.webhome.modules.manager.pooldispatchtask.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 
 * 
 * @author lpp
 * @email xxxxx
 * @date 2019-01-21 09:46:00
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("pool_dispatch_task")
public class PoolDispatchTaskEntity extends BaseEntity<PoolDispatchTaskEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 标题
	 */
    private String title;
	/**
	 * 总金额
	 */
    private BigDecimal amount;
	/**
	 * 奖池id
	 */
    private Long poolGameId;
    /**
     * 排行榜单id
     */
    private Long rankingListId;
	/**
	 * 状态 0：发起 1：成功 2：失败
	 */
    private Integer status;
    /**
     * 状态  1：日排行 2：周排行
     */
    private Integer type;
	}
