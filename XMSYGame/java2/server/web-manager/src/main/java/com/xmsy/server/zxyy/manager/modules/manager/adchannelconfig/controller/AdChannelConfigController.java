package com.xmsy.server.zxyy.manager.modules.manager.adchannelconfig.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.cache.LocalContentCache;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.modules.manager.adchannelconfig.entity.AdChannelConfigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.adchannelconfig.service.AdChannelConfigService;




/**
 * 渠道配置表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-07-02 17:05:09
 */
@RestController
@RequestMapping("adchannelconfig/adchannelconfig")
public class AdChannelConfigController {
    @Autowired
    private AdChannelConfigService adChannelConfigService;
    @Autowired
	private LocalContentCache localContentCache;
    
    /*@Autowired
	private WebhomeFilePackageService webhomeFilePackageService;*/

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("adchannelconfig:adchannelconfig:list")
    public R list(AdChannelConfigEntity adchannelconfig, PageParam pageParam){
        Page<AdChannelConfigEntity> result = new Page<AdChannelConfigEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<AdChannelConfigEntity> entityWrapper = new EntityWrapper<AdChannelConfigEntity>(adchannelconfig);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		adchannelconfig.selectPage(result, entityWrapper);
		List<AdChannelConfigEntity> list = result.getRecords();
		JSONArray JSONArray = adChannelConfigService.getAPPPackageDataList();
		if(JSONArray!=null) {
			for(AdChannelConfigEntity adChannelConfig:list) {
				for(int i=0;i<JSONArray.size();i++) {//1.遍历获取JSONArray数组
					JSONObject jsonObject = (JSONObject)JSONArray.get(i);//2.获取的每一个JSONObject对象
					if(null != jsonObject) {//不为空的话
						if(isEquals(adChannelConfig.getChannelCode(),jsonObject.get("agent"))) {
							adChannelConfig.setIosDownLoadUrl(jsonObject.getString("ios"));
							adChannelConfig.setAndroidDownLoadUrl(jsonObject.getString("android"));
							continue;
						}
					}
				}
				
			}
		}
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }
    
    private boolean isEquals(Object obj1,Object obj2) {
    	String s1="";
    	String s2="";
    	if(obj1!=null) {
    		s1=obj1.toString().trim();
    	}
    	if(obj1!=null) {
    		s2=obj2.toString().trim();
    	}
    	return s1.equals(s2);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("adchannelconfig:adchannelconfig:info")
    public R info(@PathVariable("id") Long id){
			AdChannelConfigEntity adChannelConfig = adChannelConfigService.selectById(id);
        return R.ok().put("adchannelconfig", adChannelConfig);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("adchannelconfig:adchannelconfig:save")
    public R save(@RequestBody AdChannelConfigEntity adchannelconfig){
    	/*//app打包废弃重做
    	WebhomeFilePackageEntity webhomefilepackage = new WebhomeFilePackageEntity();
    	webhomefilepackage.setChannelName(adchannelconfig.getChannelName());
    	webhomefilepackage.setChannelCode(adchannelconfig.getChannelCode());
    		webhomeFilePackageService.insert(webhomefilepackage);*/
    	//1.校验重复新增
    	String pkey =SysConstant.APP_PACK_KEY+0;
    	Object obj=localContentCache.getAPPPack(pkey);
    	if( obj!=null&&Integer.parseInt( obj.toString())==0) {
    		//抛出提示信息
    		//localContentCache.remove(pkey);
    		return R.ok().put("appdabao", "正在新增中，请勿重复新增");
    	}else {
    		localContentCache.putAPPPack(pkey, 0);//0表示正在新增中
    	}
    	//2.去新增数据
    	if(adChannelConfigService.addAPPPackage(adchannelconfig)) {
    		if("".equals(adchannelconfig.getChannelCode())) {//空渠道码，需要自己写sql
    			adChannelConfigService.addNullChannelCode(adchannelconfig);
    		}else {
    			adChannelConfigService.insert(adchannelconfig);
    		}
    		localContentCache.putAPPPack(pkey,1);//1表示打包完成
    		return R.ok();
    	}
    	//3.缓存提示打包错误
    	localContentCache.putAPPPack(pkey,3);//3表示打包错误
        return R.ok().put("msg", "调用第三方接口失败");
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("adchannelconfig:adchannelconfig:update")
    public R update(@RequestBody AdChannelConfigEntity newAdChannelConfig){
    	//1.先查出原本的渠道码。
    	AdChannelConfigEntity old=adChannelConfigService.selectById(newAdChannelConfig.getId());
    	//2.如果渠道码改变  再做打包方面修改。
    	if(!old.getChannelCode().equals(newAdChannelConfig.getChannelCode())) {
    		localContentCache.putAPPPack(SysConstant.APP_PACK_KEY+0, 0);//0表示正在打包中
    		//3.先删除旧渠道码
    		adChannelConfigService.delAPPPackage(old);
    		//4.再增加新的渠道码
    		if(adChannelConfigService.addAPPPackage(newAdChannelConfig)) {
    			localContentCache.putAPPPack(SysConstant.APP_PACK_KEY+0,1);//表示打包完成
    		}else {
    			localContentCache.putAPPPack(SysConstant.APP_PACK_KEY+0,3);//表示打包错误
    		}
    	}
    	//先调用删除打包的方法，再调用新增打包的方法
		adChannelConfigService.updateById(newAdChannelConfig);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("adchannelconfig:adchannelconfig:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			AdChannelConfigEntity adChannelConfig = adChannelConfigService.selectById(id);
			if(adChannelConfigService.delAPPPackage(adChannelConfig)) {
				adChannelConfigService.delPhysicalAdChannelConfig(id);
			}else {
				return R.ok().put("msg", "调用第三方接口失败");
			}
		}
		return R.ok();
	}
    /**
     * 获取打包状态
     */
    @RequestMapping("/getStatus")
    public R getStatus(@RequestBody AdChannelConfigEntity adChannelConfig){
    	//打包状态只有新增，所以默认key都是“appPack:渠道码 ”
    	//0表示正在打包中，1表示打包完成，2表示超时，3表示打包错误，4表示数据库已存在该渠道码
    	Object obj = localContentCache.getAPPPack(SysConstant.APP_PACK_KEY+0);
    	return R.ok().put("data", obj);
    }
    /**
     * 微信分享下载
     */
    @RequestMapping("/shareWeixin")
    public R shareWeixinUrl(@RequestBody AdChannelConfigEntity adChannelConfig){
    	//1.调用设置更新地址接口(publishUrl)
    	adChannelConfigService.updateUrl(adChannelConfig);
    	//2.调用微信分享地址URL接口(shareWeixinUrl)
    	String urlDownload = adChannelConfigService.shareWeixinUrlDownload(adChannelConfig);
    	return R.ok().put("urlDownload", urlDownload);
    }
    /**
     * 查询该渠道码是否存在
     */
    @RequestMapping("/goFindChannelCode")
    public R goFindChannelCode(AdChannelConfigEntity adChannelConfig){
		AdChannelConfigEntity adChannelConfigEntity = adChannelConfigService
				.findAdChannelByChannelCode(adChannelConfig.getChannelCode());
		if(adChannelConfigEntity!=null) {
			return R.ok().put("flag", true);
		}
    	return R.ok().put("flag",false);
    }
}
