package com.xmsy.server.zxyy.game.modules.manager.gameexperience.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.game.modules.manager.gameexperience.dao.KaiyuanGradeDao;
import com.xmsy.server.zxyy.game.modules.manager.gameexperience.entity.KaiyuanGradeEntity;
import com.xmsy.server.zxyy.game.modules.manager.gameexperience.service.KaiyuanGradeService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Transactional
@Service("kaiyuanGradeService")
public class KaiyuanGradeServiceImpl extends ServiceImpl<KaiyuanGradeDao, KaiyuanGradeEntity> implements KaiyuanGradeService {

    @Override
    public Page<KaiyuanGradeEntity> findList(KaiyuanGradeEntity kaiyuanGradeEntity, PageParam pageParam) {
        log.info("[findGameInfos] kaiYuanGradeParam {} pageParam {}", kaiyuanGradeEntity, pageParam);
        Page<KaiyuanGradeEntity> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
        return page.setRecords(this.baseMapper.findList(page, kaiyuanGradeEntity));
    }

}
