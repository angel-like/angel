package com.xmsy.server.zxyy.manager.modules.manager.webhomefileupload.controller;

import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.webhomefileupload.entity.WebhomeFileUploadEntity;
import com.xmsy.server.zxyy.manager.modules.manager.webhomefileupload.service.WebhomeFileUploadService;
import com.xmsy.server.zxyy.manager.utils.Base64Util;
import com.xmsy.server.zxyy.manager.utils.DateUtils;



/**
 * 苹果证书管理
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-08-16 11:52:13
 */
@RestController
@RequestMapping("webhomefileupload/webhomefileupload")
public class WebhomeFileUploadController {
    @Autowired
    private WebhomeFileUploadService webhomeFileUploadService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("webhomefileupload:webhomefileupload:list")
    public R list(WebhomeFileUploadEntity webhomefileupload, PageParam pageParam){
        Page<WebhomeFileUploadEntity> result = new Page<WebhomeFileUploadEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<WebhomeFileUploadEntity> entityWrapper = new EntityWrapper<WebhomeFileUploadEntity>(webhomefileupload);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		webhomefileupload.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("webhomefileupload:webhomefileupload:info")
    public R info(@PathVariable("id") Long id){
			WebhomeFileUploadEntity webhomeFileUpload = webhomeFileUploadService.selectById(id);
        return R.ok().put("webhomefileupload", webhomeFileUpload);
    }
    
    /**
     * 接收前端传过来的  p12文件 ，并进行编码
     * @throws IOException 
     */
    @RequestMapping("/uploadFileP12")
    @RequiresPermissions("webhomefileupload:webhomefileupload:save")
    public R uploadFileP12(MultipartFile file) throws IOException{
    	String p12Content=null;
    	if(!file.isEmpty()) {
    		p12Content=Base64Util.base64EncoderFile(file);
    		System.out.println(FilenameUtils.getExtension(file.getOriginalFilename()));
    	}
        return R.ok().put("p12Content", p12Content);
    }
    /**
     * 接收前端传过来的  profile文件 ，并进行编码
     * @throws IOException 
     */
    @RequestMapping("/uploadFileProfile")
    @RequiresPermissions("webhomefileupload:webhomefileupload:save")
    public R uploadFileProfile(MultipartFile file) throws IOException{
    	String profileContent=null;
    	if(!file.isEmpty()) {
    		profileContent=Base64Util.base64EncoderFile(file);
    	}
        return R.ok().put("profileContent", profileContent);
    }
    /**
     * 打包（在WebhomeFilePackageController里做了新打包功能,这里暂时不用）
     */
    @RequestMapping("/goPackage")
    @RequiresPermissions("webhomefileupload:webhomefileupload:save")
    public R goPackage(@RequestBody WebhomeFileUploadEntity webhomefileupload){
    	verification(webhomefileupload);
    	//去调用第三方接口打包
    	String url = webhomeFileUploadService.doPackage(webhomefileupload,null);
    	webhomefileupload.setFileUrl(url);
    	webhomefileupload.setFilePackageTime(DateUtils.getDay(0));
        return R.ok().put("webhomefileupload",webhomefileupload);
    }
    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("webhomefileupload:webhomefileupload:save")
    public R save(@RequestBody WebhomeFileUploadEntity webhomefileupload){
    	verification(webhomefileupload);
    	webhomeFileUploadService.insert(webhomefileupload);
        return R.ok();
    }
   
    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("webhomefileupload:webhomefileupload:update")
    public R update(@RequestBody WebhomeFileUploadEntity webhomefileupload){
    	verification(webhomefileupload);
		webhomeFileUploadService.updateById(webhomefileupload);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("webhomefileupload:webhomefileupload:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			webhomeFileUploadService.deleteById(id);
	}
        return R.ok();
    }
    
    /**校验数据*/
    public void verification(WebhomeFileUploadEntity webhomefileupload) {
    	if(StringUtils.isNotEmpty(webhomefileupload.getP12Content())&&
    			StringUtils.isNotEmpty(webhomefileupload.getProfileContent())&&
    			StringUtils.isNotEmpty(webhomefileupload.getPwd())) {
    	}else if(StringUtils.isEmpty(webhomefileupload.getP12Content())&&
    			StringUtils.isEmpty(webhomefileupload.getProfileContent())&&
    			StringUtils.isEmpty(webhomefileupload.getPwd())) {
    	}else {
    		throw new RRException(ErrorCode.FileUploadErrorCode.PARAM_ERRO.getErrMsg(),
					ErrorCode.FileUploadErrorCode.PARAM_ERRO.getCode());
    	}
    }
}
