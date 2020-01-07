package com.xmsy.server.zxyy.manager.modules.manager.userviprecord.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.userviprecord.entity.UserVipRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userviprecord.service.UserVipRecordService;



/**
 * 用户vip等级记录表
 *
 * @author adu
 * @email xxxxx
 * @date 2019-06-11 16:04:14
 */
@RestController
@RequestMapping("userviprecord/userviprecord")
public class UserVipRecordController {
    @Autowired
    private UserVipRecordService userVipRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("userviprecord:userviprecord:list")
    public R list(UserVipRecordEntity userviprecord, PageParam pageParam){
		return R.ok().put("page",userVipRecordService.findUserVipRecordPage(userviprecord, pageParam));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("userviprecord:userviprecord:info")
    public R info(@PathVariable("id") Long id){
			UserVipRecordEntity userVipRecord = userVipRecordService.selectById(id);
        return R.ok().put("userviprecord", userVipRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("userviprecord:userviprecord:save")
    public R save(@RequestBody UserVipRecordEntity userviprecord){
			userVipRecordService.insert(userviprecord);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("userviprecord:userviprecord:update")
    public R update(@RequestBody UserVipRecordEntity userviprecord){
			userVipRecordService.updateById(userviprecord);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("userviprecord:userviprecord:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			userVipRecordService.deleteById(id);
	}
        return R.ok();
    }

}
