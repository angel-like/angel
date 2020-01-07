package com.xmsy.server.zxyy.game.modules.manager.hall.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.game.common.utils.PageUtils;
import com.xmsy.server.zxyy.game.common.utils.R;
import com.xmsy.server.zxyy.game.modules.manager.hall.entity.HallEntity;
import com.xmsy.server.zxyy.game.modules.manager.hall.service.HallService;
import com.xmsy.server.zxyy.game.modules.manager.room.service.RoomService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 *
 * @author yang
 * @email xxxxx
 * @date 2019-01-31 14:03:04
 */
@RestController
@RequestMapping("hall/hall")
public class HallController {
	@Autowired
	private HallService hallService;

	@Resource(name = "roomService")
	private RoomService roomservice;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
//	@RequiresPermissions("hall:hall:list")
	public R list(HallEntity hall, PageParam pageParam) {
		Page<HallEntity> result = new Page<HallEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<HallEntity> entityWrapper = new EntityWrapper<HallEntity>(hall);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		hallService.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
//	@RequiresPermissions("hall:hall:info")
	public R info(@PathVariable("id") String id) {
		HallEntity hall = hallService.selectById(id);
		return R.ok().put("hall", hall);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
//	@RequiresPermissions("hall:hall:save")
	public R save(@RequestBody HallEntity hall) {
		hallService.insert(hall);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
//	@RequiresPermissions("hall:hall:update")
	public R update(@RequestBody HallEntity hall) {
		hallService.updateHallById(hall);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
//	@RequiresPermissions("hall:hall:delete")
	public R delete(@RequestBody Long id) {
		hallService.deleteById(id);
		return R.ok();
	}
	/**
	 * 删除
	 */
	@RequestMapping("/deleteByIds")
//	@RequiresPermissions("hall:hall:delete")
	public R deleteByIds(@RequestBody Long[] id) {
		List<Long> coll= new ArrayList<Long>(Arrays.asList(id));
		for (Long aLong : coll) {

			hallService.deleteById(aLong);
		}
		//hallService.deleteBatchIds(coll);
		return R.ok();
	}

}
