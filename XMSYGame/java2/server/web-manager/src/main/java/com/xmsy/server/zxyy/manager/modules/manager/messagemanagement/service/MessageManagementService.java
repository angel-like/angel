package com.xmsy.server.zxyy.manager.modules.manager.messagemanagement.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.manager.messagemanagement.entity.MessageManagementEntity;
import com.xmsy.server.zxyy.manager.modules.manager.messageuser.param.MessageRequestVo;

/**
 * 消息管理
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-02 16:39:41
 */
public interface MessageManagementService extends IService<MessageManagementEntity> {
	/**
	 * 查询列表（分页）
	 * @param params
	 * @return
	 */
    PageUtils queryPage(Map<String, Object> params);
    /**
     * 保存会员站内信
     * @param messageManagement
     * @return
     */
    boolean save( MessageManagementEntity messageManagement);
    /**
     * 保存会员站内信
     * @param messageManagement
     * @return
     */
    boolean saveUserMessage( Long activityId,boolean receive,int activityType,Long messageModelId,Long userId ,String account);
    
    /**
     * 保存管理员站内信
     * @param messageManagement
     * @return
     */
    boolean saveAdminMessage( MessageManagementEntity messageManagement);
    
    /**
     * 根据userId查询会员站内信
     * @param userId
     * @return
     */
    List<Map<String, Object>> findMessageById(Long userId);
    
    /**
     * 根据userId查询管理员站内信
     * @param userId
     * @return
     */
    List<Map<String, Object>> findAdminMessageById(Long userId);
    
    /**
     * 根据userId查询站内信
     * 带分页
     * @param userId
     * @return
     */
    Page<Map<String, Object>> findMessagePage(PageParam pageParam,MessageRequestVo requestVo);
    
    /**
	 * 计算未读的个数
	 * @param vo
	 * @return
	 */
	Integer countUnreadNumber(MessageRequestVo vo);
}

