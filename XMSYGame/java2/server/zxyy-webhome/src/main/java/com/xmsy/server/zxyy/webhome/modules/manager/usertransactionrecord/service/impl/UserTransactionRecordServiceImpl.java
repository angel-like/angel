package com.xmsy.server.zxyy.webhome.modules.manager.usertransactionrecord.service.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.Query;
import com.xmsy.server.zxyy.webhome.modules.manager.user.dao.UserDao;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.usertransactionrecord.dao.UserTransactionRecordDao;
import com.xmsy.server.zxyy.webhome.modules.manager.usertransactionrecord.entity.UserTransactionRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.usertransactionrecord.service.UserTransactionRecordService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("transactionRecordService")
public class UserTransactionRecordServiceImpl extends ServiceImpl<UserTransactionRecordDao, UserTransactionRecordEntity>
		implements UserTransactionRecordService {

	@Autowired
	private UserDao userDao;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Page<UserTransactionRecordEntity> page = this.selectPage(
				new Query<UserTransactionRecordEntity>(params).getPage(),
				new EntityWrapper<UserTransactionRecordEntity>());
		return new PageUtils(page);
	}

	// 新增交易记录
	@Override
	public void insertRecord(Long userId, String account, BigDecimal amount, Integer type, Integer detailType,
			Long businessId) {
		UserTransactionRecordEntity entity = new UserTransactionRecordEntity();
		entity.setUserId(userId);
		// // 如果是线下充值或扫码充值，查看是否是第一次充值
		// if (type.equals(SysConstant.TRANSACTION_RECHAGE) ||
		// type.equals(SysConstant.QRCODE)) {
		// List<UserTransactionRecordEntity> list = baseMapper
		// .selectList(new EntityWrapper<UserTransactionRecordEntity>(entity).in("type",
		// SysConstant.RECHARGE + "," + SysConstant.QRCODE));
		// log.info("[insertRecord] 是否是首充 {}", CollectionUtils.isEmpty(list));
		// if (CollectionUtils.isEmpty(list)) {
		// entity.setFristrecharge(1);// 标记为是首充
		// }
		// }
		// 获取人员金额变动后余额
		UserEntity user = userDao.selectById(userId);
		entity.setAmount(amount);// 交易金额
		entity.setOrderNo(businessId.toString());// 业务ID
		entity.setType(type);// 交易类型
		entity.setDetailType(detailType);// 交易具体类型
		entity.setUserAccount(account);// 交易账号
		entity.setMoney(user.getMoney());// 主账户余额
		entity.setCommission(user.getCommission());// 佣金余额
		entity.setCoin(user.getCoin());// 金币余额
		log.info("[insertRecord] 交易保存数据entity {}", entity);
		baseMapper.insert(entity);
	}

	@Override
	public Page<UserTransactionRecordEntity> getTransactionRecords(UserTransactionRecordEntity record,
			PageParam pageParam, String startTime, String endTime, String type) {
		Page<UserTransactionRecordEntity> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造																				// page 对象
		return page.setRecords(this.baseMapper.getTransactionRecords(page, record, startTime, endTime, type));
	}

}
