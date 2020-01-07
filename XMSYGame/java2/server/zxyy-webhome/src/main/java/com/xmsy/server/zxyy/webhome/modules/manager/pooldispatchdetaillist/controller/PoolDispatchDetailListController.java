package com.xmsy.server.zxyy.webhome.modules.manager.pooldispatchdetaillist.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.pooldispatchdetaillist.entity.PoolDispatchDetailListEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.pooldispatchdetaillist.service.PoolDispatchDetailListService;
import com.xmsy.server.zxyy.webhome.modules.manager.pooldispatchtask.entity.PoolDispatchTaskEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.pooldispatchtask.service.PoolDispatchTaskService;



/**
 * 派奖明细记录表
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-21 09:46:00
 */
@RestController
@RequestMapping("pooldispatchdetaillist/pooldispatchdetaillist")
public class PoolDispatchDetailListController {
    @Autowired
    private PoolDispatchDetailListService poolDispatchDetailListService;
    @Autowired
    private PoolDispatchTaskService poolDispatchTaskService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("pooldispatchdetaillist:pooldispatchdetaillist:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = poolDispatchDetailListService.queryPage(params);
        return R.ok().put("page", page);
    }
    /**
     * 任务列表
     */
    @RequestMapping("/taskList")
    @RequiresPermissions("pooldispatchdetaillist:pooldispatchdetaillist:info")
    public R taskList(){
		PoolDispatchTaskEntity poolDispatchTask = new PoolDispatchTaskEntity();
		poolDispatchTask.setStatus(1);
        return R.ok().put("taskList", poolDispatchTaskService.
        		selectList(new EntityWrapper<PoolDispatchTaskEntity>(poolDispatchTask)));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("pooldispatchdetaillist:pooldispatchdetaillist:info")
    public R info(@PathVariable("id") Long id){
			PoolDispatchDetailListEntity poolDispatchDetailList = poolDispatchDetailListService.selectById(id);
        return R.ok().put("pooldispatchdetaillist", poolDispatchDetailList);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("pooldispatchdetaillist:pooldispatchdetaillist:save")
    public R save(@RequestBody PoolDispatchDetailListEntity poolDispatchDetailList){
			poolDispatchDetailListService.insert(poolDispatchDetailList);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("pooldispatchdetaillist:pooldispatchdetaillist:update")
    public R update(@RequestBody PoolDispatchDetailListEntity poolDispatchDetailList){
			poolDispatchDetailListService.updateById(poolDispatchDetailList);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("pooldispatchdetaillist:pooldispatchdetaillist:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			poolDispatchDetailListService.deleteById(id);
	}
        return R.ok();
    }

}
