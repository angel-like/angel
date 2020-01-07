package com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.baijia;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.xmsy.server.zxyy.manager.common.validator.group.AddGroup;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.base.BairenBetUserRecordParams;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.base.BaseGameRecord;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * .百家乐游戏记录
 * 
 * @author aleng
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaijiaLeGameRecordParams extends BaseGameRecord {

	/**
	 * 庄家牌
	 */
	@NotNull(message = "庄家牌不能为空", groups = AddGroup.class)
	private List<Integer> bankerCard;

	/**
	 * 闲家牌
	 */
	@NotNull(message = "闲家牌不能为空", groups = AddGroup.class)
	private List<Integer> idleCard;

	/**
	 * 游戏用户列表
	 */
	@NotNull(message = "游戏用户列表不能为空", groups = AddGroup.class)
	private List<BairenBetUserRecordParams> userRecord;

}
