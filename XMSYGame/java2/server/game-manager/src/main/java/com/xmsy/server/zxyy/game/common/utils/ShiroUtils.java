package com.xmsy.server.zxyy.game.common.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.xmsy.server.zxyy.game.common.exception.RRException;
import com.xmsy.server.zxyy.game.modules.manager.sys.entity.SysUserEntity;

/**
 * Shiro工具类
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年11月12日 上午9:49:19
 */
public class ShiroUtils {

	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	public static SysUserEntity getUserEntity() {
		return (SysUserEntity)SecurityUtils.getSubject().getPrincipal();
	}

	public static Long getUserId() {
		return getUserEntity().getUserId();
	}
	
	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	public static boolean isLogin() {
		return SecurityUtils.getSubject().getPrincipal() != null;
	}

	public static String getKaptcha(String key) {
		Object kaptcha = getSessionAttribute(key);
		if(kaptcha == null){
			throw new RRException("验证码已失效");
		}
		getSession().removeAttribute(key);
		return kaptcha.toString();
	}

}
