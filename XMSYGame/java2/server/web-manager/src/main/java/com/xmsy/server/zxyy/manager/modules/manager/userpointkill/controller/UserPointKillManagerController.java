package com.xmsy.server.zxyy.manager.modules.manager.userpointkill.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.MathUtil;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.modules.manager.userpointkill.param.UserPointKillManagerParam;
import com.xmsy.server.zxyy.manager.modules.manager.userpointkill.service.UserPointKillService;



/**
 * 点杀管理
 *
 * @author aye
 * @email xxxxx
 * @date 2019-11-22 11:27:03
 */
@RestController
@RequestMapping("userpointkillmanager/userpointkillmanager")
public class UserPointKillManagerController {
    @Autowired
    private UserPointKillService userPointKillService;
    @Autowired
    private UserService userService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("userpointkill:userpointkill:list")//权限也是点杀名单的权限
    public R list(UserEntity user, PageParam pageParam){
        Page<UserEntity> result = new Page<UserEntity>(pageParam.getPage(), pageParam.getLimit());
        //实体类分页查询要多设置几个不相关的参数  选择map好
        //Page<UserEntity> userPageList = userService.selectPage(result, new EntityWrapper<UserEntity>());
		Page<Map<String, Object>> userPageMapList = userService.selectMapsPage(result,
				new EntityWrapper<UserEntity>(user).orderBy("id", false));
        List<Map<String, Object>> userMapList = userPageMapList.getRecords();
        userPointKillService.findOtherData(userMapList,user);
		return R.ok().put("page", new PageUtils(userPageMapList));
    }


    /**
     * 点杀监管  --游戏记录为主表
     */
    @RequestMapping("/listNew")
    @RequiresPermissions("userpointkill:userpointkill:list")//权限也是点杀名单的权限
    public R listNew(UserPointKillManagerParam userPointKillManagerParam, PageParam pageParam){
    	Page<Map<String, Object>> result = new Page<Map<String, Object>>(pageParam.getPage(), pageParam.getLimit());
    	List<Map<String, Object>> pageList = userPointKillService.findUserPointKillManagerPage(userPointKillManagerParam, result);
    	for(Map<String, Object> map:pageList) {
    		BigDecimal totalCoin = userPointKillService.gainTotalCoin(MathUtil.getBigDecimal(map.get("userId")).longValue());
    		map.put("totalCoin", totalCoin);
    	}
    	result.setRecords(pageList);
		return R.ok().put("page",  new PageUtils(result));
    }

}
