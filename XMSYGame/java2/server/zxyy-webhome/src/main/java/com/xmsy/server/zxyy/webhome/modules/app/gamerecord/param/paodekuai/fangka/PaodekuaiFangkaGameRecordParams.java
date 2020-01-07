package com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.paodekuai.fangka;

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
public class PaodekuaiFangkaGameRecordParams extends BaseGameRecord  {
	
	 /**
     * 局数
     */
    private Integer round;
    /**
     * 游戏房间号
     */
    private String roomNo;
   
    /**
     * 游戏模式
     */
    private Integer gameModule;
    /**
     * 玩法模式
     */
    private Integer gameType;
    /**
     * 支付类型 1.房主支付 2.AA支付
     */
    private Integer payType;
	
    /**
     * 游戏用户列表
     */
    @NotBlank(message = "游戏用户列表不能为空", groups = AddGroup.class)
    private List<PaodekuaiFangkaUserRecordParams> userRecord;

	
	}
