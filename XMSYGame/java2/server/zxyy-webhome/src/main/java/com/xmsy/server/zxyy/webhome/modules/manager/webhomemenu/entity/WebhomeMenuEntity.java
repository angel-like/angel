package com.xmsy.server.zxyy.webhome.modules.manager.webhomemenu.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 首页菜单管理
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-01-02 14:39:28
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("webhome_menu")
public class WebhomeMenuEntity extends BaseEntity<WebhomeMenuEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 父菜单ID，一级菜单从0开始
	 */
    private Long parentId;
			/**
	 * 菜单名称
	 */
    private String name;
			/**
	 * 英语名称
	 */
    private String englishName;
			/**
	 * 类型   0：一级 1：二级   2:三级
	 */
    private Integer type;
			/**
	 * 菜单图标
	 */
    private Long icon;
			/**
	 * 排序
	 */
    private Integer orderNum;
			/**
	 * 游戏ID(根据ID打开对应游戏)
	 */
    private Long gameId;
    /**
	 * 菜单颜色
	 */
    private String menuColor;
		/**
	* 父菜单名称
	*/
    @TableField(exist=false)
	private String parentName;
    
    /**
	 * 游戏名称
	 */
    @TableField(exist=false)
    private String gameName;
	}
