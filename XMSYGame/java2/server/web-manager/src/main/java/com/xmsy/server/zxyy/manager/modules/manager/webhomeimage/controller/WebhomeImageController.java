package com.xmsy.server.zxyy.manager.modules.manager.webhomeimage.controller;

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
import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.modules.manager.webhomeimage.entity.WebhomeImageEntity;
import com.xmsy.server.zxyy.manager.modules.manager.webhomeimage.service.WebhomeImageService;

import lombok.extern.slf4j.Slf4j;



/**
 * 官网图片管理
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2018-12-31 16:23:23
 */
@Slf4j
@RestController
@RequestMapping("webhomeimage/webhomeimage")
public class WebhomeImageController {
    @Autowired
    private WebhomeImageService webhomeImageService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("webhomeimage:webhomeimage:list")
    public R list(WebhomeImageEntity gameconfig, PageParam pageParam) {
    	log.info("[list] gameconfig {}", gameconfig);
		Page<WebhomeImageEntity> result = new Page<WebhomeImageEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<WebhomeImageEntity> entityWrapper = new EntityWrapper<WebhomeImageEntity>(gameconfig);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gameconfig.selectPage(result, entityWrapper);
		log.info("[list] list {}", result.getRecords());
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}



    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("webhomeimage:webhomeimage:info")
    public R info(@PathVariable("id") Long id){
    	log.info("[info] id {}", id);
			WebhomeImageEntity imageManagement = webhomeImageService.selectById(id);
			log.info("[info] imageManagement {}", imageManagement);
        return R.ok().put("imageManagement", imageManagement);
    }
    

    
    /**
     * 保存
     */
    @SysLog("官网图片新增")
    @RequestMapping("/save")
    @RequiresPermissions("webhomeimage:webhomeimage:save")
    public R save(@RequestBody WebhomeImageEntity imageManagement){
    	imageManagement.setEnable(SysConstant.ENABLE_TRUE);//默认启用
    	log.info("[save] imageManagement {}", imageManagement);
			webhomeImageService.insert(imageManagement);
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("官网图片修改")
    @RequestMapping("/update")
    @RequiresPermissions("webhomeimage:webhomeimage:update")
    public R update(@RequestBody WebhomeImageEntity imageManagement){
    	log.info("[update] imageManagement {}", imageManagement);
			webhomeImageService.updateById(imageManagement);
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("官网图片删除")
    @RequestMapping("/delete")
    @RequiresPermissions("webhomeimage:webhomeimage:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
    	log.info("[delete] id {}", id);
			webhomeImageService.deleteById(id);
	}
        return R.ok();
    }
    
    /**
     * 启用
     */
    @SysLog("官网图片启用")
    @RequestMapping("/enable")
    @RequiresPermissions("webhomeimage:webhomeimage:enable")
    public R enable(@RequestBody Long[] ids){
    for (Long id : ids) {
    	log.info("[enable] id {}", id);
			webhomeImageService.enable(id,SysConstant.ENABLE_TRUE);
	}
        return R.ok();
    }
    
    /**
     * 禁用
     */
    @SysLog("官网图片禁用")
    @RequestMapping("/prohibit")
    @RequiresPermissions("webhomeimage:webhomeimage:prohibit")
    public R prohibit(@RequestBody Long[] ids){
    for (Long id : ids) {
    	log.info("[prohibit] id {}", id);
    	webhomeImageService.enable(id,SysConstant.ENABLE_FALSE);
	}
        return R.ok();
    }

}
