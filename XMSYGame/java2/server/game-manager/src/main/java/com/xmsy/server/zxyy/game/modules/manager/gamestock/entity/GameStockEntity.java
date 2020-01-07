package com.xmsy.server.zxyy.game.modules.manager.gamestock.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.game.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;


/**
 * 游戏库存
 *
 * @author adu
 * @email xxxxx
 * @date 2019-10-28 17:03:37
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("game_stock")
public class GameStockEntity extends BaseEntity<GameStockEntity> {
	private static final long serialVersionUID = 1L;
			/**
	 * 房间id
	 */
    private String roomId;
    /**
     * 房间id
     */
    @TableField(exist=false)
    private String roomName;
			/**
	 * 实际有效库存
	 */
    private BigDecimal stock;
			/**
	 * 库存上限
	 */
    private BigDecimal stockLimit;
    /**
     * 库存标准值
     */
    private BigDecimal stockStandard;
    /**
     * 库存上限标准值
     */
    private BigDecimal stockLimitStandard;
			/**
	 * 抽税
	 */
    private BigDecimal tax;
			/**
	 * 抽税比例
	 */
    private BigDecimal taxRate;
    /**
     * 是否启动
     */
    private Boolean enable;
					}
