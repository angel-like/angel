package com.xmsy.server.zxyy.schedule.server.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.schedule.entity.RankingListDayEntity;
import com.xmsy.server.zxyy.schedule.entity.RankingListWeekEntity;
import com.xmsy.server.zxyy.schedule.jdbc.WebHomeJdbcUtil;
import com.xmsy.server.zxyy.schedule.server.RankingListService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("rankingListService")
public class RankingListServiceImpl implements RankingListService {
	@Autowired
	private WebHomeJdbcUtil jdbcUtil;
	
	@Override
	public JSONObject getRankingListById(Long id) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT id,rank_list_name rankListName,enable,top_num topNum from  ranking_list");
		sql.append(" where id =?");
		return jdbcUtil.selectByParamReturnJsonObject(sql.toString(), id);
	}

	@Override
	public JSONArray findRankingListByDay(Long rankingListId, Date countDate) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT id,user_id userId,user_account userAccount,amount,position ");
		sql.append(" from  ranking_list_day");
		sql.append(" where that_day =? and ranking_list_id = ?");
		return jdbcUtil.selectByParamReturnJsonArray(sql.toString(), countDate,rankingListId);
	}
	
	@Override
	public JSONArray findRankingListByNeed(Long rankingListId) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT id,user_id userId,user_account userAccount,amount,position ");
		sql.append(" from  ranking_list_day");
		sql.append(" where  ranking_list_id = ? and type = 0");
		return jdbcUtil.selectByParamReturnJsonArray(sql.toString(),rankingListId);
	}
	
	@Override
	public JSONArray findRankingListWeekByWeekNum(Long rankingListId, int weekNum) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT id,user_id userId,user_account userAccount,amount,position ");
		sql.append(" from  ranking_list_week");
		sql.append(" where week_of_year =? and ranking_list_id = ?");
		return jdbcUtil.selectByParamReturnJsonArray(sql.toString(), weekNum,rankingListId);
	}

	@Override
	public void saveBatchForTaskToDay(JSONArray data, Long rankingListId, Date yesterday) {
		if(data == null) {
			return ;
		}
		List<RankingListDayEntity> rankingListDayList= data.toJavaList(RankingListDayEntity.class);
		Integer position=1;
		for (RankingListDayEntity entity : rankingListDayList) {
			if(entity.getAmount().compareTo(new BigDecimal("99999999999999999999"))>0) {
				continue;
			}
			entity.setThatDay(yesterday);
			entity.setRankingListId(rankingListId);
			entity.setPosition(position);
			position++;
		}
		List<RankingListDayEntity> insertList =new ArrayList<>();
		List<RankingListDayEntity> updateList =new ArrayList<>();
		List<RankingListDayEntity> rankingListDayListForExist =new ArrayList<>();
		//查询是否有添加的排行
		JSONArray rlist = null;
		try {
			rlist = this.findRankingListByDay(rankingListId, yesterday);
		} catch (Exception e2) {
			e2.printStackTrace();
			log.error("[saveBatchForTask.findRankingListByDay] error:{}", e2);
		}
		if(rlist!=null && rlist.size()>0) {
			rankingListDayListForExist=rlist.toJavaList(RankingListDayEntity.class);
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
		if (insertList!=null && insertList.size()>0) {
			try {
				this.batchInsertToDay(insertList);
			} catch (Exception e) {
				log.error("[rankingListDayService.insertBatch] error:{}", e);
				try {
					Thread.sleep(3000L);
					log.info("第一次插入失败间隔3秒再次尝试插入");
				} catch (InterruptedException e1) {
					log.error("[Thread.sleep(3000L); rankingListDayService.insertBatch] error:{}", e);
				}
				this.batchInsertToDay(insertList);
			}
		}
		if (updateList!=null && updateList.size()>0) {
			try {
				this.batchUpdateToDay(updateList);
			} catch (Exception e) {
				log.error("[rankingListDayService.batchUpdateToDay] error:{}", e);
				try {
					Thread.sleep(3000L);
					log.info("第一次更新失败间隔3秒再次尝试插入");
				} catch (InterruptedException e1) {
					log.error("[Thread.sleep(3000L); ] error:{}", e);
				}
				this.batchUpdateToDay(updateList);
			}
		}
		
	}

	@Override
	public void batchInsertToDay(List<RankingListDayEntity> insertList) {
		StringBuffer sql=new StringBuffer();
		sql.append("INSERT INTO `ranking_list_day` (");
		sql.append("user_id,user_account,ranking_list_id,amount,position,that_day,");
		sql.append("delete_status,version,create_time,update_time)");
		sql.append("VALUES (?,?,?,?,?,?,0,0,now(),now())");
		List<Object[]> paramsList =new ArrayList<>();
		for(RankingListDayEntity data:insertList) {
			Object[] param= new Object[6];
			param[0] = data.getUserId();
			param[1] = data.getUserAccount();
			param[2] = data.getRankingListId();
			param[3] = data.getAmount();
			param[4] = data.getPosition();
			param[5] = data.getThatDay();
			paramsList.add(param);
		}
		this.jdbcUtil.insertBatch(sql.toString(), paramsList);
	}

	@Override
	public void batchUpdateToDay(List<RankingListDayEntity> updateList) {
		StringBuffer sql=new StringBuffer();
		sql.append("update`ranking_list_day` ");
		sql.append("SET position=?");
		sql.append(",update_time=now()");
		sql.append(",version=version+1");
		sql.append("where id = ?");
		List<Object[]> paramsList =new ArrayList<>();
		for(RankingListDayEntity data:updateList) {
			Object[] param= new Object[2];
			param[0] = data.getPosition();
			param[1] = data.getId();
			paramsList.add(param);
		}
		this.jdbcUtil.updateBatch(sql.toString(), paramsList);
		

		
	}

	@Override
	public JSONArray statisticsRankingListByDateRange(Long rankingListId, String startDate, String endDate) {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT user_id userId,user_account userAccount,SUM(amount)  amount ");
		sql.append(" from  ranking_list_day");
		sql.append(" where  ranking_list_id = ?");
		sql.append(" and that_day >=?");
		sql.append(" and that_day <=?");
		sql.append(" GROUP BY user_id,user_account");
		sql.append(" order by amount DESC");
		JSONArray dataList = null;
		try {
			dataList = jdbcUtil.selectByParamReturnJsonArray(sql.toString(), rankingListId,startDate,endDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}

	@Override
	public void saveBatchForTaskToWeek(JSONArray rankingList, Long rankingListId, int weekNum) {
		if(rankingList == null || rankingList.size()==0) {
			return ;
		}
	    List<RankingListWeekEntity>	rankingListWeekList = rankingList.toJavaList(RankingListWeekEntity.class);
		Integer position=1;
		for (RankingListWeekEntity entity : rankingListWeekList) {
			if(entity.getAmount().compareTo(new BigDecimal("99999999999999999999"))>0) {
				continue;
			}
			entity.setWeekOfYear( weekNum);
			entity.setRankingListId(rankingListId);
			entity.setPosition(position);
			position++;
		}
		List<RankingListWeekEntity> insertList =new ArrayList<>();
		List<RankingListWeekEntity> updateList =new ArrayList<>();
		//查询是否有添加的排行
		
		JSONArray dataList = null;
		try {
			dataList = this.findRankingListWeekByWeekNum(rankingListId, weekNum);
		} catch (Exception e2) {
			log.error("[rankingListService.batchInsertToWeek] error:{}", e2);
			e2.printStackTrace();
		}
		List<RankingListWeekEntity> rankingListWeekListForExist =new ArrayList<>();
		if(dataList !=null && !dataList.isEmpty()) {
			rankingListWeekListForExist=dataList.toJavaList(RankingListWeekEntity.class);
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
		if (insertList!=null && !insertList.isEmpty()) {
			try {
				this.batchInsertToWeek(insertList);
			} catch (Exception e) {
				log.error("[rankingListService.batchInsertToWeek] error:{}", e);
				try {
					Thread.sleep(3000L);
					log.info("第一次插入失败间隔3秒再次尝试插入");
				} catch (InterruptedException e1) {
					log.error("[Thread.sleep(3000L);]  error:{}", e1);
				}
				this.batchInsertToWeek(insertList);
			}
		}
		if (updateList!=null && !updateList.isEmpty()) {
			try {
				this.batchUpdateToWeek(updateList);
			} catch (Exception e) {
				log.error("[rankingListService.batchUpdateToWeek]  error:{}", e);
				try {
					Thread.sleep(3000L);
					log.info("第一次更新失败间隔3秒再次尝试插入");
				} catch (InterruptedException e1) {
					log.error("[Thread.sleep(3000L); ] error:{}", e);
				}
				this.batchUpdateToWeek(updateList);
			}
		}
	}

	@Override
	public void batchInsertToWeek(List<RankingListWeekEntity> insertList) {
		StringBuffer sql=new StringBuffer();
		sql.append("INSERT INTO `ranking_list_week` (");
		sql.append("user_id,user_account,ranking_list_id,amount,position,week_of_year,");
		sql.append("delete_status,version,create_time,update_time)");
		sql.append("VALUES (?,?,?,?,?,?,0,0,now(),now())");
		List<Object[]> paramsList =new ArrayList<>();
		for(RankingListWeekEntity data:insertList) {
			Object[] param= new Object[6];
			param[0] = data.getUserId();
			param[1] = data.getUserAccount();
			param[2] = data.getRankingListId();
			param[3] = data.getAmount();
			param[4] = data.getPosition();
			param[5] = data.getWeekOfYear();
			paramsList.add(param);
		}
		this.jdbcUtil.insertBatch(sql.toString(), paramsList);
	}

	@Override
	public void batchUpdateToWeek(List<RankingListWeekEntity> updateList) {
		StringBuffer sql=new StringBuffer();
		sql.append("update`ranking_list_week` ");
		sql.append("SET position=?");
		sql.append(",update_time=now()");
		sql.append(",version=version+1");
		sql.append("where id = ?");
		List<Object[]> paramsList =new ArrayList<>();
		for(RankingListWeekEntity data:updateList) {
			Object[] param= new Object[2];
			param[0] = data.getPosition();
			param[1] = data.getId();
			paramsList.add(param);
		}
		this.jdbcUtil.updateBatch(sql.toString(), paramsList);
	}

	

}
