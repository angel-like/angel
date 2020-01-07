package com.xmsy.server.zxyy.webhome.modules.manager.webhomefriendshiptemplate.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.webhomefriendshiptemplate.entity.WebhomeFriendshipTemplateEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 友情链接内容
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-03-13 15:31:14
 */
@Mapper
public interface WebhomeFriendshipTemplateDao extends BaseMapper<WebhomeFriendshipTemplateEntity> {

	List<WebhomeFriendshipTemplateEntity> selectTemplateList(@Param("friendshipId") Long friendshipId);

}
