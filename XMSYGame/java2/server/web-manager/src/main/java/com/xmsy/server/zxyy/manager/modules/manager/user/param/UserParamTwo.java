package com.xmsy.server.zxyy.manager.modules.manager.user.param;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 会员列表查询参数
 * 
 * @author lpp
 * @email xxxxx
 * @date 2019-02-16 14:04:27
 */
@Data
public class UserParamTwo  {
	

	/**
	 * 银行卡号
	 */
	private String bankCard;
	
	/**
	 * 房卡
	 */
	private Long roomCard;
	
	
	/**
	 * 金钱
	 */
	private BigDecimal money;
	/**
	 * 金币
	 */
	private Long coin;
	
	/**
	 * id
	 */
	private Long id;
	
	/**
	 * 账号
	 */
    private String account;

    
    private String userName;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    
	}
