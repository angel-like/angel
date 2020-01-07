package com.xmsy.server.zxyy.webhome.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

/**
 * 文件生成md5
 * 
 * @author Administrator
 *
 */
public class FileMd5Util {
	  /**
     * 生成md5
     * @param MultipartFile 图片文件
     * @return MD5值
     * @throws FileNotFoundException
     */
    public static String getStringMd5ForMultipartFile(MultipartFile file) throws FileNotFoundException {
    	  try {
              byte[] uploadBytes = file.getBytes();  
              MessageDigest md5 = MessageDigest.getInstance("MD5");  
              byte[] digest = md5.digest(uploadBytes);  
              String hashString = new BigInteger(1, digest).toString(16);  
              return hashString;  
          } catch (Exception e) {  
        	  
          }  
          return null; 
    }
    
    /**
     * 生成md5
     * @param file 图片文件
     * @return MD5值
     * @throws FileNotFoundException
     */
    public static String getStringMd5ForFile( File file) throws FileNotFoundException {
        String value = null;
        FileInputStream in = new FileInputStream(file);
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

}