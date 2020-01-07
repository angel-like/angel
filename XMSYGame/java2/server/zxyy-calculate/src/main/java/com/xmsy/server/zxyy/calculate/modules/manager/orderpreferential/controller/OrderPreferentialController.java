package com.xmsy.server.zxyy.calculate.modules.manager.orderpreferential.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.calculate.common.utils.PageUtils;
import com.xmsy.server.zxyy.calculate.common.utils.R;
import com.xmsy.server.zxyy.calculate.modules.manager.orderpreferential.entity.OrderPreferentialEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.orderpreferential.service.OrderPreferentialService;
import com.xmsy.server.zxyy.calculate.modules.manager.userhierarchy.entity.UserHierarchyEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.userhierarchy.service.UserHierarchyService;

import lombok.extern.slf4j.Slf4j;



/**
 * 充值优惠
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-01-25 18:46:23
 */
@Slf4j
@RestController
@RequestMapping("orderpreferential/orderpreferential")
public class OrderPreferentialController {
    @Autowired
    private OrderPreferentialService orderPreferentialService;
    @Autowired
    private UserHierarchyService userHierarchyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("orderpreferential:orderpreferential:list")
    public R list(OrderPreferentialEntity entity, PageParam pageParam){
    	log.info("[list] pageParam {},entity {}", pageParam,entity);
    	Page<OrderPreferentialEntity> result = new Page<OrderPreferentialEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<OrderPreferentialEntity> entityWrapper = new EntityWrapper<OrderPreferentialEntity>(entity);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		entity.selectPage(result, entityWrapper);
		List<OrderPreferentialEntity> list=result.getRecords();
		if(!CollectionUtils.isEmpty(list)) {
			for(OrderPreferentialEntity orderPreferentialEntity:list) {
				if(orderPreferentialEntity.getHierarchyId()!=0) {
					UserHierarchyEntity	userHierarchy=userHierarchyService.selectById(orderPreferentialEntity.getHierarchyId());
					if(userHierarchy!=null&&!StringUtils.isEmpty(userHierarchy.getName())) {
						orderPreferentialEntity.setHierarchyName(userHierarchy.getName());
					}
					
				}
			}
		}
		log.info("[list] list {}", list);
        return R.ok().put("page", new PageUtils(list, result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("orderpreferential:orderpreferential:info")
    public R info(@PathVariable("id") Long id){
    	log.info("[info] id {}", id);
			OrderPreferentialEntity orderPreferential = orderPreferentialService.selectById(id);
		log.info("[info] orderPreferential {}", orderPreferential);
        return R.ok().put("orderpreferential", orderPreferential);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("orderpreferential:orderpreferential:save")
    public R save(@RequestBody OrderPreferentialEntity orderPreferential){
			orderPreferentialService.insert(orderPreferential);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("orderpreferential:orderpreferential:update")
    public R update(@RequestBody OrderPreferentialEntity orderPreferential){
    	orderPreferential.setHierarchyId(null);
    	orderPreferential.setFirstRecharge(null);
			orderPreferentialService.updateById(orderPreferential);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("orderpreferential:orderpreferential:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			orderPreferentialService.deleteById(id);
	}
        return R.ok();
    }
    
    

}
