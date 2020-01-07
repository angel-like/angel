package com.xmsy.server.zxyy.manager.modules.manager.userriskrecord.controller;

import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.entity.SysDictionaryEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.service.SysDictionaryService;
import com.xmsy.server.zxyy.manager.modules.manager.userhierarchy.entity.UserHierarchyEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userhierarchy.service.UserHierarchyService;
import com.xmsy.server.zxyy.manager.modules.manager.userriskrecord.entity.UserRiskRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userriskrecord.service.UserRiskRecordService;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;



/**
 * 用户风控记录表
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-06 14:56:24
 */
@RestController
@RequestMapping("userriskrecord/userriskrecord")
public class UserRiskRecordController {
    @Autowired
    private UserRiskRecordService userRiskRecordService;
    
    @Autowired
    private UserHierarchyService userHierarchyService;
    
    @Autowired
    private SysDictionaryService sysDictionaryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("userriskrecord:userriskrecord:list")
    public R list(UserRiskRecordEntity userriskrecord, PageParam pageParam){
        Page<UserRiskRecordEntity> result = new Page<UserRiskRecordEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<UserRiskRecordEntity> entityWrapper = new EntityWrapper<UserRiskRecordEntity>(userriskrecord);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		userriskrecord.selectPage(result, entityWrapper);
		List<UserRiskRecordEntity> list=result.getRecords();
		if(!CollectionUtils.isEmpty(list)) {
			for(UserRiskRecordEntity userRiskConfigEntity:list) {
				if(userRiskConfigEntity.getHierarchyId()!=0) {
					UserHierarchyEntity	userHierarchy = userHierarchyService.selectById(userRiskConfigEntity.getHierarchyId());
					if(userHierarchy!=null&&!StringUtils.isEmpty(userHierarchy.getName())) {
						userRiskConfigEntity.setHierarchyName(userHierarchy.getName());
					}
				}
				SysDictionaryEntity dictionaryEntity=sysDictionaryService.selectOne(new EntityWrapper<SysDictionaryEntity>(null).eq("parent_code", "riskConfig").eq("code", userRiskConfigEntity.getRiskType().toString()));
				if(dictionaryEntity!=null) {
					if(dictionaryEntity.getName()!=null) {
						userRiskConfigEntity.setRiskTypeName(dictionaryEntity.getName());
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
    @RequiresPermissions("userriskrecord:userriskrecord:info")
    public R info(@PathVariable("id") Long id){
			UserRiskRecordEntity userRiskRecord = userRiskRecordService.selectById(id);
        return R.ok().put("userriskrecord", userRiskRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("userriskrecord:userriskrecord:save")
    public R save(@RequestBody UserRiskRecordEntity userriskrecord){
			userRiskRecordService.insert(userriskrecord);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("userriskrecord:userriskrecord:update")
    public R update(@RequestBody UserRiskRecordEntity userriskrecord){
			userRiskRecordService.updateById(userriskrecord);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("userriskrecord:userriskrecord:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			userRiskRecordService.deleteById(id);
	}
        return R.ok();
    }

}
