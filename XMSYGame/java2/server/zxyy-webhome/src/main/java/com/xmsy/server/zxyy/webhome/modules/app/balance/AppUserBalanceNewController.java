package com.xmsy.server.zxyy.webhome.modules.app.balance;

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
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.annotation.UserTransferAccountsRepeat;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.DateUtils;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.app.balance.param.SwitchOutParamNew;
import com.xmsy.server.zxyy.webhome.modules.app.balance.param.SwitchParamNew;
import com.xmsy.server.zxyy.webhome.modules.app.balance.service.AppUserBalanceService;
import com.xmsy.server.zxyy.webhome.modules.app.event.PushService;
import com.xmsy.server.zxyy.webhome.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalance.entity.UserBalanceEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalance.service.UserBalanceService;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalanceproduct.entity.UserBalanceProductEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalanceproduct.service.UserBalanceProductService;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalancerecord.entity.UserBalanceRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalancerecord.service.UserBalanceRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.userpassword.service.UserPasswordService;
import com.xmsy.server.zxyy.webhome.modules.manager.userprofitrecord.dao.UserProfitRecordDao;
import com.xmsy.server.zxyy.webhome.modules.manager.userprofitrecord.entity.UserProfitRecordEntity;

import lombok.extern.slf4j.Slf4j;


/**
 * 
 * 新版本余额宝接口
 * 
 * @author axiong  
 * @email xxxxx
 * @date 2019-06-18 11:00:25
 */
