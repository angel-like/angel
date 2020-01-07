package com.xmsy.common.bean.message;

/**
 * 注册消息
 * 
 * @author Administrator
 *
 */
public class RechargeRebateMessage extends BaseMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 代理商id
	 */
	private Long agentId;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 订单号
	 */
	private String orderNo;

	public RechargeRebateMessage() {
		super();
	}


	public RechargeRebateMessage(Long userId, Long agentId,String orderNo) {
		super();
		this.userId = userId;
		this.agentId = agentId;
		this.orderNo = orderNo;

	}


	public Long getAgentId() {
		return agentId;
	}


	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getOrderNo() {
		return orderNo;
	}


	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	


}
