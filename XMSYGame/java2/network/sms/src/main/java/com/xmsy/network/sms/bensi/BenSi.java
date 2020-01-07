package com.xmsy.network.sms.bensi;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.define.constant.ResultDef;
import com.xmsy.network.sms.SMSResult;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BenSi {

	public static final String STATUS_SUCCESS = "0";
	public static final int RESULT_SUCCESS = 0;
	// 【众享科技】您的验证码为：*,请您在5分钟内按页面提示提交验证码，请勿泄露给他人！如非本人操作请忽略本条短信。

	public static SMSResult sendMessage(BenSiParam param) {
		JSONObject benSiParam = (JSONObject) JSON.toJSON(param);
		Map<String, Object> requestMap = benSiParam.getInnerMap();
		HttpResponse result = HttpRequest.post(param.getUrl()).form(requestMap).execute();
		if (ResultDef.SUCCESS == result.getStatus()) {
			log.error("【bensi】 短信服务 responseStatus {}  textEntity {}", result.getStatus(), result.body());
			return new SMSResult(result.getStatus(), result.body(), null);
		}
		try {
			JSONObject jsonResult = JSON.parseObject(result.body());
			if (!STATUS_SUCCESS.equals(jsonResult.getString("status"))) {
				log.error("【bensi】 error {}", jsonResult);
				return new SMSResult(ResultDef.FAIL);
			}
			JSONObject resultObj = (JSONObject) jsonResult.getJSONArray("list").get(0);
			if (RESULT_SUCCESS != resultObj.getInteger("result")) {
				log.error("【bensi】 error {}", jsonResult);
				return new SMSResult(ResultDef.FAIL);
			}
		} catch (Exception e) {
			log.error("【bensi】JSON.parseObject(textEntity)", e);
		}
		return new SMSResult(ResultDef.SUCCESS, result.body());
	}

	public static void main(String[] args) {
		BenSiParam param = new BenSiParam();
		param.setAccount("100003");
		param.setAction("send");
		param.setPassword("BZNSiY");
		param.setContent("【众享科技】您的验证码为：565094,请您在5分钟内按页面提示提交验证码，请勿泄露给他人！如非本人操作请忽略本条短信。");
		param.setExtno("10690100003");
		param.setMobile("15659133221");
		param.setUrl("http://47.103.35.11:7862/sms");
		// JSONObject benSiParam = (JSONObject) JSON.toJSON(param);
		System.out.println(sendMessage(param));
	}

}
