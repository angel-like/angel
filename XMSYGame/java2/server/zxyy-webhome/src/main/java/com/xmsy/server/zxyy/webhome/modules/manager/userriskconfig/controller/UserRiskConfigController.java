package com.xmsy.server.zxyy.webhome.modules.manager.userriskconfig.controller;

import com.xmsy.common.define.page.PageParam;

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
import com.xmsy.server.zxyy.webhome.modules.manager.sysdictionary.entity.SysDictionaryEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sysdictionary.service.SysDictionaryService;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.entity.UserHierarchyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.service.UserHierarchyService;
import com.xmsy.server.zxyy.webhome.modules.manager.userriskconfig.entity.UserRiskConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userriskconfig.service.UserRiskConfigService;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;



/**
 * 用户风控配置表
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-05 11:28:56
 */
@RestController
@RequestMapping("userriskconfig/userriskconfig")
public class UserRiskConfigController {
    @Autowired
    private UserRiskConfigService userRiskConfigService;
    
    @Autowired
    private UserHierarchyService userHierarchyService;
    
    @Autowired
    private SysDictionaryService sysDictionaryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("userriskconfig:userriskconfig:list")
    public R list(UserRiskConfigEntity userriskconfig, PageParam pageParam){
        Page<UserRiskConfigEntity> result = new Page<UserRiskConfigEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<UserRiskConfigEntity> entityWrapper = new EntityWrapper<UserRiskConfigEntity>(userriskconfig);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		userriskconfig.selectPage(result, entityWrapper);
		List<UserRiskConfigEntity> list=result.getRecords();
		if(!CollectionUtils.isEmpty(list)) {
			for(UserRiskConfigEntity userRiskConfigEntity:list) {
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
        return R.ok().put("page", new PageUtils(list, result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));

    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("userriskconfig:userriskconfig:info")
    public R info(@PathVariable("id") Long id){
			UserRiskConfigEntity userRiskConfig = userRiskConfigService.selectById(id);
        return R.ok().put("userriskconfig", userRiskConfig);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("userriskconfig:userriskconfig:save")
    public R save(@RequestBody UserRiskConfigEntity userriskconfig){
			userRiskConfigService.insert(userriskconfig);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("userriskconfig:userriskconfig:update")
    public R update(@RequestBody UserRiskConfigEntity userriskconfig){
			userRiskConfigService.updateById(userriskconfig);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("userriskconfig:userriskconfig:delete")
    public R delete(@RequestBody Long[] ids){
	    for (Long id : ids) {
			userRiskConfigService.physicalDelete(id);
		}
        return R.ok();
    }
   


}
