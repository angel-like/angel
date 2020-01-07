package com.xmsy.server.zxyy.game.modules.web.stock.param;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockResult {
	private long time;//时间差秒
	private long count; //累计打码量
	private BigDecimal history;//历史
	private BigDecimal limit;//当前库存上限
	private BigDecimal value;//当前有效库存
	private BigDecimal total;//库存上限
	private BigDecimal stock;//实际有效库存
}
