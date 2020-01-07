package com.xmsy.server.serverbase.result;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xmsy.common.define.constant.ResultDef;
import com.xmsy.common.define.result.GlobalResult;
import com.xmsy.common.define.result.ResultUtils;

/**
 * .应用统一返回json格式定义
 * 
 * @author chenjisi
 * @since 2017年8月14日
 */
public class GlobalJsonReturn implements HandlerMethodReturnValueHandler {

	private static final Logger LOG = LoggerFactory.getLogger(GlobalJsonReturn.class);

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return null != returnType.getMethod().getDeclaringClass().getAnnotation(RestApp.class)
				|| null != returnType.getMethodAnnotation(RestApp.class);
	}

	@Override
	public void handleReturnValue(Object result, MethodParameter methodParameter,
			ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {
		modelAndViewContainer.setRequestHandled(true);
		HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
		HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
		GlobalResult<?> globalResult = null;
		if (null != result) {
			globalResult = ResultUtils.getSuccessObject(result, request.getRequestURI());
			print(request, response, globalResult);
			return;
		}
		Class<?> returnType = methodParameter.getMethod().getReturnType();
		if (returnType.isInstance(ResultDef.EMPTY_LIST)) {
			globalResult = ResultUtils.getSuccessObject(ResultDef.EMPTY_LIST, request.getRequestURI());
		} else if (returnType.isArray()) {
			globalResult = ResultUtils.getSuccessObject(ResultDef.EMPTY_LIST, request.getRequestURI());
		} else {
			globalResult = ResultUtils.getSuccessObject(ResultDef.EMPTY_OBJECT, request.getRequestURI());
		}
		print(request, response, globalResult);
	}

	private static void print(HttpServletRequest request, HttpServletResponse response, Object result) {
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JSON.toJSONString(result, SerializerFeature.WriteDateUseDateFormat));
			LOG.info(JSON.toJSONString(result));
			writer.flush();
		} catch (IOException ex) {
			LOG.error("printWriter", ex);
		} catch (Exception e) {
			LOG.error("printWriter", e);
		} finally {
			if (writer != null)
				writer.close();
		}
	}

}