@Slf4j
@RestController
@RequestMapping("V1.0/App")
public class AppUserBalanceNewController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserBalanceService userBalanceService;
	@Autowired
	private AppUserBalanceService appUserBalanceService;	
	@Autowired
	private UserBalanceProductService userBalanceProductService;
	@Autowired
	private UserBalanceRecordService userBalanceRecordService;
	@Autowired
	private UserProfitRecordDao userProfitRecordDao;
	@Resource
	private PushService pushService;
	@Resource
	private UserPasswordService userPasswordService;
	/**
	 * 会员余额宝记录接口  1
	 * @param token
	 * @return
	 */
	@GetMapping("/getUserYuEBaoNew")
	public R getUserYuEBaoNew(@RequestHeader("token") String token) {
		UserEntity user = userService.getUser(token);
		//通过用户id查找用户余额宝金额表		
		//UserBalanceEntity userBalanceEntity=userBalanceService.getUserBalance(user);
		List<UserBalanceEntity> userBalanceList = userBalanceService.selectList(
				new EntityWrapper<UserBalanceEntity>(new UserBalanceEntity().setUserId(user.getId())));	
		Map<String,Object>  map=appUserBalanceService.getUserYuEBaoNew(user,userBalanceList);
		return R.ok().put("data", map);
	}
	/**
	 * 点击存入  取出时   通过传过来的理财产品Id获取当前产品的信息   2  3
	 * @param token
	 * @return
	 */
	@GetMapping("/userBalanceProductInfo")
	public R userBalanceProductInfo(@RequestHeader("token") String token,@RequestParam Long userBalanceProductId) {
		//获取理财产品信息   --存入取出都要
		UserBalanceProductEntity userBalanceProduct = userBalanceProductService
				.selectById(userBalanceProductId);
		//取出时  查找用户在该理财产品的 总持有金额  +总收益（累计收益）
		UserEntity user = userService.getUser(token);
		UserBalanceEntity userBalance = userBalanceService.selectOne(
				new EntityWrapper<UserBalanceEntity>(new UserBalanceEntity().setUserId(user.getId()).setUserBalanceProductId(userBalanceProductId)));	
		if(userBalance!=null) {
			userBalanceProduct.setUserBalanceTotalMoney(userBalance.getMoney()+userBalance.getUnprofitMoney());//总持有金额 
			userBalanceProduct.setUserBalanceTotalProfit(userBalance.getProfit());//总收益（累计收益）
		}else {
			userBalanceProduct.setUserBalanceTotalMoney(0L);//总持有金额 
			userBalanceProduct.setUserBalanceTotalProfit(0L);//总收益（累计收益）
		}
		//会员当天可购买次数   去查询该会员今天买了几份
		EntityWrapper<UserBalanceRecordEntity> wrapper = new EntityWrapper<UserBalanceRecordEntity>(
				new UserBalanceRecordEntity().setUserId(user.getId()).setUserBalanceProductId(userBalanceProductId)
						.setType(0));
		wrapper.ge("create_time", DateUtils.addDayZeroPoint(new Date(), 0));
		wrapper.le("create_time", new Date());
		int count = userBalanceRecordService.selectCount(wrapper);
		userBalanceProduct.setUserTodayBuyNum(userBalanceProduct.getUserTodayBuyNum()-count);
		return R.ok().put("data", userBalanceProduct);
	}
	/**
	 * 会员余额宝金额存取记录接口	4
	 * @param token
	 * @return
	 */
	@GetMapping("/userBalanceRecordNew")
	public R UserBalanceRecordEntity(@RequestHeader("token") String token) {
		// token转为userID,并通过其获取会员用户实体
		UserEntity user = userService.getUser(token);
		PageParam pageParam=new PageParam();
		pageParam.setLimit(50);
		//pageParam.setPage(1);
		Page<UserBalanceRecordEntity> page = new Page<UserBalanceRecordEntity>(pageParam.getPage(), pageParam.getLimit());
		//通过用户id查询 该用户账户金额存取记录表
		/*List<UserBalanceRecordEntity> listRecord=userBalanceRecordService.selectList(
			new EntityWrapper<UserBalanceRecordEntity>(new UserBalanceRecordEntity().setUserId(user.getId())).orderBy("id",false));*/
		List<UserBalanceRecordEntity> listRecord = userBalanceRecordService.selectPage(page, 
				new EntityWrapper<UserBalanceRecordEntity>(new UserBalanceRecordEntity().setUserId(user.getId())).orderBy("id",false))
				.getRecords();
		if(listRecord==null||listRecord.size()==0) {
			return R.ok().put("data", new ArrayList<>());
		}
		return R.ok().put("data", appUserBalanceService.getMapUserBalanceRecord(listRecord));
	}
	/**
	 * 会员购买的理财产品信息 接口    5
	 * @param token
	 * @return
	 */
	@GetMapping("/userBalanceProductList")
	public R userBalanceProductList(@RequestHeader("token") String token) {
		UserEntity user = userService.getUser(token);
		//通过用户id查找用户余额宝金额表		
		//UserBalanceEntity userBalanceEntity=userBalanceService.getUserBalance(user);
		List<UserBalanceEntity> userBalanceList = userBalanceService.selectList(
				new EntityWrapper<UserBalanceEntity>(new UserBalanceEntity().setUserId(user.getId())));	
		List<Map<String,Object>>  list=appUserBalanceService.getBalanceProductList(user,userBalanceList);
		return R.ok().put("data", list);
	}
	/**
	 * 会员余额宝收益明细接口	 只查询近30天 	6
	 * @param token
	 * @return
	 */
	@GetMapping("/userYuEBaoProfitDetailNew")
	public R UserYuEBaoProfitDetailNew(@RequestHeader("token") String token) {
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
	
	/**
	 * 用户金币转入接口		--7
	 * 
	 */
	@Transactional
	@UserTransferAccountsRepeat("请勿重复转入")
	@PostMapping("/switchIntoNew")
	public R swichInto( @RequestBody @Valid SwitchParamNew param,@RequestHeader("token")  String token) {
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
		//0.理财产品方面的数据校验
		UserBalanceProductEntity userBalanceProduct = userBalanceProductService
				.selectById(param.getUserBalanceProductId());
		appUserBalanceService.verificationUserBalanceProduct(param,userBalanceProduct,user);
		//1.扣除会员里的金额，并保存会员交易记录
		//userService.upadateUserSubtractCoin(user, param);
		userService.updateUserCoin(user, param.getNumber()*-1, Constant.TransactionType.YUEBAO.getValue(), Constant.TransactionDetailType.YUEBAOSWITCHINTO.getValue());

		// 2.修改(不存在就增加)用户余额宝金额表里的未收益金额	--加个理财产品id条件
		if (!userBalanceService.updateUserBalanceNew(user, param)) {
			throw new RRException(ErrorCode.UserErrorCode.USER_BALANCE_ERROR.getErrMsg(),
					ErrorCode.UserErrorCode.USER_BALANCE_ERROR.getCode());			
		}
		// 3.插入用户账户金额存取记录数据
		userBalanceRecordService.insertIntoUserBalanceRecordNew(user, param);
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
	 * 用户金币转出接口	----8
	 * @param param
	 * @param token
	 * @return
	 */
	@Transactional
	@UserTransferAccountsRepeat("请勿重复转出")
	@PostMapping("/switchOutNew")
	public R swichOutNew( @RequestBody @Valid SwitchOutParamNew param,@RequestHeader("token")  String token) {
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
		UserBalanceEntity userBalanceEntity=userBalanceService.getUserBalanceNew(user,param);		
		Long totalMoney=userBalanceEntity.getMoney()+userBalanceEntity.getUnprofitMoney();//获取总金额
		if(totalMoney<param.getNumber()) {//判断金额是否足够取出
			throw new RRException(ErrorCode.UserErrorCode.USER_COIN_NOT_ENOUGH.getErrMsg(),
					ErrorCode.UserErrorCode.USER_COIN_NOT_ENOUGH.getCode());
		}
		//2.通过用户id+理财产品id  查询 用户账户金额存取记录表 条件是 1. 类型：存入 2.是否生效：未生效的 3.money>takeMoney
		List<UserBalanceRecordEntity> listRecord=userBalanceRecordService.getListUserBalanceRecordNew(user.getId(),param);	
		//3.遍历用户账户金额存取记录表   3.1修改用户账户金额存取记录表里的数据    3.2修改用户金额表里的未收益金额 与 金额 的 数据   3.3修改用户金币信息   3.4添加转出的用户账户金额存取记录
		appUserBalanceService.upOrInUserBalanceRecordNew(user, param.getNumber(), listRecord, param,userBalanceEntity);
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
	
}
