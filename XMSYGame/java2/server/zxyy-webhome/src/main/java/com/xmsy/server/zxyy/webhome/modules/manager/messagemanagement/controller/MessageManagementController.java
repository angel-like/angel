package com.xmsy.server.zxyy.webhome.modules.manager.messagemanagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.webhome.common.annotation.SysLog;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.app.event.PushService;
import com.xmsy.server.zxyy.webhome.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.webhome.modules.manager.messagemanagement.entity.MessageManagementEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.messagemanagement.service.MessageManagementService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.entity.UserHierarchyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.service.UserHierarchyService;

import lombok.extern.slf4j.Slf4j;



/**
 * 消息管理
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-02 16:39:41
 */
@Slf4j
@RestController
@RequestMapping("messagemanagement/messagemanagement")
public class MessageManagementController {
    @Autowired
    private MessageManagementService messageManagementService;
    @Autowired
    private UserHierarchyService userHierarchyService;
    @Autowired
	private PushService pushService;
    @Autowired
   	private UserService userService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("messagemanagement:messagemanagement:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = messageManagementService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/getHierarchy")
    @RequiresPermissions("messagemanagement:messagemanagement:info")
    public R getHierarchy(){
		List<UserHierarchyEntity> dataList = userHierarchyService.selectList(new EntityWrapper<UserHierarchyEntity>(new UserHierarchyEntity()));
		List<Long> ids=new ArrayList<>();
		if(dataList!=null && !dataList.isEmpty()) {
			for(UserHierarchyEntity data:dataList) {
				ids.add(data.getId());
			}
		}
        return R.ok().put("hierarchyList", dataList).put("ids", ids);
    }
    
    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("messagemanagement:messagemanagement:info")
    public R info(@PathVariable("id") Long id){
			MessageManagementEntity messageManagement = messageManagementService.selectById(id);
        return R.ok().put("messageManagement", messageManagement);
    }

    /**
     * 保存
     * @throws Exception 
     */
    @SysLog("用户站内信新增")
    @RequestMapping("/save")
    @RequiresPermissions("messagemanagement:messagemanagement:save")
    public R save(@RequestBody MessageManagementEntity messageManagement) throws Exception{
		boolean isOk=messageManagementService.save(messageManagement);
		if(messageManagement.getRightNow() && messageManagement.getTargetObject()==SysConstant.TARGET_OBJECT_1) {
			List<UserEntity>  userList = userService.findUserListByAccount(messageManagement.getUserAccount().split(","));
			if(userList!=null && !userList.isEmpty()) {
				for(UserEntity user : userList) {
					// 用户基本信息推送
					UserEntity pushMessage = new UserEntity();
					pushMessage.setId(user.getId());
					pushMessage.setUnreadNum(1);
					UserInfoMessage message = new UserInfoMessage(pushMessage, null);
					log.info("[用户未读消息-推送消息] message {}", message);
					pushService.pushExchange(message);
				}
			}
		}
		
        return R.ok().put("isOk", isOk);
    }

    /**
     * 修改
     * @throws Exception 
     */
    @SysLog("用户站内信修改")
    @RequestMapping("/update")
    @RequiresPermissions("messagemanagement:messagemanagement:update")
    public R update(@RequestBody MessageManagementEntity messageManagement) throws Exception{
    	boolean isOk=messageManagementService.save(messageManagement);
        return R.ok().put("isOk", isOk);
    }

    /**
     * 删除
     */
    @SysLog("用户站内信删除")
    @RequestMapping("/delete")
    @RequiresPermissions("messagemanagement:messagemanagement:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			messageManagementService.deleteById(id);
	}
        return R.ok();
    }

}
