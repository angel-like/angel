package com.xmsy.server.zxyy.calculate.modules.manager.fortuneactivitymanager.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.calculate.common.utils.DateUtils;
import com.xmsy.server.zxyy.calculate.common.utils.MathUtil;
import com.xmsy.server.zxyy.calculate.modules.manager.fortuneactivitymanager.dao.EnvelopeRecordDao;
import com.xmsy.server.zxyy.calculate.modules.manager.fortuneactivitymanager.entity.EnvelopeRecordEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.fortuneactivitymanager.entity.EnvelopeTaskConfigEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.fortuneactivitymanager.entity.FortuneActiviConfigEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.fortuneactivitymanager.service.EnvelopeRecordService;
import com.xmsy.server.zxyy.calculate.modules.manager.gamerecord.dao.GameRecordDao;
import com.xmsy.server.zxyy.calculate.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.user.service.UserService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("envelopeRecordService")
public class EnvelopeRecordServiceImpl extends ServiceImpl<EnvelopeRecordDao, EnvelopeRecordEntity> implements EnvelopeRecordService {
	@Autowired
    private GameRecordDao gameRecordDao;
	@Autowired
    private UserService userService;
	/*@Autowired
    private OrderRechargeService orderRechargeService;*/
	/**
	 * 通过   有效打码     获取红包
	 * @param fortuneActivi   活动实体类
	 * @param EnvelopeTaskParam  红包事件实体
	 */
	@Override
	public Integer gainValidBetEnvelope(Long userId,FortuneActiviConfigEntity fortuneActivi,
			EnvelopeTaskConfigEntity envelopeTask) {
		//1.统计 活动时间内，有效打码总数
		Long userValidBet = gameRecordDao.getUserValidBet(userId, DateUtils.formatTime(fortuneActivi.getStartTime()),
				DateUtils.formatTime(fortuneActivi.getEndTime()));
		//2.通过有效打码总数获取  该获得的红包数量
		BigDecimal total = MathUtil.getBigDecimal(userValidBet)
				.divide(MathUtil.getBigDecimal(envelopeTask.getEventParam().longValue()), RoundingMode.DOWN);
		//3.查询活动时间内，用户 已获得的红包数量  (会员id+事件id+活动id)
		Wrapper<EnvelopeRecordEntity> wrapper = new EntityWrapper<EnvelopeRecordEntity>(new EnvelopeRecordEntity()
				.setUserId(userId).setActivityId(fortuneActivi.getId()));
		wrapper.ge("create_time", fortuneActivi.getStartTime());
		wrapper.le("create_time", fortuneActivi.getEndTime());
		Integer count = this.selectCount(wrapper);
		//4.该获得的红包数量-已获得的红包数量   = 要新增未开启的红包数量
		int envelopeNum=total.intValue()*envelopeTask.getRewards()-count;
		//envelopeNum=2;/**用来添加测试数据*/
		if(envelopeNum<=0) {
			return 0;
		}
		UserEntity user = userService.selectById(userId);
		//**5.获取会员未开启的红包数量(会员id+事件id+活动id+未开启)   
		EntityWrapper<EnvelopeRecordEntity> wrapper2 = new EntityWrapper<EnvelopeRecordEntity>(new EnvelopeRecordEntity()
				.setUserId(userId).setActivityId(fortuneActivi.getId()).setStatus(0));
		wrapper2.ge("create_time", fortuneActivi.getStartTime());
		wrapper2.le("create_time", fortuneActivi.getEndTime());
		Integer noOpenNum = this.selectCount(wrapper2);
		if(noOpenNum >= 99) {
			return 0;
		}
//		Integer lostNum=0;//丢弃的红包数量   加进去 并设置已开启（不加进去就无法把超过99的红包丢弃）
		if(noOpenNum+envelopeNum>99) {//未开启的红包数量+该获得的红包数量要>100
//			lostNum=envelopeNum+noOpenNum-99;
			envelopeNum=99-noOpenNum;
			//把丢弃的红包加进去
//			List<EnvelopeRecordEntity> envelopeRecordLostList=new ArrayList<>();
//			EnvelopeRecordEntity envelopeRecordLost=null;
//			for(int i=0;i<lostNum;i++) {//没有开启时间+会员金币+获得金币等，但是显示开启=》就是被丢弃的红包
//				envelopeRecordLost=new EnvelopeRecordEntity();
//				envelopeRecordLost.setUserId(userId);//会员id
//				envelopeRecordLost.setUserAccount(user.getAccount());//用户账号
//				envelopeRecordLost.setActivityId(fortuneActivi.getId());//活动id
//				envelopeRecordLost.setEventId(envelopeTask.getId());//事件id
//				envelopeRecordLost.setStatus(1);//是否开启 0-未开启  1-已开启
//				envelopeRecordLostList.add(envelopeRecordLost);
//			}
//			this.baseMapper.addBatch(envelopeRecordLostList);
		}
		List<EnvelopeRecordEntity> envelopeRecordList=new ArrayList<>();
		EnvelopeRecordEntity envelopeRecord=null;
		for(int i=0;i<envelopeNum;i++) {
			envelopeRecord=new EnvelopeRecordEntity();
			envelopeRecord.setUserId(userId);//会员id
			envelopeRecord.setUserAccount(user.getAccount());//用户账号
			envelopeRecord.setActivityId(fortuneActivi.getId());//活动id
			envelopeRecord.setEventId(envelopeTask.getId());//事件id
			envelopeRecord.setStatus(0);//是否开启 0-未开启  1-已开启
			envelopeRecordList.add(envelopeRecord);
		}
		
		Integer b = this.baseMapper.addBatch(envelopeRecordList);
		return b;
		
	}
	/**
	 * 通过   充值金额    获取新增红包个数  
	 * @param userId 
	 * @param fortuneActivi 活动实体类
	 * @param envelopeTask  红包事件实体
	 * @param unEnvelopeNum 暂时没用
	 * @return
	 */
	@Override
	public Integer gainRechargeAmountEnvelope(Long userId, FortuneActiviConfigEntity fortuneActivi,
			EnvelopeTaskConfigEntity envelopeTask, Long rechargeAmount) {
		//1.统计 活动时间内，用户的充值金额   统计充值得红包
		/*Long userRechargeAmount = orderRechargeService.getRechargeAmount(userId, DateUtils.formatTime(fortuneActivi.getStartTime()),
				DateUtils.formatTime(fortuneActivi.getEndTime()));
		// 2.通过用户的充值金额 总数  获取 该获得的红包数量
		BigDecimal total = MathUtil.getBigDecimal(userRechargeAmount)
				.divide(MathUtil.getBigDecimal(envelopeTask.getEventParam().longValue()), RoundingMode.DOWN);
		// 3.查询活动时间内，用户 已获得的红包数量 (会员id+活动id)
		Wrapper<EnvelopeRecordEntity> wrapper = new EntityWrapper<EnvelopeRecordEntity>(
				new EnvelopeRecordEntity().setUserId(userId).setActivityId(fortuneActivi.getId()));
		wrapper.ge("create_time", fortuneActivi.getStartTime());
		wrapper.le("create_time", fortuneActivi.getEndTime());
		Integer count = this.selectCount(wrapper);
		// 4.该获得的红包数量-已获得的红包数量 = 要新增未开启的红包数量
		int envelopeNum = total.intValue() * envelopeTask.getRewards() - count;*/
		/**单次充值到指定金额得红包     充值金额/条件=红包个数  */
		BigDecimal total=MathUtil.getBigDecimal(rechargeAmount)
				.divide(MathUtil.getBigDecimal(envelopeTask.getEventParam()), RoundingMode.DOWN);
		int envelopeNum=total.intValue()* envelopeTask.getRewards() ;
		if(envelopeNum<=0) {
			return 0;
		}
		UserEntity user = userService.selectById(userId);
		//**5.获取会员未开启的红包数量(会员id+事件id+活动id+未开启)   
		EntityWrapper<EnvelopeRecordEntity> wrapper2 = new EntityWrapper<EnvelopeRecordEntity>(new EnvelopeRecordEntity()
				.setUserId(userId).setActivityId(fortuneActivi.getId()).setStatus(0));
		wrapper2.ge("create_time", fortuneActivi.getStartTime());
		wrapper2.le("create_time", fortuneActivi.getEndTime());
		Integer noOpenNum = this.selectCount(wrapper2);
		if(noOpenNum >= 99) {
			return 0;
		}
//		Integer lostNum=0;//丢弃的红包数量   加进去 并设置已开启（不加进去就无法把超过99的红包丢弃）
		if(noOpenNum+envelopeNum>99) {//未开启的红包数量+该获得的红包数量要>100
//			lostNum=envelopeNum+noOpenNum-99;
			envelopeNum=99-noOpenNum;
		}
		List<EnvelopeRecordEntity> envelopeRecordList=new ArrayList<>();
		EnvelopeRecordEntity envelopeRecord=null;
		for(int i=0;i<envelopeNum;i++) {
			envelopeRecord=new EnvelopeRecordEntity();
			envelopeRecord.setUserId(userId);//会员id
			envelopeRecord.setUserAccount(user.getAccount());//用户账号
			envelopeRecord.setActivityId(fortuneActivi.getId());//活动id
			envelopeRecord.setEventId(envelopeTask.getId());//事件id
			envelopeRecord.setStatus(0);//是否开启 0-未开启  1-已开启
			envelopeRecordList.add(envelopeRecord);
		}
		
		Integer b = this.baseMapper.addBatch(envelopeRecordList);
		return b;
	}



}
