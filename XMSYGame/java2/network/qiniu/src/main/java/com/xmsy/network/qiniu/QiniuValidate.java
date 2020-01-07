package com.xmsy.network.qiniu;


import com.xmsy.common.define.constant.ResultDef;
import com.xmsy.common.define.exception.NetworkErrorException;
import com.qiniu.util.StringUtils;

/**
 * .工具类
 * 
 * @author chenjisi
 * @since 2017年8月17日
 */
public class QiniuValidate {

	// 判空
	public static void validKey(String accesskey, String secretkey) throws NetworkErrorException {
		if (StringUtils.isNullOrEmpty(accesskey)) {
			throw new NetworkErrorException(ResultDef.NETWORK_ERROR_CODE, "qiniu validKey->accesskey is empty");
		}
		if (StringUtils.isNullOrEmpty(secretkey)) {
			throw new NetworkErrorException(ResultDef.NETWORK_ERROR_CODE, "qiniu validKey->secretkey is empty");
		}
	}

	// 判空
	public static void validKeyAndBucket(String accesskey, String secretkey, String bucket)
			throws NetworkErrorException {
		if (StringUtils.isNullOrEmpty(accesskey)) {
			throw new NetworkErrorException(ResultDef.NETWORK_ERROR_CODE,
					"qiniu validKeyAndBucket->accesskey is empty");
		}
		if (StringUtils.isNullOrEmpty(secretkey)) {
			throw new NetworkErrorException(ResultDef.NETWORK_ERROR_CODE,
					"qiniu validKeyAndBucket->secretkey is empty");
		}
		if (StringUtils.isNullOrEmpty(bucket)) {
			throw new NetworkErrorException(ResultDef.NETWORK_ERROR_CODE, "qiniu validKeyAndBucket->bucket is empty");
		}
	}

	// 判空
	public static void validUrl(String url) throws NetworkErrorException {
		if (StringUtils.isNullOrEmpty(url)) {
			throw new NetworkErrorException(ResultDef.NETWORK_ERROR_CODE, "qiniu validUrl->url is empty");
		}
	}

}
