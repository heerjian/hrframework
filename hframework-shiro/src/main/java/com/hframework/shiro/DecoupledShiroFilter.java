package com.hframework.shiro;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: DecoupledShiroFilter 
 * @Description: 
 * 与应用解耦的Shiro过滤器。<br/>
 * 如果shiro中已登录且Session中不存在登录用户，则执行shiro退出；<br/>
 * 如果shiro中未登录且Session中存在登录用户则执行shiro登录；<br/>
 * 其它情况不执行任何操作。
 * @author: heerjian
 * @date: 2018年3月7日 上午12:35:54
 */
public class DecoupledShiroFilter extends AbstractShiroFilter {
	private static final Logger log = LoggerFactory.getLogger(DecoupledShiroFilter.class);

	private static final String SHIRO_USER = "_$_$_$_ShiroUser";
	private static final String DEFAULT_SESSION_USER_NAME = "USER_SESSION_KEY";

	private String userSessionName = DEFAULT_SESSION_USER_NAME;

	public String getUserSessionName() {
		return userSessionName;
	}

	public void setUserSessionName(String userSessionName) {
		if (userSessionName == null) {
			this.userSessionName = DEFAULT_SESSION_USER_NAME;
		} else {
			this.userSessionName = userSessionName;
		}
	}

	@Override
	protected void executeChain(ServletRequest request, ServletResponse response, FilterChain origChain)
			throws IOException, ServletException {
		beforeExecuteChain(request, response);
		super.executeChain(request, response, origChain);
	}

	/**
	 * 如果shiro中已登录且Session中不存在登录用户，则执行shiro退出；<br/>
	 * 如果shiro中未登录且Session中存在登录用户则执行shiro登录；<br/>
	 * 其它情况不执行任何操作。
	 * 
	 * @param request
	 * @param response
	 */
	protected void beforeExecuteChain(ServletRequest request, ServletResponse response) {
		Subject subject = getSubject(request, response);
		Object sessionUser = getSessionUser(request, response);
		if (subject.isAuthenticated()) {
			if (sessionUser == null) {
				doLogout(subject);
			} else if (!sessionUser.equals(subject.getSession().getAttribute(SHIRO_USER))) {
				doLogin(subject, sessionUser);
			}
		} else {
			if (sessionUser != null) {
				doLogin(subject, sessionUser);
			}
		}
	}

	protected void doLogin(Subject subject, Object sessionUser) {
		try {
			log.debug("正在执行shrio登录...");

			subject.login(new SimpleAuthenticationToken(sessionUser));
			subject.getSession().setAttribute(SHIRO_USER, sessionUser);

			log.debug("shrio登录成功");
		} catch (AuthenticationException e) {
			log.error("shrio登录失败", e);
			throw e;
		}
	}

	protected void doLogout(Subject subject) {
		log.debug("正在执行shrio退出...");
		subject.getSession().removeAttribute(SHIRO_USER);
		SecurityUtils.getSubject().logout();
		log.debug("shrio退出成功");
	}

	/**
	 * 获取session中的登录用户。
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	protected Object getSessionUser(ServletRequest request, ServletResponse response) {
		if (request instanceof HttpServletRequest && !"".equals(getUserSessionName())) {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			return session.getAttribute(getUserSessionName());
		}

		return null;
	}

	protected Subject getSubject(ServletRequest request, ServletResponse response) {
		return SecurityUtils.getSubject();
	}

}
