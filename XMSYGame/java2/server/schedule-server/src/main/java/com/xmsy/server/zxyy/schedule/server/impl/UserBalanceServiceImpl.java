package com.xmsy.server.zxyy.schedule.server.impl;

import java.math.BigDecimal;
import java.util.Date;

import com.xmsy.server.zxyy.schedule.utils.Constant;
import com.xmsy.server.zxyy.schedule.utils.InviteCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.jdbc.WebHomeJdbcUtil;
import com.xmsy.server.zxyy.schedule.server.UserBalanceService;
import com.xmsy.server.zxyy.schedule.utils.DateUtils;
import com.xmsy.server.zxyy.schedule.utils.MathUtil;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service("userBalanceService")
public class UserBalanceServiceImpl implements UserBalanceService {

	@Autowired
	private WebHomeJdbcUtil jdbcUtil;
	/**
	 * 用户余额宝昨日利率
	 * @param yesterday
	 * @return
	 */
	@Override
	public JSONObject getBalanceRate(Date yesterday) {
		StringBuffer sql=new StringBuffer();
		sql.append(" select rate,effect_date effectDate from user_balance_rate");
		sql.append(" where effect_date<=? and delete_status = 0");
		sql.append(" ORDER BY effect_date DESC");
		sql.append(" LIMIT 1");
		JSONObject data=null;
		try {
			data = jdbcUtil.selectByParamReturnJsonObject(sql.toString(),yesterday);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getBalanceRate error ",e);
		}
		return data;
	}



	/**
	 * 保存昨日利率
	 * @param yesterday
	 * @param rate
	 */
	@Override
	public void saveBalanceRateYesterday(Date yesterday, BigDecimal rate) {
		StringBuffer quertSql=new StringBuffer();
		quertSql.append(" select count(*) num from user_balance_rate_day");
		quertSql.append(" where count_date=?");
		try {
			JSONObject data = jdbcUtil.selectByParamReturnJsonObject(quertSql.toString(),yesterday);
			if(data!=null && data.get("num")!=null && data.getLong("num")>0) {
				return;
			}
			StringBuffer sql=new StringBuffer();
			sql.append(" INSERT INTO user_balance_rate_day(");
			sql.append(" rate,count_date,create_time,update_time)");
			sql.append("VALUES (?,?,now(),now())");
			jdbcUtil.insert(sql.toString(),rate,yesterday);
		} catch (Exception e) {
			log.error("saveBalanceRateYesterday error ",e);
		}
	}


	@Override
	public void saveRateYesterday(Date yesterday) {
		StringBuffer querSql=new StringBuffer();
		querSql.append(" select count(*) num from user_balance_rate_day");
		querSql.append(" where count_date=?");
		StringBuffer quertSql=new StringBuffer();
		quertSql.append("SELECT id as productId , rate as rate  from   user_balance_product");
		try {
			JSONObject one = jdbcUtil.selectByParamReturnJsonObject(querSql.toString(),yesterday);
			if(one!=null && one.get("num")!=null && one.getLong("num")>0) {
				return;
			}
			JSONArray data = jdbcUtil.selectByParamReturnJsonArray(quertSql.toString());
			if(data.size()==0) {
				return;
			}
			for( int i =0;i<data.size();i++){
				JSONObject job = data.getJSONObject(i);
				BigDecimal rate =MathUtil.getBigDecimal(job.get("rate"));
				Long productId = job.getLong("productId");
				StringBuffer sql=new StringBuffer();
				sql.append(" INSERT INTO user_balance_rate_day(");
				sql.append(" rate,count_date,create_time,update_time,product_id)");
				sql.append("VALUES (?,?,now(),now(),?)");
				jdbcUtil.insert(sql.toString(),rate,yesterday,productId);
			}
		} catch (Exception e) {
			log.error("saveBalanceRateYesterday error ",e);
		}
	}



