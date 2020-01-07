package com.xmsy.server.zxyy.manager.modules.manager.sysconfig.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.cache.EhCacheName;
import com.xmsy.server.zxyy.manager.modules.manager.sysconfig.dao.SysConfigDao;
import com.xmsy.server.zxyy.manager.modules.manager.sysconfig.entity.SysConfigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysconfig.entity.SysConfigPayParam;
import com.xmsy.server.zxyy.manager.modules.manager.sysconfig.service.SysConfigService;

@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfigEntity> implements SysConfigService {

	@Override
	@CacheEvict(value = EhCacheName.SYSCONFIG_CACHE, allEntries = true)
	public boolean insert(SysConfigEntity entity) {
		return super.insert(entity);
	}

	@Override
	@CacheEvict(value = EhCacheName.SYSCONFIG_CACHE, allEntries = true)
	public boolean deleteById(Serializable id) {
		return super.deleteById(id);
	}

	@Override
	@CacheEvict(value = EhCacheName.SYSCONFIG_CACHE, allEntries = true)
	public boolean updateById(SysConfigEntity entity) {
		return super.updateById(entity);
	}

	@Override
	public SysConfigEntity selectById(Serializable id) {
		return super.selectById(id);
	}

	@Override
	public String selectByParamKey(String parentKey, String paramKey) {
		return baseMapper.findSysconfigValue(parentKey, paramKey);
	}

	@Override
	public SysConfigPayParam gainSysConfigPayParam(List<SysConfigEntity> sysConfigList,SysConfigPayParam sysConfigPayParam) {
		for(SysConfigEntity sysConfig:sysConfigList) {// 遍历sysConfigList  4个数据(产品编码,密钥,回调地址,支付地址)
			//查询得到下级的下级的数据(产品编码:h5,pc   密钥:uid,secret,publicKey,privateKey   回调地址:url    支付地址:url  )
			List<SysConfigEntity> sysConfigListSon = this.baseMapper.selectList(
					new EntityWrapper<SysConfigEntity>(new SysConfigEntity().setParentId(sysConfig.getId())));

			if(sysConfig.getParamValue().equals("product")) {//如果是产品编码。再遍历出下级(产品编码)  的下级（h5,pc）  的下级(h5下的6个数据字段)
				for(SysConfigEntity SysConfigSon:sysConfigListSon) {
					if(SysConfigSon.getParamValue().equals("h5")) {//(值找 h5下的6个数据字段)
						List<SysConfigEntity> sysConfigListGrandSon = this.selectList(
								new EntityWrapper<SysConfigEntity>(new SysConfigEntity().setParentId(SysConfigSon.getId())));
						for(SysConfigEntity SysConfigGrandSon:sysConfigListGrandSon) {
							if(SysConfigGrandSon.getParamKey().equals("alipay")) {
								sysConfigPayParam.setAlipay(SysConfigGrandSon.getParamValue());
								sysConfigPayParam.setAlipayId(SysConfigGrandSon.getId());
								continue;
							}
							if(SysConfigGrandSon.getParamKey().equals("weixin")) {
								sysConfigPayParam.setWeixin(SysConfigGrandSon.getParamValue());
								sysConfigPayParam.setWeixinId(SysConfigGrandSon.getId());
								continue;
							}
							if(SysConfigGrandSon.getParamKey().equals("qqpay")) {
								sysConfigPayParam.setQqpay(SysConfigGrandSon.getParamValue());
								sysConfigPayParam.setQqpayId(SysConfigGrandSon.getId());
								continue;
							}
							if(SysConfigGrandSon.getParamKey().equals("jindongpay")) {
								sysConfigPayParam.setJindongpay(SysConfigGrandSon.getParamValue());
								sysConfigPayParam.setJindongpayId(SysConfigGrandSon.getId());
								continue;
							}
							if(SysConfigGrandSon.getParamKey().equals("unionpay")) {
								sysConfigPayParam.setUnionpay(SysConfigGrandSon.getParamValue());
								sysConfigPayParam.setUnionpayId(SysConfigGrandSon.getId());
								continue;
							}
							if(SysConfigGrandSon.getParamKey().equals("quickpay")) {
								sysConfigPayParam.setQuickpay(SysConfigGrandSon.getParamValue());
								sysConfigPayParam.setQuickpayId(SysConfigGrandSon.getId());
								continue;
							}
						}
					}
				}

			}else if(sysConfig.getParamValue().equals("callbackUrl")) {//回调地址  1个
				SysConfigEntity sysConfigCallBackUrl = this.selectOne(
						new EntityWrapper<SysConfigEntity>(new SysConfigEntity().setParentId(sysConfig.getId())));
				sysConfigPayParam.setCallbackUrl(sysConfigCallBackUrl.getParamValue());
				sysConfigPayParam.setCallbackUrlId(sysConfigCallBackUrl.getId());

			}else if(sysConfig.getParamValue().equals("payUrl")) {//支付地址 1个
				SysConfigEntity sysConfigPayUrl = this.selectOne(
						new EntityWrapper<SysConfigEntity>(new SysConfigEntity().setParentId(sysConfig.getId())));
				sysConfigPayParam.setPayUrl(sysConfigPayUrl.getParamValue());
				sysConfigPayParam.setPayUrlId(sysConfigPayUrl.getId());

			}else if(sysConfig.getParamValue().equals("certificate")) {//密钥 4个
				List<SysConfigEntity> sysConfigCertificateList = this.selectList(
						new EntityWrapper<SysConfigEntity>(new SysConfigEntity().setParentId(sysConfig.getId())));
				for(SysConfigEntity SysConfigCertificate:sysConfigCertificateList) {
					if(SysConfigCertificate.getParamKey().equals("uid")) {
						sysConfigPayParam.setUid(SysConfigCertificate.getParamValue());
						sysConfigPayParam.setUidId(SysConfigCertificate.getId());
						continue;
					}
					if(SysConfigCertificate.getParamKey().equals("secret")) {
						sysConfigPayParam.setSecret(SysConfigCertificate.getParamValue());
						sysConfigPayParam.setSecretId(SysConfigCertificate.getId());
						continue;
					}
					if(SysConfigCertificate.getParamKey().equals("publicKey")) {
						sysConfigPayParam.setPublicKey(SysConfigCertificate.getParamValue());
						sysConfigPayParam.setPublicKeyId(SysConfigCertificate.getId());
						continue;
					}
					if(SysConfigCertificate.getParamKey().equals("privateKey")) {
						sysConfigPayParam.setPrivateKey(SysConfigCertificate.getParamValue());
						sysConfigPayParam.setPrivateKeyId(SysConfigCertificate.getId());
						continue;
					}
				}
			}
		}
		return sysConfigPayParam;
	}
	/**
	 * 批量修改或新增支付配置的参数
	 * @param sysConfigPayParam
	 */
	@Override
	@Transactional
	public void updateOrSaveSysConfigPayParam(SysConfigPayParam sysConfigPayParam) {
		// 1.通过别名 查询当前支付 对应 的 系统配置实体类
		SysConfigEntity sysConfigParent = this.selectOne(new EntityWrapper<SysConfigEntity>(
				new SysConfigEntity().setParamValue(sysConfigPayParam.getAliasName())));
		if(sysConfigParent==null) {//1.1 全部数据都是插入的
			saveAllSysConfigPayParam(sysConfigPayParam);
			return;//返回
		}
		// 2.通过系统配置Id去查询 对应的下级4个数据(产品编码,密钥,回调地址,支付地址)
		List<SysConfigEntity> sysConfigList = this.selectList(
				new EntityWrapper<SysConfigEntity>(new SysConfigEntity().setParentId(sysConfigParent.getId())));
		// 遍历下级数据
		if (sysConfigList != null&&sysConfigList.size() >0) {
			saveOrUpdateSysConfigPayParam(sysConfigList,sysConfigPayParam);
			return;//返回
		}else if (sysConfigList.size() ==0) {
			saveAllPayParam(sysConfigPayParam,sysConfigParent);
			return;//返回
		}

	}

	void saveAllPayParam(SysConfigPayParam sysConfigPayParam,SysConfigEntity sysConfigParentBack){
		if(sysConfigParentBack==null||sysConfigParentBack.getId()==null){
			//1.插入系统支付别名  (先查询整个支付的最最上层，然后插入系统支付别名)
			SysConfigEntity sysConfigParentParent = this.selectOne(new EntityWrapper<SysConfigEntity>(
					new SysConfigEntity().setParamValue("pay").setParentId(0L).setParamKey("支付")));
			sysConfigParentBack = new SysConfigEntity().setParentId(sysConfigParentParent.getId())
					.setParamKey(sysConfigPayParam.getName()).setParamValue(sysConfigPayParam.getAliasName());
			this.insert(sysConfigParentBack);//插入时 会把当前id回写到实体类里
		}

		// 2.插入4个数据(产品编码,密钥,回调地址,支付地址)
		SysConfigEntity sysConfigParentProduct = new SysConfigEntity().setParentId(sysConfigParentBack.getId())
				.setParamKey("产品编码").setParamValue("product");
		this.insert(sysConfigParentProduct);
		SysConfigEntity sysConfigParentCertificate = new SysConfigEntity().setParentId(sysConfigParentBack.getId())
				.setParamKey("密钥").setParamValue("certificate");
		this.insert(sysConfigParentCertificate);
		SysConfigEntity sysConfigParentCallbackUrl = new SysConfigEntity().setParentId(sysConfigParentBack.getId())
				.setParamKey("回调地址").setParamValue("callbackUrl");
		this.insert(sysConfigParentCallbackUrl);
		SysConfigEntity sysConfigParentPayUrl = new SysConfigEntity().setParentId(sysConfigParentBack.getId())
				.setParamKey("支付地址").setParamValue("payUrl");
		this.insert(sysConfigParentPayUrl);
		//3.插入产品编码下h5下数据   （h5下的6个数据）
		SysConfigEntity sysConfigParentH5 = new SysConfigEntity().setParentId(sysConfigParentProduct.getId())
				.setParamKey("h5").setParamValue("h5");
		this.insert(sysConfigParentH5);
		h5SaveSysConfig(sysConfigParentH5.getId(),sysConfigPayParam);
		//4.插入秘钥下的4个数据  sysConfigParentCertificate
		certificateSaveSysConfig(sysConfigParentCertificate.getId(),sysConfigPayParam);
		//5.插入回调地址下的  1个数据  sysConfigParentCallbackUrl
		callBackUrlSaveSysConfig(sysConfigParentCallbackUrl.getId(),sysConfigPayParam);
		//6..插入支付地址下的  1个数据  sysConfigParentPayUrl
		payUrlSaveSysConfig(sysConfigParentPayUrl.getId(),sysConfigPayParam);

	}
	/**
	 * 通过4个数据(产品编码,密钥,回调地址,支付地址)  去插入或者更新
	 * @param sysConfigList
	 * @param sysConfigPayParam
	 */
	private void saveOrUpdateSysConfigPayParam(List<SysConfigEntity> sysConfigList,
			SysConfigPayParam sysConfigPayParam) {
		for (SysConfigEntity sysConfig : sysConfigList) {
			if(sysConfig.getParamValue().equals("product")) {//如果是产品编码。再遍历出的(产品编码)下级（h5,pc）
				List<SysConfigEntity> sysConfigListSon = this.baseMapper.selectList(
						new EntityWrapper<SysConfigEntity>(new SysConfigEntity().setParentId(sysConfig.getId())));
				for(SysConfigEntity SysConfigSon:sysConfigListSon) {
					if(SysConfigSon.getParamValue().equals("h5")) {//(值找 h5下的6个数据字段)
						h5SaveSysConfig(SysConfigSon.getId(),sysConfigPayParam);
					}
				}
			}else if(sysConfig.getParamValue().equals("certificate")) {
				certificateSaveSysConfig(sysConfig.getId(),sysConfigPayParam);
			}else if(sysConfig.getParamValue().equals("callbackUrl")) {
				callBackUrlSaveSysConfig(sysConfig.getId(),sysConfigPayParam);
			}else if(sysConfig.getParamValue().equals("payUrl")) {
				payUrlSaveSysConfig(sysConfig.getId(),sysConfigPayParam);
			}
		}
	}

	/**
	 * 插入支付服务的所有数据
	 * @param sysConfigPayParam
	 */
	void saveAllSysConfigPayParam(SysConfigPayParam sysConfigPayParam){
		//1.插入系统支付别名  (先查询整个支付的最最上层，然后插入系统支付别名)
		SysConfigEntity sysConfigParentParent = this.selectOne(new EntityWrapper<SysConfigEntity>(
				new SysConfigEntity().setParamValue("pay").setParentId(0L).setParamKey("支付")));
		SysConfigEntity sysConfigParentBack = new SysConfigEntity().setParentId(sysConfigParentParent.getId())
				.setParamKey(sysConfigPayParam.getName()).setParamValue(sysConfigPayParam.getAliasName());
		this.insert(sysConfigParentBack);//插入时 会把当前id回写到实体类里
		// 2.插入4个数据(产品编码,密钥,回调地址,支付地址)
		SysConfigEntity sysConfigParentProduct = new SysConfigEntity().setParentId(sysConfigParentBack.getId())
				.setParamKey("产品编码").setParamValue("product");
		this.insert(sysConfigParentProduct);
		SysConfigEntity sysConfigParentCertificate = new SysConfigEntity().setParentId(sysConfigParentBack.getId())
				.setParamKey("密钥").setParamValue("certificate");
		this.insert(sysConfigParentCertificate);
		SysConfigEntity sysConfigParentCallbackUrl = new SysConfigEntity().setParentId(sysConfigParentBack.getId())
				.setParamKey("回调地址").setParamValue("callbackUrl");
		this.insert(sysConfigParentCallbackUrl);
		SysConfigEntity sysConfigParentPayUrl = new SysConfigEntity().setParentId(sysConfigParentBack.getId())
				.setParamKey("支付地址").setParamValue("payUrl");
		this.insert(sysConfigParentPayUrl);
		//3.插入产品编码下h5下数据   （h5下的6个数据）
		SysConfigEntity sysConfigParentH5 = new SysConfigEntity().setParentId(sysConfigParentProduct.getId())
				.setParamKey("h5").setParamValue("h5");
		this.insert(sysConfigParentH5);
		h5SaveSysConfig(sysConfigParentH5.getId(),sysConfigPayParam);
		//4.插入秘钥下的4个数据  sysConfigParentCertificate
		certificateSaveSysConfig(sysConfigParentCertificate.getId(),sysConfigPayParam);
		//5.插入回调地址下的  1个数据  sysConfigParentCallbackUrl
		callBackUrlSaveSysConfig(sysConfigParentCallbackUrl.getId(),sysConfigPayParam);
		//6..插入支付地址下的  1个数据  sysConfigParentPayUrl
		payUrlSaveSysConfig(sysConfigParentPayUrl.getId(),sysConfigPayParam);

	}
	/**
	 * 更新或者插入   支付地址 下的数据
	 * @param payUrlId
	 * @param sysConfigPayParam
	 */
	private void payUrlSaveSysConfig(Long payUrlId, SysConfigPayParam sysConfigPayParam) {
		if(sysConfigPayParam.getPayUrlId()!=null) {
			SysConfigEntity sysConfigPayUrl= new SysConfigEntity().setParamValue(sysConfigPayParam.getPayUrl());
			sysConfigPayUrl.setId(sysConfigPayParam.getPayUrlId());
			this.updateById(sysConfigPayUrl);
		}else {
			SysConfigEntity sysConfigPayUrl= new SysConfigEntity().setParentId(payUrlId)
					.setParamKey("url").setParamValue(sysConfigPayParam.getPayUrl()!=null?sysConfigPayParam.getPayUrl():"");
			this.baseMapper.savePayParamDao(sysConfigPayUrl);
		}
	}
	/**
	 * 更新或者插入   回调地址 下的数据
	 * @param callBackUrlId
	 * @param sysConfigPayParam
	 */
	private void callBackUrlSaveSysConfig(Long callBackUrlId, SysConfigPayParam sysConfigPayParam) {
		if(sysConfigPayParam.getCallbackUrlId()!=null) {
			SysConfigEntity sysConfigCallbackUrl= new SysConfigEntity().setParamValue(sysConfigPayParam.getCallbackUrl());
			sysConfigCallbackUrl.setId(sysConfigPayParam.getCallbackUrlId());
			this.updateById(sysConfigCallbackUrl);
		}else {
			SysConfigEntity sysConfigCallbackUrl= new SysConfigEntity().setParentId(callBackUrlId)
					.setParamKey("url").setParamValue(sysConfigPayParam.getCallbackUrl()!=null?sysConfigPayParam.getCallbackUrl():"");
			this.baseMapper.savePayParamDao(sysConfigCallbackUrl);
		}

	}
	/**
	 * 更新或者插入   CertificateId 密钥下的所有数据
	 * @param CertificateId
	 * @param sysConfigPayParam
	 */
	private void certificateSaveSysConfig(Long certificateId, SysConfigPayParam sysConfigPayParam) {
		if(sysConfigPayParam.getUidId()!=null) {
			SysConfigEntity sysConfigUid= new SysConfigEntity().setParamValue(sysConfigPayParam.getUid());
			sysConfigUid.setId(sysConfigPayParam.getUidId());
			this.updateById(sysConfigUid);
		}else {
			SysConfigEntity sysConfigUid= new SysConfigEntity().setParentId(certificateId)
					.setParamKey("uid").setParamValue(sysConfigPayParam.getUid()!=null?sysConfigPayParam.getUid():"");
			this.baseMapper.savePayParamDao(sysConfigUid);
		}
		if(sysConfigPayParam.getSecretId()!=null) {
			SysConfigEntity sysConfigSecret= new SysConfigEntity().setParamValue(sysConfigPayParam.getSecret());
			sysConfigSecret.setId(sysConfigPayParam.getSecretId());
			this.updateById(sysConfigSecret);
		}else {
			SysConfigEntity sysConfigSecret= new SysConfigEntity().setParentId(certificateId)
					.setParamKey("secret").setParamValue(sysConfigPayParam.getSecret()!=null?sysConfigPayParam.getSecret():"");
			this.baseMapper.savePayParamDao(sysConfigSecret);
		}
		if(sysConfigPayParam.getPublicKeyId()!=null) {
			SysConfigEntity sysConfigPublicKey= new SysConfigEntity().setParamValue(sysConfigPayParam.getPublicKey());
			sysConfigPublicKey.setId(sysConfigPayParam.getPublicKeyId());
			this.updateById(sysConfigPublicKey);
		}else {
			SysConfigEntity sysConfigPublicKey= new SysConfigEntity().setParentId(certificateId)
					.setParamKey("publicKey").setParamValue(sysConfigPayParam.getPublicKey()!=null?sysConfigPayParam.getPublicKey():"");
			this.baseMapper.savePayParamDao(sysConfigPublicKey);
		}
		if(sysConfigPayParam.getPrivateKeyId()!=null) {
			SysConfigEntity sysConfigPrivateKey= new SysConfigEntity().setParamValue(sysConfigPayParam.getPrivateKey());
			sysConfigPrivateKey.setId(sysConfigPayParam.getPrivateKeyId());
			this.updateById(sysConfigPrivateKey);
		}else {
			SysConfigEntity sysConfigPrivateKey= new SysConfigEntity().setParentId(certificateId)
					.setParamKey("privateKey").setParamValue(sysConfigPayParam.getPrivateKey()!=null?sysConfigPayParam.getPrivateKey():"");
			this.baseMapper.savePayParamDao(sysConfigPrivateKey);
		}
	}
	/**
	 * 更新或者插入   h5下的所有数据
	 * @param h5Id
	 * @param sysConfigPayParam
	 */
	private void h5SaveSysConfig(Long h5Id, SysConfigPayParam sysConfigPayParam) {
		if(sysConfigPayParam.getWeixinId()!=null) {
			SysConfigEntity sysConfigWeixin = new SysConfigEntity().setParamValue(sysConfigPayParam.getWeixin());
			sysConfigWeixin.setId(sysConfigPayParam.getWeixinId());
			this.updateById(sysConfigWeixin);
		}else {
			SysConfigEntity sysConfigWeixin = new SysConfigEntity().setParentId(h5Id)
					.setParamKey("weixin").setParamValue(sysConfigPayParam.getWeixin()!=null?sysConfigPayParam.getWeixin():"");
			this.baseMapper.savePayParamDao(sysConfigWeixin);
		}
		if(sysConfigPayParam.getAlipayId()!=null) {
			SysConfigEntity sysConfigAlipay = new SysConfigEntity().setParamValue(sysConfigPayParam.getAlipay());
			sysConfigAlipay.setId(sysConfigPayParam.getAlipayId());
			this.updateById(sysConfigAlipay);
		}else {
			SysConfigEntity sysConfigAlipay = new SysConfigEntity().setParentId(h5Id)
					.setParamKey("alipay").setParamValue(sysConfigPayParam.getAlipay()!=null?sysConfigPayParam.getAlipay():"");
			this.baseMapper.savePayParamDao(sysConfigAlipay);
		}
		if(sysConfigPayParam.getQqpayId()!=null) {
			SysConfigEntity sysConfigQqpay = new SysConfigEntity().setParamValue(sysConfigPayParam.getQqpay());
			sysConfigQqpay.setId(sysConfigPayParam.getQqpayId());
			this.updateById(sysConfigQqpay);
		}else {
			SysConfigEntity sysConfigQqpay= new SysConfigEntity().setParentId(h5Id)
					.setParamKey("qqpay").setParamValue(sysConfigPayParam.getQqpay()!=null?sysConfigPayParam.getQqpay():"");
			this.baseMapper.savePayParamDao(sysConfigQqpay);
		}
		if(sysConfigPayParam.getJindongpayId()!=null) {
			SysConfigEntity sysConfigJindongpay= new SysConfigEntity().setParamValue(sysConfigPayParam.getJindongpay());
			sysConfigJindongpay.setId(sysConfigPayParam.getJindongpayId());
			this.updateById(sysConfigJindongpay);
		}else {
			SysConfigEntity sysConfigJindongpay= new SysConfigEntity().setParentId(h5Id)
					.setParamKey("jindongpay").setParamValue(sysConfigPayParam.getJindongpay()!=null?sysConfigPayParam.getJindongpay():"");
			this.baseMapper.savePayParamDao(sysConfigJindongpay);
		}

		if(sysConfigPayParam.getUnionpayId()!=null) {
			SysConfigEntity sysConfigUnionpay= new SysConfigEntity().setParamValue(sysConfigPayParam.getUnionpay());
			sysConfigUnionpay.setId(sysConfigPayParam.getUnionpayId());
			this.updateById(sysConfigUnionpay);
		}else {
			SysConfigEntity sysConfigUnionpay= new SysConfigEntity().setParentId(h5Id)
					.setParamKey("unionpay").setParamValue(sysConfigPayParam.getUnionpay()!=null?sysConfigPayParam.getUnionpay():"");
			this.baseMapper.savePayParamDao(sysConfigUnionpay);
		}
		if(sysConfigPayParam.getQuickpayId()!=null) {
			SysConfigEntity sysConfigQuickpay= new SysConfigEntity().setParamValue(sysConfigPayParam.getQuickpay());
			sysConfigQuickpay.setId(sysConfigPayParam.getQuickpayId());
			this.updateById(sysConfigQuickpay);
		}else {
			SysConfigEntity sysConfigQuickpay= new SysConfigEntity().setParentId(h5Id)
					.setParamKey("quickpay").setParamValue(sysConfigPayParam.getQuickpay()!=null?sysConfigPayParam.getQuickpay():"");
			this.baseMapper.savePayParamDao(sysConfigQuickpay);
		}
	}
}
