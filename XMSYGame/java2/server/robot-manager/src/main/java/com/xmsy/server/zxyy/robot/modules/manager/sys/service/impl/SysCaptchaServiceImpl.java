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

package com.xmsy.server.zxyy.robot.modules.manager.sys.service.impl;

import java.awt.image.BufferedImage;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.code.kaptcha.Producer;
import com.xmsy.server.zxyy.robot.cache.EhCacheName;
import com.xmsy.server.zxyy.robot.common.exception.RRException;
import com.xmsy.server.zxyy.robot.modules.manager.sys.dao.SysCaptchaDao;
import com.xmsy.server.zxyy.robot.modules.manager.sys.entity.SysCaptchaEntity;
import com.xmsy.server.zxyy.robot.modules.manager.sys.service.SysCaptchaService;

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

	@Autowired
	private EhCacheCacheManager cacheCacheManager;

	@Override

	public BufferedImage getCaptcha(String uuid) {
		if (StringUtils.isBlank(uuid)) {
			throw new RRException("uuid不能为空");
		}
		// 生成文字验证码
		String code = producer.createText();
		cacheCacheManager.getCache(EhCacheName.CAPTCHA_CACHE).put(uuid, code);
		return producer.createImage(code);
	}

	@Override
	public boolean validate(String uuid, String code) {
		String validateCode = cacheCacheManager.getCache(EhCacheName.CAPTCHA_CACHE).get(uuid, String.class);
		if (null == validateCode) {
			return false;
		}
		// 删除验证码
		cacheCacheManager.getCache(EhCacheName.CAPTCHA_CACHE).evict(uuid);
		if (validateCode.equalsIgnoreCase(code)) {
			return true;
		}
		return false;
	}
}