	@Override
	public JSONArray getUserBalanceList() {
		Date nowDate=DateUtils.addDayZeroPoint(new Date(), 0);
		int totalSize=countNum(nowDate);
		if(totalSize==0) {
			return null;
		}

		int pageSize = 1000;
		int page=totalSize/pageSize;
		if(totalSize%pageSize>0) {
			page++;
		}
		StringBuffer sql=new StringBuffer();
//		sql.append(" select user_id userId,user_account userAccount,money,count_money countMoney,");
//		sql.append(" unprofit_money unprofitMoney,profit,profit_yesterday profitYesterday,count_day countDay");
		sql.append(" SELECT a.id as id, a.user_id AS userId,a.user_account as userAccount,a.money as money,");
		sql.append(" a.count_money as countMoney,a.unprofit_money AS unprofitMoney,a.profit as profit,a.profit_yesterday as profitYesterday,");
		sql.append("b.rate AS rate,b.settlement_type AS settlementType,b.rate_money_max AS  rateMoneyMax,b.id productId");
		sql.append(" FROM  user_balance a LEFT JOIN user_balance_product b ON a.user_balance_product_id = b.id");
		sql.append(" where count_day<? ");
		sql.append(" LIMIT ?,?");
		JSONArray dataArray=new JSONArray();
		JSONArray temDataArray=null;
		for(int i=0;i<page;i++) {
			temDataArray=getUserBalanceList(nowDate, sql.toString(), i*pageSize, (i+1)*pageSize);
			if(temDataArray!=null && !temDataArray.isEmpty()) {
				dataArray.addAll(temDataArray);
			}
		}
		return dataArray;
	}
	private JSONArray getUserBalanceList(Date nowDate,String sql,int limitStart,int limitEnd) {
		JSONArray dataArray=null;
		try {
			dataArray = jdbcUtil.selectByParamReturnJsonArray(sql.toString(),nowDate,limitStart,limitEnd);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getBalanceRate error ",e);
		}
		return dataArray;
	}

