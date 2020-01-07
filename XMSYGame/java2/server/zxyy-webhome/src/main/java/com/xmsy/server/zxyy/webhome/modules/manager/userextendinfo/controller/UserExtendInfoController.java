package com.xmsy.server.zxyy.webhome.modules.manager.userextendinfo.controller;

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
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.userextendinfo.entity.UserExtendInfoEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userextendinfo.service.UserExtendInfoService;



/**
 * 微信信息表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-05-20 16:18:56
 */
@RestController
@RequestMapping("userwechatinfo/userwechatinfo")
public class UserExtendInfoController {
    @Autowired
    private UserExtendInfoService userWechatInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("userwechatinfo:userwechatinfo:list")
    public R list(UserExtendInfoEntity userwechatinfo, PageParam pageParam){
        Page<UserExtendInfoEntity> result = new Page<UserExtendInfoEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<UserExtendInfoEntity> entityWrapper = new EntityWrapper<UserExtendInfoEntity>(userwechatinfo);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		userwechatinfo.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("userwechatinfo:userwechatinfo:info")
    public R info(@PathVariable("id") Long id){
			UserExtendInfoEntity userWechatInfo = userWechatInfoService.selectById(id);
        return R.ok().put("userwechatinfo", userWechatInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("userwechatinfo:userwechatinfo:save")
    public R save(@RequestBody UserExtendInfoEntity userwechatinfo){
			userWechatInfoService.insert(userwechatinfo);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("userwechatinfo:userwechatinfo:update")
    public R update(@RequestBody UserExtendInfoEntity userwechatinfo){
			userWechatInfoService.updateById(userwechatinfo);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("userwechatinfo:userwechatinfo:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			userWechatInfoService.deleteById(id);
	}
        return R.ok();
    }

}
