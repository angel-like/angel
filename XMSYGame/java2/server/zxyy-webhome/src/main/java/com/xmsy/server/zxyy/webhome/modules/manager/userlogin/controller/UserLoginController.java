package com.xmsy.server.zxyy.webhome.modules.manager.userlogin.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.userlogin.entity.UserLoginEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userlogin.service.UserLoginService;



/**
 * 用户登陆记录表
 *
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
@RestController
@RequestMapping("userlogin/userlogin")
public class UserLoginController {
    @Autowired
    private UserLoginService userLoginService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("userlogin:userlogin:list")
    public R list(UserLoginEntity userLogin, PageParam pageParam){
    	if(StringUtils.isEmpty(userLogin.getAccount())) {
    		 return R.error("请输入要查询的会员账号");
    	}
    	Page<UserLoginEntity> result = userLoginService.pageList(userLogin,pageParam);
        return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("userlogin:userlogin:info")
    public R info(@PathVariable("id") Long id){
			UserLoginEntity userLogin = userLoginService.selectById(id);
        return R.ok().put("userLogin", userLogin);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("userlogin:userlogin:save")
    public R save(@RequestBody UserLoginEntity userLogin){
			userLoginService.insert(userLogin);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("userlogin:userlogin:update")
    public R update(@RequestBody UserLoginEntity userLogin){
			userLoginService.updateById(userLogin);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("userlogin:userlogin:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			userLoginService.deleteById(id);
	}
        return R.ok();
    }

}
