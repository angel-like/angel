package com.xmsy.server.zxyy.manager.modules.manager.userpointkill.controller;

import java.math.BigDecimal;
import java.util.Date;

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
import com.xmsy.server.zxyy.manager.common.utils.MathUtil;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.sys.controller.AbstractController;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
//import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.modules.manager.userpointkill.entity.UserPointKillEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userpointkill.service.UserPointKillService;
import com.xmsy.server.zxyy.manager.modules.app.event.PushService;
import com.xmsy.server.zxyy.manager.modules.app.event.userinfo.UserInfoMessage;

/**
 * 点杀名单
 *
 * @author aye
 * @email xxxxx
 * @date 2019-11-22 11:27:03
 */
@RestController
@RequestMapping("userpointkill/userpointkill")
public class UserPointKillController extends AbstractController{
	@Autowired
	private UserPointKillService userPointKillService;
	/*@Autowired
	private UserService userService;*/
	@Autowired
	private PushService pushService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("userpointkill:userpointkill:list")
	public R list(UserPointKillEntity userpointkill, PageParam pageParam) {
		Page<UserPointKillEntity> result = new Page<UserPointKillEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<UserPointKillEntity> entityWrapper = new EntityWrapper<UserPointKillEntity>(userpointkill).orderBy("id", false);
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		if(userpointkill.getStartTime()!=null) {
			entityWrapper.gt("create_time", userpointkill.getStartTime());
			if(userpointkill.getEndTime()!=null) {
				entityWrapper.le("create_time", userpointkill.getEndTime());
			}
		}
		userpointkill.selectPage(result, entityWrapper);
		for(UserPointKillEntity UserPointKillEntity:result.getRecords()) {
			BigDecimal totalCoin = userPointKillService.gainTotalCoin(UserPointKillEntity.getUserId());
			UserPointKillEntity.setTotalCoin(totalCoin);
		}
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 设置点杀 --就是来保存
	 */
	@RequestMapping("/makeuserpointkill")
	@RequiresPermissions("userpointkill:userpointkill:save") // 权限也是点杀名单的权限
	public R makeUserPointKill(@RequestBody UserPointKillEntity userpointkill) {
		// 1.修改会员表点杀状态  + 点杀概率--字段暂时没加
		UserEntity user = new UserEntity();
		user.setId(userpointkill.getUserId());
		user.setPointKillEnable(true);//设置会员处于点杀的状态
		user.setPointKillRate(userpointkill.getPointKillRate());//点杀概率
		user.updateById();
		// 2.点杀名单增加该记录  ----会员账号  会员id  点杀概率   解除退出金额 自带  
		userpointkill.setRemainAmount(userpointkill.getRemoveAmount());//剩下退出金额
		userpointkill.setPointKillEnable(true);//设置点杀名单的状态
		userpointkill.setSysUserPointKill(getUser().getUsername());//设置点杀名单的操作人员
		userPointKillService.insert(userpointkill);
		// 3.通知游服进入黑名单
		//UserEntity userEntity = userService.selectById(userpointkill.getUserId());
		UserInfoMessage userInfoMessage=new UserInfoMessage();
		userInfoMessage.setUid(userpointkill.getUserId());//会员id
		userInfoMessage.setPointKillEnable(true);//点杀状态
		userInfoMessage.setPointKillRate(userpointkill.getPointKillRate());//点杀概率
		userInfoMessage.setRemoveAmount(userpointkill.getRemoveAmount());//解除退出金额
		pushService.pushServerUserInfo(userInfoMessage);
		return R.ok();
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("userpointkill:userpointkill:info")
	public R info(@PathVariable("id") Integer id) {
		UserPointKillEntity userPointKill = userPointKillService.selectById(id);
		BigDecimal totalCoin = userPointKillService.gainTotalCoin(userPointKill.getUserId());
		userPointKill.setTotalCoin(totalCoin);
		return R.ok().put("userpointkill", userPointKill);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("userpointkill:userpointkill:save")
	public R save(@RequestBody UserPointKillEntity userpointkill) {
		userPointKillService.insert(userpointkill);
		return R.ok();
	}

	/**
	 * 解除点杀---就是修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("userpointkill:userpointkill:update")
	public R update(@RequestBody UserPointKillEntity userpointkill) {
		
		//1.修改会员状态
		UserEntity user = new UserEntity();
		user.setId(userpointkill.getUserId());
		user.setPointKillEnable(false);
		user.setPointKillRate(MathUtil.getBigDecimal(0));
		user.updateById();
		//2.修改退出原因  
		userpointkill.setSysUserAccount(getUser().getUsername());//操作人
		userpointkill.setOperationTime(new Date());//操作时间
		userpointkill.setPointKillEnable(false);//点杀名单状态改为已解除
		userPointKillService.updateById(userpointkill);
		//3.通知游服退出黑名单
		//UserEntity userEntity = userService.selectById(userpointkill.getUserId());
		UserInfoMessage userInfoMessage=new UserInfoMessage();
		userInfoMessage.setUid(userpointkill.getUserId());
		userInfoMessage.setPointKillEnable(false);
		pushService.pushServerUserInfo(userInfoMessage);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("userpointkill:userpointkill:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			userPointKillService.deleteById(id);
		}
		return R.ok();
	}

}
