package com.xmsy.server.zxyy.webhome.modules.manager.pooldispatchtask.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.Constant.TransactionDetailType;
import com.xmsy.server.zxyy.webhome.common.utils.Constant.TransactionType;
import com.xmsy.server.zxyy.webhome.common.utils.DateUtils;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.MathUtil;
import com.xmsy.server.zxyy.webhome.common.utils.OrderNoUtil;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.Query;
import com.xmsy.server.zxyy.webhome.modules.manager.messagemanagement.dao.MessageManagementDao;
import com.xmsy.server.zxyy.webhome.modules.manager.messagemanagement.entity.MessageManagementEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.messageuser.dao.MessageUserDao;
import com.xmsy.server.zxyy.webhome.modules.manager.messageuser.entity.MessageUserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.pooldispatchdetaillist.dao.PoolDispatchDetailListDao;
import com.xmsy.server.zxyy.webhome.modules.manager.pooldispatchdetaillist.entity.PoolDispatchDetailListEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.pooldispatchtask.dao.PoolDispatchTaskDao;
import com.xmsy.server.zxyy.webhome.modules.manager.pooldispatchtask.entity.PoolDispatchTaskEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.pooldispatchtask.service.PoolDispatchTaskService;
import com.xmsy.server.zxyy.webhome.modules.manager.pooldispatchtaskdetail.dao.PoolDispatchTaskDetailDao;
import com.xmsy.server.zxyy.webhome.modules.manager.pooldispatchtaskdetail.entity.PoolDispatchTaskDetailEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.poolgame.dao.PoolGameDao;
import com.xmsy.server.zxyy.webhome.modules.manager.poolgame.entity.PoolGameEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.dao.RankingListDao;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.entity.RankingListEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.vo.RankingListRequestVo;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.vo.RankingListUserVo;
import com.xmsy.server.zxyy.webhome.modules.manager.user.dao.UserDao;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.usertransactionrecord.dao.UserTransactionRecordDao;
import com.xmsy.server.zxyy.webhome.modules.manager.usertransactionrecord.entity.UserTransactionRecordEntity;

