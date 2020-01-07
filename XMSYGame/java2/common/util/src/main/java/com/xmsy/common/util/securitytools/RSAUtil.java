package com.xmsy.common.util.securitytools;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import com.alibaba.fastjson.JSONObject;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class RSAUtil {

	// 生成秘钥对
	public static KeyPair getKeyPair() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(2048);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		return keyPair;
	}

	// 获取公钥(Base64编码)
	public static String getPublicKey(KeyPair keyPair) {
		PublicKey publicKey = keyPair.getPublic();
		byte[] bytes = publicKey.getEncoded();
		return byte2Base64(bytes);
	}

	// 获取私钥(Base64编码)
	public static String getPrivateKey(KeyPair keyPair) {
		PrivateKey privateKey = keyPair.getPrivate();
		byte[] bytes = privateKey.getEncoded();
		return byte2Base64(bytes);
	}

	// 将Base64编码后的公钥转换成PublicKey对象
	public static PublicKey string2PublicKey(String pubStr) throws Exception {
		byte[] keyBytes = base642Byte(pubStr);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	// 将Base64编码后的私钥转换成PrivateKey对象
	public static PrivateKey string2PrivateKey(String priStr) throws Exception {
		byte[] keyBytes = base642Byte(priStr);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	// 公钥加密
	public static byte[] publicEncrypt(byte[] content, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] bytes = cipher.doFinal(content);
		return bytes;
	}

	// 私钥解密
	public static byte[] privateDecrypt(byte[] content, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] bytes = cipher.doFinal(content);
		return bytes;
	}

	// 字节数组转Base64编码
	public static String byte2Base64(byte[] bytes) {
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(bytes);
	}

	// Base64编码转字节数组
	public static byte[] base642Byte(String base64Key) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		return decoder.decodeBuffer(base64Key);
	}

	/**
	 * .加密
	 * 
	 * @param contentStr
	 *            原文
	 * @param publicKeyStr
	 *            公钥字符串
	 * @return 密文
	 * @throws Exception
	 */
	public static String getPublicEncryptStr(String contentStr, String publicKeyStr) throws Exception {
		// String 类型的publicKey转publicKey对象
		PublicKey publicKey = string2PublicKey(publicKeyStr);
		byte[] content = publicEncrypt(contentStr.getBytes(), publicKey);
		return byte2Base64(content);
	}

	/**
	 * .解密
	 * 
	 * @param encryptStr
	 *            密文
	 * @param privateStr
	 *            私钥
	 * @return 原文
	 * @throws Exception
	 */
	public static String getPrivateDecryptStr(String encryptStr, String privateKeyStr) throws Exception {
		byte[] contentByte = base642Byte(encryptStr);
		// String 类型的privateKey转privateKey对象
		PrivateKey privateKey = string2PrivateKey(privateKeyStr);
		byte[] result = privateDecrypt(contentByte, privateKey);
		return new String(result);
	}

	// private static final String PUBLIC_KEY = "publicKey";
	// private static final String PRIVATE_KEY = "privateKey";

	// /**
	// * 获取RSA秘钥对
	// *
	// * @return
	// * @throws Exception
	// */
	// public static Map<String, Object> keyMap() throws Exception {
	// KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
	// keyPairGenerator.initialize(1024);
	// KeyPair keyPair = keyPairGenerator.generateKeyPair();
	// PublicKey publicKey = keyPair.getPublic();
	// PrivateKey privateKey = keyPair.getPrivate();
	//
	// Map<String, Object> keyMap = new HashMap<>(2);
	// keyMap.put(PUBLIC_KEY, publicKey);
	// keyMap.put(PRIVATE_KEY, privateKey);
	// return keyMap;
	// }
	//
	// /**
	// * 获取RSA公钥以及私钥分别保存到文件中
	// *
	// * @param keymap
	// * @throws Exception
	// */
	// public static void keyToFile(Map<String, Object> keymap) throws Exception {
	// PublicKey publicKey = (PublicKey) keymap.get(PUBLIC_KEY);
	// PrivateKey privateKey = (PrivateKey) keymap.get(PRIVATE_KEY);
	// String encryptBase64PublicKey = byte2Base64(publicKey.getEncoded());
	// String encryptBase64PrivateKey = byte2Base64(privateKey.getEncoded());
	// FileWriter fw = new FileWriter("src/main/resources/pubKey.txt");// 按需要保存路径，下同
	// FileWriter fw1 = new FileWriter("src/main/resources/priKey.txt");
	// fw.write(encryptBase64PublicKey);
	// fw.flush();
	// fw.close();
	// fw1.write(encryptBase64PrivateKey);
	// fw1.flush();
	// fw1.close();
	// }
	//
	// /**
	// * 从文件中获取公钥
	// *
	// * @return
	// * @throws Exception
	// */
	// public static RSAPublicKey getPublicKeyForFile() throws Exception {
	// File file = new File("src/main/resources/pubKey.txt");
	// FileInputStream fis = new FileInputStream(file);
	// BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	// String readLine = null;
	// StringBuffer sb = new StringBuffer();
	// while ((readLine = br.readLine()) != null) {
	// sb.append(readLine);
	// }
	// String publicKeyStr = sb.toString();
	// System.out.println("从文件获取到的公钥：" + publicKeyStr);
	// // Base64解密
	// byte[] decryptBase64 = base642Byte(publicKeyStr);
	// // 选取密钥编码规则
	// X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decryptBase64);
	// KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	// RSAPublicKey rsaPublicKey = (RSAPublicKey)
	// keyFactory.generatePublic(keySpec);
	// return rsaPublicKey;
	// }
	//
	// /**
	// * 从文件中获取私钥
	// *
	// * @return
	// * @throws Exception
	// */
	// public static RSAPrivateKey getPrivateKeyForFile() throws Exception {
	// FileInputStream fis = new
	// FileInputStream("src/main/resources/priKey.keyStore");
	// BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	// String readLine = null;
	// StringBuffer sb = new StringBuffer();
	// while ((readLine = br.readLine()) != null) {
	// sb.append(readLine);
	// }
	// String privateKey = sb.toString();
	// System.out.println("从文件获取到的私钥：" + privateKey);
	// // Base64解密
	// byte[] bytes = base642Byte(privateKey);
	// PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(bytes);
	// KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	// RSAPrivateKey rsaPrivateKey = (RSAPrivateKey)
	// keyFactory.generatePrivate(pkcs8EncodedKeySpec);
	// return rsaPrivateKey;
	// }

	public static void main(String[] args) throws Exception {
		// // 原文
		// Map<String, Object> map = new HashMap<>();
		// map.put("account", "test123456");
		// map.put("sex", "1");
		// map.put("coins", 50);
		// map.put("name", "test");
		// map.put("coinType", 0);
		// map.put("userId", 168829979);
		// map.put("timestamp", 1539520041421L);
		// System.out.println("原文：" + map);
		// // 生成秘钥对
		// KeyPair pair = getKeyPair();
		// // 获取公钥
		// String publicKeyStr = getPublicKey(pair);
		// // String 类型的publicKey转publicKey对象
		// PublicKey publicKey = string2PublicKey(publicKeyStr);
		// // 公钥加密
		// byte[] content = publicEncrypt(map.toString().getBytes(), publicKey);
		// String contentStr = byte2Base64(content);
		// System.out.println("公钥加密后的密文：" + contentStr);
		// // ==================================================================
		// // 获取私钥
		// String privateKeyStr = getPrivateKey(pair);
		// // 密文转byte数组
		// byte[] contentByte = base642Byte(contentStr);
		// // String 类型的privateKey转privateKey对象
		// PrivateKey privateKey = string2PrivateKey(privateKeyStr);
		// byte[] result = privateDecrypt(contentByte, privateKey);
		// // 结果
		// System.out.println("解密后的密文：" + new String(result));

		// ==================================================================
		String publicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAl1AoVCLeyVcqKi9uLF5qlG2JWQWTuARg3AhVFE3Z1gPH2oqArljDRP86vyAOdY+9vbA5J7r9gkOEBjnqhxzsOl47eTEwf5HxSonzMO0zfVPm0mrsgFN6SBgJepigajiU2MdyIxYMq/DFnbFXyJ4e5poBLgOzQgv4hN1tm3E315Ayn67Rxs+WjU7MgZxgEuzCwC47+7Hj37/TjMGQvwrqyXuphGKaKyvrVdvY3L5DoIn6D/sj+dsltoDi9CNBPtvt8a2znHOK8Xm4/wsQ4UfWU7rRDuFdoKExpkoA6jZYMXI5nJE03u5P5CPVohDKiYdd04l+elPAwkvq5w/TtVrZBQIDAQAB";
		JSONObject param = new JSONObject();
		param.put("account", "jmrp1uan9ercrmtz6");
		param.put("coins", 100);
		param.put("timestamp", System.currentTimeMillis());
		JSONObject result = new JSONObject();
		result.put("token", "Ob9EECTqTcOVcLELE4AxnQ");

		String encryptStr = getPublicEncryptStr(param.toString(), publicKeyStr);
		result.put("content", encryptStr);
		// String resultStr = getPrivateDecryptStr(encryptStr, privateKeyStr);
		System.out.println("新方法加密：" + result.toString());
		// System.out.println("新方法解密：" + resultStr);

		// =================================================================
		// System.out.println(RSAUtil.class.getClassLoader().getResource("").getPath());
		// System.out.println(System.getProperty("user.dir"));
		// keyToFile(keyMap());
	}
}
