package com.xmsy.server.zxyy.webhome.modules.web.homepage;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.manager.domainmanagement.entity.DomainEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.domainmanagement.entity.DomainManagementEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.domainmanagement.service.DomainManagementService;
import com.xmsy.server.zxyy.webhome.modules.manager.noticemanagement.entity.NoticeManagementEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.noticemanagement.service.NoticeManagementService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.dao.UserDao;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationtree.dao.UserRecommendationTreeDao;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationtree.entity.UserRecommendationTreeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationtree.service.UserRecommendationTreeService;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomealertcofig.service.WebhomeAlertCofigService;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomecontact.entity.WebContactManagementEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomecontact.service.WebhomeContactService;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomefriendship.entity.WebhomeFriendshipEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomefriendship.service.WebhomeFriendshipService;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomefriendshiptemplate.entity.WebhomeFriendshipTemplateEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomefriendshiptemplate.service.WebhomeFriendshipTemplateService;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomehead.entity.WebhomeHeadEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomehead.service.WebhomeHeadService;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomeimage.entity.WebHomeImageManagementEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomeimage.service.WebhomeImageService;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomemenu.service.WebhomeMenuService;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomemenutemplate.entity.WebhomeMenuTemplateEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomemenutemplate.service.WebhomeMenuTemplateService;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomepopulargames.entity.WebhomePopularGamesEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomepopulargames.service.WebhomePopularGamesService;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomepromotions.entity.WebhomePromotionsEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomepromotions.entity.WebhomePromotionsListEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomepromotions.service.WebhomePromotionsService;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomepromotionstype.entity.WebhomePromotionsTypeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomepromotionstype.service.WebhomePromotionsTypeService;
import com.xmsy.server.zxyy.webhome.modules.web.homepage.entity.WebhomeAlertEntity;
import com.xmsy.server.zxyy.webhome.utils.DomainUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 获取官网配置
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2018-12-31 11:18:25
 */
@Slf4j
@RestController
@RequestMapping("webhome/public")
public class WebConfigureController {
	@Autowired
	private WebhomeImageService webhomeImageService;
	@Autowired
	private WebhomeContactService webhomeContactService;
	@Autowired
	private WebhomeMenuService webhomeMenuService;
	@Autowired
	private WebhomeMenuTemplateService webhomeMenuTemplateService;
	@Autowired
	private WebhomeFriendshipService webhomeFriendshipService;
	@Autowired
	private NoticeManagementService noticeManagementService;
	@Autowired
	private WebhomeHeadService webhomeHeadService;
	@Autowired
	private WebhomePromotionsTypeService webhomePromotionsTypeService;
	@Autowired
	private WebhomePromotionsService webhomePromotionsService;
	@Autowired
	private WebhomePopularGamesService webhomePopularGamesService;
	@Autowired
	private WebhomeFriendshipTemplateService webhomeFriendshipTemplateService;
	@Autowired
	private DomainManagementService domainManagementService;
	@Autowired
	private WebhomeAlertCofigService webhomeAlertCofigService;
	@Autowired
	private UserRecommendationTreeDao userRecommendationTreeDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserRecommendationTreeService userRecommendationTreeService;

	/**
	 * 获取头部配置列表
	 */
	@GetMapping("/configure/getHeadList")
	public R getHeadList() {
		List<WebhomeHeadEntity> map = webhomeHeadService.selectListByWeb();
		return R.ok().put("data", map);
	}

	/**
	 * 获取头部配置内容
	 */
	@GetMapping("/configure/getHeadContent")
	public R getHeadContent(String type) {
		List<WebhomeHeadEntity> list = webhomeHeadService.selectListBytype(type);
		return R.ok().put("data", list);
	}

	/**
	 * 获取图片列表
	 */
	@GetMapping("/configure/enclosure")
	public R enclosure() {
		Multimap<String, WebHomeImageManagementEntity> map = ArrayListMultimap.create();
		List<WebHomeImageManagementEntity> list = webhomeImageService.selectListByWeb();
		if (!CollectionUtils.isEmpty(list)) {
			for (WebHomeImageManagementEntity entity : list) {
				if (SysConstant.LOGO.equals(entity.getType())) {
					map.put(SysConstant.LOGO, entity);
				}
				if (SysConstant.EXHIBITION.equals(entity.getType())) {
					map.put(SysConstant.EXHIBITION, entity);
				}
				if (SysConstant.COUPLETS.equals(entity.getType())) {
					map.put(SysConstant.COUPLETS, entity);
				}
				if (SysConstant.CANCEL.equals(entity.getType())) {
					map.put(SysConstant.CANCEL, entity);
				}
				if (SysConstant.BOTTOM.equals(entity.getType())) {
					map.put(SysConstant.BOTTOM, entity);
				}
				if (SysConstant.DOMAIN.equals(entity.getType())) {
					map.put(SysConstant.DOMAIN, entity);
				}
			}
		}
		return R.ok().put("data", map);
	}

