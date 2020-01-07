package com.xmsy.server.zxyy.webhome.modules.app.recharge;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.xmsy.common.bean.payment.PaymentParam;
import com.xmsy.common.bean.payment.ResultData;
import com.xmsy.common.define.exception.ParamInvalidException;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.common.define.result.GlobalResult;
import com.xmsy.common.define.result.ResultUtils;
import com.xmsy.server.zxyy.webhome.cache.LocalContentCache;
import com.xmsy.server.zxyy.webhome.common.annotation.RechargeOrderRepeat;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.app.recharge.param.AppQrcodePaymentParam;
import com.xmsy.server.zxyy.webhome.modules.app.recharge.param.BankCardParam;
import com.xmsy.server.zxyy.webhome.modules.manager.cashbank.entity.CashBankEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.cashbank.service.CashBankService;
import com.xmsy.server.zxyy.webhome.modules.manager.cashpriceconfig.entity.CashPriceConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.cashpriceconfig.service.CashPriceConfigService;
import com.xmsy.server.zxyy.webhome.modules.manager.cashqrcode.entity.CashQrcodeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.cashqrcode.service.CashQrcodeService;
import com.xmsy.server.zxyy.webhome.modules.manager.hierarchypaymentrelationship.service.HierarchyPaymentRelationshipService;
import com.xmsy.server.zxyy.webhome.modules.manager.orderbankrecharge.entity.OrderBankRechargeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.orderrecharge.service.OrderRechargeService;
import com.xmsy.server.zxyy.webhome.modules.manager.payconfig.entity.PayConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.payconfig.service.PayConfigService;
import com.xmsy.server.zxyy.webhome.modules.manager.rechargeamount.entity.RechargeAmountEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.rechargeamount.service.RechargeAmountService;
import com.xmsy.server.zxyy.webhome.modules.manager.rechargechannel.entity.RechargeChannelEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.rechargechannel.service.RechargeChannelService;
import com.xmsy.server.zxyy.webhome.modules.manager.sysdictionary.entity.SysDictionaryEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sysdictionary.service.SysDictionaryService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.userinfo.entity.UserInfoEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userinfo.service.UserInfoService;
import com.xmsy.server.zxyy.webhome.modules.manager.userpassword.service.UserPasswordService;
import com.xmsy.server.zxyy.webhome.modules.payment.PaymentApi;
import com.xmsy.server.zxyy.webhome.modules.web.recharge.param.QrCodeRechargeParam;
import com.xmsy.server.zxyy.webhome.utils.InviteCode;
import com.xmsy.server.zxyy.webhome.utils.IpUtil;
import com.xmsy.server.zxyy.webhome.utils.JwtUtil;
import com.xmsy.server.zxyy.webhome.utils.UserStatusVerificationUtil;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * 充值
 *
 * @author adu
 * @email xxxxx
 * @date 2019-02-18 11:00:25
 */
@Slf4j
@RestController
@RequestMapping("V1.0/App")
public class AppRechargeController {
	@Autowired
	private OrderRechargeService orderRechargeService;
	@Autowired
	private HierarchyPaymentRelationshipService hierarchyPaymentRelationshipService;
	@Autowired
	private PayConfigService payConfigService;
	@Autowired
	private RechargeChannelService rechargeChannelService;
	@Autowired
	private UserService userService;
	@Autowired
	private CashBankService cashBankService;
	@Autowired
	private RechargeAmountService rechargeAmountService;
	@Autowired
	private CashQrcodeService cashQrcodeService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private CashPriceConfigService cashPriceConfigService;
	@Resource
	private UserPasswordService userPasswordService;
	@Autowired
	private UserInfoService userInfoService;
	@Resource
	private LocalContentCache localContentCache;

	public static final String RECHAEGE_TERMINAL_WEB = "WEB";

	/**
	 * 移动端充值返回一个支付链接
	 *
	 * @throws Exception
	 */
	@PostMapping("/UrlRecharge")
	public R urlRecharge(@RequestBody @Valid AppQrcodePaymentParam paymentParam, HttpServletRequest httpServletRequest)
			throws Exception {
		payConfigService.valitePayConfig(paymentParam);
		GlobalResult<ResultData> payResult = rechargeForThird(paymentParam, httpServletRequest, false);
		return R.ok().put("data", ImmutableMap.of("url", payResult.getData().getPayUrl()));
	}

