package com.xmsy.server.zxyy.webhome.modules.manager.proxytransferrecord.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.proxytransferrecord.entity.ProxyTransferRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.proxytransferrecord.service.ProxyTransferRecordService;



/**
 * 划拨记录
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-08-07 15:51:01
 */
@RestController
@RequestMapping("proxytransferrecord/proxytransferrecord")
public class ProxyTransferRecordController {
    @Autowired
    private ProxyTransferRecordService proxyTransferRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("proxytransferrecord:proxytransferrecord:list")
    public R list(ProxyTransferRecordEntity proxytransferrecord, PageParam pageParam){
        Page<ProxyTransferRecordEntity> result = new Page<ProxyTransferRecordEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<ProxyTransferRecordEntity> entityWrapper = new EntityWrapper<ProxyTransferRecordEntity>(proxytransferrecord);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		proxytransferrecord.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("proxytransferrecord:proxytransferrecord:info")
    public R info(@PathVariable("id") Long id){
			ProxyTransferRecordEntity proxyTransferRecord = proxyTransferRecordService.selectById(id);
        return R.ok().put("proxytransferrecord", proxyTransferRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("proxytransferrecord:proxytransferrecord:save")
    public R save(@RequestBody ProxyTransferRecordEntity proxytransferrecord){
			proxyTransferRecordService.insert(proxytransferrecord);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("proxytransferrecord:proxytransferrecord:update")
    public R update(@RequestBody ProxyTransferRecordEntity proxytransferrecord){
			proxyTransferRecordService.updateById(proxytransferrecord);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("proxytransferrecord:proxytransferrecord:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			proxyTransferRecordService.deleteById(id);
	}
        return R.ok();
    }

}
