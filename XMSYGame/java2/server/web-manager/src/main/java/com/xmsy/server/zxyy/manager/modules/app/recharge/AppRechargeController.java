// package com.xmsy.server.zxyy.manager.modules.app.recharge;

// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// import lombok.extern.slf4j.Slf4j;
//
/// **
// * 充值
// *
// * @author adu
// * @email xxxxx
// * @date 2019-02-18 11:00:25
// */
// @Slf4j
// @RestController
// @RequestMapping("V1.0/App")
// public class AppRechargeController {
// @Autowired
// private OrderRechargeService orderRechargeService;
// @Autowired
// private HierarchyPaymentRelationshipService
// hierarchyPaymentRelationshipService;
// @Autowired
// private PayConfigService payConfigService;
// @Autowired
// private SpringContextUtils springContextUtils;
// @Autowired
// private UserService userService;
// @Autowired
// private CashBankService cashBankService;
// @Autowired
// private RechargeAmountService rechargeAmountService;
// @Autowired
// private CashQrcodeService cashQrcodeService;
// @Autowired
// private SysDictionaryService sysDictionaryService;
// @Autowired
// private CashPriceConfigService cashPriceConfigService;
// private static Map<Long, PayService> payServiceMap = Maps.newConcurrentMap();
//
// @PostConstruct
// private void init() {
// Map<String, PayService> beanOfTypes =
// springContextUtils.getBeanOfTypes(PayService.class);
// for (PayService payService : beanOfTypes.values()) {
// payServiceMap.put(payService.getValue(), payService);
// }
// log.info("[payServiceMap] :{}", payServiceMap);
// }
//
// /**
// * 移动端充值返回一个支付链接
// *
// * @throws Exception
// */
// @PostMapping("/UrlRecharge")
// public R urlRecharge(@RequestBody @Valid AppQrcodePaymentParam paymentParam,
// HttpServletRequest httpServletRequest)
// throws Exception {
// payConfigService.valitePayConfig(paymentParam);
// PayResult payResult = rechargeForThird(paymentParam, httpServletRequest,
// false);
// return R.ok().put("data", ImmutableMap.of("url",
// payResult.getData().getPayUrl()));
// }
//
// /**
// * web端充值 获取收款二维码类型
// *
// * @throws Exception
// */
// @GetMapping("/QrcodeType")
// public R qrcodeType() throws Exception {
// List<SysDictionaryEntity> list =
// sysDictionaryService.findListByParentCode(Constant.QR_TYPE);
// List<CashQrcodeEntity> cashQrcodeList = cashQrcodeService
// .selectList(new EntityWrapper<CashQrcodeEntity>(new
// CashQrcodeEntity().setEnable(true)));
// Set<SysDictionaryEntity> result = Sets.newHashSet();
// if (CollectionUtils.isEmpty(list) || CollectionUtils.isEmpty(cashQrcodeList))
// {
// return R.ok().put("data", result);
// }
// for (SysDictionaryEntity sysDictionary : list) {
// for (CashQrcodeEntity cashQrcode : cashQrcodeList) {
// if (sysDictionary.getCode().equals(String.valueOf(cashQrcode.getType()))) {
// result.add(sysDictionary);
// }
// }
// }
// return R.ok().put("data", result);
// }
//
// /**
// * .获取众享支付金额设定
// *
// * @param payId
// * 支付公司id
// * @return
// */
// @GetMapping("/DefaultPayAmount")
// public R DefaultPayAmount() {
// List<RechargeAmountEntity> list = rechargeAmountService.selectList(
// new EntityWrapper<RechargeAmountEntity>(new
// RechargeAmountEntity().setPayId(Constant.DEFAULT_PAY))
// .orderAsc(Lists.newArrayList("amount")));
// log.info("[DefaultPay] result {}", list);
// return R.ok().put("data", list);
// }
//
// /**
// * web端充值 获取指定收款二维码
// *
// * @throws Exception
// */
// @GetMapping("/Qrcode")
// public R qrcode(@RequestHeader("token") String token, @RequestParam("type")
// Long type) throws Exception {
// Long userId = Long.valueOf(JwtUtil.getUserId(token));// 通过token获取人员ID
// // 验证人员是否是测试账号(测试账号不允许充值)
// UserEntity userEntity = userService.selectById(userId);
// if (null == userEntity) {
// throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
// ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
// }
// return R.ok().put("data",
// cashQrcodeService.findCashQrcode(userEntity.getHierarchyId(), type));
// }
//
// /**
// * web端充值 收款码充值
// *
// * @throws Exception
// */
// @PostMapping("/QrcodeRecharge")
// public R qrcode(@RequestBody @Valid QrCodeRechargeParam qrCodeRechargeParam,
// HttpServletRequest httpServletRequest)
// throws Exception {
// CashQrcodeEntity cashQrcodeEntity =
// cashQrcodeService.selectById(qrCodeRechargeParam.getCashQrcodeId());
// if (null == cashQrcodeEntity) {
// throw new
// RRException(ErrorCode.OrderErrorCode.RECHARGE_CHANNEL_ERR.getErrMsg(),
// ErrorCode.OrderErrorCode.RECHARGE_CHANNEL_ERR.getCode());
// }
// log.info("app二维码收款 qrcode qrCodeRechargeParam {}", qrCodeRechargeParam);
// String ip = IpUtil.getIPAddress(httpServletRequest);
// String token = httpServletRequest.getHeader("token");// 获取人员token
// Long userId = Long.valueOf(JwtUtil.getUserId(token));// 通过token获取人员ID
// // 验证用户是否存在有待确认订单
// OrderRechargeEntity uncomfirmedOrder = new OrderRechargeEntity()
// .setStatus(Constant.OrderStatus.UNCONFIRMED.getValue()).setUserId(userId)
// .setRechargeType(Constant.RechargeType.BANK.getValue());
// List<OrderRechargeEntity> list = orderRechargeService
// .selectList(new EntityWrapper<OrderRechargeEntity>(uncomfirmedOrder));
// if (!CollectionUtils.isEmpty(list)) {
// throw new
// RRException(ErrorCode.OrderErrorCode.RECHARGE_RECORD_ISNOTNULL_ERRO.getErrMsg(),
// ErrorCode.OrderErrorCode.RECHARGE_RECORD_ISNOTNULL_ERRO.getCode());
// }
// // 验证人员是否是测试账号(测试账号不允许充值)
// UserEntity userEntity = userService.selectById(userId);
// if (null == userEntity) {
// throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
// ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
// }
// // 校验用户是否被冻结
// UserStatusVerificationUtil.userFrozenValidate(userEntity.getFrozenEnable());
// if (userEntity.getUserType().equals(SysConstant.TRIAL)) {
// throw new RRException(ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getErrMsg(),
// ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getCode());
// }
//
// // 创建充值订单
// OrderRechargeEntity orderRecharge = new OrderRechargeEntity()
// .setStatus(Constant.OrderStatus.UNCONFIRMED.getValue()).setUserId(userEntity.getId());
// String orderNo = System.nanoTime() + InviteCode.create();
// BigDecimal userCoinToMoney = new BigDecimal(userEntity.getCoin()).divide(new
// BigDecimal(Constant.COIN_RATE))
// .setScale(2, BigDecimal.ROUND_DOWN);
// orderRecharge.setAmount(qrCodeRechargeParam.getAmount());// 存款金额
// orderRecharge.setDepositDate(
// qrCodeRechargeParam.getRechargeTime() == null ? new Date() :
// qrCodeRechargeParam.getRechargeTime());// 存款时间
// orderRecharge.setDepositName(StringUtils.isEmpty(qrCodeRechargeParam.getNickName())
// ? userEntity.getAccount()
// : qrCodeRechargeParam.getNickName());// 存款人
// orderRecharge.setIncomeBank(cashQrcodeEntity.getTypeStr());
// orderRecharge.setIncomeBankAccount(cashQrcodeEntity.getAccount());
// orderRecharge.setPreMoney(userEntity.getMoney().add(userCoinToMoney));//
// 存款时候的余额
// orderRecharge.setFinalMoney(
// userEntity.getMoney().add(new
// BigDecimal(qrCodeRechargeParam.getAmount())).add(userCoinToMoney));
// orderRecharge.setHierarchyId(userEntity.getHierarchyId()); // 用户层级
// orderRecharge.setRemark(qrCodeRechargeParam.getRemark());// 存款备注
// orderRecharge.setPayee(cashQrcodeEntity.getNickName());
// orderRecharge.setDepositType(cashQrcodeEntity.getType().intValue());
// orderRecharge.setDepositBankAccount(qrCodeRechargeParam.getAccount());//
// 用户打款的账号
// orderRecharge.setRechargeTerminal(qrCodeRechargeParam.getRechargeTerminal());
// orderRecharge.setUserAccount(userEntity.getAccount());
// orderRecharge.setRechargeChannel(Long.valueOf(cashQrcodeEntity.getType()));
// orderRecharge.setIp(ip);
// orderRecharge.setOrderNo(orderNo);
// orderRecharge.setRechargeType(Constant.RechargeType.BANK.getValue());
// orderRechargeService.insert(orderRecharge);
// return R.ok();
// }
//
// /**
// * 根据订单号查询支付结果
// *
// * @throws Exception
// */
// @GetMapping("/RechargeOrder")
// public R RechargeOrder(@RequestParam("orderNo") String orderNo) throws
// Exception {
// OrderRechargeEntity orderRechargeEntity = orderRechargeService
// .selectOne(new EntityWrapper<OrderRechargeEntity>(new
// OrderRechargeEntity().setOrderNo(orderNo)));
// return R.ok().put("data",
// ImmutableMap.of("status", orderRechargeEntity.getStatus() ==
// Constant.OrderStatus.COMPLETE.getValue()));
// }
//
// /**
// * 获取充值价格预设配置
// *
// * @return
// * @throws Exception
// */
// @GetMapping("/RechargePrice")
// public R RechargePrice() throws Exception {
// List<CashPriceConfigEntity> orderRechargeList = cashPriceConfigService
// .selectList(new EntityWrapper<CashPriceConfigEntity>(
// new
// CashPriceConfigEntity().setEnable(true).setType(SysConstant.RECHARGE_PRICE_TYPE))
// .orderBy("price", true));
// return R.ok().put("data", orderRechargeList);
// }
//
// /**
// * 通过token 返回用户指定层级银行卡信息
// *
// */
// @GetMapping("/RechargeBankList")
// public R getRechargeBankList(HttpServletRequest httpServletRequest) {
// String token = httpServletRequest.getHeader("token");// 获取人员token
// Long userId = Long.valueOf(JwtUtil.getUserId(token));// 通过token获取人员ID
// UserEntity entity = userService.selectById(userId);// 通过Id获取人员信息
// // 根据人员所属层级ID获取该层级下所有对外展示的银行卡信息
// CashBankEntity sysBank = new CashBankEntity();
// sysBank.setHierarchyId(entity.getHierarchyId());
// Page<CashBankEntity> cashBank = cashBankService.findCashBankPage(new
// PageParam(), sysBank);
// List<Object> arry = new ArrayList<Object>();
// for (CashBankEntity cashBankEntity : cashBank.getRecords()) {
// JSONObject obj = new JSONObject();
// obj.put("children", cashBankEntity);
// obj.put("bank", cashBankEntity.getName());
// arry.add(obj);
// }
// log.info("[获取收款银行列表] sysDictionarys {}", arry);
// return R.ok().put("data", arry);
// }
//
// /**
// * 获取银行充值类型
// *
// */
// @GetMapping("/RechargeBankType")
// public R getRechargeBankType(HttpServletRequest httpServletRequest) {
// return R.ok().put("data", Constant.RechargeBankType.getLookup());
// }
//
// /**
// * 银行卡充值 表单提交
// *
// * @throws Exception
// *
// */
// @RechargeOrderRepeat("充值表单重复提交验证")
// @PostMapping("/BankRecharge")
// public R saveRechargeBank(@RequestBody @Valid OrderBankRechargeEntity
// orderBankRechargeEntity,
// HttpServletRequest httpServletRequest) throws Exception {
// log.info("app银行存款 saveRechargeBank：orderBankRechargeEntity {}",
// orderBankRechargeEntity);
// String ip = IpUtil.getIPAddress(httpServletRequest);
// String token = httpServletRequest.getHeader("token");// 获取人员token
// Long userId = Long.valueOf(JwtUtil.getUserId(token));// 通过token获取人员ID
// if (StringUtils
// .isEmpty(Constant.RechargeBankType.getBanktypemap().get(orderBankRechargeEntity.getDepositType())))
// {
// throw new
// RRException(ErrorCode.OrderErrorCode.RECEIPT_CHANNEL_ERR.getErrMsg(),
// ErrorCode.OrderErrorCode.RECEIPT_CHANNEL_ERR.getCode());
// }
// // 验证用户是否存在有待确认订单
// OrderRechargeEntity uncomfirmedOrder = new OrderRechargeEntity()
// .setStatus(Constant.OrderStatus.UNCONFIRMED.getValue()).setUserId(userId)
// .setRechargeType(Constant.RechargeType.BANK.getValue());
// List<OrderRechargeEntity> list = orderRechargeService
// .selectList(new EntityWrapper<OrderRechargeEntity>(uncomfirmedOrder));
// if (!CollectionUtils.isEmpty(list)) {
// throw new
// RRException(ErrorCode.OrderErrorCode.RECHARGE_RECORD_ISNOTNULL_ERRO.getErrMsg(),
// ErrorCode.OrderErrorCode.RECHARGE_RECORD_ISNOTNULL_ERRO.getCode());
// }
// // 验证人员是否是测试账号(测试账号不允许充值)
// UserEntity userEntity = userService.selectById(userId);
// if (null == userEntity) {
// throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
// ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
// }
// // 校验用户是否被冻结
// UserStatusVerificationUtil.userFrozenValidate(userEntity.getFrozenEnable());
// if (userEntity.getUserType().equals(SysConstant.TRIAL)) {
// throw new RRException(ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getErrMsg(),
// ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getCode());
// }
// // 查询平台收款账号信息
// CashBankEntity sysBank =
// cashBankService.selectById(orderBankRechargeEntity.getBankId());
// if (null == sysBank) {
// throw new RRException(ErrorCode.OrderErrorCode.BANK_ISNULL_ERRO.getErrMsg(),
// ErrorCode.OrderErrorCode.BANK_ISNULL_ERRO.getCode());
// }
// if (!sysBank.getHierarchyId().equals(userEntity.getHierarchyId())) {
// log.error("该用户所属层级没有该存款银行信息");
// throw new RRException(ErrorCode.OrderErrorCode.BANK_ISNULL_ERRO.getErrMsg(),
// ErrorCode.OrderErrorCode.BANK_ISNULL_ERRO.getCode());
// }
// // 创建充值订单
// BigDecimal userCoinToMoney = new BigDecimal(userEntity.getCoin()).divide(new
// BigDecimal(Constant.COIN_RATE))
// .setScale(2, BigDecimal.ROUND_DOWN);
// OrderRechargeEntity orderRecharge = new OrderRechargeEntity()
// .setStatus(Constant.OrderStatus.UNCONFIRMED.getValue()).setUserId(userEntity.getId());
// String orderNo = System.nanoTime() + InviteCode.create();
// orderRecharge.setIncomeBank(sysBank.getName());// 收款银行
// orderRecharge.setIncomeBankAccount(sysBank.getBankAccount());// 收款人卡号
// orderRecharge.setPayee(sysBank.getBankUser());// 收款人
// orderRecharge.setOpenBank(sysBank.getBankAddress());// 开户网点
// orderRecharge.setAmount(orderBankRechargeEntity.getAmount());
// orderRecharge.setDepositBank(orderBankRechargeEntity.getDepositBank());//
// 存款银行
// orderRecharge.setDepositDate(new Date());// 存款时间
// orderRecharge.setDepositName(orderBankRechargeEntity.getName());// 存款人
// orderRecharge.setDepositType(orderBankRechargeEntity.getDepositType());
// orderRecharge.setPreMoney(userEntity.getMoney().add(userCoinToMoney));//
// 存款时候的余额+金币
// orderRecharge.setFinalMoney(
// userEntity.getMoney().add(new
// BigDecimal(orderBankRechargeEntity.getAmount())).add(userCoinToMoney));//
// 存款后的余额+金币
// orderRecharge.setHierarchyId(userEntity.getHierarchyId()); // 用户层级
// orderRecharge.setRemark(orderBankRechargeEntity.getRemark());// 存款备注
// //
// orderRecharge.setDepositBankAccount(orderBankRechargeEntity.getDepositBankAccount());
// orderRecharge.setRechargeTerminal(orderBankRechargeEntity.getRechargeTerminal());
// orderRecharge.setUserAccount(userEntity.getAccount());
// orderRecharge.setRechargeChannel(Long.valueOf(Constant.TransactionDetailType.BANKRECHARGE.getValue()));
// orderRecharge.setIp(ip);
// orderRecharge.setOrderNo(orderNo);
// orderRecharge.setRechargeType(Constant.RechargeType.BANK.getValue());
// orderRechargeService.insert(orderRecharge);
// return R.ok();
// }
//
// /**
// * 创建第三方支付订单
// *
// * @param paymentParam
// * @param httpServletRequest
// * @param isCheckCode
// * 是否检查校验码
// * @return
// * @throws Exception
// */
// private PayResult rechargeForThird(AppQrcodePaymentParam paymentParam,
// HttpServletRequest httpServletRequest,
// boolean isCheckCode) throws Exception {
// String token = httpServletRequest.getHeader("token");// 获取人员token
// Long userId = Long.valueOf(JwtUtil.getUserId(token));
// String ip = IpUtil.getIPAddress(httpServletRequest);
// UserEntity user = userService.selectById(userId);
// if (null == user) {
// throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
// ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
// }
// // 校验用户是否被冻结
// UserStatusVerificationUtil.userFrozenValidate(user.getFrozenEnable());
// if (user.getUserType().equals(SysConstant.TRIAL)) {
// throw new RRException(ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getErrMsg(),
// ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getCode());
// }
// // 调用第三方支付
// String orderNo = System.nanoTime() + InviteCode.create();
// PayParam param = new
// PayParam().setOrderNo(orderNo).setAmount(paymentParam.getAmount());
// param.setPayChannel(PayChannel.getPayChannel(paymentParam.getPayChannel()));
// param.setPlatform(PayPlatform.H5);
// PayService payService = payServiceMap.get(paymentParam.getPayId());
// if (null == payService) {
// throw new RRException(ErrorCode.OrderErrorCode.PAY_COMPANY_ERR.getErrMsg(),
// ErrorCode.OrderErrorCode.PAY_COMPANY_ERR.getCode());
// }
// param.setReturnUrl(payService.getCallbackUri(HallUrlConstant.getHOST_URL()));
// param.setOrderNotifyUrl(payService.getCallbackUri(HallUrlConstant.getHOST_URL()));
// param.setOrderIp(ip);
// PayResult result = payService.pay(param);
// if (!PayResultUtil.success(result)) {// 如果成功返回
// throw new ParamInvalidException(result.getMsg());
// }
// log.info("[扫码充值] PayResult {}", result);
// paymentParam.setAmount(paymentParam.getAmount());
// BigDecimal userCoinToMoney = new BigDecimal(user.getCoin()).divide(new
// BigDecimal(Constant.COIN_RATE))
// .setScale(2, BigDecimal.ROUND_DOWN);
// // 创建充值订单
// OrderRechargeEntity rechargeOrder = new
// OrderRechargeEntity().setIp(ip).setPreMoney(user.getMoney())
// .setAmount(paymentParam.getAmount()).setUserAccount(user.getAccount()).setUserId(user.getId())
// .setRechargeType(Constant.RechargeType.THIRD.getValue()).setOrderNo(orderNo)
// .setRechargePlatform(String.valueOf(payService.getValue()))
// .setRechargeTerminal(paymentParam.getRechargeTerminal())
// .setPreMoney(user.getMoney().add(userCoinToMoney))// 存款时候的余额+金币
// .setFinalMoney(user.getMoney().add(new
// BigDecimal(paymentParam.getAmount()).add(userCoinToMoney)))// 存款后的余额+金币
// .setMerchantOrderNo(result.getData().getMerchantOrderNo())
// .setStatus(Constant.OrderStatus.UNCONFIRMED.getValue()).setHierarchyId(user.getHierarchyId())
// .setRechargeChannel(param.getPayChannel().getValue());
// log.info("[扫码充值] QrcodePaymentParam:{} user： {} param {}", user, param);
// orderRechargeService.insert(rechargeOrder);
// return result;
// }
//
// /**
// * 获取用户可用支付列表
// *
// */
// @GetMapping("/RechargeList")
// public R getRechargeList(@RequestHeader("token") String token) {
// Long userId = Long.valueOf(JwtUtil.getUserId(token));// 通过token获取人员ID
// // 验证人员是否是测试账号(测试账号不允许充值)
// UserEntity userEntity = userService.selectById(userId);
// if (null == userEntity) {
// throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
// ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
// }
// return R.ok().put("data", hierarchyPaymentRelationshipService.
// getRechargeListByHierarchyId(userEntity.getHierarchyId()));
// }
// }
