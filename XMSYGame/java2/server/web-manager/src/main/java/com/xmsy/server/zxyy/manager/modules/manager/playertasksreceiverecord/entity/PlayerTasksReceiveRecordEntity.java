package com.xmsy.server.zxyy.manager.modules.manager.playertasksreceiverecord.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 玩家任务领取记录表
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-19 09:57:30
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
    
    /**
     * 任务名称
     */
    @TableField(exist=false)
    private String taskName;
	}
