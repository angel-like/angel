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
public class UserParamThree  {
	/**
	 * id
	 */
    private Long id;
	/**
	 * 账号
	 */
    private String account;
  
    private String startTime;
    
    private String endTime;
    
    private String userName;
    
    private String hierarchyName;
    
    private String  loginIp;
    
    private String deviceCode;
    
    private String deviceType;
    
    private String userType;
    
    private Integer  trial;
    
    private Boolean forbiddenEnable;
    
    private Boolean nobetEnable; 
    
    private Boolean frozenEnable;
    
    private String sortOption; 
    
    private String orientation;
    
    private Boolean abnormalEnable;
    
    private Date createTime;
    
    private Date loginTime;
    
    private Date lastLoginTime;
    
    private String lastLoginIp;
    
    private BigDecimal money;
    
    /**
	 * 总需要打码数(等级正常打码数+等级优惠打码数-等级放宽打码)
	 */
	private BigDecimal userNeedBet;
	/**
	 * 当前用户有效打码
	 */
	private BigDecimal userValidBet;
	
    
	}
