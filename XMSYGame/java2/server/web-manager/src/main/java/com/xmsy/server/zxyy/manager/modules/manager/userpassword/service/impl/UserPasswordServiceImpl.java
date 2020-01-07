package com.xmsy.server.zxyy.manager.modules.manager.userpassword.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.util.securitytools.MD5Util;
import com.xmsy.common.util.verificationtools.GoogleAuthenticator;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.Query;
import com.xmsy.server.zxyy.manager.modules.manager.user.dao.UserDao;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserPasswordParam;
import com.xmsy.server.zxyy.manager.modules.manager.userotp.entity.UserOtpEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userotp.service.UserOtpService;
import com.xmsy.server.zxyy.manager.modules.manager.userpassword.dao.UserPasswordDao;
import com.xmsy.server.zxyy.manager.modules.manager.userpassword.entity.UserPasswordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userpassword.service.UserPasswordService;
import com.xmsy.server.zxyy.manager.utils.JwtUtil;
import com.xmsy.server.zxyy.manager.utils.PasswordVerification;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("userPasswordService")
public class UserPasswordServiceImpl extends ServiceImpl<UserPasswordDao, UserPasswordEntity>
		implements UserPasswordService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserOtpService userOtpService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Page<UserPasswordEntity> page = this.selectPage(new Query<UserPasswordEntity>(params).getPage(),
				new EntityWrapper<UserPasswordEntity>());

		return new PageUtils(page);
	}

	@Override
	public void insertPassword(Long userId, String passWord, String takePassWord) {
		UserPasswordEntity userPasswordEntity = new UserPasswordEntity();
		userPasswordEntity.setUserId(userId);
		userPasswordEntity.setBankPassword(takePassWord);// 取款密码
		userPasswordEntity.setLoginPassword(passWord);// 登陆密码
		baseMapper.insert(userPasswordEntity);
	}

	@Override
	public Boolean validateLoginPassword(String passWord, Long userId) {
		UserPasswordEntity userPasswordEntity = new UserPasswordEntity();
		userPasswordEntity.setUserId(userId);
		List<UserPasswordEntity> list = baseMapper
				.selectList(new EntityWrapper<UserPasswordEntity>(userPasswordEntity));
		if (CollectionUtils.isEmpty(list)) {
			throw new RRException(ErrorCode.PasswordErrorCode.USER_PASSWORD_ISNULL_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.USER_PASSWORD_ISNULL_ERRO.getCode());
		}
		return MD5Util.verify(passWord, list.get(0).getLoginPassword());
	}

	@Override
	public Boolean validateBankPassword(String passWord, Long userId) {
		UserPasswordEntity userPasswordEntity = new UserPasswordEntity();
		userPasswordEntity.setUserId(userId);
		List<UserPasswordEntity> list = baseMapper
				.selectList(new EntityWrapper<UserPasswordEntity>(userPasswordEntity));
		if (CollectionUtils.isEmpty(list)) {
			throw new RRException(ErrorCode.PasswordErrorCode.USER_PASSWORD_ISNULL_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.USER_PASSWORD_ISNULL_ERRO.getCode());
		}
		if (StringUtils.isEmpty(list.get(0).getBankPassword())) {
			throw new RRException(ErrorCode.PasswordErrorCode.PASSWORD_ISNULL_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.PASSWORD_ISNULL_ERRO.getCode());
		}
		return MD5Util.verify(passWord, list.get(0).getBankPassword());
	}

	// 修改用户登陆密码
	@Override
	public void editLogin(UserPasswordParam param, String ip, String username) {

		if (!param.getPassword().equals(param.getConfirmPassword())) {
			throw new RRException(ErrorCode.PasswordErrorCode.PASSWORD_ISUSED_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.PASSWORD_ISUSED_ERRO.getCode());
		}
		if (!PasswordVerification.regularVerification(param.getPassword())) {
			throw new RRException(ErrorCode.PasswordErrorCode.LOGIN_PASSWORD_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.LOGIN_PASSWORD_ERRO.getCode());
		}
		Wrapper<UserOtpEntity> wrapper = new EntityWrapper<UserOtpEntity>();
		wrapper.eq("username", username);
		UserOtpEntity userOtp = userOtpService.selectOne(wrapper);
		if (!"admin".equals(username)) {
			if (!PasswordVerification.otpVerification(param.getOtp())) {
				throw new RRException(ErrorCode.OtpErrorCode.OTP_FORMAT_ERRO.getErrMsg(),
						ErrorCode.OtpErrorCode.OTP_FORMAT_ERRO.getCode());
			}
			if (userOtp == null || userOtp.getBindIp() == null || "".equals(userOtp.getBindIp())) {
				throw new RRException(ErrorCode.OtpErrorCode.OTP_KEYS_UNBOUND_ERRO.getErrMsg(),
						ErrorCode.OtpErrorCode.OTP_KEYS_UNBOUND_ERRO.getCode());
			}
			if (!Arrays.asList(userOtp.getBindIp().split(",")).contains(ip)) {
				throw new RRException(ErrorCode.OtpErrorCode.IP_NOT_BOUND_OTP_KEYS_ERRO.getErrMsg(),
						ErrorCode.OtpErrorCode.IP_NOT_BOUND_OTP_KEYS_ERRO.getCode());
			}
			if (!GoogleAuthenticator.authcode(param.getOtp(), userOtp.getOtpSecret())) {
				throw new RRException(ErrorCode.OtpErrorCode.OTP_VALIDATION_FAILURE_ERRO.getErrMsg(),
						ErrorCode.OtpErrorCode.OTP_VALIDATION_FAILURE_ERRO.getCode());
			}
		}
		UserPasswordEntity userPasswordEntity = new UserPasswordEntity();
		userPasswordEntity.setUserId(param.getUserId());
		userPasswordEntity = baseMapper.selectOne(userPasswordEntity);
		if (userPasswordEntity == null || userPasswordEntity.getId() <= 0) {
			userPasswordEntity = new UserPasswordEntity();
			userPasswordEntity.setUserId(param.getUserId());
			userPasswordEntity.setLoginPassword(MD5Util.generate(param.getPassword()));// 登陆密码
			baseMapper.insert(userPasswordEntity);
		} else {
			userPasswordEntity.setLoginPassword(MD5Util.generate(param.getPassword()));// 登陆密码
			baseMapper.updateById(userPasswordEntity);
		}
	}

	@Override
	public void editBank(UserPasswordParam param) {
		if (!param.getPassword().equals(param.getConfirmPassword())) {
			throw new RRException(ErrorCode.PasswordErrorCode.PASSWORD_ISUSED_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.PASSWORD_ISUSED_ERRO.getCode());
		}
		if (!PasswordVerification.regularVerification(param.getPassword())) {
			throw new RRException(ErrorCode.PasswordErrorCode.TAKE_PASSWORD_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.TAKE_PASSWORD_ERRO.getCode());
		}

		UserPasswordEntity userPasswordEntity = new UserPasswordEntity();
		userPasswordEntity.setUserId(param.getUserId());
		userPasswordEntity = baseMapper.selectOne(userPasswordEntity);
		if (userPasswordEntity == null || userPasswordEntity.getId() <= 0) {
			userPasswordEntity = new UserPasswordEntity();
			userPasswordEntity.setUserId(param.getUserId());
			userPasswordEntity.setBankPassword(MD5Util.generate(param.getPassword()));// 取款密码
			baseMapper.insert(userPasswordEntity);
		} else {
			userPasswordEntity.setBankPassword(MD5Util.generate(param.getPassword()));// 取款密码
			baseMapper.updateById(userPasswordEntity);
		}

	}

	// 修改用户登陆密码
	@Override
	public void updateLoginPassWord(String token, String verificationWord, String passWord) {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = userDao.selectById(userId);
		UserPasswordEntity userPasswordEntity = new UserPasswordEntity();
		userPasswordEntity.setUserId(userId);
		if (StringUtils.isEmpty(user.getUserName()) && !StringUtils.isEmpty(user.getUnionId())) {
			// 该账户为未完善信息的第三方登陆
			throw new RRException(ErrorCode.UserErrorCode.USER_THIRD_ACCOUNT_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_THIRD_ACCOUNT_ERRO.getCode());
		}
		List<UserPasswordEntity> list = baseMapper
				.selectList(new EntityWrapper<UserPasswordEntity>(userPasswordEntity));
		if (CollectionUtils.isEmpty(list)) {
			throw new RRException(ErrorCode.PasswordErrorCode.USER_PASSWORD_ISNULL_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.USER_PASSWORD_ISNULL_ERRO.getCode());
		}
		// 验证密码
		Boolean result = validateLoginPassword(verificationWord, userId);
		if (!result) {
			throw new RRException(ErrorCode.PasswordErrorCode.PASSWORD_INVALID_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.PASSWORD_INVALID_ERRO.getCode());
		}
		list.get(0).setLoginPassword(MD5Util.generate(passWord));
		baseMapper.updateById(list.get(0));// 修改用户密码

	}

	// 修改用户取款密码
	@Override
	public void updateTakeMoneyPassWord(String token, String verificationWord, String passWord) {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = userDao.selectById(userId);
		UserPasswordEntity userPasswordEntity = new UserPasswordEntity();
		userPasswordEntity.setUserId(userId);
		log.info("[updateTakeMoneyPassWord] 是否是未完善信息的第三方登陆 {}",
				StringUtils.isEmpty(user.getUserName()) && !StringUtils.isEmpty(user.getUnionId()));
		if (StringUtils.isEmpty(user.getUserName()) && !StringUtils.isEmpty(user.getUnionId())) {
			// 该账户为未完善信息的第三方登陆
			throw new RRException(ErrorCode.UserErrorCode.USER_THIRD_ACCOUNT_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_THIRD_ACCOUNT_ERRO.getCode());
		}
		List<UserPasswordEntity> list = baseMapper
				.selectList(new EntityWrapper<UserPasswordEntity>(userPasswordEntity));
		if (CollectionUtils.isEmpty(list)) {
			throw new RRException(ErrorCode.PasswordErrorCode.USER_PASSWORD_ISNULL_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.USER_PASSWORD_ISNULL_ERRO.getCode());
		}
		// 验证密码
		Boolean result = validateBankPassword(verificationWord, userId);
		log.info("[updateTakeMoneyPassWord] result {}", result);
		if (!result) {
			throw new RRException(ErrorCode.PasswordErrorCode.PASSWORD_INVALID_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.PASSWORD_INVALID_ERRO.getCode());
		}
		list.get(0).setBankPassword(MD5Util.generate(passWord));
		baseMapper.updateById(list.get(0));// 修改用户密码

	}
}
