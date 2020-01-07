package com.xmsy.server.zxyy.webhome.modules.manager.uservip.controller;

import com.xmsy.common.define.page.PageParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import com.xmsy.server.zxyy.webhome.modules.manager.uservip.entity.UserVipEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.uservip.service.UserVipService;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;



/**
 * 用户vip等级表
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-10 16:46:36
 */
@RestController
@RequestMapping("uservip/uservip")
public class UserVipController {
    @Autowired
    private UserVipService userVipService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("uservip:uservip:list")
    public R list(UserVipEntity uservip, PageParam pageParam){
        Page<UserVipEntity> result = new Page<UserVipEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<UserVipEntity> entityWrapper = new EntityWrapper<UserVipEntity>(uservip);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		uservip.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("uservip:uservip:info")
    public R info(@PathVariable("id") Long id){
			UserVipEntity userVip = userVipService.selectById(id);
        return R.ok().put("uservip", userVip);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("uservip:uservip:save")
    public R save(@RequestBody UserVipEntity uservip){
			userVipService.insert(uservip);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("uservip:uservip:update")
    public R update(@RequestBody UserVipEntity uservip){
			userVipService.updateById(uservip);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("uservip:uservip:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			userVipService.deleteById(id);
	}
        return R.ok();
    }

}
