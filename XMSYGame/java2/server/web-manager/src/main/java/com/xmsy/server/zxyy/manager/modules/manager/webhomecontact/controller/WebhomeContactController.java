package com.xmsy.server.zxyy.manager.modules.manager.webhomecontact.controller;

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
import com.xmsy.server.zxyy.manager.modules.manager.webhomecontact.entity.WebhomeContactEntity;
import com.xmsy.server.zxyy.manager.modules.manager.webhomecontact.service.WebhomeContactService;

import lombok.extern.slf4j.Slf4j;



/**
 * 联系管理
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-01-01 16:52:13
 */
@Slf4j
@RestController
@RequestMapping("webhomecontact/webhomecontact")
public class WebhomeContactController {
    @Autowired
    private WebhomeContactService webhomeContactService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("webhomecontact:webhomecontact:list")
    public R list(WebhomeContactEntity webhomeContact, PageParam pageParam) {
    	log.info("[list] webhomeContact {}", webhomeContact);
    	String name=webhomeContact.getName();
    	webhomeContact.setName(null);
		Page<WebhomeContactEntity> result = new Page<WebhomeContactEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<WebhomeContactEntity> entityWrapper = new EntityWrapper<WebhomeContactEntity>(webhomeContact).like("name", name);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		webhomeContact.selectPage(result, entityWrapper);
		log.info("[list] list {}", result.getRecords());
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}
    

    /**
     * 查看
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("webhomecontact:webhomecontact:info")
    public R info(@PathVariable("id") Long id){
    	log.info("[info] id {}", id);
			WebhomeContactEntity contactManagement = webhomeContactService.selectById(id);
			log.info("[info] contactManagement {}", contactManagement);
        return R.ok().put("contactManagement", contactManagement);
    }

    /**
     * 保存
     */
    @SysLog("联系管理新增")
    @RequestMapping("/save")
    @RequiresPermissions("webhomecontact:webhomecontact:save")
    public R save(@RequestBody WebhomeContactEntity contactManagement){
    	contactManagement.setEnable(SysConstant.ENABLE_TRUE);
    	log.info("[save] contactManagement {}", contactManagement);
			webhomeContactService.insert(contactManagement);
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("联系管理修改")
    @RequestMapping("/update")
    @RequiresPermissions("webhomecontact:webhomecontact:update")
    public R update(@RequestBody WebhomeContactEntity contactManagement){
    	log.info("[update] contactManagement {}", contactManagement);
			webhomeContactService.updateById(contactManagement);
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("联系管理删除")
    @RequestMapping("/delete")
    @RequiresPermissions("webhomecontact:webhomecontact:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
    	log.info("[delete] id {}", id);
			webhomeContactService.deleteById(id);
	}
        return R.ok();
    }

}
