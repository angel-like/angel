package com.xmsy.server.zxyy.manager.modules.manager.messagemanagement.controller;

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
import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.messagemanagement.entity.MessageManagementEntity;
import com.xmsy.server.zxyy.manager.modules.manager.messagemanagement.service.MessageManagementService;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.SysRoleEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sys.service.SysRoleService;



/**
 * 管理员站内信管理
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-16 14:39:41
 */
@RestController
@RequestMapping("messagemanagement/adminmessage")
public class AdminMessageController {
    @Autowired
    private MessageManagementService messageManagementService;
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("messagemanagement:adminmessage:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = messageManagementService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 角色
     */
    @RequestMapping("/getRole")
    @RequiresPermissions("messagemanagement:messagemanagement:info")
    public R getRoleList(){
		List<SysRoleEntity> dataList = sysRoleService.selectList(new EntityWrapper<SysRoleEntity>(new SysRoleEntity()));
		List<Long> ids=new ArrayList<>();
		if(dataList!=null && !dataList.isEmpty()) {
			for(SysRoleEntity data:dataList) {
				ids.add(data.getRoleId());
			}
		}
        return R.ok().put("roleList", dataList).put("ids", ids);
    }
    
    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("messagemanagement:adminmessage:info")
    public R info(@PathVariable("id") Long id){
			MessageManagementEntity messageManagement = messageManagementService.selectById(id);
        return R.ok().put("messageManagement", messageManagement);
    }

    /**
     * 保存
     * @throws Exception 
     */
    @SysLog("管理员站内信新增")
    @RequestMapping("/save")
    @RequiresPermissions("messagemanagement:adminmessage:save")
    public R save(@RequestBody MessageManagementEntity messageManagement) throws Exception{
		boolean isOk=messageManagementService.saveAdminMessage(messageManagement);
        return R.ok().put("isOk", isOk);
    }

    /**
     * 修改
     * @throws Exception 
     */
    @SysLog("管理员站内信修改")
    @RequestMapping("/update")
    @RequiresPermissions("messagemanagement:adminmessage:update")
    public R update(@RequestBody MessageManagementEntity messageManagement) throws Exception{
    	boolean isOk=messageManagementService.saveAdminMessage(messageManagement);
        return R.ok().put("isOk", isOk);
    }

    /**
     * 删除
     */
    @SysLog("管理员站内信删除")
    @RequestMapping("/delete")
    @RequiresPermissions("messagemanagement:adminmessage:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			messageManagementService.deleteById(id);
	}
        return R.ok();
    }

}
