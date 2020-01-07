package com.xmsy.server.zxyy.manager.modules.manager.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.manager.cache.LocalContentCache;
import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.annotation.UserLog;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.SysUserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserPasswordParam;
import com.xmsy.server.zxyy.manager.modules.manager.userpassword.service.UserPasswordService;
import com.xmsy.server.zxyy.manager.utils.IpUtil;

import lombok.extern.slf4j.Slf4j;



/**
 * 用户修改密码
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-04 10:00:25
 */
@Slf4j
@RestController
@RequestMapping("user/passwd")
public class EditPasswordController {
    @Autowired
    private UserPasswordService userPasswordService;
    @Autowired
	private LocalContentCache localContentCache;

    /**
     * 修改登录密码
     */
    @SysLog("修改登录密码")
    @UserLog(value="修改登录密码")
    @RequestMapping("/editLogin")
    @RequiresPermissions("user:user:editpasswd")
    public R editLogin(@RequestBody UserPasswordParam user,HttpServletRequest httpServletRequest){
    	log.info("[editLogin] UserPasswordParam {}", user);
    	String ip = IpUtil.getIPAddress(httpServletRequest);
    	//用户名
		String username = ((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getUsername();
		log.info("[editLogin] UserPasswordParam {} ,ip {} ,username {}", user,ip,username);
    	userPasswordService.editLogin(user,ip,username);
    	localContentCache.remove(SysConstant.PASSWORD_ERROR + user.getUserId());
        return R.ok();
    }

    /**
     * 修改取款密码
     */
    @SysLog("修改取款密码")
    @UserLog(value="修改取款密码")
    @RequestMapping("/editBank")
    @RequiresPermissions("user:user:editpasswd")
    public R editBank(@RequestBody UserPasswordParam user){
    	log.info("[editLogin] UserPasswordParam {}", user);
    	userPasswordService.editBank(user);
        return R.ok();
    }


}
