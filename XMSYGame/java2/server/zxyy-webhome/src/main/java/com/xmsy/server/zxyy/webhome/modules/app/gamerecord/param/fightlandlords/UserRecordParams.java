package com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.fightlandlords;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.xmsy.server.zxyy.webhome.common.validator.group.AddGroup;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户记录
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-02-20 16:07:25
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
public class UserRecordParams  {
	
	/**
	 * 用户id
	 */
	@NotNull(message = "用户id不能为空", groups = AddGroup.class)
    private Long userId;
	/**
	 * 账号名称
	 */
	@NotBlank(message = "用户账号不能为空", groups = AddGroup.class)
	private String userAccount;
	/**
	 * 赢输金币
	 */
	@NotNull(message = "赢输金币不能为空", groups = AddGroup.class)
	private Long coins;
	/**
	 * 底分
	 */
	@NotNull(message = "底分不能为空", groups = AddGroup.class)
	private Long score;
	/**
	 * 倍数
	 */
	@NotNull(message = "倍数不能为空", groups = AddGroup.class)
	private Integer multiple;
	/**
	 * 是否地主
	 */
	private Boolean landlord;
	/**
	 * 是否机器人
	 */
	@NotBlank(message = "是否机器人不能为空", groups = AddGroup.class)
	private Boolean robot;
	
	/**
	 * 下注前金币
	 */
	@NotNull(message = "下注前金币不能为空", groups = AddGroup.class)
	private Long coinsBefore;
	/**
	 *结束后金币
	 */
	@NotNull(message = "结束后金币不能为空", groups = AddGroup.class)
	private Long coinsAfter;
	
	}
