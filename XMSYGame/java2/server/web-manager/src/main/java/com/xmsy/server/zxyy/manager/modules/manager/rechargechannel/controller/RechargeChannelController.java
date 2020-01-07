package com.xmsy.server.zxyy.manager.modules.manager.rechargechannel.controller;

import java.util.List;

import com.xmsy.server.zxyy.manager.modules.manager.rechargechannel.entity.RechargeChannelResultEntity;
import com.xmsy.server.zxyy.manager.modules.manager.rechargechannel.entity.RechargeParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.modules.manager.rechargechannel.entity.RechargeChannelEntity;
import com.xmsy.server.zxyy.manager.modules.manager.rechargechannel.service.RechargeChannelService;

/**
 * 支付渠道
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-03-27 10:16:53
 */
@RestController
@RequestMapping("rechargechannel/rechargechannel")
public class RechargeChannelController {
    @Autowired
    private RechargeChannelService rechargeChannelService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("rechargechannel:rechargechannel:list")
    public R list(RechargeChannelEntity rechargechannel, PageParam pageParam) {
        Page<RechargeChannelEntity> result = new Page<RechargeChannelEntity>(pageParam.getPage(), pageParam.getLimit());
        Wrapper<RechargeChannelEntity> entityWrapper = new EntityWrapper<RechargeChannelEntity>(rechargechannel);
        entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
        entityWrapper.orderBy("order_no", true);
        rechargechannel.selectPage(result, entityWrapper);

        return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
                result.getCurrent(), result.getPages()));
    }

    /**
     * 列表
     */
    @RequestMapping("/Rechargechannels")
    @RequiresPermissions("rechargechannel:rechargechannel:list")
    public R rechargechannels(RechargeChannelEntity rechargechannel, PageParam pageParam) {
        Wrapper<RechargeChannelEntity> entityWrapper = new EntityWrapper<RechargeChannelEntity>(rechargechannel);
        return R.ok().put("list", rechargechannel.selectList(entityWrapper));
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("rechargechannel:rechargechannel:info")
    public R info(@PathVariable("id") Long id) {
        RechargeChannelEntity rechargeChannel = rechargeChannelService.selectById(id);
        return R.ok().put("rechargechannel", rechargeChannel);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("rechargechannel:rechargechannel:save")
    public R save(@RequestBody RechargeChannelEntity rechargechannel) {
        rechargeChannelService.insert(rechargechannel);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("rechargechannel:rechargechannel:update")
    public R update(@RequestBody RechargeChannelEntity rechargechannel) {
        rechargeChannelService.updateById(rechargechannel);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("rechargechannel:rechargechannel:delete")
    public R delete(@RequestBody Long[] ids) {
        for (Long id : ids) {
            rechargeChannelService.deleteById(id);
        }
        return R.ok();
    }

    /**
     * 启用
     */
    @RequestMapping("/enable/{id}")
    @RequiresPermissions("rechargechannel:rechargechannel:enable")
    public R enable(@PathVariable("id") Long id) {
        RechargeChannelEntity rechargeChannel = new RechargeChannelEntity();
        rechargeChannel.setId(id);
        rechargeChannel.setEnable(SysConstant.ENABLE_TRUE);
        rechargeChannelService.updateById(rechargeChannel);
        return R.ok();
    }

    /**
     * 禁用
     */
    @RequestMapping("/disable/{id}")
    @RequiresPermissions("rechargechannel:rechargechannel:disable")
    public R disable(@PathVariable("id") Long id) {
        RechargeChannelEntity rechargeChannel = new RechargeChannelEntity();
        rechargeChannel.setId(id);
        rechargeChannel.setEnable(SysConstant.ENABLE_FALSE);
        rechargeChannelService.updateById(rechargeChannel);
        return R.ok();
    }

    /**
     * 下拉
     */
    @RequestMapping("/select")
    public R select() {
        List<RechargeChannelEntity> list = rechargeChannelService.selectList(new EntityWrapper<RechargeChannelEntity>(
                new RechargeChannelEntity().setEnable(SysConstant.ENABLE_TRUE)));
        return R.ok().put("list", list);
    }
    @RequestMapping("/selects")
    public R selects() {
        List<RechargeChannelEntity> list = rechargeChannelService.selectList(new EntityWrapper<RechargeChannelEntity>(
                new RechargeChannelEntity()));
        return R.ok().put("list", list);
    }
    /**
     * 下拉
     */
    @RequestMapping("/selectByPayId/{id}")
    public R selectByPayId(@PathVariable("id") Long id) {
        List<RechargeChannelResultEntity> list = rechargeChannelService.selectChannelsByPayId(id);
        System.out.println("初始化数据:"+list);
        return R.ok().put("list", list);
    }

    /**
     * 下拉
     */
    @RequestMapping("/selectByPayIdAndChannelId")
    public R selectByPayIdAndChannelId(@RequestBody RechargeParam rechargeParam) {
        Long channelId = rechargeParam.getChannelId();
        Long id = rechargeParam.getPayId();

        RechargeChannelResultEntity rechargechannel = rechargeChannelService.selectByPayIdAndChannelId(id, channelId);
        System.out.println("选中后数据:"+rechargechannel);
        return R.ok().put("rechargechannel", rechargechannel);
    }
}
