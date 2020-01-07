package com.xmsy.server.zxyy.manager.modules.manager.userinfoold.controller;

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
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.userinfoold.entity.UserInfoOldEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userinfoold.service.UserInfoOldService;



/**
 * 用户信息表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-06-22 15:56:39
 */
@RestController
@RequestMapping("userinfoold/userinfoold")
public class UserInfoOldController {
    @Autowired
    private UserInfoOldService userInfoOldService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("userinfoold:userinfoold:list")
    public R list(UserInfoOldEntity userinfoold, PageParam pageParam){
        Page<UserInfoOldEntity> result = new Page<UserInfoOldEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<UserInfoOldEntity> entityWrapper = new EntityWrapper<UserInfoOldEntity>(userinfoold);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		userinfoold.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("userinfoold:userinfoold:info")
    public R info(@PathVariable("id") Long id){
			UserInfoOldEntity userInfoOld = userInfoOldService.selectById(id);
        return R.ok().put("userinfoold", userInfoOld);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("userinfoold:userinfoold:save")
    public R save(@RequestBody UserInfoOldEntity userinfoold){
			userInfoOldService.insert(userinfoold);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("userinfoold:userinfoold:update")
    public R update(@RequestBody UserInfoOldEntity userinfoold){
			userInfoOldService.updateById(userinfoold);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("userinfoold:userinfoold:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			userInfoOldService.deleteById(id);
	}
        return R.ok();
    }

}
