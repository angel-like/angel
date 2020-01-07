package com.xmsy.server.zxyy.webhome.modules.manager.userlog.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.userlog.entity.UserLogEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userlog.service.UserLogService;



/**
 * 操作会员表日志
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-04 16:04:27
 */
@RestController
@RequestMapping("userlog/userlog")
public class UserLogController {
    @Autowired
    private UserLogService userLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("userlog:userlog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userLogService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("userlog:userlog:info")
    public R info(@PathVariable("id") Long id){
			UserLogEntity userLog = userLogService.selectById(id);
        return R.ok().put("userLog", userLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("userlog:userlog:save")
    public R save(@RequestBody UserLogEntity userLog){
			userLogService.insert(userLog);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("userlog:userlog:update")
    public R update(@RequestBody UserLogEntity userLog){
			userLogService.updateById(userLog);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("userlog:userlog:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			userLogService.deleteById(id);
	}
        return R.ok();
    }

}
