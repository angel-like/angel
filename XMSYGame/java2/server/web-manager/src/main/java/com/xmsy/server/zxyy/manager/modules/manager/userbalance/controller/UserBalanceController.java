package com.xmsy.server.zxyy.manager.modules.manager.userbalance.controller;

import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParamTwo;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.modules.manager.userbalanceproduct.entity.UserBalanceProductEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userbalanceproduct.service.UserBalanceProductService;
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
import com.xmsy.server.zxyy.manager.modules.manager.userbalance.entity.UserBalanceEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userbalance.service.UserBalanceService;

import java.util.List;


/**
 * 用户余额宝金额表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-06-18 09:18:59
 */
@RestController
@RequestMapping("userbalance/userbalance")
public class UserBalanceController {
    @Autowired
    private UserBalanceService userBalanceService;
    @Autowired
    private UserBalanceProductService userBalanceProductService;
    @Autowired
    private UserService userService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("userbalance:userbalance:list")
    public R list(UserBalanceEntity userbalance, PageParam pageParam){
        Page<UserBalanceEntity> result = new Page<UserBalanceEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<UserBalanceEntity> entityWrapper = new EntityWrapper<UserBalanceEntity>(userbalance);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());

        if(userbalance.getStartTime()!=null) {
            entityWrapper.gt("count_day", userbalance.getStartTime());
            if(userbalance.getEndTime()!=null) {
                entityWrapper.le("count_day", userbalance.getEndTime());
            }
        }
		userbalance.selectPage(result, entityWrapper);
        // 增加方案名称和利率
        List<UserBalanceEntity> list = result.getRecords();
        if (!CollectionUtils.isEmpty(list)){
            for (UserBalanceEntity entity : list) {
                if (entity!=null){
                    // 根据理财产品ID查询利率和方案名称
                    UserBalanceProductEntity userBalanceProductEntity = userBalanceProductService.selectById(entity.getUserBalanceProductId());

                    UserParamTwo userParamTwo = userService.queryByAccount(entity.getUserAccount()); // 根据用户count查询真实名字
                    if (userParamTwo!=null && userParamTwo.getUserName()!=null){
                        entity.setUserName(userParamTwo.getUserName());
                    }

                    entity.setProductName(userBalanceProductEntity.getProductName());  // 方案名称
                    entity.setRate(userBalanceProductEntity.getRate());  // 利率
                }
            }
        }
        Long allMoney = userBalanceService.AllMoney(userbalance);
        Long allPrize = userBalanceService.AllPrize(userbalance);
        Long allProfitYesterday = userBalanceService.AllProfitYesterday(userbalance);
        return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()))
                .put("AllMoney",allMoney)
                .put("AllPrize",allPrize)
                .put("AllProfitYesterday",allProfitYesterday);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("userbalance:userbalance:info")
    public R info(@PathVariable("id") Long id){
			UserBalanceEntity userBalance = userBalanceService.selectById(id);
        return R.ok().put("userbalance", userBalance);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("userbalance:userbalance:save")
    public R save(@RequestBody UserBalanceEntity userbalance){
			userBalanceService.insert(userbalance);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("userbalance:userbalance:update")
    public R update(@RequestBody UserBalanceEntity userbalance){
			userBalanceService.updateById(userbalance);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("userbalance:userbalance:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			userBalanceService.deleteById(id);
	}
        return R.ok();
    }

}
