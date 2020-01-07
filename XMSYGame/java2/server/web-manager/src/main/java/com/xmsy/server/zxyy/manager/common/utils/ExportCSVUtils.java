package com.xmsy.server.zxyy.manager.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.system.ApplicationHome;

import com.xmsy.WebManagerApplication;

public class ExportCSVUtils {
	
	/**
	 * 获取下载的文件
	 * @param fileName
	 * @return
	 */
	public static String getDownloadFile(String fileName) {
		try {
			ApplicationHome home = new ApplicationHome(WebManagerApplication.class);
//			String fileStr = ResourceUtils.getURL("classpath:").getPath() + "download/csv";
			String fileStr = home.getSource().getAbsolutePath() + "/download/csv";
			fileStr=fileStr.replace("/web-manager.jar", "");
			File download = new File(fileStr);
			if (!download.exists()) {
				download.mkdirs();
			}
//			 System.out.println("upload url:"+download.getAbsolutePath());
			return fileStr + "/" + fileName;
		}catch (Exception e2) {
			e2.printStackTrace();
		}
		return null;
	}
	/**
	 * 设置csv文件文本 编码 数据流
	 * @param response
	 * @param fileName
	 * @param path
	 */
	public static void exportCSVData(HttpServletResponse response,String fileName,String path) {
		byte[] data = new byte[1024];
    	try {
    		response.setContentType("application/csv; charset= gbk");
    	    response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
    	    File file =new File(path);
    		InputStream inStream = new FileInputStream(file);
    		// 循环取出流中的数据
    		int len;
    		try {
    			while ((len = inStream.read(data)) > 0)
    				response.getOutputStream().write(data, 0, len);
    			inStream.close();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		file.delete();
//    		System.out.println("delete file:"+file.delete());
    	} catch (FileNotFoundException e1) {
    		e1.printStackTrace();
    	}
		
	}
	/**
	 * obj转String
	 * @param obj
	 * @return
	 */
	public static String getString(Object obj) {
    	if(obj!=null) {
			return obj.toString().trim();
		}
    	return  "";
    }
}
