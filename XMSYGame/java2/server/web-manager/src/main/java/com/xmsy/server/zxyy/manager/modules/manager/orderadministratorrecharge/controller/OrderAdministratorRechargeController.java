package com.xmsy.server.zxyy.manager.modules.manager.orderadministratorrecharge.controller;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xmsy.server.zxyy.manager.modules.manager.ordercashexamine.entity.OrderCashExamineEntity;
import com.xmsy.server.zxyy.manager.modules.manager.ordercashexamine.service.OrderCashExamineService;
import com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.service.SysDictionaryService;
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
import com.xmsy.common.bean.message.FortuneMessage;
import com.xmsy.common.bean.message.RechargeRebateMessage;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.annotation.AdminRechargeOrderCancelRepeat;
import com.xmsy.server.zxyy.manager.common.annotation.AdminRechargeOrderRepeat;
import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.annotation.UserLog;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.Constant;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.ExportCSVUtils;
import com.xmsy.server.zxyy.manager.common.utils.MathUtil;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.modules.manager.orderadministratorrecharge.entity.OrderAdministratorRechargeEntity;
import com.xmsy.server.zxyy.manager.modules.manager.orderadministratorrecharge.param.AdminRechargeParam;
import com.xmsy.server.zxyy.manager.modules.manager.orderadministratorrecharge.service.OrderAdministratorRechargeService;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.service.OrderRechargeService;
import com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.entity.OrderTakeMoneyEntity;
import com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.service.OrderTakeMoneyService;
import com.xmsy.server.zxyy.manager.modules.manager.sys.controller.AbstractController;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.SysUserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.modules.manager.userhierarchy.entity.UserHierarchyEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userhierarchy.service.UserHierarchyService;
import com.xmsy.server.zxyy.manager.mq.MqClient;
import com.xmsy.server.zxyy.manager.utils.IpUtil;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.CharsetUtil;

/**
 * 人工充值
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-17 16:48:50
 */
