package com.xmsy.server.zxyy.manager.modules.manager.adchannelconfig.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.cache.LocalContentCache;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.MathUtil;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.constant.ThirdPartyDef;
import com.xmsy.server.zxyy.manager.modules.manager.adchannelconfig.dao.AdChannelConfigDao;
import com.xmsy.server.zxyy.manager.modules.manager.adchannelconfig.entity.AdChannelConfigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.adchannelconfig.service.AdChannelConfigService;
import com.xmsy.server.zxyy.manager.modules.manager.sysconfig.service.SysConfigService;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.jiguang.common.utils.StringUtils;


@Service("adChannelConfigService")
public class AdChannelConfigServiceImpl extends ServiceImpl<AdChannelConfigDao, AdChannelConfigEntity> implements AdChannelConfigService {
	@Autowired
	private SysConfigService SysConfigService;
	@Autowired
	private LocalContentCache localContentCache;
	/**
	 * 查询打包列表
	 */
	@Override
	public JSONArray getAPPPackageDataList() {
		String name = SysConfigService.selectByParamKey(ThirdPartyDef.ABQP, ThirdPartyDef.NAME);//平台名称
		String requestUrl = SysConfigService.selectByParamKey(ThirdPartyDef.ABQP, ThirdPartyDef.REQUEST_URL);//请求地址
		Map<String,Object> map=new HashMap<>();
		map.put("name", name);//平台名称"pl33"
		map.put("buildtype","list");//获取列表固定参数
		HttpResponse result = HttpRequest.post(requestUrl).form(map).timeout(1000*60*15).execute();
		try {
			//字符串转JSON对象
			JSONArray JSONArray = JSON.parseArray(result.body());
			return JSONArray;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
			/*throw new RRException(ErrorCode.FileUploadErrorCode.DO_PACKAGE_ERRO.getErrMsg(),
					ErrorCode.FileUploadErrorCode.DO_PACKAGE_ERRO.getCode());*/
		}
	}
	/**
	 * 增加渠道的时候   去打包
	 */
	@Override
	public Boolean addAPPPackage(AdChannelConfigEntity adChannelConfig) {
		String name = SysConfigService.selectByParamKey(ThirdPartyDef.ABQP, ThirdPartyDef.NAME);//平台名称
		String requestUrl = SysConfigService.selectByParamKey(ThirdPartyDef.ABQP, ThirdPartyDef.REQUEST_URL);//请求地址
		Map<String,Object> map=new HashMap<>();
		map.put("name", name);//平台名称"pl33"
		map.put("buildtype","addagent");//新增列表固定参数
		map.put("agent", adChannelConfig.getChannelCode());//渠道码
		HttpResponse result = HttpRequest.post(requestUrl).form(map).timeout(1000*60*15).execute();
		if(result.body().contains("当前版本已存在")) {//表示第三方打包接口有，但是本地数据库没有 直接返回添加该渠道码进数据库
			//localContentCache.putAPPPack(SysConstant.APP_PACK_KEY+0,1);//1表示打包完成
			return true;
		}
		try {
			//字符串转JSON对象
			JSONObject JSONObject = JSON.parseObject(result.body());
			if("成功".equals(JSONObject.get("msg"))||0==MathUtil.getBigDecimal(JSONObject.get("code")).intValue()) {
				//localContentCache.putAPPPack(SysConstant.APP_PACK_KEY+0,1);//1表示打包完成
				return true;
			}
			localContentCache.putAPPPack(SysConstant.APP_PACK_KEY+0,3);//3表示打包错误
			return false;
		}catch (Exception e) {
			//3.缓存提示调用第三方打包超时
	    	localContentCache.putAPPPack(SysConstant.APP_PACK_KEY+0,3);//2表示超时
			e.printStackTrace();
			throw new RRException(ErrorCode.FileUploadErrorCode.DO_PACKAGE_ERRO.getErrMsg(),
					ErrorCode.FileUploadErrorCode.DO_PACKAGE_ERRO.getCode());
		}
	}
	/**
	 * 删除渠道的时候  删除打包列表里相关数据
	 */
	@Override
	public Boolean delAPPPackage(AdChannelConfigEntity adChannelConfig) {
		String name = SysConfigService.selectByParamKey(ThirdPartyDef.ABQP, ThirdPartyDef.NAME);//平台名称
		String requestUrl = SysConfigService.selectByParamKey(ThirdPartyDef.ABQP, ThirdPartyDef.REQUEST_URL);//请求地址
		Map<String,Object> map=new HashMap<>();
		map.put("name", name);//平台名称"pl33"
		map.put("buildtype","delagent");//删除列表固定参数
		map.put("agent", adChannelConfig.getChannelCode());//渠道码
		HttpResponse result = HttpRequest.post(requestUrl).form(map).timeout(1000*60*15).execute();
		try {
			//字符串转JSON对象
			JSONObject JSONObject = JSON.parseObject(result.body());
			if("成功".equals(JSONObject.get("msg"))||0==MathUtil.getBigDecimal(JSONObject.get("code")).intValue()) {
				return true;
			}
			return false;
		}catch (Exception e) {
			e.printStackTrace();
			localContentCache.putAPPPack(SysConstant.APP_PACK_KEY+0,3);//3表示打包错误
			throw new RRException(ErrorCode.FileUploadErrorCode.DO_PACKAGE_ERRO.getErrMsg(),
					ErrorCode.FileUploadErrorCode.DO_PACKAGE_ERRO.getCode());
		}
	}
	@Override
	public boolean delPhysicalAdChannelConfig(Long id) {
		this.baseMapper.delPhysicalAdChannelConfig(id);
		return true;
	}
	/**
	 * 通过输入的发布地址去   更新安装包
	 * @param adChannelConfig
	 * @return
	 */
	@Override
	public String updateUrl(AdChannelConfigEntity adChannelConfig) {
		String requestUrl = SysConfigService.selectByParamKey(ThirdPartyDef.ABQP, ThirdPartyDef.REQUEST_URL);//请求地址
		String name = SysConfigService.selectByParamKey(ThirdPartyDef.ABQP, ThirdPartyDef.NAME);//平台名称
		Map<String,Object> map=new HashMap<>();
		map.put("name", name);//平台名称"pl33"
		map.put("buildtype","setupgrade");//设置更新地址固定参数
		AdChannelConfigEntity selectById = this.baseMapper.selectById(adChannelConfig.getId());
		map.put("agent", selectById.getChannelCode());//渠道码
		map.put("upgrade", adChannelConfig.getPublishUrl());//设置更新地址
		HttpResponse result = HttpRequest.post(requestUrl).form(map).timeout(1000*60*15).execute();
		this.baseMapper.updateById(adChannelConfig);
		try {
			//字符串转JSON对象
			JSONObject JSONObject = JSON.parseObject(result.body());
			return JSONObject.get("msg").toString();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 微信分享下载
	 */
	@Override
	public String shareWeixinUrlDownload(AdChannelConfigEntity adChannelConfig) {
		String requestUrl = SysConfigService.selectByParamKey(ThirdPartyDef.ABQP, ThirdPartyDef.REQUEST_URL);//请求地址
		Map<String,Object> map=new HashMap<>();
		map.put("buildtype","shared");//新增列表固定参数
		map.put("imgurl", adChannelConfig.getShareWeixinUrl());//微信分享图片url
		AdChannelConfigEntity selectById = this.baseMapper.selectById(adChannelConfig.getId());
		JSONObject nullAgent=null;
		if(StringUtils.isEmpty(adChannelConfig.getIosUrl())
				|| StringUtils.isEmpty(adChannelConfig.getAndroidUrl())) {
			//查询打包列表
			JSONArray jsonArray = getAPPPackageDataList();
			if(jsonArray!=null) {
				for(int i=0;i<jsonArray.size();i++) {//1.遍历获取JSONArray数组
					JSONObject jsonObject = (JSONObject)jsonArray.get(i);//2.获取的每一个JSONObject对象
					if(null!=jsonObject && !StringUtils.isEmpty(jsonObject.getString("agent")) ) {//不为空的话
						if(selectById.getChannelCode().equals(jsonObject.get("agent").toString())) {//3.通过渠道码找到该渠道对应的ios android下载地址
							map.put("iosurl", jsonObject.get("ios"));//微信分享图片url
							map.put("andurl", jsonObject.get("android"));//微信分享图片url
							break;
						}
					}else {//防止没有对应的第三方渠道码相关信息导致出错
						nullAgent=jsonObject;
					}
				}
				if(map.get("iosurl")==null) {
					try {
						map.put("iosurl", nullAgent.get("ios"));//微信分享图片url
						map.put("andurl", nullAgent.get("android"));//微信分享图片url
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}else {
				throw new RRException(ErrorCode.FileUploadErrorCode.GET_PACKAGE_ERRO.getErrMsg(),
						ErrorCode.FileUploadErrorCode.GET_PACKAGE_ERRO.getCode());
			}
		}
		if(!StringUtils.isEmpty(adChannelConfig.getIosUrl())) {
			map.put("iosurl", adChannelConfig.getIosUrl());//Iosurl
		}
		if(!StringUtils.isEmpty(adChannelConfig.getAndroidUrl())) {
			map.put("andurl", adChannelConfig.getAndroidUrl());//Androidurl
		}
		HttpResponse result = HttpRequest.post(requestUrl).form(map).timeout(1000*60*15).execute();
		try {
			//字符串转JSON对象
			JSONObject JSONObject = JSON.parseObject(result.body());
			return JSONObject.getString("url").toString();
		}catch (Exception e) {
			e.printStackTrace();
			throw new RRException(ErrorCode.FileUploadErrorCode.SHARE_WEIXINURL_ERRO.getErrMsg(),
					ErrorCode.FileUploadErrorCode.SHARE_WEIXINURL_ERRO.getCode());
		}
	}
	@Override
	public AdChannelConfigEntity findAdChannelByChannelCode(String channelCode) {
		return this.baseMapper.findAdChannelByChannelCode(channelCode);
	}
	@Override
	public boolean addNullChannelCode(AdChannelConfigEntity adChannelConfig) {
		// TODO Auto-generated method stub
		return this.baseMapper.addNullChannelCode(adChannelConfig);
	}

}
