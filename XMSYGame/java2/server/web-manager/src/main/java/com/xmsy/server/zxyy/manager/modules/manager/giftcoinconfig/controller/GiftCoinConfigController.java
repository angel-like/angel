package com.xmsy.server.zxyy.manager.modules.manager.giftcoinconfig.controller;

import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.giftcoinconfig.entity.GiftCoinConfigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.giftcoinconfig.service.GiftCoinConfigService;
import com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.entity.SysDictionaryEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.service.SysDictionaryService;
import com.xmsy.server.zxyy.manager.modules.manager.sysmessagemodel.entity.SysMessageModelEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysmessagemodel.service.SysMessageModelService;
import com.xmsy.server.zxyy.manager.modules.manager.uservip.entity.UserVipEntity;
import com.xmsy.server.zxyy.manager.modules.manager.uservip.service.UserVipService;

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
 * 金币奖励配置
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-21 11:00:51
 */
@RestController
@RequestMapping("giftcoinconfig/giftcoinconfig")
public class GiftCoinConfigController {
    @Autowired
    private GiftCoinConfigService giftCoinConfigService;
    
    @Autowired
    private SysDictionaryService sysDictionaryService;
    
    @Autowired
    private SysMessageModelService sysMessageModelService;
    
    @Autowired
    private UserVipService userVipService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("giftcoinconfig:giftcoinconfig:list")
    public R list(GiftCoinConfigEntity giftcoinconfig, PageParam pageParam){
        Page<GiftCoinConfigEntity> result = new Page<GiftCoinConfigEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GiftCoinConfigEntity> entityWrapper = new EntityWrapper<GiftCoinConfigEntity>(giftcoinconfig);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		giftcoinconfig.selectPage(result, entityWrapper);
		List<GiftCoinConfigEntity> list=result.getRecords();
		if(!CollectionUtils.isEmpty(list)) {
			for(GiftCoinConfigEntity entity:list) {
				if(entity!=null) {
					if(entity.getType()!=null&&entity.getType()!=0) {
						SysDictionaryEntity dictionaryEntity=sysDictionaryService.selectOne(new EntityWrapper<SysDictionaryEntity>(null).eq("parent_code", "UserActivityAward").eq("code", entity.getType().toString()));
						if(dictionaryEntity!=null) {
							if(dictionaryEntity.getName()!=null) {
								entity.setTypeName(dictionaryEntity.getName());
							}
						}
					}
					if(entity.getTemplateId()!=0&&entity.getTemplateId()!=null) {
						SysMessageModelEntity sysMessageModelEntity = sysMessageModelService.selectById(entity.getTemplateId());
						if(sysMessageModelEntity!=null&&!StringUtils.isEmpty(sysMessageModelEntity.getName())) {
							entity.setTemplateName(sysMessageModelEntity.getName());
						}
					}
					if(entity.getVipId()!=0&&entity.getVipId()!=null) {
						UserVipEntity userVipEntity = userVipService.selectById(entity.getVipId());
						if(userVipEntity!=null&&!StringUtils.isEmpty(userVipEntity.getName())) {
							entity.setVipName(userVipEntity.getName());
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
    @RequiresPermissions("giftcoinconfig:giftcoinconfig:info")
    public R info(@PathVariable("id") Long id){
			GiftCoinConfigEntity giftCoinConfig = giftCoinConfigService.selectById(id);
        return R.ok().put("giftcoinconfig", giftCoinConfig);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("giftcoinconfig:giftcoinconfig:save")
    public R save(@RequestBody GiftCoinConfigEntity giftcoinconfig){
			giftCoinConfigService.insert(giftcoinconfig);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("giftcoinconfig:giftcoinconfig:update")
    public R update(@RequestBody GiftCoinConfigEntity giftcoinconfig){
			giftCoinConfigService.updateById(giftcoinconfig);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("giftcoinconfig:giftcoinconfig:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			giftCoinConfigService.deleteById(id);
	}
        return R.ok();
    }

}
