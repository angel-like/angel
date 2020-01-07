package com.xmsy.server.zxyy.webhome.modules.app.pool;

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

import com.google.common.collect.ImmutableMap;
import com.xmsy.server.zxyy.webhome.cache.LocalContentCache;
import com.xmsy.server.zxyy.webhome.common.utils.DateUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.constant.HallUrlConstant;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.service.RankingListService;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.vo.RankingListRequestVo;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.vo.RankingListUserVo;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.service.UserHierarchyService;

/**
 * 排行榜
 *
 * @author adu
 * @email xxxxx
 * @date 2019-02-20 11:18:25
 */
@RestController
@RequestMapping("V1.0/App")
public class AppRankingListController {
	@Resource
	private RankingListService rankingListService;
	@Resource
	private UserHierarchyService userHierarchyService;
	@Autowired
	private LocalContentCache localContentCache;
	
	@GetMapping("/getDefault")
	public R getDefault(HttpServletRequest httpServletRequest) {
		return R.ok().put("data", userHierarchyService.getDefaultHierarchy());
	}
	
	/**
	 * 排行榜
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/Rankinglist")
	public R rankinglist(HttpServletRequest httpServletRequest) {
		Date yesterday=DateUtils.getStartOfYesterday();
		//财富榜key
		String fortuneKey="fortuneList"+DateUtils.format(yesterday, "yyyy-MM-dd");
		//派奖榜key
		String pieAwardKey="pieAwardList"+DateUtils.format(yesterday, "yyyy-MM-dd");
		Object fortuneObject=localContentCache.get(fortuneKey);
		Object pieAwardObject=localContentCache.get(pieAwardKey);
		List<RankingListUserVo> fortuneList  = new ArrayList<>();
		List<RankingListUserVo> pieAwardList = new ArrayList<>();
		//财富榜
//		fortuneObject=null;
		if(fortuneObject==null) {
//			fortuneList=getFortuneList(20);//写死的
			RankingListRequestVo fortuneListVo =new RankingListRequestVo();
			fortuneListVo.setLimit(HallUrlConstant.getRANKINGLIST_LIMIT());
			fortuneListVo.setRankingListId(HallUrlConstant.getWEALTH_RANKINGLIST());
//			fortuneListVo.setYesterday(yesterday);
			fortuneList=rankingListService.getRankingListByYesterday(fortuneListVo);
			if(fortuneList!=null && !fortuneList.isEmpty()) {
				localContentCache.put(fortuneKey, fortuneList);
			}
			
		}else {
			fortuneList=(List<RankingListUserVo>) fortuneObject;
		}
		//派奖榜
		if(pieAwardObject==null) {
//			pieAwardList=getPieAwardList(20);//写死的
			RankingListRequestVo pieAwardListVo =new RankingListRequestVo();
			pieAwardListVo.setLimit(HallUrlConstant.getRANKINGLIST_LIMIT());
			pieAwardListVo.setRankingListId(HallUrlConstant.getPIWAWARD_RANKINGLIST());
//			pieAwardListVo.setYesterday(yesterday);
			pieAwardList=rankingListService.getRankingListByYesterday(pieAwardListVo);
			if(pieAwardList!=null && !pieAwardList.isEmpty()) {
				localContentCache.put(pieAwardKey, pieAwardList);
			}
		}else {
			pieAwardList=(List<RankingListUserVo>) pieAwardObject;
		}
		return R.ok().put("data", ImmutableMap.of("fortuneList", fortuneList
				,"pieAwardList", pieAwardList));
	}
	/**
	 * 排行榜(demo)
	 * @param httpServletRequest
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<RankingListUserVo> getPieAwardList(int len) {
		RankingListUserVo rankingList=new RankingListUserVo();
		List<RankingListUserVo> pieAwardList=new ArrayList<>();;
		for(int i=1;i<=len;i++) {
			rankingList.setPosition(i);
			rankingList.setUserAccount("apl*****00"+i);
			rankingList.setAmount(new BigDecimal(20000-(i-1)*500));
			pieAwardList.add(rankingList);
			rankingList=new RankingListUserVo();
		}
		
		return pieAwardList;
	}
	
	@SuppressWarnings("unused")
	private  List<RankingListUserVo> getFortuneList(int len){
		List<RankingListUserVo> fortuneList=new ArrayList<>();
		RankingListUserVo rankingList=new RankingListUserVo();
		for(int i=1;i<=len;i++) {
			rankingList.setPosition(i);
			rankingList.setUserAccount("cheddbccs*****00"+i);
			rankingList.setAmount(new BigDecimal(200000-(i-1)*5000));
			fortuneList.add(rankingList);
			rankingList=new RankingListUserVo();
		}
		return fortuneList;
	}
	
	

}
