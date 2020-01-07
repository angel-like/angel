package com.xmsy.server.zxyy.webhome.modules.manager.user.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.webhome.common.annotation.SysLog;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.user.param.UserParam;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;

import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("user/user")
public class UserExportController {
	 @Autowired
	 private UserService userService;
	 private String rowKey[]= new String[] {"id","account","userName","superiorsAccount","hierarchyName","userType","registerIp","LoginIp","deviceCode","mainAccount","remake"};

	 /**
     * 导出csv的数据
     */
	@SysLog("导出csv的数据")
    @RequestMapping("/exportCSVData")
    @RequiresPermissions("user:user:list")
    public R exportCSVData(UserParam user,HttpServletResponse response){
    	log.info("[exportCSVData] params {}", user);
    	List<Map<String, Object>> dataList= userService.findUserList(user);
    	return R.ok().put("dataList", dataList);
    }
    /**
     * 
     */
	@SysLog("导出数据")
    @RequestMapping("/exportCSV")
    @RequiresPermissions("user:user:list")
    public byte[] exportCSV(@RequestParam UserParam userParam,HttpServletResponse response){
    	List<Map<String, Object>> dataList= userService.findUserList(userParam);
    	//指定路径和编码
    	String path="e:/testWrite.csv";
    	CsvWriter writer = CsvUtil.getWriter(path, CharsetUtil.CHARSET_UTF_8);
    	//按行写出
    	for(Map<String, Object> user:dataList) {
    		writer.write(getRowStr(user)); 
    	}
    	byte[] data = new byte[1024];
    	try {
    		InputStream inStream = new FileInputStream(path);
    		// 循环取出流中的数据
    		int len;
    		try {
    			while ((len = inStream.read(data)) > 0)
    				response.getOutputStream().write(data, 0, len);
    			inStream.close();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	} catch (FileNotFoundException e1) {
    		e1.printStackTrace();
    	}
    	return data;
    }
    
    private String[] getRowStr(Map<String, Object> user) {
    	String[] rowData=new String[rowKey.length];
    	for (int i = 0; i < rowKey.length; i++) {
			if(user.get(rowKey[i])!=null) {
				rowData[i]=user.get(rowKey[i]).toString().trim();
			}else {
				rowData[i]="";
			}
		}
    	return rowData;
    }
}