@RestController
@RequestMapping("orderadministratorrecharge/orderadministratorrecharge")
public class OrderAdministratorRechargeController extends AbstractController {
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
    @Autowired
    private MqClient mqClient;
    @Autowired
    private SysDictionaryService sysDictionaryService;
    @Autowired
   private        OrderCashExamineService orderCashExamineService;

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
                entity).ge(!StringUtils.isEmpty(entity.getStartTime()), "create_time", // 存款时间查询
                entity.getStartTime())
                .le(!StringUtils.isEmpty(entity.getEndTime()), "create_time",
                        entity.getEndTime())
                .ge(null != entity.getAmountMin() && 0 < entity.getAmountMin(), "amount", // 存款金额查询
                        entity.getAmountMin())
                .le(null != entity.getAmountMax() && 0 < entity.getAmountMax(), "amount",
                        entity.getAmountMax())
                .orderBy(false, "create_time");
        entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
        entity.selectPage(result, entityWrapper);
        List<OrderAdministratorRechargeEntity> list = result.getRecords();
        if (!CollectionUtil.isEmpty(list)) {
            for (OrderAdministratorRechargeEntity adminEntity : list) {
                if (adminEntity.getHierarchyId() != null) {
                    UserHierarchyEntity userHierarchy = userHierarchyService.selectById(adminEntity.getHierarchyId());
                    if (userHierarchy != null) {
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
                entity).ge(!StringUtils.isEmpty(entity.getStartTime()), "create_time", // 存款时间查询
                entity.getStartTime())
                .le(!StringUtils.isEmpty(entity.getEndTime()), "create_time",
                        entity.getEndTime())
                .ge(null != entity.getAmountMin() && 0 < entity.getAmountMin(), "amount", // 存款金额查询
                        entity.getAmountMin())
                .le(null != entity.getAmountMax() && 0 < entity.getAmountMax(), "amount",
                        entity.getAmountMax());
        entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
        entity.selectPage(result, entityWrapper);

        List<OrderAdministratorRechargeEntity> list = result.getRecords();
        if (!CollectionUtil.isEmpty(list)) {
            for (OrderAdministratorRechargeEntity adminEntity : list) {
                //查充值类型的lable
                Integer type = adminEntity.getRechargeType()==null?0:adminEntity.getRechargeType();
                String name = sysDictionaryService.getName("rechargeType", type.toString());
                adminEntity.setRechargeTypeName(name);
                //end
                if (adminEntity.getOperationType() == SysConstant.ADMIN_OPERATION_TYPE_0) {
                    OrderRechargeEntity orderEntity = new OrderRechargeEntity();
                    orderEntity.setAdminOrderNo(adminEntity.getOrderNo());
                    Wrapper<OrderRechargeEntity> entityWrapper1 = new EntityWrapper<OrderRechargeEntity>(orderEntity);
                    List<OrderRechargeEntity> dlist = orderRechargeService.selectList(entityWrapper1);
                    if (!CollectionUtil.isEmpty(dlist)) {
                        adminEntity.setOrderStatus(dlist.get(0).getStatus());
                    }
                } else {
                    OrderTakeMoneyEntity orderEntity = new OrderTakeMoneyEntity();
                    orderEntity.setAdminOrderNo(adminEntity.getOrderNo());
                    Wrapper<OrderTakeMoneyEntity> entityWrapper2 = new EntityWrapper<OrderTakeMoneyEntity>(orderEntity);
                    List<OrderTakeMoneyEntity> dlist = orderTakeMoneyService.selectList(entityWrapper2);
                    if (!CollectionUtil.isEmpty(dlist)) {
                        adminEntity.setOrderStatus(dlist.get(0).getStatus());
                    }
                }

            }
        }

        Map<String ,Object> map =orderAdministratorRechargeService.getCount(entity);
        return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
                result.getCurrent(), result.getPages())).put("total",map);
    }

    /**
     * 人工充值的订单下给所有充值人的交易订单列表
     */
    @RequestMapping("/getRechargeList")
    @RequiresPermissions("orderadministratorrecharge:orderadministratorrecharge:orderDetail")
    public R getRechargeList(@RequestParam(value = "adminOrderNo") String adminOrderNo,
                             @RequestParam(required = false, value = "userAccount") String userAccount,
                             @RequestParam(value = "operationType") int operationType) {
        if (operationType == SysConstant.ADMIN_OPERATION_TYPE_0) {
            OrderRechargeEntity orderEntity = new OrderRechargeEntity();
            orderEntity.setAdminOrderNo(adminOrderNo);
            if (!StringUtils.isEmpty(userAccount)) {
                orderEntity.setUserAccount(userAccount);
            }
            Wrapper<OrderRechargeEntity> entityWrapper = new EntityWrapper<OrderRechargeEntity>(orderEntity);
            return R.ok().put("rechargeList", orderRechargeService.selectList(entityWrapper));
        } else {
            OrderTakeMoneyEntity orderEntity = new OrderTakeMoneyEntity();
            orderEntity.setAdminOrderNo(adminOrderNo);
            if (!StringUtils.isEmpty(userAccount)) {
                orderEntity.setUserAccount(userAccount);
            }
            Wrapper<OrderTakeMoneyEntity> entityWrapper = new EntityWrapper<OrderTakeMoneyEntity>(orderEntity);
            return R.ok().put("rechargeList", orderTakeMoneyService.selectList(entityWrapper));
        }

    }

    /**
     * 撤销
     *
     * @throws Exception
     */
    @AdminRechargeOrderCancelRepeat("人工充值订单撤销校验")
    @UserLog(value = "人工充值订单撤销")
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
    @UserLog(value = "创建人工充值")
    @RequestMapping("/create")
    @RequiresPermissions("orderadministratorrecharge:orderadministratorrecharge:create")
    public R create(@RequestBody AdminRechargeParam adminRecharge, HttpServletRequest request) {
        SysUserEntity sysUser = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        // 设置IP地址
        String ip = IpUtil.getIPAddress(request);
        List<RechargeRebateMessage> rrmessageList = orderAdministratorRechargeService.createAdminRecharge(adminRecharge, ip, sysUser.getUserId(),
                sysUser.getUsername());
        for (RechargeRebateMessage rrm : rrmessageList) {
            mqClient.userVipPush(rrm);
            if (null != rrm.getAgentId()) {
                mqClient.agentRebatePush(rrm);
            }
        }
        return R.ok();
    }

    /**
     * 创建
     */
    @AdminRechargeOrderRepeat("创建人工充值校验")
    @UserLog(value = "创建人工存取款")
    @RequestMapping("/accessMoney")
    @RequiresPermissions("orderadministratorrecharge:orderadministratorrecharge:accessMoney")
    public R accessMoney(@RequestBody AdminRechargeParam adminRecharge, HttpServletRequest request) {
        SysUserEntity sysUser = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        // 设置IP地址
        String ip = IpUtil.getIPAddress(request);
        if (adminRecharge.getOperationType() == SysConstant.ADMIN_OPERATION_TYPE_0) {//存款
            RechargeRebateMessage rechargeRebateMessage = orderAdministratorRechargeService.createAdminRechargeV2(adminRecharge, ip, sysUser.getUserId(),
                    sysUser.getUsername());
            mqClient.userVipPush(rechargeRebateMessage);
            if (null != rechargeRebateMessage.getAgentId()) {
                mqClient.agentRebatePush(rechargeRebateMessage);
            }
            //天降财神充值得红包(1.人工充值)
            if(adminRecharge.getAmount()>0) {//只计算充值的 优惠不算
    			FortuneMessage fortuneMessage=new FortuneMessage();
    			fortuneMessage.setUserId(rechargeRebateMessage.getUserId());
    			fortuneMessage.setRechargeAmount(adminRecharge.getAmount());
    			fortuneMessage.setEventCode(SysConstant.FORTUNE_EVENT_RECHARGE);//充值事件 
    			mqClient.fortunePush(fortuneMessage);
            }
        } else {//取款
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
    public R getUser(@RequestParam(value = "account") String account) {
        UserEntity user = new UserEntity();
        user.setAccount(account);
        user = userService.selectOne(new EntityWrapper<UserEntity>(user));
        if (user == null || user.getId() == null) {
            throw new RRException(
                    ErrorCode.UserErrorCode.USER_ACCOUNT_INVALID.getErrMsg(),
                    ErrorCode.UserErrorCode.USER_ACCOUNT_INVALID.getCode());
        }
        UserHierarchyEntity userHierarchy = userHierarchyService.selectById(user.getHierarchyId());

        OrderCashExamineEntity orderCashExamineEntity = orderCashExamineService.selectOne(new EntityWrapper<>(new OrderCashExamineEntity().setUserId(user.getId()).setStatus(false)));
        BigDecimal totalMoney = MathUtil.getBigDecimal(user.getMoney());
        totalMoney = MathUtil.getBigDecimal(user.getCoin()).divide(new BigDecimal(Constant.COIN_RATE), 2, BigDecimal.ROUND_HALF_UP)
                .add(totalMoney);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("money", user.getMoney());
        data.put("userName", user.getUserName());
        data.put("coin", user.getCoin());
        data.put("hierarchyId", user.getHierarchyId());
        data.put("totalMoney", totalMoney);
        data.put("discountMultiple", userHierarchy.getDiscountMultiple());
        data.put("rechargeMultiple", userHierarchy.getRechargeMultiple());
        data.put("hierarchyName", userHierarchy.getName());
        data.put("userId", user.getId());
        BigDecimal userValidBet = BigDecimal.ZERO;
        BigDecimal userNeedBet = BigDecimal.ZERO;
        if(orderCashExamineEntity!=null &&
        		orderCashExamineEntity.getUserNeedBet()!=null && orderCashExamineEntity.getUserNeedBet().compareTo(BigDecimal.ZERO)>0) {
            userNeedBet = orderCashExamineEntity.getUserNeedBet();
            Long s = orderCashExamineService.getUserValidBet(user.getId(), orderCashExamineEntity.getUpdateTime());
            BigDecimal userValidBetTmp =MathUtil.getBigDecimal(s);
            orderCashExamineService.updateCashBetAndTime(user.getId(),userValidBetTmp,new Date());
            userValidBet= userValidBetTmp.add(orderCashExamineEntity.getUserValidBet());
        }
        data.put("userValidBet",userValidBet);
        data.put("userNeedBet",userNeedBet);
        return R.ok().put("data", data);
    }

    /**
     * 获取会员
     */
    @RequestMapping("/getDiscount")
    @RequiresPermissions("orderadministratorrecharge:orderadministratorrecharge:getUser")
    public R getDiscount(@RequestParam(value = "hierarchyId") Long hierarchyId,
                         @RequestParam(value = "userAccount") String userAccount,
                         @RequestParam(value = "amount") BigDecimal amount) {
        return R.ok().put("data", orderRechargeService.
                countRechargePreferential(userAccount, amount, true, hierarchyId));
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

    /**
     * 导出人工存取款csv的数据
     */
    @SysLog("导出csv的数据")
    @RequestMapping("/exportCSVData")
    @RequiresPermissions("orderadministratorrecharge:orderadministratorrecharge:list")
    public void exportCSVData(OrderAdministratorRechargeEntity orderAdministratorRecharge, HttpServletResponse response) {
        List<Map<String, Object>> list = orderAdministratorRechargeService.selectListRecharge(orderAdministratorRecharge);
        for (Map<String, Object> map : list) {
            map.put("status", "订单完成");
        }
        String fileName = "user_" + getUserId() + "_" + System.currentTimeMillis() + ".csv";
        String path = ExportCSVUtils.getDownloadFile(fileName);
        CsvWriter writer = CsvUtil.getWriter(path, CharsetUtil.CHARSET_GBK);
        // 按行写出
        writer.write(getCommissionHeadRowStr()); // 头信息
        for (Map<String, Object> map : list) {
            writer.write(getCommissionRowStr(map)); // 行信息
        }
        writer.close();
        ExportCSVUtils.exportCSVData(response, fileName, path);
    }

    // 获取行信息
    private String[] getCommissionRowStr(Map<String, Object> map) {
        String[] rowData = new String[14];
        rowData[0] = "订单号" + ExportCSVUtils.getString(map.get("orderNo"));
        rowData[1] = ExportCSVUtils.getString(map.get("sysUserAccount"));
        rowData[2] = ExportCSVUtils.getString(map.get("sysUserId"));
        rowData[3] = ExportCSVUtils.getString(map.get("account"));
        rowData[4] = ExportCSVUtils.getString(map.get("operationType"));
        rowData[5] = ExportCSVUtils.getString(map.get("amount"));
        rowData[6] = ExportCSVUtils.getString(map.get("discountAmount"));
        rowData[7] = ExportCSVUtils.getString(map.get("vipDiscount"));
        rowData[8] = ExportCSVUtils.getString(map.get("rechargeMultiple"));
        rowData[9] = ExportCSVUtils.getString(map.get("discountMultiple"));
        rowData[10] = ExportCSVUtils.getString(map.get("rechargeTypeName"));
        rowData[11] = ExportCSVUtils.getString(map.get("status"));
        rowData[12] = ExportCSVUtils.getString(map.get("remake"));
        rowData[13] ="\t"+ ExportCSVUtils.getString(map.get("createTime"));
        return rowData;
    }

    // 获取头信息
    private String[] getCommissionHeadRowStr() {
        String[] rowData = new String[14];
        rowData[0] = "订单号";
        rowData[1] = "操作人用户名";
        rowData[2] = "操作人id";
        rowData[3] = "会员账号";
        rowData[4] = "操作类型";
        rowData[5] = "操作金额";
        rowData[6] = "优惠金额(元)";
        rowData[7] = "VIP优惠金额(元)";
        rowData[8] = "存款打码倍数";
        rowData[9] = "优惠金额打码倍数";
        rowData[10] = "充值类型";
        rowData[11] = "订单状态";
        rowData[12] = "订单备注";
        rowData[13] = "操作时间";
        return rowData;
    }

}
