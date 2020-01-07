package com.xmsy.server.zxyy.webhome.modules.manager.picturesize.controller;


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

import com.xmsy.server.zxyy.webhome.modules.manager.picturesize.entity.PictureSizeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.picturesize.service.PictureSizeService;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;



/**
 * 图片尺寸配置
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-25 11:09:51
 */
@RestController
@RequestMapping("picturesize/picturesize")
public class PictureSizeController {
    @Autowired
    private PictureSizeService pictureSizeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("picturesize:picturesize:list")
    public R list(PictureSizeEntity picturesize, PageParam pageParam){
        Page<PictureSizeEntity> result = new Page<PictureSizeEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<PictureSizeEntity> entityWrapper = new EntityWrapper<PictureSizeEntity>(picturesize);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		picturesize.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("picturesize:picturesize:info")
    public R info(@PathVariable("id") Long id){
			PictureSizeEntity pictureSize = pictureSizeService.selectById(id);
        return R.ok().put("picturesize", pictureSize);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("picturesize:picturesize:save")
    public R save(@RequestBody PictureSizeEntity picturesize){
			pictureSizeService.insert(picturesize);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("picturesize:picturesize:update")
    public R update(@RequestBody PictureSizeEntity picturesize){
			pictureSizeService.updateById(picturesize);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("picturesize:picturesize:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			pictureSizeService.deleteById(id);
	}
        return R.ok();
    }

}
