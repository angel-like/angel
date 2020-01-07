package com.xmsy.server.zxyy.payment.service.modules.sysconfig.entity;

import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xmsy.data.dao.mybatis.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统配置表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-05-21 15:26:43
 */
@Data
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
