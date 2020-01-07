package com.xmsy.server.zxyy.webhome.modules.manager.user.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.webhome.cache.LocalContentCache;
import com.xmsy.server.zxyy.webhome.common.annotation.SysLog;
import com.xmsy.server.zxyy.webhome.common.annotation.UserLog;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.app.event.PushService;
import com.xmsy.server.zxyy.webhome.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.service.UserHierarchyService;
import com.xmsy.server.zxyy.webhome.modules.manager.userinfo.entity.UserInfoEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userinfo.service.UserInfoService;
import com.xmsy.server.zxyy.webhome.utils.VerificationUitl;

import lombok.extern.slf4j.Slf4j;



/**
 * 对会员操作
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-04 10:00:25
 */
@Slf4j
@RestController
@RequestMapping("user/operation")
public class UserOperationController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserHierarchyService userHierarchyService;
    @Autowired
    private PushService pushService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
	private LocalContentCache localContentCache;
    
    
    /**
     * 修改会员基本信息
     */
    @SysLog("修改会员基本信息")
    @UserLog(value="修改会员基本信息")
    @RequestMapping("/updateBaseInfo")
    @RequiresPermissions("userinfo:userinfo:update")
    public R updateUserBaseInfo(@RequestBody UserInfoEntity userInfo){
    	boolean editName = !StringUtils.isBlank(userInfo.getUserName());
    	if (editName && !VerificationUitl.userNameVerification(userInfo.getUserName())) {
			throw new RRException(ErrorCode.UserErrorCode.USER_NAME_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_NAME_ERRO.getCode());
		}
		userInfoService.updateUserBaseInfo(userInfo);
		if(editName) {
			UserEntity pushMessage = new UserEntity();
			pushMessage.setId(userInfo.getUserId());
			pushMessage.setUserName(userInfo.getUserName());
			UserInfoMessage message = new UserInfoMessage(pushMessage, null);
			log.info("[修改用户真实姓名-推送消息] message {}", message);
			pushService.pushUserInfo(message);
		}
        return R.ok();
    }
    
    /**
     * 修改层级
     */
    @SysLog("修改层级")
    @UserLog(value="修改层级")
    @RequestMapping("/editHierarchy")
    @RequiresPermissions("user:user:editHierarchy")
    public R editHierarchy(@RequestBody UserEntity user){
    	userService.updateById(user);
        return R.ok();
    }
    
    /**
     * 修改层级
     */
    @SysLog("修改风控层级")
    @UserLog(value="修改风控层级")
    @RequestMapping("/editRiskHierarchy")
    @RequiresPermissions("user:user:editRiskHierarchy")
    public R editRiskHierarchy(@RequestBody UserEntity user){
    	userService.updateById(user);
    	UserEntity pushMessage = new UserEntity();
		pushMessage.setId(user.getId());
		pushMessage.setRiskHierarchyId(user.getRiskHierarchyId());
    	UserInfoMessage message = new UserInfoMessage(pushMessage, null);
		log.info("[修改用户风控层级-推送消息] message {}", message);
		pushService.pushServerUserInfo(message);
        return R.ok();
    }
    
    /**
     * 修改余额
     */
    @SysLog("修改余额")
    @UserLog(value="修改余额")
    @RequestMapping("/editUserMoney")
    @RequiresPermissions("user:user:editUserMoney")
    public R editUserMoney(@RequestBody UserEntity user){
    	userService.updateById(user);
        return R.ok();
    }
    
    /**
     * 拉黑/解除
     */
    @SysLog("修改状态")
    @UserLog(value="修改状态")
    @RequestMapping("/editForbidden")
    @RequiresPermissions("user:user:more")
    public R editForbidden(@RequestBody UserEntity user){
    	userService.editForbidden(user);
    	if (user.getForbiddenEnable()) {// 拉黑
    		log.info("拉黑 通知游戏服踢人 userid:{}",user.getId());
    		//通知游戏服踢人
        	pushService.tickUser(user.getId(),"该账户被拉入黑名单,请联系客服人员！");
    	}
        return R.ok();
    }
    
    /**
     * 停压/解除
     */
    @SysLog("修改状态")
    @UserLog(value="修改状态")
    @RequestMapping("/editNobet")
    @RequiresPermissions("user:user:more")
    public R editNobet(@RequestBody UserEntity user){
    	userService.updateById(user);
    	if(user.getNobetEnable()) {
    		log.info("停压 通知游戏服踢人 userid:{}",user.getId());
    		//通知游戏服踢人
        	pushService.tickUser(user.getId(),"该账户被停压,请联系客服人员！");
    	}
        return R.ok();
    }
    
    /**
     * 异常/正常
     */
    @SysLog("修改状态")
    @UserLog(value="修改状态")
    @RequestMapping("/editAbnormal")
    @RequiresPermissions("user:user:more")
    public R editAbnormal(@RequestBody UserEntity user){
    	if(user.getAbnormalEnable()) {
    		log.info("异常 通知游戏服 userid:{}",user.getId());
    		//通知游戏服
    	}else {
    		user.setHierarchyId(userHierarchyService.getDefaultHierarchy().getId());
    		log.info("正常 通知游戏服 userid:{}",user.getId());
    		//通知游戏服
    	}
    	userService.updateById(user);
        return R.ok();
    }
    
    /**
     * 冻结/解除
     */
    @SysLog("修改状态")
    @UserLog(value="修改状态")
    @RequestMapping("/editFrozen")
    @RequiresPermissions("user:user:more")
    public R editFrozen(@RequestBody UserEntity user){
    	userService.updateById(user);
        return R.ok();
    }
    
    /**
     * 登出
     */
    @SysLog("强制登出")
    @UserLog(value="强制登出")
    @RequestMapping("/LoginOutMember")
    @RequiresPermissions("user:user:more")
    public R LoginOutMember(@RequestBody UserEntity user){
    	if(user!=null && user.getId()!=null) {
    		Object token=localContentCache.get(user.getId().toString());
        	if(token !=null) {
        		UserEntity cacheUser =(UserEntity) localContentCache.get(token.toString());
        		if(cacheUser!=null) {
        			//如果不为空表示已登陆,将账号强制退出
        			localContentCache.remove(token.toString());
        			localContentCache.remove(user.getId().toString());
        		}
        	}
        	pushService.tickUser(user.getId());
    	}
        return R.ok();
    }
    
    /**
     * 编辑会员银行卡信息
     */
    @SysLog("编辑会员银行卡信息")
    @UserLog(value="编辑会员银行卡信息")
    @RequestMapping("/editBankInfo")
    @RequiresPermissions("user:user:editbankinfo")
    public R editBankInfo(@RequestBody UserInfoEntity userInfo){
    	userInfoService.updateById(userInfo);
        return R.ok();
    }
    
    /**
     * 删除会员银行卡信息
     */
    @SysLog("删除会员银行卡信息")
    @UserLog(value="删除会员银行卡信息")
    @RequestMapping("/deleteBankInfo")
    @RequiresPermissions("user:user:deletebankinfo")
    public R deleteBankInfo(@RequestBody UserEntity user){
    	userService.deleteBankInfo(user.getId());
        return R.ok();
    }
    
    /**
     * 删除会员
     */
    @SysLog("删除会员")
    @UserLog(value="删除会员")
    @RequestMapping("/deleteUser")
    @RequiresPermissions("user:user:deleteUser")
    public R deleteUser(@RequestBody UserEntity user){
    	userService.deleteUser(user.getId());
    	log.info("停压 通知游戏服踢人 userid:{}",user.getId());
		//通知游戏服踢人
    	pushService.tickUser(user.getId(),"该账户有异常,请联系客服人员！");
        return R.ok();
    }




}
