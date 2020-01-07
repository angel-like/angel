package com.xmsy.server.zxyy.manager.modules.manager.usergiftrecord.controller;

import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.entity.SysDictionaryEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.service.SysDictionaryService;
import com.xmsy.server.zxyy.manager.modules.manager.sysprop.entity.SysPropEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysprop.service.SysPropService;
import com.xmsy.server.zxyy.manager.modules.manager.usergiftrecord.entity.UserGiftRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.usergiftrecord.service.UserGiftRecordService;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import cn.hutool.core.collection.CollectionUtil;



/**
 * 用户道具发放记录
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-24 09:34:18
 */
@RestController
@RequestMapping("usergiftrecord/usergiftrecord")
public class UserGiftRecordController {
    @Autowired
    private UserGiftRecordService userGiftRecordService;
    
    @Autowired
    private SysPropService sysPropService;
    
    @Autowired
    private SysDictionaryService sysDictionaryService;
    

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("usergiftrecord:usergiftrecord:list")
    public R list(UserGiftRecordEntity usergiftrecord, PageParam pageParam){
        Page<UserGiftRecordEntity> result = new Page<UserGiftRecordEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<UserGiftRecordEntity> entityWrapper = new EntityWrapper<UserGiftRecordEntity>(usergiftrecord);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		usergiftrecord.selectPage(result, entityWrapper);
		List<UserGiftRecordEntity> list = result.getRecords();
		if(!CollectionUtil.isEmpty(list)) {
			for(UserGiftRecordEntity entity:list) {
				if(entity!=null) {
					if(entity.getPropId()!=null&&entity.getPropId()!=0) {
						SysPropEntity sysProp = sysPropService.selectById(entity.getPropId());
						if(sysProp!=null&&!StringUtils.isEmpty(sysProp.getName())) {
							entity.setPropName(sysProp.getName());
						}
					}
					if(entity.getType()!=null&&entity.getType()!=0) {
						SysDictionaryEntity dictionaryEntity=sysDictionaryService.selectOne(new EntityWrapper<SysDictionaryEntity>(null).eq("parent_code", "UserActivityAward").eq("code", entity.getDetailType().toString()));
						if(dictionaryEntity!=null) {
							if(dictionaryEntity.getName()!=null) {
								entity.setDetailTypeName(dictionaryEntity.getName());
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
    @RequiresPermissions("usergiftrecord:usergiftrecord:info")
    public R info(@PathVariable("id") Long id){
			UserGiftRecordEntity userGiftRecord = userGiftRecordService.selectById(id);
        return R.ok().put("usergiftrecord", userGiftRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("usergiftrecord:usergiftrecord:save")
    public R save(@RequestBody UserGiftRecordEntity usergiftrecord){
			userGiftRecordService.insert(usergiftrecord);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("usergiftrecord:usergiftrecord:update")
    public R update(@RequestBody UserGiftRecordEntity usergiftrecord){
			userGiftRecordService.updateById(usergiftrecord);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("usergiftrecord:usergiftrecord:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			userGiftRecordService.deleteById(id);
	}
        return R.ok();
    }

}
