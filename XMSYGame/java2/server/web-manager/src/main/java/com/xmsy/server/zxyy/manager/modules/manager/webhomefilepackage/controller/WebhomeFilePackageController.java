package com.xmsy.server.zxyy.manager.modules.manager.webhomefilepackage.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.cache.LocalContentCache;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.modules.manager.adchannelconfig.entity.AdChannelConfigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.webhomefilepackage.entity.WebhomeFilePackageEntity;
import com.xmsy.server.zxyy.manager.modules.manager.webhomefilepackage.service.WebhomeFilePackageService;
import com.xmsy.server.zxyy.manager.modules.manager.webhomefileupload.entity.WebhomeFileUploadEntity;
import com.xmsy.server.zxyy.manager.modules.manager.webhomefileupload.service.WebhomeFileUploadService;
import com.xmsy.server.zxyy.manager.modules.manager.adchannelconfig.service.AdChannelConfigService;
/**
 * 安装包
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-08-22 17:49:18
 */
@RestController
@RequestMapping("webhomefilepackage/webhomefilepackage")
public class WebhomeFilePackageController {
	@Autowired
	private WebhomeFilePackageService webhomeFilePackageService;
	@Autowired
    private WebhomeFileUploadService webhomeFileUploadService;
	@Autowired
	private LocalContentCache localContentCache;
	@Autowired
    private AdChannelConfigService adChannelConfigService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("webhomefilepackage:webhomefilepackage:list")
	public R list(WebhomeFilePackageEntity webhomefilepackage, PageParam pageParam) {
		Page<WebhomeFilePackageEntity> result = new Page<WebhomeFilePackageEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<WebhomeFilePackageEntity> entityWrapper = new EntityWrapper<WebhomeFilePackageEntity>(
				webhomefilepackage);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		entityWrapper.orderBy("id",false);
		webhomefilepackage.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("webhomefilepackage:webhomefilepackage:info")
	public R info(@PathVariable("id") Long id) {
		WebhomeFilePackageEntity webhomeFilePackage = webhomeFilePackageService.selectById(id);
		return R.ok().put("webhomefilepackage", webhomeFilePackage);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("webhomefilepackage:webhomefilepackage:save")
	public R save(@RequestBody WebhomeFilePackageEntity webhomefilepackage) {
		// 有苹果证书  去获取苹果证书管理里面的数据
		if(StringUtils.isNotBlank(webhomefilepackage.getIosName())) {
			WebhomeFileUploadEntity webhomeFileUpload = webhomeFileUploadService
					.selectOne(new EntityWrapper<WebhomeFileUploadEntity>(
							new WebhomeFileUploadEntity().setCertificateName(webhomefilepackage.getIosName())));
			webhomefilepackage.setExpireTime(webhomeFileUpload.getExpireTime());
		}
		// 有渠道码 去获取渠道配置里的的数据
		if(StringUtils.isNotBlank(webhomefilepackage.getChannelCode())) {
			AdChannelConfigEntity channelConfig = adChannelConfigService.selectOne(new EntityWrapper<AdChannelConfigEntity>(
					new AdChannelConfigEntity().setChannelCode(webhomefilepackage.getChannelCode())));
			webhomefilepackage.setChannelName(channelConfig.getChannelName());
		}
		webhomeFilePackageService.insert(webhomefilepackage);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("webhomefilepackage:webhomefilepackage:update")
	public R update(@RequestBody WebhomeFilePackageEntity webhomefilepackage) {
		// 有渠道码 去获取渠道配置里的的数据
		if (StringUtils.isNotBlank(webhomefilepackage.getChannelCode())) {
			AdChannelConfigEntity channelConfig = adChannelConfigService
					.selectOne(new EntityWrapper<AdChannelConfigEntity>(
							new AdChannelConfigEntity().setChannelCode(webhomefilepackage.getChannelCode())));
			webhomefilepackage.setChannelName(channelConfig.getChannelName());
		}
		webhomeFilePackageService.updateById(webhomefilepackage);
		
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("webhomefilepackage:webhomefilepackage:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			webhomeFilePackageService.deleteById(id);
		}
		return R.ok();
	}
	/**
     * 获取苹果签名
     * @param sysPropEntity
     * @return
     */
    @RequestMapping("/selectIosName")
    public R IosName() {
    	List<WebhomeFileUploadEntity> list = webhomeFileUploadService.selectList(new EntityWrapper<WebhomeFileUploadEntity>());
    	return R.ok().put("iosNameList", list);
    }
    /**
     * 获取渠道信息
     * @param sysPropEntity
     * @return
     */
    @RequestMapping("/selectChannelConfig")
    public R ChannelInfo() {
    	List<AdChannelConfigEntity> list = adChannelConfigService.selectList(new EntityWrapper<AdChannelConfigEntity>());
    	return R.ok().put("channelConfigList", list);
    }
    /**
     * 文件打包
     */
    @RequestMapping("/goFilePackage")
    @RequiresPermissions("webhomefilepackage:webhomefilepackage:save")
    public R goPackage(@RequestBody WebhomeFilePackageEntity webhomeFilePackage){
    	String pkey =SysConstant.APP_PACK_KEY+webhomeFilePackage.getId();
    	Object obj=localContentCache.getAPPPack(pkey);
    	if( obj!=null&&Integer.parseInt( obj.toString())==0) {
    		//抛出提示信息
    		//localContentCache.remove(SysConstant.APP_PACK_KEY);
    		return R.ok().put("appdabao", "正在打包中，请勿重复打包");
    		
    	}else {
    		localContentCache.putAPPPack(pkey, 0);
    	}
    	String fileUrl=null;
    	// 有苹果证书  去获取苹果证书管理里面的数据
    	if(StringUtils.isNotBlank(webhomeFilePackage.getIosName())) {
    		WebhomeFileUploadEntity webhomeFileUpload = webhomeFileUploadService
    				.selectOne(new EntityWrapper<WebhomeFileUploadEntity>(
    						new WebhomeFileUploadEntity().setCertificateName(webhomeFilePackage.getIosName())));
    		fileUrl = webhomeFileUploadService.doPackage(webhomeFileUpload, webhomeFilePackage);//去打包，获取打包后生成的地址
    	}else {
    		fileUrl = webhomeFileUploadService.doPackage(null, webhomeFilePackage);
    	}	
		//通过id把地址存进行数据 回来  判断是打包安卓还是苹果
		if(webhomeFilePackage.getPlatform().equals("ios")) {
			webhomeFilePackage.setIosFileUrl(fileUrl);
		}else {
			webhomeFilePackage.setAndroidFileUrl(fileUrl);
		}
		webhomeFilePackageService.updateById(webhomeFilePackage);
		localContentCache.putAPPPack(pkey,1);
    	return R.ok();
    }
    /**
     * 文件打包
     */
    @RequestMapping("/getStatus")
    public R getStatus(@RequestBody WebhomeFilePackageEntity webhomeFilePackage){
    	Object obj = localContentCache.getAPPPack(SysConstant.APP_PACK_KEY+webhomeFilePackage.getId());
    	return R.ok().put("data", obj);
    }
    /**
     * 修改更新地址
     */
    @RequestMapping("/updateurl")
    @RequiresPermissions("webhomefilepackage:webhomefilepackage:update")
    public R goPackage(@RequestBody Long[] ids) {
		for(Long id:ids) {
			WebhomeFilePackageEntity webhomeFilePackage = webhomeFilePackageService.selectById(id);
			webhomeFileUploadService.updateUrl(webhomeFilePackage);
			
		}
		/*
		for(WebhomeFilePackageEntity webhomeFilePackage: list) {
			webhomeFileUploadService.updateUrl(webhomeFilePackage);
		}*/
    	return R.ok();
    }
}
