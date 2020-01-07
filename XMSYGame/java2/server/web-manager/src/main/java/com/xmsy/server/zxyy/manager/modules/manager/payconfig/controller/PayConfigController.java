package com.xmsy.server.zxyy.manager.modules.manager.payconfig.controller;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.xmsy.common.bean.message.SysConfigMessage;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.modules.manager.hierarchypaychannelconfigrelationship.entity.HierarchyPaychannelconfigRelationshipEntity;
import com.xmsy.server.zxyy.manager.modules.manager.hierarchypaychannelconfigrelationship.service.HierarchyPaychannelconfigRelationshipService;
import com.xmsy.server.zxyy.manager.modules.manager.paychannelconfig.entity.PayChannelConfigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.paychannelconfig.service.PayChannelConfigService;
import com.xmsy.server.zxyy.manager.modules.manager.payconfig.entity.PayConfigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.payconfig.entity.PayConfigStr;
import com.xmsy.server.zxyy.manager.modules.manager.payconfig.service.PayConfigService;
import com.xmsy.server.zxyy.manager.modules.manager.rechargeamount.service.RechargeAmountService;
import com.xmsy.server.zxyy.manager.modules.manager.sysconfig.entity.SysConfigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysconfig.entity.SysConfigPayParam;
import com.xmsy.server.zxyy.manager.modules.manager.sysconfig.service.SysConfigService;
import com.xmsy.server.zxyy.manager.mq.MqClient;
import com.xmsy.server.zxyy.manager.utils.BeanConversionTools;
import com.xmsy.server.zxyy.manager.utils.TreeBuilder;

/**
 * 支付渠道配置
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-03-25 10:09:06
 */
@Slf4j
@RestController
@RequestMapping("payconfig/payconfig")
public class PayConfigController {
	@Autowired
	private PayConfigService payConfigService;
	@Autowired
	SysConfigService  sysConfigService;
	@Autowired
	PayChannelConfigService payChannelConfigService;
	@Autowired
	RechargeAmountService rechargeAmountService;
	@Autowired
	HierarchyPaychannelconfigRelationshipService hierarchyPaychannelconfigRelationshipService;
	@Autowired
	private MqClient mqClient;

	/**
	 * .遍历系统配置节点，找出配置所在的树
	 *
	 * @param entity
	 * @param modifyId
	 * @return
	 */
	private SysConfigMessage checkChildNodes(SysConfigMessage entity, Long modifyId) {
		if (null == modifyId) {
			return null;
		}
		SysConfigMessage result = null;
		List<SysConfigMessage> children = entity.getChildren();
		if (CollectionUtils.isEmpty(children)) {
			return null;
		}
		for (SysConfigMessage child : children) {
			if (modifyId.equals(child.getId())) {
				return child;
			}
			result = checkChildNodes(child, modifyId);
			if (null != result) {
				return result;
			}
		}
		return null;
	}

