package com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.haochehui;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.xmsy.server.zxyy.webhome.common.validator.group.AddGroup;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.base.BaseGameRecord;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.base.HaochehuiBetUserRecordParams;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * .豪车会记录
 * 
 * @author aleng
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HaochehuiGameRecordParams extends BaseGameRecord {

	
	/**
	 * 开奖牌型
	 */
	@NotNull(message = "开奖牌型不能为空", groups = AddGroup.class)
	private Integer lotteryCard;

	/**
	 * 游戏用户列表
	 */
	@NotNull(message = "游戏用户列表不能为空", groups = AddGroup.class)
	private List<HaochehuiBetUserRecordParams> userRecord;

}
