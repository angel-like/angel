package com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.dezhoupuke;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.xmsy.server.zxyy.webhome.common.validator.group.AddGroup;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.base.BaseGameRecord;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 跑得快
 * 游戏记录
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-02-28 15:47:25
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
public class DezhoupukeGameRecordParams extends BaseGameRecord  {
	
	/**
	 * 公共牌型
	 */
	private List<Integer> publicCard;

	/**
	 * 玩家牌型
	 */
	@NotNull(message = "玩家牌型不能为空", groups = AddGroup.class)
	private Object[][] playCard;
	
    /**
     * 游戏用户列表
     */
    @NotBlank(message = "游戏用户列表不能为空", groups = AddGroup.class)
    private List<DezhoupukeUserRecordParams> userRecord;

	
	}
