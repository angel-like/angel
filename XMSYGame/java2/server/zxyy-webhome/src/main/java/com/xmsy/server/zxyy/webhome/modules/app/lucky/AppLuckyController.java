package com.xmsy.server.zxyy.webhome.modules.app.lucky;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.app.lucky.param.LuckyParams;
import com.xmsy.server.zxyy.webhome.modules.manager.lucky.service.LuckyService;
import com.xmsy.server.zxyy.webhome.modules.manager.luckyuserrecord.service.LuckyUserRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.userbetrecord.service.UserBetRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.userinfo.entity.UserInfoEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userinfo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("V1.0/App")
public class AppLuckyController {
//    @Autowired
//    private LuckyConfigService  luckyConfigService;
    @Autowired
    private LuckyService    luckyService;
    @Autowired
    private LuckyUserRecordService luckyUserRecordService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserBetRecordService userBetRecordService;

    /**
     * 获取抽奖配置信息
     *
     * @throws Exception
     */
    @GetMapping("/getLuckySettings")
    public R getLuckySettings(@RequestHeader("token") String token)
            throws Exception {
        UserEntity entity = userService.selectUserForToken(token);
        if (entity == null || entity.getId() == null) {
            throw new RRException(ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getErrMsg(),
                    ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getCode());
        }
        Map<String, Object> map = new HashMap<>();

        List<Map<String, Object>> luckySettings = luckyService.getLuckySettings();
        UserInfoEntity userInfoEntity = userInfoService.selectOne(new EntityWrapper<>(new UserInfoEntity().setUserId(entity.getId())));
        Long tomorrowIntegral = userBetRecordService.selectRecord(entity.getId());
        map.put("luckySettings",luckySettings);
        map.put("tomorrowIntegral",tomorrowIntegral);
        map.put("integral",userInfoEntity.getIntegral());
        return R.ok().put("data", map);
    }


    /**
     * 抽奖
     *
     * @throws Exception
     */
    @PostMapping("/userLucky")
    public R userLucky( @RequestBody LuckyParams luckyParams, @RequestHeader("token") String token)
            throws Exception {
        UserEntity entity = userService.selectUserForToken(token);;
        if (entity == null || entity.getId() == null) {
            throw new RRException(ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getErrMsg(),
                    ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getCode());
        }
        Long luckyId = Long.valueOf(luckyParams.getLuckyId());
        List<Map<String,Object>> recordEntityList = luckyUserRecordService.insertRecordTwo(entity, luckyId);
        return R.ok().put("data", recordEntityList);
    }


    /**
     * 抽奖
     *
     * @throws Exception
     */
    @PostMapping("/userLuckyTen")
    public R userLuckyTen( @RequestBody LuckyParams luckyParams, @RequestHeader("token") String token)
            throws Exception {
        UserEntity entity = userService.selectUserForToken(token);;
        if (entity == null || entity.getId() == null) {
            throw new RRException(ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getErrMsg(),
                    ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getCode());
        }
        Long luckyId = Long.valueOf(luckyParams.getLuckyId());
        List<Map<String,Object>> recordEntityList = luckyUserRecordService.insertRecordTen(entity, luckyId);
        return R.ok().put("data", recordEntityList);
    }


    /**
     * 获取个人抽奖记录
     *
     * @throws Exception
     */
    @GetMapping("/getRecord")
    public R getRecord(@RequestHeader("token") String token) throws Exception {
        UserEntity entity = userService.selectUserForToken(token);
        if (entity == null || entity.getId() == null) {
            throw new RRException(ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getErrMsg(),
                    ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getCode());
        }
        List<Map<String, Object>> userRecord = luckyUserRecordService.selectByUserId(entity.getId());
        List<Map<String, Object>> grandRecord = luckyUserRecordService.selectGrand();
        List<Map<String, Object>> recordList = luckyUserRecordService.selectAll();
        Map<String, Object> map = new HashMap<>();
        map.put("userRecord",userRecord);
        map.put("grandRecord",grandRecord);
        map.put("recordList",recordList);
        return R.ok().put("data", map);
    }
}
