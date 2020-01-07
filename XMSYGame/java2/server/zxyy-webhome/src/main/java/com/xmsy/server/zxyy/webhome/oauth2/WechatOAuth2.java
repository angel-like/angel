package com.xmsy.server.zxyy.webhome.oauth2;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.define.constant.ResultDef;
import com.xmsy.server.zxyy.webhome.constant.HallUrlConstant;
import com.xmsy.server.zxyy.webhome.constant.ThirdPartyDef;
import com.xmsy.server.zxyy.webhome.modules.manager.sysconfig.service.SysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service("wechatOAuth2")
public class WechatOAuth2 extends OAuth2 {

    @Autowired
    private SysConfigService sysConfigService;

    /*----------------------------Oauth接口--------------------------------------*/

    // 获取用户微信accessToken
    @Override
    public String getAccessToken(String code) {
        Map<String, Object> paramMap = new HashMap<>();
        String appKey = sysConfigService.selectByParamKey(ThirdPartyDef.WECHAT, ThirdPartyDef.WECHAT_APPKEY);
        String appSecret = sysConfigService.selectByParamKey(ThirdPartyDef.WECHAT, ThirdPartyDef.WECHAT_APPSECRET);
//        String appKey ="wx8e7815a1c2ae63f8";
//        String appSecret="e12f36ee02d4d76917ba46d8e45aaa44";

        paramMap.put("appid", appKey);
        paramMap.put("secret", appSecret);
        paramMap.put("grant_type", "authorization_code");
        paramMap.put("code", code);
        log.debug("[getAccessToken] appid {} secret {}", appKey, appSecret);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
        try {
            return HttpUtil.get(url, paramMap);
        } catch (Exception e) {
            log.error("[WechatOAuth2]->getAccessToken: code={}", code, e);
        }
        return null;
    }

    // 获取用户信息
    @Override
    public String getUser(String access_token, String openid) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("access_token", access_token);
        paramMap.put("openid", openid);
        String url = "https://api.weixin.qq.com/sns/userinfo";
        try {
            return HttpUtil.get(url, paramMap);
        } catch (Exception e) {
            log.error("[WechatOAuth2]->getUser:access_token={},openid={}", access_token, openid, e);
        }
        return null;
    }

    @Override
    public boolean verificationAccessToken(String access_token, String openid) {
        String url = "https://api.weixin.qq.com/sns/auth";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("access_token", access_token);
        paramMap.put("openid", openid);
        try {
            JSONObject response = JSON.parseObject(HttpUtil.get(url, paramMap));
            if (ResultDef.OAUTH_SUCCESS == response.getIntValue("errcode")) {
                return true;
            }
        } catch (Exception e) {
            log.error("[WechatOAuth2]->verificationAccessToken:access_token={},openid={}", access_token, openid, e);
        }
        return false;
    }

    @Override
    public String refreshAccessToken(String refresh_token) {
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("refresh_token", refresh_token);
        paramMap.put("appid", HallUrlConstant.getWechat_appId());
        paramMap.put("grant_type", refresh_token);
        try {
            return HttpUtil.get(url, paramMap);
        } catch (Exception e) {
            log.error("[WechatOAuth2]->refreshAccessToken:refresh_token={},appid={}", refresh_token,
                    HallUrlConstant.getWechat_appId(), e);
        }
        return null;
    }
}
