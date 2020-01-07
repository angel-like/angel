package com.xmsy.server.zxyy.game.modules.manager.gameexperience.controller;


import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.game.common.utils.R;
import com.xmsy.server.zxyy.game.modules.manager.gameexperience.entity.KaiyuanGradeEntity;
import com.xmsy.server.zxyy.game.modules.manager.gameexperience.service.KaiyuanGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import com.xmsy.server.zxyy.game.common.utils.PageUtils;
import com.baomidou.mybatisplus.plugins.Page;


/**
 * 开元游戏管理
 *
 * @author adai
 * @email sunlightcs@gmail.com
 * @date 2019-11-28 09:12:57
 */
@RestController
@RequestMapping("gameexperience/kaiyuangrade")
public class KaiyuanGradeController {
    @Autowired
    private KaiyuanGradeService kaiyuanGradeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(KaiyuanGradeEntity kaiyuanGradeEntity, PageParam pageParam) {
        @SuppressWarnings("unused")
		Page<KaiyuanGradeEntity> result1 = kaiyuanGradeService.findList(kaiyuanGradeEntity, pageParam);
        Page<KaiyuanGradeEntity> result = new Page<KaiyuanGradeEntity>(pageParam.getPage(), pageParam.getLimit());
        Wrapper<KaiyuanGradeEntity> entityWrapper = new EntityWrapper<KaiyuanGradeEntity>(kaiyuanGradeEntity)
                .ge(!StringUtils.isEmpty(kaiyuanGradeEntity.getStartTime()), "create_time", // 时间查询
                kaiyuanGradeEntity.getStartTime())
                .le(!StringUtils.isEmpty(kaiyuanGradeEntity.getEndTime()), "create_time",
                        kaiyuanGradeEntity.getEndTime());
        entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
        kaiyuanGradeEntity.selectPage(result, entityWrapper);
        return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
                result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        KaiyuanGradeEntity kaiyuanGrade = kaiyuanGradeService.selectById(id);

        return R.ok().put("kaiyuanGrade", kaiyuanGrade);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody KaiyuanGradeEntity kaiyuanGrade) {
        kaiyuanGradeService.insert(kaiyuanGrade);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody KaiyuanGradeEntity kaiyuanGrade) {
        kaiyuanGradeService.updateById(kaiyuanGrade);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {

        for (Long id : ids) {
            kaiyuanGradeService.deleteById(id);
        }


        return R.ok();
    }

}
