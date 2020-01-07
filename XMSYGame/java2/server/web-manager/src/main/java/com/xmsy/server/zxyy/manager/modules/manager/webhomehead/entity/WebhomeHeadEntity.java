package com.xmsy.server.zxyy.manager.modules.manager.webhomehead.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 官网头部管理
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-24 20:28:36
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("webhome_head")
public class WebhomeHeadEntity extends BaseEntity<WebhomeHeadEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 名字
	 */
    private String name;
			/**
	 * url
	 */
    private String url;
			/**
	 * 内容
	 */
    private String content;
			/**
	 * 附件ID
	 */
    private Long enclosureId;
    	/**
	 * 类型
	 */
    private String type;
    	/**
	 * 附件路径
	 */
    @TableField(exist = false)
    private String enclosureUrl;
	}
