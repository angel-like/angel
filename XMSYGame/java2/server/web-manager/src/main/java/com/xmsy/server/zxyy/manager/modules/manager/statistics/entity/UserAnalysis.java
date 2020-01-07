package com.xmsy.server.zxyy.manager.modules.manager.statistics.entity;



import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 会员分析参数实体类
 * @author axiong
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
public class UserAnalysis {
	
    private String startTime;
    
    private String endTime;
}
