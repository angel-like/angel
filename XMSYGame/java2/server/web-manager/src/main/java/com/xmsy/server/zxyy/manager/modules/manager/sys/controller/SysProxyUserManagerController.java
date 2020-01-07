package com.xmsy.server.zxyy.manager.modules.manager.sys.controller;



import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.modules.manager.proxytransferrecord.entity.ProxyTransferRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.proxytransferrecord.service.ProxyTransferRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.SysUserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sys.service.SysUserService;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.Constant;

/**
 * 系统代理商下的  会员管理
 * 
 * @author axiong
 * @email xxxxxx
 * @date 2019年8月6日 上午10:40:10
 */
@RestController
@RequestMapping("/sys/proxy")
public class SysProxyUserManagerController extends AbstractController{
	@Autowired
    private UserService userService;
	@Autowired
    private SysUserService sysUserService;
	@Autowired
    private ProxyTransferRecordService proxyTransferRecordService;
	/**
	 * 获取当前代理商下的会员信息
	 * @param pageParam
	 * @param user
	 * @return
	 */
	@RequestMapping("/proxyuserinfo")
	@RequiresPermissions("sys:proxy:list")
	public R proxyuserinfo(PageParam pageParam,UserEntity user) {
		Page<UserEntity> page = new Page<>(pageParam.getPage(), pageParam.getLimit());
		SysUserEntity sysUser = getUser();//获取当前登录的代理商实体
		List<UserEntity> listUser=null;
		if(sysUser.getRoleIds().equals(SysConstant.PROXYID)) {
			//去用户信息补充表里查询当前代理商对应的几个用户id,通过用户id查询对应用户的信息
			user.setProxyId(sysUser.getUserId());
			listUser= userService.findProxyUser(page, user);
		}else {
			listUser= userService.findProxyUser(page, user);
		}
		page.setRecords(listUser);
		return R.ok().put("page",new PageUtils(page));
	}
	
	/**
	 * 获取当前会员信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/userinfo/{id}")
	@RequiresPermissions("sys:proxy:info")
	public R userInfo(@PathVariable("id") Long id) {
		UserEntity user = userService.selectById(id);
		return R.ok().put("user",user);
	}
	

	/**
	 * 给当前用户  划拨金币
	 * @param user
	 * @return
	 */
	@RequestMapping("/transfer")
	@RequiresPermissions("sys:proxy:transfer")
	public R transfer(@RequestBody UserEntity user) {
		//1.代理商的余额要扣去
		SysUserEntity sysUser = getUser();
		if(!sysUser.getRoleIds().equals(SysConstant.PROXYID)) {//只有代理商能进行此操作
			throw new RRException(ErrorCode.AgentErrorCode.AGENT_ID_ERRO.getErrMsg(),
					ErrorCode.AgentErrorCode.AGENT_ID_ERRO.getCode());
		}
		if(sysUser.getProxyBalance()<user.getCoin()) {//代理商余额不足
			throw new RRException(ErrorCode.AgentErrorCode.AGENT_COIN_NOT_ENOUGH.getErrMsg(),
					ErrorCode.AgentErrorCode.AGENT_COIN_NOT_ENOUGH.getCode());
		}
		sysUserService.updateProxyTotalCoins(sysUser.getUsername(), user.getCoin()*-1,user.getCoin());
		//2.用户增加金币
		userService.updateUserCoin(user, user.getCoin(), Constant.TransactionType.TRANSFER.getValue(), Constant.TransactionDetailType.PROXY_TRANSFER.getValue());
		//3.增加划拨记录
		ProxyTransferRecordEntity proxyTransferRecord=new ProxyTransferRecordEntity();
		proxyTransferRecord.setUserId(user.getId());//会员id
		proxyTransferRecord.setUserName(user.getUserName());//会员名称
		proxyTransferRecord.setUserAccount(user.getAccount());//会员账户
		proxyTransferRecord.setTransferCoin(new BigDecimal(user.getCoin()));//划拨金额
		proxyTransferRecord.setProxyId(sysUser.getUserId());//代理商id
		proxyTransferRecord.setOrderNo(getOrderIdByTime());//划拨编号
		
		proxyTransferRecordService.insert(proxyTransferRecord);
		
		return R.ok();
	}
	//随机产生一个订单编号
    private String getOrderIdByTime() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<3;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
    }
}
