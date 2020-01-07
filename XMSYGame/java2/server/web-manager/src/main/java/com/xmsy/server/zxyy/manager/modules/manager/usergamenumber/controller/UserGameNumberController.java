package com.xmsy.server.zxyy.manager.modules.manager.usergamenumber.controller;

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
import com.xmsy.server.zxyy.manager.modules.manager.usergamenumber.entity.UserGameNumberEntity;
import com.xmsy.server.zxyy.manager.modules.manager.usergamenumber.service.UserGameNumberService;



/**
 * 用户每日游戏次数统计表
 *
 * @author adu
 * @email xxxxx
 * @date 2019-06-18 17:20:02
 */
@RestController
@RequestMapping("usergamenumber/usergamenumber")
public class UserGameNumberController {
    @Autowired
    private UserGameNumberService userGameNumberService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("usergamenumber:usergamenumber:list")
    public R list(UserGameNumberEntity usergamenumber, PageParam pageParam){
        Page<UserGameNumberEntity> result = new Page<UserGameNumberEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<UserGameNumberEntity> entityWrapper = new EntityWrapper<UserGameNumberEntity>(usergamenumber);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		userGameNumberService.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("usergamenumber:usergamenumber:info")
    public R info(@PathVariable("id") Long id){
			UserGameNumberEntity userGameNumber = userGameNumberService.selectById(id);
        return R.ok().put("usergamenumber", userGameNumber);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("usergamenumber:usergamenumber:save")
    public R save(@RequestBody UserGameNumberEntity usergamenumber){
			userGameNumberService.insert(usergamenumber);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("usergamenumber:usergamenumber:update")
    public R update(@RequestBody UserGameNumberEntity usergamenumber){
			userGameNumberService.updateById(usergamenumber);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("usergamenumber:usergamenumber:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			userGameNumberService.deleteById(id);
	}
        return R.ok();
    }

}
