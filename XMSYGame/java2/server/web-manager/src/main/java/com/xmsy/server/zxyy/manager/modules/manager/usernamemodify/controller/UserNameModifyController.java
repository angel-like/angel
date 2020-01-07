package com.xmsy.server.zxyy.manager.modules.manager.usernamemodify.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.sys.controller.AbstractController;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.SysUserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.modules.manager.usernamemodify.entity.UserNameModifyEntity;
import com.xmsy.server.zxyy.manager.modules.manager.usernamemodify.service.UserNameModifyService;

/**
 * 真实姓名审核表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-10-28 16:05:16
 */
@RestController
@RequestMapping("usernamemodify/usernamemodify")
public class UserNameModifyController extends AbstractController {
	@Autowired
	private UserNameModifyService userNameModifyService;
	@Autowired
	private UserService userService;
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("usernamemodify:usernamemodify:list")
	public R list(UserNameModifyEntity usernamemodify, PageParam pageParam) {
		Page<UserNameModifyEntity> result = new Page<UserNameModifyEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<UserNameModifyEntity> entityWrapper = new EntityWrapper<UserNameModifyEntity>(usernamemodify);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		entityWrapper.orderBy("id", false);
		usernamemodify.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("usernamemodify:usernamemodify:info")
	public R info(@PathVariable("id") Long id) {
		UserNameModifyEntity userNameModify = userNameModifyService.selectById(id);
		return R.ok().put("usernamemodify", userNameModify);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("usernamemodify:usernamemodify:save")
	public R save(@RequestBody UserNameModifyEntity usernamemodify) {
		SysUserEntity sysUser = getUser();
		usernamemodify.setApplicantAccount(sysUser.getUsername());// 获取系统用户名称作为申请人
		userNameModifyService.insert(usernamemodify);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("usernamemodify:usernamemodify:update")
	public R update(@RequestBody UserNameModifyEntity usernamemodify) {
		userNameModifyService.updateById(usernamemodify);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("usernamemodify:usernamemodify:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			userNameModifyService.deleteById(id);
		}
		return R.ok();
	}
	
	/**
	 * 修改会员状态为成功  --可批量修改
	 */
	@RequestMapping("/updateStatusSuccess")
	@RequiresPermissions("usernamemodify:usernamemodify:update")//确认权限
	public R updateStatusSuccess(@RequestBody Long[] ids) {
		for (Long id : ids) {
			//1.通过id获取  真实姓名审核实体userNameModify
			UserNameModifyEntity userNameModify = userNameModifyService.selectById(id);
			//2.通过 真实姓名审核userNameModify的 会员id  与修改后的名称 对会员名称做修改
			UserEntity userUpdate=new UserEntity();
			userUpdate.setId(userNameModify.getUserId());//会员id
			userUpdate.setUserName(userNameModify.getUserNewName());//会员修改后的名称 
			userService.updateById(userUpdate);
			//3.修改真实姓名审核实体的状态
			UserNameModifyEntity UserNameModifyUpdate = new UserNameModifyEntity();
			UserNameModifyUpdate.setId(userNameModify.getId());//要修改的id
			UserNameModifyUpdate.setStatus(2);//状态改为完成
			UserNameModifyUpdate.setCheckerAccount(getUser().getUsername());//审核人账号
			userNameModifyService.updateById(UserNameModifyUpdate);
		}
		return R.ok();
	}
	
	/**
	 * 修改会员状态为驳回  --可批量驳回
	 */
	@RequestMapping("/updateStatusFail")
	@RequiresPermissions("usernamemodify:usernamemodify:delete")//驳回权限
	public R updateStatusFail(@RequestBody Long[] ids) {
		for (Long id : ids) {
			//1.通过id获取  真实姓名审核实体userNameModify
			UserNameModifyEntity userNameModify = userNameModifyService.selectById(id);
			//2.修改真实姓名审核实体的状态
			UserNameModifyEntity UserNameModifyUpdate = new UserNameModifyEntity();
			UserNameModifyUpdate.setId(userNameModify.getId());//要修改的id
			UserNameModifyUpdate.setStatus(1);//状态改为驳回
			UserNameModifyUpdate.setCheckerAccount(getUser().getUsername());//审核人账号
			userNameModifyService.updateById(UserNameModifyUpdate);
		}
		return R.ok();
	}
	//=========================================因为确认和驳回的时候要添加备注，所以重写方法===================
	/**
	 * 修改状态为成功  2	--
	 */
	@RequestMapping("/updateStatusSuccessTwo")
	@RequiresPermissions("usernamemodify:usernamemodify:update")
	public R updateStatusSuccess2(@RequestBody UserNameModifyEntity userNameModify) {
		//2.通过 真实姓名审核userNameModify的 会员id  与修改后的名称 对会员名称做修改
		UserEntity userUpdate=new UserEntity();
		userUpdate.setId(userNameModify.getUserId());//会员id
		userUpdate.setUserName(userNameModify.getUserNewName());//会员修改后的名称 
		userService.updateById(userUpdate);
		//3.修改真实姓名审核实体的状态
		UserNameModifyEntity UserNameModifyUpdate = new UserNameModifyEntity();
		UserNameModifyUpdate.setId(userNameModify.getId());//要修改的id
		UserNameModifyUpdate.setStatus(2);//状态改为完成
		UserNameModifyUpdate.setCheckerAccount(getUser().getUsername());//审核人账号
		UserNameModifyUpdate.setRemake(userNameModify.getRemake());//修改备注
		userNameModifyService.updateById(UserNameModifyUpdate);
		return R.ok();
	}
	/**
	 * 修改会员状态为驳回  2	--
	 */
	@RequestMapping("/updateStatusFailTwo")
	@RequiresPermissions("usernamemodify:usernamemodify:delete")
	public R updateStatusFail2(@RequestBody UserNameModifyEntity userNameModify) {
		//2.修改真实姓名审核实体的状态
		UserNameModifyEntity UserNameModifyUpdate = new UserNameModifyEntity();
		UserNameModifyUpdate.setId(userNameModify.getId());//要修改的id
		UserNameModifyUpdate.setStatus(1);//状态改为驳回
		UserNameModifyUpdate.setCheckerAccount(getUser().getUsername());//审核人账号
		UserNameModifyUpdate.setRemake(userNameModify.getRemake());//修改备注
		userNameModifyService.updateById(UserNameModifyUpdate);
		return R.ok();
	}
}
