package com.xmsy.server.zxyy.manager.modules.manager.webhomefileupload.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.xmsy.server.zxyy.manager.modules.manager.webhomefileupload.dao.WebhomeFileUploadDao;
import com.xmsy.server.zxyy.manager.modules.manager.webhomefileupload.entity.WebhomeFileUploadEntity;
import com.xmsy.server.zxyy.manager.modules.manager.webhomefileupload.service.WebhomeFileUploadService;
import com.xmsy.server.zxyy.manager.cache.LocalContentCache;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.constant.ThirdPartyDef;
import com.xmsy.server.zxyy.manager.modules.manager.sysconfig.service.SysConfigService;
import com.xmsy.server.zxyy.manager.modules.manager.webhomefilepackage.entity.WebhomeFilePackageEntity;


@Service("webhomeFileUploadService")
public class WebhomeFileUploadServiceImpl extends ServiceImpl<WebhomeFileUploadDao, WebhomeFileUploadEntity> implements WebhomeFileUploadService {
	@Autowired
	private SysConfigService SysConfigService;
	@Autowired
	private LocalContentCache localContentCache;
	//调用第三方打包接口
	@Override
	public String doPackage(WebhomeFileUploadEntity webhomeFileUpload,WebhomeFilePackageEntity webhomeFilePackage) {
		String name = SysConfigService.selectByParamKey(ThirdPartyDef.ABQP, ThirdPartyDef.NAME);
		String requestUrl = SysConfigService.selectByParamKey(ThirdPartyDef.ABQP, ThirdPartyDef.REQUEST_URL);
		Map<String,Object> map=new HashMap<>();
		map.put("name", name);//"lfyl"
		//map.put("platform", webhomeFileUpload.getPlatform());//平台由传入的参数  webhomeFilePackage实体类确定了
		if(webhomeFileUpload!=null) {
			if(StringUtils.isNotEmpty(webhomeFileUpload.getP12Content())) {//只要一个不为空，其他肯定有值 
				map.put("p12", webhomeFileUpload.getP12Content());
				map.put("pofile", webhomeFileUpload.getProfileContent());
				map.put("pwd", webhomeFileUpload.getPwd());
			}
		}
		map.put("platform", webhomeFilePackage.getPlatform());
		map.put("buildtype", webhomeFilePackage.getBuildtype());
		/*//如果是重打，buildtype=upgrade时 upgrade要填写app的更新地址
		if(webhomeFilePackage.getBuildtype().equals("upgrade")) {
			map.put("upgrade", webhomeFilePackage.getUpdateUrl());
		}*/
		if(StringUtils.isNotEmpty(webhomeFilePackage.getChannelCode())) {
			map.put("agent", webhomeFilePackage.getChannelCode());
		}
		if(StringUtils.isNotEmpty(webhomeFilePackage.getUpdateUrl())) {
			map.put("upgrade", webhomeFilePackage.getUpdateUrl());
		}
		HttpResponse result = HttpRequest.post(requestUrl).form(map).timeout(1000*60*15).execute();
		try {
			//字符串转JSON对象
			JSONObject JSONObject = com.alibaba.fastjson.JSONObject.parseObject(result.body());
			String url = (String)JSONObject.get("url");
			if(StringUtils.isEmpty(url)) {
				localContentCache.putAPPPack(SysConstant.APP_PACK_KEY+webhomeFilePackage.getId(),3);
				//String msg = (String)JSONObject.get("msg");//获取异常信息
				throw new RRException(ErrorCode.FileUploadErrorCode.DO_PACKAGE_ERRO.getErrMsg(),
						ErrorCode.FileUploadErrorCode.DO_PACKAGE_ERRO.getCode());
			}
			return url;
		} catch (Exception e) {
			localContentCache.putAPPPack(SysConstant.APP_PACK_KEY+webhomeFilePackage.getId(),2);
			e.printStackTrace();
			throw new RRException(ErrorCode.FileUploadErrorCode.DO_PACKAGE_ERRO.getErrMsg(),
					ErrorCode.FileUploadErrorCode.DO_PACKAGE_ERRO.getCode());
			
		}
	}
	//调用第三方打包接口修改更新地址
	@Override
	public String updateUrl(WebhomeFilePackageEntity webhomeFilePackage) {
		String name = SysConfigService.selectByParamKey(ThirdPartyDef.ABQP, ThirdPartyDef.NAME);
		String requestUrl = SysConfigService.selectByParamKey(ThirdPartyDef.ABQP, ThirdPartyDef.REQUEST_URL);
		Map<String,Object> map=new HashMap<>();
		map.put("name", name);
		map.put("buildtype", "upgrade");//buildtype=upgrade时 upgrade要填写app的更新地址
		map.put("upgrade", webhomeFilePackage.getUpdateUrl());//app的更新地址
		if(StringUtils.isNotEmpty(webhomeFilePackage.getChannelCode())) {
			map.put("agent", webhomeFilePackage.getChannelCode());
		}
		HttpResponse result = HttpRequest.post(requestUrl).form(map).timeout(1000*60*15).execute();
		try {
			//字符串转JSON对象
			JSONObject JSONObject = com.alibaba.fastjson.JSONObject.parseObject(result.body());
			String msg = (String)JSONObject.get("msg");
			if(!"成功".equals(msg)) {
				//String msg = (String)JSONObject.get("msg");//获取异常信息
				throw new RRException(ErrorCode.FileUploadErrorCode.DO_PACKAGE_ERRO.getErrMsg(),
						ErrorCode.FileUploadErrorCode.DO_PACKAGE_ERRO.getCode());
			}
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RRException(ErrorCode.FileUploadErrorCode.DO_PACKAGE_ERRO.getErrMsg(),
					ErrorCode.FileUploadErrorCode.DO_PACKAGE_ERRO.getCode());
		}
		
	}
}
