package com.xmsy.server.zxyy.manager.modules.manager.userrebate.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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
import com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.entity.SysDictionaryEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.service.SysDictionaryService;
import com.xmsy.server.zxyy.manager.modules.manager.userrebate.entity.UserRebateEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrebate.service.UserRebateService;



/**
 * 用户返利
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-02-16 18:45:34
 */
@RestController
@RequestMapping("userrebate/userrebate")
public class UserRebateController {
    @Autowired
    private UserRebateService userRebateService;
    @Autowired
    private SysDictionaryService sysDictionaryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("userrebate:userrebate:list")
    public R list(UserRebateEntity userrebate, PageParam pageParam){
        Page<UserRebateEntity> result = new Page<UserRebateEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<UserRebateEntity> entityWrapper = new EntityWrapper<UserRebateEntity>(userrebate);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		userrebate.selectPage(result, entityWrapper);
		List<UserRebateEntity> list=result.getRecords();
		if(!CollectionUtils.isEmpty(list)) {
			for(UserRebateEntity entity:list) {
				if(entity!=null) {
					if(entity.getType()!=null&&entity.getType()!=0) {
						SysDictionaryEntity dictionaryEntity=sysDictionaryService.selectOne(new EntityWrapper<SysDictionaryEntity>(null).eq("parent_code", "RewardType").eq("code", entity.getType().toString()));
						if(dictionaryEntity!=null) {
							if(dictionaryEntity.getName()!=null) {
								entity.setTypeName(dictionaryEntity.getName());
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
    @RequiresPermissions("userrebate:userrebate:info")
    public R info(@PathVariable("id") Long id){
			UserRebateEntity userRebate = userRebateService.selectById(id);
        return R.ok().put("userrebate", userRebate);
    }

    /**
     * 保存
     */
    @SysLog("用户返利新增")
    @RequestMapping("/save")
    @RequiresPermissions("userrebate:userrebate:save")
    public R save(@RequestBody UserRebateEntity userrebate){
			userRebateService.insert(userrebate);
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("用户返利修改")
    @RequestMapping("/update")
    @RequiresPermissions("userrebate:userrebate:update")
    public R update(@RequestBody UserRebateEntity userrebate){
			userRebateService.updateById(userrebate);
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("用户返利删除")
    @RequestMapping("/delete")
    @RequiresPermissions("userrebate:userrebate:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			userRebateService.deleteById(id);
	}
        return R.ok();
    }

}
