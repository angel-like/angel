package com.xmsy.server.zxyy.webhome.modules.manager.webhomemenu.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 首页菜单管理
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-02 14:39:28
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
public class MenuEntity  {

		/**
	* 菜单名称
	*/
	private String id;
			/**
	 * 菜单名称
	 */
    private String name;
			/**
	 * 类型   0：一级 1：二级   2:三级
	 */
    private Integer type;
    /**
	 * 上级ID
	 */
    private Long parentId;
			/**
	 * 菜单图标
	 */
    private String photoUrl;
			/**
	 * 游戏ID(根据ID打开对应游戏)
	 */
    private Long gameId;
    /**
	 * 菜单颜色
	 */
    private String menuColor;
    /**
	 * 英文名称
	 */
    private String englishName;
	}
