package com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.xmsy.server.zxyy.webhome.common.validator.group.AddGroup;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 总游戏记录
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-02-20 16:05:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AppFangkaGameRecordParams implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 游戏局号
	 */
    @NotNull(message = "游戏局号不能为空", groups = AddGroup.class)
	private String gameRoundNo;
    /**
     * 游戏房间号
     */
    @NotNull(message = "游戏房间号不能为空", groups = AddGroup.class)
    private String roomNo;

}