	/**
	 * web端充值 获取收款二维码类型
	 *
	 * @throws Exception
	 */
	@GetMapping("/QrcodeType")
	public R qrcodeType() throws Exception {
		List<SysDictionaryEntity> list = sysDictionaryService.findListByParentCode(Constant.QR_TYPE);
		List<CashQrcodeEntity> cashQrcodeList = cashQrcodeService
				.selectList(new EntityWrapper<CashQrcodeEntity>(new CashQrcodeEntity().setEnable(true)));
		Set<SysDictionaryEntity> result = Sets.newHashSet();
		if (CollectionUtils.isEmpty(list) || CollectionUtils.isEmpty(cashQrcodeList)) {
			return R.ok().put("data", result);
		}
		for (SysDictionaryEntity sysDictionary : list) {
			for (CashQrcodeEntity cashQrcode : cashQrcodeList) {
				if (sysDictionary.getCode().equals(String.valueOf(cashQrcode.getType()))) {
					result.add(sysDictionary);
				}
			}
		}
		return R.ok().put("data", result);
	}

	/**
	 * .获取众享支付金额设定
	 *            支付公司id
	 * @return
	 */
	@GetMapping("/DefaultPayAmount")
	public R DefaultPayAmount() {
		List<RechargeAmountEntity> list = rechargeAmountService.selectList(
				new EntityWrapper<RechargeAmountEntity>(new RechargeAmountEntity().setPayId(Constant.DEFAULT_PAY))
						.orderAsc(Lists.newArrayList("amount")));
		//log.debug("[DefaultPay] result {}", list);
		return R.ok().put("data", list);
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
		return R.ok().put("data", cashQrcodeService.findCashQrcode(userEntity.getHierarchyId(), type));
	}

	/**
	 * web端充值 收款码充值
	 *
	 * @throws Exception
	 */
	@PostMapping("/QrcodeRecharge")
	public R qrcode(@RequestBody @Valid QrCodeRechargeParam qrCodeRechargeParam, HttpServletRequest httpServletRequest)
			throws Exception {
		CashQrcodeEntity cashQrcodeEntity = cashQrcodeService.selectById(qrCodeRechargeParam.getCashQrcodeId());
		if (null == cashQrcodeEntity) {
			throw new RRException(ErrorCode.OrderErrorCode.RECHARGE_CHANNEL_ERR.getErrMsg(),
					ErrorCode.OrderErrorCode.RECHARGE_CHANNEL_ERR.getCode());
		}
		log.debug("app二维码收款 qrcode qrCodeRechargeParam {}", qrCodeRechargeParam);
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

		// 创建充值订单
		OrderRechargeEntity orderRecharge = new OrderRechargeEntity()
				.setStatus(Constant.OrderStatus.UNCONFIRMED.getValue()).setUserId(userEntity.getId());
		String orderNo = System.nanoTime() + InviteCode.create();
		BigDecimal userCoinToMoney = new BigDecimal(userEntity.getCoin()).divide(new BigDecimal(Constant.COIN_RATE))
				.setScale(2, BigDecimal.ROUND_DOWN);
		orderRecharge.setAmount(qrCodeRechargeParam.getAmount());// 存款金额
		orderRecharge.setDepositDate(
				qrCodeRechargeParam.getRechargeTime() == null ? new Date() : qrCodeRechargeParam.getRechargeTime());// 存款时间
		orderRecharge.setDepositName(StringUtils.isEmpty(qrCodeRechargeParam.getNickName()) ? userEntity.getAccount()
				: qrCodeRechargeParam.getNickName());// 存款人
		orderRecharge.setIncomeBank(cashQrcodeEntity.getTypeStr());
		orderRecharge.setIncomeBankAccount(cashQrcodeEntity.getAccount());
		orderRecharge.setPreMoney(userEntity.getMoney().add(userCoinToMoney));// 存款时候的余额
		orderRecharge.setFinalMoney(
				userEntity.getMoney().add(new BigDecimal(qrCodeRechargeParam.getAmount())).add(userCoinToMoney));
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
		return R.ok().put("data",
				ImmutableMap.of("status", orderRechargeEntity.getStatus() == Constant.OrderStatus.COMPLETE.getValue()));
	}

