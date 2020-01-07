package com.xmsy.server.zxyy.manager.modules.manager.messageuser.controller;

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
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.messageuser.entity.MessageUserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.messageuser.service.MessageUserService;



/**
 * 消息管理
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-02 16:39:41
 */
@RestController
@RequestMapping("messageuser/messageuser")
public class MessageUserController {
    @Autowired
    private MessageUserService messageUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("messageuser:messageuser:list")
    public R list(MessageUserEntity mesageUser, PageParam pageParam) {
    	String userAccount=mesageUser.getUserAccount();
//    	mesageUser.setUserAccount(null);
		Page<MessageUserEntity> result = new Page<MessageUserEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<MessageUserEntity> entityWrapper = new EntityWrapper<MessageUserEntity>(mesageUser).like("user_account", userAccount);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		mesageUser.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("messageuser:messageuser:info")
    public R info(@PathVariable("id") Long id){
			MessageUserEntity messageUser = messageUserService.selectById(id);
        return R.ok().put("messageUser", messageUser);
    }

    /**
     * 保存
     */
    @SysLog("消息管理新增")
    @RequestMapping("/save")
    @RequiresPermissions("messageuser:messageuser:save")
    public R save(@RequestBody MessageUserEntity messageUser){
			messageUserService.insert(messageUser);
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("消息管理修改")
    @RequestMapping("/update")
    @RequiresPermissions("messageuser:messageuser:update")
    public R update(@RequestBody MessageUserEntity messageUser){
			messageUserService.updateById(messageUser);
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("消息管理删除")
    @RequestMapping("/delete")
    @RequiresPermissions("messageuser:messageuser:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			messageUserService.deleteById(id);
	}
        return R.ok();
    }

}
