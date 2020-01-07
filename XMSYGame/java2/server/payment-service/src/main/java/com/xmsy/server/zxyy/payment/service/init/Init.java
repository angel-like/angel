package com.xmsy.server.zxyy.payment.service.init;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.xmsy.server.zxyy.payment.service.constant.PayConstant;
import com.xmsy.server.zxyy.payment.service.modules.payconfig.entity.PayConfigEntity;
import com.xmsy.server.zxyy.payment.service.modules.payconfig.service.PayConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.xmsy.common.bean.message.SysConfigMessage;
import com.xmsy.network.pay.paybase.pay.impl.PayServiceBase;
import com.xmsy.server.zxyy.payment.service.constant.SysConfigConstant;
import com.xmsy.server.zxyy.payment.service.modules.config.service.SysConfigService;
import com.xmsy.server.zxyy.payment.service.modules.sysconfig.entity.SysConfigEntity;
import com.xmsy.server.zxyy.payment.service.utils.BeanConversionTools;
import com.xmsy.server.zxyy.payment.service.utils.TreeBuilder;

/**
 *
 * .支付系统启动配置
 *
 * @author aleng
 *
 */
@Component
public class Init implements CommandLineRunner {

	@Resource
	private SysConfigService sysConfigService;
	@Resource
	private PayConfigService payConfigService;
/*	@Resource
	private PayConfigRedis payConfigRedis;*/
	/**
	 * 支付系统初始化，初始化支付组件对应的配置
	 */
	@Override
	public void run(String... args) throws Exception {
		List<SysConfigEntity> sysConfigs = sysConfigService.list();
		List<SysConfigMessage> commonConfigs = BeanConversionTools.commonConfigConversion(sysConfigs);
		List<SysConfigMessage> commonConfigTree = new TreeBuilder(commonConfigs).buildTree();
		for (SysConfigMessage commonConfig : commonConfigTree) {
			if (SysConfigConstant.PAY.equals(commonConfig.getName())) {
				PayServiceBase.payServiceInit(commonConfig.getChildren(), PayConstant.getPAY_SERVICE_CALLBACK_URL());
				break;
			}
		}
		PayConstant.CALLBACK_IP=new HashMap<>();
		List<PayConfigEntity> list = payConfigService.list();
		for (PayConfigEntity entity : list) {
			if(entity.getDeleteStatus()==1){
				continue;
			}
			if (StringUtils.isNotBlank(entity.getAliasName())&&StringUtils.isNotBlank(entity.getCallbackIp())){
				PayConstant.CALLBACK_IP.put(entity.getAliasName(),entity.getCallbackIp());
			}

		}
		/*payConfigRedis.delete("callbackIp");
		payConfigRedis.saveOrUpdate(PayConstant.CALLBACK_IP);
*/
	}

}
