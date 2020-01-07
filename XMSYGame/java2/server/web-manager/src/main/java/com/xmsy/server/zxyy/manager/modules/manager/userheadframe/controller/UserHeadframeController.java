package com.xmsy.server.zxyy.manager.modules.manager.userheadframe.controller;

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
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.userheadframe.entity.UserHeadframeEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userheadframe.service.UserHeadframeService;



/**
 * 用户头像框关系表
 *
 * @author adu
 * @email xxxxx
 * @date 2019-06-18 17:20:02
 */
@RestController
@RequestMapping("userheadframe/userheadframe")
public class UserHeadframeController {
    @Autowired
    private UserHeadframeService userHeadframeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("userheadframe:userheadframe:list")
    public R list(UserHeadframeEntity userheadframe, PageParam pageParam){
        Page<UserHeadframeEntity> result = new Page<UserHeadframeEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<UserHeadframeEntity> entityWrapper = new EntityWrapper<UserHeadframeEntity>(userheadframe);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		userHeadframeService.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("userheadframe:userheadframe:info")
    public R info(@PathVariable("id") Long id){
			UserHeadframeEntity userHeadframe = userHeadframeService.selectById(id);
        return R.ok().put("userheadframe", userHeadframe);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("userheadframe:userheadframe:save")
    public R save(@RequestBody UserHeadframeEntity userheadframe){
			userHeadframeService.insert(userheadframe);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("userheadframe:userheadframe:update")
    public R update(@RequestBody UserHeadframeEntity userheadframe){
			userHeadframeService.updateById(userheadframe);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("userheadframe:userheadframe:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			userHeadframeService.deleteById(id);
	}
        return R.ok();
    }

}
