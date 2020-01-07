package com.xmsy.server.zxyy.manager.modules.manager.rankinglistweek.service.impl;

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
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.DateUtils;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.Query;
import com.xmsy.server.zxyy.manager.modules.manager.rankinglist.dao.RankingListDao;
import com.xmsy.server.zxyy.manager.modules.manager.rankinglist.entity.RankingListEntity;
import com.xmsy.server.zxyy.manager.modules.manager.rankinglistweek.dao.RankingListWeekDao;
import com.xmsy.server.zxyy.manager.modules.manager.rankinglistweek.entity.RankingListWeekEntity;
import com.xmsy.server.zxyy.manager.modules.manager.rankinglistweek.service.RankingListWeekService;
import com.xmsy.server.zxyy.manager.modules.manager.user.dao.UserDao;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("rankingListWeekService")
public class RankingListWeekServiceImpl extends ServiceImpl<RankingListWeekDao, RankingListWeekEntity> implements RankingListWeekService {
	@Autowired
	private UserDao userDao;
	// 批量插入数据的数量
	private static final int batchSize = 100;
	@Autowired
	private RankingListDao rankingListDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        RankingListWeekEntity entity=new RankingListWeekEntity();
        Long rankingListId=null;
    	if(params !=null) {
    		if(params.get("key")!=null && !"".equals(params.get("key").toString().trim())) {
    			entity.setUserAccount(params.get("key").toString().trim());
    		}
    		if(params.get("rankingListId")!=null && !"".equals(params.get("rankingListId").toString().trim())) {
    			rankingListId=Long.parseLong(params.get("rankingListId").toString().trim());
    			entity.setRankingListId(rankingListId);
    		}
    		if(params.get("firstOfWeek")!=null && !"".equals(params.get("firstOfWeek").toString().trim())) {
    			Date firstOfWeek=DateUtils.stringToDate(params.get("firstOfWeek").toString().trim(), DateUtils.DATE_PATTERN);
    			entity.setWeekOfYear(DateUtils.getWeekOfYear(firstOfWeek));
    		}
    	}
    	Wrapper<RankingListWeekEntity> wrapper = new EntityWrapper<RankingListWeekEntity>(entity);
    	wrapper.orderBy("week_of_year desc,position asc");
    	Page<RankingListWeekEntity> page = this.selectPage(
                new Query<RankingListWeekEntity>(params).getPage(),
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
    		List<RankingListWeekEntity> dataList=page.getRecords();
    		for(RankingListWeekEntity rankingListDay: dataList) {
    			rankingListDay.setRankingListName(rankingListMap.get(rankingListDay.getRankingListId()));
    		}
    	}

        return new PageUtils(page);
    }

	@Override
	@Transactional
	public void save(RankingListWeekEntity entity) {
		UserEntity user =new UserEntity();
		user.setAccount(entity.getUserAccount());
		user=userDao.selectOne(user);
		if(user==null || user.getId()==null) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		entity.setWeekOfYear(DateUtils.getWeekOfYear(entity.getFirstOfWeek()));
		List<RankingListWeekEntity> rankingListWeekListForExist = baseMapper.
				getRankingListByWeekAndRidTop100(entity);
		entity.setUserId(user.getId());
		entity.setPosition(1);
		if(!CollectionUtils.isEmpty(rankingListWeekListForExist)) {
			//当日有其他的数据需要重新排序下
			rankingListWeekListForExist.add(entity);
			Collections.sort(rankingListWeekListForExist, new Comparator<RankingListWeekEntity>() {
				@Override
				public int compare(RankingListWeekEntity u1, RankingListWeekEntity u2) {
					return u2.getAmount().compareTo(u1.getAmount());
				}
			});// 按排行值amount从大到小排序
			List<RankingListWeekEntity> insertList =new ArrayList<>();
			List<RankingListWeekEntity> updateList =new ArrayList<>();
			int position=1;
			for (RankingListWeekEntity data : rankingListWeekListForExist) {
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
			RankingListWeekEntity deleteEntity=this.baseMapper.selectById(id);
			List<RankingListWeekEntity> rankingListWeekListForExist = baseMapper.
					getRankingListByWeekAndRidTop100(deleteEntity);
			if(!CollectionUtils.isEmpty(rankingListWeekListForExist)) {
				//最后一笔的amount
				BigDecimal amount=rankingListWeekListForExist.get(rankingListWeekListForExist.size()-1).getAmount();
				//最后一笔的amount比删除的amount小或者等于 就需要重新排序
				if(amount.compareTo(deleteEntity.getAmount())<=0) {
					Collections.sort(rankingListWeekListForExist, new Comparator<RankingListWeekEntity>() {
						@Override
						public int compare(RankingListWeekEntity u1, RankingListWeekEntity u2) {
							return u2.getAmount().compareTo(u1.getAmount());
						}
					});// 按排行值amount从大到小排序
					int position=1;
					List<RankingListWeekEntity> updateList=new ArrayList<RankingListWeekEntity>();
					for (RankingListWeekEntity data : rankingListWeekListForExist) {
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
	public void saveBatchForTask(List<RankingListWeekEntity> rankingListWeekList, Long rankingListId, int weekNum) {
		if(CollectionUtils.isEmpty(rankingListWeekList)) {
			return ;
		}
		Integer position=1;
		for (RankingListWeekEntity entity : rankingListWeekList) {
			entity.setWeekOfYear( weekNum);
			entity.setRankingListId(rankingListId);
			entity.setPosition(position);
			position++;
		}
		List<RankingListWeekEntity> insertList =new ArrayList<>();
		List<RankingListWeekEntity> updateList =new ArrayList<>();
		//查询是否有添加的排行
		RankingListWeekEntity queryEntity=new RankingListWeekEntity();
		queryEntity.setWeekOfYear( weekNum);;
		queryEntity.setRankingListId(rankingListId);
		List<RankingListWeekEntity> rankingListWeekListForExist = this.
				selectList(new EntityWrapper<RankingListWeekEntity>(queryEntity)
						.orderBy("amount", false));
		if(!CollectionUtils.isEmpty(rankingListWeekListForExist)) {
			rankingListWeekListForExist.addAll(rankingListWeekList);
			Collections.sort(rankingListWeekListForExist, new Comparator<RankingListWeekEntity>() {
				@Override
				public int compare(RankingListWeekEntity u1, RankingListWeekEntity u2) {
					return u2.getAmount().compareTo(u1.getAmount());
				}
			});// 按排行值amount从大到小排序
			position=1;
			for (RankingListWeekEntity entity : rankingListWeekListForExist) {
				entity.setPosition(position);
				position++;
				if(entity.getId()!=null && entity.getId()>0l) {
					updateList.add(entity);
				}else {
					insertList.add(entity);
				}
			}
		}else {
			insertList.addAll(rankingListWeekList);
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

}
