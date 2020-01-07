package com.xmsy.server.zxyy.manager.modules.manager.usertrialconfig.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.usertrialconfig.entity.UserTrialConfigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.usertrialconfig.service.UserTrialConfigService;



/**
 * 试玩账号配置
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-25 15:52:20
 */
@RestController
@RequestMapping("usertrialconfig/usertrialconfig")
public class UserTrialConfigController {
    @Autowired
    private UserTrialConfigService userTrialConfigService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("usertrialconfig:usertrialconfig:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userTrialConfigService.queryPage(params);
        return R.ok().put("page", page);
    }
    /**
     * 信息
     */
    @RequestMapping("/getinfo")
    @RequiresPermissions("usertrialconfig:usertrialconfig:info")
    public R getinfo(){
    	List<UserTrialConfigEntity> dataList= userTrialConfigService.selectList(new EntityWrapper<UserTrialConfigEntity>(null));
    	if(dataList!=null && dataList.size()>0) {
    		return R.ok().put("usertrialconfig", dataList.get(0));
    	}
    	return R.ok();
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("usertrialconfig:usertrialconfig:info")
    public R info(@PathVariable("id") Long id){
			UserTrialConfigEntity userTrialConfig = userTrialConfigService.selectById(id);
        return R.ok().put("usertrialconfig", userTrialConfig);
    }

    /**
     * 保存
     */
    @SysLog("新增试玩账号配置")
    @RequestMapping("/save")
    @RequiresPermissions("usertrialconfig:usertrialconfig:save")
    public R save(@RequestBody UserTrialConfigEntity userTrialConfig){
			userTrialConfigService.save(userTrialConfig);
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改试玩账号配置")
    @RequestMapping("/update")
    @RequiresPermissions("usertrialconfig:usertrialconfig:update")
    public R update(@RequestBody UserTrialConfigEntity userTrialConfig){
			userTrialConfigService.save(userTrialConfig);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("usertrialconfig:usertrialconfig:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			userTrialConfigService.deleteById(id);
	}
        return R.ok();
    }

}
