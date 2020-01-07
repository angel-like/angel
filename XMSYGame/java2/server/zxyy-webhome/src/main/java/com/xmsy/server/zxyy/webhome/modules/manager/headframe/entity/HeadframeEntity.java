package com.xmsy.server.zxyy.webhome.modules.manager.headframe.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 头像框表
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-06-19 10:17:16
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
	 * 类型id
	 */
    private Long typeId;
			/**
	 * 标题
	 */
    private String title;
			/**
	 * 是否可用
	 */
    private Boolean enable;
	}
