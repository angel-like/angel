package com.xmsy.server.zxyy.manager.modules.manager.ordercashrecord.controller;


import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.modules.manager.ordercashexamine.entity.OrderCashExamineEntity;
import com.xmsy.server.zxyy.manager.modules.manager.ordercashexamine.service.OrderCashExamineService;
import com.xmsy.server.zxyy.manager.modules.manager.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import com.xmsy.server.zxyy.manager.modules.manager.ordercashrecord.entity.OrderCashRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.ordercashrecord.service.OrderCashRecordService;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;

import java.math.BigDecimal;


/**
 * 用户打码量记录表
 *
 * @author ayang
 * @email xxxxx
 * @date 2019-12-25 16:30:46
 */
@RestController
@RequestMapping("ordercashrecord/ordercashrecord")
public class OrderCashRecordController extends AbstractController {
    @Autowired
    private OrderCashRecordService orderCashRecordService;
    @Autowired
    private OrderCashExamineService  orderCashExamineService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ordercashrecord:ordercashrecord:list")
    public R list(OrderCashRecordEntity ordercashrecord, PageParam pageParam){
        Page<OrderCashRecordEntity> result = new Page<OrderCashRecordEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<OrderCashRecordEntity> entityWrapper = new EntityWrapper<OrderCashRecordEntity>(ordercashrecord);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		ordercashrecord.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ordercashrecord:ordercashrecord:info")
    public R info(@PathVariable("id") Long id){
			OrderCashRecordEntity orderCashRecord = orderCashRecordService.selectById(id);
        return R.ok().put("ordercashrecord", orderCashRecord);
    }

    /**
     * 保存
     */

    @RequestMapping("/save")
    @RequiresPermissions("ordercashrecord:ordercashrecord:save")
    public R save(@RequestBody OrderCashRecordEntity ordercashrecord){
        OrderCashExamineEntity orderCashExamineEntity = orderCashExamineService.selectOne(new EntityWrapper<>(new OrderCashExamineEntity().setUserId(ordercashrecord.getUserId()).setStatus(false)));
        if(ordercashrecord.getType()==0){
            orderCashExamineService.updateValidCashBet(ordercashrecord.getUserId());
        }
        if(ordercashrecord.getType() ==1){
       orderCashExamineService.updateCashBet(ordercashrecord.getUserId(), ordercashrecord.getAmount());
        }
        if(ordercashrecord.getType() ==2){
            if(orderCashExamineEntity.getUserValidBet().
                    add(ordercashrecord.getAmount().multiply(new BigDecimal(-1))).compareTo(BigDecimal.ZERO)<0){
                throw new RRException(ErrorCode.CashErrorCode.CASH_REDUCE_ERRO.getErrMsg(),
                        ErrorCode.CashErrorCode.CASH_REDUCE_ERRO.getCode());
            }
            orderCashExamineService.updateCashBet(ordercashrecord.getUserId(), ordercashrecord.getAmount().multiply(new BigDecimal(-1)));
        }
      ordercashrecord.setSysUserId(getUser().getUserId());
      ordercashrecord.setSysUserAccount(getUser().getUsername());

			orderCashRecordService.insert(ordercashrecord);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ordercashrecord:ordercashrecord:update")
    public R update(@RequestBody OrderCashRecordEntity ordercashrecord){
			orderCashRecordService.updateById(ordercashrecord);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ordercashrecord:ordercashrecord:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			orderCashRecordService.deleteById(id);
	}
        return R.ok();
    }

}
