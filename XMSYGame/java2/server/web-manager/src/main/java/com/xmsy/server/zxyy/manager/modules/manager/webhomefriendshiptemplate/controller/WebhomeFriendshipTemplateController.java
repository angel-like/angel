package com.xmsy.server.zxyy.manager.modules.manager.webhomefriendshiptemplate.controller;

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
import com.xmsy.server.zxyy.manager.modules.manager.webhomefriendshiptemplate.entity.WebhomeFriendshipTemplateEntity;
import com.xmsy.server.zxyy.manager.modules.manager.webhomefriendshiptemplate.service.WebhomeFriendshipTemplateService;



/**
 * 友情链接内容
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-03-13 15:31:14
 */
@RestController
@RequestMapping("webhomefriendshiptemplate/webhomefriendshiptemplate")
public class WebhomeFriendshipTemplateController {
    @Autowired
    private WebhomeFriendshipTemplateService webhomeFriendshipTemplateService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("webhomefriendshiptemplate:webhomefriendshiptemplate:list")
    public R list(WebhomeFriendshipTemplateEntity webhomefriendshiptemplate, PageParam pageParam){
        Page<WebhomeFriendshipTemplateEntity> result = new Page<WebhomeFriendshipTemplateEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<WebhomeFriendshipTemplateEntity> entityWrapper = new EntityWrapper<WebhomeFriendshipTemplateEntity>(webhomefriendshiptemplate);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		webhomefriendshiptemplate.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("webhomefriendshiptemplate:webhomefriendshiptemplate:info")
    public R info(@PathVariable("id") Long id){
			WebhomeFriendshipTemplateEntity webhomeFriendshipTemplate = webhomeFriendshipTemplateService.selectById(id);
        return R.ok().put("webhomefriendshiptemplate", webhomeFriendshipTemplate);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("webhomefriendshiptemplate:webhomefriendshiptemplate:save")
    public R save(@RequestBody WebhomeFriendshipTemplateEntity webhomefriendshiptemplate){
			webhomeFriendshipTemplateService.insert(webhomefriendshiptemplate);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("webhomefriendshiptemplate:webhomefriendshiptemplate:update")
    public R update(@RequestBody WebhomeFriendshipTemplateEntity webhomefriendshiptemplate){
			webhomeFriendshipTemplateService.updateById(webhomefriendshiptemplate);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("webhomefriendshiptemplate:webhomefriendshiptemplate:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			webhomeFriendshipTemplateService.deleteById(id);
	}
        return R.ok();
    }

}
