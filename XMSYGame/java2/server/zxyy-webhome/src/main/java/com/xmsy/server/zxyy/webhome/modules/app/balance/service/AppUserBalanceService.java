package com.xmsy.server.zxyy.webhome.modules.app.balance.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.DateUtils;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.MathUtil;
import com.xmsy.server.zxyy.webhome.modules.app.balance.param.SwitchOutParam;
import com.xmsy.server.zxyy.webhome.modules.app.balance.param.SwitchOutParamNew;
import com.xmsy.server.zxyy.webhome.modules.app.balance.param.SwitchParamNew;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalance.dao.UserBalanceDao;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalance.entity.UserBalanceEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalanceproduct.entity.UserBalanceProductEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalanceproduct.service.UserBalanceProductService;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalancerate.entity.UserBalanceRateEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalancerate.service.UserBalanceRateService;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalancerecord.entity.UserBalanceRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalancerecord.service.UserBalanceRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.userprofitrecord.entity.UserProfitRecordEntity;

@Service("appUserBalanceService")
public class AppUserBalanceService {
	@Autowired
	private UserBalanceRecordService userBalanceRecordService;
	@Autowired
	private UserBalanceDao userBalanceDao;
	@Autowired
	private UserService userService;
	@Autowired
	private UserBalanceRateService userBalanceRateService;
	@Autowired
	private UserBalanceProductService userBalanceProductService;

	/**
	 * 遍历用户账户金额存取记录表 3.1修改用户账户金额存取记录表里的数据 3.2修改用户金额表里的未收益金额 与 金额 的 数据 3.3修改用户金币信息
	 * 3.4添加转出的用户账户金额存取记录
	 * 
	 * @param user
	 * @param tNum
	 *            转出的金额
	 * @param listRecord
	 * @return
	 */
	public void upOrInUserBalanceRecord(UserEntity user, Long tNum, List<UserBalanceRecordEntity> listRecord,
			SwitchOutParam param, UserBalanceEntity userBalanceEntity) {
		if (listRecord != null && !listRecord.isEmpty()) {// 一：还存在未收益金额 先从未收益金额里面扣
			List<UserBalanceRecordEntity> updateList = new ArrayList<>();
			for (UserBalanceRecordEntity userRecord : listRecord) {// 遍历用户账户金额存取记录表
				if (userRecord.getMoney() - userRecord.getTakeMoney() > tNum) {// 剩余未收益金额>要转出
					userRecord.setTakeMoney(tNum + userRecord.getTakeMoney());
					tNum = 0l;
				} else {
					tNum = tNum - (userRecord.getMoney() - userRecord.getTakeMoney());
					userRecord.setTakeMoney(userRecord.getMoney());
				}
				updateList.add(userRecord);
				if (tNum == 0) {
					break;
				}
			}
			// 3.1修改用户账户金额存取记录表里的数据
			if (!userBalanceRecordService.updateBatchById(updateList)) {
				throw new RRException(ErrorCode.UserErrorCode.USER_BALANCE_RECORD_ERROR.getErrMsg(),
						ErrorCode.UserErrorCode.USER_BALANCE_RECORD_ERROR.getCode());
			}
		}
		// 3.2修改用户金额表里的未收益金额 与 金额 的 数据
		UserBalanceEntity updateUserBalance = new UserBalanceEntity();
		if (userBalanceEntity.getCountDay().before(DateUtils.addDayZeroPoint(new Date(), 0))
				&& userBalanceEntity.getCountMoney() <= 0) {// 判断昨日的收益
			updateUserBalance.setCountMoney(userBalanceEntity.getMoney());
		}
		updateUserBalance.setUserId(user.getId());// 通过用户id去增加或者减去 传入实体类用户里的金额
		if (tNum > 0) {
			updateUserBalance.setUnprofitMoney(userBalanceEntity.getUnprofitMoney() * -1);
			updateUserBalance.setMoney(tNum * -1);
		} else {
			updateUserBalance.setUnprofitMoney(param.getNumber() * -1);
		}
		if (userBalanceDao.updateUserBalanceByUserId(updateUserBalance) <= 0) {
			throw new RRException(ErrorCode.UserErrorCode.USER_BALANCE_ERROR.getErrMsg(),
					ErrorCode.UserErrorCode.USER_BALANCE_ERROR.getCode());
		}
		// 3.3修改用户金币信息
		/*
		 * UserEntity userEntity=new UserEntity(); userEntity.setId(user.getId());
		 * userEntity.setCoin(param.getNumber());
		 * userService.updateUserWalletByUserId(userEntity);
		 */
		userService.updateUserCoin(user, param.getNumber(), Constant.TransactionType.YUEBAO.getValue(),
				Constant.TransactionDetailType.YUEBAOSWITCHOUT.getValue());
		// 3.4添加转出的用户账户金额存取记录
		UserBalanceRecordEntity outUserBalanceRecord = new UserBalanceRecordEntity();
		outUserBalanceRecord.setUserId(user.getId());
		outUserBalanceRecord.setUserAccount(user.getAccount());
		outUserBalanceRecord.setMoney(param.getNumber());
		outUserBalanceRecord.setTakeMoney(param.getNumber());
		outUserBalanceRecord.setEffect(false);
		outUserBalanceRecord.setType(1);
		userBalanceRecordService.insert(outUserBalanceRecord);
	}

