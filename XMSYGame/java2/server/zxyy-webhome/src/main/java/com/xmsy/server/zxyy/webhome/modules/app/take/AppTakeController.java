package com.xmsy.server.zxyy.webhome.modules.app.take;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.annotation.TakeOrderRepeat;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.Constant.TakeMoneyChannel;
import com.xmsy.server.zxyy.webhome.common.utils.DateUtils;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.MathUtil;
import com.xmsy.server.zxyy.webhome.common.utils.OrderNoUtil;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.app.event.PushService;
import com.xmsy.server.zxyy.webhome.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.webhome.modules.app.take.result.OrderCommissionTakeMoneyResult;
import com.xmsy.server.zxyy.webhome.modules.app.take.result.OrderTakeMoneyResult;
import com.xmsy.server.zxyy.webhome.modules.manager.cashpriceconfig.entity.CashPriceConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.cashpriceconfig.service.CashPriceConfigService;
import com.xmsy.server.zxyy.webhome.modules.manager.ordercashexamine.service.OrderCashExamineService;
import com.xmsy.server.zxyy.webhome.modules.manager.ordertakemoney.entity.OrderTakeMoneyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.ordertakemoney.service.OrderTakeMoneyService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.userinfo.entity.UserInfoEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userinfo.service.UserInfoService;
import com.xmsy.server.zxyy.webhome.modules.manager.userpassword.entity.UserPasswordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userpassword.service.UserPasswordService;
import com.xmsy.server.zxyy.webhome.utils.JwtUtil;
import com.xmsy.server.zxyy.webhome.utils.UserStatusVerificationUtil;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 取现
 *
 * @author adu
 * @email xxxxx
 * @date 2019-02-18 11:26:25
 */
@Slf4j
@RestController
@RequestMapping("V1.0/App")
public class AppTakeController {

	@Autowired
	private OrderTakeMoneyService orderTakeMoneyService;
	@Autowired
	private OrderCashExamineService orderCashExamineService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private CashPriceConfigService cashPriceConfigService;
	@Resource
	private UserPasswordService userPasswordService;
	@Resource
	private PushService pushService;

