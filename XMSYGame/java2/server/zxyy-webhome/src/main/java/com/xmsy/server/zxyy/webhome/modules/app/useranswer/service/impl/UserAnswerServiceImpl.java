package com.xmsy.server.zxyy.webhome.modules.app.useranswer.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.util.securitytools.MD5Util;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.modules.app.useranswer.dao.UserAnswerDao;
import com.xmsy.server.zxyy.webhome.modules.app.useranswer.entity.UserAnswer;
import com.xmsy.server.zxyy.webhome.modules.app.useranswer.entity.UserAnswerEntity;
import com.xmsy.server.zxyy.webhome.modules.app.useranswer.service.UserAnswerService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.dao.UserDao;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userpassword.dao.UserPasswordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("userAnswerService")
public class UserAnswerServiceImpl extends ServiceImpl<UserAnswerDao, UserAnswerEntity> implements UserAnswerService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserPasswordDao userPasswordDao;


    @Override
    public String getPassWord(UserAnswer userAnswer) {
        String passWord = null;
        String userAccount = userAnswer.getUserAccount();
        UserEntity userEntity = new UserEntity();
        UserEntity user = userDao.selectOne(userEntity.setAccount(userAccount));
        if(user==null){
            throw new RRException(ErrorCode.UserErrorCode.USER_ACCOUNT_INVALID.getErrMsg(),
                    ErrorCode.UserErrorCode.USER_ACCOUNT_INVALID.getCode());
        }
        Long id = user.getId();
        UserAnswerEntity userAnswerEntity = new UserAnswerEntity(userAnswer);
        userAnswerEntity.setUserId(id);
        UserAnswerEntity UserAnswerData = this.baseMapper.selectOne(userAnswerEntity);
        if(UserAnswerData==null){
            throw new RRException(ErrorCode.UserErrorCode.USER_ANSWER_ERROR.getErrMsg(),
                    ErrorCode.UserErrorCode.USER_ANSWER_ERROR.getCode());
        }
        String s = userPasswordDao.selectByUserId(id);
          passWord = MD5Util.MD5(s);
        return passWord;
    }
}