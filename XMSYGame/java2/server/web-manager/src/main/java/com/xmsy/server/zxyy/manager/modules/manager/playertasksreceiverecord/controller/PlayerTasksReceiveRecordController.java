package com.xmsy.server.zxyy.manager.modules.manager.playertasksreceiverecord.controller;

import com.xmsy.common.define.page.PageParam;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.server.zxyy.manager.modules.manager.playertasks.entity.PlayerTasksEntity;
import com.xmsy.server.zxyy.manager.modules.manager.playertasks.service.PlayerTasksService;
import com.xmsy.server.zxyy.manager.modules.manager.playertasksreceiverecord.entity.PlayerTasksReceiveRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.playertasksreceiverecord.service.PlayerTasksReceiveRecordService;

import cn.hutool.core.collection.CollectionUtil;

import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;



/**
 * 玩家任务领取记录表
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-19 09:57:30
 */
@RestController
@RequestMapping("playertasksreceiverecord/playertasksreceiverecord")
public class PlayerTasksReceiveRecordController {
    @Autowired
    private PlayerTasksReceiveRecordService playerTasksReceiveRecordService;
    
    @Autowired
    private PlayerTasksService playerTasksService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("playertasksreceiverecord:playertasksreceiverecord:list")
    public R list(PlayerTasksReceiveRecordEntity playertasksreceiverecord, PageParam pageParam){
        Page<PlayerTasksReceiveRecordEntity> result = new Page<PlayerTasksReceiveRecordEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<PlayerTasksReceiveRecordEntity> entityWrapper = new EntityWrapper<PlayerTasksReceiveRecordEntity>(playertasksreceiverecord);
		entityWrapper.orderBy("create_time", false);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		playertasksreceiverecord.selectPage(result, entityWrapper);
		List<PlayerTasksReceiveRecordEntity> list = result.getRecords();
		if(!CollectionUtil.isEmpty(list)) {
			for(PlayerTasksReceiveRecordEntity entity: list) {
				if(entity==null) {
					continue;
				}
				if(entity.getTaskId()!=null&&entity.getTaskId()!=0) {
					PlayerTasksEntity tasksEntity = playerTasksService.selectById(entity.getTaskId());
					if(tasksEntity!=null&&!StringUtils.isEmpty(tasksEntity.getTitle())) {
						entity.setTaskName(tasksEntity.getTitle());
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
    @RequiresPermissions("playertasksreceiverecord:playertasksreceiverecord:info")
    public R info(@PathVariable("id") Long id){
			PlayerTasksReceiveRecordEntity playerTasksReceiveRecord = playerTasksReceiveRecordService.selectById(id);
        return R.ok().put("playertasksreceiverecord", playerTasksReceiveRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("playertasksreceiverecord:playertasksreceiverecord:save")
    public R save(@RequestBody PlayerTasksReceiveRecordEntity playertasksreceiverecord){
			playerTasksReceiveRecordService.insert(playertasksreceiverecord);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("playertasksreceiverecord:playertasksreceiverecord:update")
    public R update(@RequestBody PlayerTasksReceiveRecordEntity playertasksreceiverecord){
			playerTasksReceiveRecordService.updateById(playertasksreceiverecord);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("playertasksreceiverecord:playertasksreceiverecord:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			playerTasksReceiveRecordService.deleteById(id);
	}
        return R.ok();
    }

}
