package com.xmsy.server.zxyy.manager.modules.manager.gamerecordkaiyuan.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.constant.kaiyuan.*;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordkaiyuan.entity.GameRecordKaiyuanEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordkaiyuan.service.GameRecordKaiyuanService;
import com.xmsy.server.zxyy.manager.utils.GameEnum;
import com.xmsy.server.zxyy.manager.utils.GameGradeEnum;
import com.xmsy.server.zxyy.manager.utils.ReadCardValuesForMj;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 开源游戏
 *
 * @author sanqian
 * @email xxxxx
 * @date 2019-12-02 14:21:31
 */
@Slf4j
@RestController
@RequestMapping("gamerecordkaiyuan/gamerecordkaiyuan")
public class GameRecordKaiyuanController {
    @Autowired
    private GameRecordKaiyuanService gameRecordKaiyuanService;

    /**
     * 二人麻将列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:list")
    public R list(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(740L)).orderBy("gameEndTime",false);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            String card = record.getCardStr();
            String cardValue = record.getCardValue();
            log.info(card);
            log.info(cardValue);
            if (cardValue == null || "".equals(cardValue)) {
                String[] arr = card.split(",");
                String cardValues1 = ReadCardValuesForMj.readCard(arr[0]);
                String cardValues2 = ReadCardValuesForMj.readCard(arr[1]);
                if (record.getChairId().equals(1L)) {
                    cardValues1 = "本方:" + cardValues1;
                    cardValues2 = "对方:" + cardValues2;
                } else {
                    cardValues2 = "本方:" + cardValues2;
                    cardValues1 = "对方:" + cardValues1;
                }
                cardValue = cardValues1 + "," + cardValues2;
                log.info(cardValue);

            }
            record.setCardValue(cardValue);
        }

        return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }
    /**
     * 血流成河列表
     */
    @RequestMapping("/list/xlch")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:xlchlist")
    public R listXlch(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
        Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(650L)).orderBy("gameEndTime",false);
        entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
        entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

        entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
        gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            log.info(record.getCardStr());
         record.setCardValue(ReadCardValuesForXlch.readString(record.getCardStr()));
        }
        return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
                result.getCurrent(), result.getPages()));
    }
    /**
     * 森林舞会列表
     */
    @RequestMapping("/list/slwh")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:slwhlist")
    public R listSlwh(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
        Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(920L)).orderBy("gameEndTime",false);
        entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
        entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

        entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
        gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            if(record.getCardValue()==null||"".equals(record.getCardValue())){
                if(record.getCardStr()!=null) {
                    record.setCardValue(ReadCardValuesForSlwh.readCard(record.getCardStr()));
                }

            }
        }
        return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
                result.getCurrent(), result.getPages()));
    }

    
    /**
     * 德州扑克列表
     */
    @RequestMapping("/list/dzpk")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:dzpklist")
    public R listDzpk(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(620L)).orderBy("gameEndTime",false);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            if(record.getCardValue()==null||"".equals(record.getCardValue())){
                if(record.getCardStr()!=null) {
                    record.setCardValue(ReadCardValuesForDzpk.readCard(record.getCardStr()));
                }
            }
        }
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }

    /**
     * 二八杠
     */
    @RequestMapping("/list/erbagang")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:erbaganglist")
    public R listErbagang(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
        Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(720L)).orderBy("gameEndTime",false);
        entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
        entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

        entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
        gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            if(record.getCardValue()==null || "".equals(record.getCardValue())){
                if(record.getCardStr()!=null) {
                    record.setCardValue(ReadCardValuesForMjErBa.readCard(record.getCardStr()));
                }
            }
        }
        return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
                result.getCurrent(), result.getPages()));
    }
    
    
    /**
     * 血战骰宝列表
     */
    @RequestMapping("/list/xztb")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:xztblist")
    public R listXztb(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(1690L)).orderBy("gameEndTime",false);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            if(record.getCardValue()==null||"".equals(record.getCardValue())){
                if(record.getCardStr()!=null) {
                    record.setCardValue(ReadCardValuesForXueZhanToubao.readCard(record.getCardStr()));
                }
            }
        }
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }
    
    /**
     * 红包捕鱼
     */
    @RequestMapping("/list/hbby")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:hbbylist")
    public R listHbby(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(510L)).orderBy("gameEndTime",false);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
        }
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }

    /**
     * 幸运夺宝列表
     */
    @RequestMapping("/list/xydb")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:xydblist")
    public R listXydb(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(1610L)).orderBy("gameEndTime",false);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            if(record.getCardValue()==null||"".equals(record.getCardValue())){
                if(record.getCardStr()!=null) {
                    record.setCardValue(ReadCardValuesForXingYunDuoBao.readCard(record.getCardStr()));
                }
            }
        }
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }
    /**
     港式梭哈
     */
    @RequestMapping("/list/gangshisuoha")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:gangshisuohalist")
    public R listGangShiSuoHa(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
        Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(1370L)).orderBy("gameEndTime",false);
        entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
        entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

        entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
        gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            if(record.getCardValue()==null || "".equals(record.getCardValue())){
                if(record.getCardStr()!=null) {
                    record.setCardValue(ReadCardValuesForGssh.readCard(record.getCardStr()));
                }
            }
        }
        return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
                result.getCurrent(), result.getPages()));
    }
    /**
     * 抢庄牛牛列表
     */
    @RequestMapping("/list/qznn")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:qznnlist")
    public R listQznn(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
        Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(830L)).orderBy("gameEndTime",false);
        entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
        entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

        entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
        gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            if(record.getCardValue()==null||"".equals(record.getCardValue())){
                if(record.getCardStr()!=null) {
                    record.setCardValue(ReadCardValuesForQzhnn.readCard(record.getCardStr()));
                }
            }
        }
        return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
                result.getCurrent(), result.getPages()));
    }
    /**
     * 开元斗地主
     */
    @RequestMapping("/list/doudizhu")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:doudizhulist")
    public R listDouDiZhu(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
        Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(610L)).orderBy("gameEndTime",false);
        entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
        entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

        entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
        gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            if(record.getCardValue()==null || "".equals(record.getCardValue())){
                if(record.getCardStr()!=null) {
                    record.setCardValue(ReadCardValuesForDdz.readCard(record.getCardStr()));
                }
            }
        }
        return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
                result.getCurrent(), result.getPages()));
    }
    /**
     * 开元三公
     */
    @RequestMapping("/list/sg")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:sglist")
    public R listSG(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
        Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(830L)).orderBy("gameEndTime",false);
        entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
        entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

        entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
        gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            if(record.getCardValue()==null || "".equals(record.getCardValue())){
                if(record.getCardStr()!=null) {
                    record.setCardValue(ReadCardValuesForSang.readCard(record.getCardStr()));
                }
            }
        }
        return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
                result.getCurrent(), result.getPages()));
    }
    
    /**
     * 开元炸金花
     */
    @RequestMapping("/list/zjh")
