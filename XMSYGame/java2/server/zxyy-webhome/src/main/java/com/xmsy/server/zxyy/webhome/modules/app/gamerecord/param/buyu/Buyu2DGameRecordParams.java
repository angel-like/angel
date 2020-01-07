package com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.buyu;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.xmsy.server.zxyy.webhome.common.validator.group.AddGroup;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.base.BaseGameRecord;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 2d捕鱼
 * 游戏记录
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-02-28 15:47:25
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
public class Buyu2DGameRecordParams extends BaseGameRecord  {
	
	/**
	 * 游戏模式（经典模式）
	 */
	@NotBlank(message = "游戏模式不能为空", groups = AddGroup.class)
	private Integer gameModule;
	
    /**
     * 游戏用户列表
     */
    @NotBlank(message = "游戏用户列表不能为空", groups = AddGroup.class)
    private List<Buyu2DUserRecordParams> userRecord;

	
	}
