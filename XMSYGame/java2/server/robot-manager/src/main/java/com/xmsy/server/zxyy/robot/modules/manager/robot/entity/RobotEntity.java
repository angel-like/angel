package com.xmsy.server.zxyy.robot.modules.manager.robot.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.robot.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 机器人管理
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-18 14:58:45
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("robot")
public class RobotEntity extends BaseEntity<RobotEntity> implements Cloneable {

	private static final long serialVersionUID = 1L;
	/**
	 * 金币余额
	 */
	private Long coin;
	/**
	 * 盈利金币额
	 */
	private Long profitCoin;
	/**
	 * 机器人代号
	 */
	private String name;
	/**
	 * true启用/false禁用
	 */
	private Boolean enable;
	/**
	 * 状态(true已分配,false空闲)
	 */
	private Boolean status;
	/**
	 * 性别(false:女，true：男)
	 */
	private Boolean sex;
	/**
	 * 头像
	 */
	private String portrait;
	/**
	 * 游戏ID
	 */
	private Long gameId;
	/**
	 * 场次id
	 */
	private Long gradeId;
	/**
	 * token
	 */
	private String token;
	/**
	 * 在玩游戏信息id
	 */
	private Long gameInfoId;
	/**
	 * 游戏服务id
	 */
	private Long gameServerId;
	/**
	 * 游戏名称
	 */
	@TableField(exist = false)
	private String gameName;

	@Override
	public RobotEntity clone() {
		RobotEntity entity = null;
		try {
			entity = (RobotEntity) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}

}
