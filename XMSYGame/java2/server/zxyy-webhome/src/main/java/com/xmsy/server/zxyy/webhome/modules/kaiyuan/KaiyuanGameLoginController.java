package com.xmsy.server.zxyy.webhome.modules.kaiyuan;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.app.event.PushService;
import com.xmsy.server.zxyy.webhome.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.webhome.modules.kaiyuan.enums.KyErrorCodeEnum;
import com.xmsy.server.zxyy.webhome.modules.kaiyuan.service.KaiyuanGameService;
import com.xmsy.server.zxyy.webhome.modules.manager.kygameloginrecord.entity.KyGameloginRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.kygameloginrecord.service.KyGameloginRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.utils.IpUtil;
import com.xmsy.server.zxyy.webhome.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 游戏记录
 *
 * @author youyou
 * @email xxxxx
 * @date 2019-11-27 16:00:25
 */
@Slf4j
@RestController
@RequestMapping("V1.0/kaiyuan")
public class KaiyuanGameLoginController {
	@Autowired
	private UserService userService;
//	@Autowired
//	private HierarchyGameRoleService hierarchyGameRoleService;
	@Autowired
	private KaiyuanGameService kaiyuanGameService;
//	@Autowired
//	private LocalContentCache localContentCache;
	@Autowired
	KyGameloginRecordService kyGameloginRecordService;
	@Autowired
	PushService pushService;
	/**
	 * 登录开元游戏
	 */
	@GetMapping("/gameLogin")
	public R gameLogin(@RequestParam(value = "gameId") Long gameId, @RequestHeader("token") String token, HttpServletRequest httpServletRequest) {
		String ip = IpUtil.getIPAddress(httpServletRequest);
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity entity = userService.selectById(userId);
		int code =0;
		// 判断用户是否获取失败
		if (entity == null) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}

