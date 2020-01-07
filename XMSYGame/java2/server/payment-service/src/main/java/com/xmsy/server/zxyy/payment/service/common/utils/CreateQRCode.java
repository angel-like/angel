package com.xmsy.server.zxyy.payment.service.common.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import cn.hutool.core.codec.Base64;

/**
 * 根据路径生成二维码
 * 
 * 
 * 
 */
// 生成二维码
public class CreateQRCode {

	public static String create(String url) {
		final int width = 300;
		final int height = 300;

		// 定义二维码的参数
		HashMap<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);

		// 生成二维码
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			BufferedImage img = MatrixToImageWriter.toBufferedImage(bitMatrix);
			ImageIO.write(img, "png", out);
			byte[] bytes = out.toByteArray();
			String imgBase = Base64.encode(bytes);
			String result = "data:image/png;base64," + imgBase;
			return result;
		} catch (Exception e) {
			throw new RuntimeException("二维码生成失败");
		}

	}

}
