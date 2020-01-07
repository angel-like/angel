package com.xmsy.server.zxyy.manager.modules.manager.pooldispatchtaskdetail.service.impl;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.Query;
import com.xmsy.server.zxyy.manager.modules.manager.pooldispatchtaskdetail.dao.PoolDispatchTaskDetailDao;
import com.xmsy.server.zxyy.manager.modules.manager.pooldispatchtaskdetail.entity.PoolDispatchTaskDetailEntity;
import com.xmsy.server.zxyy.manager.modules.manager.pooldispatchtaskdetail.service.PoolDispatchTaskDetailService;

@Transactional
@Service("poolDispatchTaskDetailService")
public class PoolDispatchTaskDetailServiceImpl extends ServiceImpl<PoolDispatchTaskDetailDao, PoolDispatchTaskDetailEntity> implements PoolDispatchTaskDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
//        Page<PoolDispatchTaskDetailEntity> page = this.selectPage(
//                new Query<PoolDispatchTaskDetailEntity>(params).getPage(),
//                new EntityWrapper<PoolDispatchTaskDetailEntity>()
//        );
        Page<Map<String, Object>> page = new Query<Map<String, Object>>(params).getPage();
		params.put("limit", page.getLimit());
		params.put("offset", page.getOffset());
		page.setTotal(baseMapper.queryTotal(params));
		if(page.getTotal()>0) {
			page.setRecords(this.baseMapper.findTaskDetailPage(params));
		}else {
			page.setRecords(new ArrayList<>());
		}
		return new PageUtils(page);
    }

	@Override
	public Integer updateEntityByIdForTrim(PoolDispatchTaskDetailEntity entity) {
		return this.baseMapper.updateEntityByIdForTrim(entity);
	}

}
