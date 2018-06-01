package com.h.shiro.permission;

import java.util.Arrays;

import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.h.shiro.HelloWord;
import com.h.shiro.util.ShiroUtil;

public class PermissionTest {
	private static final Logger logger = LoggerFactory.getLogger(HelloWord.class);

	@Test
	public void testIsPermitted() {
		Subject currentUser = ShiroUtil.login("classpath:shiro-permission.ini", "heerjian", "123");
		logger.info(currentUser.isPermitted("select") ? "有select权限" : "没有select权限");
		logger.info(currentUser.isPermitted("delete") ? "有delete权限" : "没有delete权限");
		logger.info(currentUser.isPermitted("update") ? "有update权限" : "没有update权限");
		boolean[] result = currentUser.isPermitted("select", "delete");
		System.out.println(Arrays.toString(result));
		System.out.println(currentUser.isPermittedAll("select"));
	}

	@Test
	public void testCheckPermitted() {
		Subject currentUser = ShiroUtil.login("classpath:shiro-permission.ini", "heerjian", "123");
		currentUser.checkPermission("select");
		currentUser.checkPermission("update");

	}

}
