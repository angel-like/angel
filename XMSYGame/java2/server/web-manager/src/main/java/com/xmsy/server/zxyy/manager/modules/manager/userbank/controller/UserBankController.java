package com.xmsy.server.zxyy.manager.modules.manager.userbank.controller;

import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.entity.SysDictionaryEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.service.SysDictionaryService;
import com.xmsy.server.zxyy.manager.utils.InviteCode;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户银行管理
 *
 * @author aleng
 * @email
 */
@RestController
@RequestMapping("userbank/userbank")
public class UserBankController {

	// 上级路径
	public static final String parentCode = "001";

	@Autowired
	private SysDictionaryService sysDictionaryService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("userbank:userbank:list")
	public R list(@RequestParam Map<String, Object> params) {
		params.put("parentCode", parentCode);
		PageUtils page = sysDictionaryService.queryPage(params);
		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("userbank:userbank:info")
	public R info(@PathVariable("id") Long id) {
		SysDictionaryEntity sysDictionary = sysDictionaryService.selectById(id);
		return R.ok().put("sysdictionary", sysDictionary);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("userbank:userbank:save")
	public R save(@RequestBody SysDictionaryEntity sysDictionary) {
		sysDictionary.setParentCode(parentCode);
		sysDictionary.setCode(InviteCode.create());
		sysDictionaryService.insert(sysDictionary);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("userbank:userbank:update")
	public R update(@RequestBody SysDictionaryEntity sysDictionary) {
//		sysDictionary.setParentCode(parentCode);
		sysDictionaryService.updateById(sysDictionary);
		return R.ok();
	}

	/**
	 * 启用
	 */
	@RequestMapping("/enable")
	@RequiresPermissions("userbank:userbank:update")
	public R enable(@RequestBody Long[] ids )  {
		for (Long id : ids) {
		SysDictionaryEntity sysDictionary = new SysDictionaryEntity();
		sysDictionary.setId(id);
		sysDictionary.setEnable(true);
		sysDictionary.setParentCode(parentCode);
		sysDictionaryService.updateById(sysDictionary);
		}
		return R.ok();
	}

	/**
	 * 禁用
	 */
	@RequestMapping("/disable")
	@RequiresPermissions("userbank:userbank:update")
	public R disable(@RequestBody Long[] ids ) {
		for (Long id : ids) {
			SysDictionaryEntity sysDictionary = new SysDictionaryEntity();
			sysDictionary.setId(id);
			sysDictionary.setEnable(false);
			sysDictionary.setParentCode(parentCode);
			sysDictionaryService.updateById(sysDictionary);
		}
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("userbank:userbank:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			sysDictionaryService.deleteById(id);
		}
		return R.ok();
	}

}
