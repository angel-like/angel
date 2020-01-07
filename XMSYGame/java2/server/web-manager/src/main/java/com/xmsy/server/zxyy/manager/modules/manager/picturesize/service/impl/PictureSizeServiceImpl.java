package com.xmsy.server.zxyy.manager.modules.manager.picturesize.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


import com.xmsy.server.zxyy.manager.modules.manager.picturesize.dao.PictureSizeDao;
import com.xmsy.server.zxyy.manager.modules.manager.picturesize.entity.PictureSizeEntity;
import com.xmsy.server.zxyy.manager.modules.manager.picturesize.service.PictureSizeService;


@Service("pictureSizeService")
public class PictureSizeServiceImpl extends ServiceImpl<PictureSizeDao, PictureSizeEntity> implements PictureSizeService {

	@Override
	public String selectPictureSize(Integer typeId, Integer menuId) {
		return this.baseMapper.selectPictureSize(typeId, menuId);
	}


}