@Transactional
@Service("poolDispatchTaskService")
public class PoolDispatchTaskServiceImpl extends ServiceImpl<PoolDispatchTaskDao, PoolDispatchTaskEntity> implements PoolDispatchTaskService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private RankingListDao rankingListDao;
	@Autowired
	private PoolGameDao poolGameDao;
	@Autowired
	private PoolDispatchTaskDetailDao poolDispatchTaskDetailDao;
	@Autowired
	private PoolDispatchDetailListDao poolDispatchDetailListDao;
	@Autowired
	private UserTransactionRecordDao userTransactionRecordDao;
	@Autowired
	private MessageManagementDao messageManagementDao;
	
	@Autowired
	private MessageUserDao messageUserDao;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        PoolDispatchTaskEntity entity=new PoolDispatchTaskEntity();
    	if(params !=null) {
    		if(params.get("poolGameId")!=null && !"".equals(params.get("poolGameId").toString().trim())) {
    			entity.setPoolGameId(Long.parseLong(params.get("poolGameId").toString().trim()));
    		}
    		if(params.get("rankingListId")!=null && !"".equals(params.get("rankingListId").toString().trim())) {
    			entity.setRankingListId(Long.parseLong(params.get("rankingListId").toString().trim()));
    		}
    		if(params.get("key")!=null && !"".equals(params.get("key").toString().trim())) {
    			entity.setTitle(params.get("key").toString().trim());
    		}
    	}
    	Wrapper<PoolDispatchTaskEntity> wrapper = new EntityWrapper<PoolDispatchTaskEntity>(entity);
    	String sort=params.get("sort")!=null?params.get("sort").toString():"";
    	if (!StringUtils.isEmpty(sort)) {
    		String direction=params.get("direction")!=null?params.get("direction").toString():"";
    		if ("desc".equalsIgnoreCase(direction)) {
    			wrapper.orderDesc(Arrays.asList(sort.split(",")));
    		} else {
    			wrapper.orderAsc(Arrays.asList(sort.split(",")));
    		}
		}
    	Page<PoolDispatchTaskEntity> page = this.selectPage(
                new Query<PoolDispatchTaskEntity>(params).getPage(),
                wrapper
        );

        return new PageUtils(page);
    }

	@Override
	@Transactional
	public void dispatch(Long id) {
		//1.查询任务
		PoolDispatchTaskEntity task = this.selectById(id);
    	if(task==null) {
    		throw new RRException(ErrorCode.PoolDispatchErrorCode.POOLDISPATCHTASKISNOTFIND_ERRO.getErrMsg()
    				,ErrorCode.PoolDispatchErrorCode.POOLDISPATCHTASKISNOTFIND_ERRO.getCode());
		}
    	//2.查询奖金池总额
    	PoolGameEntity poolGame = poolGameDao.selectById(task.getPoolGameId());
		BigDecimal goldPoolCoins=poolGame.getPool();
		if(goldPoolCoins.compareTo(task.getAmount())<0) {
//			rmap.put("msg", "奖金池总额不足");
			throw new RRException(ErrorCode.PoolDispatchErrorCode.INSUFFICIENT_AMOUNT_OF_BONUS_POOL_ERRO.getErrMsg()
    				,ErrorCode.PoolDispatchErrorCode.INSUFFICIENT_AMOUNT_OF_BONUS_POOL_ERRO.getCode());
		}
		RankingListEntity rankingList=rankingListDao.selectById(task.getRankingListId());
		//2.查询任务明细
    	PoolDispatchTaskDetailEntity taskDetail = new PoolDispatchTaskDetailEntity();
    	taskDetail.setTaskId(task.getId());
    	Wrapper<PoolDispatchTaskDetailEntity> wrapper = new EntityWrapper<PoolDispatchTaskDetailEntity>(taskDetail);
    	List<PoolDispatchTaskDetailEntity> detailList=poolDispatchTaskDetailDao.selectList(wrapper);
    	if(detailList==null || detailList.isEmpty()) {
//    		rmap.put("msg", "奖项设置未设置");
    		throw new RRException(ErrorCode.PoolDispatchErrorCode.AWARD_SETTINGS_NOT_SET_ERRO.getErrMsg()
    				,ErrorCode.PoolDispatchErrorCode.AWARD_SETTINGS_NOT_SET_ERRO.getCode());
    	}
    	//计算需要派奖的总人数
    	Integer sumNum=detailList.stream().mapToInt(PoolDispatchTaskDetailEntity::getNum).sum();
    	//统计派奖总金额
    	BigDecimal amountSum=BigDecimal.ZERO;
    	List<PoolDispatchDetailListEntity> ddlList=new ArrayList<>();
    	//3.查询符合条件的人
    	List<RankingListUserVo> rlist=new ArrayList<>();
    	Integer maxPer=rankingList.getTopNum()!=null?rankingList.getTopNum():20;
    	RankingListRequestVo vo=new RankingListRequestVo();
    	vo.setRankingListId(task.getRankingListId());
    	vo.setLimit(maxPer);
    	if(task.getType()==1) {//日排行
    		vo.setYesterday(DateUtils.getStartOfYesterday());
    		rlist=rankingListDao.getRankingListForYesterday(vo);
    	}else {//周排行
    		vo.setWeekOfYear(DateUtils.getWeekOfYear(new Date()));
    		rlist=rankingListDao.getRankingListForLastWeek(vo);
    		
    	}
    	if(sumNum>rlist.size()) {
//			rmap.put("msg", "设置的中奖人数超出可派发的人数");
    		throw new RRException(ErrorCode.PoolDispatchErrorCode.SETTING_WIN_NUM_OVERFLOWT_ERRO.getErrMsg()
    				,ErrorCode.PoolDispatchErrorCode.SETTING_WIN_NUM_OVERFLOWT_ERRO.getCode());
		
		}
    	//取得随机数
    	List<Integer> luckList=MathUtil.getRandomInt(rlist.size(), sumNum);
		//4.组装派奖明细记录
    	Map<String, UserEntity> userMap=new HashMap<>();
    	BigDecimal dispatchAmount=task.getAmount();//这次派奖任务总额单位为分
    	int index=0;//中奖
		for(PoolDispatchTaskDetailEntity detail:detailList) {
			String[] useridArr=null;
    		if(detail.getUserids()!=null && !"".equals(detail.getUserids().trim())) {
    			useridArr=detail.getUserids().split(",");
    		}
    		//计算单人可获得的奖金   这次派奖任务总额*比例/这个奖项的人数
     		BigDecimal amount=BigDecimal.ZERO;
     		amount=dispatchAmount.multiply(detail.getRate()).
					divide(new BigDecimal(detail.getNum()), 0, BigDecimal.ROUND_HALF_UP);
//					divide(new BigDecimal(100), 0, BigDecimal.ROUND_HALF_UP);
     		for(int i=0 ;i<detail.getNum();i++) {
     			PoolDispatchDetailListEntity dispatchDetailList=new PoolDispatchDetailListEntity();
        		dispatchDetailList.setAmount(amount);
        		Long userid=null;
        		try {
        			if(useridArr!=null && useridArr.length>i) {
        				userid=Long.parseLong(useridArr[i]);
            		}else {
            			userid=rlist.get(luckList.get(index)).getUserId();
            		}
				} catch (Exception e) {
				}
        		index++;
        		if(userid==null ) {
//        			rmap.put("msg", "派奖的用户id不存在");
        			throw new RRException(ErrorCode.PoolDispatchErrorCode.USER_ID_NOT_EXIST_ERRO.getErrMsg()
            				,ErrorCode.PoolDispatchErrorCode.USER_ID_NOT_EXIST_ERRO.getCode());
        		}
    			UserEntity user=new UserEntity();
    			user=userDao.selectById(userid);
     			if(user==null || user.getId()==null
     					|| user.getDeleteStatus()==1) {
//         				rmap.put("msg", "派奖的用户id不存在");
     				throw new RRException(ErrorCode.PoolDispatchErrorCode.USER_ID_NOT_EXIST_ERRO.getErrMsg()
            				,ErrorCode.PoolDispatchErrorCode.USER_ID_NOT_EXIST_ERRO.getCode());
     			}
     			userMap.put(user.getAccount(), user);
     			dispatchDetailList.setUserAccount(user.getAccount());
        		dispatchDetailList.setUserId(userid);
        		dispatchDetailList.setDispatchTime(new Date());
        		dispatchDetailList.setDeleteStatus(0);
        		dispatchDetailList.setTaskDetailId(detail.getId());
        		dispatchDetailList.setTaskId(task.getId());
        		dispatchDetailList.setTaskTitle(task.getTitle());
        		dispatchDetailList.setDetailTitle(detail.getTitle());
        		dispatchDetailList.setVersion(0);
        		dispatchDetailList.setOrderNo(OrderNoUtil.getOrderNo());
        		ddlList.add(dispatchDetailList);
        		amountSum=amountSum.add(amount);
     		}
    	}
    	if(amountSum.compareTo(dispatchAmount)>0) {
// 			rmap.put("msg", "实际派发的奖金超出预设派发的金额");
    		throw new RRException(ErrorCode.PoolDispatchErrorCode.ACTUAL_GOLD_GO_BEYOND_DEFAULT_AMOUNT_ERRO.getErrMsg()
    				,ErrorCode.PoolDispatchErrorCode.ACTUAL_GOLD_GO_BEYOND_DEFAULT_AMOUNT_ERRO.getCode());
 		}else if(amountSum.compareTo(dispatchAmount)<0) {
// 			rmap.put("msg", "实际派发的奖金小于预设派发的金额");
 			throw new RRException(ErrorCode.PoolDispatchErrorCode.ACTUAL_GOLD_LESS_THAN_DEFAULT_AMOUNT_ERRO.getErrMsg()
    				,ErrorCode.PoolDispatchErrorCode.ACTUAL_GOLD_LESS_THAN_DEFAULT_AMOUNT_ERRO.getCode());
 		}
    	//5.更新奖金池总额
 		Integer updateNum = poolGameDao.updatePool(poolGame.getId(),amountSum.multiply(new BigDecimal(-1)));
    	if(updateNum!=1) {
    		throw new RRException(ErrorCode.PoolDispatchErrorCode.INSUFFICIENT_AMOUNT_OF_BONUS_POOL_ERRO.getErrMsg()
    				,ErrorCode.PoolDispatchErrorCode.INSUFFICIENT_AMOUNT_OF_BONUS_POOL_ERRO.getCode());
    	}
    	//6.保存派奖明细记录和更新用户金币
    	UserEntity user=new UserEntity();
    	UserTransactionRecordEntity  transactionRecord=new UserTransactionRecordEntity();
 		for(PoolDispatchDetailListEntity ddl:ddlList) {
 			poolDispatchDetailListDao.insert(ddl);
 			user.setId(ddl.getUserId());
 			user.setCoin(ddl.getAmount().longValue());
 			//更新金币
 			userDao.updateUserWalletByUserId(user);
 			transactionRecord.setUserId(ddl.getUserId());
 			transactionRecord.setUserAccount(ddl.getUserAccount());
 			transactionRecord.setAmount(ddl.getAmount());
 			transactionRecord.setOrderNo(ddl.getOrderNo());
 			transactionRecord.setFristrecharge(false);
 			transactionRecord.setType(TransactionType.AWARD.getValue());
 			transactionRecord.setDetailType(TransactionDetailType.PRIZEPOOLAWARD.getValue());
 			transactionRecord.setMoney(userMap.get(ddl.getUserAccount()).getMoney());
 			transactionRecord.setCoin(userMap.get(ddl.getUserAccount()).getCoin());
 			transactionRecord.setCommission(userMap.get(ddl.getUserAccount()).getCommission());
 			//插入交易记录
 			userTransactionRecordDao.insert(transactionRecord);
 			//发送站内信
 			MessageManagementEntity messageManagement=new MessageManagementEntity();
 			messageManagement.setContentType(1);
 			messageManagement.setTitle(String.format(Constant.WINNING_MAIL_TITLE, ddl.getDetailTitle()));
 			messageManagement.setContent(String.format(Constant.WINNING_MAIL_CONTENT,
 					DateUtils.format(new Date(), "yyyy年MM月dd日HH时mm分"),
 					ddl.getDetailTitle(),ddl.getAmount().longValue()));
 			messageManagement.setEffectTime(new Date());
 			messageManagement.setEnable(true);
 			messageManagement.setTargetObject(1);
 			messageManagement.setUserAccount(ddl.getUserAccount());
 			//保存站内信
 			messageManagementDao.insertEntityReturnId(messageManagement);
 			//保存站内信和会员的关系
 			MessageUserEntity messageUser=new MessageUserEntity();
			messageUser.setMessageId(messageManagement.getId());
			messageUser.setUserAccount(ddl.getUserAccount());
			messageUser.setUserId(ddl.getUserId());
			messageUser.setStatus(false);
			messageUserDao.insert(messageUser);
 			user=new UserEntity();
 			transactionRecord=new UserTransactionRecordEntity();
 		}
 		task.setStatus(1);
 		task.updateById();
 		
		
	}

}
