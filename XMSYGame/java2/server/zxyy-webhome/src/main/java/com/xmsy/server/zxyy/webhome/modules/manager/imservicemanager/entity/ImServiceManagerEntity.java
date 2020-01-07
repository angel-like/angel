package com.xmsy.server.zxyy.webhome.modules.manager.imservicemanager.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 33娱乐服务器管理
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-06-20 16:35:33
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("im_service_manager")
public class ImServiceManagerEntity extends BaseEntity<ImServiceManagerEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 服务器名称
	 */
	private String name;
	/**
	 * 服务器路径
	 */
	private String serviceUrl;
	/**
	 * 是否启用：1启用
	 */
	private Boolean enable;
	/**
	 * 排序号
	 */
	private Integer orderNo;
}
