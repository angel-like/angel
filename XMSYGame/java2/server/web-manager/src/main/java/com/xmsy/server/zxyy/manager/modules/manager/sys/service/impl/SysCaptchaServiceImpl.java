/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.xmsy.server.zxyy.manager.modules.manager.sys.service.impl;

import java.awt.image.BufferedImage;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.code.kaptcha.Producer;
import com.xmsy.server.zxyy.manager.cache.LocalContentCache;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.modules.manager.sys.dao.SysCaptchaDao;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.SysCaptchaEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sys.service.SysCaptchaService;

/**
 * 验证码
 *
 * @author aleng
 * @since 2.0.0 2018-02-10
 */
@Service("sysCaptchaService")
public class SysCaptchaServiceImpl extends ServiceImpl<SysCaptchaDao, SysCaptchaEntity> implements SysCaptchaService {
	@Autowired
	private Producer producer;

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Resource
	private LocalContentCache localContentCache;

	public static final String KAPTCHA_KEY_NAME = "captcha:";

	@Override

	public BufferedImage getCaptcha(String uuid) {
		if (StringUtils.isBlank(uuid)) {
			throw new RRException("uuid不能为空");
		}
		// 生成文字验证码
		String code = producer.createText();
		localContentCache.putPhoneVerificationCode(KAPTCHA_KEY_NAME + uuid, code);
		return producer.createImage(code);
	}

	@Override
	public boolean validate(String uuid, String code) {
		String validateCode = String.valueOf(localContentCache.getPhoneVerificationCode(KAPTCHA_KEY_NAME + uuid));
		// 删除验证码
		redisTemplate.delete(KAPTCHA_KEY_NAME + uuid);
		if (validateCode.equalsIgnoreCase(code)) {
			return true;
		}
		return false;
	}
}
