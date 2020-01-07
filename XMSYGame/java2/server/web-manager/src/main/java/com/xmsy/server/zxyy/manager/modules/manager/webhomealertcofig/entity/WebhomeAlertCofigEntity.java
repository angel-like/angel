package com.xmsy.server.zxyy.manager.modules.manager.webhomealertcofig.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 官网弹窗配置表

 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-05-03 10:23:36
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("webhome_alert_cofig")
public class WebhomeAlertCofigEntity extends BaseEntity<WebhomeAlertCofigEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 介绍
	 */
    private String introduction;
			/**
	 * 附件ID
	 */
    private Long enclosureId;
			/**
	 * 跳转路径
	 */
    private String url;
			/**
	 * 排序号
	 */
    private Integer num;
	}
