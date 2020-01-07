package com.xmsy.server.zxyy.manager.modules.manager.updatelog.controller;

import java.util.Date;

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
import com.xmsy.server.zxyy.manager.modules.manager.updatelog.entity.UpdateLogEntity;
import com.xmsy.server.zxyy.manager.modules.manager.updatelog.service.UpdateLogService;



/**
 * 更新日志
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-07-25 16:47:37
 */
@RestController
@RequestMapping("updatelog/updatelog")
public class UpdateLogController {
    @Autowired
    private UpdateLogService updateLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("updatelog:updatelog:list")
    public R list(UpdateLogEntity updatelog, PageParam pageParam){
        Page<UpdateLogEntity> result = new Page<UpdateLogEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<UpdateLogEntity> entityWrapper = new EntityWrapper<UpdateLogEntity>(updatelog).orderBy("id", false);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		if(updatelog.getStartTime()!=null) {
			entityWrapper.gt("finish_time", updatelog.getStartTime());
		}
		if(updatelog.getEndTime()!=null) {
			entityWrapper.le("finish_time", updatelog.getEndTime());
		}
		updatelog.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("updatelog:updatelog:info")
    public R info(@PathVariable("id") Long id){
			UpdateLogEntity updateLog = updateLogService.selectById(id);
        return R.ok().put("updatelog", updateLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("updatelog:updatelog:save")
    public R save(@RequestBody UpdateLogEntity updatelog){
			updateLogService.insert(updatelog);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("updatelog:updatelog:update")
    public R update(@RequestBody UpdateLogEntity updatelog){
    	if(updatelog.getUpdateStatus()) {
    		updatelog.setFinishTime(new Date());
    	}
		updateLogService.updateById(updatelog);
        return R.ok();
    }
    /**
     * 更新完成状态以及完成时间
     */
    @RequestMapping("/updateStatus")
    @RequiresPermissions("updatelog:updatelog:update")
    public R updateStatus(@RequestBody Long id){
    	UpdateLogEntity updatelog = new UpdateLogEntity();
    	updatelog.setId(id);
    	updatelog.setUpdateStatus(true);
    	updatelog.setFinishTime(new Date());
		updateLogService.updateById(updatelog);
        return R.ok();
    }
    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("updatelog:updatelog:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			updateLogService.deleteById(id);
	}
        return R.ok();
    }

}
