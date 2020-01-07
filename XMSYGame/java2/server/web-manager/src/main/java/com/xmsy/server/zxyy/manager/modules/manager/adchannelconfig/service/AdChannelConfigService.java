package com.xmsy.server.zxyy.manager.modules.manager.adchannelconfig.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.adchannelconfig.entity.AdChannelConfigEntity;


/**
 * 渠道配置表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-07-02 17:05:09
 */
public interface AdChannelConfigService extends IService<AdChannelConfigEntity> {
	/**
	 * 获取打包的列表
	 * @return
	 */
	JSONArray getAPPPackageDataList();
	/**
	 * 增加渠道的时候   去打包
	 * @return
	 */
	Boolean addAPPPackage(AdChannelConfigEntity adChannelConfig);
	/**
	 * 删除渠道的时候  删除打包列表里相关数据
	 * @return
	 */
	Boolean delAPPPackage(AdChannelConfigEntity adChannelConfig);
	/**
	 * 通过id物理删除 渠道配置
	 * @return
	 */
	boolean delPhysicalAdChannelConfig(Long id);
	/**
	 * 通过输入的发布地址去   更新安装包
	 * @param adChannelConfig
	 * @return
	 */
	String updateUrl(AdChannelConfigEntity adChannelConfig);
	/**
	 * 微信分享下载
	 */
	String shareWeixinUrlDownload(AdChannelConfigEntity adChannelConfig);
	/**
	 * 通过渠道码查询对应的渠道配置
	 * @param channelCode
	 * @return
	 */
	AdChannelConfigEntity findAdChannelByChannelCode(String channelCode);
	/**
	 * 空渠道码需要自己写sql插入
	 * @param adChannelConfig
	 * @return
	 */
	boolean addNullChannelCode(AdChannelConfigEntity adChannelConfig);
}

