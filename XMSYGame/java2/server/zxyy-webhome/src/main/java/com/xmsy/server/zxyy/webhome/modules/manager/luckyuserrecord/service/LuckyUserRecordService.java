package com.xmsy.server.zxyy.webhome.modules.manager.luckyuserrecord.service;



import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.luckyuserrecord.entity.LuckyUserRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户抽奖记录表
 *
 * @author ayang
 * @email xxxxx
 * @date 2019-11-22 17:55:00
 */
public interface LuckyUserRecordService extends IService<LuckyUserRecordEntity> {


    List<Map<String,Object>> insertRecordTwo(UserEntity entity, Long luckyParams)throws Exception ;

    List<Map<String, Object>> selectByUserId(Long userId)throws Exception ;
    List<Map<String, Object>>     selectGrand()throws Exception;
    List<Map<String, Object>>     selectAll()throws Exception;

    List<Map<String, Object>> insertRecordTen(UserEntity entity, Long luckyParams)throws Exception;
}

