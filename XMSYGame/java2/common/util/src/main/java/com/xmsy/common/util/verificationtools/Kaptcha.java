package com.xmsy.common.util.verificationtools;

import java.awt.image.BufferedImage;
import java.util.Properties;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
/**
 * .验证码模块
 * @author chenjisi
 * @since 2017年8月1日
 */
public enum Kaptcha {
	
	INSTANCE {
		@Override
		public String createText() {
			return kaptcha.createText();
		}

		@Override
		public BufferedImage createImage(String text) {
			return kaptcha.createImage(text);
		}
	};
	
	private static DefaultKaptcha kaptcha = null;
	static {
		Properties properties = new Properties();
		properties.put("kaptcha.border", "no");
		properties.put("kaptcha.textproducer.font.color", "black");
		properties.put("kaptcha.textproducer.char.space", "5");
		Config config = new Config(properties);
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		defaultKaptcha.setConfig(config);
		kaptcha = defaultKaptcha;
	}
	public abstract String createText();
	public abstract BufferedImage createImage(String text);
}
