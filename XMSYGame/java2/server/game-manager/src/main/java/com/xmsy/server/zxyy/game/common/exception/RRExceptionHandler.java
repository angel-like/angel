package com.xmsy.server.zxyy.game.common.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.xmsy.common.define.constant.ResultDef;
import com.xmsy.common.define.exception.ParamInvalidException;
import com.xmsy.server.zxyy.game.common.utils.R;

/**
 * 异常处理器
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年10月27日 下午10:16:19
 */
@RestControllerAdvice
public class RRExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(RRException.class)
	public R handleRRException(RRException e) {
		R r = new R();
		r.put("code", e.getCode());
		r.put("msg", e.getMessage());
		return r;
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public R handlerNoFoundException(Exception e) {
		logger.error(e.getMessage(), e);
		return R.error(404, "路径不存在，请检查路径是否正确");
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public R handleDuplicateKeyException(DuplicateKeyException e) {
		logger.error(e.getMessage(), e);
		return R.error("数据库中已存在该记录");
	}

	@ExceptionHandler(AuthorizationException.class)
	public R handleAuthorizationException(AuthorizationException e) {
		logger.error(e.getMessage(), e);
		return R.error("没有权限，请联系管理员授权");
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		StringBuilder sb = new StringBuilder("");
		for (ObjectError error : e.getBindingResult().getAllErrors()) {
			sb.append("[").append(error.getDefaultMessage()).append("] ");
		}
		logger.error(e.getMessage(), e);
		return R.error(ResultDef.FAIL, sb.toString());
	}

	@ExceptionHandler(ParamInvalidException.class)
	public R handleParamInvalidException(ParamInvalidException e) {
		logger.error(e.getMessage(), e);
		return R.error(ResultDef.FAIL, e.getMsg());
	}

	@ExceptionHandler(Exception.class)
	public R handleException(Exception e) {
		logger.error(e.getMessage(), e);
		return R.error();
	}
}
