package com.xmsy.server.zxyy.webhome.modules.manager.lucky.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import com.xmsy.server.zxyy.webhome.base.BaseEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.luckyconfig.entity.LuckyConfigEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


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
@TableName("lucky")
public class LuckyEntity extends BaseEntity<LuckyEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 名称
	 */
    private String name;
			/**
	 * 需要积分
	 */
    private Long integral;
			/**
	 * 是否启用
	 */
    private Boolean enable;

	@TableField(exist=false)
	private List<LuckyConfigEntity> settings;
	}
