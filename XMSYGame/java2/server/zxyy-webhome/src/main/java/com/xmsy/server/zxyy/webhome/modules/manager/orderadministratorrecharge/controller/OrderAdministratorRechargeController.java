package com.xmsy.server.zxyy.webhome.modules.manager.orderadministratorrecharge.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.ImmutableMap;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.annotation.AdminRechargeOrderCancelRepeat;
import com.xmsy.server.zxyy.webhome.common.annotation.AdminRechargeOrderRepeat;
import com.xmsy.server.zxyy.webhome.common.annotation.UserLog;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.MathUtil;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.manager.orderadministratorrecharge.entity.OrderAdministratorRechargeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.orderadministratorrecharge.param.AdminRechargeParam;
import com.xmsy.server.zxyy.webhome.modules.manager.orderadministratorrecharge.service.OrderAdministratorRechargeService;
import com.xmsy.server.zxyy.webhome.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.orderrecharge.service.OrderRechargeService;
import com.xmsy.server.zxyy.webhome.modules.manager.ordertakemoney.entity.OrderTakeMoneyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.ordertakemoney.service.OrderTakeMoneyService;
import com.xmsy.server.zxyy.webhome.modules.manager.sys.entity.SysUserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.entity.UserHierarchyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.service.UserHierarchyService;
import com.xmsy.server.zxyy.webhome.utils.IpUtil;

import cn.hutool.core.collection.CollectionUtil;

/**
 * 人工充值
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-17 16:48:50
 */
@RestController
@RequestMapping("orderadministratorrecharge/orderadministratorrecharge")
public class OrderAdministratorRechargeController {
	@Autowired
	private OrderAdministratorRechargeService orderAdministratorRechargeService;
	@Autowired
	private UserHierarchyService userHierarchyService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrderRechargeService orderRechargeService;
	@Autowired
	private OrderTakeMoneyService orderTakeMoneyService;


	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("orderadministratorrecharge:orderadministratorrecharge:list")
	public R list(OrderAdministratorRechargeEntity entity, PageParam pageParam) {
		entity.setDesignated(false);
		Page<OrderAdministratorRechargeEntity> result = new Page<OrderAdministratorRechargeEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<OrderAdministratorRechargeEntity> entityWrapper = new EntityWrapper<OrderAdministratorRechargeEntity>(
				entity).ge(!StringUtils.isEmpty(entity.getQueryTime()), "create_time", // 存款时间查询
						entity.getQueryTime() + SysConstant.START_TIME)
				.le(!StringUtils.isEmpty(entity.getQueryTime()), "create_time",
						entity.getQueryTime() + SysConstant.END_TIME)
				.ge(null != entity.getAmountMin() && 0 < entity.getAmountMin(), "amount", // 存款金额查询
						entity.getAmountMin())
				.le(null != entity.getAmountMax() && 0 < entity.getAmountMax(), "amount",
						entity.getAmountMax())
				.orderBy(false,"create_time");
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		entity.selectPage(result, entityWrapper);
		List<OrderAdministratorRechargeEntity> list=result.getRecords();
		if(!CollectionUtil.isEmpty(list)) {
			for(OrderAdministratorRechargeEntity adminEntity:list) {
				if(adminEntity.getHierarchyId()!=null) {
					UserHierarchyEntity userHierarchy=userHierarchyService.selectById(adminEntity.getHierarchyId());
					if(userHierarchy!=null) {
						adminEntity.setHierarchyName(userHierarchy.getName());
					}
				}
				
			}
		}
		
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}
	
