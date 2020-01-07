package com.xmsy.server.zxyy.webhome.modules.manager.rankinglistday.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.DateUtils;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.Query;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.dao.RankingListDao;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.entity.RankingListEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglistday.dao.RankingListDayDao;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglistday.entity.RankingListDayEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglistday.service.RankingListDayService;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglistweek.entity.RankingListWeekEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.dao.UserDao;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("rankingListDayService")
public class RankingListDayServiceImpl extends ServiceImpl<RankingListDayDao, RankingListDayEntity> implements RankingListDayService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private RankingListDao rankingListDao;
	
	// 批量插入数据的数量
	private static final int batchSize = 100;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        RankingListDayEntity entity=new RankingListDayEntity();
        Long rankingListId=null;
    	if(params !=null) {
    		if(params.get("key")!=null && !"".equals(params.get("key").toString().trim())) {
    			entity.setUserAccount(params.get("key").toString().trim());
    		}
    		if(params.get("rankingListId")!=null && !"".equals(params.get("rankingListId").toString().trim())) {
    			rankingListId=Long.parseLong(params.get("rankingListId").toString().trim());
    			entity.setRankingListId(rankingListId);
    		}
    		if(params.get("thatDay")!=null && !"".equals(params.get("thatDay").toString().trim())) {
    			entity.setThatDay(DateUtils.stringToDate(params.get("thatDay").toString().trim(), DateUtils.DATE_PATTERN));
    		}
    	}
    	Wrapper<RankingListDayEntity> wrapper = new EntityWrapper<RankingListDayEntity>(entity);
    	wrapper.orderBy(" that_day desc ,position asc");
    	Page<RankingListDayEntity> page = this.selectPage(
                new Query<RankingListDayEntity>(params).getPage(),
                wrapper
        );
    	if(page.getTotal()>0) {
    		Map<Long, String> rankingListMap=new HashMap<>();
    		if(rankingListId!=null) {
    			rankingListMap.put(rankingListId, rankingListDao.selectById(rankingListId).getRankListName());
    		}else {
    			List<RankingListEntity> rankingListList=rankingListDao.selectList(new EntityWrapper<RankingListEntity>(new RankingListEntity()));
    			for(RankingListEntity rankingList:rankingListList) {
    				rankingListMap.put(rankingList.getId(),rankingList.getRankListName());
    			}
    		}
    		List<RankingListDayEntity> dataList=page.getRecords();
    		for(RankingListDayEntity rankingListDay: dataList) {
    			rankingListDay.setRankingListName(rankingListMap.get(rankingListDay.getRankingListId()));
    		}
    	}

        return new PageUtils(page);
    }

	@Override
	public void save(RankingListDayEntity entity) {
		UserEntity user =new UserEntity();
		user.setAccount(entity.getUserAccount());
		user=userDao.selectOne(user);
		if(user==null || user.getId()==null) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		List<RankingListDayEntity> rankingListDayListForExist = baseMapper.
				getRankingListByDayAndRidTop100(entity);
		entity.setUserId(user.getId());
		entity.setPosition(1);
		if(!CollectionUtils.isEmpty(rankingListDayListForExist)) {
			//当日有其他的数据需要重新排序下
			rankingListDayListForExist.add(entity);
			Collections.sort(rankingListDayListForExist, new Comparator<RankingListDayEntity>() {
				@Override
				public int compare(RankingListDayEntity u1, RankingListDayEntity u2) {
					return u2.getAmount().compareTo(u1.getAmount());
				}
			});// 按排行值amount从大到小排序
			List<RankingListDayEntity> insertList =new ArrayList<>();
			List<RankingListDayEntity> updateList =new ArrayList<>();
			int position=1;
			for (RankingListDayEntity data : rankingListDayListForExist) {
				data.setPosition(position);
				position++;
				if(data.getId()!=null && data.getId()>0l) {
					updateList.add(data);
				}else {
					insertList.add(data);
				}
			}
			if (!CollectionUtils.isEmpty(insertList)) {
				this.baseMapper.insert(insertList.get(0));
			}
			if (!CollectionUtils.isEmpty(updateList)) {
				this.updateBatchById(updateList,1000);
			}
		}else {
			//当日没有其他的数据
			if(entity.getId()!=null && entity.getId()>0) {
				this.baseMapper.updateById(entity);
			}else {
				this.baseMapper.insert(entity);
			}
		}
		
		
		
	}

	@Override
	public void batchDelete(Long[] ids) {
		for(Long id:ids) {
			RankingListDayEntity deleteEntity=this.baseMapper.selectById(id);
			List<RankingListDayEntity> rankingListDayListForExist = baseMapper.
					getRankingListByDayAndRidTop100(deleteEntity);
			if(!CollectionUtils.isEmpty(rankingListDayListForExist)) {
				//最后一笔的amount
				BigDecimal amount=rankingListDayListForExist.get(rankingListDayListForExist.size()-1).getAmount();
				//最后一笔的amount比删除的amount小或者等于 就需要重新排序
				if(amount.compareTo(deleteEntity.getAmount())<=0) {
					Collections.sort(rankingListDayListForExist, new Comparator<RankingListDayEntity>() {
						@Override
						public int compare(RankingListDayEntity u1, RankingListDayEntity u2) {
							return u2.getAmount().compareTo(u1.getAmount());
						}
					});// 按排行值amount从大到小排序
					int position=1;
					List<RankingListDayEntity> updateList = new ArrayList<>();
					for (RankingListDayEntity data : rankingListDayListForExist) {
						if(position!=data.getPosition()) {
							data.setPosition(position);
							updateList.add(data);
						}
						position++;
					}
					if(!CollectionUtils.isEmpty(updateList)) {
						this.updateBatchById(updateList,100);
					}
					
				}
			}
			this.baseMapper.physicalDeleteById(id);
		}
		
	}

	@Override
	@Transactional
	public void saveBatchForTask(List<RankingListDayEntity> rankingListDayList, Long rankingListId, Date yesterday) {
		
		if(CollectionUtils.isEmpty(rankingListDayList)) {
			return ;
		}
		Integer position=1;
		for (RankingListDayEntity entity : rankingListDayList) {
			entity.setThatDay(yesterday);
			entity.setRankingListId(rankingListId);
			entity.setPosition(position);
			position++;
		}
		List<RankingListDayEntity> insertList =new ArrayList<>();
		List<RankingListDayEntity> updateList =new ArrayList<>();
		//查询是否有添加的排行
		RankingListDayEntity queryEntity=new RankingListDayEntity();
		queryEntity.setThatDay(yesterday);
		queryEntity.setRankingListId(rankingListId);
		List<RankingListDayEntity> rankingListDayListForExist = this.
				selectList(new EntityWrapper<RankingListDayEntity>(queryEntity)
						.orderBy("amount", false));
		if(!CollectionUtils.isEmpty(rankingListDayListForExist)) {
			rankingListDayListForExist.addAll(rankingListDayList);
			Collections.sort(rankingListDayListForExist, new Comparator<RankingListDayEntity>() {
				@Override
				public int compare(RankingListDayEntity u1, RankingListDayEntity u2) {
					return u2.getAmount().compareTo(u1.getAmount());
				}
			});// 按排行值amount从大到小排序
			position=1;
			for (RankingListDayEntity entity : rankingListDayListForExist) {
				entity.setPosition(position);
				position++;
				if(entity.getId()!=null && entity.getId()>0l) {
					updateList.add(entity);
				}else {
					insertList.add(entity);
				}
			}
		}else {
			insertList.addAll(rankingListDayList);
		}
		if (!CollectionUtils.isEmpty(insertList)) {
			try {
				this.insertBatch(insertList, batchSize);
			} catch (Exception e) {
				log.error("[rankingListDayService.insertBatch]", e);
				try {
					Thread.sleep(3000L);
					log.info("第一次插入失败间隔3秒再次尝试插入");
				} catch (InterruptedException e1) {
					log.error("[Thread.sleep(3000L); rankingListDayService.insertBatch]", e);
				}
				this.insertBatch(insertList, batchSize);
			}
		}
		if (!CollectionUtils.isEmpty(updateList)) {
			try {
				this.updateBatchById(updateList, batchSize);
			} catch (Exception e) {
				log.error("[rankingListDayService.updateBatchById]", e);
				try {
					Thread.sleep(3000L);
					log.info("第一次更新失败间隔3秒再次尝试插入");
				} catch (InterruptedException e1) {
					log.error("[Thread.sleep(3000L); ]", e);
				}
				this.updateBatchById(updateList, batchSize);
			}
		}
		
	}

	@Override
	public List<RankingListWeekEntity> statisticsRankingListByDateRange(Long rankingListId, String startDate,
			String endDate) {
		
		return this.baseMapper.statisticsRankingListByDateRange(rankingListId, startDate, endDate);
	}

}