	private int countNum(Date nowDate) {
		int totalSize=0;
		StringBuffer sql=new StringBuffer();
		sql.append(" select count(*) num from user_balance");
		sql.append(" where count_day<?");
		try {
			JSONObject data = jdbcUtil.selectByParamReturnJsonObject(sql.toString(),nowDate);
			if(data!=null && data.get("num")!=null && data.getLong("num")>0) {
				totalSize = data.getLong("num").intValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("countNum error ",e);
		}
		return totalSize;
	}

	private JSONArray getUserBalanceRecordList(int type,boolean effect,Long userId,Date nowDate,Long productId) {
		StringBuffer sql=new StringBuffer();
		sql.append(" select id,money,take_money takeMoney,effect from user_balance_record");
		sql.append(" where type = ? and effect = ? and user_id= ? and user_balance_product_id = ? and create_time < ?");

		JSONArray dataArray=null;
		try {
			dataArray = jdbcUtil.selectByParamReturnJsonArray(sql.toString(),type,effect,userId,productId,nowDate);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getUserBalanceRecordList error ",e);
		}
		return dataArray;
	}

	@Override
	public JSONArray getUserBalance() {

		StringBuffer sql=new StringBuffer();
		Date nowDate=DateUtils.addDayZeroPoint(new Date(), 0);
		sql.append(" SELECT a.id as id, a.user_id AS userId,a.user_account as userAccount,a.money as money,");
		sql.append(" a.count_money as countMoney,a.unprofit_money AS unprofitMoney,a.profit as profit,a.profit_yesterday as profitYesterday,b.bet_multiple as betMultiple,");
		sql.append("b.rate AS rate,b.settlement_type AS settlementType,b.rate_money_max AS  rateMoneyMax");
		sql.append(" FROM  user_balance a LEFT JOIN user_balance_product b ON a.user_balance_product_id = b.id");
		sql.append(" where count_day<?");
		JSONArray data=null;
		try {
			data = jdbcUtil.selectByParamReturnJsonArray(sql.toString(),nowDate);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getBalanceRate error ",e);
		}
		return data;

	}


	private JSONArray getUserCash(Long userId,boolean status) {
		StringBuffer sql=new StringBuffer();
		sql.append(" select id   from  order_cash_examine  ");
		sql.append(" where   user_id = ? and status = ?  ");
		JSONArray dataArray=null;
		try {
			dataArray = jdbcUtil.selectByParamReturnJsonArray(sql.toString(),userId,status);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getUserBalanceRecordList error ",e);
		}
		return dataArray;
	}


	@Override
	public void saveUserYesterdayProfi(Date yesterday, BigDecimal rate, JSONObject user) {
//		 userId, userAccount,money, countMoney,
//		 unprofitMoney,profit, profitYesterday, countDay
		BigDecimal money=MathUtil.getBigDecimal(user.get("money"));
		Long id = user.getLong("id");
		BigDecimal countMoney=MathUtil.getBigDecimal(user.get("countMoney"));
		Long userId = user.getLong("userId");
		String userAccount =user.getString("userAccount");
		BigDecimal rateMoneyMax =MathUtil.getBigDecimal(user.get("rateMoneyMax")) ;
		Long profit = user.getLong("profit");
		Integer settlementType = user.getInteger("settlementType");
		BigDecimal betMultiple =MathUtil.getBigDecimal(user.get("betMultiple")) ;
		Long productId = user.getLong("productId");
		//打码倍数
		BigDecimal  addMultiple =BigDecimal.ZERO;
		BigDecimal profitYesterday=BigDecimal.ZERO;
		if(countMoney.compareTo(BigDecimal.ZERO)<=0) {
			countMoney=money;
		}
		profitYesterday=countMoney.multiply(rate);
		addMultiple =  betMultiple.multiply(profitYesterday);
		//判断收益是否已超出最大收益
		if((MathUtil.getBigDecimal(profit)).compareTo(rateMoneyMax)>0){
			profitYesterday= BigDecimal.ZERO;
			addMultiple  =BigDecimal.ZERO;
		}
		JSONArray userCash = getUserCash(userId,false);
		// 是否有收益
		boolean isProfit =profitYesterday.compareTo(BigDecimal.ONE)>=0;
		BigDecimal unprofitMoney=BigDecimal.ZERO;
		JSONArray userBalanceInto = getUserBalanceRecordList(0, false, userId,DateUtils.addDateDays(yesterday, 1),productId);
		Object[][] updateUserBalanceInto=null;
		if(userBalanceInto!=null && !userBalanceInto.isEmpty()) {
			//id,money,takeMoney,effect
			updateUserBalanceInto=new Object[userBalanceInto.size()][];
			for(int i=0;i<userBalanceInto.size();i++) {
				Object[] updateObj = new Object[2];
				JSONObject data = userBalanceInto.getJSONObject(i);
				unprofitMoney=unprofitMoney.add(MathUtil.getBigDecimal(data.get("money"))
						.subtract(MathUtil.getBigDecimal(data.get("takeMoney"))));
				updateObj[0]=true;
				updateObj[1]=data.get("id");
				updateUserBalanceInto[i]=updateObj;
			}
		}

		StringBuffer updateYesterdayProfitSql=new StringBuffer();
		updateYesterdayProfitSql.append(" update  user_balance");
		updateYesterdayProfitSql.append(" set money = money +?,");
		updateYesterdayProfitSql.append(" count_money=?,");
		updateYesterdayProfitSql.append(" unprofit_money= ?,");
		updateYesterdayProfitSql.append(" profit= profit+?,");
		updateYesterdayProfitSql.append(" profit_yesterday=?,");
		updateYesterdayProfitSql.append(" count_day=now(),update_time=now()");
		updateYesterdayProfitSql.append(" where id = ? ");

		String[] sqlArray = null;
		int sqlnum = isProfit?3:1;
		if(isProfit&&settlementType==1){
			sqlnum +=3;
		}
		if(updateUserBalanceInto!=null) {
			sqlArray=new String[updateUserBalanceInto.length+sqlnum];
		}else {
			sqlArray=new String[sqlnum];
		}
		Object[][] params=new Object[sqlArray.length][];
		//更新用户的收益
		sqlArray[0]=updateYesterdayProfitSql.toString();
		Object param0[] = new Object[6];
		param0[0]=unprofitMoney.add(profitYesterday);
		param0[1]=0l;
		param0[2]=0l;
		param0[3]=profitYesterday.longValue();
		param0[4]=profitYesterday.longValue();
		param0[5]=id;
		params[0]=param0;
		if(isProfit) {
			//有收益  保存昨日收益记录
			StringBuffer insertProfitRecordSql=new StringBuffer();
			insertProfitRecordSql.append(" insert into  user_profit_record(");
			insertProfitRecordSql.append(" user_id,money,rate,profit,income_date,user_account,user_balance_product_id,create_time,update_time)");
			insertProfitRecordSql.append(" values(?,?,?,?,?,?,?,now(),now())");
			sqlArray[1]=insertProfitRecordSql.toString();
			Object param1[] = new Object[7];
			param1[0]=userId;
			param1[1]=countMoney;
			param1[2]=rate;
			param1[3]=profitYesterday.longValue();
			param1[4]=yesterday;
			param1[5]=userAccount;
			param1[6]=productId;
			params[1]=param1;
			//查询用户层级
			JSONObject userhierarchy = getUserhierarchy(userId);
			//存款金额
			Long rechargeAmount = 0l;
			//充值订单
			String  orderNo  =(System.nanoTime() + InviteCode.create());
			// 优惠金额
			BigDecimal discountAmount = BigDecimal.ZERO;

			// 层级优惠打码倍数
			BigDecimal hierarchyDiscountMultiple = MathUtil.getBigDecimal(userhierarchy.get("discountMultiple"));
			// 层级优惠打码
			BigDecimal hierarchyDiscountBet = discountAmount.multiply(hierarchyDiscountMultiple);
			// 层级常态打码倍数
			BigDecimal hierarchyNormalMultiple = MathUtil.getBigDecimal(userhierarchy.get("rechargeMultiple"));
			BigDecimal hierarchyNormalBet = addMultiple.multiply(hierarchyNormalMultiple);
			// 层级放宽打码比例
			BigDecimal hierarchyRelaxationRate = MathUtil.getBigDecimal(userhierarchy.get("relaxationRate"));
			// 层级放宽打码： （层级优惠打码+层级常态打码)*层级放宽打码比例
			BigDecimal hierarchyRelaxationBet = (hierarchyDiscountBet.add(hierarchyNormalBet))
					.multiply(hierarchyRelaxationRate);
			BigDecimal amount = MathUtil.getBigDecimal(userhierarchy.get("money"));
			BigDecimal userMoney = addMultiple.add(amount).add(discountAmount);
			// 层级行政费率
			BigDecimal administrativeRate = MathUtil.getBigDecimal(userhierarchy.get("administrativeRate"));
			BigDecimal  hierarchyAdministrativeAmount =administrativeRate.multiply(userMoney);
			// 总需要打码： （层级优惠打码+层级常态打码+上次缺少的打码量-上次剩余打码-层级放宽打码)
			BigDecimal userNeedBet = hierarchyDiscountBet.add(hierarchyNormalBet).subtract(hierarchyRelaxationBet);
			userNeedBet = userNeedBet.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : userNeedBet;
//				Long userValidBet = getUserValidBet(user.getId(), user.getCreateTime());
			// 用户有效打码
			BigDecimal userValidBet = new BigDecimal(0 )
					.divide(new BigDecimal(Constant.COIN_RATE));

			if (userCash.size() > 0) {
					JSONObject cash = userCash.getJSONObject(0);
					Long cashId = cash.getLong("id");
					StringBuffer sql=new StringBuffer();
					sql.append(" update  order_cash_examine");
					sql.append(" set user_need_bet = user_need_bet+?, ");
				    sql.append("  update_time = now()");
				 	sql.append(" where id = ?");
					sqlArray[2]=sql.toString();
				sqlArray[2]=sql.toString();
				Object param2[] = new Object[2];
				param2[0]=addMultiple;
				param2[1]=cashId;
				params[2]=param2;
				}
			if(userCash.size()==0) {
				StringBuffer sql = new StringBuffer();
				sql.append(" insert into  order_cash_examine(");
				sql.append(" user_id,user_account,user_need_bet,order_time,order_no,recharge_amount,hierarchy_discount_amount,hierarchy_discount_multiple,");
				sql.append("hierarchy_discount_bet,hierarchy_relaxation_rate,hierarchy_relaxation_bet,hierarchy_normal_multiple,hierarchy_normal_bet, ");
//				sql.append(" )");
				sql.append("user_valid_bet,hierarchy_administrative_rate,hierarchy_administrative_amount,user_money,create_time,update_time)");
				sql.append("VALUES (?,?,?,now(),?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now())");
				sqlArray[2] = sql.toString();
				Object param2[] = new Object[16];
				param2[0] = userId;
				param2[1] = userAccount;
				param2[2] = addMultiple;
				param2[3] = orderNo;
				param2[4] = rechargeAmount;
				param2[5] = discountAmount;
				param2[6] = hierarchyDiscountMultiple;
				param2[7] = hierarchyDiscountBet;
				param2[8] = hierarchyRelaxationRate;
				param2[9] = hierarchyRelaxationBet;
				param2[10] = hierarchyNormalMultiple;
				param2[11] = hierarchyNormalBet;
				param2[12] = userValidBet;
				param2[13] = administrativeRate;
				param2[14] = hierarchyAdministrativeAmount;
				param2[15] = userMoney;

				params[2] = param2;
			}
				if(settlementType==1){
					StringBuffer balancesql=new StringBuffer();
					Long newMoney =0l;
					balancesql.append(" update  user_balance");
					balancesql.append(" set money=?");
					balancesql.append(" where id=? ");
					sqlArray[3]=balancesql.toString();
					Object param3[] = new Object[2];
					param3[0]=newMoney;
					param3[1]=id;
					params[3]=param3;

					StringBuffer usersql=new StringBuffer();

					usersql.append(" update  user");
					usersql.append(" set coin=coin+?");
					usersql.append(" where id = ? ");
					sqlArray[4]=usersql.toString();
					Object param4[] = new Object[2];
					param4[0]=money.add(unprofitMoney).add(profitYesterday).longValue();
					param4[1]=userId;
					params[4]= param4;

					StringBuffer  BalanceRecordSql=new StringBuffer();
					BalanceRecordSql.append(" insert into  user_balance_record(");
					BalanceRecordSql.append(" money,user_id,user_account,take_money,user_balance_product_id,type,create_time,update_time)");
					BalanceRecordSql.append(" values(?,?,?,?,?,?,now(),now())");
					sqlArray[5]=BalanceRecordSql.toString();
					Object param5[] = new Object[6];
					param5[0]=(money.add(unprofitMoney).add(profitYesterday)).longValue();
					param5[1]=userId;
					param5[2]=userAccount;
					param5[3]=(money.add(unprofitMoney).add(profitYesterday)).longValue();
					param5[4]=productId;
					param5[5]=1l;
					params[5]= param5;
				}

//				insertUserCash(userId,userAccount,addMultiple);
			}
		if(updateUserBalanceInto!=null) {
			StringBuffer updateUserBalanceRecordSql=new StringBuffer();
			updateUserBalanceRecordSql.append(" update  user_balance_record");
			updateUserBalanceRecordSql.append(" set effect=?,update_time=now()");
			updateUserBalanceRecordSql.append(" where id=? ");
			int i=sqlnum;
			for(Object[] UserBalance:updateUserBalanceInto) {
				sqlArray[i]=updateUserBalanceRecordSql.toString();
				params[i]=UserBalance;
				i++;
			}


		}
		jdbcUtil.transactionExecuteUpdate(sqlArray, params);

	}
	/**
	 * 重置昨日收益为0
	 */
	@Override
	public void resetUserYesterdayProfit() {
		StringBuffer sql=new StringBuffer();
		sql.append(" update  user_balance");
		sql.append(" set profit_yesterday=0");
		sql.append(" where profit_yesterday>0");
		jdbcUtil.update(sql.toString());
	}
   private  JSONObject  getUserhierarchy(Long userId){
	   StringBuffer sql=new StringBuffer();
	   sql.append("SELECT a.money as money,a.coin as coin , b.recharge_multiple rechargeMultiple,b.discount_multiple  discountMultiple,b.administrative_rate administrativeRate,");
	   sql.append("b.relaxation_rate relaxationRate FROM user a ");
	   sql.append("LEFT JOIN user_hierarchy  b ON a.hierarchy_id = b.id ");
	   sql.append("where a.id = ?");
	   JSONObject jsonObject = null;
	   try {
		   jsonObject = jdbcUtil.selectByParamReturnJsonObject(sql.toString(), userId);
	   } catch (Exception e) {
		   e.printStackTrace();
	   }
	   return jsonObject;


   }




}
