package com.xmsy.server.zxyy.manager.modules.app.ranking;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.manager.cache.LocalContentCache;
import com.xmsy.server.zxyy.manager.common.utils.Constant;
import com.xmsy.server.zxyy.manager.common.utils.DateUtils;
import com.xmsy.server.zxyy.manager.common.utils.MathUtil;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.constant.HallUrlConstant;
import com.xmsy.server.zxyy.manager.modules.app.ranking.param.AppRechargeRankingParam;
import com.xmsy.server.zxyy.manager.modules.app.ranking.param.AppShareRankingParam;
import com.xmsy.server.zxyy.manager.modules.manager.rankinglist.service.RankingListService;
import com.xmsy.server.zxyy.manager.modules.manager.rankinglist.vo.RankingListRequestVo;
import com.xmsy.server.zxyy.manager.modules.manager.rankinglist.vo.RankingListUserVo;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendation.dao.UserRecommendationDao;

/**
 * 排行榜
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-08 11:00:25
 */
@RestController
@RequestMapping("V1.0/App")
public class AppRankingController {
	@Autowired
    private UserRecommendationDao userRecommendationDao;
	@Autowired
	private LocalContentCache localContentCache;
	@Autowired
	private RankingListService rankingListService;
	/**
	 * 周排行榜，前7日，前20名
	 * 
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/weeklyRanking")
	public R weeklyRanking() {
//		List<AppRechargeRankingParam> list=orderRechargeDao.weeklyRanking();
//		return R.ok().put("data", list);
		List<AppRechargeRankingParam> list=new ArrayList<>();
		Date yesterday=DateUtils.getStartOfYesterday();
		//充值榜key
		String rechargeKey="rechargeWeeklyRanking"+DateUtils.format(yesterday, "yyyy-MM-dd");
		Object rechargeObject=localContentCache.get(rechargeKey);
		if(rechargeObject==null) {
			RankingListRequestVo rechargeListVo =new RankingListRequestVo();
			rechargeListVo.setLimit(HallUrlConstant.getRANKINGLIST_LIMIT());
			rechargeListVo.setRankingListId(HallUrlConstant.getRECHARGE_RANKINGLIST());
			rechargeListVo.setStartDate(DateUtils.addDateDays(yesterday, -7));
			rechargeListVo.setEndDate(yesterday);
			List<RankingListUserVo> rechargeRankingList=rankingListService.getRankingListBySevenDays(rechargeListVo);
			if(rechargeRankingList!=null && !rechargeRankingList.isEmpty()) {
				AppRechargeRankingParam appRechargeRanking=null;
				for(RankingListUserVo user:rechargeRankingList) {
					appRechargeRanking=new AppRechargeRankingParam();
					appRechargeRanking.setPortrait(user.getPortrait());
					appRechargeRanking.setUserAccount(user.getUserAccount());
					appRechargeRanking.setRechargeAmount(MathUtil.getBigDecimal(user.getAmount())
							.multiply(new BigDecimal(Constant.CLIENT_COIN_RATE)).longValue());
					list.add(appRechargeRanking);
				}
				localContentCache.put(rechargeKey, list);
			}
		}else {
			list=(List<AppRechargeRankingParam>)rechargeObject;
		}
		return R.ok().put("data", list);
	}
	/**
	 * 日排行榜，昨日，前20名
	 * 
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/dayRanking")
	public R dayRanking() {
//		List<AppRechargeRankingParam> list=orderRechargeDao.dayRanking();
//		return R.ok().put("data", list);
		List<AppRechargeRankingParam> list=new ArrayList<>();
		Date yesterday=DateUtils.getStartOfYesterday();
		//充值榜key
		String rechargeKey="rechargeRankingList"+DateUtils.format(yesterday, "yyyy-MM-dd");
		Object rechargeObject=localContentCache.get(rechargeKey);
		if(rechargeObject==null) {
			RankingListRequestVo rechargeListVo =new RankingListRequestVo();
			rechargeListVo.setLimit(HallUrlConstant.getRANKINGLIST_LIMIT());
			rechargeListVo.setRankingListId(HallUrlConstant.getRECHARGE_RANKINGLIST());
			rechargeListVo.setYesterday(yesterday);
			List<RankingListUserVo> rechargeRankingList=rankingListService.getRankingListByYesterday(rechargeListVo);
			if(rechargeRankingList!=null && !rechargeRankingList.isEmpty()) {
				AppRechargeRankingParam appRechargeRanking=null;
				for(RankingListUserVo user:rechargeRankingList) {
					appRechargeRanking=new AppRechargeRankingParam();
					appRechargeRanking.setPortrait(user.getPortrait());
					appRechargeRanking.setUserAccount(user.getUserAccount());
					appRechargeRanking.setRechargeAmount(MathUtil.getBigDecimal(user.getAmount())
							.multiply(new BigDecimal(Constant.CLIENT_COIN_RATE)).longValue());
					list.add(appRechargeRanking);
				}
				localContentCache.put(rechargeKey, list);
			}
		}else {
			list=(List<AppRechargeRankingParam>)rechargeObject;
		}
		return R.ok().put("data", list);
	}
	/**
	 * 分享榜，前20名
	 * 
	 */
	@GetMapping("/shareRanking")
	public R shareRanking() {
		List<AppShareRankingParam> list=userRecommendationDao.shareRanking();
		return R.ok().put("data", list);
	}

}
