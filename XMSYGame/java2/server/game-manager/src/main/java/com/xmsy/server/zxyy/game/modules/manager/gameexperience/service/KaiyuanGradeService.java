package com.xmsy.server.zxyy.game.modules.manager.gameexperience.service;




import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.game.modules.manager.gameexperience.entity.KaiyuanGradeEntity;

/**
 * 开元游戏场次等级
 *
 * @author ahui
 * @email sunlightcs@gmail.com
 * @date 2019-11-28 09:12:57
 */
public interface KaiyuanGradeService extends IService<KaiyuanGradeEntity> {
    Page<KaiyuanGradeEntity> findList(KaiyuanGradeEntity kaiyuanGradeEntity, PageParam pageParam);

}

