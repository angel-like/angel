package com.xmsy.server.zxyy.game.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class ReadUtils {
	
	/**
     * 读取json文件，返回json串
     * @param fileName
     * @return
     */
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String readJsonFile(InputStream inputStream ) {
    	String jsonStr = "";
    	try {
    		
    		Reader reader = new InputStreamReader(inputStream,"utf-8");
    		int ch = 0;
    		StringBuffer sb = new StringBuffer();
    		while ((ch = reader.read()) != -1) {
    			sb.append((char) ch);
    		}
    		reader.close();
    		jsonStr = sb.toString();
    		return jsonStr;
    	} catch (IOException e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    /*
	 * 读取配置文件
	*/	
	public String readFile ( InputStream inputStream ) throws IOException {
		
		StringBuilder builder = new StringBuilder();
		try {
			InputStreamReader reader = new InputStreamReader(inputStream , "UTF-8" );
			BufferedReader bfReader = new BufferedReader( reader );
			String tmpContent = null;
			while ( ( tmpContent = bfReader.readLine() ) != null ) {
				builder.append( tmpContent );
			}
			bfReader.close();
		} catch ( UnsupportedEncodingException e ) {
			// 忽略
		}
		return this.filter( builder.toString() );
	}
	// 过滤输入字符串, 剔除多行注释以及替换掉反斜杠
	private String filter ( String input ) {
		return input.replaceAll( "/\\*[\\s\\S]*?\\*/", "" );
	}


}
