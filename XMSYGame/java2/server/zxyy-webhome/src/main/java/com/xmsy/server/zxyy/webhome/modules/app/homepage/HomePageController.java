package com.xmsy.server.zxyy.webhome.modules.app.homepage;

import java.util.List;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.manager.apppromotions.entity.AppPromotionsListEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.apppromotions.service.AppPromotionsService;
import com.xmsy.server.zxyy.webhome.modules.manager.apppromotionstype.entity.AppPromotionsTypeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.apppromotionstype.service.AppPromotionsTypeService;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomeimage.entity.WebHomeImageManagementEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomeimage.service.WebhomeImageService;

//import lombok.extern.slf4j.Slf4j;

/**
 * .首页信息查询
 * 
 * @author Administrator
 *
 */
//@Slf4j
@RestController
@RequestMapping("V1.0/App")
public class HomePageController {

	@Autowired
	private WebhomeImageService webhomeImageService;
	@Autowired
	private AppPromotionsTypeService appPromotionsTypeService;
	@Autowired
	private AppPromotionsService appPromotionsService;

	/**
	 * 获取广告
	 */
	@GetMapping("/advertising")
	public R advertising() {
		Multimap<String, WebHomeImageManagementEntity> map = ArrayListMultimap.create();
		List<WebHomeImageManagementEntity> list = webhomeImageService.selectListByWeb();
		if (!CollectionUtils.isEmpty(list)) {
			for (WebHomeImageManagementEntity entity : list) {
				if (SysConstant.COUPLETS.equals(entity.getType())) {
					map.put(SysConstant.COUPLETS, entity);
				}
			}
		}
		//log.info("[HomePageController]->advertising map {}", map);
		return R.ok().put("data", map);
	}
	/**
	 * 获取优惠活动类型列表
	 */
	@GetMapping("/configure/getPromotionsTypeList")
	public R getPromotionsTypeList() {
		List<AppPromotionsTypeEntity> list = appPromotionsTypeService
				.selectList(new EntityWrapper<AppPromotionsTypeEntity>(new AppPromotionsTypeEntity()));
		return R.ok().put("data", list);
	}

	/**
	 * 通过ID获取优惠活动列表
	 */
	@GetMapping("/configure/getPromotionsListForTypeId")
	public R getPromotionsListForTypeId(@RequestParam("typeId") Long typeId, PageParam pageParam) {
		Page<AppPromotionsListEntity> list = appPromotionsService.getPromotionsList(typeId, pageParam);
		return R.ok().put("data", list);
	}

}
