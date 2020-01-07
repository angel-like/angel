
package com.xmsy.server.zxyy.game.utils;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * 
 * .结合hibernateValidate 检验对象
 *
 * @author chenjisi
 * @date 2017年11月7日 下午2:03:29 
 * @Description: TODO
 */
public class EntityValidateUtil {

	public static final String MSG = "EntityValidateUtil->validateModel检验的参数对象为null";

	public static String validateModel(Object obj) {// 验证某一个对象
		if (null == obj) {
			return MSG;
		}
		StringBuffer buffer = new StringBuffer(64);// 用于存储验证后的错误信息
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj);// 验证某个对象,，其实也可以只验证其中的某一个属性的
		Iterator<ConstraintViolation<Object>> iter = constraintViolations.iterator();
		while (iter.hasNext()) {
			String message = iter.next().getMessage();
			buffer.append(message).append(",");
		}
		return buffer.toString();
	}

}