package com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.twentyonespot;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.xmsy.server.zxyy.webhome.common.validator.group.AddGroup;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.base.BaseGameRecord;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 21点
 * 游戏记录
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-03-16 14:47:25
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
public class TwentyOneSpotGameRecordParams extends BaseGameRecord  {
	
	/**
	 * 玩家牌型集合
	 */
	@NotBlank(message = "玩家牌型集合不能为空", groups = AddGroup.class)
	private List<Integer> playerCards;
	/**
	 * 庄家牌型集合
	 */
	@NotBlank(message = "庄家牌型集合不能为空", groups = AddGroup.class)
	private List<Integer> bankerCards;
	
    /**
     * 游戏用户列表
     */
    @NotBlank(message = "游戏用户列表不能为空", groups = AddGroup.class)
    private List<TwentyOneSpotUserRecordParams> userRecord;

	
	}
