package com.xmsy.server.serverbase.exceptionhandler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.xmsy.common.define.constant.ResultDef;
import com.xmsy.common.define.exception.InnerErrorException;
import com.xmsy.common.define.exception.NetworkErrorException;
import com.xmsy.common.define.exception.ParamInvalidException;
import com.xmsy.common.define.exception.PermissionDeniedException;
import com.xmsy.common.define.exception.UnknownErrorException;
import com.xmsy.common.define.result.GlobalResult;
import com.xmsy.common.define.result.ResultUtils;

/**
 * 
 * .全局异常处理
 *
 * @author chenjisi
 * @date 2017年12月1日 下午2:49:56
 * @Description: TODO
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * .非法参数异常
	 */
	@ExceptionHandler(ParamInvalidException.class)
	@ResponseStatus(HttpStatus.OK)
	public GlobalResult<?> handleParamInvalidException(HttpServletRequest request, ParamInvalidException e) {
		logger.error("path: {} errorCode: {} errorMsg: {}", e.getClass().getName(), e.getCode(), e.getMsg(), e);
		return ResultUtils.getErrorResult(e.getCode(), e.getMsg(), request.getRequestURI());
	}

	/**
	 * .权限异常
	 */
	@ExceptionHandler(PermissionDeniedException.class)
	@ResponseStatus(HttpStatus.OK)
	public GlobalResult<?> handlePermissionDeniedException(HttpServletRequest request, PermissionDeniedException e) {
		logger.error("path: {} errorCode: {} errorMsg: {}", e.getClass().getSimpleName(), e.getCode(), e.getMsg(), e);
		return ResultUtils.getErrorResult(e.getCode(), e.getMsg(), request.getRequestURI());
	}

	/**
	 * .未知异常
	 */
	@ExceptionHandler(UnknownErrorException.class)
	@ResponseStatus(HttpStatus.OK)
	public GlobalResult<?> handleUnknownErrorException(HttpServletRequest request, UnknownErrorException e) {
		logger.error("path: {} errorCode: {} errorMsg: {}", e.getClass().getSimpleName(), e.getCode(), e.getMsg(), e);
		return ResultUtils.getErrorResult(e.getCode(), e.getMsg(), request.getRequestURI());

	}

	/**
	 * .网络异常
	 */
	@ExceptionHandler(NetworkErrorException.class)
	@ResponseStatus(HttpStatus.OK)
	public GlobalResult<?> handleNetworkErrorException(HttpServletRequest request, NetworkErrorException e) {
		logger.error("path: {} errorCode: {} errorMsg: {}", e.getClass().getSimpleName(), e.getCode(), e.getMsg(), e);
		return ResultUtils.getErrorResult(e.getCode(), e.getMsg(), request.getRequestURI());

	}

	/**
	 * .系统内部错误异常
	 */
	@ExceptionHandler(InnerErrorException.class)
	@ResponseStatus(HttpStatus.OK)
	public GlobalResult<?> handleInnerErrorException(HttpServletRequest request, InnerErrorException e) {
		logger.error("path: {} errorCode: {} errorMsg: {}", e.getClass().getSimpleName(), e.getCode(), e.getMsg(), e);
		return ResultUtils.getErrorResult(e.getCode(), e.getMsg(), request.getRequestURI());

	}

	/**
	 * .程序中未能考虑到的异常
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.OK)
	public GlobalResult<?> handleException(HttpServletRequest request, Exception e) {
		logger.error("path: {} ", e.getClass().getSimpleName(), e);
		return ResultUtils.getErrorResult(ResultDef.INNER_ERROR_CODE, e.getMessage(), request.getRequestURI());
	}

}
