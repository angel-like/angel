package com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationrecord.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationrecord.entity.UserRecommendationRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationrecord.service.UserRecommendationRecordService;



/**
 * 用户推荐记录表
 *
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 19:15:51
 */
@RestController
@RequestMapping("userrecommendation/userrecommendation")
public class UserRecommendationRecordController {
	@Autowired
	private UserRecommendationRecordService userRecommendationRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("userrecommendation:userrecommendation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userRecommendationRecordService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("userrecommendation:userrecommendation:info")
    public R info(@PathVariable("id") Long id){
			UserRecommendationRecordEntity userRecommendation = userRecommendationRecordService.selectById(id);
        return R.ok().put("userRecommendation", userRecommendation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("userrecommendation:userrecommendation:save")
    public R save(@RequestBody UserRecommendationRecordEntity userRecommendation){
			userRecommendationRecordService.insert(userRecommendation);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("userrecommendation:userrecommendation:update")
    public R update(@RequestBody UserRecommendationRecordEntity userRecommendation){
			userRecommendationRecordService.updateById(userRecommendation);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("userrecommendation:userrecommendation:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			userRecommendationRecordService.deleteById(id);
	}
        return R.ok();
    }

}
