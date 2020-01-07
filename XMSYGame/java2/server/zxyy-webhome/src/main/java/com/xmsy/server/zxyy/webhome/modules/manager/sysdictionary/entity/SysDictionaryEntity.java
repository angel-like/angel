package com.xmsy.server.zxyy.webhome.modules.manager.sysdictionary.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 数字字典
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-08 10:47:07
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
	}
