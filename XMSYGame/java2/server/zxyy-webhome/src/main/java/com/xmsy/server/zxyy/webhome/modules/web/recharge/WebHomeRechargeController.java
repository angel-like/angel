package com.xmsy.server.zxyy.webhome.modules.web.recharge;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.xmsy.common.bean.payment.PaymentParam;
import com.xmsy.common.bean.payment.ResultData;
import com.xmsy.common.define.exception.ParamInvalidException;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.common.define.result.GlobalResult;
import com.xmsy.common.define.result.ResultUtils;
import com.xmsy.server.zxyy.webhome.common.annotation.RechargeOrderRepeat;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.CreateQRCode;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.manager.cashbank.entity.CashBankEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.cashbank.service.CashBankService;
import com.xmsy.server.zxyy.webhome.modules.manager.cashqrcode.entity.CashQrcodeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.cashqrcode.service.CashQrcodeService;
import com.xmsy.server.zxyy.webhome.modules.manager.orderbankrecharge.entity.OrderBankRechargeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.orderrecharge.service.OrderRechargeService;
import com.xmsy.server.zxyy.webhome.modules.manager.payconfig.entity.PayConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.payconfig.entity.PayConfigFirstEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.payconfig.entity.PayConfigResultEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.payconfig.service.PayConfigService;
import com.xmsy.server.zxyy.webhome.modules.manager.rechargechannel.entity.RechargeChannelEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.rechargechannel.entity.RechargeChannelResultEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.rechargechannel.service.RechargeChannelService;
import com.xmsy.server.zxyy.webhome.modules.manager.sys.service.SysCaptchaService;
import com.xmsy.server.zxyy.webhome.modules.manager.sysdictionary.entity.SysDictionaryEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sysdictionary.service.SysDictionaryService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.payment.PaymentApi;
import com.xmsy.server.zxyy.webhome.modules.web.recharge.param.QrCodeRechargeParam;
import com.xmsy.server.zxyy.webhome.modules.web.recharge.param.QrcodePaymentParam;
import com.xmsy.server.zxyy.webhome.utils.InviteCode;
import com.xmsy.server.zxyy.webhome.utils.IpUtil;
import com.xmsy.server.zxyy.webhome.utils.JwtUtil;
import com.xmsy.server.zxyy.webhome.utils.UserStatusVerificationUtil;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * 充值
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
@Slf4j
@RestController
@RequestMapping("webhome")
public class WebHomeRechargeController {
	@Autowired
	private OrderRechargeService orderRechargeService;
	@Autowired
	private UserService userService;
	@Autowired
	private SysCaptchaService sysCaptchaService;
	@Autowired
	private CashBankService cashBankService;
	@Autowired
	private PayConfigService payConfigService;
	@Autowired
	private CashQrcodeService cashQrcodeService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private RechargeChannelService rechargeChannelService;

	/**
	 * web端充值 获取二维码 返回一个带有二维码的实体
	 * 
	 * @throws Exception
	 */
	@PostMapping("/QrcodeRecharge")
	public R qrcodeRecharge(@RequestBody @Valid QrcodePaymentParam paymentParam, HttpServletRequest httpServletRequest)
			throws Exception {
		payConfigService.valitePayConfig(paymentParam);
		GlobalResult<ResultData> payResult = rechargeForThird(paymentParam, httpServletRequest, true);
		String qrcode = CreateQRCode.create(payResult.getData().getPayUrl());
		return R.ok().put("qrcode", qrcode).put("orderNo", payResult.getData().getOrderNo());
	}

	/**
	 * 移动端充值返回一个支付链接
	 * 
	 * @throws Exception
	 */
	@PostMapping("/UrlRecharge")
	public R urlRecharge(@RequestBody @Valid QrcodePaymentParam paymentParam, HttpServletRequest httpServletRequest)
			throws Exception {
		payConfigService.valitePayConfig(paymentParam);
		GlobalResult<ResultData> payResult = rechargeForThird(paymentParam, httpServletRequest, false);
		return R.ok().put("url", payResult.getData().getPayUrl());
	}

