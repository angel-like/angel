package com.xmsy.server.zxyy.manager.modules.manager.gamestockoperationrecord.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 游戏库存操作记录
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-12-26 10:16:33
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("game_stock_operation_record")
public class GameStockOperationRecordEntity extends BaseEntity<GameStockOperationRecordEntity> {
	private static final long serialVersionUID = 1L;
			/**
	 * 房间id
	 */
    private String roomId;
    
    
		/**
	 * 库存id
	 */
	private Long stockId;
    
    
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
			/**
	 * 操作者id
	 */
    private Long sysUserId;
			/**
	 * 操作者名称
	 */
    private String sysUserName;
			/**
	 * 操作内容
	 */
    private String operationContent;
    
    @TableField(exist=false)
    private String startTime;
    
    @TableField(exist=false)
    private String endTime;
					}
