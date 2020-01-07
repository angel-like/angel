package com.xmsy.server.zxyy.robot.modules.manager.gamerecord.entity;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.xmsy.server.zxyy.robot.common.validator.group.AddGroup;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 总游戏记录
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-04-02 11:44:25
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
	@NotBlank(message = "游戏用户列表不能为空", groups = AddGroup.class)
	private List<UserCoinParams> userCoin;

}