		if(!"USER".equals(entity.getUserType())) { //正式会员才有权限
			throw new RRException(ErrorCode.KyGameErrorCode.GAMEROLE_IS_NULL.getErrMsg(),
					ErrorCode.KyGameErrorCode.GAMEROLE_IS_NULL.getCode());
		}
		try{

			JSONObject returnObj = kaiyuanGameService.gameLogin(entity,ip,gameId).getJSONObject("d");
			code = returnObj.getIntValue("code");
			if (code  == 0 ){//游戏登录成功
				//将用户coin 冻结
				userService.freezeUserCoin(entity, Constant.TransactionType.FREEZE.getValue(), Constant.TransactionDetailType.KAIYUANLOGIN.getValue());

				//金币推送
				UserEntity userUpdateParam = new UserEntity();
				userUpdateParam.setId(entity.getId());
				userUpdateParam.setCoin(0L-entity.getCoin());
				UserInfoMessage message = new UserInfoMessage(userUpdateParam,null);
				log.info("[进入开元 金币冻结-推送消息] message {}", message);
				pushService.pushUserInfo(message);
				return R.ok().put("data", returnObj);
			}else{
				throw new RRException(KyErrorCodeEnum.KyErrorRecordCodeEnum.getKyErrorCodeMsg(code),code);
			}


	}catch (RRException e){
			throw new RRException(KyErrorCodeEnum.KyErrorRecordCodeEnum.getKyErrorCodeMsg(code),code);
		}catch (Exception e){
			e.printStackTrace();
		throw new RRException(ErrorCode.KyGameErrorCode.SERVER_IS_ERRO.getErrMsg(),
				ErrorCode.KyGameErrorCode.SERVER_IS_ERRO.getCode());
	}

	}

	@GetMapping("/gameLogout")
		public R gameLogout(@RequestHeader("token") String token, HttpServletRequest httpServletRequest) {
			Map<String,Object> result  =new HashMap<>();
			Long userId = Long.valueOf(JwtUtil.getUserId(token));
			UserEntity entity = userService.selectById(userId);
			int code =0;
		// 判断用户是否获取失败
		if (entity == null) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}

		try{
			JSONObject returnObj = kaiyuanGameService.logout(entity).getJSONObject("d");
			if ((returnObj.getIntValue("code") == 0)){//游戏退出成功
				// 用户下分:1,先查询用户 余额，2 下分。
				JSONObject object = kaiyuanGameService.queryMoney(entity).getJSONObject("d");
				code = object.getIntValue("code");
				if ( code== 0){
					//查询成功。
					String totalMoney = object.getString("totalMoney");
					String freeMoney = object.getString("freeMoney");
					BigDecimal brm = new BigDecimal(totalMoney).multiply(new BigDecimal(100)).setScale(0,BigDecimal.ROUND_HALF_UP);

					BigDecimal congealMoney = (new BigDecimal(totalMoney).subtract(new BigDecimal(freeMoney))).multiply(new BigDecimal(100)).setScale(0,BigDecimal.ROUND_HALF_DOWN);
					if (new BigDecimal(freeMoney).compareTo(BigDecimal.ZERO) >0){
//						entity.setFreezeCoin(congealMoney.longValue());
                        entity.setFreezeCoin(0L);
                        entity.setCoin(brm.longValue());
						JSONObject clearmoney  = kaiyuanGameService.clearMoney(entity,(freeMoney));
					//	userService.updateUserCoin(entity,brm.longValue(),Constant.TransactionType.UNFREEZE.getValue(), Constant.TransactionDetailType.KAIYUANLOGOUT.getValue());
						code = clearmoney.getIntValue("code");
						if (code == 0){
							userService.unfreezeUserCoin(entity);
							UserEntity userUpdateParam = new UserEntity();
							userUpdateParam.setId(entity.getId());
							userUpdateParam.setCoin(brm.longValue());
							UserInfoMessage message = new UserInfoMessage(userUpdateParam,null);
							log.info("[进入开元 金币冻结-推送消息] message {}", message);
							pushService.pushUserInfo(message);
						}

					}
					result.put("freeMoney",brm.longValue());
					result.put("congealMoney",congealMoney);

					//将用户登录状态设为0
					List<KyGameloginRecordEntity> list = kyGameloginRecordService.selectList(new EntityWrapper<KyGameloginRecordEntity>(new KyGameloginRecordEntity().setUserId(userId).setStatus(1L)).last("limit 1"));
					if(list!= null && list.size() >0){
						KyGameloginRecordEntity  kyGameloginRecordEntity = list.get(0);
						kyGameloginRecordEntity.setStatus(0L);
						kyGameloginRecordService.updateById(kyGameloginRecordEntity);

					}


					return R.ok().put("data", result);
				}else{
					throw new RRException(KyErrorCodeEnum.KyErrorRecordCodeEnum.getKyErrorCodeMsg(code),code);
				}
			}else{
				throw new RRException(KyErrorCodeEnum.KyErrorRecordCodeEnum.getKyErrorCodeMsg(code),code);
			}


		}catch (RRException e){
			throw new RRException(KyErrorCodeEnum.KyErrorRecordCodeEnum.getKyErrorCodeMsg(code),code);
		}catch (Exception e){
			e.printStackTrace();
			throw new RRException(ErrorCode.KyGameErrorCode.SERVER_IS_ERRO.getErrMsg(),
					ErrorCode.KyGameErrorCode.SERVER_IS_ERRO.getCode());
		}

	}



	@GetMapping("/clearmoney")
	public R clearmoney(@RequestHeader("token") String token, HttpServletRequest httpServletRequest) {

		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity entity = userService.selectById(userId);

		// 判断用户是否获取失败
		if (entity == null) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}

		try{
			JSONObject returnObj = kaiyuanGameService.clearMoney(entity,"0").getJSONObject("d");
			if ((returnObj.getIntValue("code") == 0)){//游戏登录成功
				return R.ok().put("data", returnObj);
			}


		}catch (Exception e){
			e.printStackTrace();
			throw new RRException(ErrorCode.KyGameErrorCode.SERVER_IS_ERRO.getErrMsg(),
					ErrorCode.KyGameErrorCode.SERVER_IS_ERRO.getCode());
		}

		return  null;

	}

	}