	/**
	 * 列表
	 */
	@RequestMapping("/orderList")
	@RequiresPermissions("orderadministratorrecharge:orderadministratorrecharge:orderList")
	public R orderList(OrderAdministratorRechargeEntity entity, PageParam pageParam) {
		entity.setDesignated(true);
		Page<OrderAdministratorRechargeEntity> result = new Page<OrderAdministratorRechargeEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<OrderAdministratorRechargeEntity> entityWrapper = new EntityWrapper<OrderAdministratorRechargeEntity>(
				entity).ge(!StringUtils.isEmpty(entity.getQueryTime()), "create_time", // 存款时间查询
						entity.getQueryTime() + SysConstant.START_TIME)
				.le(!StringUtils.isEmpty(entity.getQueryTime()), "create_time",
						entity.getQueryTime() + SysConstant.END_TIME)
				.ge(null != entity.getAmountMin() && 0 < entity.getAmountMin(), "amount", // 存款金额查询
						entity.getAmountMin())
				.le(null != entity.getAmountMax() && 0 < entity.getAmountMax(), "amount",
						entity.getAmountMax());
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		entity.selectPage(result, entityWrapper);
		
		List<OrderAdministratorRechargeEntity> list=result.getRecords();
		if(!CollectionUtil.isEmpty(list)) {
			for(OrderAdministratorRechargeEntity adminEntity:list) {
				if(adminEntity.getOperationType()==SysConstant.ADMIN_OPERATION_TYPE_0) {
					OrderRechargeEntity orderEntity=new OrderRechargeEntity();
					orderEntity.setAdminOrderNo(adminEntity.getOrderNo());
					Wrapper<OrderRechargeEntity> entityWrapper1 = new EntityWrapper<OrderRechargeEntity>(orderEntity);
					List<OrderRechargeEntity> dlist = orderRechargeService.selectList(entityWrapper1);
					if(!CollectionUtil.isEmpty(dlist)) {
						adminEntity.setOrderStatus(dlist.get(0).getStatus());
					}
				}else {
					OrderTakeMoneyEntity orderEntity=new OrderTakeMoneyEntity();
					orderEntity.setAdminOrderNo(adminEntity.getOrderNo());
					Wrapper<OrderTakeMoneyEntity> entityWrapper2 = new EntityWrapper<OrderTakeMoneyEntity>(orderEntity);
					List<OrderTakeMoneyEntity> dlist = orderTakeMoneyService.selectList(entityWrapper2);
					if(!CollectionUtil.isEmpty(dlist)) {
						adminEntity.setOrderStatus(dlist.get(0).getStatus());
					}
				}
				
			}
		}
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}
	
	/**
	 * 人工充值的订单下给所有充值人的交易订单列表
	 */
	@RequestMapping("/getRechargeList")
	@RequiresPermissions("orderadministratorrecharge:orderadministratorrecharge:orderDetail")
	public R getRechargeList(@RequestParam(value="adminOrderNo") String adminOrderNo,
			@RequestParam(required =false,value="userAccount") String userAccount,
			@RequestParam(value="operationType") int operationType) {
		if(operationType==SysConstant.ADMIN_OPERATION_TYPE_0) {
			OrderRechargeEntity orderEntity=new OrderRechargeEntity();
			orderEntity.setAdminOrderNo(adminOrderNo);
			if(!StringUtils.isEmpty(userAccount)) {
				orderEntity.setUserAccount(userAccount);
			}
			Wrapper<OrderRechargeEntity> entityWrapper = new EntityWrapper<OrderRechargeEntity>(orderEntity);
			return R.ok().put("rechargeList", orderRechargeService.selectList(entityWrapper));
		}else {
			OrderTakeMoneyEntity orderEntity=new OrderTakeMoneyEntity();
			orderEntity.setAdminOrderNo(adminOrderNo);
			if(!StringUtils.isEmpty(userAccount)) {
				orderEntity.setUserAccount(userAccount);
			}
			Wrapper<OrderTakeMoneyEntity> entityWrapper = new EntityWrapper<OrderTakeMoneyEntity>(orderEntity);
			return R.ok().put("rechargeList", orderTakeMoneyService.selectList(entityWrapper));
		}
		
	}
	
