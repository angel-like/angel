package com.xmsy.server.zxyy.webhome.modules.manager.luckyuserrecord.service.impl;



import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.OrderNoUtil;
import com.xmsy.server.zxyy.webhome.common.utils.PrizeUtils;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.app.event.PushService;
import com.xmsy.server.zxyy.webhome.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.webhome.modules.app.user.param.UpdateCoinParam;
import com.xmsy.server.zxyy.webhome.modules.manager.lucky.entity.LuckyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.lucky.service.LuckyService;
import com.xmsy.server.zxyy.webhome.modules.manager.luckyconfig.entity.LuckyConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.luckyconfig.service.LuckyConfigService;
import com.xmsy.server.zxyy.webhome.modules.manager.luckyuserrecord.dao.LuckyUserRecordDao;
import com.xmsy.server.zxyy.webhome.modules.manager.luckyuserrecord.entity.LuckyUserRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.luckyuserrecord.service.LuckyUserRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.ordercashexamine.service.OrderCashExamineService;
import com.xmsy.server.zxyy.webhome.modules.manager.shopproprecord.entity.ShopPropRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.dao.UserDao;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.userinfo.entity.UserInfoEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userinfo.service.UserInfoService;
import com.xmsy.server.zxyy.webhome.modules.manager.usertransactionrecord.entity.UserTransactionRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.usertransactionrecord.service.UserTransactionRecordService;
import com.xmsy.server.zxyy.webhome.utils.Base64Util;
import com.xmsy.server.zxyy.webhome.utils.VerificationUitl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Transactional
@Service("luckyUserRecordService")
public class LuckyUserRecordServiceImpl extends ServiceImpl<LuckyUserRecordDao, LuckyUserRecordEntity> implements LuckyUserRecordService {
    @Autowired
    UserService userService;
    @Autowired
    private UserTransactionRecordService userTransactionRecordService;
    @Autowired
    private OrderCashExamineService orderCashExamineService;
    @Autowired
    private PushService pushService;
    @Autowired
    private LuckyConfigService luckyConfigService;
    @Autowired
    private LuckyService luckyService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private  UserDao userDao;



