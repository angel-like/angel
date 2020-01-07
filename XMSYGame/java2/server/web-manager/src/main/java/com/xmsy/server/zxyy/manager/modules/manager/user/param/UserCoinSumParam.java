package com.xmsy.server.zxyy.manager.modules.manager.user.param;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;


/**
 * 用户信息表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
@Data
public class UserCoinSumParam {
	/**
	 * 下注金额
	 */
    private Long betCoins;
	/**
	 * 输赢金额
	 */
    private Long prizeCoins;
	
    /**
     * 时间
     */
    @JsonFormat(pattern="yyyy-MM-dd")
   	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createTime;

}