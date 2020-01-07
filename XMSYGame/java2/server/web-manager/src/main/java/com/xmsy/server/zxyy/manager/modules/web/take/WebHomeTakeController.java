package com.xmsy.server.zxyy.manager.modules.web.take;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.annotation.TakeOrderRepeat;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.Constant;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.OrderNoUtil;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.common.utils.Constant.TakeMoneyChannel;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.modules.manager.ordercashexamine.entity.OrderCashExamineEntity;
import com.xmsy.server.zxyy.manager.modules.manager.ordercashexamine.service.OrderCashExamineService;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.service.OrderRechargeService;
import com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.entity.OrderTakeMoneyEntity;
import com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.service.OrderTakeMoneyService;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.modules.manager.userinfo.entity.UserInfoEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userinfo.service.UserInfoService;
import com.xmsy.server.zxyy.manager.utils.JwtUtil;
import com.xmsy.server.zxyy.manager.utils.UserStatusVerificationUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 取现
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
@Slf4j
@RestController
@RequestMapping("webhome")
public class WebHomeTakeController {

	@Autowired
	private OrderTakeMoneyService orderTakeMoneyService;
	@Autowired
	private OrderCashExamineService orderCashExamineService;
	@Autowired
	private OrderRechargeService orderRechargeService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserInfoService userInfoService;

	// 获取取款审查列表
	@GetMapping("/ExamineList")
	public R getExamineList(@RequestParam(required = false, value = "startTime") String startTime,
			@RequestParam(required = false, value = "endTime") String endTime, HttpServletRequest httpServletRequest,
			PageParam pageParam) throws Exception {
		String token = httpServletRequest.getHeader("token");// 获取人员token
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = userService.selectById(userId);
		if (null == user) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		orderCashExamineService.updateCashExamineForQuery(user);
		Page<OrderCashExamineEntity> result = orderCashExamineService.findOrderCashExamines(pageParam,
				new OrderCashExamineEntity().setUserId(userId), startTime, endTime);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	// 获取取款渠道
	@GetMapping("/TakeMoneyChannel")
	public R getTakeMoneyChannel(@RequestHeader("token") String token, HttpServletRequest httpServletRequest)
			throws Exception {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		if (null == userId) {
			throw new RRException(ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getErrMsg(),
					ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getCode());
		}
		List<UserInfoEntity> userinfoColl = userInfoService
				.selectList(new EntityWrapper<UserInfoEntity>(new UserInfoEntity().setUserId(userId)).last("limit 1"));
		if (CollectionUtils.isEmpty(userinfoColl)) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		List<Map<String, Object>> channelList = Lists.newArrayList();
		UserInfoEntity entity = userinfoColl.get(0);
		// Map<String, Object> alipayChannelMap = new HashMap<String, Object>();
		// if (!StringUtils.isEmpty(entity.getAlipayAccount())) {
		// alipayChannelMap.put("id", TakeMoneyChannel.ALIPAY.getValue());
		// alipayChannelMap.put("name", TakeMoneyChannel.ALIPAY.getName());
		// channelList.add(alipayChannelMap);
		// }
		Map<String, Object> bankChannelMap = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(entity.getBankCard())) {
			bankChannelMap.put("id", TakeMoneyChannel.BANK.getValue());
			bankChannelMap.put("name", TakeMoneyChannel.BANK.getName());
			channelList.add(bankChannelMap);
		}
		return R.ok().put("data", channelList);
	}

	// 提交余额取款申请
	@TakeOrderRepeat("线下余额取款订单重复提交校验")
	@PostMapping("/TakeMoney")
	public R saveTakeMoney(@RequestBody @Valid OrderTakeMoneyEntity takeMoneyRecordEntity,
			HttpServletRequest httpServletRequest) throws Exception {
		String token = httpServletRequest.getHeader("token");// 获取人员token
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = userService.selectById(userId);
		List<OrderRechargeEntity> list=orderRechargeService.selectList(new EntityWrapper<OrderRechargeEntity>(
				new OrderRechargeEntity().setStatus(Constant.OrderStatus.COMPLETE.getValue()).setUserId(userId)));
		if(CollectionUtils.isEmpty(list)) {
			throw new RRException(ErrorCode.OrderErrorCode.RECHARGE_IS_NULL.getErrMsg(),
					ErrorCode.OrderErrorCode.RECHARGE_IS_NULL.getCode());
		}
		if (null == user) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		if (user.getUserType().equals(SysConstant.TRIAL)) {
			throw new RRException(ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getCode());
		}
		// 校验用户是否被冻结
		UserStatusVerificationUtil.userFrozenValidate(user.getFrozenEnable());
		// 是否余额不足
		if (user.getMoney().compareTo(new BigDecimal(takeMoneyRecordEntity.getTakeAmount())) < 0) {
			throw new RRException(ErrorCode.OrderErrorCode.MONEY_NOT_ENOUGH.getErrMsg(),
					ErrorCode.OrderErrorCode.MONEY_NOT_ENOUGH.getCode());
		}
		log.info("[saveTakeMoney] takeMoneyRecordEntity {}", takeMoneyRecordEntity);
		takeMoneyRecordEntity.setType(Constant.TakeMoneyType.TAKE_MONEY.getValue());
		takeMoneyRecordEntity.setOrderNo(OrderNoUtil.getOrderNo());
		orderTakeMoneyService.saveTakeMoneyOrder(takeMoneyRecordEntity, user);
		return R.ok();
	}

	// 提交佣金取款
	@TakeOrderRepeat("线下佣金取款订单重复提交校验")
	@PostMapping("/TakeCommission")
	public R saveTakeCommission(@RequestBody @Valid OrderTakeMoneyEntity takeMoneyRecordEntity,
			HttpServletRequest httpServletRequest) throws Exception {
		String token = httpServletRequest.getHeader("token");// 获取人员token
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = userService.selectById(userId);
		if (null == user) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		// 校验用户是否被冻结
		UserStatusVerificationUtil.userFrozenValidate(user.getFrozenEnable());
		log.info("[saveTakeCommission] takeMoneyRecordEntity {}", takeMoneyRecordEntity);
		takeMoneyRecordEntity.setType(Constant.TakeMoneyType.COMMISSION.getValue());
		takeMoneyRecordEntity.setOrderNo(OrderNoUtil.getOrderNo());
		orderTakeMoneyService.saveTakeCommissionOrder(takeMoneyRecordEntity, user);
		return R.ok();
	}
}
