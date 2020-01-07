package com.xmsy.server.zxyy.manager.modules.manager.headframe.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 头像框表
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-06-18 17:20:02
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("headframe")
public class HeadframeEntity extends BaseEntity<HeadframeEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 类型 
	 */
    private String type;
    /**
    * 类型 
    */
    @TableField(exist=false)
    private String typeName;
			/**
	 * 类型id
	 */
    private Long typeId;
	/**
	 * 类型id
	 */
    @TableField(exist=false)
	private String typeIdName;
			/**
	 * 标题
	 */
    private String title;
    /**
     * 是否可用
     */
    private Boolean enable;
	}
