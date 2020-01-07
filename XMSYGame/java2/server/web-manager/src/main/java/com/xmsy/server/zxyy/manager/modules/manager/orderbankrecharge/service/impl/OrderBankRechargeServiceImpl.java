package com.xmsy.server.zxyy.manager.modules.manager.orderbankrecharge.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.Query;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.modules.manager.orderbankrecharge.dao.OrderBankRechargeDao;
import com.xmsy.server.zxyy.manager.modules.manager.orderbankrecharge.entity.OrderBankRechargeEntity;
import com.xmsy.server.zxyy.manager.modules.manager.orderbankrecharge.service.OrderBankRechargeService;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("rechargeBankService")
public class OrderBankRechargeServiceImpl extends ServiceImpl<OrderBankRechargeDao, OrderBankRechargeEntity>
		implements OrderBankRechargeService {

	@Autowired
	private UserService userService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String name = (String) params.get("name");
		Page<OrderBankRechargeEntity> page = this.selectPage(new Query<OrderBankRechargeEntity>(params).getPage(),
				new EntityWrapper<OrderBankRechargeEntity>().like("name", name).orderBy("status", false)
						.orderBy("create_time", false));
		return new PageUtils(page);
	}

	/**
	 * 确认订单
	 */
	@Override
	public void confirmedById(Long id) {

		// 通过id获取该条记录详情，并验证该条记录是否是待确认状态，防止出现多次充值
		OrderBankRechargeEntity entity = baseMapper.selectById(id);
		log.info("[confirmedById] entity  {}", entity);
		if (entity == null) {// 找不到记录
			throw new RRException(ErrorCode.OrderErrorCode.RECHARGE_RECORD_ISNULL.getErrMsg(),
					ErrorCode.OrderErrorCode.RECHARGE_RECORD_ISNULL.getCode());
		}

		// 通过用户ID获取人员信息
		Long userId = entity.getUserId();
		UserEntity user = userService.selectById(userId);
		log.info("[confirmedById] user  {}", user);

		// 判断该人员是否是试玩账号，试玩账号不允许操作
		if (SysConstant.TRIAL.equals(user.getUserType())) {
			log.info("[confirmedById] 是否是试玩账号  {}", SysConstant.TRIAL.equals(user.getUserType()));
			throw new RRException(ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getErrMsg(), ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getCode());
		}

		// 通过ID修改充值记录状态
		OrderBankRechargeEntity orderBankRechargeEntity = new OrderBankRechargeEntity();
		orderBankRechargeEntity.setRechargeDate(new Date());
		log.info("[confirmedById] rechargeBankEntity  {}", orderBankRechargeEntity);
		baseMapper.updateById(orderBankRechargeEntity);

	}

}
