package com.xmsy.server.zxyy.manager.modules.manager.userbalancerecord.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.baomidou.mybatisplus.mapper.EntityWrapper;
//import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.userbalancerecord.entity.UserBalanceRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userbalancerecord.service.UserBalanceRecordService;



/**
 * 用户账户金额存取记录表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-06-18 09:18:59
 */
@RestController
@RequestMapping("userbalancerecord/userbalancerecord")
public class UserBalanceRecordController {
    @Autowired
    private UserBalanceRecordService userBalanceRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("userbalancerecord:userbalancerecord:list")
    public R list(UserBalanceRecordEntity userbalancerecord, PageParam pageParam){
        Page<UserBalanceRecordEntity> result = new Page<UserBalanceRecordEntity>(pageParam.getPage(), pageParam.getLimit());
//		Wrapper<UserBalanceRecordEntity> entityWrapper = new EntityWrapper<UserBalanceRecordEntity>(userbalancerecord).orderBy("id", false);
//		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
//		userbalancerecord.selectPage(result, entityWrapper);
        List<UserBalanceRecordEntity> list = userBalanceRecordService.getPageList(userbalancerecord, result);
        result.setRecords(list);
		return R.ok().put("page", new PageUtils(result));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("userbalancerecord:userbalancerecord:info")
    public R info(@PathVariable("id") Long id){
			UserBalanceRecordEntity userBalanceRecord = userBalanceRecordService.selectById(id);
        return R.ok().put("userbalancerecord", userBalanceRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("userbalancerecord:userbalancerecord:save")
    public R save(@RequestBody UserBalanceRecordEntity userbalancerecord){
			userBalanceRecordService.insert(userbalancerecord);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("userbalancerecord:userbalancerecord:update")
    public R update(@RequestBody UserBalanceRecordEntity userbalancerecord){
			userBalanceRecordService.updateById(userbalancerecord);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("userbalancerecord:userbalancerecord:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			userBalanceRecordService.deleteById(id);
	}
        return R.ok();
    }

}
