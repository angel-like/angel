package com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.laba;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.base.BaseGameRecord;
import com.xmsy.server.zxyy.manager.modules.app.gamerecord.param.base.LabaBetUserRecordParams;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/***
 * .海底宝藏详细游戏记录
 * 
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UnderseaTreasureGameRecordParams extends BaseGameRecord {

	/**
	 * 游戏用户列表
	 */
	@NotNull(message = "游戏用户列表不能为空")
	private List<LabaBetUserRecordParams> userRecord;

}
