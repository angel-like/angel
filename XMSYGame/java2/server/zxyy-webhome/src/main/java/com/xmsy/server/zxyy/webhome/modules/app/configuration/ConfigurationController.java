package com.xmsy.server.zxyy.webhome.modules.app.configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.xmsy.server.zxyy.webhome.modules.manager.paychannelconfig.entity.PayChannelConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.paychannelconfig.service.PayChannelConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.app.configuration.param.AppAdConfigParam;
import com.xmsy.server.zxyy.webhome.modules.app.configuration.param.AppAlertConfigParam;
import com.xmsy.server.zxyy.webhome.modules.app.configuration.param.AppPopularGamesParam;
import com.xmsy.server.zxyy.webhome.modules.app.configuration.param.ConfigUrationParam;
import com.xmsy.server.zxyy.webhome.modules.manager.appadcofig.dao.AppAdCofigDao;
import com.xmsy.server.zxyy.webhome.modules.manager.appalertcofig.service.AppAlertCofigService;
import com.xmsy.server.zxyy.webhome.modules.manager.appcustomerservice.entity.AppCustomerServiceEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.appcustomerservice.service.AppCustomerServiceService;
import com.xmsy.server.zxyy.webhome.modules.manager.apphotpromotions.entity.AppHotPromotionsEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.apphotpromotions.service.AppHotPromotionsService;
import com.xmsy.server.zxyy.webhome.modules.manager.apppaycofig.service.AppPayCofigService;
import com.xmsy.server.zxyy.webhome.modules.manager.apppopulargames.service.AppPopularGamesService;
import com.xmsy.server.zxyy.webhome.modules.manager.domainmanagement.service.DomainManagementService;
import com.xmsy.server.zxyy.webhome.modules.manager.noticemanagement.entity.NoticeManagementEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.noticemanagement.service.NoticeManagementService;
import com.xmsy.server.zxyy.webhome.modules.manager.payconfig.entity.PayConfigFirstEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.payconfig.entity.PayConfigResultEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.payconfig.service.PayConfigService;
import com.xmsy.server.zxyy.webhome.modules.manager.rechargeamount.entity.RechargeAmountEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.rechargeamount.service.RechargeAmountService;
import com.xmsy.server.zxyy.webhome.modules.manager.rechargechannel.entity.RechargeChannelResultEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.rechargechannel.service.RechargeChannelService;
import com.xmsy.server.zxyy.webhome.modules.manager.sharepicturemanage.service.SharePictureManageService;
import com.xmsy.server.zxyy.webhome.modules.manager.sysdictionary.service.SysDictionaryService;
import com.xmsy.server.zxyy.webhome.modules.manager.sysregisternecessary.entity.SysRegisterNecessaryEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sysregisternecessary.service.SysRegisterNecessaryService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.utils.JwtUtil;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-02-14 17:18:25
 */
@Slf4j
@RestController
@RequestMapping("V1.0/App")
public class ConfigurationController {
	@Autowired
	private AppPayCofigService appPayCofigService;
	@Autowired
	private AppAdCofigDao appAdCofigDao;
	@Autowired
	private AppCustomerServiceService appCustomerServiceService;
	@Autowired
	private AppAlertCofigService appAlertCofigService;
	@Autowired
	private PayConfigService payConfigService;
	@Autowired
	private RechargeAmountService rechargeAmountService;
	@Autowired
	private RechargeChannelService rechargeChannelService;
	@Autowired
	private NoticeManagementService noticeManagementService;
	@Autowired
	private SysDictionaryService SysDictionaryService;
	@Autowired
	private AppPopularGamesService appPopularGamesService;
	@Autowired
	private AppHotPromotionsService appHotPromotionsService;
	@Autowired
	private DomainManagementService domainManagementService;
	@Autowired
	private SysRegisterNecessaryService sysRegisterNecessaryService;
	@Autowired
	private SharePictureManageService sharePictureManageService;
	@Autowired
	private UserService userService;
	@Autowired
	private PayChannelConfigService payChannelConfigService;

	/**
	 * 获取支付侧边导航
	 */
	@GetMapping("/appRechargeNavigation")
	public R appRechargeNavigation() {
		List<PayConfigResultEntity> list = payConfigService.appRechargeNavigation();
		//log.info("[appRechargeNavigation] list {}", list);
		return R.ok().put("data", list);
	}

	/**
	 * 获取首推支付宝和微信
	 */
	@GetMapping("/firstPayForApp")
	public R firstPayForApp() {
		List<PayConfigFirstEntity> list = rechargeChannelService.selectFirstRecommendForApp();
		//log.info("[firstPayForApp] list {}", list);
		return R.ok().put("data", list);
	}

	/**
	 * 获取H5支付方式路径
	 */
	@GetMapping("/H5PayUrl")
	public R H5PayUrl() {
		return R.ok().put("data", SysDictionaryService.getName(SysConstant.urlCode, SysConstant.H5PayCode));
	}

