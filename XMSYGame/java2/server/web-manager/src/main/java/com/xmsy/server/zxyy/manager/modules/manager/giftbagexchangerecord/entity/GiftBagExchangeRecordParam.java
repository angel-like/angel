package com.xmsy.server.zxyy.manager.modules.manager.giftbagexchangerecord.entity;


import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;


/**
 * 兑换码兑换记录表
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-07-18 15:14:51
 */
@Data
public class GiftBagExchangeRecordParam {
							/**
	 * 兑换人数
	 */
    private Integer exchangeNum;
		/**
	 * 兑换金额
	 */
	private BigDecimal totalExchangeAmount;
	
		/**
	 * 兑换时间
	 */
	private Date exchangeTime;
    
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
	}