	/**
	 * 获取GAMERECORD图片
	 */
	@GetMapping("/configure/gameGecordEnclosure")
	public R gameGecordEnclosure() {
		Multimap<String, WebHomeImageManagementEntity> map = ArrayListMultimap.create();
		List<WebHomeImageManagementEntity> list = webhomeImageService.selectListByWeb();
		if (!CollectionUtils.isEmpty(list)) {
			for (WebHomeImageManagementEntity entity : list) {
				if (SysConstant.GAMERECORD.equals(entity.getType())) {
					map.put(SysConstant.GAMERECORD, entity);
				}
			}
		}
		return R.ok().put("data", map);
	}

	/**
	 * 获取App下载图片
	 */
	@GetMapping("/configure/gameAppDownloadEnclosure")
	public R gameAppDownloadEnclosure() {
		Multimap<String, WebHomeImageManagementEntity> map = ArrayListMultimap.create();
		List<WebHomeImageManagementEntity> list = webhomeImageService.selectListByWeb();
		if (!CollectionUtils.isEmpty(list)) {
			for (WebHomeImageManagementEntity entity : list) {
				if (SysConstant.APPDOWNLOAD.equals(entity.getType())) {
					map.put(SysConstant.APPDOWNLOAD, entity);
				}
			}
		}
		return R.ok().put("data", map);
	}

	/**
	 * 获取App教程图片
	 */
	@GetMapping("/configure/gameAppTutorialEnclosure")
	public R gameAppTutorialEnclosure() {
		Multimap<String, WebHomeImageManagementEntity> map = ArrayListMultimap.create();
		List<WebHomeImageManagementEntity> list = webhomeImageService.selectListByWeb();
		if (!CollectionUtils.isEmpty(list)) {
			for (WebHomeImageManagementEntity entity : list) {
				if (SysConstant.APPTUTORIAL.equals(entity.getType())) {
					map.put(SysConstant.APPTUTORIAL, entity);
				}
			}
		}
		return R.ok().put("data", map);
	}

	/**
	 * 获取TRANSACTIONRECORD图片
	 */
	@GetMapping("/configure/transacTionrecordEnclosure")
	public R transacTionrecordEnclosure() {
		Multimap<String, WebHomeImageManagementEntity> map = ArrayListMultimap.create();
		List<WebHomeImageManagementEntity> list = webhomeImageService.selectListByWeb();
		if (!CollectionUtils.isEmpty(list)) {
			for (WebHomeImageManagementEntity entity : list) {

				if (SysConstant.TRANSACTIONRECORD.equals(entity.getType())) {
					map.put(SysConstant.TRANSACTIONRECORD, entity);
				}

			}
		}
		return R.ok().put("data", map);
	}

	/**
	 * 获取RECHARGERECORD图片
	 */
	@GetMapping("/configure/recharGerecordEnclosure")
	public R recharGerecordEnclosure() {
		Multimap<String, WebHomeImageManagementEntity> map = ArrayListMultimap.create();
		List<WebHomeImageManagementEntity> list = webhomeImageService.selectListByWeb();
		if (!CollectionUtils.isEmpty(list)) {
			for (WebHomeImageManagementEntity entity : list) {
				if (SysConstant.RECHARGERECORD.equals(entity.getType())) {
					map.put(SysConstant.RECHARGERECORD, entity);
				}
			}
		}
		return R.ok().put("data", map);
	}

	/**
	 * 获取优惠活动图片
	 */
	@GetMapping("/configure/promotionsEnclosure")
	public R promotionsEnclosure() {
		Multimap<String, WebHomeImageManagementEntity> map = ArrayListMultimap.create();
		List<WebHomeImageManagementEntity> list = webhomeImageService.selectListByWeb();
		if (!CollectionUtils.isEmpty(list)) {
			for (WebHomeImageManagementEntity entity : list) {
				if (SysConstant.PROMOTIONS.equals(entity.getType())) {
					map.put(SysConstant.PROMOTIONS, entity);
				}
			}
		}
		return R.ok().put("data", map);
	}

