package com.xmsy.network.pay.btpay.query;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.network.pay.btpay.def.Config;
import com.xmsy.network.pay.btpay.utils.AES128Util;
import com.xmsy.network.pay.paybase.param.QueryOrderParam;
import com.xmsy.network.pay.paybase.query.QueryOrder;
import com.xmsy.network.pay.paybase.result.QueryOrderResult;
import com.xmsy.network.pay.paybase.utils.Md5Util;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * .回调处理
 * 
 * @author aleng
 * @date 2018年11月2日
 * @version 1.0
 */
@Slf4j
@Component
public class BtPayQueryHandler implements QueryOrder {

	@Override
	public QueryOrderResult getOrder(QueryOrderParam param) {
		JSONObject requestParam = new JSONObject();
		requestParam.put("user_id", Config.APPID);
		requestParam.put("trade_no", param.getBillNo());
		JSONObject data = new JSONObject();
        data.put("orderId",param.getBillNo());
        String dataEn = AES128Util.encryptBase64(data.toJSONString(), Config.APPSECRET);
        JSONObject req = new JSONObject();
        Long time = new Date().getTime();
        req.put("username",Config.APPID);
        req.put("data",dataEn);
        req.put("timestamp",time);
        StringBuilder sign = new StringBuilder();
        sign.append(Config.APPID).append(time).append(dataEn).append(Config.APPSECRET);
        req.put("sign", Md5Util.md5(sign.toString()));
        System.out.println(req.toJSONString());
        String result = HttpUtil.post("http://www.btcode123.com/pay/api/queryOrder",req.toJSONString());
        JSONObject r = JSON.parseObject(result);
        if("0".equals(r.get("code").toString())){
            String info = r.get("orderInfo").toString();
            JSONObject orderInfo = JSON.parseObject(info);
            orderInfo.get("status");
            orderInfo.get("submitAmount");
            orderInfo.get("orderId");
            orderInfo.get("outerOrderId");
            log.info("[BtPayQueryHandler] jsonResult {}",orderInfo);
        }
	        
		
		return null;
	}
}
