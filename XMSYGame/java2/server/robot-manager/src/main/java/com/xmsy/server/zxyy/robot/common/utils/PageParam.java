package com.xmsy.server.zxyy.robot.common.utils;

import java.util.Date;

import lombok.Data;

@Data
public class PageParam {
    private int page;
    private int limit;
    private Date startTime;
    private Date endTime;
    
}
