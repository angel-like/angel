package com.xmsy.server.zxyy.manager.modules.manager.enclosuregroup.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 图片分组
 * 
 * @author ayang
 * @email xxxxx
 * @date 2019-10-28 15:43:09
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("enclosure_group")
public class EnclosureGroupEntity extends BaseEntity<EnclosureGroupEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 分组名称
	 */
    private String name;
	}
