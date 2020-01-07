package com.xmsy.server.zxyy.manager.modules.manager.userquestion.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.userquestion.entity.UserQuestionEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userquestion.service.UserQuestionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




/**
 * 用户问题表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-15 16:56:34
 */
@RestController
@RequestMapping("userquestion/userquestion")
public class UserQuestionController {
    @Autowired
    private UserQuestionService userQuestionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("userquestion:userquestion:list")
    public R list( UserQuestionEntity entity, PageParam pageParam){
        Page<UserQuestionEntity> result = new Page<UserQuestionEntity>(pageParam.getPage(), pageParam.getLimit());
        Wrapper<UserQuestionEntity> entityWrapper = new EntityWrapper<UserQuestionEntity>(entity).like(!StringUtils.isEmpty(entity.getQuestion()), "question",
                entity.getQuestion());
        entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
//        entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
        entity.selectPage(result, entityWrapper);
        return R.ok().put("page",new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
                result.getCurrent(), result.getPages()));
    }



    /**
     * 信息
     */
//    @RequestMapping("/info/{id}")
//    @RequiresPermissions("generator:userquestion:info")
//    public R info(@PathVariable("id") Long id){
//		UserQuestionEntity userQuestion = userQuestionService.getById(id);
//
//        return R.ok().put("userQuestion", userQuestion);
//    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("userquestion:userquestion:save")
    public R save(@RequestBody UserQuestionEntity userQuestion){
		userQuestionService.insert(userQuestion);
        return R.ok();
    }

//    /**
//     * 修改
//     */
//    @RequestMapping("/update")
//    @RequiresPermissions("userquestion:userquestion:update")
//    public R update(@RequestBody UserQuestionEntity userQuestion){
//		userQuestionService.updateById(userQuestion);
//
//        return R.ok();
//    }

//    /**
//     * 删除
//     */
//    @RequestMapping("/delete")
//    @RequiresPermissions("generator:userquestion:delete")
//    public R delete(@RequestBody Long[] ids){
//		userQuestionService.removeByIds(Arrays.asList(ids));
//
//        return R.ok();
//    }

}
