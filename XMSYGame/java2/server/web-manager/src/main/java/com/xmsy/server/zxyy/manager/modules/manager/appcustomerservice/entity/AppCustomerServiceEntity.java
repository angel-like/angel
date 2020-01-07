package com.xmsy.server.zxyy.manager.modules.manager.appcustomerservice.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * app客服管理
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-03-06 14:43:56
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("app_customer_service")
public class AppCustomerServiceEntity extends BaseEntity<AppCustomerServiceEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 客服url
	 */
    private String url;
	}
