package com.xmsy.server.zxyy.webhome.modules.manager.kygameloginrecord.controller;

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
import com.xmsy.server.zxyy.webhome.modules.manager.kygameloginrecord.entity.KyGameloginRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.kygameloginrecord.service.KyGameloginRecordService;



/**
 * 活动分类
 *
 * @author sanqian
 * @email xxxxx
 * @date 2019-12-04 15:16:53
 */
@RestController
@RequestMapping("kygameloginrecord/kygameloginrecord")
public class KyGameloginRecordController {
    @Autowired
    private KyGameloginRecordService kyGameloginRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("kygameloginrecord:kygameloginrecord:list")
    public R list(KyGameloginRecordEntity kygameloginrecord, PageParam pageParam){
        Page<KyGameloginRecordEntity> result = new Page<KyGameloginRecordEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<KyGameloginRecordEntity> entityWrapper = new EntityWrapper<KyGameloginRecordEntity>(kygameloginrecord);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		kygameloginrecord.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("kygameloginrecord:kygameloginrecord:info")
    public R info(@PathVariable("id") Long id){
			KyGameloginRecordEntity kyGameloginRecord = kyGameloginRecordService.selectById(id);
        return R.ok().put("kygameloginrecord", kyGameloginRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("kygameloginrecord:kygameloginrecord:save")
    public R save(@RequestBody KyGameloginRecordEntity kygameloginrecord){
			kyGameloginRecordService.insert(kygameloginrecord);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("kygameloginrecord:kygameloginrecord:update")
    public R update(@RequestBody KyGameloginRecordEntity kygameloginrecord){
			kyGameloginRecordService.updateById(kygameloginrecord);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("kygameloginrecord:kygameloginrecord:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			kyGameloginRecordService.deleteById(id);
	}
        return R.ok();
    }

}
