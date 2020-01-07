package com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
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
@Mapper
public interface RankingListDao extends BaseMapper<RankingListEntity> {

	/**
	 * 查找昨日的榜单排名
	 * 
	 * @param vo
	 * @return
	 */
	List<RankingListUserVo> getRankingListForYesterday(RankingListRequestVo vo);

	/**
	 * 查找指定周的榜单排名
	 * 
	 * @param vo
	 * @return
	 */
	List<RankingListUserVo> getRankingListForLastWeek(RankingListRequestVo vo);

	/**
	 * 查找昨日的榜单排名 首页
	 * 
	 * @param vo
	 * @return
	 */
	List<RankingListUserVo> getRankingListForYesterdayToHomePage(RankingListRequestVo vo);

	/**
	 * 查找指定周的榜单排名 首页
	 * 
	 * @param vo
	 * @return
	 */
	List<RankingListUserVo> getRankingListForLastWeekToHomePage(RankingListRequestVo vo);

	/**
	 * 查找昨日的榜单排名
	 * 
	 * @param vo
	 * @return
	 */
	List<RankingListUserVo> getRankingListByYesterday(RankingListRequestVo vo);

	/**
	 * 查找昨日的榜单排名
	 * 
	 * @param vo
	 * @return
	 */
	List<RankingListUserVo> getImRankingListByYesterday(RankingListRequestVo vo);

	/**
	 * 查找一段时间间隔的榜单排名
	 * 
	 * @param vo
	 * @return
	 */
	List<RankingListUserVo> getRankingListByDataInterval(RankingListRequestVo vo);
}
