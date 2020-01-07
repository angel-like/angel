package com.xmsy.server.zxyy.webhome.modules.app.sysconfig;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.sysdictionary.entity.SysDictionaryEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sysdictionary.service.SysDictionaryService;

//import lombok.extern.slf4j.Slf4j;

//@Slf4j
@RestController
@RequestMapping("V1.0/App")
public class SysconfigController {

	@Autowired
	private SysDictionaryService sysDictionaryService;

	/**
	 * 获取银行列表
	 */
	@GetMapping("/Bank/list")
	public R banklist() {
		List<SysDictionaryEntity> sysDictionarys = sysDictionaryService.findListByParentCode(Constant.CODE);
		//log.info("[获取用户存款银行列表] sysDictionarys {}", sysDictionarys);
		return R.ok().put("data", sysDictionarys);
	}

}
