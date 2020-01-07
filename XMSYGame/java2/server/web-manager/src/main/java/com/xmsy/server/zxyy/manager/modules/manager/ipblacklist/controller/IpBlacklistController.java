package com.xmsy.server.zxyy.manager.modules.manager.ipblacklist.controller;

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
import com.xmsy.server.zxyy.manager.modules.manager.ipblacklist.entity.IpBlacklistEntity;
import com.xmsy.server.zxyy.manager.modules.manager.ipblacklist.service.IpBlacklistService;



/**
 * ip黑名单
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-02-17 15:58:11
 */
@RestController
@RequestMapping("ipblacklist/ipblacklist")
public class IpBlacklistController {
    @Autowired
    private IpBlacklistService ipBlacklistService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ipblacklist:ipblacklist:list")
    public R list(IpBlacklistEntity ipblacklist, PageParam pageParam){
        Page<IpBlacklistEntity> result = new Page<IpBlacklistEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<IpBlacklistEntity> entityWrapper = new EntityWrapper<IpBlacklistEntity>(ipblacklist);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		ipblacklist.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ipblacklist:ipblacklist:info")
    public R info(@PathVariable("id") Long id){
			IpBlacklistEntity ipBlacklist = ipBlacklistService.selectById(id);
        return R.ok().put("ipblacklist", ipBlacklist);
    }

    /**
     * 保存
     */
    @SysLog("ip黑名单新增")
    @RequestMapping("/save")
    @RequiresPermissions("ipblacklist:ipblacklist:save")
    public R save(@RequestBody IpBlacklistEntity ipblacklist){
			ipBlacklistService.insert(ipblacklist);
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("ip黑名单修改")
    @RequestMapping("/update")
    @RequiresPermissions("ipblacklist:ipblacklist:update")
    public R update(@RequestBody IpBlacklistEntity ipblacklist){
			ipBlacklistService.updateById(ipblacklist);
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("ip黑名单删除")
    @RequestMapping("/delete")
    @RequiresPermissions("ipblacklist:ipblacklist:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			ipBlacklistService.deleteById(id);
	}
        return R.ok();
    }

}
