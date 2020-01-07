package com.xmsy.server.zxyy.webhome.modules.manager.userotp.controller;

import java.io.IOException;
import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.zxing.WriterException;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.common.util.verificationtools.GoogleAuthenticator;
import com.xmsy.server.zxyy.webhome.common.annotation.SysLog;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.sys.entity.SysUserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sys.service.SysUserService;
import com.xmsy.server.zxyy.webhome.modules.manager.userotp.entity.UserOtpEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userotp.service.UserOtpService;

/**
 * 用户动态码
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-06 10:55:50
 */
@RestController
@RequestMapping("userotp/userotp")
public class UserOtpController {
	@Autowired
	private UserOtpService userOtpService;
	@Autowired
	private SysUserService sysUserService;
	private static final String OTP = "棋牌";

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("userotp:userotp:list")
	public R list(UserOtpEntity userOtp, PageParam pageParam) {
		String userName = userOtp.getUsername();
		userOtp.setUsername(null);
		Page<UserOtpEntity> result = new Page<UserOtpEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<UserOtpEntity> entityWrapper = new EntityWrapper<UserOtpEntity>(userOtp);
		entityWrapper.orderBy("id", false);
		userOtpService.selectPage(result, entityWrapper.like("username", userName));
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("userotp:userotp:info")
	public R info(@PathVariable("id") Long id) {
		UserOtpEntity userOtp = userOtpService.selectById(id);
		return R.ok().put("userotp", userOtp);
	}

	/**
	 * 生成otp二维码
	 * 
	 * @throws IOException
	 * @throws WriterException
	 */
	@SysLog("生成otp")
	@RequestMapping("/info/otp/{userId}")
	@RequiresPermissions("userotp:userotp:info")
	public R otp(@PathVariable("userId") Long userId) throws WriterException, IOException {
		SysUserEntity sysUserEntity = sysUserService.selectById(userId);
		if (null == sysUserEntity) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		Map<String, Object> result = GoogleAuthenticator.getAuthenticator(OTP);
		result.put("username", sysUserEntity.getUsername());
		return R.ok().put("userotp", result);
	}

	/**
	 * 保存
	 */
	@SysLog("OTP保存")
	@RequestMapping("/save")
	@RequiresPermissions("userotp:userotp:save")
	public R save(@RequestBody @Valid UserOtpEntity userOtp) {
		UserOtpEntity param = new UserOtpEntity();
		param.setUsername(userOtp.getUsername());
		Wrapper<UserOtpEntity> wrapper = new EntityWrapper<UserOtpEntity>(param);
		int userOtpCount = userOtpService.selectCount(wrapper);
		if (userOtpCount > 0) {
			throw new RRException(ErrorCode.OtpErrorCode.OTP_EXIST_ERRO.getErrMsg(),
					ErrorCode.OtpErrorCode.OTP_EXIST_ERRO.getCode());
		}
		userOtpService.insert(userOtp);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@SysLog("OTP修改")
	@RequestMapping("/update")
	@RequiresPermissions("userotp:userotp:update")
	public R update(@RequestBody UserOtpEntity userOtp) {
		userOtp.setUsername(null);
		userOtp.setOtpSecret(null);
		userOtpService.updateById(userOtp);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("userotp:userotp:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			userOtpService.deleteById(id);
		}
		return R.ok();
	}

}
