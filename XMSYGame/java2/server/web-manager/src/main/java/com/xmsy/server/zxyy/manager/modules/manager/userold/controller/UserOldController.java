package com.xmsy.server.zxyy.manager.modules.manager.userold.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.userold.entity.UserOldEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userold.service.UserOldService;



/**
 * 用户信息表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-06-22 15:56:39
 */
@RestController
@RequestMapping("userold/userold")
public class UserOldController {
    @Autowired
    private UserOldService userOldService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("userold:userold:list")
    public R list(UserOldEntity userold, PageParam pageParam){
       /* Page<UserOldEntity> result = new Page<UserOldEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<UserOldEntity> entityWrapper = new EntityWrapper<UserOldEntity>(userold);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		userold.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));*/
    	PageUtils pageUtils = userOldService.findUserPage(userold, pageParam);
		return R.ok().put("page", pageUtils);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("userold:userold:info")
    public R info(@PathVariable("id") Long id){
			UserOldEntity userOld = userOldService.selectById(id);
        return R.ok().put("userold", userOld);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("userold:userold:save")
    public R save(@RequestBody UserOldEntity userold){
			userOldService.insert(userold);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("userold:userold:update")
    public R update(@RequestBody UserOldEntity userold){
			userOldService.updateById(userold);
        return R.ok();
    }
    /**
     * 修改备注
     */
    @RequestMapping("/updateRemark")
    @RequiresPermissions("userold:userold:update")
    public R updateRemark(@RequestBody UserOldEntity userold){
			userOldService.updateUserRemark(userold);
        return R.ok();
    }
    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("userold:userold:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			userOldService.deleteById(id);
	}
        return R.ok();
    }

}
