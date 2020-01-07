package com.xmsy.server.zxyy.manager.modules.manager.signrewardcontinuous.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 签到连续奖励
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-05-13 15:50:02
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("sign_reward_continuous")
public class SignRewardContinuousEntity extends BaseEntity<SignRewardContinuousEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 连续登陆天数
	 */
    private Integer dayNum;
			/**
	 * 奖励
	 */
    private Long reward;
	}
