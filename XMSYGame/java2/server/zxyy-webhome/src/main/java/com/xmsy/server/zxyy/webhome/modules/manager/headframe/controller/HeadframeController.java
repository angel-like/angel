package com.xmsy.server.zxyy.webhome.modules.manager.headframe.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.headframe.entity.HeadframeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.headframe.service.HeadframeService;



/**
 * 头像框表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-06-19 10:17:16
 */
@RestController
@RequestMapping("headframe/headframe")
public class HeadframeController {
    @Autowired
    private HeadframeService headframeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("headframe:headframe:list")
    public R list(HeadframeEntity headframe, PageParam pageParam){
        Page<HeadframeEntity> result = new Page<HeadframeEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<HeadframeEntity> entityWrapper = new EntityWrapper<HeadframeEntity>(headframe);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		headframe.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("headframe:headframe:info")
    public R info(@PathVariable("id") Long id){
			HeadframeEntity headframe = headframeService.selectById(id);
        return R.ok().put("headframe", headframe);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("headframe:headframe:save")
    public R save(@RequestBody HeadframeEntity headframe){
			headframeService.insert(headframe);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("headframe:headframe:update")
    public R update(@RequestBody HeadframeEntity headframe){
			headframeService.updateById(headframe);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("headframe:headframe:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			headframeService.deleteById(id);
	}
        return R.ok();
    }

}
