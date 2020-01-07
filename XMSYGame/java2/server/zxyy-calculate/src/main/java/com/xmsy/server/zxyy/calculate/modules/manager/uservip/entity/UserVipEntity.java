package com.xmsy.server.zxyy.calculate.modules.manager.uservip.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.calculate.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户vip等级表
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-10 15:18:13
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("user_vip")
public class UserVipEntity extends BaseEntity<UserVipEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * vip等级名称
	 */
    private String name;
    /**
     * 排序
     */
    private Integer sort;
	/**
	 * 充值达到
	 */
    private Long rechargeReached;
			/**
	 * 充值优惠比例
	 */
    private BigDecimal rechargeRate;
	}
