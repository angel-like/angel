package com.xmsy.server.zxyy.manager.modules.manager.rechargechannel.entity;

import lombok.Data;

/**
 * 支付渠道
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-27 10:16:53
 */
@Data
public class RechargeChannelResultEntity {

    /**
     * id
     */
    private Long id;
    /**
     * 支付名称
     */
    private String name;

    private String iconUrl;
    /**
     * MD5
     */
    private String iconMd5;
    /**
     * 最高限制
     */
    private Long highLimit;
    /**
     * 最低限制
     */
    private Long lowLimit;
    /**
     * 最低限制
     */
    private String alias;
    /**
     * 充值金额
     */
    private String amount;
    /**
     * 充值金额
     */
    private String hierarchyId;
    /**
     * 支付配置id
     */
    private Long pccId;
    private String showName;
    private String orderNo;
    /**
     * 状态(1.启用，0.禁用)
     */
    private Boolean enable;
}
