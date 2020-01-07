package com.xmsy.server.zxyy.manager.modules.manager.lucky.controller;

import java.util.List;
import java.util.Map;

import com.xmsy.common.define.page.PageParam;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import com.xmsy.server.zxyy.manager.modules.manager.lucky.entity.LuckyEntity;
import com.xmsy.server.zxyy.manager.modules.manager.lucky.service.LuckyService;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;



/**
 * 幸运转盘
 *
 * @author ayang
 * @email xxxxx
 * @date 2019-11-21 12:58:04
 */
@RestController
@RequestMapping("lucky/lucky")
public class LuckyController {
    @Autowired
    private LuckyService luckyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("lucky:lucky:list")
    public R list(LuckyEntity lucky, PageParam pageParam){
        Page<LuckyEntity> result = new Page<LuckyEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<LuckyEntity> entityWrapper = new EntityWrapper<LuckyEntity>(lucky);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		lucky.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }

    /**
     * 列表
     */
    @RequestMapping("/newlist")
//    @RequiresPermissions("lucky:lucky:list")
    public R newlist(){
        List<Map<String, Object>> luckySettings = luckyService.getLuckySettings();
        return R.ok().put("data",luckySettings );
    }
    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("lucky:lucky:info")
    public R info(@PathVariable("id") Long id){
			LuckyEntity lucky = luckyService.selectById(id);
        return R.ok().put("lucky", lucky);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("lucky:lucky:save")
    public R save(@RequestBody LuckyEntity lucky){
			luckyService.insert(lucky);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("lucky:lucky:update")
    public R update(@RequestBody LuckyEntity lucky){
			luckyService.updateById(lucky);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("lucky:lucky:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			luckyService.deleteById(id);
	}
        return R.ok();
    }
    /**
     * 修改状态
     */
    @RequestMapping("/updateEnable")
    @RequiresPermissions("lucky:lucky:update")
    public R updateEnable(@RequestParam("id") Long id, @RequestParam("enable") Boolean enable) {
        LuckyEntity entity = new LuckyEntity();
        entity.setId(id);
        entity.setEnable(enable);
        luckyService.updateById(entity);
        return R.ok();
    }
}
