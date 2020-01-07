package com.xmsy.server.zxyy.webhome.modules.app.shop;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.webhome.modules.manager.giftbagconfig.entity.GiftBagConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.giftbagconfig.service.GiftBagConfigService;
import com.xmsy.server.zxyy.webhome.modules.manager.giftbagexchangerecord.service.GiftBagExchangeRecordService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.webhome.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.webhome.modules.manager.messagemanagement.entity.MessageManagementEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.messagemanagement.service.MessageManagementService;
import com.xmsy.server.zxyy.webhome.modules.app.event.PushService;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.app.shop.param.ExchangeCodeParam;
import com.xmsy.server.zxyy.webhome.modules.app.shop.param.ExchangeParam;
import com.xmsy.server.zxyy.webhome.modules.app.shop.param.ShopParam;
import com.xmsy.server.zxyy.webhome.modules.app.shop.service.AppShopService;
import com.xmsy.server.zxyy.webhome.modules.manager.shopproduct.entity.ShopProductEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.shopproduct.service.ShopProductService;
import com.xmsy.server.zxyy.webhome.modules.manager.shoproomcardproportion.entity.ShopRoomCardProportionEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.shoproomcardproportion.service.ShopRoomCardProportionService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.utils.DateUtils;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;


/**
 * 
 * 获取道具参数详情接口
 * 购买道具接口
 * @author axiong
 * @email xxxxx
 * @date 2019-06-11 11:00:25
 */
@Slf4j
@RestController
@RequestMapping("V1.0/App")
public class AppShopController{
	@Autowired
	private ShopProductService shopProductService;
	@Autowired
	private AppShopService appShopService;
	@Autowired
	private UserService userService;	
	@Autowired
	private ShopRoomCardProportionService shopRoomCardProportionService;
	@Autowired
    private GiftBagConfigService giftBagConfigService;
	@Autowired
    private GiftBagExchangeRecordService giftBagExchangeRecordService;
	@Autowired
	private MessageManagementService messageManagementService;
	@Autowired
	private PushService pushService;
	
	public static final String TITLE = "兑换结果通知";
	public static final String FAIL_CONTENT = "兑换码: %s , 兑换额度: %s, 被取消, 请联系管理员";
	public static final String SUCCUSS_CONTENT = "兑换码: %s, 兑换额度 : %s, 已被确认, 请注意查收";
	public static final int CONTENT_TYPE = 1; // 用户邮件类型
	public static final int TARGET_OBJECT = 1;// 指定用户
	
	/**
	 * 获取商城产品道具参数详情接口
	 * 
	 */
	@GetMapping("/product")
	public R getProduct() {		
		List<ShopProductEntity> list = appShopService.getListShopProductEntity();
		List<Map<String, Object>> resultList = appShopService.getListProduct(list);
		return R.ok().put("data", resultList);
	}

