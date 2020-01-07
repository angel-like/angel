package com.xmsy.server.zxyy.manager.modules.manager.luckyconfig.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 幸运转盘
 * 
 * @author ayang
 * @email xxxxx
 * @date 2019-11-21 12:58:04
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("lucky_config")
public class LuckyConfigEntity extends BaseEntity<LuckyConfigEntity> {
	private static final long serialVersionUID = 1L;
	private  Long id;
							/**
	 * 转盘等级
	 */
    private Long luckyId;
			/**
	 * 道具id
	 */
    private Long propId;
			/**
	 * 道具数量
	 */
    private Long propNum;
    private  String name;
	@TableField(exist=false)
	private String propName;
	private boolean grand;
	private BigDecimal chance;

	}
