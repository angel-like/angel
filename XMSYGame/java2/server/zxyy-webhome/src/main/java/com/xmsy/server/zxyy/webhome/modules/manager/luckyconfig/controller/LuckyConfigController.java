package com.xmsy.server.zxyy.webhome.modules.manager.luckyconfig.controller;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;

import com.xmsy.common.define.page.PageParam;

import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;


import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.luckyconfig.entity.LuckyConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.luckyconfig.service.LuckyConfigService;


import java.util.List;


/**
 * 幸运转盘
 *
 * @author ayang
 * @email xxxxx
 * @date 2019-11-21 12:58:04
 */
@RestController
@RequestMapping("luckyconfig/luckyconfig")
public class LuckyConfigController {
    @Autowired
    private LuckyConfigService luckyConfigService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("luckyconfig:luckyconfig:list")
    public R list(LuckyConfigEntity luckyconfig, PageParam pageParam){
        Page<LuckyConfigEntity> result = new Page<LuckyConfigEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<LuckyConfigEntity> entityWrapper = new EntityWrapper<LuckyConfigEntity>(luckyconfig);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		luckyconfig.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("luckyconfig:luckyconfig:info")
    public R info(@PathVariable("id") Long id){
			LuckyConfigEntity luckyConfig = luckyConfigService.selectById(id);
        return R.ok().put("luckyconfig", luckyConfig);
    }
    /**
     * 信息
     */
    @RequestMapping("/getconfig/{id}")
    @RequiresPermissions("luckyconfig:luckyconfig:info")
    public R getconfig(@PathVariable("id") Long id){
        List<LuckyConfigEntity> list = luckyConfigService.getAllconfig(id);
        List<String> nameList = luckyConfigService.getNames(id);
        return R.ok().put("list", list).put("nameList",nameList);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("luckyconfig:luckyconfig:save")
    public R save(@RequestBody LuckyConfigEntity luckyconfig){
			luckyConfigService.insert(luckyconfig);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("luckyconfig:luckyconfig:update")
    public R update(@RequestBody LuckyConfigEntity luckyconfig){
			luckyConfigService.updateById(luckyconfig);
        return R.ok();
    }
    /**
     * 修改
     */
    @RequestMapping("/updateConfig")
    @RequiresPermissions("luckyconfig:luckyconfig:update")
    public R updateConfig(@RequestBody JSONObject jsonObject){
        String configs = jsonObject.getStr("configs");
        List<LuckyConfigEntity>  configList  =  JSON.parseArray(configs, LuckyConfigEntity.class);

       for (LuckyConfigEntity luckyConfigEntity : configList) {
           luckyConfigService.updateById(luckyConfigEntity);
       }
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("luckyconfig:luckyconfig:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			luckyConfigService.deleteById(id);
	}
        return R.ok();
    }

}
