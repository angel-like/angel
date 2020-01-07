package com.xmsy.server.zxyy.webhome.modules.manager.signuserrecord.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.signuserrecord.entity.SignUserRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.signuserrecord.service.SignUserRecordService;



/**
 * 用户签到记录表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-05-13 15:50:02
 */
@RestController
@RequestMapping("signuserrecord/signuserrecord")
public class SignUserRecordController {
    @Autowired
    private SignUserRecordService signUserRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("signuserrecord:signuserrecord:list")
    public R list(SignUserRecordEntity signuserrecord, PageParam pageParam){
        Page<SignUserRecordEntity> result = new Page<SignUserRecordEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<SignUserRecordEntity> entityWrapper = new EntityWrapper<SignUserRecordEntity>(signuserrecord);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		signuserrecord.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("signuserrecord:signuserrecord:info")
    public R info(@PathVariable("id") Long id){
			SignUserRecordEntity signUserRecord = signUserRecordService.selectById(id);
        return R.ok().put("signuserrecord", signUserRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("signuserrecord:signuserrecord:save")
    public R save(@RequestBody SignUserRecordEntity signuserrecord){
			signUserRecordService.insert(signuserrecord);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("signuserrecord:signuserrecord:update")
    public R update(@RequestBody SignUserRecordEntity signuserrecord){
			signUserRecordService.updateById(signuserrecord);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("signuserrecord:signuserrecord:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			signUserRecordService.deleteById(id);
	}
        return R.ok();
    }

}
