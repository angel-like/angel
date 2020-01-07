package com.xmsy.server.zxyy.webhome.modules.manager.usergamenumber.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户每日游戏次数统计表
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-06-18 10:46:57
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("user_game_number")
public class UserGameNumberEntity extends BaseEntity<UserGameNumberEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 用户ID
	 */
    private Long userId;
			/**
	 * 用户账号
	 */
    private String userAccount;
			/**
	 * 房间id
	 */
    private Long roomId;
			/**
	 * 房间名称
	 */
    private String roomName;
			/**
	 * 游戏id
	 */
    private Long gameId;
			/**
	 * 游戏名称
	 */
    private String gameName;
			/**
	 * 统计日期
	 */
    private Date countDate;
			/**
	 * 游戏次数
	 */
    private Integer gameNum;
    /**
     * 胜局次数
     */
    private Integer winNum;
    /**
     * 输局次数
     */
    private Integer loseNum;
	}