	/**
	 * 获取app活动url路径
	 */
	@GetMapping("/AppActivityUrl")
	public R AppActivityUrl() {
		return R.ok().put("data", SysDictionaryService.getName(SysConstant.urlCode, SysConstant.AppActivityUrl));
	}

	/**
	 * 获取支付导航内容
	 */
	@GetMapping("/appRechargeNavigationChild")
	public R appRechargeNavigationChild(@RequestParam Long navigationId) {
		//log.info("[appRechargeNavigationChild] navigationId {}", navigationId);
		List<RechargeAmountEntity> list = rechargeAmountService
				.selectList(new EntityWrapper<RechargeAmountEntity>(new RechargeAmountEntity().setPayId(navigationId)));
		//log.info("[appRechargeNavigationChild] list {}", list);
		return R.ok().put("data", list);
	}

	/**
	 * .获取第三方支付的金额列表以及支付限额
	 * 
	 * @param payId
	 *            支付公司id
	 * @return
	 */
	@GetMapping("/RechargeAmountAndPayLimit")
	public R appRechargeAmountAndPayLimit(@RequestParam Long payId,@RequestHeader("token") String token) {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity selectOne = userService.selectById(userId);
		List<RechargeChannelResultEntity> rechargeChannels = rechargeChannelService.selectChannelsByPayIdhierarchyId(payId,selectOne.getHierarchyId());
		List<PayChannelConfigEntity> list = payChannelConfigService
				.selectList(new EntityWrapper<PayChannelConfigEntity>(new PayChannelConfigEntity().setPayId(payId))
						.orderAsc(Lists.newArrayList("amount")));
		for(RechargeChannelResultEntity entity:rechargeChannels) {
			List<Long> amount =new ArrayList<>();
			for(PayChannelConfigEntity ra:list) {
				if(ra.getChannelId()==entity.getChannelId()) {
					String[] split = ra.getAmount().split(",");
					for(String s:split) {
						if(s!=null&&!s.equals("")) {
							Long l;
							try {
								l=Long.parseLong(s);
							}catch (Exception e){
								e.printStackTrace();
								continue;
							}

							if (null != entity.getLowLimit() && entity.getLowLimit() >  l) {
								continue;
							}
							if (null != entity.getHighLimit() && entity.getHighLimit() < l) {
								continue;
							}
							amount.add(l);

						}

					}

				}
			}
			entity.setAmount(amount);

		}
		log.info("[RechargeAmountAndLimit] rechargeChannels {}", rechargeChannels);
		JSONObject result = new JSONObject();
		result.put("channel", rechargeChannels);
		return R.ok().put("data", result);
	}
	/**
	 * .获取第三方支付的金额列表以及支付限额
	 * 
	 * @param payId
	 *            支付公司id
	 * @return
	 */
	@GetMapping("/RechargeAmountAndLimit")
	public R appRechargeAmountAndLimit(@RequestParam Long payId) {
		log.info("[appRechargeAmountAndPayLimit] payId {}", payId);
		List<RechargeAmountEntity> list = rechargeAmountService
				.selectList(new EntityWrapper<RechargeAmountEntity>(new RechargeAmountEntity().setPayId(payId))
						.orderAsc(Lists.newArrayList("amount")));
		List<RechargeChannelResultEntity> rechargeChannels = rechargeChannelService.selectChannelsByPayId(payId);
		log.info("[RechargeAmountAndLimit] rechargeChannels {}", rechargeChannels);
		JSONObject result = new JSONObject();
		result.put("channel", rechargeChannels);
		result.put("amounts", list);
		return R.ok().put("data", result);
	}

	/**
	 * 获取指定支付公司指定金额的支付方式
	 */
	@GetMapping("/paymentMethod")
	public R paymentMethod(@RequestParam Long payId, @RequestParam Long amount) {
		//log.info("[paymentMethod] payId {} amount {}", payId, amount);
		List<RechargeChannelResultEntity> rechargeChannels = rechargeChannelService.selectChannelsByPayId(payId);
		log.info("[paymentMethod] rechargeChannels {}", rechargeChannels);
		if (CollectionUtils.isEmpty(rechargeChannels)) {
			return R.ok().put("data", rechargeChannels);
		}
		List<RechargeChannelResultEntity> rechargeChannelResult = Lists.newArrayList();
		List<Long> list = Lists.newArrayList();
		for (RechargeChannelResultEntity rechargeChannel : rechargeChannels) {
			if (amount <= rechargeChannel.getHighLimit() && amount >= rechargeChannel.getLowLimit()) {
				rechargeChannelResult.add(rechargeChannel);
			}
			list.add(rechargeChannel.getLowLimit());
			list.add(rechargeChannel.getHighLimit());
		}
		if (CollectionUtils.isEmpty(rechargeChannelResult)) {
			Collections.sort(list);
			throw new RRException("[最小支付金额" + list.get(0) + ",最大支付金额" + list.get(list.size() - 1) + "]",
					ErrorCode.PayErrorCode.PAY_CHANNEL_AMOUNT_ERROR.getCode());
		} else {
			return R.ok().put("data", rechargeChannelResult);
		}
	}

