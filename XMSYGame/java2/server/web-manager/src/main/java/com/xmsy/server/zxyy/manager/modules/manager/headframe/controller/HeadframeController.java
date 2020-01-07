package com.xmsy.server.zxyy.manager.modules.manager.headframe.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.Constant;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.headframe.entity.HeadframeEntity;
import com.xmsy.server.zxyy.manager.modules.manager.headframe.service.HeadframeService;
import com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.entity.SysDictionaryEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.service.SysDictionaryService;
import com.xmsy.server.zxyy.manager.modules.manager.uservip.entity.UserVipEntity;
import com.xmsy.server.zxyy.manager.modules.manager.uservip.service.UserVipService;



/**
 * 头像框表
 *
 * @author adu
 * @email xxxxx
 * @date 2019-06-18 17:20:02
 */
@RestController
@RequestMapping("headframe/headframe")
public class HeadframeController {
    @Autowired
    private HeadframeService headframeService;
    @Autowired
    private UserVipService userVipService;
    @Autowired
    private SysDictionaryService sysDictionaryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("headframe:headframe:list")
    public R list(HeadframeEntity headframe, PageParam pageParam){
        Page<HeadframeEntity> result = new Page<HeadframeEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<HeadframeEntity> entityWrapper = new EntityWrapper<HeadframeEntity>(headframe);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		headframeService.selectPage(result, entityWrapper);
		if(!CollectionUtils.isEmpty(result.getRecords())) {
			for(HeadframeEntity headframeEntity:result.getRecords()) {
				SysDictionaryEntity dictionaryEntity=sysDictionaryService.
						selectOne(new EntityWrapper<SysDictionaryEntity>(null).eq("parent_code", "headframeType")
								.eq("code", headframeEntity.getType()));
				if(dictionaryEntity!=null && dictionaryEntity.getName()!=null) {
					headframeEntity.setTypeName(dictionaryEntity.getName());
				}
				
				if(Constant.HeadFrameType.VIP.getValue().equals(headframeEntity.getType())) {
					if(headframeEntity.getTypeId()==0) {
						headframeEntity.setTypeIdName("VIP0");
						continue;
					}
					UserVipEntity userVip=userVipService.selectById(headframeEntity.getTypeId());	
					if(userVip!=null && userVip.getName()!=null) {
						headframeEntity.setTypeIdName(userVip.getName());
					}
				}
			}
		}
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("headframe:headframe:info")
    public R info(@PathVariable("id") Long id){
			HeadframeEntity headframe = headframeService.selectById(id);
        return R.ok().put("headframe", headframe);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("headframe:headframe:save")
    public R save(@RequestBody HeadframeEntity headframe){
			headframeService.insert(headframe);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("headframe:headframe:update")
    public R update(@RequestBody HeadframeEntity headframe){
			headframeService.updateById(headframe);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("headframe:headframe:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			headframeService.deleteById(id);
	}
        return R.ok();
    }
    
    @RequestMapping("/typeIdList/{type}")
    public R getTypeIdList(@PathVariable("type") String type){
    	List<Map<String, Object>> list = new ArrayList<>();
		if(Constant.HeadFrameType.VIP.getValue().equals(type)) {
			list=userVipService.findUserVipForSelect();	
		}
        return R.ok().put("list", list);
    }


}