	/**
	 * 获取首页支付图片
	 */
	@GetMapping("/configure/homePayEnclosure")
	public R homePayEnclosure() {
		Multimap<String, WebHomeImageManagementEntity> map = ArrayListMultimap.create();
		List<WebHomeImageManagementEntity> list = webhomeImageService.selectListByWeb();
		if (!CollectionUtils.isEmpty(list)) {
			for (WebHomeImageManagementEntity entity : list) {
				if (SysConstant.PAY.equals(entity.getType())) {
					map.put(SysConstant.PAY, entity);
				}
			}
		}
		return R.ok().put("data", map);
	}

	/**
	 * 获取联系方式及网站信息
	 */
	@GetMapping("/configure/contactmanagement")
	public R contactmanagement() {
		Multimap<String, WebContactManagementEntity> map = ArrayListMultimap.create();
		List<WebContactManagementEntity> list = webhomeContactService.selectListByWeb();
		if (!CollectionUtils.isEmpty(list)) {
			for (WebContactManagementEntity entity : list) {
				if (SysConstant.CONTACT_CONTENT.equals(entity.getType())) {
					map.put("contact", entity);
				}
				if (SysConstant.WEBSITE_CONTENT.equals(entity.getType())) {
					map.put("website", entity);
				}

			}
		}
		return R.ok().put("data", map);
	}

	/**
	 * 获取菜单列表
	 */
	@GetMapping("/configure/getMenu")
	public R getMenu() {
		List<Object> map = webhomeMenuService.selectMenuList();
		return R.ok().put("data", map);
	}

	/**
	 * 获取友情链接列表
	 */
	@GetMapping("/configure/getFriendship")
	public R getFriendship() {
		List<WebhomeFriendshipEntity> list = webhomeFriendshipService.selectFriendshipList();
		return R.ok().put("data", list);
	}

	/**
	 * 获取友情链接内容
	 */
	@GetMapping("/configure/getFriendshipTemplate")
	public R getFriendshipTemplate(Long friendshipId) {
		List<WebhomeFriendshipTemplateEntity> list = webhomeFriendshipTemplateService.selectTemplateList(friendshipId);
		return R.ok().put("data", list);
	}

	/**
	 * 根据菜单Id获取菜单模板
	 */
	@GetMapping("/configure/MenuTemplate")
	public R MenuTemplate(Long id) {
		List<WebhomeMenuTemplateEntity> list = webhomeMenuTemplateService.selectListForMenuId(id);
		return R.ok().put("data", list);
	}

	/**
	 * 获取公告
	 */
	@GetMapping("/configure/noticeManagementList")
	public R noticeManagementList() {
		NoticeManagementEntity entity = new NoticeManagementEntity();
		entity.setEnable(SysConstant.ENABLE_TRUE);
		List<NoticeManagementEntity> list = noticeManagementService
				.selectList(new EntityWrapper<NoticeManagementEntity>(entity));
		return R.ok().put("data", list);
	}

	/**
	 * 获取优惠活动类型列表
	 */
	@GetMapping("/configure/getPromotionsTypeList")
	public R getPromotionsTypeList() {
		List<WebhomePromotionsTypeEntity> list = webhomePromotionsTypeService
				.selectList(new EntityWrapper<WebhomePromotionsTypeEntity>(new WebhomePromotionsTypeEntity()));
		return R.ok().put("data", list);
	}

	/**
	 * 通过ID获取优惠列表列表
	 */
	@GetMapping("/configure/getPromotionsListForTypeId")
	public R getPromotionsListForTypeId(@RequestParam("typeId") Long typeId, PageParam pageParam) {
		Page<WebhomePromotionsListEntity> list = webhomePromotionsService.getPromotionsList(typeId, pageParam);
		return R.ok().put("data", list);
	}

	/**
	 * 通过指定优惠活动类别获取优惠活动列表
	 */
	@GetMapping("/configure/promotions/type/{typeId}")
	public R getPromotionsListByTypeId(@PathVariable("typeId") Long typeId, PageParam pageParam) {
		Page<WebhomePromotionsListEntity> list = webhomePromotionsService.getPromotions(typeId, pageParam);
		return R.ok().put("data", list);
	}

	/**
	 * 通过指定优惠内容
	 */
	@GetMapping("/configure/promotions/{id}")
	public R getPromotiontById(@PathVariable("id") Long id) {
		WebhomePromotionsEntity promotion = webhomePromotionsService.selectById(id);
		return R.ok().put("data", promotion);
	}

