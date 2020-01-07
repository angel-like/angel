package com.xmsy.server.zxyy.webhome.modules.manager.playertasks.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 玩家任务表
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-17 14:53:30
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("player_tasks")
public class PlayerTasksEntity extends BaseEntity<PlayerTasksEntity> {
	private static final long serialVersionUID = 1L;
		/**
	* 房间类型
	*/
	private Long roomId;
	
	/**
	* 游戏id
	*/
	private Long gameId;
	/**
	 * 游戏类型id
	 */
	private Long gradeId;
	/**
	 * 是否启用
	 */
	private Boolean enable;
	/**
	* 标题
	*/
	private String title;
	/**
	* 任务类型
	*/
	private Long type;
	/**
	* 任务次数
	*/
	private Integer taskNum;
	/**
	* 福缘任务功能类型
	*/
	private String functionalType;
	/**
	* 道具id
	*/
	private Long propId;
	/**
	* 道具数量
	*/
	private Integer propNum;
	/**
	* 条件
	*/
	private Integer condition;
	
	/**
	* 对局要求
	*/
	private Long confrontationRequirement;
	
	/**
	* 周期
	*/
	private Integer cycle;
	/**
	* 排序
	*/
	private Integer sorts;
	/**
	* 生效时间
	*/
	private Date effectTime;
	/**
	* 失效时间
	*/
	private Date failureTime;
	/**
	 * 是否全部领取完
	 */
	@TableField(exist=false)
	private Boolean whetherReceive;
	/**
	* 已领取的任务次数
	*/
	@TableField(exist=false)
	private Integer receiveTaskNum;
	/**
	 * 是否完成
	 */
	@TableField(exist=false)
	private Boolean whetherComplete;
	
	/**
	 * 完成次数
	 */
	@TableField(exist=false)
	private Integer completeNum;
	}