//    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:zjhlist")
    public R listZjh(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(220L)).orderBy("gameEndTime",false);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            if(record.getCardValue()==null||"".equals(record.getCardValue())){
                if(record.getCardStr()!=null) {
                    record.setCardValue(ReadCardValuesForZjh.readCard(record.getCardStr()));
                }
            }
        }
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }
    
    /**
     * 十三水列表
     */
    @RequestMapping("/list/sss")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:ssslist")
    public R listSss(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(630L)).orderBy("gameEndTime",false);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            if(record.getCardValue()==null||"".equals(record.getCardValue())){
                if(record.getCardStr()!=null) {
                    record.setCardValue(ReadCardValuesForShiSanShui.readCard(record.getCardStr()));
                }
            }
        }
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }
    
    /**
     * 赌场扑克列表
     */
    @RequestMapping("/list/dcpk")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:dcpklist")
    public R listDcpk(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(1860L)).orderBy("gameEndTime",false);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            if(record.getCardValue()==null||"".equals(record.getCardValue())){
                if(record.getCardStr()!=null) {
                    record.setCardValue(ReadCardValuesForDuChangPuKe.readCard(record.getCardStr()));
                }
            }
        }
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }
    /**
     * 21点列表
     */
    @RequestMapping("/list/esyd")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:esydlist")
    public R listEsyd(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
        Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(600L)).orderBy("gameEndTime",false);
        entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
        entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

        entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
        gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            if(record.getCardValue()==null||"".equals(record.getCardValue())){
                if(record.getCardStr()!=null) {
                    record.setCardValue(ReadCardValuesForEsyd.readCard(record.getCardStr()));
                }
            }
        }
        return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
                result.getCurrent(), result.getPages()));
    }

    /**
     * 通比牛牛
     */
    @RequestMapping("/list/tbnn")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:tbnnlist")
    public R listTbnn(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
        Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(870L)).orderBy("gameEndTime",false);
        entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
        entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

        entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
        gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            if(record.getCardValue()==null||"".equals(record.getCardValue())){
                if(record.getCardStr()!=null) {
                    record.setCardValue(ReadCardValuesForTbnn.readCard(record.getCardStr()));
                }
            }
        }
        return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
                result.getCurrent(), result.getPages()));
    }
    /**
     * 奔驰宝马列表
     */
    @RequestMapping("/list/bcbm")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:bcbmlist")
    public R listBcbm(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(1960L)).orderBy("gameEndTime",false);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            if(record.getCardValue()==null||"".equals(record.getCardValue())){
                if(record.getCardStr()!=null) {
                    record.setCardValue(ReadCardValuesForBenChiBaoMa.readCard(record.getCardStr()));
                }
            }
        }
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }
    
    /**
     * 五星宏辉列表
     */
    @RequestMapping("/list/wxhh")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:wxhhlist")
    public R listWxhh(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(1970L)).orderBy("gameEndTime",false);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            if(record.getCardValue()==null||"".equals(record.getCardValue())){
                if(record.getCardStr()!=null) {
                    record.setCardValue(ReadCardValuesForWuXingHongHui.readCard(record.getCardStr()));
                }
            }
        }
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }
    /**
     * 金鲨银鲨列表
     */
    @RequestMapping("/list/jsys")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:jsyslist")
    public R listJsys(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
        Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(1940L)).orderBy("gameEndTime",false);
        entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
        entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

        entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
        gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            if(record.getCardValue()==null||"".equals(record.getCardValue())){
                if(record.getCardStr()!=null) {
                    record.setCardValue(ReadCardValuesForJinShaYinSha.readCard(record.getCardStr()));
                }
            }
        }
        return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
                result.getCurrent(), result.getPages()));
    }
    
    /**
     * 极速炸金花列表
     */
    @RequestMapping("/list/jszjh")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:jszjhlist")
    public R listJszjh(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(230L)).orderBy("gameEndTime",false);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            if(record.getCardValue()==null||"".equals(record.getCardValue())){
                if(record.getCardStr()!=null) {
                    record.setCardValue(ReadCardValuesForJiSuZhaJinHua.readCard(record.getCardStr()));
                }
            }
        }
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }
    /**
     * 抢庄牌九
     */
    @RequestMapping("/list/qzpj")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:qzpjlist")
    public R listQzpj(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
        Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(730L)).orderBy("gameEndTime",false);
        entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
        entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

        entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
        gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            if(record.getCardValue()==null||"".equals(record.getCardValue())){
                if(record.getCardStr()!=null) {
                    record.setCardValue(ReadCardValuesForQzpj.readCard(record.getCardStr()));
                }
            }
        }
        return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
                result.getCurrent(), result.getPages()));
    }

    /**
     * 单挑牛牛
     */
    @RequestMapping("/list/dtnn")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:dtnnlist")
    public R listDtnn(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
        Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(1810L)).orderBy("gameEndTime",false);
        entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
        entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

        entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
        gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            if(record.getCardValue()==null||"".equals(record.getCardValue())){
                if(record.getCardStr()!=null) {
                    record.setCardValue(ReadCardValuesForDtnn.readCard(record.getCardStr()));
                }
            }
        }
        return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
                result.getCurrent(), result.getPages()));
    }
    
    /**
     * 百家乐列表
     */
    @RequestMapping("/list/bjl")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:bjllist")
    public R listBjl(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(910L)).orderBy("gameEndTime",false);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            if(record.getCardValue()==null||"".equals(record.getCardValue())){
                if(record.getCardStr()!=null) {
                    record.setCardValue(ReadCardValuesForBaiJiaLe.readCard(record.getCardStr()));
                }
            }
        }
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }
    
    /**
     * 百人骰宝列表
     */
    @RequestMapping("/list/brtb")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:brtblist")
    public R listBrtb(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(1980L)).orderBy("gameEndTime",false);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            if(record.getCardValue()==null||"".equals(record.getCardValue())){
                if(record.getCardStr()!=null) {
                    record.setCardValue(ReadCardValuesForBaiRenTouBao.readCard(record.getCardStr()));
                }
            }
        }
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }


    /**
     * 万人炸金花列表
     */
    @RequestMapping("/list/wrzjh")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:wrzjhlist")
    public R listWrzjh(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(1950L)).orderBy("gameEndTime",false);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            if(record.getCardValue()==null||"".equals(record.getCardValue())){
                if(record.getCardStr()!=null) {
                    record.setCardValue(ReadCardValuesForWrzjh.readCard(record.getCardStr()));
                }
            }
        }
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }

    /**
     * 押宝抢庄牛牛列表
     */
    @RequestMapping("/list/ybqznn")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:ybqznnlist")
    public R listYbqznn(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(1850L)).orderBy("gameEndTime",false);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            if(record.getCardValue()==null||"".equals(record.getCardValue())){
                if(record.getCardStr()!=null) {
                    record.setCardValue(ReadCardValuesForYaBaoQiangZhuangNiuNiu.readCard(record.getCardStr()));
                }
            }
        }
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }

    /**
     * 炸金牛列表
     */
    @RequestMapping("/list/zjn")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:zjnlist")
    public R listZjn(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
    	Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(1990L)).orderBy("gameEndTime",false);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            if(record.getCardValue()==null||"".equals(record.getCardValue())){
                if(record.getCardStr()!=null) {
                    record.setCardValue(ReadCardValuesForZjn.readCard(record.getCardStr()));
                }
            }
        }
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }

    /**
     * 押庄龙虎列表
     */
    @RequestMapping("/list/yzlh")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:yzlhlist")
    public R listYzlh(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
    	Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(900L)).orderBy("gameEndTime",false);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());

		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            if(record.getCardValue()==null||"".equals(record.getCardValue())){
                if(record.getCardStr()!=null) {
                    record.setCardValue(ReadCardValuesForYaZhuangLongHu.readCard(record.getCardStr()));
                }
            }
        }
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
    }
    
    /**
     * 百人牛牛列表
     */
    @RequestMapping("/list/brnn")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:brnnlist")
    public R listBrnn(GameRecordKaiyuanEntity gamerecordkaiyuan, PageParam pageParam){
        Page<GameRecordKaiyuanEntity> result = new Page<GameRecordKaiyuanEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<GameRecordKaiyuanEntity> entityWrapper = new EntityWrapper<GameRecordKaiyuanEntity>(gamerecordkaiyuan.setKindId(930L)).orderBy("gameEndTime",false);
		entityWrapper.ge(!StringUtils.isBlank(gamerecordkaiyuan.getStartTime()),"create_time", gamerecordkaiyuan.getStartTime());
		entityWrapper.le(!StringUtils.isBlank(gamerecordkaiyuan.getEndTime()),"create_time", gamerecordkaiyuan.getEndTime());
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		gamerecordkaiyuan.selectPage(result, entityWrapper);
        List<GameRecordKaiyuanEntity> records = result.getRecords();
        for (GameRecordKaiyuanEntity record : records) {
            String gameName = GameEnum.getGameName(record.getKindId());
            record.setGameName(gameName);
            String  gradeName = GameGradeEnum.getGradeName(record.getServerId());
            record.setGradeName(gradeName);
            if(record.getCardValue()==null||"".equals(record.getCardValue())){
                if(record.getCardStr()!=null) {
                    record.setCardValue(ReadCardValuesForBaiRenNiuNiu.readCard(record.getCardStr()));
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
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:info")
    public R info(@PathVariable("id") Long id){
			GameRecordKaiyuanEntity gameRecordKaiyuan = gameRecordKaiyuanService.selectById(id);
        String gameName = GameEnum.getGameName(gameRecordKaiyuan.getKindId());
        gameRecordKaiyuan.setGameName(gameName);
        String  gradeName = GameGradeEnum.getGradeName(gameRecordKaiyuan.getServerId());
        gameRecordKaiyuan.setGradeName(gradeName);
        return R.ok().put("gamerecordkaiyuan", gameRecordKaiyuan);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:save")
    public R save(@RequestBody GameRecordKaiyuanEntity gamerecordkaiyuan){
			gameRecordKaiyuanService.insert(gamerecordkaiyuan);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:update")
    public R update(@RequestBody GameRecordKaiyuanEntity gamerecordkaiyuan){
			gameRecordKaiyuanService.updateById(gamerecordkaiyuan);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gamerecordkaiyuan:gamerecordkaiyuan:delete")
    public R delete(@RequestBody Long[] ids){
    for (Long id : ids) {
			gameRecordKaiyuanService.deleteById(id);
	}
        return R.ok();
    }

}
