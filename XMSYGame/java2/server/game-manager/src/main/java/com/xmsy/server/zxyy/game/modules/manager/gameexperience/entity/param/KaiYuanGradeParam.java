package com.xmsy.server.zxyy.game.modules.manager.gameexperience.entity.param;

import lombok.Data;


@Data
public class KaiYuanGradeParam {

    /**
     * 游戏id
     */
    private Integer gameId;
    /**
     * 开元房间号
     */
    private Integer serviceId;
    /**
     * 场次名称
     */
    private String name;

    /**
     * 场次等级id
     */
    private Integer gradeId;
}
