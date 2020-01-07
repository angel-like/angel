/*
 * package com.xmsy.server.zxyy.manager.modules.web.recharge.callback;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Service; import
 * org.springframework.transaction.annotation.Transactional;
 * 
 * import com.xmsy.common.bean.message.CallbackMessage; import
 * com.xmsy.network.pay.paybase.callback.PayOrderHandle; import
 * com.xmsy.server.zxyy.manager.mq.MqClient;
 * 
 * import lombok.extern.slf4j.Slf4j;
 * 
 *//**
	 * 充值回调
	 * 
	 * @author Administrator
	 *
	 *//*
		 * @Slf4j
		 * 
		 * @Service public class CallBackService implements PayOrderHandle {
		 * 
		 * @Autowired private MqClient mqClient;
		 * 
		 * @Override
		 * 
		 * @Transactional public boolean orderHandle(CallbackMessage callbackParam)
		 * throws Exception { log.info("[CallBackService] orderHandle callbackParam {}",
		 * callbackParam); return mqClient.orderRechargePush(callbackParam); } }
		 */