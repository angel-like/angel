package com.xmsy.server.zxyy.webhome.modules.app.userquestion.service;


import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.app.userquestion.entity.UserQuestionEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户问题表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-15 16:56:34
 */
public interface UserQuestionService extends IService<UserQuestionEntity> {
    List<Map<String,Object>> selectAll();

}

