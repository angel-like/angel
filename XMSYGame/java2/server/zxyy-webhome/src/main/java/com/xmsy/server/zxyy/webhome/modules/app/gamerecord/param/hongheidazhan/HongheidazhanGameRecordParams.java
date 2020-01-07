package com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.hongheidazhan;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.xmsy.server.zxyy.webhome.common.validator.group.AddGroup;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.base.BaseGameRecord;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.base.HongheidazhanBetUserRecordParams;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * .红黑大战记录
 * 
 * @author aleng
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HongheidazhanGameRecordParams extends BaseGameRecord {

	/**
	 * 红方牌型
	 */
	@NotNull(message = "红方牌型不能为空", groups = AddGroup.class)
	private List<Integer> redCard;

	/**
	 * 黑方牌型
	 */
	@NotNull(message = "黑方牌型不能为空", groups = AddGroup.class)
	private List<Integer> blackCard;

	/**
	 * 游戏用户列表
	 */
	@NotNull(message = "游戏用户列表不能为空", groups = AddGroup.class)
	private List<HongheidazhanBetUserRecordParams> userRecord;

}
