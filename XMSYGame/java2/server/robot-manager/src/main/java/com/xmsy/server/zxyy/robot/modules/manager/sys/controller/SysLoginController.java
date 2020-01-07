package com.xmsy.server.zxyy.robot.modules.manager.sys.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.robot.common.annotation.SysLog;
import com.xmsy.server.zxyy.robot.common.utils.R;
import com.xmsy.server.zxyy.robot.modules.manager.sys.entity.SysUserEntity;
import com.xmsy.server.zxyy.robot.modules.manager.sys.form.SysLoginForm;
import com.xmsy.server.zxyy.robot.modules.manager.sys.service.SysCaptchaService;
import com.xmsy.server.zxyy.robot.modules.manager.sys.service.SysUserService;
import com.xmsy.server.zxyy.robot.modules.manager.sys.service.SysUserTokenService;

import lombok.extern.slf4j.Slf4j;

/**
 * 登录相关
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年11月10日 下午1:15:31
 */
@Slf4j
@RestController
public class SysLoginController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserTokenService sysUserTokenService;
	@Autowired
	private SysCaptchaService sysCaptchaService;

	/**
	 * 验证码
	 */
	@GetMapping("captcha.jpg")
	public void captcha(HttpServletResponse response, String uuid) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");
		// 获取图片验证码
		BufferedImage image = sysCaptchaService.getCaptcha(uuid);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
		IOUtils.closeQuietly(out);
	}

	/**
	 * 登录
	 */
	@SysLog("登录")
	@PostMapping("/sys/login")
	public Map<String, Object> login(@RequestBody SysLoginForm form, HttpServletRequest httpServletRequest)
			throws IOException {
		log.info("[syslogin] SysLoginForm {}", form);
		// 用户信息
		SysUserEntity user = sysUserService.queryByUserName(form.getUsername());
		// 账号不存在、密码错误
		if (user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
			return R.error("账号或密码不正确");
		}
		// 账号锁定
		if (user.getStatus() == 0) {
			return R.error("账号已被锁定,请联系管理员");
		}
		// 生成token，并保存到数据库
		R r = sysUserTokenService.createToken(user.getUserId());
		return r;
	}

	/**
	 * 退出
	 */
	@SysLog("退出")
	@PostMapping("/sys/logout")
	public R logout() {
		sysUserTokenService.logout(getUserId());
		return R.ok();
	}

	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

}
