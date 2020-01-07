package com.xmsy.server.zxyy.manager.modules.manager.userlog.service.impl;

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
import com.xmsy.server.zxyy.manager.modules.manager.userlog.dao.UserLogDao;
import com.xmsy.server.zxyy.manager.modules.manager.userlog.entity.UserLogEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userlog.service.UserLogService;

@Transactional
@Service("userLogService")
public class UserLogServiceImpl extends ServiceImpl<UserLogDao, UserLogEntity> implements UserLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	UserLogEntity entity=new UserLogEntity();
    	if(params !=null) {
    		if(params.get("userAccount")!=null) {
    			entity.setMemberAccount(params.get("userAccount").toString());
    		}
    		if(params.get("operationType")!=null) {
    			entity.setOperation(params.get("operationType").toString());
    		}
    		if(params.get("operator")!=null) {
    			entity.setUsername(params.get("operator").toString());
    		}
    	}
    	Wrapper<UserLogEntity> wrapper = new EntityWrapper<UserLogEntity>(entity);
    	if(params !=null) {
    		if(params.get("startTime")!=null) {
    			wrapper.ge("create_date", params.get("startTime"));
    		}
    		if(params.get("endTime")!=null) {
    			wrapper.lt("create_date", params.get("endTime"));
    		}
    	}
    	String sort=params.get("sort")!=null?params.get("sort").toString():"";
    	if (!StringUtils.isEmpty(sort)) {
    		String direction=params.get("direction")!=null?params.get("direction").toString():"";
    		if ("desc".equalsIgnoreCase(direction)) {
    			wrapper.orderDesc(Arrays.asList(sort.split(",")));
    		} else {
    			wrapper.orderAsc(Arrays.asList(sort.split(",")));
    		}
		}
    	Page<UserLogEntity> page = this.selectPage(
                new Query<UserLogEntity>(params).getPage(),
                wrapper
        );


        return new PageUtils(page);
    }

}
