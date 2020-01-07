package com.xmsy.server.zxyy.manager.modules.manager.userinfo.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.userinfo.entity.UserInfoEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userinfo.service.UserInfoService;



/**
 * 用户基本信息表
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-15 10:49:21
 */
@RestController
@RequestMapping("userinfo/userinfo")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("userinfo:userinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userInfoService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("userinfo:userinfo:info")
    public R info(@PathVariable("id") Long id){
			UserInfoEntity userInfo = userInfoService.selectById(id);
        return R.ok().put("userinfo", userInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("userinfo:userinfo:save")
    public R save(@RequestBody UserInfoEntity userInfo){
		userInfoService.insert(userInfo);
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改用户信息")
    @RequestMapping("/update")
    @RequiresPermissions("userinfo:userinfo:update")
    public R update(@RequestBody UserInfoEntity userInfo){
		userInfoService.updateUserBaseInfo(userInfo);
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除用户信息")
    @RequestMapping("/delete")
    @RequiresPermissions("userinfo:userinfo:delete")
    public R delete(@RequestBody Long[] ids){
	    for (Long id : ids) {
			userInfoService.deleteById(id);
		}
        return R.ok();
    }

}
