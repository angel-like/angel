package com.xmsy.server.zxyy.webhome.modules.manager.usernamemodify.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.usernamemodify.entity.UserNameModifyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.usernamemodify.service.UserNameModifyService;



/**
 * 真实姓名审核表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-10-28 16:05:16
 */
@RestController
@RequestMapping("usernamemodify/usernamemodify")
public class UserNameModifyController {
    @Autowired
    private UserNameModifyService userNameModifyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("usernamemodify:usernamemodify:list")
    public R list(UserNameModifyEntity usernamemodify, PageParam pageParam){
        Page<UserNameModifyEntity> result = new Page<UserNameModifyEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<UserNameModifyEntity> entityWrapper = new EntityWrapper<UserNameModifyEntity>(usernamemodify);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		usernamemodify.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("usernamemodify:usernamemodify:info")
    public R info(@PathVariable("id") Long id){
			UserNameModifyEntity userNameModify = userNameModifyService.selectById(id);
        return R.ok().put("usernamemodify", userNameModify);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("usernamemodify:usernamemodify:save")
    public R save(@RequestBody UserNameModifyEntity usernamemodify){
			userNameModifyService.insert(usernamemodify);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("usernamemodify:usernamemodify:update")
    public R update(@RequestBody UserNameModifyEntity usernamemodify){
			userNameModifyService.updateById(usernamemodify);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("usernamemodify:usernamemodify:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			userNameModifyService.deleteById(id);
	}
        return R.ok();
    }

}
