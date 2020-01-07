package com.xmsy.server.zxyy.webhome.modules.webim.homepage;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.cache.LocalContentCache;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.DateUtils;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.constant.HallUrlConstant;
import com.xmsy.server.zxyy.webhome.constant.MaterialEnum;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.manager.appcustomerservice.entity.AppCustomerServiceEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.appcustomerservice.service.AppCustomerServiceService;
import com.xmsy.server.zxyy.webhome.modules.manager.imcontact.entity.ImContactManagerEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.imcontact.service.ImContactService;
import com.xmsy.server.zxyy.webhome.modules.manager.imdownloadmanage.service.ImDownloadManageService;
import com.xmsy.server.zxyy.webhome.modules.manager.imgameintroduction.service.ImGameIntroductionService;
import com.xmsy.server.zxyy.webhome.modules.manager.imgameranking.service.ImGameRankingService;
import com.xmsy.server.zxyy.webhome.modules.manager.immaterial.entity.ImMaterialEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.immaterial.service.ImMaterialService;
import com.xmsy.server.zxyy.webhome.modules.manager.imprizepoolranking.service.ImPrizePoolRankingService;
import com.xmsy.server.zxyy.webhome.modules.manager.impromotionalgame.entity.ImPromotionalGameEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.impromotionalgame.service.ImPromotionalGameService;
import com.xmsy.server.zxyy.webhome.modules.manager.imservicemanager.service.ImServiceManagerService;
import com.xmsy.server.zxyy.webhome.modules.manager.imshuffling.service.ImShufflingService;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.service.RankingListService;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.vo.RankingListRequestVo;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.vo.RankingListUserVo;
import com.xmsy.server.zxyy.webhome.modules.webim.homepage.result.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

/**
 * 33娱乐官网接口
 *
 * @author aleng
 */
@Slf4j
@RestController
@RequestMapping("webim/v1/home")
public class WebPageController {

    @Resource
    private ImMaterialService imMaterialService;
    @Autowired
    private AppCustomerServiceService appCustomerServiceService;
    @Autowired
    private ImServiceManagerService imServiceManagerService;
    @Autowired
    private ImShufflingService imShufflingService;
    @Autowired
    private ImPromotionalGameService imPromotionalGameService;
    @Autowired
    private ImDownloadManageService imDownloadManageService;
    @Resource
    private RankingListService rankingListService;
    @Autowired
    private LocalContentCache localContentCache;
    @Autowired
    private ImGameIntroductionService imGameIntroductionService;
    @Autowired
    private ImGameRankingService imGameRankingService;
    @Autowired
    private ImPrizePoolRankingService imPrizePoolRankingService;
    @Autowired
    private ImContactService imContactService;

    /**
     * 获取内容详情
     */
    @GetMapping("/material/info/{id}")
    public R getMaterial(@PathVariable("id") Long id) {
        ImMaterialEntity imMaterial = imMaterialService.selectById(id);
        return R.ok().put("data", imMaterial);
    }

    /**
     * 获取活动列表
     */
    @GetMapping("/material/activities")
    public R getActivities(PageParam pageParam) {
        ImMaterialEntity imMaterial = new ImMaterialEntity().setCategory(MaterialEnum.ACTIVITY.getId());
        Page<ImMaterialEntity> imMaterialPage = imMaterialService.getImMaterials(imMaterial, pageParam);
        return R.ok().put("page", new PageUtils(imMaterialPage.getRecords(), imMaterialPage.getTotal(),
                imMaterialPage.getSize(), imMaterialPage.getCurrent(), imMaterialPage.getPages()));
    }

    /**
     * 获取新闻列表
     */
    @GetMapping("/material/news")
    public R getNews(PageParam pageParam) {
        ImMaterialEntity material = new ImMaterialEntity().setCategory(MaterialEnum.NEWS.getId());
        Page<ImMaterialEntity> imMaterialPage = imMaterialService.getImMaterials(material, pageParam);
        return R.ok().put("page", new PageUtils(imMaterialPage.getRecords(), imMaterialPage.getTotal(),
                imMaterialPage.getSize(), imMaterialPage.getCurrent(), imMaterialPage.getPages()));
    }

