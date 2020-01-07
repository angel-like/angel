package com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.controller;

import java.util.List;

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
import com.xmsy.server.zxyy.webhome.common.annotation.SysLog;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.app.event.PushService;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.entity.UserHierarchyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.service.UserHierarchyService;

import lombok.extern.slf4j.Slf4j;



/**
 * 用户层级表
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-01-02 11:57:53
 */
@Slf4j
@RestController
@RequestMapping("userhierarchy/userhierarchy")
public class UserHierarchyController {
    @Autowired
    private UserHierarchyService userHierarchyService;
    @Autowired
    private PushService pushService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("userhierarchy:userhierarchy:list")
    public R list(UserHierarchyEntity userHierarchy, PageParam pageParam){
    	log.info("[list] userHierarchy {} ,pageParam {}",userHierarchy, pageParam);
    	String name=userHierarchy.getName();
    	userHierarchy.setName(null);
    	Page<UserHierarchyEntity> result = new Page<UserHierarchyEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<UserHierarchyEntity> entityWrapper = new EntityWrapper<UserHierarchyEntity>(userHierarchy).like("name", name);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		userHierarchy.selectPage(result, entityWrapper);
		log.info("[list] list {} ",result.getRecords());
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("userhierarchy:userhierarchy:info")
    public R info(@PathVariable("id") Long id){
    	log.info("[list] id {} ", id);
			UserHierarchyEntity userHierarchy = userHierarchyService.selectById(id);
			log.info("[info] userHierarchy {} ", userHierarchy);
        return R.ok().put("userHierarchy", userHierarchy);
    }

    /**
     * 保存
     */
    @SysLog("新增层级")
    @RequestMapping("/save")
    @RequiresPermissions("userhierarchy:userhierarchy:save")
    public R save(@RequestBody UserHierarchyEntity userHierarchy){
    	userHierarchy.getGameIds();
    	userHierarchyService.insertHierarchyAndPreferential(userHierarchy);
    	//推送层级胜率变更
    	pushService.pushHierarchyRateList(userHierarchyService.getHierarchyRateList());
    	log.info("[save] userHierarchy {} ", userHierarchy);
			
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改层级")
    @RequestMapping("/update")
    @RequiresPermissions("userhierarchy:userhierarchy:update")
    public R update(@RequestBody UserHierarchyEntity userHierarchy){
    	log.info("[update] userHierarchy {} ", userHierarchy);
			userHierarchyService.updateById(userHierarchy);
			//推送层级胜率变更
	    	pushService.pushHierarchyRateList(userHierarchyService.getHierarchyRateList());
        return R.ok();
    }
    
    /**
     * 设置默认
     */
    @SysLog("设置默认层级")
    @RequestMapping("/setdefault/{id}")
    @RequiresPermissions("userhierarchy:userhierarchy:setdefault")
    public R setDefault(@PathVariable("id") Long id){
    	log.info("[setDefault] id {} ", id);
			userHierarchyService.setDefault(id);
        return R.ok();
    }
    /**
     * 开启vip权限
     */
    @SysLog("开启vip权限")
    @RequestMapping("/openVipEnable/{id}")
    @RequiresPermissions("userhierarchy:userhierarchy:openVipEnable")
    public R openVipEnable(@PathVariable("id") Long id){
    	log.info("[setDefault] id {} ", id);
    	UserHierarchyEntity userHierarchy=new UserHierarchyEntity();
    	userHierarchy.setId(id);
    	userHierarchy.setVipEnable(SysConstant.ENABLE_TRUE);
			userHierarchyService.updateById(userHierarchy);
        return R.ok();
    }
    /**
     * 关闭vip权限
     */
    @SysLog("关闭vip权限")
    @RequestMapping("/closeVipEnable/{id}")
    @RequiresPermissions("userhierarchy:userhierarchy:closeVipEnable")
    public R closeVipEnable(@PathVariable("id") Long id){
    	log.info("[setDefault] id {} ", id);
    	UserHierarchyEntity userHierarchy=new UserHierarchyEntity();
    	userHierarchy.setId(id);
    	userHierarchy.setVipEnable(SysConstant.ENABLE_FALSE);
			userHierarchyService.updateById(userHierarchy);
        return R.ok();
    }


    /**
     * 删除
     */
    @SysLog("删除层级")
    @RequestMapping("/delete")
    @RequiresPermissions("userhierarchy:userhierarchy:delete")
    public R delete(@RequestBody Long[] ids){
		    for (Long id : ids) {
		    	log.info("[delete] id {} ", id);
					userHierarchyService.deleteById(id);
			}
    	//推送层级胜率变更
		pushService.pushHierarchyRateList(userHierarchyService.getHierarchyRateList());
        return R.ok();
    }
    /**
     * 层级下拉
     */
    @RequestMapping("/select")
    public R select(){
    	List<UserHierarchyEntity> list=userHierarchyService.selectList(new EntityWrapper<UserHierarchyEntity>(new UserHierarchyEntity()));
    	return R.ok().put("list", list);
    }
    
    /**
	 * 层级信息
	 */
	@RequestMapping("/getHierarchy")
	public R getHierarchy(UserHierarchyEntity userHierarchy) {
		List<UserHierarchyEntity> dataList = userHierarchyService.selectList(new EntityWrapper<UserHierarchyEntity>(userHierarchy));
		return R.ok().put("hierarchyList", dataList);
	}

}
