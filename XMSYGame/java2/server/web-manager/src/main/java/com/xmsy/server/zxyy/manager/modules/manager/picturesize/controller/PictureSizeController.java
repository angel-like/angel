package com.xmsy.server.zxyy.manager.modules.manager.picturesize.controller;


import com.xmsy.common.define.page.PageParam;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import com.xmsy.server.zxyy.manager.modules.manager.picturesize.entity.PictureSizeEntity;
import com.xmsy.server.zxyy.manager.modules.manager.picturesize.service.PictureSizeService;
import com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.entity.SysDictionaryEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.service.SysDictionaryService;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;



/**
 * 图片尺寸配置
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-25 11:03:55
 */
@RestController
@RequestMapping("picturesize/picturesize")
public class PictureSizeController {
    @Autowired
    private PictureSizeService pictureSizeService;
    @Autowired
    private SysDictionaryService sysDictionaryService;

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
		List<PictureSizeEntity> list=result.getRecords();
		if(!CollectionUtils.isEmpty(list)) {
			for(PictureSizeEntity entity:list) {
				if(entity!=null) {
					if(entity.getMenuId()!=null&&entity.getMenuId()!=0) {
						SysDictionaryEntity dictionaryEntity=sysDictionaryService.selectOne(new EntityWrapper<SysDictionaryEntity>(null).eq("parent_code", "PictureSizeMenu").eq("code", entity.getMenuId().toString()));
						if(dictionaryEntity!=null) {
							if(dictionaryEntity.getName()!=null) {
								entity.setMenuName(dictionaryEntity.getName());
							}
						}
					}
				}
			}
		}
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
    
    /**
	 * 通过上级typeId,menuId获取下拉
	 */
	@RequestMapping("/select/{typeId}/{menuId}")
	public R delete(@PathVariable("typeId") Integer typeId,@PathVariable("menuId") Integer menuId) {
		String pictureSize = pictureSizeService.selectPictureSize(typeId,menuId);
		return R.ok().put("pictureSize", pictureSize);
	}

}
