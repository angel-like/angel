package com.xmsy.server.zxyy.manager.modules.manager.domainmanagement.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 域名管理
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-06 14:09:43
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("webhome_domain")
public class DomainManagementEntity extends BaseEntity<DomainManagementEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 域名
	 */
    private String domain;
			/**
	 * 1:官网,2:管理后台
	 */
    private Integer type;
	}
