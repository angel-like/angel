package com.xmsy.server.zxyy.webhome.modules.app.balance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.webhome.common.annotation.UserTransferAccountsRepeat;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.DateUtils;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.MathUtil;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.app.balance.param.SwitchOutParam;
import com.xmsy.server.zxyy.webhome.modules.app.balance.param.SwitchParam;
import com.xmsy.server.zxyy.webhome.modules.app.balance.service.AppUserBalanceService;
import com.xmsy.server.zxyy.webhome.modules.app.event.PushService;
import com.xmsy.server.zxyy.webhome.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalance.entity.UserBalanceEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalance.service.UserBalanceService;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalancerate.entity.UserBalanceRateEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalancerate.service.UserBalanceRateService;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalancerecord.entity.UserBalanceRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalancerecord.service.UserBalanceRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.userpassword.service.UserPasswordService;
import com.xmsy.server.zxyy.webhome.modules.manager.userprofitrecord.dao.UserProfitRecordDao;
import com.xmsy.server.zxyy.webhome.modules.manager.userprofitrecord.entity.UserProfitRecordEntity;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 余额宝接口
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-06-18 11:00:25
 */
@Slf4j
@RestController
@RequestMapping("V1.0/App")
public class AppUserBalanceController {
	@Autowired
	private UserBalanceService userBalanceService;
	@Autowired
	private UserBalanceRecordService userBalanceRecordService;
	@Autowired
	private UserService userService;	
	@Autowired
	private AppUserBalanceService appUserBalanceService;	
	@Resource
	private PushService pushService;
	@Autowired
	private UserBalanceRateService userBalanceRateService;
	@Autowired
	private UserProfitRecordDao userProfitRecordDao;
	@Resource
	private UserPasswordService userPasswordService;
	/**
	 * 用户金币转入接口
	 * 
	 */
	@Transactional
	@UserTransferAccountsRepeat("请勿重复转入")
	@PostMapping("/switchInto")
	public R swichInto( @RequestBody @Valid SwitchParam param,@RequestHeader("token")  String token) {
		param.setNumber(param.getNumber()*Constant.DB_COIN_RATE);//金币数据乘以100
		if(param.getNumber()<=0) {
			throw new RRException(ErrorCode.UserErrorCode.USER_BALANCE_NUMBER_ERROR.getErrMsg(),
					ErrorCode.UserErrorCode.USER_BALANCE_NUMBER_ERROR.getCode());
		}
		// token转为userID,并通过其获取会员用户实体
		UserEntity user = userService.getUser(token);
		if(user.getForbiddenEnable()) {//抛出用户账户被禁用
			throw new RRException(ErrorCode.UserErrorCode.USER_FORBIDDEN_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_FORBIDDEN_ERRO.getCode());
		}
		//1.扣除会员里的金额，并保存会员交易记录
		//userService.upadateUserSubtractCoin(user, param);
		userService.updateUserCoin(user, param.getNumber()*-1, Constant.TransactionType.YUEBAO.getValue(), Constant.TransactionDetailType.YUEBAOSWITCHINTO.getValue());

		// 2.修改(不存在就增加)用户余额宝金额表里的未收益金额
		if (!userBalanceService.updateUserBalance(user, param)) {
			throw new RRException(ErrorCode.UserErrorCode.USER_BALANCE_ERROR.getErrMsg(),
					ErrorCode.UserErrorCode.USER_BALANCE_ERROR.getCode());			
		}
		// 3.插入用户账户金额存取记录数据
		userBalanceRecordService.insertIntoUserBalanceRecord(user, param);
		// 推送客户端
		UserInfoMessage message = null;
		try {
			message = new UserInfoMessage(user.getId(), param.getNumber());
			log.info("[用户余额宝转入-推送消息] message {}", message);
			pushService.pushExchange(message);
		} catch (Exception e) {
			log.error("[用户余额宝转入-推送消息] message {}", message);
		}
		return R.ok().put("data", "转入成功");
	}
	/**
	 * 用户金币转入接口中的实时利息金币接口
	 */
	@GetMapping("/getSwitchIntoRate")
	public R getSwichIntoRate( @RequestParam Long number) {
		Long totalNumber=number*Constant.DB_COIN_RATE;//金币数据乘以100
		//去查询当前的利率
		UserBalanceRateEntity userBalanceRateEntity=userBalanceRateService.selectOne(
				new EntityWrapper<UserBalanceRateEntity>(new UserBalanceRateEntity())
				.le("effect_date", new Date()).orderBy("effect_date", false)
				);
		if(userBalanceRateEntity==null || userBalanceRateEntity.getId()==null) {
			return R.ok().put("data",0.00);
		}
		return R.ok().put("data",MathUtil.getBigDecimal(userBalanceRateEntity.getRate()).multiply(new BigDecimal(totalNumber)));
	}
	/**
	 * 用户金币转出接口
	 * @param param
	 * @param token
	 * @return
	 */
	@Transactional
	@UserTransferAccountsRepeat("请勿重复转出")
	@PostMapping("/switchOut")
	public R swichOut( @RequestBody @Valid SwitchOutParam param,@RequestHeader("token")  String token) {
		param.setNumber(param.getNumber()*Constant.DB_COIN_RATE);//金币数据乘以100
		if(param.getNumber()<=0) {
			throw new RRException(ErrorCode.UserErrorCode.USER_BALANCE_NUMBER_ERROROUT.getErrMsg(),
					ErrorCode.UserErrorCode.USER_BALANCE_NUMBER_ERROROUT.getCode());
		}
		// token转为userID,并通过其获取会员用户实体
		UserEntity user = userService.getUser(token);
		if(user.getForbiddenEnable()) {//抛出用户账户被禁用
			throw new RRException(ErrorCode.UserErrorCode.USER_FORBIDDEN_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_FORBIDDEN_ERRO.getCode());
		}
		//取款密码校验
		if(!userPasswordService.validateBankPassword(param.getPassword(), user.getId())) {
			throw new RRException(ErrorCode.PasswordErrorCode.PASSWORD_INVALID_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.PASSWORD_INVALID_ERRO.getCode());
		}
		//1.获取用户余额宝金额表实体
		UserBalanceEntity userBalanceEntity=userBalanceService.getUserBalance(user);		
		Long totalMoney=userBalanceEntity.getMoney()+userBalanceEntity.getUnprofitMoney();//获取总金额
		if(totalMoney<param.getNumber()) {//判断金额是否足够取出
			throw new RRException(ErrorCode.UserErrorCode.USER_COIN_NOT_ENOUGH.getErrMsg(),
					ErrorCode.UserErrorCode.USER_COIN_NOT_ENOUGH.getCode());
		}
		//2.通过用户id查询 用户账户金额存取记录表 条件是 1. 类型：存入 2.是否生效：未生效的 3.money>takeMoney
		List<UserBalanceRecordEntity> listRecord=userBalanceRecordService.getListUserBalanceRecord(user.getId());	
		//3.遍历用户账户金额存取记录表   3.1修改用户账户金额存取记录表里的数据 	3.2添加转出的用户账户金额存取记录 	3.3修改用户金额表里的未收益金额 与 金额 的 数据 	3.4修改用户金币信息
		appUserBalanceService.upOrInUserBalanceRecord(user, param.getNumber(), listRecord, param,userBalanceEntity);
		// 推送客户端
		UserInfoMessage message = null;
		try {
			message = new UserInfoMessage(user.getId(), param.getNumber()*-1);
			log.info("[用户余额宝转出-推送消息] message {}", message);
			pushService.pushExchange(message);
		} catch (Exception e) {
			log.error("[用户余额宝转出-推送消息] message {}", message);
		}
		return R.ok();
	}
	/**
	 * 会员余额宝金额存取记录接口
	 * @param token
	 * @return
	 */
	@GetMapping("/getUserBalanceRecord")
	public R getUserBalanceRecordEntity(@RequestHeader("token") String token) {
		// token转为userID,并通过其获取会员用户实体
		UserEntity user = userService.getUser(token);
		//通过用户id查询 该用户账户金额存取记录表
		List<UserBalanceRecordEntity> listRecord=userBalanceRecordService.selectList(
			new EntityWrapper<UserBalanceRecordEntity>(new UserBalanceRecordEntity().setUserId(user.getId())).orderBy("id",false));
		if(listRecord==null||listRecord.size()==0) {
			return R.ok().put("data", new ArrayList<>());
		}
		return R.ok().put("data", appUserBalanceService.getMapUserBalanceRecord(listRecord));
	}
	/**
	 * 会员余额宝记录接口
	 * @param token
	 * @return
	 */
	@GetMapping("/getUserYuEBao")
	public R getUserYuEBao(@RequestHeader("token") String token) {
		UserEntity user = userService.getUser(token);
		//通过用户id查找用户余额宝金额表		
		//UserBalanceEntity userBalanceEntity=userBalanceService.getUserBalance(user);
		UserBalanceEntity userBalanceEntity=userBalanceService.selectOne(
				new EntityWrapper<UserBalanceEntity>(new UserBalanceEntity().setUserId(user.getId())));	
		Map<String,Object>  map=appUserBalanceService.getUserYuEBao(user,userBalanceEntity);
		return R.ok().put("data", map);
	}
	/**
	 * 会员余额宝收益明细接口
	 * @param token
	 * @return
	 */
	@GetMapping("/getUserYuEBaoProfitDetail")
	public R getUserYuEBaoProfitDetail(@RequestHeader("token") String token) {
		UserEntity user = userService.getUser(token);		
		//通过用户id以及要多少天内，查询会员余额宝收益明细记录
		UserProfitRecordEntity userProfitRecordEntity=new UserProfitRecordEntity();
		userProfitRecordEntity.setIncomeDate(DateUtils.addDayZeroPoint(new Date(),-30));
		userProfitRecordEntity.setUserId(user.getId());
		List<Map<String, Object>> listresult=userProfitRecordDao.getUserYuEBaoProfitDetailDataDao(userProfitRecordEntity);
		if(listresult==null||listresult.size()==0) {
			return R.ok().put("data", new ArrayList<>());
		}
		return R.ok().put("data", listresult);
	}
	
}
