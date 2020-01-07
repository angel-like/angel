package com.xmsy.server.zxyy.manager.modules.manager.userquestion.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.userquestion.entity.UserQuestionEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户问题表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-15 16:56:34
 */
@Mapper
public interface UserQuestionDao extends BaseMapper<UserQuestionEntity> {
	
}
