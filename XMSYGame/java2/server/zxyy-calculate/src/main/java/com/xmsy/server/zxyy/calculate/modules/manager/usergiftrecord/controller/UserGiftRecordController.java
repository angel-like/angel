package com.xmsy.server.zxyy.calculate.modules.manager.usergiftrecord.controller;

import com.xmsy.common.define.page.PageParam;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.server.zxyy.calculate.modules.manager.usergiftrecord.entity.UserGiftRecordEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.usergiftrecord.service.UserGiftRecordService;



import com.xmsy.server.zxyy.calculate.common.utils.PageUtils;
import com.xmsy.server.zxyy.calculate.common.utils.R;



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
