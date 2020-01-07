package com.xmsy.server.zxyy.webhome.modules.manager.userinfo.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.DateUtils;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.Query;
import com.xmsy.server.zxyy.webhome.modules.manager.sysregisternecessary.dao.SysRegisterNecessaryDao;
import com.xmsy.server.zxyy.webhome.modules.manager.sysregisternecessary.entity.SysRegisterNecessaryEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.dao.UserDao;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userinfo.dao.UserInfoDao;
import com.xmsy.server.zxyy.webhome.modules.manager.userinfo.entity.UserInfoEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userinfo.entity.result.UserPushInfo;
import com.xmsy.server.zxyy.webhome.modules.manager.userinfo.service.UserInfoService;
import com.xmsy.server.zxyy.webhome.utils.Base64Util;
import com.xmsy.server.zxyy.webhome.utils.JwtUtil;
import com.xmsy.server.zxyy.webhome.utils.VerificationUitl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service("userInfoService")
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfoEntity> implements UserInfoService {
    @Autowired
    private SysRegisterNecessaryDao sysRegisterNecessaryDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private  UserInfoDao userInfoDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<UserInfoEntity> page = this.selectPage(new Query<UserInfoEntity>(params).getPage(),
                new EntityWrapper<UserInfoEntity>());

        return new PageUtils(page);
    }

    @Override
    public Integer updateUserBaseInfo(UserInfoEntity entity) {
        if (!StringUtils.isBlank(entity.getUserName())) {
            UserEntity user = new UserEntity();
            user.setId(entity.getUserId());
            user.setUserName(entity.getUserName());
            user.updateById();
        }

        return this.baseMapper.updateUserBaseInfo(entity);
    }

    @Override
    public Integer deleteUserInfoByUserId(Long userId) {
        return this.baseMapper.deleteUserInfoByUserId(userId);
    }

    @Override
    public Integer updateByUserId(UserInfoEntity entity) {
        return this.baseMapper.updateByUserId(entity);
    }

    @Override
    public List<UserPushInfo> findUserInfoListByAccount(List<String> accountList) {
        return this.baseMapper.findUserInfoListByAccount(accountList);
    }

    @Override
    public List<String> findUserInfoListByhierarchyId(List<String> hierarchyIds) {
        return this.baseMapper.findUserInfoListByhierarchyId(hierarchyIds);
    }

    @Override
    public List<Map<String, Object>> selectByUserId(Long userId) {
        List<Map<String, Object>> list = new ArrayList<>();
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setUserId(userId);
        UserEntity userEntity = userDao.selectById(userId);
        String nickName = Base64Util.base64Decoder(userEntity.getNickName());
        Map<String, Object> nikeNameMap = new HashMap<>();
        nikeNameMap.put("name", "昵称");
        nikeNameMap.put("show", true);
        nikeNameMap.put("necessary", true);
        nikeNameMap.put("value", nickName);
        nikeNameMap.put("remark", "nickName");
        list.add(nikeNameMap);
        UserInfoEntity entity = this.baseMapper.selectOne(userInfoEntity);
        BeanMap beanMap = BeanMap.create(entity);
        List<SysRegisterNecessaryEntity> sysRegisterNecessaryEntities = sysRegisterNecessaryDao.selectList(new EntityWrapper<>(new SysRegisterNecessaryEntity().setShow(true)));
        for (SysRegisterNecessaryEntity sysRegisterNecessaryEntity : sysRegisterNecessaryEntities) {

            String remark = sysRegisterNecessaryEntity.getRemark();
            Map<String, Object> map = new HashMap<>();
            map.put("name", sysRegisterNecessaryEntity.getName());
            map.put("show", sysRegisterNecessaryEntity.getShow());
            map.put("necessary", sysRegisterNecessaryEntity.getNecessary());
            map.put("remark", remark);
//		Object o = beanMap.get(remark);
          if("userPhone".equals(remark)||"userIdentity".equals(remark))
          {
              continue;
          }
            if ("userBirthday".equals(remark)) {
                String s;
                Date date = (Date) beanMap.get(remark);
                if (date == null) {
                    s = "";
                } else {
                    s = DateUtils.format(date, "yyyy-MM-dd");
                }
                map.put("value", s);

            } else {
                Object o = beanMap.get(remark);
                if (o == null) {
                    map.put("value", "");
                } else {
                    map.put("value", beanMap.get(remark));
                }
            }
            list.add(map);
        }
        return list;
    }

    @Override
    public void UpdateByUser(Map<String, Object> beanMap, String token) {
        Long userId = Long.valueOf(JwtUtil.getUserId(token));
        UserEntity userEntity = userDao.selectById(userId);
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        String nickName = (String) beanMap.get("nickName");
        if (!VerificationUitl.nickNameVerification(nickName)) {
            throw new RRException(ErrorCode.UserErrorCode.NICK_NAME_ERRO.getErrMsg(),
                    ErrorCode.UserErrorCode.NICK_NAME_ERRO.getCode());
        }
        String nikeName = Base64Util.base64Encoder((String) beanMap.get("nickName"));
        userEntity.setNickName(nikeName);
        List<String> keyList = new ArrayList<>();
//		BeanMap beanMap = BeanMap.create(userInfo);
        userDao.updateById(userEntity);
        userInfoEntity.setUserId(userId);
        UserInfoEntity user = this.selectOne(new EntityWrapper<>(userInfoEntity));
        Map<String, Object> newMap = new HashMap<>();
        List<SysRegisterNecessaryEntity> sysRegisterNecessaryEntities = sysRegisterNecessaryDao.selectList(new EntityWrapper<>(new SysRegisterNecessaryEntity().setShow(true)));
        for (SysRegisterNecessaryEntity sysRegisterNecessaryEntity : sysRegisterNecessaryEntities) {
            String remark = sysRegisterNecessaryEntity.getRemark();
            String validationRule = sysRegisterNecessaryEntity.getValidationRule();
            String validationDescribe = sysRegisterNecessaryEntity.getValidationDescribe();
            Boolean necessary = sysRegisterNecessaryEntity.getNecessary();
            if ("userBirthday".equals(remark)) {
                String o = (String) beanMap.get(remark);
                if (necessary) {
                    if (o == null || "".equals(o)) {
                        throw new RRException(sysRegisterNecessaryEntity.getName() + "不能为空", 999990);
                    } else if (!VerificationUitl.Verification(o, validationRule)) {
                        throw new RRException(validationDescribe, 99999);
                    }
                }
                if (!necessary) {
                    if (o != null && !"".equals(o)) {
                        if (!VerificationUitl.Verification(o, validationRule)) {
                            throw new RRException(validationDescribe, 99999);
                        }
                    }
                }

                Date date = DateUtils.stringToDate(o, "yyyy-MM-dd");
                newMap.put(remark, date);
                keyList.add(remark);
//                if (o == null || "".equals(o)){
//                    newMap.put("userBirthday",null);
//                }

            } else {
                String o = (String) beanMap.get(remark);
                if (necessary) {
                    if (o == null || "".equals(o)) {
                        throw new RRException(sysRegisterNecessaryEntity.getName() + "不能为空", 999990);
                    } else if (!VerificationUitl.Verification(o, validationRule)) {
                        throw new RRException(validationDescribe, 99999);
                    }
                }
                if (!necessary) {
                    if (o != null && !"".equals(o)) {
                        if (!VerificationUitl.Verification(o, validationRule)) {
                            throw new RRException(validationDescribe, 99999);
                        }
                    }
                }

                newMap.put(remark, o);
//                if (o == null || "".equals(o)){
//                    newMap.put(remark,"");
//                }
                keyList.add(remark);
            }
        }
        BeanMap userInfoMap = BeanMap.create(user);
//        String string = JSONObject.toJSONString(newMap);
//        UserInfoEntity userInfo = JSONObject.parseObject(string, UserInfoEntity.class);

        List<String> remark = sysRegisterNecessaryDao.getRemark();
        remark.removeAll(keyList);
        for (String s : remark) {
            newMap.put(s,userInfoMap.get(s));
        }
        newMap.put("id",user.getId());
//        userInfo.setId(user.getId());
        userInfoDao.updateUserInfo(newMap);
//        this.baseMapper.update(userInfo, new EntityWrapper<>(userInfo));
    }

}
