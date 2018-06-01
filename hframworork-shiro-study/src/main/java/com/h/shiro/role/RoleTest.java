package com.h.shiro.role;

import java.util.Arrays;

import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.h.shiro.HelloWord;
import com.h.shiro.util.ShiroUtil;

import junit.framework.TestCase;

public class RoleTest extends TestCase {
	private static final Logger logger = LoggerFactory.getLogger(HelloWord.class);

	@Test
	public void testHashRole() {
		Subject currentUser = ShiroUtil.login("classpath:shiro-role.ini", "heerjian", "123");
		boolean[] results = null;
		results = currentUser.hasRoles(Arrays.asList("cwy", "lwy"));
		logger.info(results[0] ? "有cwy角色" : "没有cwy角色");
		logger.info(results[1] ? "有lwy角色" : "没有lwy角色");
		logger.info(currentUser.hasAllRoles(Arrays.asList("cwy", "lwy")) ? "两种角色都有" : "两种角色不全有");
	}

	@Test
	public void testCheckRole() {
		try {

			Subject currentUser = ShiroUtil.login("classpath:shiro-role.ini", "heerjian", "123");
			currentUser.checkRole("cwy");
			currentUser.checkRoles(Arrays.asList("cwy"));
			currentUser.checkRoles("cwy", "lwy");
			currentUser.checkRoles(Arrays.asList("cwy", "lwy"));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	

}