	/**
	 * web端充值 获取收款二维码类型
	 * 
	 * @throws Exception
	 */
	@GetMapping("/QrcodeType")
	public R qrcodeType(@RequestHeader("token") String token) throws Exception {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));// 通过token获取人员ID
		// 验证人员是否是测试账号(测试账号不允许充值)
		UserEntity userEntity = userService.selectById(userId);
		if (null == userEntity) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		List<Map<String, Object>> dataList = cashQrcodeService.findCashQrcodeTypeByHierarchyId(userEntity.getHierarchyId());
		return R.ok().put("qrcodeType", dataList==null?new ArrayList<>():dataList);
	}

	/**
	 * web端充值 获取指定收款二维码
	 * 
	 * @throws Exception
	 */
	@GetMapping("/Qrcode")
	public R qrcode(@RequestHeader("token") String token, @RequestParam("type") Long type) throws Exception {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));// 通过token获取人员ID
		// 验证人员是否是测试账号(测试账号不允许充值)
		UserEntity userEntity = userService.selectById(userId);
		if (null == userEntity) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		return R.ok().put("qrcode", cashQrcodeService.findCashQrcodeByHierarchyId(userEntity.getHierarchyId(), type));
	}

	/**
	 * web端充值 收款码充值
	 * 
	 * @throws Exception
	 */
	@PostMapping("/QrcodeToRecharge")
	public R qrcode(@RequestBody @Valid QrCodeRechargeParam qrCodeRechargeParam, HttpServletRequest httpServletRequest)
			throws Exception {
		CashQrcodeEntity cashQrcodeEntity = cashQrcodeService.selectById(qrCodeRechargeParam.getCashQrcodeId());
		if (null == cashQrcodeEntity) {
			throw new RRException(ErrorCode.OrderErrorCode.RECHARGE_CHANNEL_ERR.getErrMsg(),
					ErrorCode.OrderErrorCode.RECHARGE_CHANNEL_ERR.getCode());
		}
		log.info("官网二维码收款 qrcode qrCodeRechargeParam {}", qrCodeRechargeParam);
		String ip = IpUtil.getIPAddress(httpServletRequest);
		String token = httpServletRequest.getHeader("token");// 获取人员token
		Long userId = Long.valueOf(JwtUtil.getUserId(token));// 通过token获取人员ID
		// 验证用户是否存在有待确认订单
		OrderRechargeEntity uncomfirmedOrder = new OrderRechargeEntity()
				.setStatus(Constant.OrderStatus.UNCONFIRMED.getValue()).setUserId(userId)
				.setRechargeType(Constant.RechargeType.BANK.getValue());
		List<OrderRechargeEntity> list = orderRechargeService
				.selectList(new EntityWrapper<OrderRechargeEntity>(uncomfirmedOrder));
		if (!CollectionUtils.isEmpty(list)) {
			throw new RRException(ErrorCode.OrderErrorCode.RECHARGE_RECORD_ISNOTNULL_ERRO.getErrMsg(),
					ErrorCode.OrderErrorCode.RECHARGE_RECORD_ISNOTNULL_ERRO.getCode());
		}
		// 验证人员是否是测试账号(测试账号不允许充值)
		UserEntity userEntity = userService.selectById(userId);
		if (null == userEntity) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		// 校验用户是否被冻结
		UserStatusVerificationUtil.userFrozenValidate(userEntity.getFrozenEnable());
		if (userEntity.getUserType().equals(SysConstant.TRIAL)) {
			throw new RRException(ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getCode());
		}

		BigDecimal userCoinToMoney = new BigDecimal(userEntity.getCoin()).divide(new BigDecimal(Constant.COIN_RATE))
				.setScale(2, BigDecimal.ROUND_DOWN);
		// 创建充值订单
		OrderRechargeEntity orderRecharge = new OrderRechargeEntity()
				.setStatus(Constant.OrderStatus.UNCONFIRMED.getValue()).setUserId(userEntity.getId());
		String orderNo = System.nanoTime() + InviteCode.create();
		orderRecharge.setAmount(qrCodeRechargeParam.getAmount());// 存款金额
		orderRecharge.setDepositDate(
				qrCodeRechargeParam.getRechargeTime() == null ? new Date() : qrCodeRechargeParam.getRechargeTime());// 存款时间
		orderRecharge.setDepositName(StringUtils.isEmpty(qrCodeRechargeParam.getNickName()) ? userEntity.getAccount()
				: qrCodeRechargeParam.getNickName());// 存款人
		orderRecharge.setIncomeBank(cashQrcodeEntity.getTypeStr());
		orderRecharge.setIncomeBankAccount(cashQrcodeEntity.getAccount());
		orderRecharge.setPreMoney(userEntity.getMoney().add(userCoinToMoney));// 存款时候的余额+金币
		orderRecharge.setFinalMoney(
				userEntity.getMoney().add(new BigDecimal(qrCodeRechargeParam.getAmount())).add(userCoinToMoney));// 存款后的余额+金币
		orderRecharge.setHierarchyId(userEntity.getHierarchyId()); // 用户层级
		orderRecharge.setRemark(qrCodeRechargeParam.getRemark());// 存款备注
		orderRecharge.setPayee(cashQrcodeEntity.getNickName());
		orderRecharge.setDepositType(cashQrcodeEntity.getType().intValue());
		orderRecharge.setDepositBankAccount(qrCodeRechargeParam.getAccount());// 用户打款的账号
		orderRecharge.setRechargeTerminal(qrCodeRechargeParam.getRechargeTerminal());
		orderRecharge.setUserAccount(userEntity.getAccount());
		orderRecharge.setRechargeChannel(Long.valueOf(cashQrcodeEntity.getType()));
		orderRecharge.setIp(ip);
		orderRecharge.setOrderNo(orderNo);
		orderRecharge.setGmUser(userService.isGmUser(userEntity.getId()));
		orderRecharge.setRechargeType(Constant.RechargeType.BANK.getValue());
		orderRechargeService.insert(orderRecharge);
		return R.ok();
	}

	/**
	 * 根据订单号查询支付结果
	 * 
	 * @throws Exception
	 */
	@GetMapping("/RechargeOrder")
	public R RechargeOrder(@RequestParam("orderNo") String orderNo) throws Exception {
		OrderRechargeEntity orderRechargeEntity = orderRechargeService
				.selectOne(new EntityWrapper<OrderRechargeEntity>(new OrderRechargeEntity().setOrderNo(orderNo)));
		orderRechargeEntity.getStatus();
		return R.ok().put("status", orderRechargeEntity.getStatus() == Constant.OrderStatus.COMPLETE.getValue());
	}

	/**
	 * 通过token 返回用户指定层级银行卡信息
	 *
	 */
	@GetMapping("/getRechargeBankList")
	public R getRechargeBankList(HttpServletRequest httpServletRequest) {
		String token = httpServletRequest.getHeader("token");// 获取人员token
		Long userId = Long.valueOf(JwtUtil.getUserId(token));// 通过token获取人员ID
		UserEntity entity = userService.selectById(userId);// 通过Id获取人员信息
		// 根据人员所属层级ID获取该层级下所有对外展示的银行卡信息
		CashBankEntity sysBank = new CashBankEntity();
		sysBank.setHierarchyId(entity.getHierarchyId().toString());
		Page<CashBankEntity> cashBank = cashBankService.findCashBankPage(new PageParam(), sysBank);
		List<Object> arry = new ArrayList<Object>();
		for (CashBankEntity cashBankEntity : cashBank.getRecords()) {
			JSONObject obj = new JSONObject();
			obj.put("children", cashBankEntity);
			obj.put("bank", cashBankEntity.getName());
			arry.add(obj);
		}
		return R.ok().put("data", arry);
	}

	/**
	 * 获取银行类型
	 *
	 */
	@GetMapping("/BankType")
	public R getRechargeBankType(HttpServletRequest httpServletRequest) {
		return R.ok().put("data", sysDictionaryService.findListByParentCode(Constant.CODE));
	}
	/**
	 * 获取银行充值类型
	 *
	 */
	@GetMapping("/RechargeBankType")
	public R getRechargeBankType2(HttpServletRequest httpServletRequest) {
		return R.ok().put("data", sysDictionaryService.findListByParentCode(Constant.DEPOSITMETHODCODE));
	}

	/**
	 * 银行卡充值 表单提交
	 * 
	 * @throws Exception
	 *
	 */
	@RechargeOrderRepeat("充值表单重复提交验证")
	@PostMapping("/BankRecharge")
	public R saveRechargeBank(@RequestBody @Valid OrderBankRechargeEntity orderBankRechargeEntity,
			HttpServletRequest httpServletRequest) throws Exception {
		log.info("官网银行存款 saveRechargeBank：orderBankRechargeEntity {}", orderBankRechargeEntity);
		String ip = IpUtil.getIPAddress(httpServletRequest);
		String token = httpServletRequest.getHeader("token");// 获取人员token
		Long userId = Long.valueOf(JwtUtil.getUserId(token));// 通过token获取人员ID
		SysDictionaryEntity rechargeBankType = sysDictionaryService.selectById(orderBankRechargeEntity.getDepositType());
		log.info("rechargeBankType {}",rechargeBankType);
		if(rechargeBankType==null || rechargeBankType.getId()==null) {
			throw new RRException(ErrorCode.OrderErrorCode.RECEIPT_CHANNEL_ERR.getErrMsg(),
					ErrorCode.OrderErrorCode.RECEIPT_CHANNEL_ERR.getCode());
		}
		// 验证用户是否存在有待确认订单
		OrderRechargeEntity uncomfirmedOrder = new OrderRechargeEntity()
				.setStatus(Constant.OrderStatus.UNCONFIRMED.getValue()).setUserId(userId)
				.setRechargeType(Constant.RechargeType.BANK.getValue());
		List<OrderRechargeEntity> list = orderRechargeService
				.selectList(new EntityWrapper<OrderRechargeEntity>(uncomfirmedOrder));
		if (!CollectionUtils.isEmpty(list)) {
			throw new RRException(ErrorCode.OrderErrorCode.RECHARGE_RECORD_ISNOTNULL_ERRO.getErrMsg(),
					ErrorCode.OrderErrorCode.RECHARGE_RECORD_ISNOTNULL_ERRO.getCode());
		}
		// 验证人员是否是测试账号(测试账号不允许充值)
		UserEntity userEntity = userService.selectById(userId);
		if (null == userEntity) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		// 校验用户是否被冻结
		UserStatusVerificationUtil.userFrozenValidate(userEntity.getFrozenEnable());
		if (userEntity.getUserType().equals(SysConstant.TRIAL)) {
			throw new RRException(ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getCode());
		}
		// 查询平台收款账号信息
		CashBankEntity sysBank = cashBankService.selectById(orderBankRechargeEntity.getBankId());
		if (null == sysBank) {
			throw new RRException(ErrorCode.OrderErrorCode.BANK_ISNULL_ERRO.getErrMsg(),
					ErrorCode.OrderErrorCode.BANK_ISNULL_ERRO.getCode());
		}
//		if (!sysBank.getHierarchyId().equals(userEntity.getHierarchyId())) {
//			log.error("该用户所属层级没有该存款银行信息");
//			throw new RRException(ErrorCode.OrderErrorCode.BANK_ISNULL_ERRO.getErrMsg(),
//					ErrorCode.OrderErrorCode.BANK_ISNULL_ERRO.getCode());
//		}
		String[] strings = sysBank.getHierarchyId().split(",");
		Boolean flag=true;
		for(String s:strings) {
			if (s.equals(userEntity.getHierarchyId().toString())) {
				flag=false;
			}
		}
		if(flag) {
			log.error("该用户所属层级没有该存款银行信息");
			throw new RRException(ErrorCode.OrderErrorCode.BANK_ISNULL_ERRO.getErrMsg(),
					ErrorCode.OrderErrorCode.BANK_ISNULL_ERRO.getCode());
		}
		BigDecimal userCoinToMoney = new BigDecimal(userEntity.getCoin()).divide(new BigDecimal(Constant.COIN_RATE))
				.setScale(2, BigDecimal.ROUND_DOWN);
		// 创建充值订单
		OrderRechargeEntity orderRecharge = new OrderRechargeEntity()
				.setStatus(Constant.OrderStatus.UNCONFIRMED.getValue()).setUserId(userEntity.getId());
		String orderNo = System.nanoTime() + InviteCode.create();
		orderRecharge.setIncomeBank(sysBank.getName());// 收款银行
		orderRecharge.setIncomeBankAccount(sysBank.getBankAccount());// 收款人卡号
		orderRecharge.setPayee(sysBank.getBankUser());// 收款人
		orderRecharge.setOpenBank(sysBank.getBankAddress());// 开户网点
		orderRecharge.setAmount(orderBankRechargeEntity.getAmount());// 存款金额
		orderRecharge.setDepositBank(rechargeBankType.getName());// 存款银行
		orderRecharge.setDepositDate(new Date());// 存款时间
		orderRecharge.setDepositName(orderBankRechargeEntity.getName());// 存款人
		orderRecharge.setDepositType(orderBankRechargeEntity.getDepositType());
		orderRecharge.setPreMoney(userEntity.getMoney().add(userCoinToMoney));// 存款时候的余额+金币
		orderRecharge.setFinalMoney(
				userEntity.getMoney().add(new BigDecimal(orderBankRechargeEntity.getAmount())).add(userCoinToMoney));// 存款后的余额+金币
		orderRecharge.setFinalMoney(userEntity.getMoney().add(new BigDecimal(orderBankRechargeEntity.getAmount())));
		orderRecharge.setHierarchyId(userEntity.getHierarchyId()); // 用户层级
		orderRecharge.setRemark(orderBankRechargeEntity.getRemark());// 存款备注
		// orderRecharge.setDepositBankAccount(orderBankRechargeEntity.getDepositBankAccount());//
		orderRecharge.setRechargeTerminal(orderBankRechargeEntity.getRechargeTerminal());
		orderRecharge.setUserAccount(userEntity.getAccount());
		orderRecharge.setRechargeChannel(Long.valueOf(Constant.TransactionDetailType.BANKRECHARGE.getValue()));
		orderRecharge.setIp(ip);
		orderRecharge.setOrderNo(orderNo);
		orderRecharge.setGmUser(userService.isGmUser(userEntity.getId()));
		orderRecharge.setRechargeType(Constant.RechargeType.BANK.getValue());
		orderRechargeService.insert(orderRecharge);
		return R.ok();
	}

	/**
	 * 创建第三方支付订单
	 * 
	 * @param paymentParam
	 * @param httpServletRequest
	 * @param isCheckCode
	 *            是否检查校验码
	 * @return
	 * @throws Exception
	 */
	private GlobalResult<ResultData> rechargeForThird(QrcodePaymentParam paymentParam,
			HttpServletRequest httpServletRequest, boolean isCheckCode) throws Exception {
		String token = httpServletRequest.getHeader("token");// 获取人员token
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		String ip = IpUtil.getIPAddress(httpServletRequest);
		UserEntity user = userService.selectById(userId);
		if (null == user) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		// 校验用户是否被冻结
		UserStatusVerificationUtil.userFrozenValidate(user.getFrozenEnable());
		if (user.getUserType().equals(SysConstant.TRIAL)) {
			throw new RRException(ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getCode());
		}
		if (isCheckCode && !sysCaptchaService.validate(paymentParam.getCaptchaUuid(), paymentParam.getCode())) {
			throw new RRException(ErrorCode.InvitationCodeErrorCode.RECOMMENDATION_CODE_ISNULL.getErrMsg(),
					ErrorCode.InvitationCodeErrorCode.RECOMMENDATION_CODE_ISNULL.getCode());
		}
		PayConfigEntity payConfig = payConfigService.selectById(paymentParam.getPayId());
		if (null == payConfig) {
			throw new RRException(ErrorCode.OrderErrorCode.PAY_COMPANY_ERR.getErrMsg(),
					ErrorCode.OrderErrorCode.PAY_COMPANY_ERR.getCode());
		}
		RechargeChannelEntity rechargeChannel = rechargeChannelService.selectById(paymentParam.getPayChannel());
		if (null == rechargeChannel) {
			throw new RRException(ErrorCode.OrderErrorCode.RECHARGE_CHANNEL_ERR.getErrMsg(),
					ErrorCode.OrderErrorCode.RECHARGE_CHANNEL_ERR.getCode());
		}
		// 调用第三方支付
		String orderNo = System.nanoTime() + InviteCode.create();
		PaymentParam param = new PaymentParam().setOrderNo(orderNo).setAmount(paymentParam.getAmount())
				.setPayChannel(rechargeChannel.getAlias()).setPayServiceName(payConfig.getAliasName()).setOrderIp(ip);
		GlobalResult<ResultData> result = PaymentApi.payPC(param);
		if (!ResultUtils.isSuccess(result)) {// 如果成功返回
			throw new ParamInvalidException(result.getErrorMsg());
		}
		BigDecimal userCoinToMoney = new BigDecimal(user.getCoin()).divide(new BigDecimal(Constant.COIN_RATE))
				.setScale(2, BigDecimal.ROUND_DOWN);
		// 充值是时候为了测试，让充值金额放大10000倍
		// 创建充值订单
		OrderRechargeEntity rechargeOrder = new OrderRechargeEntity().setIp(ip).setPreMoney(user.getMoney())
				.setAmount(paymentParam.getAmount()).setUserAccount(user.getAccount()).setUserId(user.getId())
				.setRechargeType(Constant.RechargeType.THIRD.getValue()).setOrderNo(orderNo)
				.setRemark(paymentParam.getRemark()).setRechargePlatform(payConfig.getPayName())
				.setRechargeTerminal(paymentParam.getRechargeTerminal())
				.setPreMoney(user.getMoney().add(userCoinToMoney))// 存款时候的余额+金币
				.setFinalMoney(user.getMoney().add(new BigDecimal(paymentParam.getAmount())).add(userCoinToMoney))// 存款后的余额+金币
				.setMerchantOrderNo(result.getData().getMerchantOrderNo())
				.setStatus(Constant.OrderStatus.UNCONFIRMED.getValue()).setHierarchyId(user.getHierarchyId())
				.setRechargeChannel(rechargeChannel.getId())
				.setGmUser(userService.isGmUser(user.getId()));
		log.info("[扫码充值] QrcodePaymentParam:{} user：{}", param, user);
		orderRechargeService.insert(rechargeOrder);
		return result;
	}

	/**
	 * 获取支付侧边导航
	 */
	@GetMapping("/webRechargeNavigation")
	public R webRechargeNavigation() {
		List<PayConfigResultEntity> list = payConfigService.webRechargeNavigation();
		log.info("[webRechargeNavigation] list {}", list);
		return R.ok().put("data", list);
	}

	/**
	 * 获取首推支付宝和微信
	 */
	@GetMapping("/firstPayForWeb")
	public R firstPayForWeb() {
		List<PayConfigFirstEntity> list = rechargeChannelService.selectFirstRecommendForWeb();
		log.info("[firstPayForWeb] list {}", list);
		return R.ok().put("data", list);
	}

	/**
	 * 获取指定支付公司指定金额的支付方式列表
	 */
	@GetMapping("/paymentMethodForWeb")
	public R paymentMethodForWeb(@RequestParam Long payId, @RequestParam Long amount) {
		log.info("[paymentMethodForWeb] payId {} amount {}", payId, amount);
		List<RechargeChannelResultEntity> rechargeChannels = rechargeChannelService.selectChannelsByPayId(payId);
		log.info("[paymentMethodForWeb] rechargeChannels {}", rechargeChannels);
		if (CollectionUtils.isEmpty(rechargeChannels)) {
			return R.ok().put("data", rechargeChannels);
		}
		List<RechargeChannelResultEntity> rechargeChannelResult = Lists.newArrayList();
		List<Long> list = Lists.newArrayList();
		for (RechargeChannelResultEntity rechargeChannel : rechargeChannels) {
			if (amount <= rechargeChannel.getHighLimit() && amount >= rechargeChannel.getLowLimit()) {
				rechargeChannelResult.add(rechargeChannel);
			}
			list.add(rechargeChannel.getLowLimit());
			list.add(rechargeChannel.getHighLimit());
		}
		if (CollectionUtils.isEmpty(rechargeChannelResult)) {
			Collections.sort(list);
			throw new RRException("[最小支付金额" + list.get(0) + ",最大支付金额" + list.get(list.size() - 1) + "]",
					ErrorCode.PayErrorCode.PAY_CHANNEL_AMOUNT_ERROR.getCode());
		} else {
			return R.ok().put("data", rechargeChannelResult);
		}
	}
}
