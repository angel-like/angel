package com.xmsy.server.zxyy.webhome.modules.manager.webhomecontact.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.Query;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomecontact.dao.WebhomeContactDao;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomecontact.entity.WebContactManagementEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomecontact.entity.WebhomeContactEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomecontact.service.WebhomeContactService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("contactManagementService")
public class WebhomeContactServiceImpl extends ServiceImpl<WebhomeContactDao, WebhomeContactEntity> implements WebhomeContactService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<WebhomeContactEntity> page = this.selectPage(
                new Query<WebhomeContactEntity>(params).getPage(),
                new EntityWrapper<WebhomeContactEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public List<WebContactManagementEntity> selectListByWeb() {
		List<WebContactManagementEntity> list=baseMapper.selectListByWeb();
		log.info("[selectListByWeb] list {}", list);
		return list;
	}

}
