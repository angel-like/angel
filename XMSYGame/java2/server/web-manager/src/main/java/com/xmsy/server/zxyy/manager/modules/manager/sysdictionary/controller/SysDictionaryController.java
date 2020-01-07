package com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.entity.SysDictionaryEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.service.SysDictionaryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数字字典
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-08 10:47:07
 */
@RestController
@RequestMapping("sysdictionary/sysdictionary")
public class SysDictionaryController {
	@Autowired
	private SysDictionaryService sysDictionaryService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sysdictionary:sysdictionary:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = sysDictionaryService.queryPage(params);
		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sysdictionary:sysdictionary:info")
	public R info(@PathVariable("id") Long id) {
		SysDictionaryEntity sysDictionary = sysDictionaryService.selectById(id);
		return R.ok().put("sysdictionary", sysDictionary);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/getParentList")
	@RequiresPermissions("sysdictionary:sysdictionary:info")
	public R getInfoByCode() {
		SysDictionaryEntity sysDictionary = new SysDictionaryEntity();
		sysDictionary.setParentCode("0");
		List<SysDictionaryEntity> dataList = sysDictionaryService
				.selectList(new EntityWrapper<SysDictionaryEntity>(sysDictionary));
		sysDictionary.setCode("0");
		sysDictionary.setName("一级");
		dataList.add(0, sysDictionary);
		return R.ok().put("dictionaryList", dataList);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sysdictionary:sysdictionary:save")
	public R save(@RequestBody SysDictionaryEntity sysDictionary) {
		sysDictionaryService.insert(sysDictionary);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sysdictionary:sysdictionary:update")
	public R update(@RequestBody SysDictionaryEntity sysDictionary) {
		sysDictionaryService.updateById(sysDictionary);
		return R.ok();
	}

	/**
	 * 启用
	 */
	@RequestMapping("/enable/{id}")
	@RequiresPermissions("sysdictionary:sysdictionary:update")
	public R enable(@PathVariable("id") Long id) {
		SysDictionaryEntity sysDictionary = new SysDictionaryEntity();
		sysDictionary.setId(id);
		sysDictionary.setEnable(true);
		sysDictionaryService.updateById(sysDictionary);
		return R.ok();
	}

	/**
	 * 禁用
	 */
	@RequestMapping("/disable/{id}")
	@RequiresPermissions("sysdictionary:sysdictionary:update")
	public R disable(@PathVariable("id") Long id) {
		SysDictionaryEntity sysDictionary = new SysDictionaryEntity();
		sysDictionary.setId(id);
		sysDictionary.setEnable(false);
		sysDictionaryService.updateById(sysDictionary);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sysdictionary:sysdictionary:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			sysDictionaryService.deleteById(id);
		}
		return R.ok();
	}

	/**
	 * 通过上级code获取下拉
	 */
	@RequestMapping("/select/{parentCode}")
	public R delete(@PathVariable("parentCode") String parentCode) {
		List<SysDictionaryEntity> list = sysDictionaryService
				.selectList(new EntityWrapper<SysDictionaryEntity>().eq("parent_code", parentCode).eq("enable",true
				));
		return R.ok().put("data", list);
	}
	/**
	 * 福缘任务类型下拉
	 * 通过上级code获取下拉
	 */
	@RequestMapping("/otherTaskTypeSelect/{parentCode}")
	public R otherTaskTypeSelect(@PathVariable("parentCode") String parentCode) {
		List<SysDictionaryEntity> list = sysDictionaryService
				.selectList(new EntityWrapper<SysDictionaryEntity>().eq("parent_code", parentCode).eq("enable",true
				));
		List<SysDictionaryEntity> listOtherTaskType=new ArrayList<>();
		for(SysDictionaryEntity sysDictionary:list) {
			if(sysDictionary.getCode().equals("shareWeChat")) {//分享微信
				listOtherTaskType.add(sysDictionary);
				continue;
			}
			if(sysDictionary.getCode().equals("peopleAgency")) {//发展下级代理
				listOtherTaskType.add(sysDictionary);
				continue;
			}
			if(sysDictionary.getCode().equals("signEveryDay")) {//每日签到
				listOtherTaskType.add(sysDictionary);
				continue;
			}
			if(sysDictionary.getCode().equals("yuEBao")) {//余额宝
				listOtherTaskType.add(sysDictionary);
				continue;
			}
		}
		return R.ok().put("data", listOtherTaskType);
	}
}
