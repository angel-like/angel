package com.xmsy.network.qiniu;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * .七牛云上传
 * 
 * @author chenjisi
 * @since 2017年8月15日
 */
@SuppressWarnings("unused")
public class QiniuUpload {

	private static final Logger logger = LoggerFactory.getLogger(QiniuUpload.class);

	/**
	 * .字节流上传
	 * 
	 * @param args
	 */
	public static String uploadCallBack(String bucket, String callbackUrl, String accessKey, String secretKey,
			InputStream inputStream) {
		StringMap putPolicy = getPolicy(callbackUrl);
		String upToken = getUploadTokenCallBack(bucket, putPolicy, accessKey, secretKey);
		return uploadByInputStream(inputStream, upToken);
	}

	/**
	 * .字节流上传
	 * 
	 * @param args
	 */
	public static String upload(String bucket, String accessKey, String secretKey, InputStream inputStream) {
		String upToken = getUploadToken(bucket, accessKey, secretKey);
		return uploadByInputStream(inputStream, upToken);
	}

	/**
	 * .字节数组上传
	 * 
	 * @param args
	 */
	public static String uploadCallBack(String bucket, String callbackUrl, String accessKey, String secretKey,
			byte[] file) {
		StringMap putPolicy = getPolicy(callbackUrl);
		String upToken = getUploadTokenCallBack(bucket, putPolicy, accessKey, secretKey);
		return uploadByByteArray(file, upToken);
	}

	/**
	 * .字节数组上传
	 * 
	 * @param args
	 */
	public static String upload(String bucket, String accessKey, String secretKey, byte[] file) {
		String upToken = getUploadToken(bucket, accessKey, secretKey);
		return uploadByByteArray(file, upToken);
	}

	/**
	 * .获取上传策略
	 * 
	 * @param callbackUrl
	 * @return
	 */
	private static StringMap getPolicy(String callbackUrl) {
		StringMap putPolicy = new StringMap();
		putPolicy.put("callbackUrl", callbackUrl);
		putPolicy.put("callbackBody",
				"{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
		putPolicy.put("callbackBodyType", "application/json");
		return putPolicy;
	}

	/**
	 * .获取上传token
	 */
	public static String getUploadToken(String bucket, String accessKey, String secretKey) {
		String upToken = null;
		try {
			Auth auth = Auth.create(accessKey, secretKey);
			upToken = auth.uploadToken(bucket);
		} catch (Exception e) {
			logger.error("getUploadToken->Exception:", e);
		}
		return upToken;
	}

	/**
	 * .获取覆盖上传token
	 */
	private static String getUploadToken(String bucket, String key, String accessKey, String secretKey) {
		String upToken = null;
		try {
			Auth auth = Auth.create(accessKey, secretKey);
			upToken = auth.uploadToken(bucket, key);
		} catch (Exception e) {
			logger.error("getUploadToken->Exception:", e);
		}
		return upToken;
	}

	/**
	 * .获取上传回调token
	 */
	private static String getUploadTokenCallBack(String bucket, StringMap putPolicy, String accessKey,
			String secretKey) {
		String upToken = null;
		try {
			Auth auth = Auth.create(accessKey, secretKey);
			long expireSeconds = 3600;
			upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
		} catch (Exception e) {
			logger.error("getUploadToken->Exception:", e);
		}
		return upToken;
	}

	/**
	 * .字节流上传
	 */
	private static String uploadByInputStream(InputStream inputStream, String upToken) {
		// 构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone0());
		// ...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		// ...生成上传凭证，然后准备上传
		// 默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = null;
		try {
			Response response = uploadManager.put(inputStream, key, upToken, null, null);
			// 解析上传成功的结果
			// DefaultPutRet putRet = new
			// Gson().fromJson(response.bodyString(), DefaultPutRet.class);
			return response.bodyString();
		} catch (QiniuException ex) {
			Response r = ex.response;
			logger.error("uploadByByteArray->QiniuException: Response:{}", ex.response, ex);
			try {
				System.err.println(r.bodyString());
			} catch (QiniuException ex2) {
				logger.error("uploadByByteArray->QiniuException:", ex2);
			}
		}
		return null;
	}

	/**
	 * .字节数组上传
	 */
	public static String uploadByByteArray(byte[] file, String upToken) {
		// 构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration();
		// ...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		// ...生成上传凭证，然后准备上传
		// 默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = null;
		try {
			Response response = uploadManager.put(file, key, upToken);
			// 解析上传成功的结果
			// DefaultPutRet putRet = new
			// Gson().fromJson(response.bodyString(), DefaultPutRet.class);
			return response.bodyString();
		} catch (QiniuException ex) {
			Response r = ex.response;
			logger.error("uploadByByteArray->QiniuException: Response:{}", ex.response, ex);
			try {
				System.err.println(r.bodyString());
			} catch (QiniuException ex2) {
				logger.error("uploadByByteArray->QiniuException:", ex2);
			}
		}
		return null;
	}

	public static void main(String[] args) {
		// getUploadToken("donggua-app-public");
	}
}
