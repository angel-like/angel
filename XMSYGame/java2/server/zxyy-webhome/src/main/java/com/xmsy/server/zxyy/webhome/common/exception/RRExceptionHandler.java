package com.xmsy.server.zxyy.webhome.common.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.xmsy.common.define.constant.ResultDef;
import com.xmsy.common.define.exception.OrderRepeatException;
import com.xmsy.common.define.exception.ParamInvalidException;
import com.xmsy.server.zxyy.webhome.common.utils.R;

import lombok.extern.slf4j.Slf4j;

/**
 * 异常处理器
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年10月27日 下午10:16:19
 */
@Slf4j
@RestControllerAdvice
public class RRExceptionHandler {

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(RRException.class)
	public R handleRRException(RRException e) {
		log.error(e.getMessage(), e);
		R r = new R();
		r.put("code", e.getCode());
		r.put("msg", e.getMsg());
		return r;
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public R handlerNoFoundException(Exception e) {
		log.error(e.getMessage(), e);
		return R.error(404, "路径不存在，请检查路径是否正确");
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public R handleDuplicateKeyException(DuplicateKeyException e) {
		log.error(e.getMessage(), e);
		return R.error("数据库中已存在该记录");
	}

	@ExceptionHandler(AuthorizationException.class)
	public R handleAuthorizationException(AuthorizationException e) {
		log.error(e.getMessage(), e);
		return R.error("没有权限，请联系管理员授权");
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		StringBuilder sb = new StringBuilder("");
		for (ObjectError error : e.getBindingResult().getAllErrors()) {
			sb.append("[").append(error.getDefaultMessage()).append("] ");
		}
		log.error(e.getMessage(), e);
		return R.error(ResultDef.PARAM_INVALID_CODE, sb.toString());
	}

	@ExceptionHandler(ParamInvalidException.class)
	public R handleParamInvalidException(ParamInvalidException e) {
		log.error(e.getMessage(), e);
		return R.error(ResultDef.PARAM_INVALID_CODE, e.getMsg());
	}

	@ExceptionHandler(OrderRepeatException.class)
	public R handleOrderRepeatException(OrderRepeatException e) {
		log.error(e.getMessage(), e);
		return R.error(e.getCode(), e.getMsg());
	}

	@ExceptionHandler(MybatisPlusException.class)
	public R handleMybatisPlusException(Exception e) {
		log.error(e.getMessage(), e);
		return R.error();
	}

	@ExceptionHandler(Exception.class)
	public R handleException(Exception e) {
		log.error(e.getMessage(), e);
		return R.error();
	}
}
