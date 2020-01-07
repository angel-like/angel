package com.xmsy.server.zxyy.manager.modules.manager.useranswer.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.useranswer.entity.UserAnswerEntity;
import com.xmsy.server.zxyy.manager.modules.manager.useranswer.service.UserAnswerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 用户答案表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-15 16:56:34
 */
@RestController
@RequestMapping("useranswer/useranswer")
public class UserAnswerController {
    @Autowired
    private UserAnswerService userAnswerService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("useranswer:useranswer:list")
    public R list( UserAnswerEntity entity, PageParam pageParam){
        Page<UserAnswerEntity> result = new Page<UserAnswerEntity>(pageParam.getPage(), pageParam.getLimit());
        Wrapper<UserAnswerEntity> entityWrapper = new EntityWrapper<UserAnswerEntity>(entity);
        entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
//        entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
        entity.selectPage(result, entityWrapper);
        return R.ok().put("page", result);
    }



    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:useranswer:save")
    public R save(@RequestBody UserAnswerEntity userAnswer){
	 userAnswerService.insert(userAnswer);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:useranswer:update")
    public R update(@RequestBody UserAnswerEntity userAnswer){
		userAnswerService.updateById(userAnswer);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:useranswer:delete")
    public R delete(@RequestBody Long[] ids){
		userAnswerService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
