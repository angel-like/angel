package com.xmsy.server.zxyy.manager.modules.web.pool;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.manager.cache.LocalContentCache;
import com.xmsy.server.zxyy.manager.common.utils.DateUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.constant.HallUrlConstant;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.modules.manager.rankinglist.service.RankingListService;
import com.xmsy.server.zxyy.manager.modules.manager.rankinglist.vo.RankingListRequestVo;
import com.xmsy.server.zxyy.manager.modules.manager.rankinglist.vo.RankingListUserVo;

/**
 * 排行榜
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-18 11:18:25
 */
@RestController
@RequestMapping("webhome/public")
public class WebHomeRankingListController {
	@Resource
	private RankingListService rankingListService;
	@Autowired
	private LocalContentCache localContentCache;

	/**
	 * 排行榜
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/rankinglist")
	public R rankinglist(HttpServletRequest httpServletRequest) {
		Date yesterday = DateUtils.getStartOfYesterday();
		// 财富榜key
		String fortuneKey = SysConstant.FORTUNEKEY + DateUtils.format(yesterday, "yyyy-MM-dd");
		// 派奖榜key
		String pieAwardKey = SysConstant.PIEAWARKEY + DateUtils.format(yesterday, "yyyy-MM-dd");
		Object fortuneObject = localContentCache.get(fortuneKey);
		Object pieAwardObject = localContentCache.get(pieAwardKey);
		List<RankingListUserVo> fortuneList = new ArrayList<>();
		List<RankingListUserVo> pieAwardList = new ArrayList<>();
		// 财富榜
		if (fortuneObject == null) {
			// 先用写死的
//			fortuneList = getFortuneList(20);
			 RankingListRequestVo fortuneListVo =new RankingListRequestVo();
			 fortuneListVo.setLimit(HallUrlConstant.getRANKINGLIST_LIMIT());
			 fortuneListVo.setRankingListId(HallUrlConstant.getWEALTH_RANKINGLIST());
			 fortuneListVo.setYesterday(yesterday);
			 fortuneList=rankingListService.getRankingListByYesterday(fortuneListVo);
			 if(fortuneList!=null && !fortuneList.isEmpty()) {
				localContentCache.put(fortuneKey, fortuneList);
				
			 }
		} else {
			fortuneList = (List<RankingListUserVo>) fortuneObject;
		}
		// 派奖榜
		if (pieAwardObject == null) {
			// 先用写死的
//			pieAwardList = getPieAwardList(HallUrlConstant.getRANKINGLIST_LIMIT());
			 RankingListRequestVo pieAwardListVo =new RankingListRequestVo();
			 pieAwardListVo.setLimit(HallUrlConstant.getRANKINGLIST_LIMIT());
			 pieAwardListVo.setRankingListId(HallUrlConstant.getPIWAWARD_RANKINGLIST());
			 pieAwardListVo.setYesterday(yesterday);
			 pieAwardList=rankingListService.getRankingListByYesterday(pieAwardListVo);
			 if(pieAwardList!=null && !pieAwardList.isEmpty()) {
					localContentCache.put(pieAwardKey, pieAwardList);
			 }
		} else {
			pieAwardList = (List<RankingListUserVo>) pieAwardObject;
		}
		return R.ok().put("fortuneList", fortuneList).put("pieAwardList", pieAwardList);
	}

	/**
	 * 排行榜(demo)
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<RankingListUserVo> getPieAwardList(int len) {
		RankingListUserVo rankingList = new RankingListUserVo();
		List<RankingListUserVo> pieAwardList = new ArrayList<>();
		;
		for (int i = 1; i <= len; i++) {
			rankingList.setPosition(i);
			rankingList.setUserAccount("apl*****00" + i);
			rankingList.setAmount(new BigDecimal(20000 - (i - 1) * 500));
			rankingList.setAddress("福建省厦门市");
			pieAwardList.add(rankingList);
			rankingList = new RankingListUserVo();
		}

		return pieAwardList;
	}

	@SuppressWarnings("unused")
	private List<RankingListUserVo> getFortuneList(int len) {
		List<RankingListUserVo> fortuneList = new ArrayList<>();
		RankingListUserVo rankingList = new RankingListUserVo();
		for (int i = 1; i <= len; i++) {
			rankingList.setPosition(i);
			rankingList.setUserAccount("che*****00" + i);
			rankingList.setAmount(new BigDecimal(200000 - (i - 1) * 5000));
			rankingList.setAddress("福建省厦门市");
			fortuneList.add(rankingList);
			rankingList = new RankingListUserVo();
		}
		return fortuneList;
	}

}
