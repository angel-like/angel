package com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.sangong;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.xmsy.server.zxyy.webhome.common.validator.group.AddGroup;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.base.BairenSanGongBetUserRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.base.BaseGameRecord;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * .百人三公记录
 * 
 * @author aleng
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BairenSanGongGameRecordParams extends BaseGameRecord {

	/**
	 * 庄家牌
	 */
	@NotNull(message = "庄家牌不能为空", groups = AddGroup.class)
	private List<Integer> bankerCard;

	/**
	 * 闲家牌
	 */
	@NotNull(message = "闲家牌不能为空", groups = AddGroup.class)
	private Integer[][] idleCard;

	/**
	 * 游戏用户列表
	 */
	@NotNull(message = "游戏用户列表不能为空", groups = AddGroup.class)
	private List<BairenSanGongBetUserRecordParams> userRecord;

}
