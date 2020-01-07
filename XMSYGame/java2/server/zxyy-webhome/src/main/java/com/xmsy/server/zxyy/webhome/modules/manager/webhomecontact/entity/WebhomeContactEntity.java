package com.xmsy.server.zxyy.webhome.modules.manager.webhomecontact.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 联系管理
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-01 16:52:13
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("webhome_contact")
public class WebhomeContactEntity extends BaseEntity<WebhomeContactEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 附件ID
	 */
    private Long enclosureId;
			/**
	 * 名称
	 */
    private String name;
			/**
	 * 内容
	 */
    private String content;
    		/**
	 * 类型(联系方式，网站信息）
	 */
    private String type;
    		/**
	 * 是否展示
	 */
    private Boolean enable;
			/**
	 * 备注
	 */
    private String remake;
    /**
	 * 路径
	 */
    private String url;
	}
