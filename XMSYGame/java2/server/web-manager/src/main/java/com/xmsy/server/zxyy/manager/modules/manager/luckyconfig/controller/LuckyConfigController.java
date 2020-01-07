package com.xmsy.server.zxyy.manager.modules.manager.luckyconfig.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.luckyconfig.entity.LuckyConfigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.luckyconfig.service.LuckyConfigService;

import cn.hutool.json.JSONObject;



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
            luckyConfigService.updateConfigById(luckyConfigEntity);
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
