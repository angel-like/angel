package com.xmsy.server.zxyy.webhome.modules.app.pool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.poolgame.service.PoolGameService;

/**
 * 奖池
 *
 * @author adu
 * @email xxxxx
 * @date 2019-02-20 11:18:25
 */
@RestController
@RequestMapping("V1.0/App")
public class AppPoolController {
	@Resource
	private PoolGameService poolGameService;
	
	/**
	 * 总奖金
	 */
	@GetMapping("/PoolBonus")
	public R poolbonus(HttpServletRequest httpServletRequest) {
		return poolbonusdemo();//先用写死的
		//下面是正式逻辑（等待确认）
//		return R.ok().put("data", ImmutableMap.of("poolBonus",poolGameService.sumAllPool()));
	}
	/**
	 * 总奖金(demo)
	 */
	private R poolbonusdemo() {
		return R.ok().put("data", ImmutableMap.of("poolBonus", System.currentTimeMillis()/1000));
	}
	
	

}