	/**
	 * 获取充值价格预设配置
	 *
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/RechargePrice")
	public R RechargePrice() throws Exception {
		List<CashPriceConfigEntity> orderRechargeList = cashPriceConfigService
				.selectList(new EntityWrapper<CashPriceConfigEntity>(
						new CashPriceConfigEntity().setEnable(true).setType(SysConstant.RECHARGE_PRICE_TYPE))
						.orderBy("price", true));
		return R.ok().put("data", orderRechargeList);
	}

	/**
	 * 通过token 返回用户指定层级银行卡信息
	 *
	 */
	@GetMapping("/RechargeBankList")
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
		//log.debug("[获取收款银行列表] sysDictionarys {}", arry);
		return R.ok().put("data", arry);
	}

	/**
	 * 获取银行充值类型
	 *
	 */
	@GetMapping("/RechargeBankType")
	public R getRechargeBankType(HttpServletRequest httpServletRequest) {
		return R.ok().put("data", Constant.RechargeBankType.getLookup());
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
		log.debug("app银行存款 001 saveRechargeBank：orderBankRechargeEntity {}", orderBankRechargeEntity);
		String ip = IpUtil.getIPAddress(httpServletRequest);
		String token = httpServletRequest.getHeader("token");// 获取人员token
		Long userId = Long.valueOf(JwtUtil.getUserId(token));// 通过token获取人员ID
		SysDictionaryEntity rechargeBankType = sysDictionaryService.selectById(orderBankRechargeEntity.getDepositType());
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
		if (null == sysBank || StringUtils.isEmpty(sysBank.getHierarchyId())) {
			throw new RRException(ErrorCode.OrderErrorCode.BANK_ISNULL_ERRO.getErrMsg(),
					ErrorCode.OrderErrorCode.BANK_ISNULL_ERRO.getCode());
		}
		/*if (!sysBank.getHierarchyId().equals(userEntity.getHierarchyId())) {
			log.error("该用户所属层级没有该存款银行信息");
			throw new RRException(ErrorCode.OrderErrorCode.BANK_ISNULL_ERRO.getErrMsg(),
					ErrorCode.OrderErrorCode.BANK_ISNULL_ERRO.getCode());
		}*/
		String[] strings = sysBank.getHierarchyId().split(",");
		Boolean flag=true;
		for(String s:strings) {
			if (s.equals(userEntity.getHierarchyId().toString())) {
				flag=false;
				break;
			}
		}
		if(flag) {
			log.error("该用户所属层级没有该存款银行信息");
			throw new RRException(ErrorCode.OrderErrorCode.BANK_ISNULL_ERRO.getErrMsg(),
					ErrorCode.OrderErrorCode.BANK_ISNULL_ERRO.getCode());
		}
		// 创建充值订单
		BigDecimal userCoinToMoney = new BigDecimal(userEntity.getCoin()).divide(new BigDecimal(Constant.COIN_RATE))
				.setScale(2, BigDecimal.ROUND_DOWN);
		OrderRechargeEntity orderRecharge = new OrderRechargeEntity()
				.setStatus(Constant.OrderStatus.UNCONFIRMED.getValue()).setUserId(userEntity.getId());
		String orderNo = System.nanoTime() + InviteCode.create();
		orderRecharge.setIncomeBank(sysBank.getName());// 收款银行
		orderRecharge.setIncomeBankAccount(sysBank.getBankAccount());// 收款人卡号
		orderRecharge.setPayee(sysBank.getBankUser());// 收款人
		orderRecharge.setOpenBank(sysBank.getBankAddress());// 开户网点
		orderRecharge.setAmount(orderBankRechargeEntity.getAmount());
		orderRecharge.setDepositBank(rechargeBankType.getName());// 存款银行
		orderRecharge.setDepositDate(new Date());// 存款时间
		orderRecharge.setDepositName(orderBankRechargeEntity.getName());// 存款人
		orderRecharge.setDepositType(orderBankRechargeEntity.getDepositType());
		orderRecharge.setPreMoney(userEntity.getMoney().add(userCoinToMoney));// 存款时候的余额+金币
		orderRecharge.setFinalMoney(
				userEntity.getMoney().add(new BigDecimal(orderBankRechargeEntity.getAmount())).add(userCoinToMoney));// 存款后的余额+金币
		orderRecharge.setHierarchyId(userEntity.getHierarchyId()); // 用户层级
		orderRecharge.setRemark(orderBankRechargeEntity.getRemark());// 存款备注
		// orderRecharge.setDepositBankAccount(orderBankRechargeEntity.getDepositBankAccount());
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

//	private Boolean isRechargeBankType(List<SysDictionaryEntity> rechargeBankTypeList,Integer depositType) {
//		for(SysDictionaryEntity rechargeBankType : rechargeBankTypeList) {
//			if(depositType==rechargeBankType.getId().intValue()) {
//				return true;
//			}
//		}
//		return false;
//	}

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
	private GlobalResult<ResultData> rechargeForThird(AppQrcodePaymentParam paymentParam,
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
		PayConfigEntity payConfig = payConfigService.selectById(paymentParam.getPayId());
//		PayConfigEntity payConfig = payConfigService.selectByAliasName()
		if (null == payConfig) {
			throw new RRException(ErrorCode.OrderErrorCode.PAY_COMPANY_ERR.getErrMsg(),
					ErrorCode.OrderErrorCode.PAY_COMPANY_ERR.getCode());
		}
		RechargeChannelEntity rechargeChannel = rechargeChannelService.selectById(paymentParam.getPayChannel());
//		RechargeChannelEntity rechargeChannel = rechargeChannelService.getRechargeChannelByAliasName();
		if (null == rechargeChannel) {
			throw new RRException(ErrorCode.OrderErrorCode.RECHARGE_CHANNEL_ERR.getErrMsg(),
					ErrorCode.OrderErrorCode.RECHARGE_CHANNEL_ERR.getCode());
		}
		// 调用第三方支付
		String orderNo = System.nanoTime() + InviteCode.create();
		PaymentParam param = new PaymentParam().setOrderNo(orderNo).setAmount(paymentParam.getAmount())
				.setPayChannel(rechargeChannel.getAlias()).setPayServiceName(payConfig.getAliasName()).setOrderIp(ip).setUserName(user.getAccount());
		GlobalResult<ResultData> result = PaymentApi.payH5(param);
		if (!ResultUtils.isSuccess(result)) {// 如果成功返回
			throw new ParamInvalidException(result.getErrorMsg());
		}
//		GlobalResult<ResultData> result = new GlobalResult<ResultData>();
//		result.setData(new ResultData("www.baidu.com", "", ""));
		//log.debug("[第三方充值] result {}", result);
		paymentParam.setAmount(paymentParam.getAmount());
		BigDecimal userCoinToMoney = new BigDecimal(user.getCoin()).divide(new BigDecimal(Constant.COIN_RATE))
				.setScale(2, BigDecimal.ROUND_DOWN);
		// 创建充值订单
		OrderRechargeEntity rechargeOrder = new OrderRechargeEntity().setIp(ip).setPreMoney(user.getMoney())
				.setAmount(paymentParam.getAmount()).setUserAccount(user.getAccount()).setUserId(user.getId())
				.setRechargeType(Constant.RechargeType.THIRD.getValue()).setOrderNo(orderNo)
				.setRechargePlatform(payConfig.getPayName())
				.setRechargeTerminal(StringUtils.isEmpty(paymentParam.getRechargeTerminal()) ? RECHAEGE_TERMINAL_WEB : paymentParam.getRechargeTerminal())
				.setPreMoney(user.getMoney().add(userCoinToMoney))// 存款时候的余额+金币
				.setFinalMoney(user.getMoney().add(new BigDecimal(paymentParam.getAmount()).add(userCoinToMoney)))// 存款后的余额+金币
				.setMerchantOrderNo(result.getData().getMerchantOrderNo())
				.setStatus(Constant.OrderStatus.UNCONFIRMED.getValue()).setHierarchyId(user.getHierarchyId())
				.setRechargeChannel(rechargeChannel.getId())
				.setDepositDate(new Date())// 存款时间
				.setGmUser(userService.isGmUser(user.getId()));
		log.debug("[扫码充值] QrcodePaymentParam:{} user： {} param {}", user, param);
		orderRechargeService.insert(rechargeOrder);
		return result;
	}

	/**
	 * 获取用户可用支付列表
	 *
	 */
	@GetMapping("/RechargeList")
	public R getRechargeList(@RequestHeader("token") String token) {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));// 通过token获取人员ID
		// 验证人员是否是测试账号(测试账号不允许充值)
		UserEntity userEntity = userService.selectById(userId);
		if (null == userEntity) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		return R.ok().put("data", hierarchyPaymentRelationshipService.
				getRechargeListByHierarchyId(userEntity.getHierarchyId()));
	}
	/**
	 * 获取银行卡卡号
	 *
	 */
	@PostMapping("/bankCardNum")
	public R showBankCardNum(@RequestBody BankCardParam bankCard,@RequestHeader("token") String token) {
		// token转为userID,并通过其获取会员用户实体
		UserEntity user = userService.getUser(token);
		if(StringUtils.isEmpty(bankCard.getPassword())) {//1.如果密码没有输入
			//先查询下缓存是否有通过验证的标记，有赋值给这个类的值去验证密码，没有的话报错
			String  passwordCache= localContentCache.getPassword(user.getId().toString()).toString();
			if(StringUtils.isEmpty(passwordCache)) {
				throw new RRException(ErrorCode.PasswordErrorCode.BANK_PASSWORD_ISNULL_ERRO.getErrMsg(),
						ErrorCode.PasswordErrorCode.BANK_PASSWORD_ISNULL_ERRO.getCode());
			}
			bankCard.setPassword(passwordCache);
		}else {//2.把密码放入缓存中
			localContentCache.putPassword(user.getId().toString(), bankCard.getPassword());
		}
		//校验密码是否正确
		if(!userPasswordService.validateBankPassword(bankCard.getPassword(),user.getId())) {
			throw new RRException(ErrorCode.PasswordErrorCode.PASSWORD_INVALID_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.PASSWORD_INVALID_ERRO.getCode());
		}
		//3.获取会员信息
		UserInfoEntity userInfoEntity = userInfoService
				.selectOne(new EntityWrapper<UserInfoEntity>(new UserInfoEntity().setUserId(user.getId())));
		// 校验用户是否已经绑定银行卡
		if (StringUtils.isEmpty(userInfoEntity.getBankCard())) {
			throw new RRException(ErrorCode.UserErrorCode.BANKCARD_NOT_BOUND.getErrMsg(),
					ErrorCode.UserErrorCode.BANKCARD_NOT_BOUND.getCode());
		}
		Map<String,String> map=new HashMap<String,String>();
		map.put("userBankCard", userInfoEntity.getBankCard());
		return R.ok().put("data",map);
	}
	/**
	 * 充值记录接口
	 *
	 */
	@PostMapping("/rechargeRecord")
	public R rechargeRecord(@RequestHeader("token") String token, @RequestParam("status") int status) {
		// token转为userID,并通过其获取会员用户实体
		UserEntity user = userService.getUser(token);
		OrderRechargeEntity orderRecharge=new OrderRechargeEntity();
		orderRecharge.setUserId(user.getId());
		if(status==1) {
			orderRecharge.setStatus(status);//订单状态0：未确认，1:取消 2：完成
		}else if(status==2) {
			orderRecharge.setStatus(status);//订单状态0：未确认，1:取消 2：完成
		}else if(status!=0) {  //0代表全部    未确认不显示就忽略
			//抛异常
			throw new RRException(ErrorCode.OrderErrorCode.RECHARGE_TYPE_IS_ERRO.getErrMsg(),
					ErrorCode.OrderErrorCode.RECHARGE_TYPE_IS_ERRO.getCode());
		}

		List<OrderRechargeEntity> dataList = orderRechargeService
				.selectList(new EntityWrapper<OrderRechargeEntity>(orderRecharge).orderBy("id", false));
		List<Map<String, Object>> list = Lists.newArrayList();
		if(dataList != null && !dataList.isEmpty()) {
			Map<String, Object> map = null;
			for(OrderRechargeEntity orderRechargeEntity:dataList) {
				map= new HashMap<>();
				if(orderRechargeEntity.getStatus()==1) {////充值状态
					map.put("status", "失败");
				}else if(orderRechargeEntity.getStatus()==2) {
					map.put("status", "成功");
				}else if(orderRechargeEntity.getStatus()==0) {
					map.put("status", "待审核");
				}else if(orderRechargeEntity.getStatus()==4) {
					map.put("status", "已锁定");
				}
				if(orderRechargeEntity.getRechargeType()==1) {//充值类型
					map.put("rechargeType", "后台人工充值");
				}else if(orderRechargeEntity.getRechargeType()==2) {
					map.put("rechargeType", "第三方支付");
				}else if(orderRechargeEntity.getRechargeType()==3) {
					map.put("rechargeType", "线下银行卡充值");
				}
				map.put("amount", orderRechargeEntity.getAmount());//充值金额
				map.put("orderNo", orderRechargeEntity.getOrderNo());//订单号
				map.put("createTime", orderRechargeEntity.getCreateTime());//创建时间
				map.put("rechargeTime", orderRechargeEntity.getRechargeTime());//充值时间(系统给用户账号加钱的时间)
				list.add(map);
			}
		}
		return R.ok().put("data", list);
	}

}
