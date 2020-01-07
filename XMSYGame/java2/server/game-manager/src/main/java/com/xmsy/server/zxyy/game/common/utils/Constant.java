package com.xmsy.server.zxyy.game.common.utils;

/**
 * 常量
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年11月15日 下午1:23:52
 */
public class Constant {
	/** 中奖站内信标题 */
	public static final String WINNING_MAIL_TITLE = "中奖通知:恭喜您获得%s";
	/** 中奖站内信内容 */
	public static final String WINNING_MAIL_CONTENT = "恭喜您于%s获得%s,金币%g";
	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;
	
	/** 超级管理员角色ID */
	public static final Long SUPER_ROLE = 1L;
	/** 财务角色ID */
	public static final Long FINANCE_ROLE = 2L;
	/** 代理商角色ID */
	public static final Long AGENCY_ROLE = 3L;
	/** 普通角色ID */
	public static final Long COMMON_ROLE = 4L;

	/**
	 * 菜单类型
	 * 
	 * @author aleng
	 * @email xxxxxx
	 * @date 2016年11月15日 下午1:24:29
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     * 定时任务状态
     * 
     * @author aleng
     * @email xxxxxx
     * @date 2016年12月3日 上午12:07:22
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
    	NORMAL(0),
        /**
         * 暂停
         */
    	PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     *充值类型
     */
    public enum RechargeType {
        /**
         * 银行卡充值
         */
    	BANK("BANK"),
        /**
         * 第三方充值
         */
        THIRD("THIRD"),
        /**
         * 人工充值
         */
        ADMIN("ADMIN");
    	

        private String value;

        RechargeType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    
    /**
     *订单状态
     */
    public enum OrderStatus {
        /**
         * 未确认
         */
    	UNCONFIRMED(0),
        /**
         * 审核失败
         */
    	AUDITFAILURE(1),
        /**
         * 订单完成
         */
    	COMPLETE(2);

        private int value;

        OrderStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     *交易类型
     */
    public enum TransactionType {
        /**
         * 存款
         */
    	RECHARGE(0),
        /**
         * 取款
         */
    	TAKE(1),
        /**
         * 冲销
         */
    	WRITEOFF(2),
        /**
         * 返利
         */
    	REBATE(3),
        /**
         * 派奖
         */
    	AWARD(4);

        private int value;

        TransactionType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     *交易具体类型
     */
    public enum TransactionDetailType{
    	/**
    	 * 银行充值
    	 */
    	BANKRECHARGE(11),
    	/**
    	 * 支付宝充值
    	 */
    	ALIPAYRECHARGE(12),
    	/**
    	 * 微信充值
    	 */
    	 WECHATRECHARGE(13),
    	/**
    	 * 人工充值
    	 */
    	 ARTIFICIALRECHARGE(14),
    	/**
    	 * 佣金取款
    	 */
    	 COMMISSIONTAKE(15),
    	/**
    	 * 账户取款
    	 */
    	 ACCOUNTTAKE(16),
    	/**
    	 * 额度转换
    	 */
    	QUOTACONVERSION(17),
    	/**
    	 * 签到返利
    	 */
    	CHECKINREBATE(18),
    	/**
    	 * 推荐返利
    	 */
    	RECOMMENDEDREBATE(19),
    	/**
    	 * 奖池派奖
    	 */
    	PRIZEPOOLAWARD(20);
    	private int value;
    	
    	TransactionDetailType(int value) {
    		this.value = value;
    	}
    	
    	public int getValue() {
    		return value;
    	}
    }
    
    /**
     *游戏配置属性名称
     */
    public enum GameConfigName {
        /**
         * 初始库存
         */
    	INITIALSTOCK("initialStock"),
        /**
         * 目标库存
         */
    	GOALSTOCK("goalStock"),
        /**
         * 游戏胜率
         */
    	GAMERATE("gameRate"),
    	 /**
         * 层级游戏胜率
         */
    	HIERARCHYGAMERATE("hierarchyGameRate"),
    	  /**
         * 区间游戏胜率
         */
    	INTERVALGAMERATE("intervalGameRate"),
        /**
         * 小游戏胜率
         */
    	MINIRATE("miniRate"),
    	/**
         * 盈利值
         */
    	PROFITVALUE("profitValue"),
    	/**
         * 限红最大值
         */
    	LIMITREDMAX("limitRedMax"),
    	/**
         * vip限红
         */
    	VIPLIMITRED("vipLimitRed"),
    	/**
         * 最小携带筹码值
         */
    	ROOMMIN("roomMin"),
    	/**
         * 最大携带筹码值
         */
    	ROOMMAX("roomMax"),
    	/**
         * 筹码最小值
         */
    	CHIPMIN("chipMin"),
    	/**
         * 筹码最大值
         */
    	CHIPMAX("chipMax"),
    	/**
         * 10个筹码值 
         */
    	TENCHIPS("tenChips"),
    	/**
         * 不同下注区域 
         */
    	DIFFERENTBETAREA("differentBetArea"),
    	 /**
         * 当前库存
         */
    	CURRENTSTOCK("currentStock"),
    	 /**
         * 累计库存
         */
    	CUMULATIVESTOCK("cumulativeStock"),
    	 /**
         * 最多机器人数量
         */
    	MAXROBOT("maxRobot"),
    	 /**
         * 机器人等待时间
         */
    	ROBOTWAIT("robotWait"),
        /**
         * 个体奖池抽点率
         */
    	OWNWATERRATE("ownWaterRate"),
        /**
         * 总奖池抽点率
         */
    	GLOBALWATERRATE("globalWaterRate"),
        /**
         * 个体奖池爆奖阈值
         */
    	OWNPOOLTHRESHOLD("ownPoolThreshold"),
        /**
         * 总奖池爆奖阈值
         */
    	GLOBALPOOLTHRESHOLD("globalPoolThreshold");
    	

        private String value;

        GameConfigName(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