	/**
	 * 购买商城产品接口
	 * @param token
	 * @param param
	 * @return
	 */
	@PostMapping("/buy/product")
	public R buyProduct(@RequestHeader("token") String token, @RequestBody @Valid ShopParam param) {
		// 获取要购买的商城产品实体
		ShopProductEntity shopproduct = shopProductService.selectById(param.getProductId());
		//获取会员实体
		UserEntity user = userService.getUser(token);
		//获取购买的道具交易记录  param.getNumber()购买数量  跟金币无关
		shopProductService.updateUserAndShopPropRecord(shopproduct, user,param.getNumber());				
		return R.ok().put("data", "交易成功");
	}
	/**
	 * 获取金币兑换房卡比例的接口
	 * @param token
	 * @return
	 */
	@GetMapping("/proportion")
	public R proportion(@RequestHeader("token") String token) {	
		BigDecimal proportion =shopRoomCardProportionService.findOneProportion(new ShopRoomCardProportionEntity().setEffectDate(DateUtils.getDateAddDay(0)));	
		return R.ok().put("data",proportion==null?0:proportion);
	}
	/**
	 * 金币兑换房卡的接口   
	 * 先去查询当天的兑换比例
	 * @param token
	 * @param param
	 * @return
	 */
	@PostMapping("/exchange")
	public R exchange(@RequestHeader("token") String token, @RequestBody @Valid ExchangeParam ExchangeParam) {
		if(ExchangeParam.getNumber()<=Constant.DEFAULT) {//兑换数量要大于0
			throw new RRException(ErrorCode.ShopErrorCode.SHOP_EXCHANGE_ROOMCARD_ERROR.getErrMsg(),
					ErrorCode.ShopErrorCode.SHOP_EXCHANGE_ROOMCARD_ERROR.getCode());
		}
		//获取会员实体
		UserEntity user = userService.getUser(token);
		//通过会员实体以及要兑换房卡数量： 修改用户信息  增加兑换记录
		shopProductService.exchangeUserAndShopPropRecord(user, ExchangeParam.getNumber());	
		return R.ok().put("data", "兑换成功");
	}
	
	
	/**
	 * 礼包兑换接口
	 * @param token
	 * @param ExchangeParam
	 * @return
	 */
	@PostMapping("/exchangeCode")
	public R exchangeCode(@RequestHeader("token") String token, @RequestBody @Valid ExchangeCodeParam exchangeCodeParam) {
		//获取会员实体
		UserEntity user = userService.getUser(token);
		GiftBagConfigEntity giftBagConfigEntity = giftBagConfigService.selectOne(new EntityWrapper<GiftBagConfigEntity>(
				new GiftBagConfigEntity().setExchangeCode(exchangeCodeParam.getExchangeCode())));
		//1.校验(兑换码是否存在，是否使用，兑换次数是否足够，是否在生效时间内，兑换账户，单个用户限制次数)
		giftBagConfigService.verificationExchangeCode(giftBagConfigEntity,user);
		//2.密码校验
		if(giftBagConfigEntity.getPassword()!=null) {
			if(!giftBagConfigEntity.getPassword().equals(exchangeCodeParam.getPassword())) {
				throw new RRException(ErrorCode.ShopErrorCode.GIFT_EXCHANGE_PASSWORD_ERROR.getErrMsg(),
						ErrorCode.ShopErrorCode.GIFT_EXCHANGE_PASSWORD_ERROR.getCode());
			}
		}
		//3.把兑换码对应的金额增加到用户身上
		userService.updateUserCoin(user,giftBagConfigEntity.getAcountMoney().longValue(),Constant.TransactionType.RECHARGE.getValue(), Constant.TransactionDetailType.GIFTEXCHANGE.getValue());
		//4.修改礼包配置表的 兑换数量  以及    领取状态
		giftBagConfigService.updateGiftBagConfig(giftBagConfigEntity.getId(), giftBagConfigEntity.getExchangeNum());;
		// 5.增加礼包兑换记录
		giftBagExchangeRecordService.addGiftBagExchangeRecord(user, exchangeCodeParam.getExchangeCode(),giftBagConfigEntity.getAcountMoney());
		// 6. 邮件推送
		Date failTime = DateUtil.nextWeek(); // 邮件失效时间
		Date effectTime = new Date(); // 邮件生效时间
		try {
			MessageManagementEntity messageManagement = new MessageManagementEntity()
					.setUserAccount(user.getAccount())
					.setContent(String.format(SUCCUSS_CONTENT,giftBagConfigEntity.getExchangeCode(), giftBagConfigEntity.getAcountMoney().divide(new BigDecimal(100))))
					.setContentType(CONTENT_TYPE).setTargetObject(TARGET_OBJECT).setEnable(true).setTitle(TITLE)
					.setEffectTime(effectTime).setFailureTime(failTime);
			messageManagementService.save(messageManagement);
			UserEntity pushMessage = new UserEntity();
			pushMessage.setId(user.getId());
			pushMessage.setCoin(giftBagConfigEntity.getAcountMoney().longValue());
			pushMessage.setUnreadNum(1);
			pushMessage.setId(user.getId());
			UserInfoMessage message = new UserInfoMessage(pushMessage, null);
			log.info("[礼包兑换确认->用户未读消息-推送消息] message {}", message);
			pushService.pushExchange(message);
		} catch (Exception e) {
			log.error("[礼包兑换确认,邮件和消息处理] Exception", e);
		}
		return R.ok().put("data", "兑换成功");
	}
}
