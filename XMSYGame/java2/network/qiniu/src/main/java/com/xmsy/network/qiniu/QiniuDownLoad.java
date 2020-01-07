package com.xmsy.network.qiniu;

import com.qiniu.util.Auth;

/**
 * .七牛云下载
 * 
 * @author chenjisi
 * @since 2017年8月15日
 */
public class QiniuDownLoad {

	// 生成下载连接
	public static String download(String url, String accessKey, String secretkey) {
		// 调用privateDownloadUrl方法生成下载链接,第二个参数可以设置Token的过期时间
		Auth auth = Auth.create(accessKey, secretkey);
		String downloadRUL = auth.privateDownloadUrl(url, 3600);
		return downloadRUL;
	}

	public static void main(String args[]) {
//		String url = "http://oupfapcbf.bkt.clouddn.com/Fnx5Cv4mpX4BLLPGFn2AJslDOJY6";
//		String url1 = "http://oupf1bxac.bkt.clouddn.com/A.png";
//		System.out.println(download(url));
//		System.out.println(download(url1));
	}

}
