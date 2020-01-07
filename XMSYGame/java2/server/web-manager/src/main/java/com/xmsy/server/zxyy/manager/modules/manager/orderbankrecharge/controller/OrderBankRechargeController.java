package com.xmsy.server.zxyy.manager.modules.manager.orderbankrecharge.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.manager.common.utils.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.orderbankrecharge.entity.OrderBankRechargeEntity;
import com.xmsy.server.zxyy.manager.modules.manager.orderbankrecharge.service.OrderBankRechargeService;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;

/**
 * 银行卡充值
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-01-08 16:22:01
 */
@RestController
@RequestMapping("orderbankrecharge/orderbankrecharge")
public class OrderBankRechargeController {
	@Autowired
	private OrderBankRechargeService orderBankRechargeService;
	@Autowired
	private UserService userService;

	/**
	 * 列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/list")
	@RequiresPermissions("orderbankrecharge:orderbankrecharge:list")
	public R list(PageParam pageParam, @RequestParam Map<String, Object> params) {
		PageUtils page = orderBankRechargeService.queryPage(params);
		List<OrderBankRechargeEntity> list = (List<OrderBankRechargeEntity>) page.getList();
		for (OrderBankRechargeEntity entity : list) {
			entity.getUserId();
			UserEntity userEntity = new UserEntity();
			if (!StringUtils.isEmpty(entity.getUserId())) {
				userEntity.setId(entity.getUserId());
				List<UserEntity> userList = userService.selectList(new EntityWrapper<UserEntity>(userEntity));
				if (!CollectionUtils.isEmpty(userList)) {
					entity.setAccount(userList.get(0).getAccount());
				}
			}
		}
		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("orderbankrecharge:orderbankrecharge:info")
	public R info(@PathVariable("id") Long id) {
		OrderBankRechargeEntity rechargeBank = orderBankRechargeService.selectById(id);
		// 转账方式
//		if (SysConstant.BANK.equals(rechargeBank.getDepositType())) {
//			rechargeBank.setDepositType("银行转账");
//		}
//		if (SysConstant.INTERNET.equals(rechargeBank.getDepositType())) {
//			rechargeBank.setDepositType("网银转账");
//		}
//		if (SysConstant.OTHER.equals(rechargeBank.getDepositType())) {
//			rechargeBank.setDepositType("第三方转账");
//		}
//		// 订单状态
//		if (SysConstant.TOBECONFIRMED.equals(rechargeBank.getStatus())) {
//			rechargeBank.setStatus("待确认");
//		}
//		// 订单状态
//		if (SysConstant.CONFIRMED.equals(rechargeBank.getStatus())) {
//			rechargeBank.setStatus("已确认");
//		}
//		// 充值账号
//		if (rechargeBank.getUserId() != null) {
//			rechargeBank.setAccount(userService.selectById(rechargeBank.getUserId()).getAccount());
//		}
		return R.ok().put("rechargebank", rechargeBank);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("orderbankrecharge:orderbankrecharge:save")
	public R save(@RequestBody OrderBankRechargeEntity rechargeBank) {
		orderBankRechargeService.insert(rechargeBank);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("orderbankrecharge:orderbankrecharge:update")
	public R update(@RequestBody OrderBankRechargeEntity rechargeBank) {
		orderBankRechargeService.updateById(rechargeBank);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("orderbankrecharge:orderbankrecharge:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			orderBankRechargeService.deleteById(id);
		}
		return R.ok();
	}

	/**
	 * 确认
	 */
	@RequestMapping("/confirmed")
	@RequiresPermissions("orderbankrecharge:orderbankrecharge:confirmed")
	public R confirmed(@RequestBody Long[] ids) {
		for (Long id : ids) {
			orderBankRechargeService.confirmedById(id);
		}
		return R.ok();
	}

}
