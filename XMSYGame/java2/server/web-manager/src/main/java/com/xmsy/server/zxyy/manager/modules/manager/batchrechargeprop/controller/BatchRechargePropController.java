package com.xmsy.server.zxyy.manager.modules.manager.batchrechargeprop.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.cache.LocalContentCache;
import com.xmsy.server.zxyy.manager.common.annotation.AdminRechargeOrderRepeat;
import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.annotation.UserLog;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.batchrechargeprop.entity.BatchRechargePropEntity;
import com.xmsy.server.zxyy.manager.modules.manager.batchrechargeprop.service.BatchRechargePropService;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.SysUserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysprop.entity.SysPropEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysprop.service.SysPropService;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;



/**
 * 批量充值会员道具
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-27 11:41:20
 */
@RestController
@RequestMapping("batchrechargeprop/batchrechargeprop")
public class BatchRechargePropController {
    @Autowired
    private BatchRechargePropService batchRechargePropService;
    
    @Autowired
    private LocalContentCache localContentCache;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private SysPropService sysPropService;
    
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("batchrechargeprop:batchrechargeprop:list")
    public R list(BatchRechargePropEntity batchrechargeprop, PageParam pageParam){
        Page<BatchRechargePropEntity> result = new Page<BatchRechargePropEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<BatchRechargePropEntity> entityWrapper = new EntityWrapper<BatchRechargePropEntity>(batchrechargeprop);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		batchrechargeprop.selectPage(result, entityWrapper);
		List<BatchRechargePropEntity> list=result.getRecords();
		if(!CollectionUtils.isEmpty(list)) {
			for(BatchRechargePropEntity entity:list) {
				if(entity!=null) {
					if(entity.getVipId()!=null&&entity.getVipId()!=0) {
						entity.setVipName(localContentCache.getVipName(entity.getVipId()));
					}
					if(entity.getHierarchyId()!=null&&entity.getHierarchyId()!=0) {
						entity.setHierarchyName(localContentCache.getHierarchyName(entity.getHierarchyId()));
					}
					if(entity.getPropId()!=null&&entity.getPropId()!=0) {
						SysPropEntity sysPropEntity=sysPropService.selectById(entity.getPropId());
						if(sysPropEntity!=null) {
							if(sysPropEntity.getName()!=null) {
								entity.setPropName(sysPropEntity.getName());
							}
						}
					}
				}
			}
		}
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("batchrechargeprop:batchrechargeprop:info")
    public R info(@PathVariable("id") Long id){
			BatchRechargePropEntity batchRechargeProp = batchRechargePropService.selectById(id);
        return R.ok().put("batchrechargeprop", batchRechargeProp);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("batchrechargeprop:batchrechargeprop:save")
    public R save(@RequestBody BatchRechargePropEntity batchrechargeprop){
    	batchRechargePropService.insert(batchrechargeprop);
        return R.ok();
    }
    /**
	 * 获取会员
	 */
	@RequestMapping("/getUser")
	@RequiresPermissions("batchrechargeprop:batchrechargeprop:getUser")
	public R getUser(@RequestParam(value = "account") String account) {
		UserEntity user = new UserEntity();
		user.setAccount(account);
		user=userService.selectOne(new EntityWrapper<UserEntity>(user));
		if(user ==null || user.getId()==null) {
			throw new RRException(
					 ErrorCode.UserErrorCode.USER_ACCOUNT_INVALID.getErrMsg(),
					ErrorCode.UserErrorCode.USER_ACCOUNT_INVALID.getCode());
		}
		Map<String, Object> data =new HashMap<String, Object>();
		data.put("roomCard", user.getRoomCard());
		data.put("userName", user.getUserName());
		data.put("userId", user.getId());
		return R.ok().put("data", data);
	}
    
    /**
	 * 创建
	 */
	@AdminRechargeOrderRepeat("创建人工赠送会员道具校验")
	@SysLog(value="人工赠送会员道具")
	@UserLog(value="人工赠送会员道具")
	@RequestMapping("/create")
	@RequiresPermissions("batchrechargeprop:batchrechargeprop:create")
	public R create(@RequestBody BatchRechargePropEntity batchrechargeprop) {
		SysUserEntity sysUser = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		batchRechargePropService.createBatchrechargeprop(batchrechargeprop, sysUser.getUserId(),
				sysUser.getUsername());
		return R.ok();
	}

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("batchrechargeprop:batchrechargeprop:update")
    public R update(@RequestBody BatchRechargePropEntity batchrechargeprop){
			batchRechargePropService.updateById(batchrechargeprop);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("batchrechargeprop:batchrechargeprop:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			batchRechargePropService.deleteById(id);
	}
        return R.ok();
    }

}
