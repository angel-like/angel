package com.xmsy.server.zxyy.webhome.modules.manager.roomcardproxy.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.roomcardproxy.entity.RoomCardProxyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.roomcardproxy.service.RoomCardProxyService;



/**
 * 房卡代理说明
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-07-02 10:58:28
 */
@RestController
@RequestMapping("roomcardproxy/roomcardproxy")
public class RoomCardProxyController {
    @Autowired
    private RoomCardProxyService roomCardProxyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("roomcardproxy:roomcardproxy:list")
    public R list(RoomCardProxyEntity roomcardproxy, PageParam pageParam){
        Page<RoomCardProxyEntity> result = new Page<RoomCardProxyEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<RoomCardProxyEntity> entityWrapper = new EntityWrapper<RoomCardProxyEntity>(roomcardproxy);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		roomcardproxy.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("roomcardproxy:roomcardproxy:info")
    public R info(@PathVariable("id") Long id){
			RoomCardProxyEntity roomCardProxy = roomCardProxyService.selectById(id);
        return R.ok().put("roomcardproxy", roomCardProxy);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("roomcardproxy:roomcardproxy:save")
    public R save(@RequestBody RoomCardProxyEntity roomcardproxy){
			roomCardProxyService.insert(roomcardproxy);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("roomcardproxy:roomcardproxy:update")
    public R update(@RequestBody RoomCardProxyEntity roomcardproxy){
			roomCardProxyService.updateById(roomcardproxy);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("roomcardproxy:roomcardproxy:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			roomCardProxyService.deleteById(id);
	}
        return R.ok();
    }

}
