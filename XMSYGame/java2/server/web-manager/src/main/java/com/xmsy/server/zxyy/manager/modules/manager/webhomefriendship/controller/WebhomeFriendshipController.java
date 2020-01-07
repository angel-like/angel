package com.xmsy.server.zxyy.manager.modules.manager.webhomefriendship.controller;

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
import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.webhomefriendship.entity.WebhomeFriendshipEntity;
import com.xmsy.server.zxyy.manager.modules.manager.webhomefriendship.service.WebhomeFriendshipService;



/**
 * 友情链接管理
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-07 11:02:39
 */
@RestController
@RequestMapping("webhomefriendship/webhomefriendship")
public class WebhomeFriendshipController {
    @Autowired
    private WebhomeFriendshipService webhomeFriendshipService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("webhomefriendship:webhomefriendship:list")
    public R list(WebhomeFriendshipEntity WebhomeFriendship, PageParam pageParam) {
    	String name=WebhomeFriendship.getName();
    	WebhomeFriendship.setName(null);
		Page<WebhomeFriendshipEntity> result = new Page<WebhomeFriendshipEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<WebhomeFriendshipEntity> entityWrapper = new EntityWrapper<WebhomeFriendshipEntity>(WebhomeFriendship).like("name", name).orderBy("order_num");
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		WebhomeFriendship.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("webhomefriendship:webhomefriendship:info")
    public R info(@PathVariable("id") Long id){
			WebhomeFriendshipEntity webhomeFriendship = webhomeFriendshipService.selectById(id);
        return R.ok().put("webhomefriendship", webhomeFriendship);
    }

    /**
     * 保存
     */
    @SysLog("友情链接新增")
    @RequestMapping("/save")
    @RequiresPermissions("webhomefriendship:webhomefriendship:save")
    public R save(@RequestBody WebhomeFriendshipEntity webhomeFriendship){
			webhomeFriendshipService.insert(webhomeFriendship);
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("友情链接修改")
    @RequestMapping("/update")
    @RequiresPermissions("webhomefriendship:webhomefriendship:update")
    public R update(@RequestBody WebhomeFriendshipEntity webhomeFriendship){
			webhomeFriendshipService.updateById(webhomeFriendship);
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("友情链接删除")
    @RequestMapping("/delete")
    @RequiresPermissions("webhomefriendship:webhomefriendship:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			webhomeFriendshipService.deleteById(id);
	}
        return R.ok();
    }

}
