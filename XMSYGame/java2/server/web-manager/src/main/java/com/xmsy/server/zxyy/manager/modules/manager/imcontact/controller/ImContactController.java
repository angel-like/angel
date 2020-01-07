package com.xmsy.server.zxyy.manager.modules.manager.imcontact.controller;

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
import com.xmsy.server.zxyy.manager.modules.manager.imcontact.entity.ImContactEntity;
import com.xmsy.server.zxyy.manager.modules.manager.imcontact.service.ImContactService;



/**
 * 33联系方式
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-07-01 14:20:14
 */
@RestController
@RequestMapping("imcontact/imcontact")
public class ImContactController {
    @Autowired
    private ImContactService imContactService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("imcontact:imcontact:list")
    public R list(ImContactEntity imcontact, PageParam pageParam){
        Page<ImContactEntity> result = new Page<ImContactEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<ImContactEntity> entityWrapper = new EntityWrapper<ImContactEntity>(imcontact).orderBy("sort");
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		imcontact.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("imcontact:imcontact:info")
    public R info(@PathVariable("id") Long id){
			ImContactEntity imContact = imContactService.selectById(id);
        return R.ok().put("imcontact", imContact);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("imcontact:imcontact:save")
    public R save(@RequestBody ImContactEntity imcontact){
			imContactService.insert(imcontact);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("imcontact:imcontact:update")
    public R update(@RequestBody ImContactEntity imcontact){
			imContactService.updateById(imcontact);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("imcontact:imcontact:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			imContactService.deleteById(id);
	}
        return R.ok();
    }

}
