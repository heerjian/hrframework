package com.hframework.shiro;


import org.apache.shiro.authc.AuthenticationToken;

/**
 * 简单的Shiro认证令牌。
 * 
 * @author Future
 * @date 2015年6月13日
 */
public class SimpleAuthenticationToken implements AuthenticationToken {
	private static final long serialVersionUID = 1L;

	private Object principal;
	private Object credentials;

	public SimpleAuthenticationToken() {
	}

	public SimpleAuthenticationToken(Object principal) {
		this(principal, null);
	}

	public SimpleAuthenticationToken(Object principal, Object credentials) {
		this.principal = principal;
		this.credentials = credentials;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	@Override
	public Object getCredentials() {
		if (credentials == null)
			return principal.hashCode();
		return credentials;
	}

	public void setPrincipal(Object principal) {
		this.principal = principal;
	}

}
