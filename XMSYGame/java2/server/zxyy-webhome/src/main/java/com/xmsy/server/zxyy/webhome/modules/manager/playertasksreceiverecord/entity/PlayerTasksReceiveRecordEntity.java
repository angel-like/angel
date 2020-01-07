package com.xmsy.server.zxyy.webhome.modules.manager.playertasksreceiverecord.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 玩家任务领取记录表
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-19 09:57:53
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("player_tasks_receive_record")
public class PlayerTasksReceiveRecordEntity extends BaseEntity<PlayerTasksReceiveRecordEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 用户id
	 */
    private Long userId;
			/**
	 * 用户账号
	 */
    private String userAccount;
			/**
	 * 任务id
	 */
    private Long taskId;
			/**
	 * 完成时间
	 */
    private Date finishDate;
	}
