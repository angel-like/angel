package com.xmsy.server.zxyy.webhome.modules.manager.webhomefileupload.controller;

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
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomefileupload.entity.WebhomeFileUploadEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomefileupload.service.WebhomeFileUploadService;



/**
 * 文件上传
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
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("webhomefileupload:webhomefileupload:save")
    public R save(@RequestBody WebhomeFileUploadEntity webhomefileupload){
			webhomeFileUploadService.insert(webhomefileupload);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("webhomefileupload:webhomefileupload:update")
    public R update(@RequestBody WebhomeFileUploadEntity webhomefileupload){
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

}