    @Override
    public  List<Map<String,Object>> insertRecordTwo(UserEntity user, Long luckyParams) throws Exception {

        List<LuckyConfigEntity> config = luckyConfigService.getSettings(luckyParams);

        LuckyEntity luckyEntity = luckyService.selectById(luckyParams);


        if(!luckyEntity.getEnable()){
            throw new RRException(ErrorCode.LuckyErrorCode.LUCKY_CONFIG_ERRO.getErrMsg(),
                    ErrorCode.LuckyErrorCode.LUCKY_CONFIG_ERRO.getCode());

        }
        Long integral = luckyEntity.getIntegral();
        UserInfoEntity userInfoEntity = userInfoService.selectOne( new EntityWrapper<UserInfoEntity>(new UserInfoEntity().setUserId(user.getId())));
        if(integral>userInfoEntity.getIntegral()){
            throw new RRException(ErrorCode.LuckyErrorCode.LUCKY_INTEGRAL_ERRO.getErrMsg(),
                    ErrorCode.LuckyErrorCode.LUCKY_CONFIG_ERRO.getCode());
        }
        userInfoEntity.setIntegral(-integral);
        Integer sum = userInfoService.updateByUserId(userInfoEntity);
        if(sum==0){
            throw new RRException(ErrorCode.LuckyErrorCode.LUCKY_INTEGRAL_ERRO.getErrMsg(),
                    ErrorCode.LuckyErrorCode.LUCKY_CONFIG_ERRO.getCode());
        }


        int prizeIndex = PrizeUtils.getPrizeIndex(config, integral);
        LuckyConfigEntity luckyConfigEntity = config.get(prizeIndex);

        Long roomCard = 0l;
        Long coin = 0l;
        List<Map<String,Object>> recordEntityList = new ArrayList<>();
        LuckyUserRecordEntity luckyUserRecordEntity = new LuckyUserRecordEntity();

        luckyUserRecordEntity.setLuckyName(luckyEntity.getName());
        luckyUserRecordEntity.setPropName(luckyConfigEntity.getPropName());
        luckyUserRecordEntity.setPropNum(luckyConfigEntity.getPropNum());
        luckyUserRecordEntity.setUserAccount(user.getAccount());
        luckyUserRecordEntity.setUserId(user.getId());
        luckyUserRecordEntity.setGrand(luckyConfigEntity.isGrand());
        luckyUserRecordEntity.setPropId(luckyConfigEntity.getPropId());
        luckyUserRecordEntity.setLuckyId(luckyConfigEntity.getLuckyId());
        this.insert(luckyUserRecordEntity);
        Map<String, Object> UserRecord = new HashMap<>();
        UserRecord.put("id",luckyConfigEntity.getId());
        UserRecord.put("luckyId",luckyConfigEntity.getLuckyId());
        UserRecord.put("luckyName",luckyEntity.getName());
        UserRecord.put("propId",luckyConfigEntity.getPropId());
        UserRecord.put("propNum",luckyConfigEntity.getPropNum());
        String account = user.getAccount();
        String nickName =  Base64Util.base64Decoder(user.getNickName());
//        String s = VerificationUitl.accountNoShow(account);
        UserRecord.put("nickName",nickName);

        UserRecord.put("userId",user.getId());
        UserRecord.put("grand",luckyConfigEntity.isGrand());
        UserRecord.put("propName",luckyConfigEntity.getPropName());
        recordEntityList.add(UserRecord);
        List<Map<String, Object>> luckySettings = luckyService.getLuckySettings();
        for (int i = 0; i < 3; i++) {
            Random rand = new Random();
            int randNum = rand.nextInt(luckySettings.size());
            Map<String, Object> map = luckySettings.get(randNum);
            @SuppressWarnings("unchecked")
			List<LuckyConfigEntity> configs =( List<LuckyConfigEntity>)map.get("settings");
            Long integrals= (Long) map.get("integral");
            String name =  (String) map.get("name");
            int x = PrizeUtils.getPrizeIndex(configs, integrals);
            LuckyConfigEntity luckys = configs.get(x);
            Map<String, Object> record = new HashMap<>();
            record.put("id",luckys.getId());
            record.put("luckyId",luckys.getLuckyId());
            record.put("luckyName",name);
            record.put("propId",luckys.getPropId());
            record.put("propNum",luckys.getPropNum());
            record.put("grand",luckys.isGrand());
            record.put("propName",luckys.getPropName());
            Random random = new Random();
            int vvv = random.nextInt(1000)+10;
            record.put("nickName","*****"+vvv);
            record.put("userId",0l);
            recordEntityList.add(record);
        }
        UserEntity userParam = new UserEntity();
        userParam.setId(user.getId());
        if( SysConstant.COIN_PROPID.equals(luckyConfigEntity.getPropId()) ){
            coin = luckyConfigEntity.getPropNum()*Constant.DB_COIN_RATE;
            UpdateCoinParam param = new UpdateCoinParam();
            param.setCoin(coin);
            param.setUserId(user.getId());
            userService.updateUserCoin(param);
            UserTransactionRecordEntity transactionRecord = new UserTransactionRecordEntity();
            // 新增交易记录
            transactionRecord.setType(Constant.TransactionType.REBATE.getValue());
            transactionRecord.setOrderNo(OrderNoUtil.getOrderNo());
            transactionRecord.setDetailType(Constant.TransactionDetailType.LUCKY_RECEIVE.getValue());
            transactionRecord.setUserId(user.getId());
            transactionRecord.setUserAccount(user.getAccount());
            transactionRecord.setAmount(new BigDecimal(coin/ Constant.DB_COIN_RATE));
            transactionRecord.setCoin(user.getCoin()+coin);// 上面已经修改完了用户金币
            userTransactionRecordService.insert(transactionRecord);
            // 新增稽查记录
            orderCashExamineService.bindUserinfoGiftCashExamine(user, luckyConfigEntity.getPropNum());


        }
        //查询道具名称  2就是房卡
      if (SysConstant.ROOMCARD_PROPID.equals(luckyConfigEntity.getPropId())) {
          roomCard = luckyConfigEntity.getPropNum();
          userParam.setRoomCard(luckyConfigEntity.getPropNum().longValue());
          Integer i = userDao.updateUserWalletByUserId(userParam);
          if (i > 0) {
              // 添加金币兑换房卡表（道具交易记录表）
              ShopPropRecordEntity shopPropRecord = new ShopPropRecordEntity();
              shopPropRecord.setSysPropId(luckyConfigEntity.getPropId());// 道具名称id(0表示在道具列表里不存在)
              shopPropRecord.setPropNumber(Integer.parseInt(String.valueOf(luckyConfigEntity.getPropNum())));// 兑换的道具数量
              shopPropRecord.setType(Constant.PROP_USE_TYPE_3);// 类型0：购买 1：使用 2 完成任务领取
              shopPropRecord.setProduceId(0L); // 产品id(0表示在商城产品表里不存在)
              shopPropRecord.setUserId(user.getId());// 会员id
              shopPropRecord.setUserAccount(user.getAccount());// 会员账号
              shopPropRecord.insert();
          }
      }
      //查询道具名称  -1 就是再来一次
        if(luckyConfigEntity.getPropId()==-1){
         userInfoEntity.setIntegral(integral);
            @SuppressWarnings("unused")
			Integer integer = userInfoService.updateByUserId(userInfoEntity);
            integral = 0l;
        }
        try {

            // 推送结果
            UserEntity pushMessage = new UserEntity();
            pushMessage.setId(user.getId());
            pushMessage.setCoin(coin);
            pushMessage.setRoomCard(roomCard);
            UserInfoMessage message = new UserInfoMessage(pushMessage,null);
            message.setIntegral(-integral);
            log.info("[抽奖->抽奖成功-推送消息] message {}", message);
            pushService.pushExchange(message);
        } catch (Exception e) {
            log.error("[抽奖->抽奖失败] Exception", e);
        }
        return recordEntityList;
    }

