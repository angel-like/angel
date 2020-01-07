package com.xmsy.server.zxyy.webhome.modules.app.user.param;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;

import com.xmsy.server.zxyy.webhome.modules.app.user.validate.CoinExchange;
import com.xmsy.server.zxyy.webhome.modules.app.user.validate.CommissionExchange;
import com.xmsy.server.zxyy.webhome.modules.app.user.validate.MoneyExchange;

import lombok.Data;

/**
 * .用户额度转换
 * 
 * @author Administrator
 *
 */
@Data
public class ExchangeParam {

	@DecimalMin(value = "1", message = "转换金额必须大于1", groups = { MoneyExchange.class })
	private BigDecimal money;

	@DecimalMin(value = "1", message = "转换佣金必须大于1", groups = { CommissionExchange.class })
	private BigDecimal commission;

	@DecimalMin(value = "100", message = "转换金币必须是大于100等整数", groups = { CoinExchange.class })
	private Long coin;

}
