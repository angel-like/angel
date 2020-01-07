package com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.twoeightbar;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.xmsy.server.zxyy.webhome.common.validator.group.AddGroup;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.base.BaseGameRecord;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 二八杠
 * 游戏记录
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-03-16 14:47:25
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
public class TwoEightBarGameRecordParams extends BaseGameRecord  {
	
	 /**
     * 局数
     */
    private Integer round;
	
    /**
     * 游戏用户列表
     */
    @NotBlank(message = "游戏用户列表不能为空", groups = AddGroup.class)
    private List<TwoEightBarUserRecordParams> userRecord;

	
	}