	private void pushSysConfigChange(Long modifyId) {
		List<SysConfigEntity> sysConfigs = sysConfigService.selectList(new EntityWrapper<SysConfigEntity>(null));
		List<SysConfigMessage> SysConfigMessageList = BeanConversionTools.commonConfigConversion(sysConfigs);
		List<SysConfigMessage> SysConfigMessageTree = new TreeBuilder(SysConfigMessageList).buildTree();
		List<SysConfigMessage> result = Lists.newArrayList();
		for (SysConfigMessage tempConfig : SysConfigMessageTree) {
			if (tempConfig.getId().equals(modifyId)) {
				result.add(tempConfig);
				break;
			}
			if (null != checkChildNodes(tempConfig, modifyId)) {
				result.add(tempConfig);
			}
		}
		if (!CollectionUtils.isEmpty(result)) {
			//System.out.println(result);
			mqClient.sysConfigPush(result.get(0));
		}
	}

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("payconfig:payconfig:list")
	public R list(PayConfigEntity payconfig, PageParam pageParam) {
		Page<PayConfigEntity> result = new Page<PayConfigEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<PayConfigEntity> entityWrapper = new EntityWrapper<PayConfigEntity>(payconfig).orderBy("order_num",true);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		payconfig.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 列表
	 */
	@RequestMapping("/Payconfigs")
	@RequiresPermissions("payconfig:payconfig:list")
	public R payconfigs(PayConfigEntity payconfig, PageParam pageParam) {
		Wrapper<PayConfigEntity> entityWrapper = new EntityWrapper<PayConfigEntity>(payconfig);
		return R.ok().put("list", payconfig.selectList(entityWrapper));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("payconfig:payconfig:info")
	public R info(@PathVariable("id") Long id) {
		PayConfigEntity payConfig = payConfigService.selectById(id);
		return R.ok().put("payconfig", payConfig);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("payconfig:payconfig:save")
	public R save(@RequestBody PayConfigStr payConfigStr) {

		PayConfigEntity payConfigEntity = JSONObject.parseObject(payConfigStr.getPayconfig(), PayConfigEntity.class);
	    payConfigService.insert(payConfigEntity);
		SysConfigMessage message = new SysConfigMessage();
		message.setType(1);
		message.setAliasName(payConfigEntity.getAliasName());
		message.setCallbackIp(payConfigEntity.getCallbackIp());
		mqClient.sysConfigPush(message);
		SysConfigPayParam sysConfigPayParam = JSONObject.parseObject(payConfigStr.getSysConfigPayParam(), SysConfigPayParam.class);
		sysConfigService.updateOrSaveSysConfigPayParam(sysConfigPayParam);
		//2.推送修改的支付信息
		//通过别名 查询当前支付 对应 的 系统配置实体类
		SysConfigEntity sysConfig = sysConfigService.selectOne(new EntityWrapper<SysConfigEntity>(
				new SysConfigEntity().setParamValue(sysConfigPayParam.getAliasName())));
		pushSysConfigChange(sysConfig.getParentId());
		return R.ok();
	}


	/**
	 * 保存
	 */
	@RequestMapping("/saveAll")
	@RequiresPermissions("payconfig:payconfig:save")
	public R saveAll(@RequestBody PayConfigStr payConfigStr) {
       //产品代码存储
		SysConfigPayParam sysConfigPayParam = JSONObject.parseObject(payConfigStr.getSysConfigPayParam(), SysConfigPayParam.class);
		sysConfigService.updateOrSaveSysConfigPayParam(sysConfigPayParam);

       //限制金额存储
		PayChannelConfigEntity payChannelConfigEntity = JSONObject.parseObject(payConfigStr.getPayChannelConfigEntity(), PayChannelConfigEntity.class);
		Long payChannelConfigEntityId = payChannelConfigService.updateOrSavePayChannelConfigs(payChannelConfigEntity);
		//层级id存储
		if (payConfigStr.getHierarchyId()!=null) {
			List<Long> ids = JSONObject.parseArray(payConfigStr.getHierarchyId(), Long.class);
			if (ids.size() > 0 && ids != null) {
				hierarchyPaychannelconfigRelationshipService.deleteByPaychannelconfigId(payChannelConfigEntityId);
			}
			List<HierarchyPaychannelconfigRelationshipEntity> entityList = new ArrayList<>();
			for (Long pid : ids) {
				HierarchyPaychannelconfigRelationshipEntity hhh = new HierarchyPaychannelconfigRelationshipEntity();
				hhh.setPaychannelconfigId(payChannelConfigEntityId);
				hhh.setHierarchyId(pid);
				entityList.add(hhh);

			}
			hierarchyPaychannelconfigRelationshipService.insertBatch(entityList);
		}
		//2.推送修改的支付信息
		//通过别名 查询当前支付 对应 的 系统配置实体类
		SysConfigEntity sysConfig = sysConfigService.selectOne(new EntityWrapper<SysConfigEntity>(
				new SysConfigEntity().setParamValue(sysConfigPayParam.getAliasName())));
		pushSysConfigChange(sysConfig.getParentId());
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("payconfig:payconfig:update")
	public R update(@RequestBody  PayConfigStr payConfigStr) {
		PayConfigEntity payConfigEntity = JSONObject.parseObject(payConfigStr.getPayconfig(), PayConfigEntity.class);
		PayConfigEntity configEntity = payConfigService.selectById(payConfigEntity.getId());
		String callbackIp = payConfigEntity.getCallbackIp();
		log.info("[callbackIp]->data {}",callbackIp);
		String callbackIp1 = configEntity.getCallbackIp();
		log.info("[callbackIp1]->data {}",callbackIp1);
		boolean b =true;
		if(StringUtils.isNotBlank(callbackIp)&&StringUtils.isNotBlank(callbackIp1)){
			b=!callbackIp.equals(callbackIp1);
		}else
		if(!StringUtils.isNotBlank(callbackIp)&&!StringUtils.isNotBlank(callbackIp1)){
			b=false;
		}
		payConfigService.updateById(payConfigEntity);
		if(b){
			SysConfigMessage message = new SysConfigMessage();
			message.setType(1);
			message.setAliasName(payConfigEntity.getAliasName());
			message.setCallbackIp(payConfigEntity.getCallbackIp());
			mqClient.sysConfigPush(message);
		}

		SysConfigPayParam sysConfigPayParam = JSONObject.parseObject(payConfigStr.getSysConfigPayParam(), SysConfigPayParam.class);
		sysConfigService.updateOrSaveSysConfigPayParam(sysConfigPayParam);
		//2.推送修改的支付信息
		//通过别名 查询当前支付 对应 的 系统配置实体类
		SysConfigEntity sysConfig = sysConfigService.selectOne(new EntityWrapper<SysConfigEntity>(
				new SysConfigEntity().setParamValue(sysConfigPayParam.getAliasName())));
		pushSysConfigChange(sysConfig.getParentId());
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("payconfig:payconfig:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			payConfigService.deleteById(id);
		}
		return R.ok();
	}

	/**
	 * 启用
	 */
	@RequestMapping("/enable/{id}")
	@RequiresPermissions("payconfig:payconfig:enable")
	public R enable(@PathVariable("id") Long id) {
		PayConfigEntity payConfig = new PayConfigEntity();
		payConfig.setId(id);
		payConfig.setEnable(SysConstant.ENABLE_TRUE);
		payConfigService.updateAllById(payConfig);
		return R.ok();
	}

	/**
	 * 禁用
	 */
	@RequestMapping("/disable/{id}")
	@RequiresPermissions("payconfig:payconfig:disable")
	public R disable(@PathVariable("id") Long id) {
		PayConfigEntity payConfig = new PayConfigEntity();
		payConfig.setId(id);
		payConfig.setEnable(SysConstant.ENABLE_FALSE);
		payConfigService.updateAllById(payConfig);
		return R.ok();
	}

	/**
	 * 设为首推
	 */
	@RequestMapping("/firstPush/{id}")
	@RequiresPermissions("payconfig:payconfig:firstPush")
	public R firstPush(@PathVariable("id") Long id) {
		payConfigService.firstPush(id);
		return R.ok();
	}

	/**
	 * 下拉
	 */
	@RequestMapping("/select")
	public R select() {
		List<PayConfigEntity> list = payConfigService.selectList(
				new EntityWrapper<PayConfigEntity>(new PayConfigEntity()));
		return R.ok().put("list", list);
	}

}
