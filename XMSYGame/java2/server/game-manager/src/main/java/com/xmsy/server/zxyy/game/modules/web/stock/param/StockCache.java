package com.xmsy.server.zxyy.game.modules.web.stock.param;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class StockCache {
	private Long time = 0l;//时间差秒
	private Long count = 0l; //累计打码量
	private BigDecimal limit = BigDecimal.ZERO;//当前库存上限
	private List<Long> history ;//历史
	private BigDecimal value = BigDecimal.ZERO;//当前有效库存
	private BigDecimal total = BigDecimal.ZERO;//库存上限
	private BigDecimal stock = BigDecimal.ZERO;//实际有效库存
}
