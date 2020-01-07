package com.xmsy.server.zxyy.manager.modules.manager.fortuneactivitymanager.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.DateUtils;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.fortuneactivitymanager.entity.FortuneActiviConfigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.fortuneactivitymanager.service.FortuneActiviConfigService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
 * 天降财神活动配置
 *
 * @author ahui
 * @email sunlightcs@gmail.com
 * @date 2019-12-12 11:38:04
 */
@RestController
@RequestMapping("fortuneactiviconfig/fortuneactiviconfig")
public class FortuneActiviConfigController {
    @Autowired
    private FortuneActiviConfigService fortuneActiviConfigService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("fortuneactiviconfig:fortuneactiviconfig:list")
    public R list(FortuneActiviConfigEntity fortuneActiviConfigEntity, PageParam pageParam) {
        Page<FortuneActiviConfigEntity> result = new Page<FortuneActiviConfigEntity>(pageParam.getPage(), pageParam.getLimit());
        Wrapper<FortuneActiviConfigEntity> entityWrapper = new EntityWrapper<FortuneActiviConfigEntity>(fortuneActiviConfigEntity);
        entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
        fortuneActiviConfigEntity.selectPage(result, entityWrapper);
        return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
                result.getCurrent(), result.getPages()));
    }

    /**
     * 根据开始时间查询
     */
    @RequestMapping("/checkTime/{startTime}/{id}/{t}")
    @RequiresPermissions("fortuneactiviconfig:fortuneactiviconfig:save")
    public R checkTime(@PathVariable("startTime") String startTime, @PathVariable("id") Long id, @PathVariable("t") String t) {


        Wrapper<FortuneActiviConfigEntity> entityWrapper = new EntityWrapper<FortuneActiviConfigEntity>();
        entityWrapper.eq("delete_status", 0);
        if (id > 0) {
            entityWrapper.ne("id", id);
        }
        if (t.equals("0")) {
            //开始时间
            entityWrapper.le("start_time", startTime).gt("end_time", startTime);
        }
        if (t.equals("1")) {
            //开始时间
            entityWrapper.lt("start_time", startTime).ge("end_time", startTime);

        }

        FortuneActiviConfigEntity one = fortuneActiviConfigService.selectOne(entityWrapper);
        if (t.equals("1")) {
            //开始时间
            Wrapper<FortuneActiviConfigEntity> entityWrapper1 = new EntityWrapper<FortuneActiviConfigEntity>();
            entityWrapper1.eq("delete_status", 0);
            if (id > 0) {
                entityWrapper1.ne("id", id);
            }
            entityWrapper1.lt("start_time", startTime).le("end_time", startTime);
            if (one == null) {
                one = fortuneActiviConfigService.selectOne(entityWrapper1);
            }

        }
        boolean after = true;
        if (one != null && one.getId() > 0) {
            if (id > 0) {
                after = id == one.getId() ? true : false;
            } else {
                after = false;
            }
        }
        return R.ok().put("after", after);

    }

    @RequestMapping("/checkEndTime/{startTime}/{endTime}/{id}")
    @RequiresPermissions("fortuneactiviconfig:fortuneactiviconfig:save")
    public R checkEndTime(@PathVariable("startTime") String startTime, @PathVariable("endTime") String endTime, @PathVariable("id") Long id) {


        Wrapper<FortuneActiviConfigEntity> entityWrapper = new EntityWrapper<FortuneActiviConfigEntity>();
        entityWrapper.eq("delete_status", 0);
        if (id > 0) {
            entityWrapper.ne("id", id);
        }
        //开始时间
        entityWrapper.lt("start_time", endTime).ge("end_time", endTime);
        FortuneActiviConfigEntity one = fortuneActiviConfigService.selectOne(entityWrapper);

        //开始时间
        Wrapper<FortuneActiviConfigEntity> entityWrapper1 = new EntityWrapper<FortuneActiviConfigEntity>();
        entityWrapper1.eq("delete_status", 0);
        if (id > 0) {
            entityWrapper1.ne("id", id);
        }
        entityWrapper1.lt("start_time", endTime).le("end_time", endTime);
        if (one == null) {
            one = fortuneActiviConfigService.selectOne(entityWrapper1);
            if (one != null) {
                if (StringUtils.isNotBlank(startTime)) {
                    Date endTime1 = one.getEndTime();
                    Date date = DateUtils.stringToDate(startTime, DateUtils.DATE_TIME_PATTERN);
                    int i = date.compareTo(endTime1);
                    if (date.compareTo(endTime1) > -1) {
                        one = null;
                    }
                }else{
                    one = null;
                }
            }
        }
        boolean after = true;
        if (one != null && one.getId() > 0) {
            if (id > 0) {
                after = id == one.getId() ? true : false;
            } else {
                after = false;
            }
        }
        return R.ok().put("after", after);

    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("fortuneactiviconfig:fortuneactiviconfig:info")
    public R info(@PathVariable("id") Long id) {
        FortuneActiviConfigEntity fortuneActiviConfig = fortuneActiviConfigService.selectById(id);

        return R.ok().put("fortuneActiviConfig", fortuneActiviConfig);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("fortuneactiviconfig:fortuneactiviconfig:save")
    public R save(@RequestBody FortuneActiviConfigEntity fortuneActiviConfig) {

        fortuneActiviConfigService.insert(fortuneActiviConfig);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("fortuneactiviconfig:fortuneactiviconfig:update")
    public R update(@RequestBody FortuneActiviConfigEntity fortuneActiviConfig) {
        fortuneActiviConfigService.updateById(fortuneActiviConfig);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("fortuneactiviconfig:fortuneactiviconfig:delete")
    public R delete(@RequestBody Long[] ids) {
       /* List<Long> idList = Arrays.asList(ids);
        fortuneActiviConfigService.deleteBatchIds(idList);*/
        for (Long id : ids) {
            fortuneActiviConfigService.deleteById(id);
        }
        return R.ok();
    }

}
