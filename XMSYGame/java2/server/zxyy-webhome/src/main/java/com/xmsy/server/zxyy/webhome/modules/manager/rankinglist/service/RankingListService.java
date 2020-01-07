package com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.entity.RankingListEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.vo.RankingListRequestVo;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.vo.RankingListUserVo;

/**
 * 排行榜
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-21 09:46:00
 */
public interface RankingListService extends IService<RankingListEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 查找昨日的榜单排名 首页
	 * 
	 * @param vo
	 * @return
	 */
	List<RankingListUserVo> getRankingListForYesterdayToHomePage(RankingListRequestVo vo);

	/**
	 * 查找昨日的榜单排名
	 * 
	 * @param vo
	 * @return
	 */
	List<RankingListUserVo> getRankingListByYesterday(RankingListRequestVo vo);

	/**
	 * 查找昨日的榜单排名33娱乐
	 * 
	 * @param vo
	 * @return
	 */
	List<RankingListUserVo> getImRankingListByYesterday(RankingListRequestVo vo);

	/**
	 * 查找前7天的榜单排名
	 * 
	 * @param vo
	 * @return
	 */
	List<RankingListUserVo> getRankingListBySevenDays(RankingListRequestVo vo);

	/**
	 * 查找指定周的榜单排名 首页
	 * 
	 * @param vo
	 * @return
	 */
	List<RankingListUserVo> getRankingListForLastWeekToHomePage(RankingListRequestVo vo);

}