    /**
     * 获取特色列表
     */
    @GetMapping("/material/special")
    public R getSpecialList(PageParam pageParam) {
        ImMaterialEntity imMaterial = new ImMaterialEntity().setCategory(MaterialEnum.SPECIAL.getId());
        Page<ImMaterialEntity> imMaterialPage = imMaterialService.getImMaterials(imMaterial, pageParam);
        return R.ok().put("page", new PageUtils(imMaterialPage.getRecords(), imMaterialPage.getTotal(),
                imMaterialPage.getSize(), imMaterialPage.getCurrent(), imMaterialPage.getPages()));
    }

    /**
     * 获取维护公告列表列表
     */
    @GetMapping("/material/maintaince")
    public R getMaintainList(PageParam pageParam) {
        ImMaterialEntity imMaterial = new ImMaterialEntity().setCategory(MaterialEnum.MAINTENANCE.getId());
        Page<ImMaterialEntity> imMaterialPage = imMaterialService.getImMaterials(imMaterial, pageParam);
        return R.ok().put("page", new PageUtils(imMaterialPage.getRecords(), imMaterialPage.getTotal(),
                imMaterialPage.getSize(), imMaterialPage.getCurrent(), imMaterialPage.getPages()));
    }

    /**
     * 获取盗公告列表
     */
    @GetMapping("/material/anti-theft")
    public R getAntitheft(PageParam pageParam) {
        ImMaterialEntity imMaterial = new ImMaterialEntity().setCategory(MaterialEnum.ANTI_THEFT.getId());
        Page<ImMaterialEntity> imMaterialPage = imMaterialService.getImMaterials(imMaterial, pageParam);
        return R.ok().put("page", new PageUtils(imMaterialPage.getRecords(), imMaterialPage.getTotal(),
                imMaterialPage.getSize(), imMaterialPage.getCurrent(), imMaterialPage.getPages()));
    }

    /**
     * 获取最新消息
     */
    @GetMapping("/material/latest-new")
    public R getLatestNew(PageParam pageParam) {
        ImMaterialEntity imMaterial = new ImMaterialEntity().setCategory(MaterialEnum.LATEST_NEW.getId());
        Page<ImMaterialEntity> imMaterialPage = imMaterialService.getImMaterials(imMaterial, pageParam);
        return R.ok().put("page", new PageUtils(imMaterialPage.getRecords(), imMaterialPage.getTotal(),
                imMaterialPage.getSize(), imMaterialPage.getCurrent(), imMaterialPage.getPages()));
    }

    /**
     * 获取游戏截图列表
     */
    @GetMapping("/material/game/creenshots")
    public R getGameCreenshots(PageParam pageParam) {
        ImMaterialEntity imMaterial = new ImMaterialEntity().setCategory(MaterialEnum.GAME_SCREENSHOTS.getId());
        Page<ImMaterialEntity> imMaterialPage = imMaterialService.getImMaterials(imMaterial, pageParam);
        return R.ok().put("page", new PageUtils(imMaterialPage.getRecords(), imMaterialPage.getTotal(),
                imMaterialPage.getSize(), imMaterialPage.getCurrent(), imMaterialPage.getPages()));
    }

    /**
     * 获取新手入门列表
     */
    @GetMapping("/material/new-hand-start")
    public R getNewHandStart(PageParam pageParam) {
        ImMaterialEntity imMaterial = new ImMaterialEntity().setCategory(MaterialEnum.NEW_HAND_START.getId());
        Page<ImMaterialEntity> imMaterialPage = imMaterialService.getImMaterials(imMaterial, pageParam);
        return R.ok().put("page", new PageUtils(imMaterialPage.getRecords(), imMaterialPage.getTotal(),
                imMaterialPage.getSize(), imMaterialPage.getCurrent(), imMaterialPage.getPages()));
    }

