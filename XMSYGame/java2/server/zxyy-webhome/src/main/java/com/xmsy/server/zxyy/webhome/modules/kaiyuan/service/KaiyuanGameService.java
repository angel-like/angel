package com.xmsy.server.zxyy.webhome.modules.kaiyuan.service;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;

import java.math.BigDecimal;

/**
 * 开元游戏登录
 *
 * @author youyou
 * @email xxxxx
 * @date 2019-11-26 11:18:25
 */
public interface KaiyuanGameService {
  public   JSONObject gameLogin(UserEntity entity, String ip, Long gameId) throws  Exception;

  public   JSONObject logout(UserEntity entity) throws  Exception;

  public   JSONObject clearMoney(UserEntity entity, String amount) throws  Exception;

  public JSONObject queryMoney(UserEntity entity) throws  Exception;

  public JSONObject getRecord() throws  Exception;
  public JSONObject getOnlineStatus(UserEntity entity) throws  Exception;

}
