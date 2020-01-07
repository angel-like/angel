package com.xmsy.server.zxyy.manager.modules.manager.giftbagexchangerecord.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 兑换码兑换记录表
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-07-18 15:14:51
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("gift_bag_exchange_record")
public class GiftBagExchangeRecordEntity extends BaseEntity<GiftBagExchangeRecordEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 兑换用户id
	 */
    private Long userId;
			/**
	 * 用户账户
	 */
    private String userAccount;
			/**
	 * 用户名称
	 */
    private String userName;
			/**
	 * 兑换码
	 */
    private String exchangeCode;
			/**
	 * 兑换时间
	 */
    private Date exchangeTime;
    
		/**
	 * 兑换金额
	 */
	private BigDecimal acountMoney;
    
    
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
