package com.zy.shiro.realms;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
								//继承AuthorizingRealm，可以再添加授权方法
public class ShiroRealm extends AuthorizingRealm {//原先继承这个AuthenticatingRealm，只能实现认证方法
	/**
	 * 授权方法
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("授权方法----doGetAuthorizationInfo：");
		//1.从PrincipalCollection获取用户登录信息
		Object principal=principals.getPrimaryPrincipal();//获取的是用户账户
		//2.利用登录的用户信息来判断用户当前的角色或者权限（可能要查数据库）
		Set<String> roles=new HashSet<>();
		//去查询数据库权限，然后把权限信息放进roles里 交给SimpleAuthorizationInfo对象病返回就有该权限
		roles.add("123");
		if("admin".equals(principal)) {
			roles.add("admin");
		}
		
		//3.创建SimpleAuthorizationInfo,并设置reles属性。并返回对象
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo(roles);
		return info;
	}
	/**
	 * 认证方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("doGetAuthenticationInfo------->>>>>>>>>1"+token);
		//1.AuthenticationToken转为UsernamePasswordToken
		UsernamePasswordToken uptoken=(UsernamePasswordToken)token;
		
		//2.从UsernamePasswordToken中获取username
		String username=uptoken.getUsername();
		
		//3.调用数据库，查询
		boolean flag=username.equals("123")||username.equals("admin");
		//4.2 根据用户信息情况，是否抛出AuthenticationException异常
		if("1234".equals(username)) {
			throw new LockedAccountException("用户被锁定");
		}
		//4.1 用户不存在，抛出UnknownAccountException异常
		if(!flag) {
			throw new UnknownAccountException("用户不存在");
		}
				
		
		//5.根据用户情况，构建AuthenticationInfo对象并返回
		//以下信息是从数据库中获取的.
		//1). principal: 认证的实体信息. 可以是 username, 也可以是数据表对应的用户的 实体类对象. 
		Object principal = username;
		//2). credentials: 密码. 
		Object credentials = ""; //"fc1709d0a95a6be30bc5926fdb7f22f4";
		if("123".equals(username)) {
			credentials="b2793335f43645fd8e00c7d18e14e05f";
		}else if("admin".equals(username)) {
			credentials="038bdaf98f2037b31f1e75b5b4c9b26e";
		}
		//3). realmName: 当前 realm 对象的 name. 调用父类的 getName() 方法即可
		String realmName = getName();
		//4)多加个盐，密码就不一样
		ByteSource credentialsSalt=ByteSource.Util.bytes(username);
		
		SimpleAuthenticationInfo info=null;
		info=new SimpleAuthenticationInfo(principal,credentials,credentialsSalt,realmName);
		return info;
	}
	/**
	 * 测试加密后的密码
	 * @param args
	 */
	public static void main(String[] args) {
		String hashAlgorithmName="md5";
		Object credentials="123456";
		Object salt=ByteSource.Util.bytes("admin");
		int hashIterations=15;
		Object result=new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
		System.out.println(result);
	}
	

}
