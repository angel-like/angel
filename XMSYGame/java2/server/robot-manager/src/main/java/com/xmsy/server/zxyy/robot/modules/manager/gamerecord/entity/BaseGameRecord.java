package com.xmsy.server.zxyy.robot.modules.manager.gamerecord.entity;

import javax.validation.constraints.NotBlank;

import com.xmsy.server.zxyy.robot.common.validator.group.AddGroup;

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
	@NotBlank(message = "游戏id不能为空", groups = AddGroup.class)
    private Long gameId;
    /**
     * 场次id
     */
	@NotBlank(message = "场次id不能为空", groups = AddGroup.class)
    private Long gradeId;
    /**
     * 房间id
     */
	@NotBlank(message = "房间id不能为空", groups = AddGroup.class)
    private Long roomId;
    /**
     * 游戏局号
     */
	@NotBlank(message = "游戏局号不能为空", groups = AddGroup.class)
    private String gameRoundNo;
    /**
     * 是否小游戏
     */
    private Boolean mini;
}
