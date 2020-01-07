package com.xmsy.server.zxyy.manager.modules.manager.sysconfig.entity;

import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 系统配置表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-05-21 15:26:43
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_config")
public class SysConfigEntity extends BaseEntity<SysConfigEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * key
	 */
	private String paramKey;
	/**
	 * value
	 */
	private String paramValue;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 备注0为最上级
	 */
	private Long parentId;

	@TableField(exist = false)
	private List<SysConfigEntity> children;
}
