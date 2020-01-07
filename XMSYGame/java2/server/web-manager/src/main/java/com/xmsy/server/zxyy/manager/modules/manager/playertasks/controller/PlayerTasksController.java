package com.xmsy.server.zxyy.manager.modules.manager.playertasks.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.manager.modules.manager.playertasks.entity.PlayerTasksEntity;
import com.xmsy.server.zxyy.manager.modules.manager.playertasks.service.PlayerTasksService;
import com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.entity.SysDictionaryEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.service.SysDictionaryService;
import com.xmsy.server.zxyy.manager.modules.manager.sysprop.entity.SysPropEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysprop.service.SysPropService;



/**
 * 玩家任务表
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-17 14:53:56
 */
@RestController
@RequestMapping("playertasks/playertasks")
public class PlayerTasksController {
    @Autowired
    private PlayerTasksService playerTasksService;
    
    @Autowired
    private SysDictionaryService sysDictionaryService;
    @Autowired
    private GameInfoComponent gameInfo;
    
    @Autowired
    private SysPropService sysPropService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("playertasks:playertasks:list")
    public R list(PlayerTasksEntity playertasks, PageParam pageParam){
        Page<PlayerTasksEntity> result = new Page<PlayerTasksEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<PlayerTasksEntity> entityWrapper = new EntityWrapper<PlayerTasksEntity>(playertasks);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		entityWrapper.orderBy("sorts", true);
		playertasks.selectPage(result, entityWrapper);
		List<PlayerTasksEntity> list=result.getRecords();
		if(!CollectionUtils.isEmpty(list)) {
//			 Map<Long, String> gameMap = localContentCache.getGameMap();//SysConstant.gameMap
//			 Map<Long, String> roomMap = localContentCache.getRoomMap();//SysConstant.roomMap
			 Map<Long, String> gameMap = gameInfo.getGameMap();//SysConstant.gameMap
			 Map<Long, String> roomMap = gameInfo.getRoomMap();//SysConstant.roomMap
			 Map<Long, String> gradeMap = gameInfo.getGradeMap();
			for(PlayerTasksEntity entity:list) {
				if(entity!=null) {
					if(entity.getType()!=null&&entity.getType()!=0) {
						SysDictionaryEntity dictionaryEntity=sysDictionaryService.selectOne(new EntityWrapper<SysDictionaryEntity>(null).eq("parent_code", "TaskType").eq("code", entity.getType().toString()));
						if(dictionaryEntity!=null) {
							if(dictionaryEntity.getName()!=null) {
								entity.setTaskTypeName(dictionaryEntity.getName());
							}
						}
					}
					if(entity.getConfrontationRequirement()!=null&&entity.getConfrontationRequirement()!=0) {
						SysDictionaryEntity dictionaryEntity=sysDictionaryService.selectOne(new EntityWrapper<SysDictionaryEntity>(null).eq("parent_code", "ConfrontationType").eq("code", entity.getConfrontationRequirement().toString()));
						if(dictionaryEntity!=null) {
							if(dictionaryEntity.getName()!=null) {
								entity.setConfrontationTypeName(dictionaryEntity.getName());
							}
						}
					}
					if(entity.getPropId()!=null&&entity.getPropId()!=0) {
						SysPropEntity sysProp = sysPropService.selectById(entity.getPropId());
						if(sysProp!=null&&!StringUtils.isEmpty(sysProp.getName())) {
							entity.setPropName(sysProp.getName());
						}
					}
					if(entity.getRoomId()!=null&&entity.getRoomId()!=0) {
						entity.setRoomName(roomMap.get(entity.getRoomId()));

					}else if(entity.getRoomId()==0) {
						entity.setRoomName("-");
					}
					if(entity.getGameId()!=null&&entity.getGameId()!=0) {
						entity.setGameName(gameMap.get(entity.getGameId()));

					}else if(entity.getGameId()==0) {
						entity.setGameName("-");
					}
					if(entity.getGradeId()!=null&&entity.getGradeId()!=0) {
						entity.setGradeName(gradeMap.get(entity.getGradeId()));

					}else if(entity.getGradeId()==0) {
						entity.setGradeName("-");
					}
				}
				//跳转类型通过数据字段查出
				if(entity.getType()==3&&entity.getFunctionalType()!=null) {
					entity.setFunctionalTypeName(sysDictionaryService.getName("transferFunction", entity.getFunctionalType()));
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
    @RequiresPermissions("playertasks:playertasks:info")
    public R info(@PathVariable("id") Long id){
			PlayerTasksEntity playerTasks = playerTasksService.selectById(id);
        return R.ok().put("playertasks", playerTasks);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("playertasks:playertasks:save")
    public R save(@RequestBody PlayerTasksEntity playertasks){
			playerTasksService.insert(playertasks);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("playertasks:playertasks:update")
    public R update(@RequestBody PlayerTasksEntity playertasks){
			playerTasksService.updateById(playertasks);
        return R.ok();
    }
    /**
     * 修改任务  开启  或者   关闭的状态
     */
    @RequestMapping("/taskEnable")
    @RequiresPermissions("playertasks:playertasks:update")
    public R taskEnable(@RequestBody PlayerTasksEntity playertasks){
		playerTasksService.updateById(playertasks);
        return R.ok();
    }
    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("playertasks:playertasks:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			playerTasksService.deleteById(id);
	}
        return R.ok();
    }

}