	/**
	 * 获取vip充值通道
	 */
	@GetMapping("/appPayConfig")
	public R appPayConfig(@RequestHeader("token") String token) {
		//log.info("[appPayConfig] token {}", token);
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		List<ConfigUrationParam> list = appPayCofigService.selectPayConfigList(userId);
		log.info("[appPayConfig] list {}", list);
		return R.ok().put("data", list);
	}

	/**
	 * 获取app广告
	 */
	@GetMapping("/appadConfig")
	public R appadConfig() {
		List<AppAdConfigParam> list = appAdCofigDao.selectAppAdListForApp();
		//log.info("[appadConfig] list {}", list);
		return R.ok().put("data", list);
	}

	/**
	 * 获取app客服路径
	 */
	@GetMapping("/appCustomerService")
	public R appCustomerService() {
		List<AppCustomerServiceEntity> list = appCustomerServiceService
				.selectList(new EntityWrapper<AppCustomerServiceEntity>(new AppCustomerServiceEntity()));
		//log.info("[appadConfig] list {}", list);
		return R.ok().put("data", list);
	}

	/**
	 * 获取app弹窗广告列表
	 */
	@GetMapping("/appAlertCofig")
	public R appAlertCofig() {
		List<AppAlertConfigParam> list = appAlertCofigService.selectListForWeb();
		//log.info("[appadConfig] list {}", list);
		return R.ok().put("data", list);
	}

	/**
	 * 获取公告
	 */
	@GetMapping("/configure/noticeManagementList")
	public R noticeManagementList(@RequestParam(required = false) Long noticeType) {
		NoticeManagementEntity entity = new NoticeManagementEntity();
		entity.setEnable(SysConstant.ENABLE_TRUE);
		String now = DateUtil.now();
		if (null != noticeType) {
			entity.setNoticeType(noticeType);
		}
		List<NoticeManagementEntity> list = noticeManagementService.selectList(
				new EntityWrapper<NoticeManagementEntity>(entity).le("effect_time", now).ge("failure_time", now));
		return R.ok().put("data", list);
	}

	/**
	 * 获取热门游戏
	 */
	@GetMapping("/hotGames")
	public R hotGames() {
		List<AppPopularGamesParam> hotGames = appPopularGamesService.selectListForApp();
		for (AppPopularGamesParam entity : hotGames) {
			Random random = new Random();
			entity.setOnlineCount(random.nextInt(300) + 100);
		}
		return R.ok().put("data", hotGames);
	}

	/**
	 * 获取热门活动
	 */
	@GetMapping("/hotActivitys")
	public R hotActivity() {
		List<AppHotPromotionsEntity> hotActivitys = appHotPromotionsService
				.selectList(new EntityWrapper<>(new AppHotPromotionsEntity()).orderBy("order_num"));
		List<AppHotPromotionsEntity> hotActivitys1= new ArrayList<>();
		List<AppHotPromotionsEntity> notice= new ArrayList<>();

		for (AppHotPromotionsEntity atp : hotActivitys){
			if (atp.getClassify().equals(1L)){
				hotActivitys1.add(atp);
			}else if (atp.getClassify().equals(2L)){
				notice.add(atp);
			}
		}
       Map<String,Object> data = new HashMap<>();
		data.put("hotActivitys",hotActivitys1);
		data.put("notice",notice);
		return R.ok().put("data", data);
	}
	/**
	 * 合并用户信息接口
	 */
	@GetMapping("/appCustomerInfo")
	public R appCustomerInfo() {
		Map<String, Object> mapTotal=new HashMap<>();
		List<AppCustomerServiceEntity> list = appCustomerServiceService
				.selectList(new EntityWrapper<AppCustomerServiceEntity>(new AppCustomerServiceEntity()));
        SysRegisterNecessaryEntity sysRegisterNecessaryEntity = sysRegisterNecessaryService.selectById(SysConstant.REGISTER_PHONE);
        Map<String, Object> map = sharePictureManageService.findOneSharePictureManage();
		String officialUrl = domainManagementService.getOfficalDomain();
		
		mapTotal.put("sharePicture", map);
		mapTotal.put("appCustomerService", list);
		mapTotal.put("registerPhoneNecessary", sysRegisterNecessaryEntity.getNecessary());
		mapTotal.put("officialUrl", officialUrl);
		
		return R.ok().put("data",mapTotal);
		/*return R.ok().put("data", ImmutableMap.of("sharePicture",map,"appCustomerService",list,"registerPhoneNecessary",
				sysRegisterNecessaryEntity.getNecessary(),"officialUrl",officialUrl));*/
	}
}
