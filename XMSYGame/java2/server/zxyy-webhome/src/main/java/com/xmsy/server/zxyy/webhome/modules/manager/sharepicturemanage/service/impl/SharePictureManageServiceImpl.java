package com.xmsy.server.zxyy.webhome.modules.manager.sharepicturemanage.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.modules.manager.sharepicturemanage.dao.SharePictureManageDao;
import com.xmsy.server.zxyy.webhome.modules.manager.sharepicturemanage.entity.SharePictureManageEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sharepicturemanage.service.SharePictureManageService;


@Service("sharePictureManageService")
public class SharePictureManageServiceImpl extends ServiceImpl<SharePictureManageDao, SharePictureManageEntity> implements SharePictureManageService {

	@Override
	public Map<String, Object> findOneSharePictureManage() {
		Map<String, Object> map = this.baseMapper.findOneSharePictureManage();
		if(map==null) {
			throw new RRException(ErrorCode.ShareErrorCode.SHARE_PICTURE_ERROR.getErrMsg(),
					ErrorCode.ShareErrorCode.SHARE_PICTURE_ERROR.getCode());
		}
		return map;
	}


}
