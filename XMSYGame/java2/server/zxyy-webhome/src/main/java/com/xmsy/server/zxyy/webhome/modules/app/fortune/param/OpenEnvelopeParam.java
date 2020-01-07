package com.xmsy.server.zxyy.webhome.modules.app.fortune.param;



import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
public class OpenEnvelopeParam {

	/**
	 * 要打开的红包数量
	 */
	@NotNull(message = "打开数量不能为空")
	private Long number;
}
