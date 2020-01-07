package com.xmsy.server.zxyy.webhome.modules.app.userinfo.controller;

import com.xmsy.server.zxyy.webhome.common.annotation.SysLog;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.userinfo.entity.UserInfoEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userinfo.service.UserInfoService;
import com.xmsy.server.zxyy.webhome.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * 用户基本信息表
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-15 10:49:21
 */
@RestController
@RequestMapping("V1.0/App/userinfo")
public class AppUserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 信息
     */
    @GetMapping ("/info")
    public R info(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        Long userId = Long.valueOf(JwtUtil.getUserId(token));
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setUserId(userId);
//        UserInfoEntity userInfo = userInfoService.selectOne(new EntityWrapper<>(userInfoEntity));
        List<Map<String, Object>> userInfo = userInfoService.selectByUserId(userId);
        return R.ok().put("data", userInfo);
    }


    /**
     * 修改
     */
    @SysLog("修改用户信息")
    @RequestMapping("/update")
//    @RequiresPermissions("userinfo:userinfo:update")
    public R update(@RequestBody Map<String,Object> map,HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        userInfoService.UpdateByUser(map,token);
        return R.ok().put("data",true);
    }


}