    /**
     * 获取游背景轮播图列表
     */
    @GetMapping("/material/background")
    public R getBackground(PageParam pageParam) {
        ImMaterialEntity imMaterial = new ImMaterialEntity().setCategory(MaterialEnum.BACKGROUND.getId());
        List<ImMaterialEntity> imMaterials = imMaterialService.getImMaterials(imMaterial);
        return R.ok().put("data", imMaterials);
    }

    /**
     * 获取vip段位介绍
     */
    @GetMapping("/material/vip-introduction")
    public R getVipIntroduction(PageParam pageParam) {
        ImMaterialEntity imMaterial = new ImMaterialEntity().setCategory(MaterialEnum.VIP_INTRODUCTION.getId());
        List<ImMaterialEntity> imMaterials = imMaterialService.getImMaterials(imMaterial);
        if (CollectionUtils.isEmpty(imMaterials)) {
            return R.ok();
        }
        ImMaterialEntity entity = imMaterials.get(0);
        return R.ok().put("data", entity.selectById());
    }

    /**
     * 获取玩家福利列表
     */
    @GetMapping("/material/player-benefit")
    public R getPlayerBenefit(PageParam pageParam) {
        ImMaterialEntity imMaterial = new ImMaterialEntity().setCategory(MaterialEnum.PLAYER_BENEFITS.getId());
        Page<ImMaterialEntity> imMaterialPage = imMaterialService.getImMaterials(imMaterial, pageParam);
        return R.ok().put("page", new PageUtils(imMaterialPage.getRecords(), imMaterialPage.getTotal(),
                imMaterialPage.getSize(), imMaterialPage.getCurrent(), imMaterialPage.getPages()));
    }

    /**
     * 获取充值帮助列表
     */
    @GetMapping("/material/recharge-help")
    public R getRechargeHelp(PageParam pageParam) {
        ImMaterialEntity imMaterial = new ImMaterialEntity().setCategory(MaterialEnum.RECHARGE_HELP.getId());
        Page<ImMaterialEntity> imMaterialPage = imMaterialService.getImMaterials(imMaterial, pageParam);
        return R.ok().put("page", new PageUtils(imMaterialPage.getRecords(), imMaterialPage.getTotal(),
                imMaterialPage.getSize(), imMaterialPage.getCurrent(), imMaterialPage.getPages()));
    }

    /**
     * 获取取款规则列表
     */
    @GetMapping("/material/withdrawals-rule")
    public R getWithdrawalsRule(PageParam pageParam) {
        ImMaterialEntity imMaterial = new ImMaterialEntity().setCategory(MaterialEnum.WITHDRAWALS_RULE.getId());
        Page<ImMaterialEntity> imMaterialPage = imMaterialService.getImMaterials(imMaterial, pageParam);
        return R.ok().put("page", new PageUtils(imMaterialPage.getRecords(), imMaterialPage.getTotal(),
                imMaterialPage.getSize(), imMaterialPage.getCurrent(), imMaterialPage.getPages()));
    }

    /**
     * 获取客服路径
     */
    @GetMapping("/customer-service")
    public R appCustomerService() {
        List<AppCustomerServiceEntity> list = appCustomerServiceService
                .selectList(new EntityWrapper<AppCustomerServiceEntity>(new AppCustomerServiceEntity()));
        log.info("[AppCustomerServiceEntity] list {}", list);
        return R.ok().put("data", list);
    }

    /**
     * 获取服务器列表
     */
    @GetMapping("/service-list")
    public R serviceList(PageParam pageParam) {
        Page<ImServiceManagerResult> pageResult = imServiceManagerService.selectServiceList(pageParam);
        return R.ok().put("data", pageResult);
    }

    /**
     * 获取轮播图列表
     */
    @GetMapping("/shuffling-list")
    public R shufflingList() {
        List<ImShufflingResult> list = imShufflingService.selectShufflingList();
        return R.ok().put("data", list);
    }

