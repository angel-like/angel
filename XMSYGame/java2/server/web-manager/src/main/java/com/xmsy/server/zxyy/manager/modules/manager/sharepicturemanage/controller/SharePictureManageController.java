package com.xmsy.server.zxyy.manager.modules.manager.sharepicturemanage.controller;

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
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.sharepicturemanage.entity.SharePictureManageEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sharepicturemanage.service.SharePictureManageService;
import com.xmsy.server.zxyy.manager.modules.manager.webhomeenclosure.entity.WebhomeEnclosureEntity;
import com.xmsy.server.zxyy.manager.modules.manager.webhomeenclosure.service.WebhomeEnclosureService;


/**
 * 分享图片管理
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-07-08 15:13:55
 */
@RestController
@RequestMapping("sharepicturemanage/sharepicturemanage")
public class SharePictureManageController {
    @Autowired
    private SharePictureManageService sharePictureManageService;
    @Autowired
    private WebhomeEnclosureService webhomeEnclosureService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sharepicturemanage:sharepicturemanage:list")
    public R list(SharePictureManageEntity sharepicturemanage, PageParam pageParam){
        Page<SharePictureManageEntity> result = new Page<SharePictureManageEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<SharePictureManageEntity> entityWrapper = new EntityWrapper<SharePictureManageEntity>(new SharePictureManageEntity());
		entityWrapper.like("share_title", sharepicturemanage.getShareTitle());
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		sharepicturemanage.selectPage(result, entityWrapper);
        // 获取图片url
        List<SharePictureManageEntity> list =  result.getRecords();
        if(!CollectionUtils.isEmpty(list)){
            for (SharePictureManageEntity entity : list) {
                if(entity!=null) {
                    if(entity.getEnclosureId()!=null){
                        WebhomeEnclosureEntity  WebhomeEntity = webhomeEnclosureService.selectById(entity.getEnclosureId());
//                        String url = WebhomeEntity.getUrl();
                        entity.setPictureUrl(WebhomeEntity.getUrl());
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
    @RequiresPermissions("sharepicturemanage:sharepicturemanage:info")
    public R info(@PathVariable("id") Long id){
			SharePictureManageEntity sharePictureManage = sharePictureManageService.selectById(id);
        return R.ok().put("sharepicturemanage", sharePictureManage);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sharepicturemanage:sharepicturemanage:save")
    public R save(@RequestBody SharePictureManageEntity sharepicturemanage){
			sharePictureManageService.insert(sharepicturemanage);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sharepicturemanage:sharepicturemanage:update")
    public R update(@RequestBody SharePictureManageEntity sharepicturemanage){
			sharePictureManageService.updateById(sharepicturemanage);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sharepicturemanage:sharepicturemanage:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			sharePictureManageService.deleteById(id);
	}
        return R.ok();
    }

}
