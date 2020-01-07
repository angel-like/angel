package com.xmsy.server.zxyy.manager.modules.manager.usertransactionrecord.entity;

import java.math.BigDecimal;

import lombok.Data;


/**
 * 资金交易明细
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-01-26 20:25:16
 */
@Data

public class WebhomeUserTransactionRecordEntity  {
    private String type;
    private String createTime;
    private BigDecimal amount;
    private BigDecimal money;
    private Long coin;
    private BigDecimal commission;
    private String detailType;
	}
