package com.xmsy.server.zxyy.game.modules.web.hall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.xmsy.server.zxyy.game.common.utils.R;
import com.xmsy.server.zxyy.game.modules.manager.hall.entity.HallEntity;
import com.xmsy.server.zxyy.game.modules.manager.hall.service.HallService;

/**
 * 
 *
 * @author yang
 * @email xxxxx
 * @date 2019-01-31 14:03:04
 */
@RestController
@RequestMapping("web/hall")
public class HallHandler {
	@Autowired
	private HallService hallService;

	/**
	 * 列表
	 * 
	 * @throws Exception
	 */
	@GetMapping("/list")
	public R list(@RequestParam(value = "id", required = false) Long id) throws Exception {
		if (null != id) {
			return R.ok().putData("hall", Lists.newArrayList(hallService.selectAll().get(id)));
		}
		return R.ok().putData("hall", hallService.selectAll().values());
	}

	/**
	 * 信息
	 */
	@GetMapping("/info/{id}")
	public R info(@PathVariable("id") Long id) {
		HallEntity hall = hallService.selectById(id);
		return R.ok().putData("hall", hall);
	}

}
