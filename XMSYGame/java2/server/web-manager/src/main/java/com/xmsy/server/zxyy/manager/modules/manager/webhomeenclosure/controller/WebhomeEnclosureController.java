package com.xmsy.server.zxyy.manager.modules.manager.webhomeenclosure.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.network.qiniu.QiniuUpload;
import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.constant.HallUrlConstant;
import com.xmsy.server.zxyy.manager.modules.manager.webhomeenclosure.entity.EnclosureResult;
import com.xmsy.server.zxyy.manager.modules.manager.webhomeenclosure.entity.WebhomeEnclosureEntity;
import com.xmsy.server.zxyy.manager.modules.manager.webhomeenclosure.service.WebhomeEnclosureService;
import com.xmsy.server.zxyy.manager.utils.FileMd5Util;
import com.xmsy.server.zxyy.manager.utils.QiniuUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 文件附件表
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2018-12-31 15:05:47
 */
@Slf4j
@RestController
@RequestMapping("webhomeenclosure/webhomeenclosure")
public class WebhomeEnclosureController {
	@Autowired
	private WebhomeEnclosureService webhomeEnclosureService;

	/**
	 * 上传附件,并返回附件Id
	 */
	@SysLog("上传附件")
	@RequestMapping("/uploadFile")
	public R uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
		String Md5Val = FileMd5Util.getStringMd5ForMultipartFile(file);
		log.info("[uploadFile] file {}", file);
		String token = QiniuUtils.getUploadTokenPublic();
		String result = QiniuUpload.uploadByByteArray(file.getBytes(), token);// 图片上传成功返回
		JSONObject jsonObject = JSON.parseObject(result);
		String url = HallUrlConstant.getQINIU_URL() + jsonObject.get("hash");// 七牛云路径
		WebhomeEnclosureEntity entity = new WebhomeEnclosureEntity();
		entity.setName(file.getOriginalFilename());
		entity.setUrl(url);
		entity.setType(file.getContentType());
		WebhomeEnclosureEntity enclosureEntity = webhomeEnclosureService
				.selectOne(new EntityWrapper<WebhomeEnclosureEntity>(entity));
		if (enclosureEntity != null) {
			return R.ok().put("id", enclosureEntity.getId()).put("Md5Val", Md5Val).put("url", url).put("name",file.getOriginalFilename());
		}
		Long id = webhomeEnclosureService.insertGetId(entity);
		return R.ok().put("id", id).put("Md5Val", Md5Val).put("url", url).put("name",file.getOriginalFilename());
	}

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("webhomeenclosure:webhomeenclosure:list")
	public R list(WebhomeEnclosureEntity webhomeEnclosureEntity, PageParam pageParam) {

		log.info("[list] webhomeenclosure {}", webhomeEnclosureEntity);
//		Long groupId=webhomeEnclosureEntity.getGroupId();
		Page<WebhomeEnclosureEntity> result = new Page<WebhomeEnclosureEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<WebhomeEnclosureEntity> entityWrapper = new EntityWrapper<WebhomeEnclosureEntity>(webhomeEnclosureEntity);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		webhomeEnclosureEntity.selectPage(result, entityWrapper);
		log.info("[list] list {}", result.getRecords());
		List<EnclosureResult> list = webhomeEnclosureService.getGroupInfo();
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages())).put("list",list);
//	      Long groupId = (Long)params.get("groupId");
//		PageUtils page = webhomeEnclosureService.queryPage(params);

//		return R.ok().put("page", page).put("list",list);
	}
//	/**
//	 *分组信息
//	 */
//	@RequestMapping("/group")
//	@RequiresPermissions("webhomeenclosure:webhomeenclosure:list")
//	public R group() {
//		List<EnclosureResult> list = webhomeEnclosureService.getGroupInfo();
//		return R.ok().put("list", list);
//	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("webhomeenclosure:webhomeenclosure:info")
	public R info(@PathVariable("id") Long id) {
		WebhomeEnclosureEntity enclosure = webhomeEnclosureService.selectById(id);
		return R.ok().put("enclosure", enclosure);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("webhomeenclosure:webhomeenclosure:save")
	public R save(@RequestBody WebhomeEnclosureEntity enclosure) {
		webhomeEnclosureService.insert(enclosure);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("webhomeenclosure:webhomeenclosure:update")
	public R update(@RequestBody WebhomeEnclosureEntity enclosure) {
		webhomeEnclosureService.updateById(enclosure);
		return R.ok();
	}

	/**
	 * 查询url
	 * 
	 * @param id
	 * @return
	 */
	@SysLog("查看附件")
	@RequestMapping("/enclosure/{id}")
	public R enclosureList(@PathVariable("id") Long id) {
		WebhomeEnclosureEntity Entity = webhomeEnclosureService.selectById(id);
		if (Entity == null) {
			throw new RRException(ErrorCode.EnclosureErrorCode.ENCLOSURE_ISNULL_ERRO.getErrMsg(),
					ErrorCode.EnclosureErrorCode.ENCLOSURE_ISNULL_ERRO.getCode());
		}
		return R.ok().put("url", Entity.getUrl());
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("webhomeenclosure:webhomeenclosure:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			webhomeEnclosureService.deleteById(id);
		}
		return R.ok();
	}

}
