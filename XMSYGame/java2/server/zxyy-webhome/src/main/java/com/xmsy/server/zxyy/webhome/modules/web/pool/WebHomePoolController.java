package com.xmsy.server.zxyy.webhome.modules.web.pool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.poolgame.service.PoolGameService;

/**
 * 奖池
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-18 11:18:25
 */
@RestController
@RequestMapping("webhome/public")
public class WebHomePoolController {
	@Resource
	private PoolGameService poolGameService;
	
	/**
	 * 总奖金
	 */
	@GetMapping("/poolbonus")
	public R poolbonus(HttpServletRequest httpServletRequest) {
		return poolbonusdemo();//先用写死的
		//下面是正式逻辑（等待确认）
//		return R.ok().put("poolBonus", poolGameService.sumAllPool());
	}
	/**
	 * 总奖金(demo)
	 */
	private R poolbonusdemo() {
		return R.ok().put("poolBonus", System.currentTimeMillis()/1000);
	}
	
	

}
