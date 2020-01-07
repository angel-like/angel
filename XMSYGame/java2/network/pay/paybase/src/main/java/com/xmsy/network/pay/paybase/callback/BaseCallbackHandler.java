package com.xmsy.network.pay.paybase.callback;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Maps;
import com.xmsy.network.pay.paybase.result.CallbackResult;

import lombok.extern.slf4j.Slf4j;

/**
 * .回调处理
 * 
 * @author aleng
 * @date 2018年11月6日
 * @version 1.0
 */
@Slf4j
public abstract class BaseCallbackHandler {

	/**
	 * .key:第三方支付公司别名 .value:具体的回调服务
	 */
	private static final Map<String, BaseCallbackHandler> CALLBACK_MAP = Maps.newConcurrentMap();

	/**
	 * .回调处理方法
	 * 
	 * @param request
	 * @param response
	 * @param payServiceName
	 *            具体的支付服务名称
	 * @param payOrderHandle
	 *            回调产生结果后订单处理逻辑
	 * @throws Exception
	 */
	public static void callback(HttpServletRequest request, HttpServletResponse response, String payServiceName,
			PayOrderHandle payOrderHandle) throws Exception {
		if (null == payOrderHandle) {
			log.error("[callback] payOrderHandle is null 未找到指定的订单处理服务");
		}
		BaseCallbackHandler callbackHandler = CALLBACK_MAP.get(payServiceName);
		if (null == callbackHandler) {
			log.error("[callback] callbackHandler is null 未找到指定的回调服务");
		}
		CallbackResult callbackResult = callbackHandler.getCallbackResult(request);
		log.info("[callback] callbackResult {}", callbackResult);
		if (null == callbackResult || !callbackResult.isSuccess()) {
			log.error("[callback] CallbackResult：{}", callbackResult);
			callbackHandler.getResponse(false, response);
			return;
		} else {
			callbackHandler.getResponse(payOrderHandle.orderHandle(callbackResult.getCallbackParam()), response);
			return;
		}
	}

	// 回调回来的参数验证签名
	protected abstract boolean verfiy(Map<String, Object> parameters);

	// 回调参数解析
	protected abstract CallbackResult getCallbackResult(HttpServletRequest request);

	// 获取回调结果
	protected abstract void getResponse(boolean orderHandleResult, HttpServletResponse response);

	// 获取回调参数
	protected TreeMap<String, Object> getRarameters(HttpServletRequest request) {
		TreeMap<String, Object> parameters = new TreeMap<>();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();
			if (null != request.getParameter(parameterName)) {
				parameters.put(parameterName, request.getParameter(parameterName));
			}
		}
		return parameters;
	}

	// 字符串读取
	protected String readAsString(HttpServletRequest request) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder("");
		try {
			br = request.getReader();
			String str;
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	protected static Map<String, BaseCallbackHandler> getCallbackMap() {
		return CALLBACK_MAP;
	}

	/**
	 * 校验回调服务是否存在
	 * 
	 * @param payServiceName
	 * @return
	 */
	public static boolean verfiyPayServiceName(String payServiceName) {
		return CALLBACK_MAP.get(payServiceName) != null;
	}
}
