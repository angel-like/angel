package com.xmsy.server.zxyy.manager.modules.manager.rankinglist.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.Query;
import com.xmsy.server.zxyy.manager.modules.manager.rankinglist.dao.RankingListDao;
import com.xmsy.server.zxyy.manager.modules.manager.rankinglist.entity.RankingListEntity;
import com.xmsy.server.zxyy.manager.modules.manager.rankinglist.service.RankingListService;
import com.xmsy.server.zxyy.manager.modules.manager.rankinglist.vo.RankingListRequestVo;
import com.xmsy.server.zxyy.manager.modules.manager.rankinglist.vo.RankingListUserVo;

@Transactional
@Service("rankingListService")
public class RankingListServiceImpl extends ServiceImpl<RankingListDao, RankingListEntity> implements RankingListService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        RankingListEntity entity=new RankingListEntity();
    	if(params !=null) {
    		if(params.get("key")!=null && !"".equals(params.get("key").toString().trim())) {
    			entity.setRankListName(params.get("key").toString().trim());
    		}
    		if(params.get("enable")!=null && !"".equals(params.get("enable").toString().trim())) {
    			entity.setEnable("true".equals(params.get("enable").toString().trim()));
    		}
    	}
    	Wrapper<RankingListEntity> wrapper = new EntityWrapper<RankingListEntity>(entity);
    	String sort=params.get("sort")!=null?params.get("sort").toString():"";
    	if (!StringUtils.isEmpty(sort)) {
    		String direction=params.get("direction")!=null?params.get("direction").toString():"";
    		if ("desc".equalsIgnoreCase(direction)) {
    			wrapper.orderDesc(Arrays.asList(sort.split(",")));
    		} else {
    			wrapper.orderAsc(Arrays.asList(sort.split(",")));
    		}
		}
    	Page<RankingListEntity> page = this.selectPage(
                new Query<RankingListEntity>(params).getPage(),
                wrapper
        );
        return new PageUtils(page);
    }

	@Override
	public List<RankingListUserVo> getRankingListForYesterdayToHomePage(RankingListRequestVo vo) {
		return this.baseMapper.getRankingListForYesterdayToHomePage(vo);
	}

	@Override
	public List<RankingListUserVo> getRankingListForLastWeekToHomePage(RankingListRequestVo vo) {
		return this.baseMapper.getRankingListForLastWeekToHomePage(vo);
	}

	@Override
	public List<RankingListUserVo> getRankingListByYesterday(RankingListRequestVo vo) {
		return this.baseMapper.getRankingListByYesterday(vo);
	}

	@Override
	public List<RankingListUserVo> getRankingListBySevenDays(RankingListRequestVo vo) {
		return this.baseMapper.getRankingListByDataInterval(vo);
	}

}
