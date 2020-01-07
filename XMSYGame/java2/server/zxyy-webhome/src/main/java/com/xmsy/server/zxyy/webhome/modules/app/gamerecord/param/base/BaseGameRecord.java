package com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.base;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
public class BaseGameRecord {
	
	/**
	 * 游戏id
	 */
	@NotNull(message = "游戏id不能为空")
    private Long gameId;
    /**
     * 场次id
     */
	@NotNull(message = "场次id不能为空")
    private Long gradeId;
	
	 /**
     * 场次编号
     */
    private Integer gradeNumber;
    /**
     * 房间id
     */
	@NotNull(message = "房间id不能为空")
    private Long roomId;
    /**
     * 游戏局号
     */
	@NotNull(message = "游戏局号不能为空")
    private String gameRoundNo;
    /**
     * 是否小游戏
     */
    private Boolean mini;
}
