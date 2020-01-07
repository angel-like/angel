package com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.feiqinzoushou;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.xmsy.server.zxyy.webhome.common.validator.group.AddGroup;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.base.BaseGameRecord;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * .飞禽走兽记录
 * 
 * @author aleng
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FeiqinzoushouGameRecordParams extends BaseGameRecord {

	
	/**
	 * 开奖牌型
	 */
	@NotNull(message = "开奖牌型不能为空", groups = AddGroup.class)
	private Integer lotteryCard;

	/**
	 * 游戏用户列表
	 */
	@NotNull(message = "游戏用户列表不能为空", groups = AddGroup.class)
	private List<FeiqinzoushouBetUserRecordParams> userRecord;

}
