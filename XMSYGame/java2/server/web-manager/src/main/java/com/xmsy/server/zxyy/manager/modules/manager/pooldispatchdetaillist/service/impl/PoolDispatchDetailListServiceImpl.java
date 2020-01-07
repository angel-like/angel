package com.xmsy.server.zxyy.manager.modules.manager.pooldispatchdetaillist.service.impl;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.Query;
import com.xmsy.server.zxyy.manager.modules.manager.pooldispatchdetaillist.dao.PoolDispatchDetailListDao;
import com.xmsy.server.zxyy.manager.modules.manager.pooldispatchdetaillist.entity.PoolDispatchDetailListEntity;
import com.xmsy.server.zxyy.manager.modules.manager.pooldispatchdetaillist.service.PoolDispatchDetailListService;

@Transactional
@Service("poolDispatchDetailListService")
public class PoolDispatchDetailListServiceImpl extends ServiceImpl<PoolDispatchDetailListDao, PoolDispatchDetailListEntity> implements PoolDispatchDetailListService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        PoolDispatchDetailListEntity entity=new PoolDispatchDetailListEntity();
    	if(params !=null) {
    		if(params.get("taskId")!=null && !"".equals(params.get("taskId").toString().trim())) {
    			entity.setTaskId(Long.parseLong(params.get("taskId").toString().trim()));
    		}
    		if(params.get("key")!=null && !"".equals(params.get("key").toString().trim())) {
    			entity.setUserAccount(params.get("key").toString().trim());
    		}
    	}
    	Wrapper<PoolDispatchDetailListEntity> wrapper = new EntityWrapper<PoolDispatchDetailListEntity>(entity);
    	String sort=params.get("sort")!=null?params.get("sort").toString():"";
    	if (!StringUtils.isEmpty(sort)) {
    		String direction=params.get("direction")!=null?params.get("direction").toString():"";
    		if ("desc".equalsIgnoreCase(direction)) {
    			wrapper.orderDesc(Arrays.asList(sort.split(",")));
    		} else {
    			wrapper.orderAsc(Arrays.asList(sort.split(",")));
    		}
		}
    	Page<PoolDispatchDetailListEntity> page = this.selectPage(
                new Query<PoolDispatchDetailListEntity>(params).getPage(),
                wrapper
        );

        return new PageUtils(page);
    }

}
