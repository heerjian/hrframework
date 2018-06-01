package com.h.shiro.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public Login() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("login doget");
		request.getRequestDispatcher("login.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
			// 得到当前执行的用户
			Subject currentUser = SecurityUtils.getSubject();
			// 创建Token令牌，用户密码
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			try {
				currentUser.login(token);
				logger.info("登陆成功");
				request.getRequestDispatcher("success.jsp").forward(request, response);   
			} catch (AuthenticationException e) {
				logger.error("登陆失败");
				request.getRequestDispatcher("login.jsp").forward(request, response);   
			}
		}

	}

}
