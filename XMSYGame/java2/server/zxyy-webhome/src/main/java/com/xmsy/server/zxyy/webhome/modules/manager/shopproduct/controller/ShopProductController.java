package com.xmsy.server.zxyy.webhome.modules.manager.shopproduct.controller;

import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.ImmutableMap;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.shopproduct.entity.ShopProductEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.shopproduct.service.ShopProductService;
import com.xmsy.server.zxyy.webhome.modules.manager.sysprop.entity.SysPropEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sysprop.service.SysPropService;



/**
 * 商城产品表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-06-10 15:38:05
 */
@RestController
@RequestMapping("shopproduct/shopproduct")
public class ShopProductController {
    @Autowired
    private ShopProductService shopProductService;
    @Autowired
    private SysPropService sysPropService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("shopproduct:shopproduct:list")
    public R list(ShopProductEntity shopproduct, PageParam pageParam){
        Page<ShopProductEntity> result = new Page<ShopProductEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<ShopProductEntity> entityWrapper = new EntityWrapper<ShopProductEntity>(shopproduct);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		shopproduct.selectPage(result, entityWrapper);
		List<ShopProductEntity> list=result.getRecords();
		if(!CollectionUtils.isEmpty(list)) {
			for(ShopProductEntity entity:list) {
				if(entity!=null) {
					if(entity.getSysPropId()!=null&&entity.getSysPropId()!=0) {
						SysPropEntity sysPropEntity=sysPropService.selectOne(new EntityWrapper<SysPropEntity>(null).eq("id", entity.getSysPropId()));
						if(sysPropEntity!=null) {
							entity.setSysPropName(sysPropEntity.getName());
						}
					}
				}
			}
		}	
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }
    /**
     * 下拉选择
     * @return
     */
    @RequestMapping("/select")
    public R select(SysPropEntity sysPropEntity){
    	List<SysPropEntity> list = sysPropService.selectList(new EntityWrapper<SysPropEntity>(sysPropEntity));
    	return R.ok().put("list", list);
    }
    
    /**
	 * 获取修改添加页面下拉选择
	 */
	@RequestMapping("/getSysPropId")
	public R getSysPropId(@RequestParam(value ="sysPropId")Long sysPropId) {
		ShopProductEntity shopProduct=new ShopProductEntity();
		shopProduct.setSysPropId(sysPropId);
		shopProduct = shopProductService .selectOne(new EntityWrapper<ShopProductEntity>(shopProduct));
		if(shopProduct ==null || shopProduct.getId()==null) {
			throw new RRException(
					 ErrorCode.UserErrorCode.USER_ACCOUNT_INVALID.getErrMsg(),
					ErrorCode.UserErrorCode.USER_ACCOUNT_INVALID.getCode());
		}
		
		return	R.ok().put("data",ImmutableMap.of("sysPropId", shopProduct.getSysPropId()));
	}


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("shopproduct:shopproduct:info")
    public R info(@PathVariable("id") Long id){
			ShopProductEntity shopProduct = shopProductService.selectById(id);
        return R.ok().put("shopproduct", shopProduct);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("shopproduct:shopproduct:save")
    public R save(@RequestBody ShopProductEntity shopproduct){
    	//设置创建时间
    	shopproduct.setCreateTime(new Date());
		shopProductService.insert(shopproduct);	
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("shopproduct:shopproduct:update")
    public R update(@RequestBody ShopProductEntity shopproduct){
    	//设置修改时间
    	shopproduct.setUpdateTime(new Date());
		shopProductService.updateById(shopproduct);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("shopproduct:shopproduct:delete")
    public R delete(@RequestBody Long[] ids){
	    for (Long id : ids) {
			shopProductService.deleteById(id);
		}
	        return R.ok();
	    }

}
