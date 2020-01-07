package com.xmsy.server.zxyy.webhome.modules.manager.messagemanagement.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.annotation.SysLog;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.messagemanagement.entity.MessageManagementEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.messagemanagement.service.MessageManagementService;
import com.xmsy.server.zxyy.webhome.modules.manager.messageuser.entity.MessageUserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.messageuser.param.MessageRequestVo;
import com.xmsy.server.zxyy.webhome.modules.manager.messageuser.service.MessageUserService;
import com.xmsy.server.zxyy.webhome.modules.manager.sys.entity.SysUserEntity;



/**
 * 消息管理
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-02 16:39:41
 */
@RestController
@RequestMapping("message/message")
public class QueryMessageController {
    @Autowired
    private MessageManagementService messageManagementService;
    @Autowired
    private MessageUserService messageUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(MessageRequestVo requestVo,PageParam pageParam){
    	SysUserEntity user=(SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    	requestVo.setContentType(2);
    	requestVo.setHierarchyIds(user.getRoleIds().split(","));
    	requestVo.setUserId(user.getUserId());
    	requestVo.setUserAccount(user.getUsername());
		Page<Map<String, Object>> result = messageManagementService.findMessagePage(pageParam, requestVo);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }
    /**
     * 未读条数
     */
    @RequestMapping("/getNum")
    public R getNum(){
    	SysUserEntity user=(SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    	MessageRequestVo requestVo=new MessageRequestVo();
    	requestVo.setContentType(2);
    	requestVo.setHierarchyIds(user.getRoleIds().split(","));
    	requestVo.setUserId(user.getUserId());
    	requestVo.setUserAccount(user.getUsername());
    	requestVo.setRegisterDate(user.getCreateTime());
    	return R.ok().put("num",messageManagementService.countUnreadNumber(requestVo));
    }


    
    /**
     * 信息
     */
    @RequestMapping("/info/{id}/{statusId}")
    public R info(@PathVariable("id") Long id,@PathVariable("statusId") Long statusId){
    	SysUserEntity user=(SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		MessageManagementEntity messageManagement = messageManagementService.selectById(id);
		//去更新已读
		MessageUserEntity messageUserEntity=new MessageUserEntity();
		messageUserEntity.setMessageId(messageManagement.getId());
		messageUserEntity.setStatus(true);
		messageUserEntity.setId(statusId);
		messageUserEntity.setUserId(user.getUserId());
		messageUserEntity.setUserAccount(user.getUsername());
		messageUserService.save(messageUserEntity);
		return R.ok().put("messageManagement", messageManagement);
    }


    /**
     * 设置删除
     */
    @SysLog("管理员站内信设置删除")
    @RequestMapping("/setdelete")
    public R delete(@RequestBody List<Long> ids){
    	SysUserEntity user=(SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    	messageUserService.setDeleteBatchForAdmin(user, ids);
        return R.ok();
    }
    /**
     * 设置已读
     */
    @SysLog("管理员站内信设置已读")
    @RequestMapping("/setread")
    public R setread(@RequestBody List<Long> ids){
    	SysUserEntity user=(SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    	messageUserService.setReadBatchForAdmin(user, ids);
    	return R.ok();
    }

}
