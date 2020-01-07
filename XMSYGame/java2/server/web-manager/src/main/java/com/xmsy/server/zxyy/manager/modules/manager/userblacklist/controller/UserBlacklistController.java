package com.xmsy.server.zxyy.manager.modules.manager.userblacklist.controller;

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
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.modules.manager.userblacklist.entity.UserBlacklistEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userblacklist.service.UserBlacklistService;



/**
 * 用户白名单表
 *
 * @author lzy
 * @email xxxxx
 * @date 2019-01-15 10:50:25
 */
@RestController
@RequestMapping("userblacklist/userblacklist")
public class UserBlacklistController {
    @Autowired
    private UserBlacklistService userBlacklistService;
    @Autowired
    private UserService userService;

    /**
     * 黑名单列表
     */
    @RequestMapping("/blacklist")
    @RequiresPermissions("userblacklist:userblacklist:list")
    public R blacklist(UserBlacklistEntity userBlacklist, PageParam pageParam){
    	userBlacklist.setType(false);
    	Page<UserBlacklistEntity> result = new Page<UserBlacklistEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<UserBlacklistEntity> entityWrapper = new EntityWrapper<UserBlacklistEntity>(userBlacklist);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		userBlacklist.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("userblacklist:userblacklist:info")
    public R info(@PathVariable("id") Long id){
			UserBlacklistEntity userBlacklist = userBlacklistService.selectById(id);
        return R.ok().put("userblacklist", userBlacklist);
    }

    /**
     * 保存
     */
    @SysLog("新增黑名单")
    @RequestMapping("/save")
    @RequiresPermissions("userblacklist:userblacklist:save")
    public R save(@RequestBody UserBlacklistEntity userBlacklist){
    	UserEntity user=new UserEntity();
    	user.setAccount(userBlacklist.getUserAccount());
    	user=userService.selectOne(new EntityWrapper<UserEntity>(user));
    	if(user!=null && user.getId()>0) {
    		userBlacklist.setUserId(user.getId());
    		userBlacklistService.insert(userBlacklist);
            return R.ok();
    	}else {
    		throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
    	}
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("userblacklist:userblacklist:update")
    public R update(@RequestBody UserBlacklistEntity userBlacklist){
			userBlacklistService.updateById(userBlacklist);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("userblacklist:userblacklist:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
	userBlacklistService.deleteById(id);
	}
        return R.ok();
    }

}
