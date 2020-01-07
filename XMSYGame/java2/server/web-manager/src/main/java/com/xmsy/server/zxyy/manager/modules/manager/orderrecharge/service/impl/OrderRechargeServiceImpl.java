package com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.bean.message.RechargeRebateMessage;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.Constant;
import com.xmsy.server.zxyy.manager.common.utils.Constant.OrderStatus;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.MathUtil;
import com.xmsy.server.zxyy.manager.common.utils.OrderNoUtil;
import com.xmsy.server.zxyy.manager.modules.manager.ordercashexamine.dao.OrderCashExamineDao;
import com.xmsy.server.zxyy.manager.modules.manager.ordercashexamine.entity.OrderCashExamineEntity;
import com.xmsy.server.zxyy.manager.modules.manager.ordercashexamine.service.OrderCashExamineService;
import com.xmsy.server.zxyy.manager.modules.manager.orderpreferential.entity.OrderPreferentialEntity;
import com.xmsy.server.zxyy.manager.modules.manager.orderpreferential.service.OrderPreferentialService;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.dao.OrderRechargeDao;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.entity.OrderBankRechargeLockingEntity;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.entity.OrderRechargeStatisticsThree;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.entity.OrderRechargeStatisticsTwo;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.service.OrderRechargeService;
import com.xmsy.server.zxyy.manager.modules.manager.statistics.entity.RechargeReport;
import com.xmsy.server.zxyy.manager.modules.manager.statistics.entity.RechargeTableReport;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParamFour;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.modules.manager.usertransactionrecord.entity.UserTransactionRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.usertransactionrecord.service.UserTransactionRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.uservip.entity.UserVipEntity;
import com.xmsy.server.zxyy.manager.modules.manager.uservip.service.UserVipService;
import com.xmsy.server.zxyy.manager.modules.web.user.result.UserOrderRechargeExchangeParam;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * .充值订单处理逻辑
 *
 * @author Administrator
 */
