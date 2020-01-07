package com.xmsy.server.zxyy.manager.modules.manager.userpassword.controller;

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
import com.xmsy.server.zxyy.manager.modules.manager.userpassword.entity.UserPasswordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userpassword.service.UserPasswordService;



/**
 * 用户密码表
 *
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
@RestController
@RequestMapping("userpassword/userpassword")
public class UserPasswordController {
    @Autowired
    private UserPasswordService userPasswordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("userpassword:userpassword:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userPasswordService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("userpassword:userpassword:info")
    public R info(@PathVariable("id") Long id){
			UserPasswordEntity userPassword = userPasswordService.selectById(id);
        return R.ok().put("userPassword", userPassword);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("userpassword:userpassword:save")
    public R save(@RequestBody UserPasswordEntity userPassword){
			userPasswordService.insert(userPassword);
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("密码修改")
    @RequestMapping("/update")
    @RequiresPermissions("userpassword:userpassword:update")
    public R update(@RequestBody UserPasswordEntity userPassword){
			userPasswordService.updateById(userPassword);
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("密码删除")
    @RequestMapping("/delete")
    @RequiresPermissions("userpassword:userpassword:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			userPasswordService.deleteById(id);
	}
        return R.ok();
    }

}
