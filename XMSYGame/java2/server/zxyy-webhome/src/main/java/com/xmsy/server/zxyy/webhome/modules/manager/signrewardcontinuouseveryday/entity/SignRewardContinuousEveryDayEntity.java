package com.xmsy.server.zxyy.webhome.modules.manager.signrewardcontinuouseveryday.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 每天签到奖励
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-05-13 15:50:02
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("sign_reward_continuous_every_day")
public class SignRewardContinuousEveryDayEntity extends BaseEntity<SignRewardContinuousEveryDayEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 签到名称
	 */
    private String dayName;
			/**
	 * 奖励
	 */
    private Long reward;
    
    /**
     * 第几天
     */
    private Integer dayNum;
    
    /**
     * vip等级
	 */
    private Long vipId;
	}
