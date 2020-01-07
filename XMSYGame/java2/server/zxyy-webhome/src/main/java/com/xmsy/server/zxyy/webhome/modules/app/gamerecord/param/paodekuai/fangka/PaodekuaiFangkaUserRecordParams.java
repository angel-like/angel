package com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.paodekuai.fangka;

import javax.validation.constraints.NotBlank;

import com.xmsy.server.zxyy.webhome.common.validator.group.AddGroup;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 跑的快
 * 用户记录
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-02-28 15:50:25
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
public class PaodekuaiFangkaUserRecordParams  {
	
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
	 *结束后金币
	 */
	@NotBlank(message = "结束后金币不能为空", groups = AddGroup.class)
	private Long coinsAfter;
	/**
	 * 底分
	 */
	@NotBlank(message = "底分不能为空", groups = AddGroup.class)
	private Long baseScore;
	
	/**
	 * 剩余张数
	 */
	@NotBlank(message = "剩余张数不能为空", groups = AddGroup.class)
	private Integer surplusCardsNum;
	
	
	/**
	 * 被炸弹数
	 */
	@NotBlank(message = "被炸弹数不能为空", groups = AddGroup.class)
	private Integer coverBombNum;
	
	/**
	 * 所出炸弹数
	 */
	@NotBlank(message = "所出炸弹数不能为空", groups = AddGroup.class)
	private Integer bombNum;
	
	/**
	 * 总炸弹数
	 */
	@NotBlank(message = "总炸弹数不能为空", groups = AddGroup.class)
	private Integer totalBombNum;
	
	/**
	 * 包赔数
	 */
	@NotBlank(message = "包赔数不能为空", groups = AddGroup.class)
	private Integer compensateNum;
	
	/**
	 * 总盈亏
	 */
	@NotBlank(message = "总盈亏不能为空", groups = AddGroup.class)
	private Long prizeCoins;
	
	
	/**
	 * 是否机器人
	 */
	@NotBlank(message = "是否机器人不能为空", groups = AddGroup.class)
	private Boolean robot;

	/**
	 * 首出模式
	 */
	@NotBlank(message = "首出模式不能为空", groups = AddGroup.class)
	private Integer firstOut;

	/**
	 * 可选模式
	 */
	@NotBlank(message = "可选模式不能为空", groups = AddGroup.class)
	private Integer optional;

	/**
	 * 全关
	 */
	@NotBlank(message = "全关不能为空", groups = AddGroup.class)
	private String pass;

	/**
	 * 结算倍数
	 */
	@NotBlank(message = "结算倍数不能为空", groups = AddGroup.class)
	private Integer settlementTimes;
	
	}
