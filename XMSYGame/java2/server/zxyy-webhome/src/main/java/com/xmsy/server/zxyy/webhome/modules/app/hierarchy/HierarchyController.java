package com.xmsy.server.zxyy.webhome.modules.app.hierarchy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.service.UserHierarchyService;

@RestController
@RequestMapping("V1.0/App")
public class HierarchyController {

	@Autowired
	private UserHierarchyService userHierarchyService;

	/**
	 * 获取银行列表
	 */
	@GetMapping("/hierarchy/rateList")
	public R rateList() {
		return R.ok().put("data", userHierarchyService.getHierarchyRateList());
	}

}
