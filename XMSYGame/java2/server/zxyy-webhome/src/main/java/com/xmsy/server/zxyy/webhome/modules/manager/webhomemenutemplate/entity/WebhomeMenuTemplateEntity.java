package com.xmsy.server.zxyy.webhome.modules.manager.webhomemenutemplate.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 菜单模板表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-02 19:32:58
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("webhome_menu_template")
public class WebhomeMenuTemplateEntity extends BaseEntity<WebhomeMenuTemplateEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 菜单ID
	 */
    private Long menuId;
			/**
	 * 标题
	 */
    private String title;
			/**
	 * 简介
	 */
    private String synopsis;
			/**
	 * 附件ID
	 */
    private Long enclosureId;
			/**
	 * 排序
	 */
    private String orderNum;
			/**
	 *游戏ID
	 */
    private Long gameId;
	
	/**
	 *图片路径
	 */
	@TableField(exist=false)
    private String enclosureUrl;
	/**
	 *游戏ID
	 */
	@TableField(exist=false)
    private String gameName;
	
	}
