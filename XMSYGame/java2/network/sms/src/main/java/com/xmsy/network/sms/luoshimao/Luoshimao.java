package com.xmsy.network.sms.luoshimao;

import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.xmsy.common.define.constant.ResultDef;
import com.xmsy.network.sms.ErrorContrast;
import com.xmsy.network.sms.SMSResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Luoshimao {

	private static final String TITLE = "验证码：";
	private static final String COMPANY = "【铁壳测试】";
	private static final String URI_SIMPLE_SMS = "http://sms-api.luosimao.com/v1/send.json";
	private static final String URI_BATCH_SMS = "http://sms-api.luosimao.com/v1/send_batch.json";
	private static final String API_KEY = "4a2a22f816249db338de1829a81e23d9";
	private static final int SUCCESS_STATU = 0;// Luoshimao平台返回0表示成功

	public static SMSResult sendMessage(String phoneNo, String msg) {
		// just replace key here
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("api", API_KEY));
		WebResource webResource = client.resource(URI_SIMPLE_SMS);
		MultivaluedMapImpl formData = new MultivaluedMapImpl();
		formData.add("mobile", phoneNo);
		String message = TITLE + msg + COMPANY;
		formData.add("message", message);
		ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class,
				formData);
		String textEntity = response.getEntity(String.class);
		int status = response.getStatus();
		if (ResultDef.SUCCESS != status) {
			log.error("【luoshimao】短信服务responseStatus {}  textEntity {}", status, textEntity);
			return new SMSResult(status, textEntity, null);
		}
		try {
			JSONObject result = JSON.parseObject(textEntity);
			if (SUCCESS_STATU != result.getIntValue("error")) {
				textEntity = ErrorContrast.getErrorMsg(result.getIntValue("error"));
				status = ResultDef.FAIL;
				log.error("【luoshimao】 error {}", textEntity);
			}
		} catch (Exception e) {
			log.error("【luoshimao】JSON.parseObject(textEntity)", e);
		}
		return new SMSResult(status, textEntity, msg);
	}

	public static SMSResult sendMessageBatch(String batchPhoneNo, String msg) {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("api", API_KEY));
		WebResource webResource = client.resource(URI_BATCH_SMS);
		MultivaluedMapImpl formData = new MultivaluedMapImpl();
		formData.add("mobile_list", batchPhoneNo);
		formData.add("message", msg);
		ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class,
				formData);
		String textEntity = response.getEntity(String.class);
		int status = response.getStatus();
		if (ResultDef.SUCCESS != status) {
			log.error("【luoshimao】短信服务（批量）responseStatus {}  textEntity {}", status, textEntity);
			return new SMSResult(status, textEntity, null);
		}
		String errorMsg = null;
		try {
			JSONObject result = JSON.parseObject(textEntity);
			errorMsg = ErrorContrast.getErrorMsg(result.getIntValue("error"));
		} catch (Exception e) {
			log.error("【luoshimao】sendMessageBatch JSON.parseObject(textEntity)", e);
		}
		errorMsg = errorMsg == null ? textEntity : errorMsg;
		return new SMSResult(status, errorMsg, msg);
	}

	public static void main(String[] args) {
		// Api.sendMessage("15659133221",
		// EnumNumber.RANDOM_SIX.getRandomNumber().toString());
		SMSResult result = Luoshimao.sendMessage("15659133221", "26428");// 需要、
		System.out.println(result);
	}

}