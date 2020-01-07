package com.xmsy.server.zxyy.webhome.modules.manager.webhomeenclosure.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 文件附件表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2018-12-31 15:05:47
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("webhome_enclosure")
public class WebhomeEnclosureEntity extends BaseEntity<WebhomeEnclosureEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 文件名
	 */
    private String name;
			/**
	 * 文件路径
	 */
    private String url;
			/**
	 * 文件类型
	 */
    private String type;
	}
