package com.xmsy.server.zxyy.webhome.modules.manager.hierarchygamerole.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 层级游戏权限关联表
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-09 14:26:36
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("hierarchy_game_role")
public class HierarchyGameRoleEntity extends BaseEntity<HierarchyGameRoleEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 层级ID
	 */
    private Long hierarchyId;
			/**
	 * 游戏ID
	 */
    private Long gameId;
	}
