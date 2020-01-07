package com.xmsy.server.zxyy.webhome.modules.manager.uservip.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户vip等级表
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-10 16:46:36
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
	 * 充值达到
	 */
    private Long rechargeReached;
			/**
	 * 充值优惠比例
	 */
    private BigDecimal rechargeRate;
    
    /**
     * 排序
     */
    private Integer sort;
	}