    @Override
    public List<Map<String, Object>> selectByUserId(Long userId) throws Exception {
        return this.baseMapper.selectByUserId(userId);
    }

    @Override
    public List<Map<String, Object>> selectGrand() throws Exception {
        return this.baseMapper.selectGrand();
    }

    @Override
    public List<Map<String, Object>> selectAll() throws Exception {
        return this.baseMapper.selectAll();
    }
       //抽十次
    @Override
    public List<Map<String, Object>> insertRecordTen(UserEntity user, Long luckyParams) throws Exception{
        List<LuckyConfigEntity> config = luckyConfigService.getSettings(luckyParams);

        LuckyEntity luckyEntity = luckyService.selectById(luckyParams);


        if(!luckyEntity.getEnable()){
            throw new RRException(ErrorCode.LuckyErrorCode.LUCKY_CONFIG_ERRO.getErrMsg(),
                    ErrorCode.LuckyErrorCode.LUCKY_CONFIG_ERRO.getCode());

        }
        Long integral = luckyEntity.getIntegral()*10;
        UserInfoEntity userInfoEntity = userInfoService.selectOne( new EntityWrapper<UserInfoEntity>(new UserInfoEntity().setUserId(user.getId())));
        if(integral>userInfoEntity.getIntegral()){
            throw new RRException(ErrorCode.LuckyErrorCode.LUCKY_INTEGRAL_ERRO.getErrMsg(),
                    ErrorCode.LuckyErrorCode.LUCKY_CONFIG_ERRO.getCode());
        }
        userInfoEntity.setIntegral(-integral);
        Integer sum = userInfoService.updateByUserId(userInfoEntity);
        if(sum==0){
            throw new RRException(ErrorCode.LuckyErrorCode.LUCKY_INTEGRAL_ERRO.getErrMsg(),
                    ErrorCode.LuckyErrorCode.LUCKY_CONFIG_ERRO.getCode());
        }
        Long roomCard = 0l;
        Long coin = 0l;
        UserEntity userParam = new UserEntity();
        userParam.setId(user.getId());
        String nickName =  Base64Util.base64Decoder(user.getNickName());
        List<Map<String,Object>> recordEntityList = new ArrayList<>();
        for (int i = 0; i <10; i++) {
            int prizeIndex = PrizeUtils.getPrizeIndex(config, integral);
            LuckyConfigEntity luckyConfigEntity = config.get(prizeIndex);
            LuckyUserRecordEntity luckyUserRecordEntity = new LuckyUserRecordEntity();
            luckyUserRecordEntity.setLuckyName(luckyEntity.getName());
            luckyUserRecordEntity.setPropName(luckyConfigEntity.getPropName());
            luckyUserRecordEntity.setPropNum(luckyConfigEntity.getPropNum());
            luckyUserRecordEntity.setUserAccount(user.getAccount());
            luckyUserRecordEntity.setUserId(user.getId());
            luckyUserRecordEntity.setGrand(luckyConfigEntity.isGrand());
            luckyUserRecordEntity.setPropId(luckyConfigEntity.getPropId());
            luckyUserRecordEntity.setLuckyId(luckyConfigEntity.getLuckyId());
            this.insert(luckyUserRecordEntity);
            Map<String, Object> UserRecord = new HashMap<>();
            UserRecord.put("id",luckyConfigEntity.getId());
            UserRecord.put("luckyId",luckyConfigEntity.getLuckyId());
            UserRecord.put("luckyName",luckyEntity.getName());
            UserRecord.put("propId",luckyConfigEntity.getPropId());
            UserRecord.put("propNum",luckyConfigEntity.getPropNum());
            String account = user.getAccount();
//            String s = VerificationUitl.accountNoShow(account);
            UserRecord.put("nickName",nickName);

            UserRecord.put("userId",user.getId());
            UserRecord.put("grand",luckyConfigEntity.isGrand());
            UserRecord.put("propName",luckyConfigEntity.getPropName());
            recordEntityList.add(UserRecord);

            if( SysConstant.COIN_PROPID.equals(luckyConfigEntity.getPropId()) ){
                coin += luckyConfigEntity.getPropNum()*Constant.DB_COIN_RATE;

            }
            //查询道具名称  2就是房卡
            if (SysConstant.ROOMCARD_PROPID.equals(luckyConfigEntity.getPropId())) {
                roomCard += luckyConfigEntity.getPropNum();
            }
        }
        UpdateCoinParam param = new UpdateCoinParam();
        param.setCoin(coin);
        param.setUserId(user.getId());
        userService.updateUserCoin(param);
        UserTransactionRecordEntity transactionRecord = new UserTransactionRecordEntity();
        // 新增交易记录
        transactionRecord.setType(Constant.TransactionType.REBATE.getValue());
        transactionRecord.setOrderNo(OrderNoUtil.getOrderNo());
        transactionRecord.setDetailType(Constant.TransactionDetailType.LUCKY_RECEIVE_TEN.getValue());
        transactionRecord.setUserId(user.getId());
        transactionRecord.setUserAccount(user.getAccount());
        transactionRecord.setAmount(new BigDecimal(coin/ Constant.DB_COIN_RATE));
        transactionRecord.setCoin(user.getCoin()+coin);// 上面已经修改完了用户金币
        userTransactionRecordService.insert(transactionRecord);
        // 新增稽查记录
        orderCashExamineService.bindUserinfoGiftCashExamine(user, coin);
        userParam.setRoomCard(roomCard);
        Integer n = userDao.updateUserWalletByUserId(userParam);
        if (n > 0) {
            // 添加金币兑换房卡表（道具交易记录表）
            ShopPropRecordEntity shopPropRecord = new ShopPropRecordEntity();
            shopPropRecord.setSysPropId(SysConstant.ROOMCARD_PROPID);// 道具名称id(0表示在道具列表里不存在)
            shopPropRecord.setPropNumber(Integer.parseInt(String.valueOf(roomCard)));// 兑换的道具数量
            shopPropRecord.setType(Constant.PROP_USE_TYPE_3);// 类型0：购买 1：使用 2 完成任务领取
            shopPropRecord.setProduceId(0L); // 产品id(0表示在商城产品表里不存在)
            shopPropRecord.setUserId(user.getId());// 会员id
            shopPropRecord.setUserAccount(user.getAccount());// 会员账号
            shopPropRecord.insert();
        }
//        //查询道具名称  -1 就是再来一次
//        if(luckyConfigEntity.getPropId()==-1){
//            userInfoEntity.setIntegral(integral);
//            @SuppressWarnings("unused")
//            Integer integer = userInfoService.updateByUserId(userInfoEntity);
//            integral = 0l;
//        }
        try {

            // 推送结果
            UserEntity pushMessage = new UserEntity();
            pushMessage.setId(user.getId());
            pushMessage.setCoin(coin);
            pushMessage.setRoomCard(roomCard);
            UserInfoMessage message = new UserInfoMessage(pushMessage,null);
            message.setIntegral(-integral);
            log.info("[抽奖->抽奖成功-推送消息] message {}", message);
            pushService.pushExchange(message);
        } catch (Exception e) {
            log.error("[抽奖->抽奖失败] Exception", e);
        }
        return recordEntityList;
    }
}
