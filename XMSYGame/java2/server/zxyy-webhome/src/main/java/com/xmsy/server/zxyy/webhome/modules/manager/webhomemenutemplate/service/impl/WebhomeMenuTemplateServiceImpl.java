package com.xmsy.server.zxyy.webhome.modules.manager.webhomemenutemplate.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.Query;

import com.xmsy.server.zxyy.webhome.modules.manager.webhomemenutemplate.dao.WebhomeMenuTemplateDao;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomemenutemplate.entity.WebhomeMenuTemplateEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomemenutemplate.service.WebhomeMenuTemplateService;


@Service("webhomeMenuTemplateService")
public class WebhomeMenuTemplateServiceImpl extends ServiceImpl<WebhomeMenuTemplateDao, WebhomeMenuTemplateEntity> implements WebhomeMenuTemplateService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	String menuId=(String) params.get("menuId");
        Page<WebhomeMenuTemplateEntity> page = this.selectPage(
                new Query<WebhomeMenuTemplateEntity>(params).getPage(),
                new EntityWrapper<WebhomeMenuTemplateEntity>().eq("menu_id", menuId)
        );

        return new PageUtils(page);
    }

    //根据菜单ID获取菜单模板
	@Override
	public List<WebhomeMenuTemplateEntity> selectListForMenuId(Long menuId) {
		return baseMapper.selectListForWeb(menuId);
	}

}
