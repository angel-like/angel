package com.xmsy.server.zxyy.webhome.modules.manager.pooldispatchtaskdetail.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.webhome.common.annotation.SysLog;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.pooldispatchtask.entity.PoolDispatchTaskEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.pooldispatchtask.service.PoolDispatchTaskService;
import com.xmsy.server.zxyy.webhome.modules.manager.pooldispatchtaskdetail.entity.PoolDispatchTaskDetailEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.pooldispatchtaskdetail.service.PoolDispatchTaskDetailService;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.entity.UserHierarchyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.service.UserHierarchyService;



/**
 * 派奖奖项明细表
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-21 09:46:00
 */
@RestController
@RequestMapping("pooldispatchtaskdetail/pooldispatchtaskdetail")
public class PoolDispatchTaskDetailController {
    @Autowired
    private PoolDispatchTaskDetailService poolDispatchTaskDetailService;
    @Autowired
    private PoolDispatchTaskService poolDispatchTaskService;
    @Autowired
    private UserHierarchyService userHierarchyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("pooldispatchtaskdetail:pooldispatchtaskdetail:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = poolDispatchTaskDetailService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("pooldispatchtaskdetail:pooldispatchtaskdetail:info")
    public R info(@PathVariable("id") Long id){
			PoolDispatchTaskDetailEntity poolDispatchTaskDetail = poolDispatchTaskDetailService.selectById(id);
        return R.ok().put("pooldispatchtaskdetail", poolDispatchTaskDetail);
    }
    
    /**
     * 任务列表
     */
    @RequestMapping("/taskList/{status}")
    @RequiresPermissions("pooldispatchtaskdetail:pooldispatchtaskdetail:info")
    public R taskList(@PathVariable("status") Integer status){
		PoolDispatchTaskEntity poolDispatchTask = new PoolDispatchTaskEntity();
		if(status<=2) {
			poolDispatchTask.setStatus(status);
		}
		
        return R.ok().put("taskList", poolDispatchTaskService.
        		selectList(new EntityWrapper<PoolDispatchTaskEntity>(poolDispatchTask)));
    }
    
    /**
     * 信息
     */
    @RequestMapping("/getHierarchy")
    @RequiresPermissions("messagemanagement:messagemanagement:info")
    public R getHierarchy(){
		List<UserHierarchyEntity> dataList = userHierarchyService.selectList(new EntityWrapper<UserHierarchyEntity>(new UserHierarchyEntity()));
		List<Long> ids=new ArrayList<>();
		if(dataList!=null && !dataList.isEmpty()) {
			for(UserHierarchyEntity data:dataList) {
				ids.add(data.getId());
			}
		}
        return R.ok().put("hierarchyList", dataList).put("ids", ids);
    }

    /**
     * 保存
     */
    @SysLog("新增派奖奖项明细")
    @RequestMapping("/save")
    @RequiresPermissions("pooldispatchtaskdetail:pooldispatchtaskdetail:save")
    public R save(@RequestBody PoolDispatchTaskDetailEntity poolDispatchTaskDetail){
			poolDispatchTaskDetailService.insert(poolDispatchTaskDetail);
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改派奖奖项明细")
    @RequestMapping("/update")
    @RequiresPermissions("pooldispatchtaskdetail:pooldispatchtaskdetail:update")
    public R update(@RequestBody PoolDispatchTaskDetailEntity poolDispatchTaskDetail){
			poolDispatchTaskDetailService.updateEntityByIdForTrim(poolDispatchTaskDetail);
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除派奖奖项明细")
    @RequestMapping("/delete")
    @RequiresPermissions("pooldispatchtaskdetail:pooldispatchtaskdetail:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			poolDispatchTaskDetailService.deleteById(id);
	}
        return R.ok();
    }

}