	/**
	 * 撤销
	 * @throws Exception 
	 */
	@AdminRechargeOrderCancelRepeat("人工充值订单撤销校验")
	@UserLog(value="人工充值订单撤销")
	@RequestMapping("/revoke")
	@RequiresPermissions("orderadministratorrecharge:orderadministratorrecharge:revoke")
	public R revoke(@RequestBody OrderRechargeEntity orderEntity) throws Exception {
		orderRechargeService.revokeRecharge(orderEntity.getId());
		return R.ok();
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("orderadministratorrecharge:orderadministratorrecharge:info")
	public R info(@PathVariable("id") Long id) {
		OrderAdministratorRechargeEntity orderAdministratorRecharge = orderAdministratorRechargeService.selectById(id);
		return R.ok().put("orderadministratorrecharge", orderAdministratorRecharge);
	}

	/**
	 * 层级
	 */
	@RequestMapping("/getHierarchy")
	@RequiresPermissions("messagemanagement:messagemanagement:info")
	public R getHierarchy() {
		List<UserHierarchyEntity> dataList = userHierarchyService.selectList(new EntityWrapper<UserHierarchyEntity>(new UserHierarchyEntity()));
		List<Long> ids = new ArrayList<>();
		if (dataList != null && !dataList.isEmpty()) {
			for (UserHierarchyEntity data : dataList) {
				ids.add(data.getId());
			}
		}
		return R.ok().put("hierarchyList", dataList).put("ids", ids);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("orderadministratorrecharge:orderadministratorrecharge:save")
	public R save(@RequestBody OrderAdministratorRechargeEntity orderAdministratorRecharge) {
		orderAdministratorRechargeService.insert(orderAdministratorRecharge);
		return R.ok();
	}

	/**
	 * 创建
	 */
	@AdminRechargeOrderRepeat("创建人工充值校验")
	@UserLog(value="创建人工充值")
	@RequestMapping("/create")
	@RequiresPermissions("orderadministratorrecharge:orderadministratorrecharge:create")
	public R create(@RequestBody AdminRechargeParam adminRecharge, HttpServletRequest request) {
		SysUserEntity sysUser = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		// 设置IP地址
		String ip = IpUtil.getIPAddress(request);
		orderAdministratorRechargeService.createAdminRecharge(adminRecharge, ip, sysUser.getUserId(),
				sysUser.getUsername());
		return R.ok();
	}
	
	/**
	 * 创建
	 */
	@AdminRechargeOrderRepeat("创建人工充值校验")
	@UserLog(value="创建人工存取款")
	@RequestMapping("/accessMoney")
	@RequiresPermissions("orderadministratorrecharge:orderadministratorrecharge:accessMoney")
	public R accessMoney(@RequestBody AdminRechargeParam adminRecharge, HttpServletRequest request) {
		SysUserEntity sysUser = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		// 设置IP地址
		String ip = IpUtil.getIPAddress(request);
		if(adminRecharge.getOperationType()==SysConstant.ADMIN_OPERATION_TYPE_0) {//存款
			orderAdministratorRechargeService.createAdminRecharge(adminRecharge, ip, sysUser.getUserId(),
					sysUser.getUsername());
		}else {//取款
			orderAdministratorRechargeService.createAdminTakeMoney(adminRecharge, ip, sysUser.getUserId(), 
					sysUser.getUsername());
		}
		
		return R.ok();
	}
	
	/**
	 * 获取会员
	 */
	@RequestMapping("/getUser")
	@RequiresPermissions("orderadministratorrecharge:orderadministratorrecharge:getUser")
	public R getUser(@RequestParam(value = "account")String account) {
		UserEntity user = new UserEntity();
		user.setAccount(account);
		user=userService.selectOne(new EntityWrapper<UserEntity>(user));
		if(user ==null || user.getId()==null) {
			throw new RRException(
					 ErrorCode.UserErrorCode.USER_ACCOUNT_INVALID.getErrMsg(),
					ErrorCode.UserErrorCode.USER_ACCOUNT_INVALID.getCode());
		}
		BigDecimal totalMoney =MathUtil.getBigDecimal(user.getMoney());
		totalMoney=MathUtil.getBigDecimal(user.getCoin()).divide(new BigDecimal(Constant.COIN_RATE),2,BigDecimal.ROUND_HALF_UP)
				.add(totalMoney);
		return R.ok().put("data", ImmutableMap.of("money", user.getMoney()
				,"userName", user.getUserName(),
				"coin", user.getCoin(),
				"totalMoney", totalMoney,
				"userId",user.getId()));
	}
	
	/**
	 * 获取会员
	 */
	@RequestMapping("/getDiscount")
	@RequiresPermissions("orderadministratorrecharge:orderadministratorrecharge:getUser")
	public R getDiscount(			@RequestParam(value = "hierarchyId")Long hierarchyId,
			@RequestParam(value = "amount")BigDecimal amount) {
		return R.ok().put("data", orderRechargeService.
				countRechargePreferential(amount, true, hierarchyId));
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("orderadministratorrecharge:orderadministratorrecharge:update")
	public R update(@RequestBody OrderAdministratorRechargeEntity orderAdministratorRecharge) {
		orderAdministratorRechargeService.updateById(orderAdministratorRecharge);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("orderadministratorrecharge:orderadministratorrecharge:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			orderAdministratorRechargeService.deleteById(id);
		}
		return R.ok();
	}

}
