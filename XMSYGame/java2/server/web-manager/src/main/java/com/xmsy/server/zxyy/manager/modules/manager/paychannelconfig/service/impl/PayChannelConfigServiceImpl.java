package com.xmsy.server.zxyy.manager.modules.manager.paychannelconfig.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.modules.manager.paychannelconfig.dao.PayChannelConfigDao;
import com.xmsy.server.zxyy.manager.modules.manager.paychannelconfig.entity.PayChannelConfigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.paychannelconfig.service.PayChannelConfigService;


@Service("payChannelConfigService")
public class PayChannelConfigServiceImpl extends ServiceImpl<PayChannelConfigDao, PayChannelConfigEntity> implements PayChannelConfigService {
    @Override
    public Page<PayChannelConfigEntity> getPayChannelList(PayChannelConfigEntity paychannelconfig,
                                                          PageParam pageParam) {
        Page<PayChannelConfigEntity> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
        return page.setRecords(this.baseMapper.getPayChannelList(page, paychannelconfig));
    }

    @Override
    public PayChannelConfigEntity getPayChannel(PayChannelConfigEntity entity) {
        PayChannelConfigEntity payChannelConfig = this.baseMapper.getPayChannel(entity.getPayId(), entity.getChannelId());
        if (payChannelConfig == null) {
            payChannelConfig = new PayChannelConfigEntity();
            payChannelConfig.setChannelId(entity.getChannelId());
            payChannelConfig.setPayId(entity.getPayId());
            payChannelConfig.setOrderNo(0);
            payChannelConfig.setEnable(true);
            ArrayList<Long> list = new ArrayList<>();
            payChannelConfig.setHierarchyIds(list);
        } else {
            String hierarchyId = payChannelConfig.getHierarchyId();
            String[] split = hierarchyId.split(",");
            ArrayList<Long> list = new ArrayList<>();
            for (String s : split) {
                Long l = Long.parseLong(s);
                list.add(l);
            }
            payChannelConfig.setHierarchyIds(list);
        }

        return payChannelConfig;
    }

    @Override
    public PayChannelConfigEntity getById(Long id) {
        PayChannelConfigEntity payChannelConfig = this.baseMapper.getById(id);
        String hierarchyId = payChannelConfig.getHierarchyId();
        String[] split = hierarchyId.split(",");
        ArrayList<Long> list = new ArrayList<>();
        for (String s : split) {
            Long l = Long.parseLong(s);
            list.add(l);
        }
        payChannelConfig.setHierarchyIds(list);
        return payChannelConfig;
    }

    ;

    @Override
    public Page<PayChannelConfigEntity> getPayChannelConfigs(PayChannelConfigEntity paychannelconfig,
                                                             PageParam pageParam) {
        Page<PayChannelConfigEntity> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
        return page.setRecords(this.baseMapper.getPayChannelConfigs(page, paychannelconfig));
    }

    @Override
    public Long updateOrSavePayChannelConfigs(PayChannelConfigEntity paychannelconfig) {
        PayChannelConfigEntity payChannelConfigEntity = new PayChannelConfigEntity();
        payChannelConfigEntity.setChannelId(paychannelconfig.getChannelId());
        payChannelConfigEntity.setPayId(paychannelconfig.getPayId());

        Long id;
        Wrapper<PayChannelConfigEntity> entityWrapper = new EntityWrapper<PayChannelConfigEntity>(payChannelConfigEntity);
        List<PayChannelConfigEntity> payChannelConfigEntities = this.baseMapper.selectList(entityWrapper);
        if (payChannelConfigEntities.size() == 0 || payChannelConfigEntities == null) {
            this.baseMapper.insert(paychannelconfig);
            id = paychannelconfig.getId();
        } else {
            PayChannelConfigEntity channelConfigEntity = payChannelConfigEntities.get(0);
            channelConfigEntity.setHighLimit(paychannelconfig.getHighLimit());
            channelConfigEntity.setLowLimit(paychannelconfig.getLowLimit());
            channelConfigEntity.setAmount(paychannelconfig.getAmount());
            channelConfigEntity.setEnable(paychannelconfig.getEnable());
            channelConfigEntity.setShowName(paychannelconfig.getShowName());
            channelConfigEntity.setOrderNo(paychannelconfig.getOrderNo());
            channelConfigEntity.setUpdateTime(new Date());
            this.baseMapper.updateById(channelConfigEntity);

            id = channelConfigEntity.getId();
        }
        return id;
    }


}
