package com.xmsy.server.zxyy.webhome.modules.statistics.game.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.modules.statistics.game.dao.GameRebateDao;
import com.xmsy.server.zxyy.webhome.modules.statistics.game.service.GameRebateService;
import com.xmsy.server.zxyy.webhome.utils.InviteCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("gameRebateService")
public class GameRebateServiceImpl implements GameRebateService {
	@Resource
	private GameRebateDao gameRebateDao;

	@Override
	public void gameRebateStatisticsByDay() {
		List<Map<Object, Object>> list = gameRebateDao.selectValidBetForNow();
		List<Map<Object, Object>> gameList = gameRebateDao.selectGameList();
		if (!CollectionUtils.isEmpty(list) && !CollectionUtils.isEmpty(gameList)) {

			for (Map<Object, Object> map : list) {
				if (new BigDecimal(map.get("validBet").toString()).compareTo(BigDecimal.ZERO) != 0) {// 有效投注不为0

					for (Map<Object, Object> gameMap : gameList) {
						if (new BigDecimal(gameMap.get("rebateRate").toString()).compareTo(BigDecimal.ZERO) != 0) {// 如果游戏返利大于0
							if (map.get("gameId").toString().equals(gameMap.get("gameId").toString())) {// 如果记录游戏id等于游戏id
								try {
									// 返利总额
									BigDecimal rebateAmount = new BigDecimal(gameMap.get("rebateRate").toString())
											.multiply(new BigDecimal(map.get("validBet").toString()));
									// 新增返利记录
									gameRebateDao.insertRebateRecord(Long.valueOf(map.get("gameId").toString()),
											Long.valueOf(map.get("userId").toString()), rebateAmount.longValue());
								} catch (Exception e) {
									log.info("【用户返利记录添加失败】 用户ID {} ,游戏ID {} ,返利金额 {}", map.get("userId"),
											map.get("gameId"), map.get("userId"),
											new BigDecimal(gameMap.get("rebateRate").toString())
													.multiply(new BigDecimal(map.get("validBet").toString())));
								}

							}
						}
					}

				}
			}

		}
		// 获取今日所有用户返利总额
		List<Map<Object, Object>> rebateNumList = gameRebateDao.selectRebateNum();
		if (!CollectionUtils.isEmpty(rebateNumList)) {
			for (Map<Object, Object> rebateNum : rebateNumList) {
				// 返利金额大于0的情况下，给用户加金币
				if (Long.valueOf(rebateNum.get("rebateCoin").toString()) != 0
						&& Long.valueOf(rebateNum.get("rebateCoin").toString()) != null) {
					try {
						// 为用户加金币
						gameRebateDao.updateUserCoin(Long.valueOf(rebateNum.get("rebateCoin").toString()),
								Long.valueOf(rebateNum.get("userId").toString()));
					} catch (Exception e) {
						log.info("【用户金币添加失败】 用户ID {} ,返利金额 {}", rebateNum.get("userId"), rebateNum.get("rebateCoin"));
					}
					try {
						// 获取用户新增后金币余额
						Map<Object, Object> userMap = gameRebateDao
								.selectUserForUserId(Long.valueOf(rebateNum.get("userId").toString()));
						// 为用户新增交易记录
						gameRebateDao.insertUserTransactionRecord(Long.valueOf(rebateNum.get("userId").toString()),
								userMap.get("account").toString(), Constant.TransactionType.REBATE.getValue(),
								System.nanoTime() + InviteCode.create(),
								new BigDecimal(rebateNum.get("rebateCoin").toString()),
								new BigDecimal(userMap.get("money").toString()),
								Long.valueOf(userMap.get("coin").toString()),
								new BigDecimal(userMap.get("commission").toString()),
								Constant.TransactionDetailType.GAMEREBATE.getValue());
					} catch (Exception e) {
						log.info("【用户交易记录添加失败】 用户ID {} ,返利金额 {}", rebateNum.get("userId"), rebateNum.get("rebateCoin"));
					}

				}
			}

		}

	}

}
