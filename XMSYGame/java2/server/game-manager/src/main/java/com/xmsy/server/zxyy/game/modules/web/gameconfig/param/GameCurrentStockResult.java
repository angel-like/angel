package com.xmsy.server.zxyy.game.modules.web.gameconfig.param;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class GameCurrentStockResult implements Serializable, Cloneable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 游戏id
	 */
	@NotNull(message = "游戏id不能为空")
	private Long gameId;
	
	/**
	 * 当前库存
	 */
	@NotNull(message = "当前库存不能为空")
	private BigDecimal currentStock;

}
