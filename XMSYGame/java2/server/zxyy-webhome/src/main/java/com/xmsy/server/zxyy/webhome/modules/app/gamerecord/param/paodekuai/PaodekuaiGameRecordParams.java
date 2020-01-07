package com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.paodekuai;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.xmsy.server.zxyy.webhome.common.validator.group.AddGroup;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.base.BaseGameRecord;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 跑得快
 * 游戏记录
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-02-28 15:47:25
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
public class PaodekuaiGameRecordParams extends BaseGameRecord  {
	
    /**
     * 游戏用户列表
     */
    @NotBlank(message = "游戏用户列表不能为空", groups = AddGroup.class)
    private List<PaodekuaiUserRecordParams> userRecord;

	
	}
