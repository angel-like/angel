package com.xmsy.server.zxyy.webhome.modules.manager.usertrialconfig.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 试玩账号配置
 * 
 * @author lpp
 * @email xxxxx
 * @date 2019-01-25 15:52:20
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("user_trial_config")
public class UserTrialConfigEntity extends BaseEntity<UserTrialConfigEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 试玩个数
	 */
    private Long trialNumber;
			/**
	 * 试玩金额
	 */
    private BigDecimal trialAmount;
	}
