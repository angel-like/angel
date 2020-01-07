package com.xmsy.server.zxyy.manager.modules.manager.sysconfig.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.xmsy.common.bean.message.SysConfigMessage;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.sysconfig.entity.SysConfigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysconfig.entity.SysConfigPayParam;
import com.xmsy.server.zxyy.manager.modules.manager.sysconfig.service.SysConfigService;
import com.xmsy.server.zxyy.manager.mq.MqClient;
import com.xmsy.server.zxyy.manager.utils.BeanConversionTools;
import com.xmsy.server.zxyy.manager.utils.TreeBuilder;

/**
 * 系统配置表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-05-21 15:26:43
 */
@RestController
@RequestMapping("sysconfig/sysconfig")
public class SysConfigController {

	@Autowired
	private MqClient mqClient;
	@Autowired
	private SysConfigService sysConfigService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sysconfig:sysconfig:list")
	public R list(SysConfigEntity sysconfig, PageParam pageParam) {
		Page<SysConfigEntity> result = new Page<SysConfigEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<SysConfigEntity> entityWrapper = new EntityWrapper<SysConfigEntity>(sysconfig);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		sysconfig.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sysconfig:sysconfig:info")
	public R info(@PathVariable("id") Long id) {
		SysConfigEntity sysConfig = sysConfigService.selectById(id);
		return R.ok().put("sysconfig", sysConfig);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sysconfig:sysconfig:save")
	public R save(@RequestBody SysConfigEntity sysconfig) {
		if (!sysConfigService.insert(sysconfig)) {
			return R.error();
		}
		pushSysConfigChange(sysconfig.getParentId());
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sysconfig:sysconfig:update")
	public R update(@RequestBody SysConfigEntity sysconfig) {
		if (!sysConfigService.updateById(sysconfig)) {
			return R.error();
		}
		pushSysConfigChange(sysconfig.getParentId());
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sysconfig:sysconfig:delete")
	public R delete(@RequestBody Long[] ids) {
		SysConfigEntity sysconfig = null;
		for (Long id : ids) {
			sysconfig = sysConfigService.selectById(id);
			if (sysConfigService.deleteById(id)) {
				pushSysConfigChange(sysconfig.getParentId());
			}
		}
		return R.ok();
	}

	/**
	 * 下拉
	 */
	@RequestMapping("/select")
	// @RequiresPermissions("sysconfig:sysconfig:select")
	public R select() {
		List<SysConfigEntity> list = sysConfigService
				.selectList(new EntityWrapper<SysConfigEntity>(new SysConfigEntity()));
		SysConfigEntity entity = new SysConfigEntity();
		entity.setId(0L);
		entity.setParamKey("系统管理");
		list.add(entity);
		return R.ok().put("select", list);
	}

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
	
	//========================================支付渠道管理页面调用  系统配置里的参数===============================
	/**
	 * 查看系统配置参数
	 */
	@RequestMapping("/sysConfigParam/{aliasName}")
	@RequiresPermissions("payconfig:payconfig:list")//能看支付渠道配置的权限就能看这个
	public R sysConfigParam(@PathVariable("aliasName") String aliasName) {
		SysConfigPayParam sysConfigPayParam=new SysConfigPayParam();//返回的参数     (给与对应的id)
		//1.通过别名 查询当前支付 对应 的  系统配置实体类
		SysConfigEntity sysConfigParent = sysConfigService
				.selectOne(new EntityWrapper<SysConfigEntity>(new SysConfigEntity().setParamValue(aliasName)));	
		if(null==sysConfigParent) {
			return R.ok().put("sysConfigPayParam", sysConfigPayParam);
		}
		//2.通过系统配置Id去查询 对应的下级4个数据(产品编码,密钥,回调地址,支付地址)
		List<SysConfigEntity> sysConfigList = sysConfigService.selectList(
				new EntityWrapper<SysConfigEntity>(new SysConfigEntity().setParentId(sysConfigParent.getId())));
		//3.遍历下级数据  把数据放进sysConfigPayParam
		if(sysConfigList!=null) {
			sysConfigPayParam = sysConfigService.gainSysConfigPayParam(sysConfigList, sysConfigPayParam);
		}
		return R.ok().put("sysConfigPayParam", sysConfigPayParam);
	}
	
	/**
	 * 批量修改或新增支付配置的参数
	 */
	@RequestMapping("/updateOrSaveSysConfigParam")
	@RequiresPermissions("payconfig:payconfig:update")
	public R updateSysConfigPayParam(@RequestBody SysConfigPayParam sysConfigPayParam) {
		//有id就去更新，没有id就去插入   -插入时 别名   公司名称也需要传过来
		sysConfigService.updateOrSaveSysConfigPayParam(sysConfigPayParam);
//2.推送修改的支付信息
		//通过别名 查询当前支付 对应 的 系统配置实体类
		SysConfigEntity sysConfig = sysConfigService.selectOne(new EntityWrapper<SysConfigEntity>(
				new SysConfigEntity().setParamValue(sysConfigPayParam.getAliasName())));
		pushSysConfigChange(sysConfig.getParentId());
		return R.ok();
	}
	
}
