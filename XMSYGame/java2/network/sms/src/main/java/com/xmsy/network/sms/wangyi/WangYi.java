package com.xmsy.network.sms.wangyi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.xmsy.common.define.constant.ResultDef;
import com.xmsy.network.sms.ErrorContrast;
import com.xmsy.network.sms.SMSResult;

import lombok.extern.slf4j.Slf4j;

/**
 * 校验验证码工具类
 *
 * @author Administrator
 */
@Slf4j
public class WangYi {

	private static final int SUCCESS_STATU = 200;// 网易云平台返回200表示成功
	// 发送验证码的请求路径URL
	private static final String SERVER_URL = "https://api.netease.im/sms/sendcode.action";
	// 发送验证码的请求路径URL
	private static final String Batch_SERVER_URL = "https://api.netease.im/sms/sendtemplate.action";
	// 网易云信分配的账号，请替换你在管理后台应用下申请的Appkey
	private static final String APP_KEY = "8d7074a436220e835e494eabbcc8a4f1";
	// 网易云信分配的密钥，请替换你在管理后台应用下申请的appSecret
	private static final String APP_SECRET = "2b7098d9a137";
	// 随机数
	private static final String NONCE = "345896";
	// 短信模板ID
	private static final String TEMPLATEID = "3057527";
	// 验证码长度，范围4～10，默认为4
	private static final String CODELEN = "6";

	public static SMSResult sendMsg(String phone, String params) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost post = new HttpPost(SERVER_URL);
		String curTime = String.valueOf((new Date().getTime() / 1000L));
		String checkSum = CheckSumBuilder.getCheckSum(APP_SECRET, params, curTime);

		// 设置请求的header
		post.addHeader("AppKey", APP_KEY);
		post.addHeader("Nonce", params);
		post.addHeader("CurTime", curTime);
		post.addHeader("CheckSum", checkSum);
		post.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		// 设置请求参数
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("mobile", phone));
		nameValuePairs.add(new BasicNameValuePair("codeLen", CODELEN));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
		SMSResult reslut = null;
		int status = ResultDef.NETWORK_ERROR_CODE;
		String responseEntity = null;
		// 执行请求
		try {
			HttpResponse response = httpclient.execute(post);
			status = response.getStatusLine().getStatusCode();
			responseEntity = EntityUtils.toString(response.getEntity(), "utf-8");
			if (ResultDef.SUCCESS != status) {
				return new SMSResult(status, responseEntity, null);
			}
			reslut = JSON.parseObject(responseEntity, SMSResult.class);
			if (SUCCESS_STATU != reslut.getCode()) {
				Optional<String> optional = Optional.ofNullable(ErrorContrast.getErrorMsg(reslut.getCode()));
				reslut.setMsg(optional.orElse(responseEntity));
			}
		} catch (Exception e) {
			log.error("【网易云】 SendCode", e);
			if (SUCCESS_STATU != status) {
				responseEntity = ErrorContrast.getErrorMsg(status);
			}
			reslut = new SMSResult(ResultDef.FAIL, responseEntity);
		}
		return reslut;
	}

	public static SMSResult sendMsgBatch(String batchPhoneNo, String sum) throws IOException {
		// batchPhoneNo="['15659133221','13115905053']";
		// params="['chenjisi','50']";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost post = new HttpPost(Batch_SERVER_URL);
		String curTime = String.valueOf((new Date().getTime() / 1000L));
		String checkSum = CheckSumBuilder.getCheckSum(APP_SECRET, NONCE, curTime);

		// 设置请求的header
		post.addHeader("AppKey", APP_KEY);
		post.addHeader("Nonce", NONCE);
		post.addHeader("CurTime", curTime);
		post.addHeader("CheckSum", checkSum);
		post.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		// 设置请求参数
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("templateid", TEMPLATEID));
		nameValuePairs.add(new BasicNameValuePair("mobiles", batchPhoneNo));
		nameValuePairs.add(new BasicNameValuePair("params", CODELEN));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
		SMSResult reslut = null;
		// 执行请求
		try {
			HttpResponse response = httpclient.execute(post);
			String responseEntity = EntityUtils.toString(response.getEntity(), "utf-8");
			reslut = JSON.parseObject(responseEntity, SMSResult.class);
		} catch (Exception e) {
			log.error("【网易云】 SendCode", e);
			new SMSResult(ResultDef.NETWORK_ERROR_CODE);
		}
		return reslut;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(sendMsg("15659133221", "1234"));

	}

}
