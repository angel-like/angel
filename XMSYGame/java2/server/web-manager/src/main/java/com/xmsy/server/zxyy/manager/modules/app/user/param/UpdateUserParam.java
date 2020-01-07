package com.xmsy.server.zxyy.manager.modules.app.user.param;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.xmsy.server.zxyy.manager.modules.app.user.validate.UserAlipay;
import com.xmsy.server.zxyy.manager.modules.app.user.validate.UserBank;
import com.xmsy.server.zxyy.manager.modules.app.user.validate.UserName;
import com.xmsy.server.zxyy.manager.modules.app.user.validate.UserPortrait;
import com.xmsy.server.zxyy.manager.modules.app.user.validate.UserWalletPassword;

import lombok.Data;

/**
 * .修改用户基本信息参数
 * 
 * @author aleng
 *
 */
@Data
public class UpdateUserParam {
	
	@NotEmpty(message = "用户头像不能为空", groups = { UserPortrait.class })
	private String portrait;

	@NotEmpty(message = "用户名称不能为空", groups = { UserName.class })
	private String userName;

	@NotNull(message = "用户开户行不能为空", groups = { UserBank.class })
	private Long bank;
	
	@NotEmpty(message = "开户网点不能为空", groups = { UserBank.class })
	private String bankAddress;

	@NotEmpty(message = "用户银行卡号不能为空", groups = { UserBank.class })
	private String bankCard;
	//银行名称
	private String bankName;

	@NotEmpty(message = "支付宝账号不能为空", groups = { UserAlipay.class })
	private String alipay;
	
	@NotEmpty(message = "用户取款密码不能为空", groups = { UserWalletPassword.class })
	private String walletPassword;
	
	@NotEmpty(message = "用户确认取款密码不能为空", groups = { UserWalletPassword.class })
	private String confirmWalletPassword;

}
