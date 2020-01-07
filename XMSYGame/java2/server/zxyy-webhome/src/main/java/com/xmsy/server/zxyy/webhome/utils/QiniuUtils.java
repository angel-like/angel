package com.xmsy.server.zxyy.webhome.utils;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.define.constant.ResultDef;
import com.xmsy.common.define.exception.NetworkErrorException;
import com.xmsy.network.qiniu.QiniuUpload;
import com.xmsy.network.qiniu.QiniuValidate;
import com.xmsy.server.zxyy.webhome.constant.HallUrlConstant;

/**
 * .七牛云对象操作工具类
 * 
 * @author chenjisi
 * @since 2017年8月20日
 */
public class QiniuUtils {

	// 获取公有空间上传token
	public static String getUploadTokenPublic() throws NetworkErrorException {
		String accesskey = HallUrlConstant.getQINIU_ACCESS_KEY();
		String secretkey = HallUrlConstant.getQINIU_SECRET_KEY();
		String bucket = HallUrlConstant.getQINIU_BUCKET_PULIC();
		QiniuValidate.validKeyAndBucket(accesskey, secretkey, bucket);
		Optional<String> result = Optional.ofNullable(QiniuUpload.getUploadToken(bucket, accesskey, secretkey));
		result.orElseThrow(() -> new NetworkErrorException(ResultDef.NETWORK_ERROR_CODE, "七牛获取公有空间上传token异常"));
		return result.get();
	}

	// 获取私有空间上传token
	public static String getUploadTokenPrivate() throws NetworkErrorException {
		String accesskey = HallUrlConstant.getQINIU_ACCESS_KEY();
		String secretkey = HallUrlConstant.getQINIU_SECRET_KEY();
		String bucket = HallUrlConstant.getQINIU_BUCKET_PULIC();
		QiniuValidate.validKeyAndBucket(accesskey, secretkey, bucket);
		Optional<String> result = Optional.ofNullable(QiniuUpload.getUploadToken(bucket, accesskey, secretkey));
		result.orElseThrow(() -> new NetworkErrorException(ResultDef.NETWORK_ERROR_CODE, "七牛获取私有空间上传token异常"));
		return result.get();
	}

	// 上传附件,返回url
	public static String uploadFile(MultipartFile file) throws Exception {
		String accesskey = HallUrlConstant.getQINIU_ACCESS_KEY();
		String secretkey = HallUrlConstant.getQINIU_SECRET_KEY();
		String bucket = HallUrlConstant.getQINIU_BUCKET_PULIC();
		QiniuValidate.validKeyAndBucket(accesskey, secretkey, bucket);
		Optional<String> result = Optional.ofNullable(QiniuUpload.getUploadToken(bucket, accesskey, secretkey));
		result.orElseThrow(() -> new NetworkErrorException(ResultDef.NETWORK_ERROR_CODE, "七牛获取公有空间上传token异常"));
		String token = result.get();
		String data = QiniuUpload.uploadByByteArray(file.getBytes(), token);// 图片上传成功返回
		JSONObject jsonObject = JSON.parseObject(data);
		String url = HallUrlConstant.getQINIU_URL() + jsonObject.get("hash");// 七牛云路径
		return url;
	}

}