	/**
	 * 获取热门游戏列表
	 */
	@GetMapping("/configure/getPopularGamesList")
	public R getPopularGamesList() {
		List<WebhomePopularGamesEntity> list = webhomePopularGamesService.selectEnableListForWeb();
		return R.ok().put("data", list);
	}

	/**
	 * 获取线路检测列表
	 */
	@GetMapping("/configure/getDomainList")
	public R getDomainList() {
		List<DomainManagementEntity> list = domainManagementService
				.selectList(new EntityWrapper<DomainManagementEntity>(new DomainManagementEntity().setType(1)));
		List<DomainEntity> domainList = new ArrayList<DomainEntity>();
		if (!CollectionUtils.isEmpty(list)) {
			for (DomainManagementEntity entity : list) {
				if (null != entity && !StringUtils.isEmpty(entity.getDomain())) {
					DomainEntity DomainEntity = new DomainEntity();
					DomainEntity.setDomain(entity.getDomain());
					long time = DomainUtil.ping(entity.getDomain().substring(entity.getDomain().indexOf("//") + 2));
					DomainEntity.setTime(time + 10);
					domainList.add(DomainEntity);
				}
			}
		}
		return R.ok().put("data", domainList);
	}

	/**
	 * 获取弹窗列表
	 */
	@GetMapping("/configure/getAlertAd")
	public R getAlertAd() {
		List<WebhomeAlertEntity> list = webhomeAlertCofigService.getAlertAd();
		return R.ok().put("data", list);
	}

	/**
	 * 邀请关系树补充数据
	 */
	@GetMapping("/configure/getSql")
	public R sql(String token) {
		if (!token.equals(
				"eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI1MDMxMzRfNzgyOTA2Mjg5OTY0MDA0MCJ9.-mOzAouAZkab8eU1RxzgyaGKeUcwyvHyvWJW-YB6lo8")) {
			return R.error("身份验证失败");
		}
		List<UserRecommendationTreeEntity> treeList = new ArrayList<UserRecommendationTreeEntity>();
		List<Map<String, Object>> noSuperiorList = userRecommendationTreeDao.getNoSuperiorSql();// 获取所有没有上级的用户
		if (!CollectionUtils.isEmpty(noSuperiorList)) {
			for (Map<String, Object> map : noSuperiorList) {
				UserRecommendationTreeEntity entity = new UserRecommendationTreeEntity();
				entity.setUserId((Long) map.get("id"));
				entity.setUserParantDistance(0);
				entity.setParantUserId((Long) map.get("id"));
				treeList.add(entity);
			}
			try {
				userRecommendationTreeService.insertBatch(treeList, 500);
			} catch (Exception e) {
				log.error(e.getMessage());
			}

		}
		List<Map<String, Object>> list = userRecommendationTreeDao.getSql();// 获取所有有下级没有上级的用户
		if (!CollectionUtils.isEmpty(list)) {
			for (Map<String, Object> map : list) {
				List<Long> ids = new ArrayList<Long>();
				ids.add((Long) map.get("superiorsUserId"));
				List<UserEntity> userList = userDao.selectList(new EntityWrapper<UserEntity>(
						new UserEntity().setSuperiorsId((Long) map.get("superiorsUserId"))));
				if (!CollectionUtils.isEmpty(userList)) {
					digui((Long) map.get("superiorsUserId"), ids);
				}
			}
		}
		return R.ok();
	}

	private void digui(Long userId, List<Long> ids) {
		List<UserEntity> userList = userDao
				.selectList(new EntityWrapper<UserEntity>(new UserEntity().setSuperiorsId(userId)));
		List<Long> newIds = new ArrayList<Long>(ids);
		if (!CollectionUtils.isEmpty(userList)) {
			for (UserEntity user : userList) {
				newIds.add(user.getId());
				List<UserEntity> list = userDao
						.selectList(new EntityWrapper<UserEntity>(new UserEntity().setSuperiorsId(user.getId())));
				if (!CollectionUtils.isEmpty(list)) {
					digui(user.getId(), newIds);
				}
				for (int i = 0; i < newIds.size(); i++) {
					UserRecommendationTreeEntity entity = new UserRecommendationTreeEntity();
					entity.setUserId(user.getId());
					entity.setUserParantDistance(newIds.size() - (i + 1));
					entity.setParantUserId(newIds.get(i));
					try {
						userRecommendationTreeDao.insert(entity);
					} catch (Exception e) {
						log.error(e.getMessage());
					}

				}
				newIds.remove(user.getId());
			}
		}

	}

}
