package com.xmsy.server.zxyy.manager.modules.manager.webhomehead.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.Query;
import com.xmsy.server.zxyy.manager.modules.manager.webhomehead.dao.WebhomeHeadDao;
import com.xmsy.server.zxyy.manager.modules.manager.webhomehead.entity.WebhomeHeadEntity;
import com.xmsy.server.zxyy.manager.modules.manager.webhomehead.service.WebhomeHeadService;


@Service("webhomeHeadService")
public class WebhomeHeadServiceImpl extends ServiceImpl<WebhomeHeadDao, WebhomeHeadEntity> implements WebhomeHeadService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<WebhomeHeadEntity> page = this.selectPage(
                new Query<WebhomeHeadEntity>(params).getPage(),
                new EntityWrapper<WebhomeHeadEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 获取头部列表
     */
	@Override
	public List<WebhomeHeadEntity> selectListByWeb() {
		
		
		return baseMapper.selectListByWeb();
	}

	@Override
	public List<WebhomeHeadEntity> selectListBytype(String type) {
		return baseMapper.selectListBytype(type);
	}

}
