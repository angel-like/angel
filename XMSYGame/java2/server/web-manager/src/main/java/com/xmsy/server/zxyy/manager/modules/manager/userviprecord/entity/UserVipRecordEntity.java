package com.xmsy.server.zxyy.manager.modules.manager.userviprecord.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户vip等级记录表
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-06-11 16:04:14
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("user_vip_record")
public class UserVipRecordEntity extends BaseEntity<UserVipRecordEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * vip等级id
	 */
    private Long vipId;
			/**
	 * 已经充值
	 */
    private Long rechargeAmount;
			/**
	 * 用户id
	 */
    private Long userId;
			/**
	 * 用户名称
	 */
    private String userAccount;
	}
