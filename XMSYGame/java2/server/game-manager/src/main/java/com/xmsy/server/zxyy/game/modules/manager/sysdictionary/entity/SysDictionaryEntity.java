package com.xmsy.server.zxyy.game.modules.manager.sysdictionary.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.game.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 数据字典表
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-05-11 16:14:54
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("sys_dictionary")
public class SysDictionaryEntity extends BaseEntity<SysDictionaryEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * code
	 */
    private String code;
			/**
	 * 名称
	 */
    private String name;
			/**
	 * 上级
	 */
    private String parentCode;
			/**
	 * 是否可用
	 */
    private Boolean enable;
    //==================================批量修改游戏配置使用============================
    /**
     * 数据库不存在的   gameConfig的id
     */
    @TableField(exist=false)
    private Long gameConigId;
    /**
     * 数据库不存在的   gameConfig的gameId
     */
    @TableField(exist=false)
    private Long gameConigGameId;
    /**
     * 数据库不存在的   gameConfig的值
     */
    @TableField(exist=false)
    private String val;
	}
