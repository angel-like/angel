package com.xmsy.server.zxyy.webhome.modules.manager.cashbank.controller;

import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.entity.UserHierarchyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.service.UserHierarchyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.annotation.SysLog;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.manager.cashbank.entity.CashBankEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.cashbank.service.CashBankService;

import java.util.List;

/**
 * 系统银行表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-07 18:46:36
 */
@RestController
@RequestMapping("cashbank/cashbank")
public class CashBankController {
	@Autowired
	private CashBankService cashBankService;
	@Autowired
	private UserHierarchyService userHierarchyService;


	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("cashbank:cashbank:list")
	public R list(CashBankEntity cashBankEntity, PageParam pageParam) {
		Page<CashBankEntity> result = cashBankService.findCashBankPage(pageParam, cashBankEntity);
		List<CashBankEntity> CashBankList = result.getRecords();
		for(CashBankEntity cashBank:CashBankList) {
			String[] strings = cashBank.getHierarchyId().split(",");
			StringBuilder hierarchyName=new StringBuilder();
			for(String s:strings) {
				UserHierarchyEntity hierarchy= userHierarchyService.selectById(s);
				hierarchyName.append(hierarchy.getName()+",");
			};
			cashBank.setHierarchyName(hierarchyName.toString().substring(0,hierarchyName.toString().length() - 1));
		}
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("cashbank:cashbank:info")
	public R info(@PathVariable("id") Long id) {
		CashBankEntity cashbank = cashBankService.selectById(id);
		return R.ok().put("cashbank", cashbank);
	}

	/**
	 * 保存
	 */
	@SysLog("银行卡新增")
	@RequestMapping("/save")
	@RequiresPermissions("cashbank:cashbank:save")
	public R save(@RequestBody CashBankEntity cashbank) {
		cashbank.setEnable(SysConstant.ENABLE_TRUE);// 默认展示
		cashBankService.insert(cashbank);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@SysLog("银行卡修改")
	@RequestMapping("/update")
	@RequiresPermissions("cashbank:cashbank:update")
	public R update(@RequestBody CashBankEntity cashbank) {
		cashBankService.updateById(cashbank);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@SysLog("银行卡删除")
	@RequestMapping("/delete")
	@RequiresPermissions("cashbank:cashbank:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			cashBankService.deleteById(id);
		}
		return R.ok();
	}

	/**
	 * 启用
	 */
	@SysLog("银行卡启用")
	@RequestMapping("/enable")
	@RequiresPermissions("cashbank:cashbank:enable")
	public R enable(@RequestBody Long[] ids) {
		for (Long id : ids) {
			CashBankEntity cashbank = new CashBankEntity();
			cashbank.setId(id);
			cashbank.setEnable(SysConstant.ENABLE_TRUE);
			cashBankService.updateById(cashbank);
		}
		return R.ok();
	}

	/**
	 * 禁用
	 */
	@SysLog("银行卡禁用")
	@RequestMapping("/prohibit")
	@RequiresPermissions("cashbank:cashbank:enable")
	public R prohibit(@RequestBody Long[] ids) {
		for (Long id : ids) {
			CashBankEntity cashbank = new CashBankEntity();
			cashbank.setId(id);
			cashbank.setEnable(SysConstant.ENABLE_FALSE);
			cashBankService.updateById(cashbank);
		}
		return R.ok();
	}

}
