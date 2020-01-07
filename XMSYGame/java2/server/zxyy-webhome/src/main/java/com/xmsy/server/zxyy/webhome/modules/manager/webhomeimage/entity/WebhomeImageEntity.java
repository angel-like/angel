package com.xmsy.server.zxyy.webhome.modules.manager.webhomeimage.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 官网图片管理
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2018-12-31 16:23:23
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("webhome_image")
public class WebhomeImageEntity extends BaseEntity<WebhomeImageEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 附件ID
	 */
    private Long enclosureId;
			/**
	 * 图片类型（LOG,轮播图，对联）
	 */
    private String type;
			/**
	 * 状态(启用，禁用)
	 */
    private Boolean enable;
			/**
	 * 排序号
	 */
    private Integer num;
    /**
	 * 跳转路径
	 */
    private String url;
	}
