package com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.sangong.qiangzhuang;

import javax.validation.constraints.NotBlank;

import com.xmsy.server.zxyy.webhome.common.validator.group.AddGroup;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 抢庄三公 用户记录
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-02-28 15:50:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SangongQiangzhuangUserRecordParams {

	/**
	 * 用户id
	 */
	@NotBlank(message = "用户id不能为空", groups = AddGroup.class)
	private Long userId;
	/**
	 * 账号名称
	 */
	@NotBlank(message = "用户账号不能为空", groups = AddGroup.class)
	private String userAccount;
	/**
	 * 下注前金币
	 */
	@NotBlank(message = "下注前金币不能为空", groups = AddGroup.class)
	private Long coinsBefore;
	/**
	 * 下注金币
	 */
	@NotBlank(message = "下注金币不能为空", groups = AddGroup.class)
	private Long betCoins;

	/**
	 * 赢输金币
	 */
	@NotBlank(message = "赢输金币不能为空", groups = AddGroup.class)
	private Long coins;
	/**
	 * 结束后金币
	 */
	@NotBlank(message = "结束后金币不能为空", groups = AddGroup.class)
	private Long coinsAfter;
	/**
	 * 底分
	 */
	@NotBlank(message = "房间底分不能为空", groups = AddGroup.class)
	private Long baseScore;
	
	/**
	 * 总盈亏
	 */
	@NotBlank(message = "总盈亏不能为空", groups = AddGroup.class)
	private Long prizeCoins;
	
	/**
	 * 庄家倍数
	 */
	@NotBlank(message = "庄家倍数不能为空", groups = AddGroup.class)
	private Integer bankerMultiple;
	
	/**
	 * 下注倍数
	 */
	@NotBlank(message = "下注倍数不能为空", groups = AddGroup.class)
	private Integer betMultiple;
	/**
	 * 是否机器人
	 */
	@NotBlank(message = "是否机器人不能为空", groups = AddGroup.class)
	private Boolean robot;
	/**
	 * 是否庄家
	 */
	@NotBlank(message = "是否庄家不能为空", groups = AddGroup.class)
	private Boolean banker;

}
