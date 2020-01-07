package com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.longhu;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.xmsy.server.zxyy.manager.common.validator.group.AddGroup;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.base.BairenBetUserRecordParams;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.base.BaseGameRecord;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * .龙虎斗游戏记录
 * 
 * @author aleng
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LongHuGameRecordParams extends BaseGameRecord {

	/**
	 * 丢弃的牌
	 */
	@NotNull(message = "丢弃的牌不能为空", groups = AddGroup.class)
	private Integer openCard;

	/**
	 * 庄家牌
	 */
	@NotNull(message = "龙牌型不能为空", groups = AddGroup.class)
	private Integer dragonCard;

	/**
	 * 闲家牌
	 */
	@NotNull(message = "虎牌型不能为空", groups = AddGroup.class)
	private Integer tigerCard;

	/**
	 * 游戏用户列表
	 */
	@NotNull(message = "游戏用户列表不能为空", groups = AddGroup.class)
	private List<BairenBetUserRecordParams> userRecord;

}
