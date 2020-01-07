package com.xmsy.server.zxyy.webhome.modules.manager.ordertakemoney.entity;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 取款记录表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-28 15:26:30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order_take_money")
public class OrderTakeMoneyEntity extends BaseEntity<OrderTakeMoneyEntity> {
    private static final long serialVersionUID = 1L;
    /**
     * 取款用户id
     */
    private Long userId;
    /**
     * 取款用户账号
     */
    private String userAccount;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 人工取款主订单号
     */
    private String adminOrderNo;

    /**
     * 取款账号
     */
    private String account;
    /**
     * 取款金额
     */
    @Min(value = 10, message = "最小取款金额10元")
    @Max(value = 100000000, message = "最大取款金额1000万元")
    @NotNull(message = "取款金额不能为空")
    private Long takeAmount;
    /**
     * 总需要打码数(等级正常打码数+等级优惠打码数-等级放宽打码)
     */
    private BigDecimal userNeedBet;
    /**
     * 当前用户有效打码
     */
    private BigDecimal userValidBet;
    /**
     * 手续费
     */
    private BigDecimal poundage;
    /**
     * 用户账户余额
     */
    private Long userSurplusCoin;
    /**
     * 实际得到的金额
     */
    private BigDecimal obtainAmount;
    /**
     * 收入账号
     */
    private String incomeNo;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 开户人
     */
    private String accountName;
    /**
     * 状态(0:未审核，1：失败，2.完成)
     */
    private Integer status;
    /**
     * 取款审核ID
     */
    private Long orderExamineId;
    /**
     * 取款账号类型(1:银行转账，2：支付宝转账，3：其它)
     */
    @NotNull(message = "取款类型不能为空")
    private Integer accountType;
    /**
     * 操作管理员id
     */
    private Long sysUserId;
    /**
     * 操作用户账号
     */
    private String sysUserAccount;
    /**
     * 备注
     */
    private String remark;
    /**
     * 0:余额取现，1：佣金取现
     */
    private Integer type;
    /**
     * 是否取消下注
     */
    private Boolean betCancel;
    /**
     * 是否GM会员
     */
    private Boolean gmUser;

    /**
     * 用户取款密码（）
     */
    @NotEmpty(message = "取款密码不能为空")
    @TableField(exist = false)
    private String password;


    //=====================================查询字段

    /**
     * 查询时间
     */
    @TableField(exist = false)
    private String queryTime;
    /**
     * 充值最小金额
     */
    @TableField(exist = false)
    private Integer amountMin;
    /**
     * 充值最大金额
     */
    @TableField(exist = false)
    private Integer amountMax;
    /**
     * 用户入款银行模糊查询
     */
    @TableField(exist = false)
    private String userBankName;

    /**
     * 手续费最小金额
     */
    @TableField(exist = false)
    private BigDecimal poundageMin;
    /**
     * 手续费最大金额
     */
    @TableField(exist = false)
    private BigDecimal poundageMax;
    /**
     * 入款账号
     */
    @TableField(exist = false)
    private String userIncomeNo;
    /**
     * 排序字段
     */
    @TableField(exist = false)
    private String term;
    /**
     * 升/降序
     */
    @TableField(exist = false)
    private Boolean enable;

    public BigDecimal getUserNeedBet() {
        return userNeedBet = userNeedBet == null ? new BigDecimal(0) : userNeedBet;
    }

    public void setUserNeedBet(BigDecimal userNeedBet) {
        this.userNeedBet = userNeedBet == null ? new BigDecimal(0) : userNeedBet;
    }

    public BigDecimal getUserValidBet() {
        return userValidBet = userValidBet == null ? new BigDecimal(0) : userValidBet;
    }

    public void setUserValidBet(BigDecimal userValidBet) {
        this.userValidBet = userValidBet == null ? new BigDecimal(0) : userValidBet;
    }
//	/**
//	 * 取款需要打码
//	 */
//	@TableField(exist = false)
//	private BigDecimal userNeedBet;
//	/**
//	 * 用户有效打码
//	 */
//	@TableField(exist = false)
//	private BigDecimal userValidBet;

}
