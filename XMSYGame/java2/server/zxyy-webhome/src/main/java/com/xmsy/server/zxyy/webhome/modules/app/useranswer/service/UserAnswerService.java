package com.xmsy.server.zxyy.webhome.modules.app.useranswer.service;


import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.useranswer.entity.UserAnswer;
import com.xmsy.server.zxyy.webhome.modules.app.useranswer.entity.UserAnswerEntity;

/**
 * 用户答案表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-15 16:56:34
 */
public interface UserAnswerService extends IService<UserAnswerEntity> {



    String   getPassWord(UserAnswer userAnswer);

}

