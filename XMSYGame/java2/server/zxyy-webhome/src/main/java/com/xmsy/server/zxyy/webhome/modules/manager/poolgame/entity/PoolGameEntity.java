package com.xmsy.server.zxyy.webhome.modules.manager.poolgame.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 游戏奖池表
 * 
 * @author lpp
 * @email xxxxx
 * @date 2019-01-21 09:46:00
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("pool_game")
public class PoolGameEntity extends BaseEntity<PoolGameEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 奖金
	 */
    private BigDecimal pool;
			/**
	 * 状态（0：禁用  1：启用）
	 */
    private Boolean enable;
			/**
	 * 游戏id
	 */
    private Long gameId;
			/**
	 * 下注比例
	 */
    private BigDecimal betRate;
    /**
     * 游戏名称
     */
    @TableField(exist=false)
    private String gameName;
	}
