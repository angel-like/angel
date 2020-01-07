package com.xmsy.server.zxyy.manager.modules.manager.luckyuserrecord.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户抽奖记录表
 * 
 * @author ayang
 * @email xxxxx
 * @date 2019-11-22 17:55:00
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("lucky_user_record")
public class LuckyUserRecordEntity extends BaseEntity<LuckyUserRecordEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
    private Long userId;
			/**
	 * 用户账号
	 */
    private String userAccount;
			/**
	 * 转盘名称
	 */
    private String luckyName;
			/**
	 * 奖励名称
	 */
    private String propName;
			/**
	 * 奖励数量
	 */
    private Long propNum;
			/**
	 * 是否大奖
	 */
    private Boolean grand;
	/**
	 * 开始时间
	 */
	@TableField(exist=false)
	private String sTime;
	/**
	 * 结束时间
	 */
	@TableField(exist=false)
	private String eTime;
	}