    /**
     * 获取推荐游戏列表
     */
    @GetMapping("/promotional-game-list")
    public R promotionalGameList() {
        List<ImPromotionalGameResult> list = imPromotionalGameService.selectPromotionalGameList();
        return R.ok().put("data", list);
    }

    /**
     * 获取推荐游戏内容
     */
    @GetMapping("/promotional-game-info")
    public R promotionalGameInfo(Long promotionalGameId) {
        ImPromotionalGameEntity entity = imPromotionalGameService.selectById(promotionalGameId);
        JSONObject obj = new JSONObject();
        if (null == entity) {
            throw new RRException(ErrorCode.WimHomePageCode.GAME_OBTAIN_ERRO.getErrMsg(),
                    ErrorCode.WimHomePageCode.GAME_OBTAIN_ERRO.getCode());
        }
        obj.put("content", entity.getIntroduction());
        obj.put("gameId", entity.getGameId());
        obj.put("gameName", localContentCache.getGameName(entity.getGameId()));
        return R.ok().put("data", obj);
    }

    /**
     * 获取pc下载内容
     */
    @GetMapping("/pc-download-list")
    public R pcDownloadList() {
        String type = "PC";
        List<ImDownloadManageResult> list = imDownloadManageService.selectListForType(type);
        return R.ok().put("data", list);
    }

    /**
     * 获取app下载内容
     */
    @GetMapping("/app-download-list")
    public R appDownloadList() {
        String type = "APP";
        List<ImDownloadManageResult> list = imDownloadManageService.selectListForType(type);
        return R.ok().put("data", list);
    }

    /**
     * 获取下载内容
     *
     * @param type :类型
     */
    @GetMapping("/download-list/{type}")
    public R downloadList(@PathVariable("type") String type) {
        List<ImDownloadResult> list = imDownloadManageService.selectImDownloadList(type);
        return R.ok().put("data", list);
    }

    /**
     * 排行榜
     */
    @SuppressWarnings("unchecked")
    @GetMapping("/rank-list")
    public R rankingList() {
        Date yesterday = DateUtils.getStartOfYesterday();
        // 财富榜key
        String fortuneKey = SysConstant.FORTUNEKEY +"web"+ DateUtils.format(yesterday, "yyyy-MM-dd");
        Object fortuneObject = localContentCache.get(fortuneKey);
        List<RankingListUserVo> fortuneList = new ArrayList<RankingListUserVo>();
        // 财富榜
        if (fortuneObject == null) {
            // 先用写死的
            RankingListRequestVo fortuneListVo = new RankingListRequestVo();
            fortuneListVo.setLimit(HallUrlConstant.getRANKINGLIST_LIMIT());
            fortuneListVo.setRankingListId(HallUrlConstant.getWEALTH_RANKINGLIST());
            fortuneList = rankingListService.getImRankingListByYesterday(fortuneListVo);
            if (fortuneList != null && !fortuneList.isEmpty()) {
                localContentCache.put(fortuneKey, fortuneList);

            }
        } else {
            fortuneList = (List<RankingListUserVo>) fortuneObject;
        }
        return R.ok().put("data", fortuneList);
    }

    /**
     * 游戏介绍
     */
    @GetMapping("/game-introduction")
    public R gameIntroduction() {
        List<ImGameIntroductionResult> list = imGameIntroductionService.selectListForWib();
        return R.ok().put("data", list);
    }

    /**
     * 热门游戏排行榜
     */
    @GetMapping("/game-rank")
    public R gameRanking() {
        List<ImGameRankingResult> list = imGameRankingService.selectListForWib();
        return R.ok().put("data", list);
    }

    /**
     * 游戏奖池排行榜
     */
    @GetMapping("/game-pool-rank")
    public R gamePrizeRanking() {
        List<ImGamePrizeRankingResult> list = imPrizePoolRankingService.selectListForWib();
        return R.ok().put("data", list);
    }