@Slf4j
@Transactional
@Service("orderRechargeService")
public class OrderRechargeServiceImpl extends ServiceImpl<OrderRechargeDao, OrderRechargeEntity>
        implements OrderRechargeService {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderCashExamineService orderCashExamineService;

    @Autowired
    private UserTransactionRecordService userTransactionRecordService;

    @Autowired
    private OrderPreferentialService orderPreferentialService;
    @Autowired
    private OrderCashExamineDao orderCashExamineDao;
    @Autowired
    private UserVipService userVipService;

    @Override
    public Integer updateOrderRecharge(String merchantOrderNo, String orderNo, Integer completed, Integer uncomfirmed,
                                       String rechargeTime) {
        log.info("[updateOrderRecharge] merchantOrderNo {},orderNo {},completed {},uncomfirmed {},rechargeTime {}",
                merchantOrderNo, orderNo, completed, uncomfirmed, rechargeTime);
        return baseMapper.updateOrderRecharge(merchantOrderNo, orderNo, completed, uncomfirmed, rechargeTime);
    }

    @Override
    @Transactional
    public void saveAdministratorRecharge(OrderRechargeEntity orderRecharge, int transactionType, UserEntity user)
            throws Exception {
        //计算VIP优惠
        BigDecimal vipDiscountAmount = BigDecimal.ZERO;
        if (user != null && user.getVipId() > 0) {
            UserVipEntity userVip = userVipService.selectById(user.getVipId());
            if (null != userVip && null != userVip.getRechargeRate()) {
                vipDiscountAmount = userVip.getRechargeRate().multiply
                        (MathUtil.getBigDecimal(orderRecharge.getAmount()));
            }
        }
        orderRecharge.setDiscountAmount(vipDiscountAmount.add
                (MathUtil.getBigDecimal(orderRecharge.getDiscountAmount())));
        orderRecharge.setFinalMoney(orderRecharge.getPreMoney().add(orderRecharge.getDiscountAmount())
                .add(MathUtil.getBigDecimal(orderRecharge.getAmount())));// 存款后的余额+金币
        // 保存订单
        orderRecharge.setOrderNo(OrderNoUtil.getOrderNo());
        BigDecimal userNeedBet = orderCashExamineService.saveCashExamine(orderRecharge, user);
        orderRecharge.setUserNeedBet(userNeedBet);
        this.baseMapper.insert(orderRecharge);
        saveOrderRechargeTransactionRecord(orderRecharge, transactionType, user);
//		// 添加代理商返佣
//		RechargeRebateMessage message = new RechargeRebateMessage();
//		message.setUserId(user.getId());
//		mqClient.userVipPush(message);
//		if (null == user.getSuperiorsId()) {
//			return;
//		}
//		message.setAgentId(user.getSuperiorsId());
//		message.setOrderNo(orderRecharge.getOrderNo());
//		mqClient.agentRebatePush(message);
    }
    // 订单充值修改用户账号金额，生成交易记录
    private void saveOrderRechargeTransactionRecord3(OrderRechargeEntity orderRecharge, int transactionType,
                                                    UserEntity user,Integer type) {
        UserTransactionRecordEntity transactionRecord = new UserTransactionRecordEntity();
        transactionRecord.setType(transactionType);
        transactionRecord.setOrderNo(orderRecharge.getOrderNo());
        transactionRecord.setDetailType(orderRecharge.getRechargeChannel().intValue());
        transactionRecord.setFristrecharge(orderRecharge.getFristrecharge());
        transactionRecord.setUserId(orderRecharge.getUserId());
        transactionRecord.setUserAccount(orderRecharge.getUserAccount());
        // 充值金额=充值金额+优惠金额
        transactionRecord.setAmount(MathUtil.getBigDecimal(orderRecharge.getAmount()));
        // 更新用户余额(本来需要更新用户金额，再额度转换，两个动作直接合成一个额度转换直接把冲的金额转成冲金币，但是交易记录两个两条)
        // 充值之后的账户余额=账户余额+充值金额
        transactionRecord.setMoney(user.getMoney());
        transactionRecord.setCommission(user.getCommission());
        transactionRecord.setRemake(orderRecharge.getRemark());
        transactionRecord.setCoin(user.getCoin() + transactionRecord.getAmount().multiply(new BigDecimal(Constant.COIN_RATE)).longValue());
       /* // 保存交易记录
        userTransactionRecordService.insert(transactionRecord);

        //额度转换交易记录--废弃 adu 2019-11-21
        UserEntity userUpdateParam = new UserEntity();
        userUpdateParam.setId(user.getId());
        userUpdateParam.setCoin(transactionRecord.getAmount().multiply(new BigDecimal(Constant.COIN_RATE)).longValue());
        userService.updateUserWalletByUserId(userUpdateParam);*/
        //start  保存充值返利账目明细
        //获取优惠金额
        BigDecimal discountAmount = orderRecharge.getDiscountAmount() == null ? BigDecimal.ZERO : orderRecharge.getDiscountAmount();
        //判断优惠金额大于零
        UserTransactionRecordEntity transactionRecord1 = new UserTransactionRecordEntity();
        if (discountAmount.compareTo(BigDecimal.ZERO) > 0) {
            //保存返利记录
            Long coin = transactionRecord.getCoin();
            transactionRecord1 = transactionRecord;
            transactionRecord1.setId(null);
            transactionRecord1.setType(3);
            transactionRecord1.setAmount(discountAmount);
            transactionRecord1.setDetailType(Integer.parseInt("50"+type));
            transactionRecord1.setCoin(coin+transactionRecord1.getAmount().multiply(new BigDecimal(Constant.COIN_RATE)).longValue());
            userTransactionRecordService.insert(transactionRecord1);
            //额度转换交易记录--废弃 adu 2019-11-21
            UserEntity userUpdateParam1 = new UserEntity();
            userUpdateParam1.setId(user.getId());
            userUpdateParam1.setCoin(transactionRecord1.getAmount().multiply(new BigDecimal(Constant.COIN_RATE)).longValue());
            userService.updateUserWalletByUserId(userUpdateParam1);
        }
        //end
        // 充值+额度转换两个动作一起做（只修改一次数据库）
//		userService.rechargeAndChanger(user, transactionRecord.getAmount(),
//				transactionRecord.getAmount().multiply(new BigDecimal(Constant.COIN_RATE)).longValue());
    }
    @Override
    @Transactional
    public void saveAdministratorRechargeV3(OrderRechargeEntity orderRecharge, int transactionType, UserEntity user
            , BigDecimal discountMultiple
            , BigDecimal rechargeMultiple,Integer type )
            throws Exception {
        //计算VIP优惠
        BigDecimal vipDiscountAmount = BigDecimal.ZERO;
        if (user != null && user.getVipId() > 0) {
            UserVipEntity userVip = userVipService.selectById(user.getVipId());
            if (null != userVip && null != userVip.getRechargeRate()) {
                vipDiscountAmount = userVip.getRechargeRate().multiply
                        (MathUtil.getBigDecimal(orderRecharge.getAmount()));
            }
        }
        orderRecharge.setDiscountAmount(vipDiscountAmount.add
                (MathUtil.getBigDecimal(orderRecharge.getDiscountAmount())));
        orderRecharge.setFinalMoney(orderRecharge.getPreMoney().add(orderRecharge.getDiscountAmount())
                .add(MathUtil.getBigDecimal(orderRecharge.getAmount())));// 存款后的余额+金币
        // 保存订单
        orderRecharge.setOrderNo(OrderNoUtil.getOrderNo());
        BigDecimal userNeedBet = orderCashExamineService.saveCashExamine(orderRecharge, user
                , discountMultiple, rechargeMultiple);
        orderRecharge.setUserNeedBet(userNeedBet);
        this.baseMapper.insert(orderRecharge);
        saveOrderRechargeTransactionRecord3(orderRecharge, transactionType, user,type);
    }
    @Override
    @Transactional
    public void saveAdministratorRechargeV2(OrderRechargeEntity orderRecharge, int transactionType, UserEntity user
            , BigDecimal discountMultiple
            , BigDecimal rechargeMultiple)
            throws Exception {
        //计算VIP优惠
        BigDecimal vipDiscountAmount = BigDecimal.ZERO;
        if (user != null && user.getVipId() > 0) {
            UserVipEntity userVip = userVipService.selectById(user.getVipId());
            if (null != userVip && null != userVip.getRechargeRate()) {
                vipDiscountAmount = userVip.getRechargeRate().multiply
                        (MathUtil.getBigDecimal(orderRecharge.getAmount()));
            }
        }
        orderRecharge.setDiscountAmount(vipDiscountAmount.add
                (MathUtil.getBigDecimal(orderRecharge.getDiscountAmount())));
        orderRecharge.setFinalMoney(orderRecharge.getPreMoney().add(orderRecharge.getDiscountAmount())
                .add(MathUtil.getBigDecimal(orderRecharge.getAmount())));// 存款后的余额+金币
        // 保存订单
        orderRecharge.setOrderNo(OrderNoUtil.getOrderNo());
        BigDecimal userNeedBet = orderCashExamineService.saveCashExamine(orderRecharge, user
                , discountMultiple, rechargeMultiple);
        orderRecharge.setUserNeedBet(userNeedBet);
        this.baseMapper.insert(orderRecharge);
        saveOrderRechargeTransactionRecord(orderRecharge, transactionType, user);
    }

    @Override
    public RechargeRebateMessage orderRechargeConfirm(OrderRechargeEntity orderRecharge) throws Exception {
        UserEntity user = userService.selectById(orderRecharge.getUserId());
        if (null == user) {
            throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
                    ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
        }
        // 充值优惠计算
        if (rechargePreferential(orderRecharge, user)) {
            // 如果是首冲,修改首冲状态
            user.setFirstRecharge(true);
            userService.updateUserFirstRecharge(user.getId());
        }
        // 创建取款稽查记录
        BigDecimal userNeedBet = orderCashExamineService.saveCashExamine(orderRecharge, user);
        orderRecharge.setUserNeedBet(userNeedBet);
        this.baseMapper.updateById(orderRecharge);
        // 添加用户交易记录
        saveOrderRechargeTransactionRecord(orderRecharge, Constant.TransactionType.RECHARGE.getValue(), user);

        // 添加代理商返佣
        RechargeRebateMessage message = new RechargeRebateMessage();
        message.setUserId(user.getId());

        if (user.getSuperiorsId() != null) {
            message.setAgentId(user.getSuperiorsId());
            message.setOrderNo(orderRecharge.getOrderNo());
        }
        return message;
    }

    // 订单充值修改用户账号金额，生成交易记录
    private void saveOrderRechargeTransactionRecord(OrderRechargeEntity orderRecharge, int transactionType,
                                                    UserEntity user) {
        UserTransactionRecordEntity transactionRecord = new UserTransactionRecordEntity();
        transactionRecord.setType(transactionType);
        transactionRecord.setOrderNo(orderRecharge.getOrderNo());
        transactionRecord.setDetailType(orderRecharge.getRechargeChannel().intValue());
        transactionRecord.setFristrecharge(orderRecharge.getFristrecharge());
        transactionRecord.setUserId(orderRecharge.getUserId());
        transactionRecord.setUserAccount(orderRecharge.getUserAccount());
        // 充值金额=充值金额+优惠金额
        transactionRecord.setAmount(MathUtil.getBigDecimal(orderRecharge.getAmount()));
        // 更新用户余额(本来需要更新用户金额，再额度转换，两个动作直接合成一个额度转换直接把冲的金额转成冲金币，但是交易记录两个两条)
        // 充值之后的账户余额=账户余额+充值金额
        transactionRecord.setMoney(user.getMoney());
        transactionRecord.setCommission(user.getCommission());
        transactionRecord.setRemake(orderRecharge.getRemark());
        transactionRecord.setCoin(user.getCoin() + transactionRecord.getAmount().multiply(new BigDecimal(Constant.COIN_RATE)).longValue());
        // 保存交易记录
        userTransactionRecordService.insert(transactionRecord);

        //额度转换交易记录--废弃 adu 2019-11-21
        UserEntity userUpdateParam = new UserEntity();
        userUpdateParam.setId(user.getId());
        userUpdateParam.setCoin(transactionRecord.getAmount().multiply(new BigDecimal(Constant.COIN_RATE)).longValue());
        userService.updateUserWalletByUserId(userUpdateParam);
        //start  保存充值返利账目明细
        //获取优惠金额
        BigDecimal discountAmount = orderRecharge.getDiscountAmount() == null ? BigDecimal.ZERO : orderRecharge.getDiscountAmount();
        //判断优惠金额大于零
        UserTransactionRecordEntity transactionRecord1 = new UserTransactionRecordEntity();
        if (discountAmount.compareTo(BigDecimal.ZERO) > 0) {
            //保存返利记录
            Long coin = transactionRecord.getCoin();
            transactionRecord1 = transactionRecord;
            transactionRecord1.setId(null);
            transactionRecord1.setType(3);
            transactionRecord1.setAmount(discountAmount);
            transactionRecord1.setDetailType(50);
            transactionRecord1.setCoin(coin+transactionRecord1.getAmount().multiply(new BigDecimal(Constant.COIN_RATE)).longValue());
            userTransactionRecordService.insert(transactionRecord1);
            //额度转换交易记录--废弃 adu 2019-11-21
            UserEntity userUpdateParam1 = new UserEntity();
            userUpdateParam1.setId(user.getId());
            userUpdateParam1.setCoin(transactionRecord1.getAmount().multiply(new BigDecimal(Constant.COIN_RATE)).longValue());
            userService.updateUserWalletByUserId(userUpdateParam1);
        }
        //end
        // 充值+额度转换两个动作一起做（只修改一次数据库）
//		userService.rechargeAndChanger(user, transactionRecord.getAmount(),
//				transactionRecord.getAmount().multiply(new BigDecimal(Constant.COIN_RATE)).longValue());
    }

    // 充值报表
    @Override
    public RechargeReport getRechargeReport(String startTime, String endTime) {
        return baseMapper.getRechargeReport(startTime, endTime);
    }

    // 充值报表
    @Override
    public List<RechargeTableReport> getRechargeTableReport(String startTime, String endTime) {
        return baseMapper.getRechargeTableReport(startTime, endTime);
    }

    // 优惠计算
    private boolean rechargePreferential(OrderRechargeEntity rechargeOrder, UserEntity user) {
        // 获取层级优惠
        List<OrderPreferentialEntity> preferentials = orderPreferentialService
                .getPreferentialsByHierarchyId(user.getHierarchyId());
        // 首冲优惠对象
        OrderPreferentialEntity firstRecharge = null;
        // 满送优惠对象
        OrderPreferentialEntity presentRecharge = null;
        for (OrderPreferentialEntity preferential : preferentials) {
            if (preferential.getFirstRecharge()) {
                firstRecharge = preferential;
            } else {
                presentRecharge = preferential;
            }
        }
        if (log.isInfoEnabled()) {
            log.info("[优惠对象] firstRecharge:{} presentRecharge：{}", firstRecharge, presentRecharge);
        }
        BigDecimal preferentialDiscountAmount = BigDecimal.ZERO;
        BigDecimal rechargeAmount = new BigDecimal(rechargeOrder.getAmount());
        if (user.getFirstRecharge()) {
            // 非首冲（不是首冲判断是否符合满送）
            if (rechargeAmount.longValue() > presentRecharge.getRechargeAmount().longValue()) {
                preferentialDiscountAmount = presentRecharge.getGiftProportion()
                        .multiply(new BigDecimal(rechargeOrder.getAmount()));
                rechargeOrder.setDiscountId(presentRecharge.getId());
            }
        } else {
            // 首冲
            preferentialDiscountAmount = firstRecharge.getGiftProportion().multiply(rechargeAmount);
            rechargeOrder.setDiscountId(firstRecharge.getId());
            rechargeOrder.setFristrecharge(true);
        }
        // VIP用户优惠
        BigDecimal vipDiscountAmount = BigDecimal.ZERO;
        UserVipEntity userVip = userVipService.selectById(user.getVipId());
        if (null != userVip && null != userVip.getRechargeRate()) {
            vipDiscountAmount = userVip.getRechargeRate().multiply(rechargeAmount);
        }
        BigDecimal totalDiscountAmount = preferentialDiscountAmount.add(vipDiscountAmount);
        if (log.isInfoEnabled()) {
            log.info("[VIP优惠] userVip:{} totalDiscountAmount:{}", firstRecharge, totalDiscountAmount);
        }
        rechargeOrder.setDiscountAmount(totalDiscountAmount);
        return !user.getFirstRecharge();
    }

    @Override
    @Transactional
    public void revokeRecharge(Long rechargeId) {
        OrderRechargeEntity orderRecharge = this.baseMapper.selectById(rechargeId);
        if (orderRecharge == null || orderRecharge.getId() == null
                || orderRecharge.getStatus() == OrderStatus.REVOKE.getValue()) {
            throw new RRException(ErrorCode.OrderErrorCode.RECHARGE_RECORD_ISNULL.getErrMsg(),
                    ErrorCode.OrderErrorCode.RECHARGE_RECORD_ISNULL.getCode());
        }
        try {
            orderCancelUpdateCashExamine(orderRecharge.getOrderNo(), orderRecharge.getUserNeedBet());
            userService.revokeMoney(orderRecharge);
            orderRecharge.setStatus(OrderStatus.REVOKE.getValue());
            this.updateById(orderRecharge);
        } catch (Exception e) {
            log.error("[充值订单撤销保存失败] revokeMoney {}", orderRecharge, e);
        }
    }

    /**
     * 取消人工充值订单，稽查订单修改重试
     *
     * @param orderRechargeOrderNo
     */
    private void orderCancelUpdateCashExamine(String orderRechargeOrderNo, BigDecimal userNeedBet) {
        int i = 0;
        while (i < 10) {
            OrderCashExamineEntity orderCashExamine = new OrderCashExamineEntity();
            orderCashExamine.setOrderNo(orderRechargeOrderNo);
            orderCashExamine = orderCashExamineDao.selectOne(orderCashExamine);
            if (orderCashExamine == null || orderCashExamine.getId() == null) {
                throw new RRException(ErrorCode.OrderErrorCode.EXAMINE_IS_NULL.getErrMsg(),
                        ErrorCode.OrderErrorCode.EXAMINE_IS_NULL.getCode());
            }
            // 是否结算（0：未审核，1：已审核）
            OrderCashExamineEntity newOrderCashExamine = new OrderCashExamineEntity();
            if (orderCashExamine.getStatus()) {// 1：已审核
                // 新的取款稽查需要扣除已经结算的 总需要打码数
                newOrderCashExamine = orderCashExamineDao.findRecentOrderCashExamine(orderCashExamine.getUserId());
                if (newOrderCashExamine == null || newOrderCashExamine.getId() == null) {
                    throw new RRException(ErrorCode.OrderErrorCode.EXAMINE_IS_NULL.getErrMsg(),
                            ErrorCode.OrderErrorCode.EXAMINE_IS_NULL.getCode());
                }
            } else {// 0：未审核
                // 需要把上一条取款稽查记录修改为未审核
                newOrderCashExamine = orderCashExamine;
            }
            newOrderCashExamine.setUserNeedBet(newOrderCashExamine.getUserNeedBet().subtract(userNeedBet));
            if (newOrderCashExamine.getUserNeedBet().compareTo(BigDecimal.ZERO) < 0) {
                newOrderCashExamine.setUserNeedBet(BigDecimal.ZERO);
            }
            if (orderCashExamineDao.updateById(newOrderCashExamine) > 0) {
                return;
            } else {
                i++;
            }
        }
        log.error("[revokeRecharge] ->orderCancelUpdateCashExamine 失败  orderRechargeOrderNo {}", orderRechargeOrderNo);
        throw new RRException("取消人工充值订单,稽查记录更新失败 ");

    }

    @Override
    public Page<UserOrderRechargeExchangeParam> rechargeExamineList(Long userId, PageParam pageParam, String startTime,
                                                                    String endTime) {
        log.info("[rechargeExamineList] userId:{} , startTime:{} , endTime:{} , pageParam:{}", userId, startTime,
                endTime, pageParam);
        Page<UserOrderRechargeExchangeParam> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造
        // page 对象
        return page.setRecords(baseMapper.rechargeExamineList(page, userId, startTime, endTime));
    }

    // 获取指定类型，指定日期充值总额
    @Override
    public int sumAmountForDate(String date, Integer rechargeType) {
        // TODO Auto-generated method stub
        return baseMapper.sumAmountForDate(date, rechargeType);
    }

    @Override
    public Map<String, Object> sumRechargeAmount(String startDate, String endDate, Integer rechargeType) {
        // TODO Auto-generated method stub
        return baseMapper.sumRechargeAmount(startDate, endDate, rechargeType);
    }

    /**
     * 根据订单ID修改订单 目前只有修改状态，修改修改什么直接在sql中添
     */
    @Override
    public boolean updateAll(OrderRechargeEntity orderRecharge) {
        // TODO Auto-generated method stub
        return baseMapper.updateAll(orderRecharge);
    }

    @Override
    public int batchLocking(OrderBankRechargeLockingEntity lockingEntity) {
        if (lockingEntity.getNum() <= 0) {
            throw new RRException("条数需大于0");
        }
        // 修改成功的订单集合
        List<OrderRechargeEntity> unconfirmedList = new ArrayList<OrderRechargeEntity>();
        while (true) {
            // 如果修改成功的订单数等于要求的数量，则跳出循环
            if (unconfirmedList.size() == lockingEntity.getNum()) {
                break;
            }
            // 获取所有状态为未确定的订单
            List<OrderRechargeEntity> list = selectList(new EntityWrapper<OrderRechargeEntity>(
                    new OrderRechargeEntity().setStatus(Constant.OrderStatus.UNCONFIRMED.getValue())
                            .setRechargeType(Constant.RechargeType.BANK.getValue())));
            // 如果没有未确认过订单，跳出循环
            if (CollectionUtil.isEmpty(list)) {
                if (unconfirmedList.size() == 0) {
                    throw new RRException("无未确定订单");
                }
                break;
            } else {
                // 随机获取到订单，并进行修改
                Random random = new Random();
                int s = random.nextInt(list.size()) % (list.size() - 0 + 1) + 0;
                OrderRechargeEntity updateRecharge = list.get(s);
                updateRecharge.setStatus(Constant.OrderStatus.LOCKING.getValue());
                updateRecharge.setSysUserAccount(lockingEntity.getSysUserAccount());
                updateRecharge.setSysUserId(lockingEntity.getSysUserId());
                boolean result = updateById(updateRecharge);
                if (result) {
                    // 如果修改成功
                    unconfirmedList.add(updateRecharge);
                }
            }

        }
        return unconfirmedList.size();
    }

    @Override
    public Map<String, Object> countRechargePreferential(BigDecimal amount, boolean isFirst, Long hierarchyId) {
        Map<String, Object> resultMap = new HashMap<>();
        Long discountId = 0L;
        BigDecimal discountAmount = BigDecimal.ZERO;
        // 获取层级优惠
        List<OrderPreferentialEntity> preferentials = orderPreferentialService
                .getPreferentialsByHierarchyId(hierarchyId);
        if (preferentials == null || preferentials.isEmpty()) {
            throw new RRException("层级优惠未设置 ");
        }
        // 优惠对象
        OrderPreferentialEntity presentRecharge = null;
        for (OrderPreferentialEntity preferential : preferentials) {
            if (preferential.getFirstRecharge() != isFirst) {
                presentRecharge = preferential;
                break;
            }
        }
        if (presentRecharge == null || presentRecharge.getId() == null) {
            throw new RRException("层级优惠未设置 ");
        }
        log.debug("[优惠对象] presentRecharge：{}", presentRecharge);
        if (isFirst) {// 非首冲（不是首冲判断是否符合满送）
            if (amount.longValue() >= presentRecharge.getRechargeAmount().longValue()) {
                discountAmount = presentRecharge.getGiftProportion().multiply(amount);
                discountId = presentRecharge.getId();
            }
        } else {// 首冲
            discountAmount = presentRecharge.getGiftProportion().multiply(amount);
            discountId = presentRecharge.getId();
        }
        resultMap.put("discountAmount", discountAmount);
        resultMap.put("discountId", discountId);
        return resultMap;

    }

    @Override
    public Map<String, Object> countRechargePreferential(String account, BigDecimal amount, boolean isFirst,
                                                         Long hierarchyId) {
        Map<String, Object> resultMap = countRechargePreferential(amount, isFirst, hierarchyId);
        UserEntity user = userService.selectOne(new EntityWrapper<UserEntity>(new UserEntity().setAccount(account)));
        BigDecimal vipDiscountAmount = BigDecimal.ZERO;
        if (user != null && user.getVipId() > 0) {
            UserVipEntity userVip = userVipService.selectById(user.getVipId());
            if (null != userVip && null != userVip.getRechargeRate()) {
                vipDiscountAmount = userVip.getRechargeRate().multiply(amount);
            }
        }
        resultMap.put("vipDiscountAmount", vipDiscountAmount);
        return resultMap;
    }

    @Override
    public OrderRechargeStatisticsThree getLastRechargeAmountByUserId(Long userId) {
        return baseMapper.getLastRechargeAmountByUserId(userId);
    }

    @Override
    public OrderRechargeStatisticsTwo orderRechargeNum(Long userId) {
        return baseMapper.orderRechargeNum(userId);
    }

    @Override
    public List<Map<String, Object>> selectListOrderRecharge(OrderRechargeEntity orderRecharge) {
        return baseMapper.selectListOrderRecharge(orderRecharge);
    }

    @Override
    public List<OrderRechargeEntity> depositRecord(UserParamFour user) {
        return baseMapper.depositRecord(user);
    }

}