	/**
	 * 获取取现价格预设配置
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/TakePrice")
	public R TakePrice() throws Exception {
		List<CashPriceConfigEntity> orderRechargeList = cashPriceConfigService
				.selectList(new EntityWrapper<CashPriceConfigEntity>(
						new CashPriceConfigEntity().setEnable(true).setType(SysConstant.TAKE_PRICE_TYPE))
								.orderBy("price", true));
		return R.ok().put("data", orderRechargeList);
	}

	// 获取取款审查列表
	@GetMapping("/ExamineList")
	public R getExamineList(@RequestHeader("token") String token) throws Exception {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = userService.selectById(userId);
		if (null == user) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		return R.ok().put("data", orderCashExamineService.updateCashExamineForQuery(user));
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

	// 获取取款渠道
	@PostMapping("/TakeMoney/password")
	public R checkTakeMoneyPassword(@RequestHeader("token") String token) throws Exception {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = userService.selectById(userId);
		if (null == user) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		UserPasswordEntity entity = new UserPasswordEntity().setUserId(userId);
		UserPasswordEntity result = userPasswordService.selectOne(new EntityWrapper<UserPasswordEntity>(entity));
		return R.ok().put("data", result != null && StringUtils.isNotEmpty(result.getBankPassword()));
	}

//	// 提交余额取款申请
//	@TakeOrderRepeat("线下余额取款订单重复提交校验")
//	@PostMapping("/TakeMoney")
//	public R saveTakeMoney(@RequestBody @Valid OrderTakeMoneyEntity takeMoneyRecordEntity,
//			HttpServletRequest httpServletRequest) throws Exception {
//		String token = httpServletRequest.getHeader("token");// 获取人员token
//		Long userId = Long.valueOf(JwtUtil.getUserId(token));
//		UserEntity user = userService.selectById(userId);
//		List<OrderRechargeEntity> list = orderRechargeService.selectList(new EntityWrapper<OrderRechargeEntity>(
//				new OrderRechargeEntity().setStatus(Constant.OrderStatus.COMPLETE.getValue()).setUserId(userId)));
//		if (CollectionUtils.isEmpty(list)) {
//			throw new RRException(ErrorCode.OrderErrorCode.RECHARGE_IS_NULL.getErrMsg(),
//					ErrorCode.OrderErrorCode.RECHARGE_IS_NULL.getCode());
//		}
//		if (null == user) {
//			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
//					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
//		}
//		if (user.getUserType().equals(SysConstant.TRIAL)) {
//			throw new RRException(ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getErrMsg(),
//					ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getCode());
//		}
//		// 校验用户是否被冻结
//		UserStatusVerificationUtil.userFrozenValidate(user.getFrozenEnable());
//		// 是否余额不足
//		if (user.getMoney().compareTo(new BigDecimal(takeMoneyRecordEntity.getTakeAmount())) < 0) {
//			throw new RRException(ErrorCode.OrderErrorCode.MONEY_NOT_ENOUGH.getErrMsg(),
//					ErrorCode.OrderErrorCode.MONEY_NOT_ENOUGH.getCode());
//		}
//		log.info("[saveTakeMoney] takeMoneyRecordEntity {}", takeMoneyRecordEntity);
//		takeMoneyRecordEntity.setType(Constant.TakeMoneyType.TAKE_MONEY.getValue());
//		takeMoneyRecordEntity.setOrderNo(OrderNoUtil.getOrderNo());
//		orderTakeMoneyService.saveTakeMoneyOrder(takeMoneyRecordEntity, user);
//		// 推送客户端
//		UserInfoMessage message = null;
//		try {
//			UserEntity pushMessage = new UserEntity();
//			pushMessage.setId(userId);
//			pushMessage.setMoney(BigDecimal.ZERO.subtract(new BigDecimal(takeMoneyRecordEntity.getTakeAmount()))
//					.subtract(takeMoneyRecordEntity.getPoundage()));
//			message = new UserInfoMessage(pushMessage, null);
//			log.info("[用户余额取款-推送消息] message {}", message);
//			pushService.pushExchange(message);
//		} catch (Exception e) {
//			log.error("[用户余额取款-推送消息] message {}", message);
//		}
//		return R.ok();
//	}
	
		// 提交余额取款申请
		@TakeOrderRepeat("线下余额取款订单重复提交校验")
		@PostMapping("/TakeMoney")
		public R saveTakeMoney(@RequestBody @Valid OrderTakeMoneyEntity takeMoneyRecordEntity,
				HttpServletRequest httpServletRequest) throws Exception {
			String token = httpServletRequest.getHeader("token");// 获取人员token
			Long userId = Long.valueOf(JwtUtil.getUserId(token));
			UserEntity user = userService.selectById(userId);
//			List<OrderRechargeEntity> list = orderRechargeService.selectList(new EntityWrapper<OrderRechargeEntity>(
//					new OrderRechargeEntity().setStatus(Constant.OrderStatus.COMPLETE.getValue()).setUserId(userId)));
//			if (CollectionUtils.isEmpty(list)) {
//				throw new RRException(ErrorCode.OrderErrorCode.RECHARGE_IS_NULL.getErrMsg(),
//						ErrorCode.OrderErrorCode.RECHARGE_IS_NULL.getCode());
//			}
			if (null == user) {
				throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
						ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
			}
			if (user.getUserType().equals(SysConstant.TRIAL)) {
				throw new RRException(ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getErrMsg(),
						ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getCode());
			}
			if(user.getUserName()==null||"".equals(user.getUserName())) {
				throw new RRException(ErrorCode.UserErrorCode.USERNAME_IS_NULL.getErrMsg(),
						ErrorCode.UserErrorCode.USERNAME_IS_NULL.getCode());
			}
			// 校验用户是否被冻结
			UserStatusVerificationUtil.userFrozenValidate(user.getFrozenEnable());
			// 是否余额不足
			BigDecimal money=user.getMoney().add(MathUtil.getBigDecimal(user.getCoin()/Constant.COIN_RATE));
			if (money.compareTo(new BigDecimal(takeMoneyRecordEntity.getTakeAmount())) < 0) {
				throw new RRException(ErrorCode.OrderErrorCode.MONEY_NOT_ENOUGH.getErrMsg(),
						ErrorCode.OrderErrorCode.MONEY_NOT_ENOUGH.getCode());
			}
			//log.info("[saveTakeMoney] takeMoneyRecordEntity {}", takeMoneyRecordEntity);
			takeMoneyRecordEntity.setType(Constant.TakeMoneyType.TAKE_MONEY.getValue());
			takeMoneyRecordEntity.setOrderNo(OrderNoUtil.getOrderNo());
			UserEntity pushMessage = orderTakeMoneyService.saveTakeMoneyOrder(takeMoneyRecordEntity, user);
			// 推送客户端
			UserInfoMessage message = null;
			try {
				message = new UserInfoMessage(pushMessage, null);
				log.debug("[用户余额取款-推送消息] message {}", message);
				pushService.pushExchange(message);
			} catch (Exception e) {
				log.error("[用户余额取款-推送消息] message {}", message);
			}
			return R.ok();
		}

	// 提交佣金取款
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
		if (user.getUserType().equals(SysConstant.TRIAL)) {
			throw new RRException(ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getCode());
		}
		if(user.getUserName()==null||"".equals(user.getUserName())) {
			throw new RRException(ErrorCode.UserErrorCode.USERNAME_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USERNAME_IS_NULL.getCode());
		}
		//log.info("[saveTakeCommission] takeMoneyRecordEntity {}", takeMoneyRecordEntity);
		takeMoneyRecordEntity.setType(Constant.TakeMoneyType.COMMISSION.getValue());
		takeMoneyRecordEntity.setOrderNo(OrderNoUtil.getOrderNo());
		orderTakeMoneyService.saveTakeCommissionOrder(takeMoneyRecordEntity, user);
		// 推送客户端
		UserInfoMessage message = null;
		try {
			UserEntity pushMessage = new UserEntity();
			pushMessage.setId(userId);
			pushMessage.setCommission(BigDecimal.ZERO.subtract(new BigDecimal(takeMoneyRecordEntity.getTakeAmount())));
			message = new UserInfoMessage(pushMessage, null);
			log.debug("[用户佣金取款-推送消息] message {}", message);
			pushService.pushExchange(message);
		} catch (Exception e) {
			log.error("[用户佣金取款-推送消息] message {}", message);
		}
		return R.ok();
	}

	// 取款记录
	@GetMapping("/TakeMoneyRecord")
	public R getTakeMoneyRecord(@RequestHeader("token") String token, PageParam pageParam) throws Exception {
		// 游戏记录和转出记录现在查询7天的
		pageParam.setLimit(30);
		String startTime = DateUtils.getPastDate(Constant.QUERY_DAY);
		String endTime = DateUtil.now();
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		Page<OrderTakeMoneyResult> result = orderTakeMoneyService.getTakeMoneyRecord(pageParam, userId, startTime,
				endTime);
		return R.ok().put("data", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	// 佣金取款记录
	@GetMapping("/commissionTakeMoneyRecord")
	public R commissionTakeMoneyRecord(@RequestHeader("token") String token, PageParam pageParam) throws Exception {
		// 游戏记录和转出记录现在查询7天的
		String startTime = DateUtils.getPastDate(Constant.QUERY_DAY);
		String endTime = DateUtil.now();
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		Page<OrderCommissionTakeMoneyResult> result = orderTakeMoneyService.commissionTakeMoneyRecord(pageParam, userId,
				startTime, endTime);
		return R.ok().put("data", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}
}
