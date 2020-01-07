package com.xmsy.server.zxyy.webhome.modules.app.headframe.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.modules.manager.headframe.entity.HeadframeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.headframe.service.HeadframeService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.userheadframe.entity.UserHeadframeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userheadframe.service.UserHeadframeService;



@Service("appHeadFrameService")
public class AppHeadFrameService {
	@Autowired
	private UserService userService;
	@Autowired
	private HeadframeService headframeService;
	@Autowired
	private UserHeadframeService userHeadframeService;
	/**
	 * 	获取头像框所需要的json数据
	 * @return
	 */
	public List<Map<String,Object>> getListHeadFrameData(List<HeadframeEntity> listHeadframe,List<UserHeadframeEntity> listUserHeadframe){
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();	
		Map<String,Object> mapTotal = null;//存放所有的
		Map<String,Object> mapCondition = null;//存放condition里的数据
		for(HeadframeEntity headframeEntity:listHeadframe) {
			mapTotal=new HashMap<String,Object>();
			mapCondition=new HashMap<String,Object>();
			mapCondition.put("type",headframeEntity.getType());
			mapCondition.put("typeId", headframeEntity.getTypeId());
			mapTotal.put("condition", mapCondition);
			mapTotal.put("id", headframeEntity.getId());
			boolean lock=true;//为了判断此头像是否被锁定，去用户头像框关系表里查询是否存在
			if(Constant.HeadFrameType.VIP.getValue().equals(headframeEntity.getType())
					&&headframeEntity.getTypeId()==0) {
				lock=false;
				mapTotal.put("lock", lock);
				resultList.add(mapTotal);
				continue;
			}
			for(UserHeadframeEntity userHeadframe:listUserHeadframe){
				if(userHeadframe.getHeadframeId()==headframeEntity.getId()) {
					lock=false;
					break;
				}
			}
			mapTotal.put("lock", lock);
			resultList.add(mapTotal);
		}
		return resultList;
	}
	/**
	 * 修改用户对象里的  头像框id
	 * @param user
	 * @param headframeId
	 * @return
	 */
	public Boolean updateUserHeadframe(UserEntity user,Long headframeId) {
		HeadframeEntity headframeEntity = headframeService.selectById(headframeId);
		if(headframeEntity==null || headframeEntity.getId()==null) {
			throw new RRException(ErrorCode.UserErrorCode.HEADFRAME_ERROR.getErrMsg(),
					ErrorCode.UserErrorCode.HEADFRAME_ERROR.getCode());
		}
		if(Constant.HeadFrameType.VIP.getValue().equals(headframeEntity.getType())
				&&headframeEntity.getTypeId()==0) {
			user.setHeadframeId(headframeId);
			return userService.updateById(user);
		}
		UserHeadframeEntity userHeadframeEntity=new UserHeadframeEntity();
		userHeadframeEntity.setHeadframeId(headframeId);
		userHeadframeEntity.setUserId(user.getId());
		Wrapper<UserHeadframeEntity> wrapper=new EntityWrapper<UserHeadframeEntity>(userHeadframeEntity);
		if(userHeadframeService.selectCount(wrapper)==0) {
			throw new RRException(ErrorCode.UserErrorCode.USER_NOT_HEADFRAME_ERROR.getErrMsg(),
					ErrorCode.UserErrorCode.USER_NOT_HEADFRAME_ERROR.getCode());
		}
		user.setHeadframeId(headframeId);
		return userService.updateById(user);
	}
}
