package com.xmsy.server.zxyy.webhome.modules.manager.sharerecord.controller;

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

import com.xmsy.server.zxyy.webhome.modules.manager.sharerecord.entity.ShareRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sharerecord.service.ShareRecordService;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;



/**
 * 分享记录
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-22 12:14:28
 */
@RestController
@RequestMapping("sharerecord/sharerecord")
public class ShareRecordController {
    @Autowired
    private ShareRecordService shareRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sharerecord:sharerecord:list")
    public R list(ShareRecordEntity sharerecord, PageParam pageParam){
        Page<ShareRecordEntity> result = new Page<ShareRecordEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<ShareRecordEntity> entityWrapper = new EntityWrapper<ShareRecordEntity>(sharerecord);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		sharerecord.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sharerecord:sharerecord:info")
    public R info(@PathVariable("id") Long id){
			ShareRecordEntity shareRecord = shareRecordService.selectById(id);
        return R.ok().put("sharerecord", shareRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sharerecord:sharerecord:save")
    public R save(@RequestBody ShareRecordEntity sharerecord){
			shareRecordService.insert(sharerecord);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sharerecord:sharerecord:update")
    public R update(@RequestBody ShareRecordEntity sharerecord){
			shareRecordService.updateById(sharerecord);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sharerecord:sharerecord:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			shareRecordService.deleteById(id);
	}
        return R.ok();
    }

}
