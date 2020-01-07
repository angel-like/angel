package com.xmsy.server.zxyy.webhome.modules.manager.giftbagconfig.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 兑换码配置表
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-07-15 10:13:06
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("gift_bag_config")
public class GiftBagConfigEntity extends BaseEntity<GiftBagConfigEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 兑换额度
	 */
	private BigDecimal acountMoney;
	/**
	 * 兑换码
	 */
	private String exchangeCode;
	/**
	 * 打码量(默认为1)
	 */
	private Boolean codeCapacity;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 兑换码状态(0:已关闭，1：使用中)
	 */
	private Boolean exchange;
	/**
	 * 总兑换次数
	 */
	private Integer exchangeTotalNum;
	/**
	 * 兑换次数
	 */
	private Integer exchangeNum;
	/**
	 * 指定兑换者账号（不填写则为不限制）
	 */
	private String exchangeAccount;
	/**
	 * 生效时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 单个用户限制次数（0:单次，1:多次）
	 */
	private Boolean limitTimesUser;
	/**
	 * 周期(天)
	 */
	private Integer period;
	/**
	 * 可以领取次数(每天)
	 */
	private Integer receiveTimes;
	}
