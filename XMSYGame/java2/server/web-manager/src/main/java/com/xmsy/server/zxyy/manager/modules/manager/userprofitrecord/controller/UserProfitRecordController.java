package com.xmsy.server.zxyy.manager.modules.manager.userprofitrecord.controller;


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
import com.xmsy.server.zxyy.manager.modules.manager.userprofitrecord.entity.UserProfitRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userprofitrecord.service.UserProfitRecordService;



/**
 * 用户账户金额收益记录表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-06-19 17:48:37
 */
@RestController
@RequestMapping("userprofitrecord/userprofitrecord")
public class UserProfitRecordController {
    @Autowired
    private UserProfitRecordService userProfitRecordService;
    /**
     * 列表
     */
    @RequestMapping("/getList")
    @RequiresPermissions("userprofitrecord:userprofitrecord:list")
    public R getList(UserProfitRecordEntity userprofitrecord, PageParam pageParam){

        /*String alias = paychannelconfig.getAlias();
        HashMap<String, Object> map = new HashMap<>();
        map.put("alias", alias);
        List<RechargeChannelEntity> list = rechargeChannelService.selectByMap(map);
        if (StringUtils.isNotBlank(alias)&&list.size() > 0) {
            RechargeChannelEntity channelEntity = list.get(0);
            paychannelconfig.setChannelId(channelEntity.getId());
        }*/
        Page<UserProfitRecordEntity> result = userProfitRecordService.getList(userprofitrecord, pageParam);
        return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
                result.getCurrent(), result.getPages()));
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("userprofitrecord:userprofitrecord:list")
    public R list(UserProfitRecordEntity userprofitrecord, PageParam pageParam){
        Page<UserProfitRecordEntity> result = new Page<UserProfitRecordEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<UserProfitRecordEntity> entityWrapper = new EntityWrapper<UserProfitRecordEntity>(userprofitrecord);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		userprofitrecord.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("userprofitrecord:userprofitrecord:info")
    public R info(@PathVariable("id") Long id){
			UserProfitRecordEntity userProfitRecord = userProfitRecordService.selectById(id);
        return R.ok().put("userprofitrecord", userProfitRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("userprofitrecord:userprofitrecord:save")
    public R save(@RequestBody UserProfitRecordEntity userprofitrecord){
			userProfitRecordService.insert(userprofitrecord);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("userprofitrecord:userprofitrecord:update")
    public R update(@RequestBody UserProfitRecordEntity userprofitrecord){
			userProfitRecordService.updateById(userprofitrecord);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("userprofitrecord:userprofitrecord:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			userProfitRecordService.deleteById(id);
	}
        return R.ok();
    }

}
