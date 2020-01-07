package com.xmsy.server.zxyy.webhome.modules.manager.signrewardcontinuous.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.signrewardcontinuous.entity.SignRewardContinuousEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.signrewardcontinuous.service.SignRewardContinuousService;



/**
 * 签到连续奖励
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-05-13 15:50:02
 */
@RestController
@RequestMapping("signrewardcontinuous/signrewardcontinuous")
public class SignRewardContinuousController {
    @Autowired
    private SignRewardContinuousService signRewardContinuousService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("signrewardcontinuous:signrewardcontinuous:list")
    public R list(SignRewardContinuousEntity signrewardcontinuous, PageParam pageParam){
        Page<SignRewardContinuousEntity> result = new Page<SignRewardContinuousEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<SignRewardContinuousEntity> entityWrapper = new EntityWrapper<SignRewardContinuousEntity>(signrewardcontinuous);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		signrewardcontinuous.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("signrewardcontinuous:signrewardcontinuous:info")
    public R info(@PathVariable("id") Long id){
			SignRewardContinuousEntity signRewardContinuous = signRewardContinuousService.selectById(id);
        return R.ok().put("signrewardcontinuous", signRewardContinuous);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("signrewardcontinuous:signrewardcontinuous:save")
    public R save(@RequestBody SignRewardContinuousEntity signrewardcontinuous){
			signRewardContinuousService.insert(signrewardcontinuous);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("signrewardcontinuous:signrewardcontinuous:update")
    public R update(@RequestBody SignRewardContinuousEntity signrewardcontinuous){
			signRewardContinuousService.updateById(signrewardcontinuous);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("signrewardcontinuous:signrewardcontinuous:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			signRewardContinuousService.deleteById(id);
	}
        return R.ok();
    }

}