	/**
	 * 获取会员余额宝金额存取记录 money time remark type
	 * 
	 * @param listRecord
	 * @return
	 */
	public List<Map<String, Object>> getMapUserBalanceRecord(List<UserBalanceRecordEntity> listRecord) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (UserBalanceRecordEntity userBalanceRecord : listRecord) {
			map = new HashMap<String, Object>();
			map.put("money", userBalanceRecord.getMoney());
			map.put("time", userBalanceRecord.getCreateTime());
			map.put("remark", "完成");
			map.put("type", userBalanceRecord.getType());// 类型 0:存入1:取出
			UserBalanceProductEntity userBalanceProduct = userBalanceProductService
					.selectById(userBalanceRecord.getUserBalanceProductId());
			map.put("userBalanceProductName", userBalanceProduct.getProductName());
			list.add(map);
		}

		return list;
	}

	/**
	 * 获取余额页面所需的各种数据
	 * 
	 * @param user
	 * @param userBalanceEntity
	 * @return
	 */
	public Map<String, Object> getUserYuEBao(UserEntity user, UserBalanceEntity userBalanceEntity) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (userBalanceEntity == null || userBalanceEntity.getId() == null) {
			map.put("totalMoney", Constant.DEFAULT);// 总金额 默认为0
			map.put("profit", Constant.DEFAULT);// 总收益(累计收益) 默认为0
			map.put("profit_yesterday", Constant.DEFAULT);// 昨日收益(每日收益) 默认为0
		} else {
			map.put("totalMoney", userBalanceEntity.getMoney() + userBalanceEntity.getUnprofitMoney());// 总金额
			map.put("profit", userBalanceEntity.getProfit());// 总收益(累计收益)
			map.put("profit_yesterday", userBalanceEntity.getProfitYesterday());// 昨日收益(每日收益)
		}
		// 去查询当前的利率
		UserBalanceRateEntity userBalanceRateEntity = userBalanceRateService
				.selectOne(new EntityWrapper<UserBalanceRateEntity>(new UserBalanceRateEntity())
						.le("effect_date", new Date()).orderBy("effect_date", false));
		map.put("rate", userBalanceRateEntity == null || userBalanceRateEntity.getRate() == null ? 0
				: userBalanceRateEntity.getRate());
		return map;
	}

	/**
	 * 获取收益记录页面所需要的数据
	 * 
	 * @param listUserProfitRecord
	 * @return
	 */
	public List<Map<String, Object>> getUserYuEBaoProfitDetailData(List<UserProfitRecordEntity> listUserProfitRecord) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (UserProfitRecordEntity userProfitRecord : listUserProfitRecord) {
			map = new HashMap<String, Object>();
			map.put("money", userProfitRecord.getMoney());// 金额
			map.put("rate", userProfitRecord.getRate());// 利率
			map.put("profit", userProfitRecord.getProfit());// 收益
			map.put("time", userProfitRecord.getIncomeDate());// 收益的日期
			list.add(map);
		}
		return list;

	}

	// ===========================================新版本余额宝==========================================
	/**
	 * 获取余额页面所需的各种数据
	 * @param user
	 * @param userBalanceEntity
	 * @return
	 */
	public Map<String, Object> getUserYuEBaoNew(UserEntity user, List<UserBalanceEntity> userBalanceList) {
		Map<String,Object> map=new HashMap<String,Object>();
		//获取 所有理财产品信息
		List<UserBalanceProductEntity> userBalanceProductList = userBalanceProductService
				.selectList(new EntityWrapper<UserBalanceProductEntity>().orderBy("order_num", true));
		if(userBalanceList==null || userBalanceList.size()==0) {//用户没有任何理财产品
			map.put("totalMoney",Constant.DEFAULT);//总金额(理总财金额)  默认为0
			map.put("totalprofit", Constant.DEFAULT);//总收益(总累计收益) 默认为0
			map.put("profitYesterday",Constant.DEFAULT);//总昨日收益(总每日收益) 默认为0
			map.put("profitToday",Constant.DEFAULT);//总今日预计收益 默认为0
			List<Map<String,Object>> userBalanceProductListMap=new  ArrayList<>();//理财产品信息集合
			Map<String,Object> userBalanceProductmap=null;//理财产品信息
			for(UserBalanceProductEntity userBalanceProductEntity:userBalanceProductList) {
				//每个理财产品对应信息
				userBalanceProductmap=new HashMap<String,Object>();//理财产品信息
				userBalanceProductmap.put("userBalanceProductId", userBalanceProductEntity.getId());//方案Id
				userBalanceProductmap.put("userBalanceProductName", userBalanceProductEntity.getProductName());//方案名称
				userBalanceProductmap.put("userBalanceProductRate", userBalanceProductEntity.getRate());//利率
				userBalanceProductmap.put("userBalanceProductMoney", 0);//金额
				userBalanceProductmap.put("userBalanceProductOrderNum", userBalanceProductEntity.getOrderNum());//排序
				userBalanceProductmap.put("userBalanceProductRemainMoney", userBalanceProductEntity.getRemaindBuyNum());//剩余可购买金额
				userBalanceProductmap.put("userBalanceProductEnable", userBalanceProductEntity.getEnable());//状态(0:禁用，1:启用)
				userBalanceProductListMap.add(userBalanceProductmap);
			}
			map.put("userBalanceProduct", userBalanceProductListMap);//每个理财产品对应信息
		}else {
			Long totalMoney=0L;
			Long totalProfit=0L;
			Long totalProfitYesterday=0L;
			Long totalProfitToday=0L;
			List<Map<String,Object>> userBalanceProductListMap=new  ArrayList<>();//理财产品信息集合
			Map<String,Object> userBalanceProductmap=null;//理财产品信息
			List<Long> idList=new ArrayList<>();//理财产品id集合  
			for(UserBalanceEntity userBalanceEntity:userBalanceList) {
				totalMoney+=userBalanceEntity.getMoney()+userBalanceEntity.getUnprofitMoney();
				totalProfit+=userBalanceEntity.getProfit();
				totalProfitYesterday+=userBalanceEntity.getProfitYesterday();
				//获取 理财产品信息
				UserBalanceProductEntity userBalanceProduct = userBalanceProductService
						.selectById(userBalanceEntity.getUserBalanceProductId());
				idList.add(userBalanceEntity.getUserBalanceProductId());//把当前理财产品id放进id集合
				//计算今日预计收益
				if(userBalanceProduct.getEnable()) {}//理财产品开启状态
				totalProfitToday+=MathUtil.getBigDecimal(userBalanceEntity.getMoney()).multiply(userBalanceProduct.getRate()).longValue();
				//每个理财产品对应信息
				userBalanceProductmap=new HashMap<String,Object>();//理财产品信息
				userBalanceProductmap.put("userBalanceProductId", userBalanceProduct.getId());//方案Id
				userBalanceProductmap.put("userBalanceProductName", userBalanceProduct.getProductName());//方案名称
				userBalanceProductmap.put("userBalanceProductRate", userBalanceProduct.getRate());//利率
				userBalanceProductmap.put("userBalanceProductMoney", userBalanceEntity.getMoney()+userBalanceEntity.getUnprofitMoney());//理财金额
				userBalanceProductmap.put("userBalanceProductOrderNum", userBalanceProduct.getOrderNum());//排序
				userBalanceProductmap.put("userBalanceProductRemainMoney", userBalanceProduct.getRemaindBuyNum());//剩余可购买金额
				userBalanceProductmap.put("userBalanceProductEnable", userBalanceProduct.getEnable());//状态(0:禁用，1:启用)
				userBalanceProductListMap.add(userBalanceProductmap);
			}
			// 查找会员没有购买的理财产品信息
			for (UserBalanceProductEntity userBalanceProductEntity : userBalanceProductList) {
				if (!idList.contains(userBalanceProductEntity.getId())) {
					userBalanceProductmap = new HashMap<String, Object>();// 理财产品信息
					userBalanceProductmap.put("userBalanceProductId", userBalanceProductEntity.getId());// 方案Id
					userBalanceProductmap.put("userBalanceProductName", userBalanceProductEntity.getProductName());// 方案名称
					userBalanceProductmap.put("userBalanceProductRate", userBalanceProductEntity.getRate());// 利率
					userBalanceProductmap.put("userBalanceProductMoney", 0);// 理财金额
					userBalanceProductmap.put("userBalanceProductOrderNum", userBalanceProductEntity.getOrderNum());//排序
					userBalanceProductmap.put("userBalanceProductRemainMoney", userBalanceProductEntity.getRemaindBuyNum());//剩余可购买金额
					userBalanceProductmap.put("userBalanceProductEnable", userBalanceProductEntity.getEnable());//状态(0:禁用，1:启用)
					userBalanceProductListMap.add(userBalanceProductmap);
				}
			}
			map.put("totalMoney",totalMoney);//总金额(理财金额)
			map.put("totalProfit",totalProfit);//总收益(累计收益)
			map.put("totalProfitYesterday",totalProfitYesterday);//总昨日收益(每日收益)
			map.put("totalProfitToday",totalProfitToday);//总今日预计收益
			map.put("userBalanceProduct", userBalanceProductListMap);
		}
		map.put("nowCoin", user.getCoin());
		return map;
	}
	/**
	 * 获取对应会员理财产品详细信息
	 * @param user
	 * @param userBalanceList
	 * @return
	 */
	public List<Map<String,Object>> getBalanceProductList(UserEntity user, List<UserBalanceEntity> userBalanceList) {
		List<Map<String,Object>> userBalanceProductListMap=new  ArrayList<>();//理财产品信息集合
		if(userBalanceList==null || userBalanceList.size()==0) {//用户没有任何理财产品
			
		}else {
			Map<String,Object> userBalanceProductmap=null;//理财产品信息
			List<Long> idList=new ArrayList<>();//理财产品id集合  
			for(UserBalanceEntity userBalanceEntity:userBalanceList) {
				if(userBalanceEntity.getMoney()!=0||userBalanceEntity.getUnprofitMoney()!=0) {//理财金额为0的不显示
					//获取 理财产品信息
					UserBalanceProductEntity userBalanceProduct = userBalanceProductService
							.selectById(userBalanceEntity.getUserBalanceProductId());
					idList.add(userBalanceEntity.getUserBalanceProductId());//把当前理财产品id放进id集合
					//每个理财产品对应信息
					userBalanceProductmap=new HashMap<String,Object>();//理财产品信息
					userBalanceProductmap.put("userBalanceProductId", userBalanceProduct.getId());//方案Id
					userBalanceProductmap.put("userBalanceProductName", userBalanceProduct.getProductName());//方案名称
					//userBalanceProductmap.put("userBalanceProductRate", userBalanceProduct.getRate());//利率
					userBalanceProductmap.put("userBalanceProductMoney", userBalanceEntity.getMoney()+userBalanceEntity.getUnprofitMoney());//投资金额
					userBalanceProductmap.put("profitYesterday", userBalanceEntity.getProfitYesterday());//昨日收益
					userBalanceProductmap.put("userBalanceProductProfit", userBalanceEntity.getProfit());//总收益（累计收益）
					userBalanceProductmap.put("userBalanceProductRemainMoney", userBalanceProduct.getRemaindBuyNum());//剩余可购买金额
					userBalanceProductmap.put("userBalanceProductEnable", userBalanceProduct.getEnable());//状态(0:禁用，1:启用)
					userBalanceProductListMap.add(userBalanceProductmap);
				}
			}
		}
		return userBalanceProductListMap;
	}
	/**
	 * 转入时  对理财产品实体类里各个参数做数据校验
	 * @param userBalanceProduct
	 * @param user
	 */
	public void verificationUserBalanceProduct(SwitchParamNew param, UserBalanceProductEntity userBalanceProduct,
			UserEntity user) {
		//1. 产品是否启用
		if (!userBalanceProduct.getEnable()) {
			throw new RRException(ErrorCode.UserBalance.USER_BALANCE_PRODUCT_ERROR.getErrMsg(),
					ErrorCode.UserBalance.USER_BALANCE_PRODUCT_ERROR.getCode());
		}
		//2.金币范围限制
		if(param.getNumber()<userBalanceProduct.getMinMoney()||param.getNumber()>userBalanceProduct.getMaxMoney()) {
			throw new RRException(ErrorCode.UserBalance.USER_BALANCE_SWITH_COIN_ERROR.getErrMsg(),
					ErrorCode.UserBalance.USER_BALANCE_SWITH_COIN_ERROR.getCode());
		}
		//3.剩余可买金额  要大于  购买金额
		if (userBalanceProduct.getRemaindBuyNum()<param.getNumber()) {
			throw new RRException(ErrorCode.UserBalance.USER_BALANCE_REMAIN_BUY_NUM_ERRO.getErrMsg(),
					ErrorCode.UserBalance.USER_BALANCE_REMAIN_BUY_NUM_ERRO.getCode());
		}
		//4.会员当天可购买次数   去查询该会员今天买了几份
		EntityWrapper<UserBalanceRecordEntity> wrapper = new EntityWrapper<UserBalanceRecordEntity>(
				new UserBalanceRecordEntity().setUserId(user.getId())
						.setUserBalanceProductId(param.getUserBalanceProductId())
						.setType(0));
		wrapper.ge("create_time", DateUtils.addDayZeroPoint(new Date(),0));
		wrapper.le("create_time", new Date());
		int count = userBalanceRecordService.selectCount(wrapper);
		if (userBalanceProduct.getUserTodayBuyNum()<=count) {
			throw new RRException(ErrorCode.UserBalance.USER_BALANCE_USER_TODAY_BUY_NUM_ERRO.getErrMsg(),
					ErrorCode.UserBalance.USER_BALANCE_USER_TODAY_BUY_NUM_ERRO.getCode());
		}
		
		UserBalanceProductEntity userBalanceProductupdate=new UserBalanceProductEntity();
		userBalanceProductupdate.setId(userBalanceProduct.getId());
		userBalanceProductupdate.setRemaindBuyNum(userBalanceProduct.getRemaindBuyNum()-param.getNumber());//剩余可买金额要减去购买的金额
		userBalanceProductupdate.updateById();
	}
	
	/**
	 * 遍历用户账户金额存取记录表 3.1修改用户账户金额存取记录表里的数据 3.2修改用户金额表里的未收益金额 与 金额 的 数据 3.3修改用户金币信息
	 * 3.4添加转出的用户账户金额存取记录
	 * 
	 * @param user
	 * @param tNum	转出的金额
	 * @param listRecord
	 * @return
	 */
	public void upOrInUserBalanceRecordNew(UserEntity user, Long tNum, List<UserBalanceRecordEntity> listRecord,
			SwitchOutParamNew param, UserBalanceEntity userBalanceEntity) {
		if (listRecord != null && !listRecord.isEmpty()) {// 一：还存在未收益金额 先从未收益金额里面扣
			List<UserBalanceRecordEntity> updateList = new ArrayList<>();
			for (UserBalanceRecordEntity userRecord : listRecord) {// 遍历用户账户金额存取记录表
				if (userRecord.getMoney() - userRecord.getTakeMoney() > tNum) {// 剩余未收益金额>要转出
					userRecord.setTakeMoney(tNum + userRecord.getTakeMoney());
					tNum = 0l;
				} else {
					tNum = tNum - (userRecord.getMoney() - userRecord.getTakeMoney());
					userRecord.setTakeMoney(userRecord.getMoney());
				}
				updateList.add(userRecord);
				if (tNum == 0) {
					break;
				}
			}
			// 3.1修改用户账户金额存取记录表里的数据
			if (!userBalanceRecordService.updateBatchById(updateList)) {
				throw new RRException(ErrorCode.UserErrorCode.USER_BALANCE_RECORD_ERROR.getErrMsg(),
						ErrorCode.UserErrorCode.USER_BALANCE_RECORD_ERROR.getCode());
			}
		}
		// 3.2修改用户金额表里的未收益金额 与 金额 的 数据
		UserBalanceEntity updateUserBalance = new UserBalanceEntity();
		if (userBalanceEntity.getCountDay().before(DateUtils.addDayZeroPoint(new Date(), 0))
				&& userBalanceEntity.getCountMoney() <= 0) {// 判断昨日的收益
			updateUserBalance.setCountMoney(userBalanceEntity.getMoney());
		}
		updateUserBalance.setUserId(user.getId());// 通过用户id+理财产品id  去增加或者减去 传入实体类用户里的金额
		updateUserBalance.setUserBalanceProductId(param.getUserBalanceProductId());//理财产品id
		if (tNum > 0) {
			updateUserBalance.setUnprofitMoney(userBalanceEntity.getUnprofitMoney() * -1);
			updateUserBalance.setMoney(tNum * -1);
		} else {
			updateUserBalance.setUnprofitMoney(param.getNumber() * -1);
		}
		if (userBalanceDao.updateUserBalanceByUserIdNew(updateUserBalance) <= 0) {
			throw new RRException(ErrorCode.UserErrorCode.USER_BALANCE_ERROR.getErrMsg(),
					ErrorCode.UserErrorCode.USER_BALANCE_ERROR.getCode());
		}
		// 3.3修改用户金币信息
		/*
		 * UserEntity userEntity=new UserEntity(); userEntity.setId(user.getId());
		 * userEntity.setCoin(param.getNumber());
		 * userService.updateUserWalletByUserId(userEntity);
		 */
		userService.updateUserCoin(user, param.getNumber(), Constant.TransactionType.YUEBAO.getValue(),
				Constant.TransactionDetailType.YUEBAOSWITCHOUT.getValue());
		// 3.4添加转出的用户账户金额存取记录
		UserBalanceRecordEntity outUserBalanceRecord = new UserBalanceRecordEntity();
		outUserBalanceRecord.setUserId(user.getId());
		outUserBalanceRecord.setUserAccount(user.getAccount());
		outUserBalanceRecord.setUserBalanceProductId(param.getUserBalanceProductId());
		outUserBalanceRecord.setMoney(param.getNumber());
		outUserBalanceRecord.setTakeMoney(param.getNumber());
		outUserBalanceRecord.setEffect(false);
		outUserBalanceRecord.setType(1);
		userBalanceRecordService.insert(outUserBalanceRecord);
	}
}
