package com.xmsy.server.zxyy.webhome.modules.manager.imdownloadmanage.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.webhome.modules.manager.imdownloadmanage.entity.ImDownloadManageEntity;
import com.xmsy.server.zxyy.webhome.modules.webim.homepage.result.ImDownloadManageResult;
import com.xmsy.server.zxyy.webhome.modules.webim.homepage.result.ImDownloadResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 33推荐下载管理
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-21 19:44:44
 */
@Mapper
public interface ImDownloadManageDao extends BaseMapper<ImDownloadManageEntity> {

    List<ImDownloadManageResult> selectListForType(@Param("type") String type);

    List<ImDownloadResult> selectImDownloadList(@Param("type") String type);

}
