package com.h.shiro.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.h.shiro.HelloWord;

public class ShiroUtil {
	private static final Logger logger = LoggerFactory.getLogger(HelloWord.class);

	public static Subject login(String configFile, String username, String password) {
		// 读取配置文件，初始化SecurityManager功能
		Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);
		// 获取SecurityManager实例
		SecurityManager securityManager = factory.getInstance();
		// 将securityManager绑定到SecurityUtils
		SecurityUtils.setSecurityManager(securityManager);
		// 得到当前执行的用户
		Subject currentUser = SecurityUtils.getSubject();
		// 创建Token令牌，用户密码
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			currentUser.login(token);
			logger.info("登陆成功");
		} catch (AuthenticationException e) {
			logger.error("登陆失败");
		}

		// 创建Token令牌，用户密码
		return currentUser;
	}

}
