package com.xmsy.server.zxyy.manager.modules.app.gamerecord.param;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.xmsy.server.zxyy.manager.common.validator.group.AddGroup;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.base.BaseGameRecord;

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
public class GameRecordParams extends BaseGameRecord {
	/**
     * 游戏局数
     */
    private Integer round;

	/**
	 * 游戏用户列表
	 */
	@NotNull(message = "游戏不能为空", groups = AddGroup.class)
	private List<UserCoinParams> userCoin;

}
