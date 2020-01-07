package com.xmsy.server.zxyy.manager.modules.manager.messageuser.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.manager.messageuser.entity.MessageUserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.messageuser.param.MessageRequestVo;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;


/**
 * 消息管理
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-02 16:39:41
 */
public interface MessageUserService extends IService<MessageUserEntity> {

    PageUtils queryPage(Map<String, Object> params);
    /**
     * 保存会员站内信和会员关系
     * @param token
     * @param entity
     */
    int save(String token,MessageUserEntity entity);
    /**
     * 批量设置会员站内信已读
     * @param token
     * @param messageIds
     */
    void setReadBatch(String token);
    /**
     * 批量设置会员站内信删除
     * @param token
     * @param messageIds
     */
    void setDeleteBatch(String token,List<Long> messageIds);
    
    //**************************************************************
    
    /**
     * 保存管理员站内信和会员关系
     * @param entity
     */
    void save(MessageUserEntity entity);
    /**
     * 批量设置管理员站内信已读
     * @param user
     * @param messageIds
     */
    void setReadBatchForAdmin(SysUserEntity user,List<Long> messageIds);
    /**
     * 批量设置管理员站内信删除
     * @param user
     * @param messageIds
     */
    void setDeleteBatchForAdmin(SysUserEntity user,List<Long> messageIds);
    
    /**
	 * 根据消息Id和userid 查询
	 * @param messageId
	 * @param userId
	 * @return
	 */
     MessageUserEntity queryByMessageId(Long messageId ,Long userId);

    Page<Map<String, Object>> findMessagePage(PageParam pageParam, MessageRequestVo requestVo);
    
}

