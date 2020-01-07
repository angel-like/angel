package com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.sangong.qiangzhuang;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.xmsy.server.zxyy.webhome.common.validator.group.AddGroup;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.base.BaseGameRecord;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 抢庄三公 游戏记录
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-03-16 14:47:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SangongQiangzhuangGameRecordParams extends BaseGameRecord {
	
	/**
	 * 玩家牌型集合
	 */
	@NotBlank(message = "玩家牌型集合不能为空", groups = AddGroup.class)
	private List<Integer> playerCard;
	
	/**
	 * 结算牌型集合
	 */
	@NotBlank(message = "结算牌型集合不能为空", groups = AddGroup.class)
	private Integer[][] settlementCard;

	/**
	 * 游戏用户列表
	 */
	@NotBlank(message = "游戏用户列表不能为空", groups = AddGroup.class)
	private List<SangongQiangzhuangUserRecordParams> userRecord;

}
