package com.xmsy.common.util.beantools;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * 
 * .Bean操作公共库，继承于Spring BeanUtils
 *
 * @author chenjisi
 * @date 2017年1月2日 下午11:05:50
 * @Description: TODO
 */
public abstract class BeanUtilsEx extends BeanUtils {

	private static Logger logger = LoggerFactory.getLogger(BeanUtilsEx.class);

	/**
	 * 获取对象中值为null的属性名称
	 * 
	 * @param source
	 * @return
	 */
	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (null == srcValue) {
				emptyNames.add(pd.getName());
			}
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	/**
	 * 拷贝对象属性，忽略值为空的属性
	 * 
	 * @param source
	 *            - source bean
	 * @param target
	 *            - target bean
	 * @throws BeansException
	 *             把source中不为null的属性复制到target中
	 */
	public static void copyPropertiesIgnoreNull(Object source, Object target) throws BeansException {
		copyPropertiesIgnoreNull(source, target, null, (String[]) null);
	}

	/**
	 * 拷贝对象属性，忽略值为空的属性
	 * 
	 * @param source
	 *            - source bean
	 * @param target
	 *            - target bean
	 * @param ignoreProperties-
	 *            跳过的属性
	 * @throws BeansException
	 */
	public static void copyPropertiesIgnoreNull(Object source, Object target, String... ignoreProperties)
			throws BeansException {
		copyPropertiesIgnoreNull(source, target, null, ignoreProperties);
	}


	/**
	 * 拷贝对象属性，忽略值为空的属性
	 * 
	 * @param source
	 *            - source bean
	 * @param target
	 *            - target bean
	 * @param editable
	 *            - 指定对象类型，可以传入null
	 * @param ignoreProperties
	 *            - 跳过的属性
	 * @throws BeansException
	 */

	private static void copyPropertiesIgnoreNull(Object source, Object target, Class<?> editable,
			String... ignoreProperties) throws BeansException {

		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();
		if (editable != null) {
			if (!editable.isInstance(target)) {
				throw new IllegalArgumentException("Target class [" + target.getClass().getName()
						+ "] not assignable to Editable class [" + editable.getName() + "]");
			}
			actualEditable = editable;
		}
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

		for (PropertyDescriptor targetPd : targetPds) {
			Method writeMethod = targetPd.getWriteMethod();
			if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null) {
					Method readMethod = sourcePd.getReadMethod();
					if (readMethod != null && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0],
							readMethod.getReturnType())) {
						try {
							if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
								readMethod.setAccessible(true);
							}
							Object value = readMethod.invoke(source);
							if (null != value) {
								if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
									writeMethod.setAccessible(true);
								}
								writeMethod.invoke(target, value);
							}
							// CHECKSTYLE.OFF: IllegalCatch
						} catch (Throwable ex) {
							logger.error(
									"【BeanUtilsEx->copyPropertiesIgnoreNull】 source:{} target:{} editable:{} ignoreProperties:{}",
									source, target, editable, ignoreProperties, ex);
							// CHECKSTYLE.ON: IllegalCatch
							throw new FatalBeanException(
									"Could not copy property '" + targetPd.getName() + "' from source to target", ex);
						}
					}
				}
			}
		}
	}
}
