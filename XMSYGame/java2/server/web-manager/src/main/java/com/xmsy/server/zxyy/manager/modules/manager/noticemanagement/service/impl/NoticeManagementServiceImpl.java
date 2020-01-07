package com.xmsy.server.zxyy.manager.modules.manager.noticemanagement.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.Query;
import com.xmsy.server.zxyy.manager.modules.manager.messageuser.param.MessageRequestVo;
import com.xmsy.server.zxyy.manager.modules.manager.noticemanagement.dao.NoticeManagementDao;
import com.xmsy.server.zxyy.manager.modules.manager.noticemanagement.entity.NoticeManagementEntity;
import com.xmsy.server.zxyy.manager.modules.manager.noticemanagement.service.NoticeManagementService;
import com.xmsy.server.zxyy.manager.modules.manager.user.dao.UserDao;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.utils.JwtUtil;

@Transactional
@Service("noticeManagementService")
public class NoticeManagementServiceImpl extends ServiceImpl<NoticeManagementDao, NoticeManagementEntity> implements NoticeManagementService {

	@Autowired
	private UserDao userDao;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        NoticeManagementEntity entity=new NoticeManagementEntity();
    	if(params !=null) {
    		if(params.get("key")!=null) {
    			entity.setTitle(params.get("key").toString());
    		}
    	}
    	Wrapper<NoticeManagementEntity> wrapper = new EntityWrapper<NoticeManagementEntity>(entity);
    	String sort=params.get("sort")!=null?params.get("sort").toString():"";
    	if (!StringUtils.isEmpty(sort)) {
    		String direction=params.get("direction")!=null?params.get("direction").toString():"";
    		if ("desc".equalsIgnoreCase(direction)) {
    			wrapper.orderDesc(Arrays.asList(sort.split(",")));
    		} else {
    			wrapper.orderAsc(Arrays.asList(sort.split(",")));
    		}
		}
    	Page<NoticeManagementEntity> page = this.selectPage(
                new Query<NoticeManagementEntity>(params).getPage(),
                wrapper
        );

        return new PageUtils(page);
    }

	@Override
	public List<Map<String, Object>> findMemberNoticeListByEffect(String token) {
		Long userId=Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = this.userDao.selectById(userId);
		if(user==null || user.getId()<=0) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		MessageRequestVo requestVo=new MessageRequestVo();
    	requestVo.setHierarchyId(user.getHierarchyId());
//    	requestVo.setUserId(user.getId());
//    	requestVo.setUserAccount(user.getAccount());
		return this.baseMapper.findNoticeListByEffect(requestVo);
	}

}
