package com.xmsy.server.zxyy.webhome.modules.app.shop.service;
/**
 * 获取道具参数详情列表 方法
 * @author axiong
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.xmsy.server.zxyy.webhome.modules.manager.shopproduct.entity.ShopProductEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sysprop.entity.SysPropEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sysprop.service.SysPropService;

@Service("appShopService")
public class AppShopService {
	@Autowired
	private SysPropService sysPropService;

	/**
	 * 获取正在上架，未删除的商品
	 * 并且通过sys_prop_id,product_number排序的商城产品集合List<ShopProductEntity>
	 * @return List<ShopProductEntity>
	 */
	public List<ShopProductEntity> getListShopProductEntity(){
		ShopProductEntity shopproduct = new ShopProductEntity();
		shopproduct.setDeleteStatus(0);
		shopproduct.setSell(true);
		Wrapper<ShopProductEntity> entityWrapper = new EntityWrapper<ShopProductEntity>(shopproduct);
		entityWrapper.orderBy("sys_prop_id,product_number");// 数据库查询的是按照id进行排列的，所以下面可以通过与上一次对比来进行判断是否相等
		List<ShopProductEntity> listShopProductEntity = shopproduct.selectList(entityWrapper);
		return listShopProductEntity;
	}
	/**
	 * 获取经过格式化的商城商品集合
	 * @param list
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getListProduct(List<ShopProductEntity> list) {
		//总返回结果
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>(); 
		//存放商城产品的集合
		List<Map<String,Object>> productList=new ArrayList<Map<String,Object>>(); 
		// 通过商城产品实体类 获取系统道具实体类
		if (!CollectionUtils.isEmpty(list)) {
			Map<String, Object> map = null;
			Map<String, Object> mapProduct = null;
			int i = 0;
			for (ShopProductEntity entity : list) {	//上一個系統道具id  與    當前系統道具id相比較
				if (i != 0 && !list.get(i - 1).getSysPropId().equals(entity.getSysPropId())) {// 如果系统道具id是不一样的
					map = new HashMap<String, Object>();
					SysPropEntity sysPropEntity = sysPropService.selectById(list.get(i - 1).getSysPropId());// 获取上一個系统道具实体，并把参数传给整体map
					map.put("propId", sysPropEntity.getId());
					map.put("propName", sysPropEntity.getName());
					map.put("product", productList);
					resultList.add(map);
					productList = new ArrayList<Map<String, Object>>();
				}
				if (entity != null) {//系統id一樣，mapProduct放一起
					// 存放商城产品的一部分json数据
					mapProduct = new HashMap<String, Object>();
					mapProduct.put("productId", entity.getId());
					mapProduct.put("propNum", entity.getProductNumber());
					mapProduct.put("price", entity.getProductPrice());
					productList.add(mapProduct);
				}
				i++;
			}
			map = new HashMap<String, Object>();
			SysPropEntity sysPropEntity = sysPropService.selectById(list.get(list.size() - 1).getSysPropId());// 获取系统道具实体，并把参数传给整体map
			map.put("propId", sysPropEntity.getId());
			map.put("propName", sysPropEntity.getName());
			map.put("product", productList);
			resultList.add(map);
		}
		return resultList;
	}
	
}
