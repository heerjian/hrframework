package com.hframework.shiro;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 简单的登录认证Realm。只处理登录，不处理用户权限。
 * 
 * @author Future
 * @date 2015年6月13日
 */
public class SimpleAuthorizingRealm extends AuthorizingRealm {

	public SimpleAuthorizingRealm() {
		super();
		setAuthenticationTokenClass(AuthenticationToken.class);
		setPermissionResolver(new WildcardPermissionResolver());
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		if (token.getPrincipal() != null) {
			return new SimpleAuthenticationInfo(token.getPrincipal(),
					token.getCredentials(), getName());
		}
		return null;
	}

}
