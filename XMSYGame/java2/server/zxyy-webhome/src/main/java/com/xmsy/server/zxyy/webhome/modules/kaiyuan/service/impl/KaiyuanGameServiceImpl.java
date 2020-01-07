package com.xmsy.server.zxyy.webhome.modules.kaiyuan.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.webhome.constant.HallUrlConstant;
import com.xmsy.server.zxyy.webhome.modules.kaiyuan.server.KaiyuanHttpRequestInterface;
import com.xmsy.server.zxyy.webhome.modules.kaiyuan.service.KaiyuanGameService;
import com.xmsy.server.zxyy.webhome.modules.manager.kygameloginrecord.entity.KyGameloginRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.kygameloginrecord.service.KyGameloginRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("kaiyuanGameService")
public class KaiyuanGameServiceImpl implements KaiyuanGameService {
    private  static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS ");

    @Autowired
    KyGameloginRecordService kyGameloginRecordService;

    @Override
    public JSONObject gameLogin(UserEntity entity, String ip, Long gameId) throws Exception {
        Date date = new Date();
        StringBuffer sb = new StringBuffer();
        sb.append("s=0").append("&");
        sb.append("account="+entity.getId()+"_"+HallUrlConstant.getPROVIDER_CODE()).append("&");
        sb.append("money="+new BigDecimal(entity.getCoin()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP)).append("&");

        String orderNo = HallUrlConstant.getAGENT()+sdf.format(date).trim()+entity.getAccount().trim();
        sb.append("orderid="+orderNo).append("&");
        sb.append("ip="+ip).append("&");
        sb.append("lineCode="+HallUrlConstant.getLINECODE()).append("&");
        sb.append("KindID="+gameId);

        log.info("开元游戏登录请求参数 {} ", sb.toString());
        JSONObject jsonObject = KaiyuanHttpRequestInterface.sendToKaiyuan(sb.toString(),"channelHandle");
        if(jsonObject.getJSONObject("d").getIntValue("code") == 0){
            KyGameloginRecordEntity kyGameloginRecordEntity = new KyGameloginRecordEntity();
            List<KyGameloginRecordEntity> list = kyGameloginRecordService.selectList(new EntityWrapper<KyGameloginRecordEntity>(new KyGameloginRecordEntity().setUserId(entity.getId())));
            if (list != null && list.size() >0){
                kyGameloginRecordEntity =list.get(0);
            }
            kyGameloginRecordEntity.setGameId(gameId);
            kyGameloginRecordEntity.setAccount(entity.getAccount());
            kyGameloginRecordEntity.setUserId(entity.getId());
            kyGameloginRecordEntity.setOrderId(orderNo);
            kyGameloginRecordEntity.setGameUrl(jsonObject.getJSONObject("d").getString("url"));
            kyGameloginRecordEntity.setStatus(1L);
            kyGameloginRecordService.insertOrUpdate(kyGameloginRecordEntity);

        }
        return jsonObject;
    }

    @Override
    public JSONObject logout(UserEntity entity) throws Exception {
//        Date date = new Date();
        StringBuffer sb = new StringBuffer();
        sb.append("s=8").append("&");
//        sb.append("account="+entity.getAccount());
        sb.append("account="+entity.getId()+"_"+HallUrlConstant.getPROVIDER_CODE()).append("&");
        log.info("开元游戏退出请求参数  {}", sb.toString());
        return KaiyuanHttpRequestInterface.sendToKaiyuan(sb.toString(),"channelHandle");
    }

    @Override
    public JSONObject clearMoney(UserEntity entity,String amount) throws Exception {
        Date date = new Date();
        StringBuffer sb = new StringBuffer();
        sb.append("s=3").append("&");
//       sb.append("account="+entity.getAccount()).append("&");
        sb.append("account="+entity.getId()+"_"+HallUrlConstant.getPROVIDER_CODE()).append("&");
        sb.append("money=").append(amount).append("&");
        String orderNo = HallUrlConstant.getAGENT()+sdf.format(date).trim()+entity.getAccount().trim();
        sb.append("orderid="+orderNo);
        log.info("开元游戏下分请求参数  {}", sb.toString());
        return KaiyuanHttpRequestInterface.sendToKaiyuan(sb.toString(),"channelHandle");

    }


    @Override
    public JSONObject queryMoney(UserEntity entity) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("s=7").append("&");
 //       sb.append("account="+entity.getAccount());
        sb.append("account="+entity.getId()+"_"+HallUrlConstant.getPROVIDER_CODE()).append("&");
        log.info("开元游戏查询玩家分请求参数  {}", sb.toString());
        JSONObject returnJson = KaiyuanHttpRequestInterface.sendToKaiyuan(sb.toString(),"channelHandle");


        return  returnJson;

    }


    @Override
    public JSONObject getRecord() throws Exception {
        Long timestamp =System.currentTimeMillis();
        StringBuffer sb = new StringBuffer();
        sb.append("s=6").append("&");
        sb.append("startTime="+(timestamp-4*60*1000)).append("&");
        sb.append("endTime="+timestamp);
        JSONObject returnJson = KaiyuanHttpRequestInterface.sendToKaiyuan(sb.toString()).getJSONObject("d");
        return  returnJson;

    }

    @Override
    public JSONObject getOnlineStatus(UserEntity entity) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("s=5").append("&");
 //       sb.append("account="+entity.getAccount());
        sb.append("account="+entity.getId()+"_"+HallUrlConstant.getPROVIDER_CODE()).append("&");
        JSONObject returnJson = KaiyuanHttpRequestInterface.sendToKaiyuan(sb.toString(),"channelHandle");


        return  returnJson;
    }


}