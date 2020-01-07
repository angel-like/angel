package com.xmsy.server.zxyy.manager.modules.manager.sysmessagemodelprop.controller;


import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.sysmessagemodelprop.entity.SysMessageModelPropEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysmessagemodelprop.service.SysMessageModelPropService;
import com.xmsy.server.zxyy.manager.modules.manager.sysprop.entity.SysPropEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysprop.service.SysPropService;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import cn.hutool.core.collection.CollectionUtil;



/**
 * 站内信
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-22 16:15:53
 */
@RestController
@RequestMapping("sysmessagemodelprop/sysmessagemodelprop")
public class SysMessageModelPropController {
    @Autowired
    private SysMessageModelPropService sysMessageModelPropService;
    
    @Autowired
    private SysPropService sysPropService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sysmessagemodelprop:sysmessagemodelprop:list")
    public R list(SysMessageModelPropEntity sysmessagemodelprop, PageParam pageParam){
        Page<SysMessageModelPropEntity> result = sysMessageModelPropService.queryByMessageId(sysmessagemodelprop, pageParam);
        Wrapper<SysMessageModelPropEntity> entityWrapper = new EntityWrapper<SysMessageModelPropEntity>(sysmessagemodelprop);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		sysmessagemodelprop.selectPage(result, entityWrapper);
		List<SysMessageModelPropEntity> list = result.getRecords();
		if(!CollectionUtil.isEmpty(list)) {
			for(SysMessageModelPropEntity entity: list) {
				if(entity!=null) {
					if(entity.getPropId()!=null&&entity.getPropId()!=0) {
						SysPropEntity sysProp = sysPropService.selectById(entity.getPropId());
						if(sysProp!=null&&!StringUtils.isEmpty(sysProp.getName())) {
							entity.setPropName(sysProp.getName());
						}
					}
				}
			}
		}
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sysmessagemodelprop:sysmessagemodelprop:info")
    public R info(@PathVariable("id") Long id){
			SysMessageModelPropEntity sysMessageModelProp = sysMessageModelPropService.selectById(id);
        return R.ok().put("sysmessagemodelprop", sysMessageModelProp);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sysmessagemodelprop:sysmessagemodelprop:save")
    public R save(@RequestBody SysMessageModelPropEntity sysmessagemodelprop){
			sysMessageModelPropService.insert(sysmessagemodelprop);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sysmessagemodelprop:sysmessagemodelprop:update")
    public R update(@RequestBody SysMessageModelPropEntity sysmessagemodelprop){
			sysMessageModelPropService.updateById(sysmessagemodelprop);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sysmessagemodelprop:sysmessagemodelprop:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			sysMessageModelPropService.deleteById(id);
	}
        return R.ok();
    }

}
