package com.xmsy.server.zxyy.manager.modules.manager.paychannelconfig.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.modules.manager.hierarchypaychannelconfigrelationship.entity.HierarchyPaychannelconfigRelationshipEntity;
import com.xmsy.server.zxyy.manager.modules.manager.hierarchypaychannelconfigrelationship.service.HierarchyPaychannelconfigRelationshipService;
import com.xmsy.server.zxyy.manager.modules.manager.paychannelconfig.entity.PayChannelConfigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.paychannelconfig.service.PayChannelConfigService;
import com.xmsy.server.zxyy.manager.modules.manager.payconfig.entity.PayConfigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.payconfig.service.PayConfigService;
import com.xmsy.server.zxyy.manager.modules.manager.rechargeamount.service.RechargeAmountService;
import com.xmsy.server.zxyy.manager.modules.manager.rechargechannel.entity.RechargeChannelEntity;
import com.xmsy.server.zxyy.manager.modules.manager.rechargechannel.service.RechargeChannelService;
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
 * @date 2019-04-10 14:20:07
 */
@RestController
@RequestMapping("paychannelconfig/paychannelconfig")
public class PayChannelConfigController {
    @Autowired
    private PayChannelConfigService payChannelConfigService;
    @Autowired
    SysConfigService sysConfigService;
    @Autowired
    RechargeAmountService rechargeAmountService;
    @Autowired
    RechargeChannelService rechargeChannelService;
    @Autowired
    HierarchyPaychannelconfigRelationshipService hierarchyPaychannelconfigRelationshipService;
    @Autowired
    private PayConfigService payConfigService;
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
        if (org.apache.shiro.util.CollectionUtils.isEmpty(children)) {
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
        if (!org.apache.shiro.util.CollectionUtils.isEmpty(result)) {
            //System.out.println(result);
            mqClient.sysConfigPush(result.get(0));
        }
    }
    /**
     * 支付渠道列表
     */
    @RequestMapping("/channelList")
    @RequiresPermissions("paychannelconfig:paychannelconfig:list")
    public R channelList(PayChannelConfigEntity paychannelconfig, PageParam pageParam) {

        /*String alias = paychannelconfig.getAlias();
        HashMap<String, Object> map = new HashMap<>();
        map.put("alias", alias);
        List<RechargeChannelEntity> list = rechargeChannelService.selectByMap(map);
        if (StringUtils.isNotBlank(alias)&&list.size() > 0) {
            RechargeChannelEntity channelEntity = list.get(0);
            paychannelconfig.setChannelId(channelEntity.getId());
        }*/
        Page<PayChannelConfigEntity> result = payChannelConfigService.getPayChannelList(paychannelconfig, pageParam);
        return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
                result.getCurrent(), result.getPages()));
    }

    /**
     * 分页
     */
    @RequestMapping("/list")
    @RequiresPermissions("paychannelconfig:paychannelconfig:list")
    public R page(PayChannelConfigEntity paychannelconfig, PageParam pageParam) {
        Page<PayChannelConfigEntity> result = payChannelConfigService.getPayChannelConfigs(paychannelconfig, pageParam);
        return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
                result.getCurrent(), result.getPages()));
    }

    /**
     * 列表
     */
    @RequestMapping("/Paychannelconfigs")
    @RequiresPermissions("paychannelconfig:paychannelconfig:list")
    public R list(PayChannelConfigEntity paychannelconfig, PageParam pageParam) {
        Wrapper<PayChannelConfigEntity> entityWrapper = new EntityWrapper<PayChannelConfigEntity>(paychannelconfig);
        paychannelconfig.selectList(entityWrapper);
        return R.ok().put("list", paychannelconfig.selectList(entityWrapper));
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("paychannelconfig:paychannelconfig:info")
    public R info(@PathVariable("id") Long id) {
        PayChannelConfigEntity payChannelConfig = payChannelConfigService.selectById(id);
        return R.ok().put("data", payChannelConfig);
    }

    /**
     * 信息
     */
    @RequestMapping("/getByPayIdAndChannelId")
    @RequiresPermissions("paychannelconfig:paychannelconfig:info")
    public R getByPayIdAndChannelId(@RequestBody PayChannelConfigEntity entity) {

        PayChannelConfigEntity payChannelConfig = payChannelConfigService.getPayChannel(entity);

        return R.ok().put("data", payChannelConfig);
    }

    /**
     * 信息
     */
    @RequestMapping("/getById/{id}")
    @RequiresPermissions("paychannelconfig:paychannelconfig:info")
    public R getById(@PathVariable("id") Long id) {

        PayChannelConfigEntity payChannelConfig = payChannelConfigService.getById(id);
        return R.ok().put("data", payChannelConfig);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("paychannelconfig:paychannelconfig:save")
    public R save(@RequestBody PayChannelConfigEntity paychannelconfig) {
        PayChannelConfigEntity param = new PayChannelConfigEntity();
        param.setChannelId(paychannelconfig.getChannelId());
        param.setPayId(paychannelconfig.getPayId());
        Wrapper<PayChannelConfigEntity> entityWrapper = new EntityWrapper<PayChannelConfigEntity>(param);
        List<PayChannelConfigEntity> list = payChannelConfigService.selectList(entityWrapper);
        if (!CollectionUtils.isEmpty(list)) {
            throw new RRException(ErrorCode.PayErrorCode.PAY_CHANNEL_REPEAT_ERROR.getErrMsg(),
                    ErrorCode.PayErrorCode.PAY_CHANNEL_REPEAT_ERROR.getCode());
        }
        payChannelConfigService.insert(paychannelconfig);
        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions("paychannelconfig:paychannelconfig:save")
    public R saveOrUpdate(@RequestBody PayChannelConfigEntity paychannelconfig) {
		/*//产品代码存储
		String productCode = paychannelconfig.getProductCode();
		Long scId = paychannelconfig.getScId();
		SysConfigEntity configEntity = new SysConfigEntity();
		configEntity.setId(scId);

		sysConfigService.updateOrSaveSysConfig(configEntity);*/


        PayConfigEntity payConfig = payConfigService.selectById(paychannelconfig.getPayId());
        String name = payConfig.getName();
        String aliasName = payConfig.getAliasName();
        SysConfigPayParam sysConfigPayParam = new SysConfigPayParam();
        sysConfigPayParam.setName(name);
        sysConfigPayParam.setAliasName(aliasName);
        //SysConfigPayParam sysConfigPayParam=new SysConfigPayParam();//返回的参数     (给与对应的id)
        //1.通过别名 查询当前支付 对应 的  系统配置实体类
        SysConfigEntity sysConfigParent = sysConfigService
                .selectOne(new EntityWrapper<SysConfigEntity>(new SysConfigEntity().setParamValue(aliasName)));
        if (sysConfigParent != null) {
            //2.通过系统配置Id去查询 对应的下级4个数据(产品编码,密钥,回调地址,支付地址)
            List<SysConfigEntity> sysConfigList = sysConfigService.selectList(
                    new EntityWrapper<SysConfigEntity>(new SysConfigEntity().setParentId(sysConfigParent.getId())));
            //3.遍历下级数据  把数据放进sysConfigPayParam
            if (sysConfigList != null) {
                sysConfigPayParam = sysConfigService.gainSysConfigPayParam(sysConfigList, sysConfigPayParam);
            }
        }

        RechargeChannelEntity rechargeChannelEntity = rechargeChannelService.selectById(paychannelconfig.getChannelId());
        String alias1 = rechargeChannelEntity.getAlias();

        String productCode = paychannelconfig.getProductCode();
        if (StringUtils.isNotBlank(alias1) && alias1.equals("alipay")) {
            sysConfigPayParam.setAlipay(productCode);
        } else if (StringUtils.isNotBlank(alias1) && alias1.equals("weixin")) {
            sysConfigPayParam.setWeixin(productCode);
        } else if (StringUtils.isNotBlank(alias1) && alias1.equals("unionpay")) {
            sysConfigPayParam.setUnionpay(productCode);
        } else if (StringUtils.isNotBlank(alias1) && alias1.equals("quickpay")) {
            sysConfigPayParam.setQuickpay(productCode);
        } else if (StringUtils.isNotBlank(alias1) && alias1.equals("qqpay")) {
            sysConfigPayParam.setQqpay(productCode);
        } else if (StringUtils.isNotBlank(alias1) && alias1.equals("jindongpay")) {
            sysConfigPayParam.setJindongpay(productCode);
        }
        sysConfigService.updateOrSaveSysConfigPayParam(sysConfigPayParam);
        //限制金额存储

        Long payChannelConfigEntityId = payChannelConfigService.updateOrSavePayChannelConfigs(paychannelconfig);
        //层级id存储
        if (paychannelconfig.getHierarchyId() != null) {
            List<Long> ids = JSONObject.parseArray(paychannelconfig.getHierarchyId(), Long.class);
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
    @RequiresPermissions("paychannelconfig:paychannelconfig:update")
    public R update(@RequestBody PayChannelConfigEntity paychannelconfig) {
        payChannelConfigService.updateById(paychannelconfig);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("paychannelconfig:paychannelconfig:delete")
    public R delete(@RequestBody Long[] ids) {
        for (Long id : ids) {
            payChannelConfigService.deleteById(id);
        }
        return R.ok();
    }

    /**
     * 启用
     */
    @SysLog("支付渠道启用")
    @RequestMapping("/enable")
    @RequiresPermissions("paychannelconfig:paychannelconfig:enable")
    public R enable(@RequestBody Long[] ids) {
        for (Long id : ids) {
            PayChannelConfigEntity payChannelConfigEntity = new PayChannelConfigEntity();
            payChannelConfigEntity.setId(id);
            payChannelConfigEntity.setEnable(SysConstant.ENABLE_TRUE);
            payChannelConfigService.updateById(payChannelConfigEntity);
        }
        return R.ok();
    }

    /**
     * 禁用
     */
    @SysLog("支付渠道禁用")
    @RequestMapping("/prohibit")
    @RequiresPermissions("paychannelconfig:paychannelconfig:enable")
    public R prohibit(@RequestBody Long[] ids) {
        for (Long id : ids) {
            PayChannelConfigEntity payChannelConfigEntity = new PayChannelConfigEntity();
            payChannelConfigEntity.setId(id);
            payChannelConfigEntity.setEnable(SysConstant.ENABLE_FALSE);
            payChannelConfigService.updateById(payChannelConfigEntity);
        }
        return R.ok();
    }
}
