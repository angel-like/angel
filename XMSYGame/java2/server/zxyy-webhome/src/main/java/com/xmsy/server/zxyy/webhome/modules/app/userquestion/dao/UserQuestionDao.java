package com.xmsy.server.zxyy.webhome.modules.app.userquestion.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.webhome.modules.app.userquestion.entity.UserQuestionEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 用户问题表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-15 16:56:34
 */
@Mapper
public interface UserQuestionDao extends BaseMapper<UserQuestionEntity> {
   List<Map<String,Object>> selectAll();
	
}
