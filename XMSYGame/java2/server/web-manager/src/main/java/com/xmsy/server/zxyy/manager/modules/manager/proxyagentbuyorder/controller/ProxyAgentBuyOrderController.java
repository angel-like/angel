package com.xmsy.server.zxyy.manager.modules.manager.proxyagentbuyorder.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.ImmutableMap;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.modules.manager.proxyagentbuyorder.entity.ProxyAgentBuyOrderEntity;
import com.xmsy.server.zxyy.manager.modules.manager.proxyagentbuyorder.service.ProxyAgentBuyOrderService;
import com.xmsy.server.zxyy.manager.modules.manager.proxyordertransactionrecord.entity.ProxyOrderTransactionRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.proxyordertransactionrecord.service.ProxyOrderTransactionRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.sys.controller.AbstractController;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.SysUserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sys.service.SysUserService;

/**
 * 代理购买订单列表
 *
 * @author ahui
 * @email xxxxx
 * @date 2019-08-02 10:09:16
 */
@RestController
@RequestMapping("proxyagentbuyorder/proxyagentbuyorder")
public class ProxyAgentBuyOrderController extends AbstractController {
	@Autowired
	private ProxyAgentBuyOrderService proxyAgentBuyOrderService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private ProxyOrderTransactionRecordService proxyOrderTransactionRecordService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("proxyagentbuyorder:proxyagentbuyorder:list")
	public R list(ProxyAgentBuyOrderEntity proxyagentbuyorder, PageParam pageParam) {
		SysUserEntity sysUser = getUser();
		if (sysUser.getRoleIds().equals(SysConstant.PROXYID)) {
			proxyagentbuyorder.setProxyName(sysUser.getUsername());
		}
		Page<ProxyAgentBuyOrderEntity> result = new Page<ProxyAgentBuyOrderEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<ProxyAgentBuyOrderEntity> entityWrapper = new EntityWrapper<ProxyAgentBuyOrderEntity>(
				proxyagentbuyorder);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		entityWrapper.orderBy("id", false);
		if (proxyagentbuyorder.getStartTime() != null && !proxyagentbuyorder.getStartTime().equals("")) {
			entityWrapper.gt("create_time", proxyagentbuyorder.getStartTime());
		}
		if (proxyagentbuyorder.getEndTime() != null && !proxyagentbuyorder.getEndTime().equals("")) {
			entityWrapper.le("create_time", proxyagentbuyorder.getEndTime());
		}
		proxyagentbuyorder.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("proxyagentbuyorder:proxyagentbuyorder:info")
	public R info(@PathVariable("id") Long id) {
		ProxyAgentBuyOrderEntity proxyAgentBuyOrder = proxyAgentBuyOrderService.selectById(id);
		return R.ok().put("proxyagentbuyorder", proxyAgentBuyOrder);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("proxyagentbuyorder:proxyagentbuyorder:save")
	public R save(@RequestBody ProxyAgentBuyOrderEntity proxyagentbuyorder) {
		SysUserEntity sysUser = getUser();
		if(!sysUser.getRoleIds().equals(SysConstant.SYSMANAGERID)) {//只有系统管理员才能为代理商充值订单
			throw new RRException(ErrorCode.AgentErrorCode.AGENT_NO_AUTHOTITY_ERRO.getErrMsg(),
					ErrorCode.AgentErrorCode.AGENT_NO_AUTHOTITY_ERRO.getCode());
		}
		proxyAgentBuyOrderService.insert(proxyagentbuyorder);
		// 传参，账户名称，和购买获的总金币，更新代理商余额
		sysUserService.updateTotalCoins(proxyagentbuyorder.getProxyName(), proxyagentbuyorder.getGetTatolCoins());

		// 往订单表增加一条记录
		ProxyOrderTransactionRecordEntity entity = new ProxyOrderTransactionRecordEntity();
		// System.out.println(proxyagentbuyorder.getUserId());//测试可以拿到用户id
		entity.setUserId(proxyagentbuyorder.getUserId());// 设置用户id
		entity.setProxyAccount(proxyagentbuyorder.getProxyAccount());// 设置用户账户
		entity.setAmount(BigDecimal.valueOf(proxyagentbuyorder.getGetTatolCoins()));// 设置用户交易额
		entity.setOrderNo(getOrderIdByTime());// 设置订单编号
		// 根据账户名称查用户表返回单个代理用户对象
		SysUserEntity sysUserEntity = sysUserService.queryByUserName(proxyagentbuyorder.getProxyName());
		// System.out.println(sysUserEntity+" 1111111111111111");
        Long l = sysUserEntity == null ? 0L : sysUserEntity.getProxyBalance();
        entity.setProxyBalance(BigDecimal.valueOf(l));// 设置代理商余额
		// System.out.println(entity);
		proxyOrderTransactionRecordService.insert(entity);
		return R.ok();
	}

	// 随机产生一个订单编号
	private String getOrderIdByTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String newDate = sdf.format(new Date());
		String result = "";
		Random random = new Random();
		for (int i = 0; i < 3; i++) {
			result += random.nextInt(10);
		}
		return newDate + result;
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("proxyagentbuyorder:proxyagentbuyorder:update")
	public R update(@RequestBody ProxyAgentBuyOrderEntity proxyagentbuyorder) {
		proxyAgentBuyOrderService.updateById(proxyagentbuyorder);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("proxyagentbuyorder:proxyagentbuyorder:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			proxyAgentBuyOrderService.deleteById(id);
		}
		return R.ok();
	}

	/**
	 * 获取代理名称
	 */
	@RequestMapping("/getProxyName")
	@RequiresPermissions("proxyagentbuyorder:proxyagentbuyorder:list")
	public R getProxyAccount(@RequestParam(value = "account") String phone) {// account账户是手机号
		SysUserEntity sysUser = new SysUserEntity();
		sysUser.setMobile(phone);
		sysUser = sysUserService.selectOne(new EntityWrapper<SysUserEntity>(sysUser));
		if (sysUser == null || sysUser.getUserId() == null) {
			throw new RRException(ErrorCode.UserErrorCode.USER_ACCOUNT_INVALID.getErrMsg(),
					ErrorCode.UserErrorCode.USER_ACCOUNT_INVALID.getCode());
		}
		System.out.println(sysUser);
		return R.ok().put("data", ImmutableMap.of("proxyName", sysUser.getUsername(), "userId", sysUser.getUserId()));
	}

}
