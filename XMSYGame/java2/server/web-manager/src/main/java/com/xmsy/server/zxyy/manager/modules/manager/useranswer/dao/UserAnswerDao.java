package com.xmsy.server.zxyy.manager.modules.manager.useranswer.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.useranswer.entity.UserAnswerEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户答案表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-15 16:56:34
 */
@Mapper
public interface UserAnswerDao extends BaseMapper<UserAnswerEntity> {
	
}
