package com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.entity.SysDictionaryEntity;

/**
 * 用户动态码
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-08 10:47:07
 */
public interface SysDictionaryService extends IService<SysDictionaryEntity> {

	PageUtils queryPage(Map<String, Object> params);

	List<SysDictionaryEntity> findListByParentCode(String parentCode);

	String getName(String parentCode, String code);

}
