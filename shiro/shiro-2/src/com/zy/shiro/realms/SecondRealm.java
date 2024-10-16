package com.zy.shiro.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

public class SecondRealm extends AuthenticatingRealm {

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("doGetAuthenticationInfo------->>>>>>>>>2"+token);
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
		//1). principal: 认证的实体信息. 可以是 username, 也可以是数据表对应的用户的实体类对象. 
		Object principal = username;
		//2). credentials: 密码. 
		Object credentials = ""; //"fc1709d0a95a6be30bc5926fdb7f22f4";
		if("123".equals(username)) {
			credentials="07c9d1b884c46800598c2730063fa009c69dadee---";
		}else if("admin".equals(username)) {
			credentials="ce2f6417c7e1d32c1d81a797ee0b499f87c5de06---";
		}
		//3). realmName: 当前 realm 对象的 name. 调用父类的 getName() 方法即可
		String realmName = getName();
		//4)多加个盐，密码就不一样
		ByteSource credentialsSalt=ByteSource.Util.bytes(username);
		
		SimpleAuthenticationInfo info=null;
		info=new SimpleAuthenticationInfo(principal,credentials,credentialsSalt,realmName);
		return info;
	}
	public static void main(String[] args) {
		String hashAlgorithmName="SHA1";
		Object credentials="123456";
		Object salt=ByteSource.Util.bytes("123");
		int hashIterations=1024;
		Object result=new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
		System.out.println(result);
	}

}
