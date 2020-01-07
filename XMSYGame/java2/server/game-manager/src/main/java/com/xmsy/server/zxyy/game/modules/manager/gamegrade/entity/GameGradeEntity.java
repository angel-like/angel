package com.xmsy.server.zxyy.game.modules.manager.gamegrade.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.game.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 游戏场次等级
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-02-19 13:38:51
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("game_grade")
public class GameGradeEntity extends BaseEntity<GameGradeEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 场次名称
	 */
	private String name;
}
