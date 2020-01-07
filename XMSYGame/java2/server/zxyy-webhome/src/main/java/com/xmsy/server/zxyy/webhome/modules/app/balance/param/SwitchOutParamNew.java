package com.xmsy.server.zxyy.webhome.modules.app.balance.param;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotations.TableField;

import lombok.Data;
@Data
public class SwitchOutParamNew {
	/**
	 * 转出金币数量
	 */
	@NotNull(message = "金币数量不能为空")
	private Long number;
	/**
	 * 用户取款密码（）
	 */
	@NotEmpty(message = "取款密码不能为空")
	@TableField(exist = false)
	private String password;
	/**
     * 理财产品id
     */
	@NotNull(message = "理财产品id不能为空")
    private Long userBalanceProductId;
}
