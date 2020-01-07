package com.xmsy.server.zxyy.game.modules.manager.gameexperience.entity;



import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.game.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 开元游戏场次等级
 *
 * @author adai
 * @email sunlightcs@gmail.com
 * @date 2019-11-28 09:12:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("kaiyuan_grade")
public class KaiyuanGradeEntity extends BaseEntity<KaiyuanGradeEntity> implements Cloneable {
	private static final long serialVersionUID = 1L;

	/**
	 * 游戏id
	 */
	private Integer gameId;
	/**
	 * 开元房间号
	 */
	private Integer serviceId;
	/**
	 * 场次名称
	 */
	private String name;

	/**
	 * 场次等级id
	 */
	private Integer gradeId;
	/**
	 * 游戏名称
	 */
	@TableField(exist = false)
	private String gameName;
	/**
	 * 游戏场次名称
	 */
	@TableField(exist = false)
	private String gradeName;
	@TableField(exist = false)
	private String startTime;
	@TableField(exist = false)
	private String endTime;
}
