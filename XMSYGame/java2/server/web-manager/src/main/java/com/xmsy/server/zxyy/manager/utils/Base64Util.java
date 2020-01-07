package com.xmsy.server.zxyy.manager.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;

public class Base64Util {

	public static void main(String[] args) throws Exception {
		 String text = "p^_^😁";
		//编码
		 String encodedText = base64Encoder(text);
		System.out.println(encodedText);
		//解码
		System.out.println(base64Decoder("cGluZ15fXvCfmIE="));

	}
	/**
	 * 编码
	 * @param text
	 * @return
	 */
	public static String base64Encoder(String text) {
		if(text==null || "".equals(text.trim())) {
			return "";
		}
		Base64.Encoder encoder = Base64.getEncoder();
		byte[] textByte;
		String encodedText ="";
		try {
			textByte = text.getBytes("UTF-8");
			encodedText = encoder.encodeToString(textByte);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodedText;
	}
	/**
	 * 解码
	 * @param encodedText
	 * @return
	 */
	public static String base64Decoder(String encodedText) {
		if(encodedText==null || "".equals(encodedText.trim())) {
			return "";
		}
		Base64.Decoder decoder = Base64.getDecoder();
		String text = "";
		try {
			text = new String(decoder.decode(encodedText), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return text;
	}
	/**
	 * 对MultipartFile文件进行编码
	 * @param file
	 * @return
	 */
	public static String base64EncoderFile(MultipartFile file) {
		InputStream inputFromFile=null;
		String encodedString=null;
		try {
			inputFromFile = file.getInputStream();
			byte[] src = new byte[inputFromFile.available()];
			inputFromFile.read(src);
			encodedString = Base64.getEncoder().encodeToString(src);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return encodedString;
	}
}
