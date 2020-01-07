package com.xmsy.server.zxyy.manager.modules.manager.fortuneactivitymanager.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.modules.manager.fortuneactivitymanager.dao.EnvelopeRecordDao;
import com.xmsy.server.zxyy.manager.modules.manager.fortuneactivitymanager.entity.EnvelopeRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.fortuneactivitymanager.service.EnvelopeRecordService;
import org.springframework.stereotype.Service;


@Service("envelopeRecordService")
public class EnvelopeRecordServiceImpl extends ServiceImpl<EnvelopeRecordDao, EnvelopeRecordEntity> implements EnvelopeRecordService {

    @Override
    public Page<EnvelopeRecordEntity> count(PageParam pageParam, EnvelopeRecordEntity envelopeRecordEntity){
        Page<EnvelopeRecordEntity> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
        return page.setRecords(baseMapper.getCount(page,envelopeRecordEntity));
    };

}
