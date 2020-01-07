package com.xmsy.network.sms;

import com.xmsy.common.define.constant.ResultDef;
import com.xmsy.network.sms.bensi.BenSi;
import com.xmsy.network.sms.bensi.BenSiParam;
import com.xmsy.network.sms.luoshimao.Luoshimao;
import com.xmsy.network.sms.wangyi.WangYi;

/**
 * .发送短信验证码
 * 
 * @author chenjisi
 * @since 2017年8月4日
 */
public class SendSMS {

	// 发送短信
	public static SMSResult send(String phoneNo, String randomCode) throws Exception {
		SMSResult result = WangYi.sendMsg(phoneNo, randomCode);
		if (!isSuccess(result)) {
			result = Luoshimao.sendMessage(phoneNo, randomCode);
		}
		return result;
	}

	// 发送短信
	public static SMSResult sendSMS(String phoneNo, String randomCode) {
		return Luoshimao.sendMessage(phoneNo, randomCode);
	}

	// 短信发送
	public static SMSResult sendSMS(String url, String phoneNo, String account, String password, String content,
			String extno) {
		return BenSi.sendMessage(new BenSiParam().setAccount(account).setPassword(password).setMobile(phoneNo)
				.setContent(content).setExtno(extno).setUrl(url));
	}

	public static final boolean isSuccess(SMSResult result) {
		return result != null && ResultDef.SUCCESS == result.getCode();
	}

}