    /**
     * 获取更多推荐游戏列表（根据房间分类）
     */
    @GetMapping("/promotional-room-game-list")
    public R roomPromotionalGameList() {
        List<ImPromotionalGameResult> list = imPromotionalGameService.selectPromotionalGameList();
        Multimap<String, ImPromotionalGameResult> scoreMultimap = ArrayListMultimap.create();
        String roomName = null;
        for (ImPromotionalGameResult promotionalGame : list) {
//            roomName = SysConstant.gameRoomMap.get(promotionalGame.getGameId());
            roomName = localContentCache.getGameName(promotionalGame.getGameId());
            if (StringUtils.isEmpty(roomName)) {
                continue;
            }
            scoreMultimap.put(roomName, promotionalGame);
        }
        List<ImPromotionalRoomGameResult> result = Lists.newArrayList();
        ImPromotionalRoomGameResult imPromotionalRoomGameResult = null;
        for (Entry<String, Collection<ImPromotionalGameResult>> entry : scoreMultimap.asMap().entrySet()) {
            imPromotionalRoomGameResult = new ImPromotionalRoomGameResult();
            imPromotionalRoomGameResult.setRoomName(entry.getKey());
            imPromotionalRoomGameResult.setGameList(entry.getValue());
            result.add(imPromotionalRoomGameResult);
        }
        return R.ok().put("data", result);
    }


    /**
     * 获取联系方式
     */
    @GetMapping("/im-contact")
    public R getImContactData(String type) {
        Multimap<String, ImContactManagerEntity> map = ArrayListMultimap.create();
        List<ImContactManagerEntity> listImContact = imContactService.getImContact();
        if (!CollectionUtils.isEmpty(listImContact)) {
            for (ImContactManagerEntity imContactManager : listImContact) {
            	if(SysConstant.H5CONTACT.equals(type)) {//h5联系方式
            		if (SysConstant.h5_CONTACTINT_CONTENT == imContactManager.getType()) {
                        map.put(SysConstant.H5CONTACT, imContactManager);
                    }
            	}else if(SysConstant.WEBSITE.equals(type)){//网站信息
            		if (SysConstant.WEBINT_CONTENT == imContactManager.getType()) {
                        map.put(SysConstant.WEBSITE, imContactManager);
                    }
            	}else if(SysConstant.CONTACT.equals(type)) {//网站联系方式
            		if (SysConstant.CONTACTINT_CONTENT == imContactManager.getType()) {
                        map.put(SysConstant.CONTACT, imContactManager);
                    }
            	}else if(SysConstant.OFFICAL_MEDIA.equals(type)) {//官方自媒体
            		if (SysConstant.OFFICAL_MEDIA_INT == imContactManager.getType()) {
                        map.put(SysConstant.OFFICAL_MEDIA, imContactManager);
                    }
            	}else if(SysConstant.COOPRATION_MEDIA.equals(type)) {//合作媒体
            		if (SysConstant.COOPRATION_MEDIA_INT == imContactManager.getType()) {
                        map.put(SysConstant.COOPRATION_MEDIA, imContactManager);
                    }
            	}
            	//为空就全部给
            	if(StringUtils.isEmpty(type)) {
            		if (SysConstant.CONTACTINT_CONTENT == imContactManager.getType()) {
                        map.put(SysConstant.CONTACT, imContactManager);
                    }
            		if (SysConstant.WEBINT_CONTENT == imContactManager.getType()) {
                        map.put(SysConstant.WEBSITE, imContactManager);
                    }
            		if (SysConstant.h5_CONTACTINT_CONTENT == imContactManager.getType()) {
                        map.put(SysConstant.H5CONTACT, imContactManager);
                    }
            		if (SysConstant.OFFICAL_MEDIA_INT == imContactManager.getType()) {
                        map.put(SysConstant.OFFICAL_MEDIA, imContactManager);
                    }
            		if (SysConstant.COOPRATION_MEDIA_INT == imContactManager.getType()) {
                        map.put(SysConstant.COOPRATION_MEDIA, imContactManager);
                    }
            	}
            }
        }

        return R.ok().put("data", map);
    }
}
