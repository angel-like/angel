package com.xmsy.network.sms;

/**
 * .第三方错误码对照
 * @author chenjisi
 * @since 2017年8月3日
 */
public final class ErrorContrast {

	public static String getErrorMsg(int errorCode) {
		switch (errorCode) {
		case  0:
			return "成功";
		case -10:
			return "验证信息失败,检查api key是否和各种中心内的一致，调用传入是否正确";
		case -20:
			return "短信余额不足,进入个人中心购买充值";
		case -30:
			return "短信内容为空,检查调用传入参数：message";
		case -31:
			return "短信内容存在敏感词,修改短信内容,更换词语";
		case -32:
			return "短信内容缺少签名信息	短信内容末尾增加签名信息eg.【铁壳测试】";
		case -34:
			return "签名不可用	在后台 短信->签名管理下进行添加签名";
		case -40:
			return "检查手机号是否正确";
		case -43:
			return "单次提交控制在10万个号码以内";
		case -50:
			return "请求发送IP不在白名单内	查看IP白名单的设置";
		case -60:
			return "定时时间为过去,检查定时的时间，取消定时或重新设定定时时间";
			//网易云的错误码
		case  400:
			return "手机号错误";
		case 404:
			return "对象不存在";
		case 405:
			return "参数长度过长";
		case 406:
			return "对象只读";
		case 408:
			return "客户端请求超时";
		case 414:
			return "参数错误";
		case 415:
			return "客户端网络问题";
		case 416:
			return "频率控制";
		case 422:
			return "账号被禁用";
		case 500:
			return "服务器内部错误";
		case 503:
			return "服务器繁忙";
		case 509:
			return "无效协议";
		case 998:
			return "解包错误";
		case 999:
			return "打包错误";
		default:
			return null;
		}
	}
	
	
}
