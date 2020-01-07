package com.xmsy.server.zxyy.webhome.modules.manager.webhomeenclosure.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.Query;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomeenclosure.dao.WebhomeEnclosureDao;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomeenclosure.entity.WebhomeEnclosureEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomeenclosure.service.WebhomeEnclosureService;


@Service("enclosureService")
public class WebhomeEnclosureServiceImpl extends ServiceImpl<WebhomeEnclosureDao, WebhomeEnclosureEntity> implements WebhomeEnclosureService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<WebhomeEnclosureEntity> page = this.selectPage(
                new Query<WebhomeEnclosureEntity>(params).getPage(),
                new EntityWrapper<WebhomeEnclosureEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public Long insertGetId(WebhomeEnclosureEntity entity) {
		baseMapper.insertGetId(entity);
		return entity.getId();
	}

}
