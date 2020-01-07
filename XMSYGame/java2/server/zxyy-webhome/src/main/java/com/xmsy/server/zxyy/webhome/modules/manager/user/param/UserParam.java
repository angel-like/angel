package com.xmsy.server.zxyy.webhome.modules.manager.user.param;

import lombok.Data;

/**
 * 会员列表查询参数
 * 
 * @author lpp
 * @email xxxxx
 * @date 2019-02-16 14:04:27
 */
@Data
public class UserParam  {
	/**
	 * 账号
	 */
    private String account;
    /**
	 * 上级账号
	 */
    private String superiorsAccount;
	/**
	 * 
	 */
    private String status;
    
    private String startTime;
    
    private String endTime;
    
    private String userName;
    
    private String bankCard;
    
    private Long hierarchyId;
    
    private Long riskHierarchyId;
    
    private String registerIp;
    
    private String  loginIp;
    
    private String deviceCode;
    
    private Long superiorsId;
    
    private String deviceType;
    
    private String userType;
    
    private Integer  trial;
    
    private Boolean forbiddenEnable;
    
    private Boolean nobetEnable; 
    
    private Boolean frozenEnable;
    
    private String sortOption; 
    
    private String orientation;
    
    private Boolean abnormalEnable;
    
    
	}
