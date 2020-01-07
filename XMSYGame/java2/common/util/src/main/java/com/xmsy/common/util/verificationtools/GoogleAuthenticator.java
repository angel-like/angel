package com.xmsy.common.util.verificationtools;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

//Google  Authenticator

//只从google出了双重身份验证后，就方便了大家，等同于有了google一个级别的安全，但是我们该怎么使用google authenticator (双重身份验证)，

//测试方法：
//
//1、执行测试代码中的“genSecret”方法，将生成一个KEY（用户为testuser），URL打开是一张二维码图片。
//
//2、在手机中下载“GOOGLE身份验证器”。
//
//3、在身份验证器中配置账户，输入账户名（第一步中的用户testuser）、密钥（第一步生成的KEY），选择基于时间。
//
//4、运行authcode方法将key和要测试的验证码带进去（codes，key），就可以知道是不是正确的秘钥了！返回值布尔

//main我就不写了大家~~因为这个可以当做util工具直接调用就行了
//

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * google验证器
 * 
 * @author aleng
 * @date 2019年1月5日
 * @version 1.0
 */

public class GoogleAuthenticator {

	// 生成的key长度( Generate secret key length)
	public static final int SECRET_SIZE = 10;

	public static final String SEED = "g8GjEvTbW5oVSV7avL47357438reyhreyuryetredLDVKs2m0QN7vxRs2im5MDaNCWGmcD2rvcZx";
	// Java实现随机数算法
	public static final String RANDOM_NUMBER_ALGORITHM = "SHA1PRNG";
	// 最多可偏移的时间
	int window_size = 3; // default 3 - max 17

	/**
	 * set the windows size. This is an integer value representing the number of 30
	 * second windows we allow The bigger the window, the more tolerant of clock
	 * skew we are.
	 * 
	 * @param s
	 *            window size - must be >=1 and <=17. Other values are ignored
	 */
	public void setWindowSize(int s) {
		if (s >= 1 && s <= 17)
			window_size = s;
	}

	public static Boolean authcode(String codes, String savedSecret) {
		// enter the code shown on device. Edit this and run it fast before the
		// code expires!
		long code = Long.parseLong(codes);
		long t = System.currentTimeMillis();
		GoogleAuthenticator ga = new GoogleAuthenticator();
		ga.setWindowSize(15); // should give 5 * 30 seconds of grace...
		boolean r = ga.check_code(savedSecret, code, t);
		return r;
	}

	/**
	 * Generate a random secret key. This must be saved by the server and associated
	 * with the users account to verify the code displayed by Google Authenticator.
	 * The user must register this secret on their device. 生成一个随机秘钥
	 * 
	 * @return secret key
	 */
	public static String generateSecretKey() {
		SecureRandom sr = null;
		try {
			sr = SecureRandom.getInstance(RANDOM_NUMBER_ALGORITHM);
			sr.setSeed(Base64.decodeBase64(SEED));
			byte[] buffer = sr.generateSeed(SECRET_SIZE);
			Base32 codec = new Base32();
			byte[] bEncodedKey = codec.encode(buffer);
			String encodedKey = new String(bEncodedKey);
			return encodedKey;
		} catch (NoSuchAlgorithmException e) {
			// should never occur... configuration error
		}
		return null;
	}

	/**
	 * Return a URL that generates and displays a QR barcode. The user scans this
	 * bar code with the Google Authenticator application on their smartphone to
	 * register the auth code. They can also manually enter the secret if desired
	 * 
	 * @param user
	 *            user id (e.g. fflinstone)
	 * @param host
	 *            host or system that the code is for (e.g. myapp.com)
	 * @param secret
	 *            the secret that was previously generated for this user
	 * @return the URL for the QR code to scan
	 */
	public static String getQRBarcodeURL(String user, String host, String secret) {
		String format = "http://www.google.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=otpauth://totp/%s@%s?secret=%s";
		return String.format(format, user, host, secret);
	}

	/**
	 * 生成一个google身份验证器，识别的字符串，只需要把该方法返回值生成二维码扫描就可以了。
	 * 
	 * @param user
	 *            账号
	 * @param secret
	 *            密钥
	 * @return
	 */
	public static String getQRBarcode(String user, String secret) {
		String format = "otpauth://totp/%s?secret=%s";
		return String.format(format, user, secret);
	}

	/**
	 * Check the code entered by the user to see if it is valid 验证code是否合法
	 * 
	 * @param secret
	 *            The users secret.
	 * @param code
	 *            The code displayed on the users device
	 * @param t
	 *            The time in msec (System.currentTimeMillis() for example)
	 * @return
	 */
	public boolean check_code(String secret, long code, long timeMsec) {
		Base32 codec = new Base32();
		byte[] decodedKey = codec.decode(secret);
		// convert unix msec time into a 30 second "window"
		// this is per the TOTP spec (see the RFC for details)
		long t = (timeMsec / 1000L) / 30L;
		// Window is used to check codes generated in the near past.
		// You can use this value to tune how far you're willing to go.
		for (int i = -window_size; i <= window_size; ++i) {
			long hash;
			try {
				hash = verify_code(decodedKey, t + i);
			} catch (Exception e) {
				// Yes, this is bad form - but
				// the exceptions thrown would be rare and a static
				// configuration problem
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
				// return false;
			}
			if (hash == code) {
				return true;
			}
		}
		// The validation code is invalid.
		return false;
	}

	private static int verify_code(byte[] key, long t) throws NoSuchAlgorithmException, InvalidKeyException {
		byte[] data = new byte[8];
		long value = t;
		for (int i = 8; i-- > 0; value >>>= 8) {
			data[i] = (byte) value;
		}
		SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(signKey);
		byte[] hash = mac.doFinal(data);
		int offset = hash[20 - 1] & 0xF;
		// We're using a long because Java hasn't got unsigned int.
		long truncatedHash = 0;
		for (int i = 0; i < 4; ++i) {
			truncatedHash <<= 8;
			// We are dealing with signed bytes:
			// we just keep the first byte.
			truncatedHash |= (hash[offset + i] & 0xFF);
		}
		truncatedHash &= 0x7FFFFFFF;
		truncatedHash %= 1000000;
		return (int) truncatedHash;
	}

	/**
	 * 生成谷歌身份验证数据
	 * 
	 * @return
	 */
	public static Map<String, Object> getAuthenticator(String phone) throws WriterException, IOException {
		HashMap<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		// 生成随机密钥
		String secrets = generateSecretKey();
		// 把这个qrcode生成二维码，用google身份验证器扫描二维码就能添加成功
		String qrcode = getQRBarcode(phone, secrets);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("secrets", secrets);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(qrcode, BarcodeFormat.QR_CODE, 300, 300, hints);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BufferedImage img = MatrixToImageWriter.toBufferedImage(bitMatrix);
		ImageIO.write(img, "png", out);
		byte[] bytes = out.toByteArray();
		String imgBase = cn.hutool.core.codec.Base64.encode(bytes);
		String result = "data:image/png;base64," + imgBase;
		map.put("qrcode", result);
		return map;
	}

	public static void main(String[] args) throws WriterException, IOException {
		System.out.println(getAuthenticator("15880913652"));
		// 位数必须为6位
		System.out.println(authcode("952214", "4MJFD7N3NHLF2SBP"));
	}
}