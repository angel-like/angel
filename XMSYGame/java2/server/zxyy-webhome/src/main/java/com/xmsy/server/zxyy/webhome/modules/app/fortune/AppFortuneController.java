package com.xmsy.server.zxyy.webhome.modules.app.fortune;


import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.xmsy.common.bean.message.FortuneMessage;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.app.event.PushService;
import com.xmsy.server.zxyy.webhome.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.webhome.modules.app.fortune.param.OpenEnvelopeParam;
import com.xmsy.server.zxyy.webhome.modules.manager.fortuneactivitymanager.entity.EnvelopeRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.fortuneactivitymanager.entity.FortuneActiviConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.fortuneactivitymanager.service.EnvelopeRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.fortuneactivitymanager.service.FortuneActiviConfigService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.mq.MqClient;
import com.xmsy.server.zxyy.webhome.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;



/**
 * 
 * 天降财神接口
 * 
 * @author aye
 * @email xxxxx
 * @date 2019-12-12 11:00:25
 */ 
@Slf4j
@RestController
@RequestMapping("V1.0/App")
public class AppFortuneController {
	@Autowired
	private FortuneActiviConfigService fortuneActiviConfigService;
	@Autowired
	private MqClient mqClient;
	@Autowired
	private UserService userService;
	@Autowired
	private EnvelopeRecordService envelopeRecordService;
	@Resource
	private PushService pushService;
	/**
	 * 天降财神活动列表      1
	 * @param token
	 * @return
	 */
	@GetMapping("/fortuneActivity")
	public R fortuneActivity(@RequestHeader("token")  String token) {
		//获取当前时间 在 活动时间内   （只有一个活动，但有多个事件）
		Wrapper<FortuneActiviConfigEntity> entityWrapper = new EntityWrapper<FortuneActiviConfigEntity>();
		entityWrapper.le("start_time", DateUtils.getToday());
		entityWrapper.ge("end_time", DateUtils.getToday());
		FortuneActiviConfigEntity fortuneActiviConfig = fortuneActiviConfigService.selectOne(entityWrapper);
		if(fortuneActiviConfig==null) {
			return R.ok().put("data", fortuneActiviConfig);
		}
		return R.ok().put("data", fortuneActiviConfig);
	}
	/**
	 * 测试RabbitMQ推送             2
	 * @return
	 */
	@GetMapping("/pushRabbit")
	public R pushRabbit() {
		FortuneMessage fortuneMessage=new FortuneMessage();
		fortuneMessage.setUserId(538439L);
		//fortuneMessage.setValidBet(145L);
		fortuneMessage.setRechargeAmount(1000L);
		fortuneMessage.setEventCode("recharge-money");//测试红包
		mqClient.fortunePush(fortuneMessage);
		return R.ok().put("data", "推送成功");
	}
	
	/**
	 * 获取用户未开启红包集合  数量        3
	 * @param token
	 * @return
	 */
	@GetMapping("/envelopeNum")
	public R envelopeNum(@RequestHeader("token")  String token) {
		//1.获取会员
		UserEntity user = userService.getUser(token);
		//2.获取当前时间 在 活动时间内   （只有一个活动，但有多个事件）
		Wrapper<FortuneActiviConfigEntity> wrapper = new EntityWrapper<FortuneActiviConfigEntity>();
		wrapper.le("start_time", DateUtils.getToday());
		wrapper.ge("end_time", DateUtils.getToday());
		FortuneActiviConfigEntity fortuneActiviConfig = fortuneActiviConfigService.selectOne(wrapper);
		if(fortuneActiviConfig==null) {
			throw new RRException(ErrorCode.UserEnvelope.USER_FORTUNE_ACTIVITY_ERROR.getErrMsg(),
					ErrorCode.UserEnvelope.USER_FORTUNE_ACTIVITY_ERROR.getCode());
		}
		//3.获取活动时间内未开启的红包集合
		Wrapper<EnvelopeRecordEntity> entityWrapper = new EntityWrapper<EnvelopeRecordEntity>(
				new EnvelopeRecordEntity().setUserId(user.getId())
				.setActivityId(fortuneActiviConfig.getId())
				.setStatus(0));
		entityWrapper.ge("create_time", fortuneActiviConfig.getStartTime());
		entityWrapper.le("create_time", fortuneActiviConfig.getEndTime());
		List<EnvelopeRecordEntity> list = envelopeRecordService.selectList(entityWrapper);
		return R.ok().put("data", list.size());
	}
	/**
	 * 用户点击 开启红包       4
	 *  1个或者全部开启   
	 * @param token
	 * @return
	 */
	@PostMapping("/openEnvelope")
	public R openEnvelope(@RequestHeader("token")  String token,@RequestBody @Valid OpenEnvelopeParam openEnvelopeParam) {
		//1.获取会员
		UserEntity user = userService.getUser(token);
		//2.获取当前时间 在 活动时间内   （只有一个活动，但有多个事件）
		Wrapper<FortuneActiviConfigEntity> wrapper = new EntityWrapper<FortuneActiviConfigEntity>();
		wrapper.le("start_time", DateUtils.getToday());
		wrapper.ge("end_time", DateUtils.getToday());
		FortuneActiviConfigEntity fortuneActiviConfig = fortuneActiviConfigService.selectOne(wrapper);
		if(fortuneActiviConfig==null) {
			throw new RRException(ErrorCode.UserEnvelope.USER_FORTUNE_ACTIVITY_TIMEOUT_ERROR.getErrMsg(),
					ErrorCode.UserEnvelope.USER_FORTUNE_ACTIVITY_TIMEOUT_ERROR.getCode());
		}
		//3.获取活动时间内 未开启的红包集合 （会与id,活动id,未开启的）
		Wrapper<EnvelopeRecordEntity> entityWrapper = new EntityWrapper<EnvelopeRecordEntity>(
				new EnvelopeRecordEntity().setUserId(user.getId())
				.setActivityId(fortuneActiviConfig.getId())
				.setStatus(0));//未开启
		entityWrapper.ge("create_time", fortuneActiviConfig.getStartTime());
		entityWrapper.le("create_time", fortuneActiviConfig.getEndTime());
		Long coin=0l;//接收获得的红包
		Integer unEnvelopeNum=0;
		if(openEnvelopeParam.getNumber()==1) {
			//3.1开启一个红包
			unEnvelopeNum=1;
			EnvelopeRecordEntity envelopeRecord = envelopeRecordService.selectOne(entityWrapper);
			coin=envelopeRecordService.openEnvelopeOne(envelopeRecord,fortuneActiviConfig,user);
		}else {
			//3.2开启全部红包
			List<EnvelopeRecordEntity> envelopeRecordlist = envelopeRecordService.selectList(entityWrapper);
			unEnvelopeNum=envelopeRecordlist.size();
			coin=envelopeRecordService.openEnvelopeAll(envelopeRecordlist, fortuneActiviConfig, user);
		}
		// 推送客户端
		UserInfoMessage message = null;
		try {
			message = new UserInfoMessage();
			message.setUid(user.getId());//用户id
			message.setCoin(coin);//开启个红包得到的  金币
			message.setUnEnvelopeNum(unEnvelopeNum*-1);//开启了多少个红包
			log.info("[用户 开启红包-推送消息] message {}", message);
			pushService.pushUserInfo(message);
		} catch (Exception e) {
			log.error("[用户 开启红包-推送消息] message {}", message);
		}
		return R.ok().put("data", coin);
	}
}
