package com.xmsy.server.zxyy.webhome.modules.manager.pooldispatchdetaillist.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 派奖明细记录表
 * 
 * @author lpp
 * @email xxxxx
 * @date 2019-01-21 09:46:00
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("pool_dispatch_detail_list")
public class PoolDispatchDetailListEntity extends BaseEntity<PoolDispatchDetailListEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
    private Long userId;
    /**
	 * 用户账号
	 */
    private String userAccount;
    /**
   	 * 订单号
   	 */
     private String OrderNo;
			/**
	 * 任务id
	 */
    private Long taskId;
			/**
	 * 派奖任务标题
	 */
    private String taskTitle;
			/**
	 * 派奖奖项id
	 */
    private Long taskDetailId;
			/**
	 * 派奖明细标题
	 */
    private String detailTitle;
			/**
	 * 金额
	 */
    private BigDecimal amount;
			/**
	 * 派发时间
	 */
    private Date dispatchTime;
					}
