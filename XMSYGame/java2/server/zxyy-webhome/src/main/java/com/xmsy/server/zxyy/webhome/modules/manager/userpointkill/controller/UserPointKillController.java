package com.xmsy.server.zxyy.webhome.modules.manager.userpointkill.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.userpointkill.entity.UserPointKillEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userpointkill.service.UserPointKillService;



/**
 * 点杀名单
 *
 * @author aye
 * @email xxxxx
 * @date 2019-11-22 11:27:03
 */
@RestController
@RequestMapping("userpointkill/userpointkill")
public class UserPointKillController {
    @Autowired
    private UserPointKillService userPointKillService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("userpointkill:userpointkill:list")
    public R list(UserPointKillEntity userpointkill, PageParam pageParam){
        Page<UserPointKillEntity> result = new Page<UserPointKillEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<UserPointKillEntity> entityWrapper = new EntityWrapper<UserPointKillEntity>(userpointkill);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		userpointkill.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("userpointkill:userpointkill:info")
    public R info(@PathVariable("id") Integer id){
			UserPointKillEntity userPointKill = userPointKillService.selectById(id);
        return R.ok().put("userpointkill", userPointKill);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("userpointkill:userpointkill:save")
    public R save(@RequestBody UserPointKillEntity userpointkill){
			userPointKillService.insert(userpointkill);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("userpointkill:userpointkill:update")
    public R update(@RequestBody UserPointKillEntity userpointkill){
			userPointKillService.updateById(userpointkill);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("userpointkill:userpointkill:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			userPointKillService.deleteById(id);
	}
        return R.ok();
    }

}
