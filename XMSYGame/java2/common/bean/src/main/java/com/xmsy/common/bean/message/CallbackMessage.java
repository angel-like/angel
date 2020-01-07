package com.xmsy.common.bean.message;

/**
 * .支付回调参数封装
 * 
 * @author aleng
 * @date 2018年11月2日
 * @version 1.0
 */
public class CallbackMessage extends BaseMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 支付平台订单号
	private String merchantNo;
	// 金额
	private Integer amount;
	// 接入方在接入时上传的参数
	private String attach;
	// 接入方的商户编号
	private String appId;
	// 我方平台订单号
	private String orderNo;
	// 商品名称
	private String goods;
	// 商品价格
	private String goodsPrice;
	// 商品数量
	private String goodsNum;
	// 签名
	private String sign;
	// 状态：0：未支付 1：成功
	private Integer status;

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public String getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(String goodsNum) {
		this.goodsNum = goodsNum;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CallbackMessage [merchantNo=" + merchantNo + ", amount=" + amount + ", attach=" + attach + ", appId="
				+ appId + ", orderNo=" + orderNo + ", goods=" + goods + ", goodsPrice=" + goodsPrice + ", goodsNum="
				+ goodsNum + ", sign=" + sign + ", status=" + status + ", messageId=" + messageId + ", createTime="
				+ createTime + ", messageContent=" + messageContent + "]";
	}
}
