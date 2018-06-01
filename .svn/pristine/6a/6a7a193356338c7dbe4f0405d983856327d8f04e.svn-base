package com.hframework.shiro;


import java.util.Collection;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 从Session中认证登录用户，并获取用户角色和权限。需要登录的用户实现{@link ShiroUser}接口才能获取用户角色和权限。
 * 
 * @author Future
 * @date 2015年6月13日
 */
public class SessionUserRealm extends SimpleAuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		Object principal = principals.getPrimaryPrincipal();
		if (principal instanceof ShiroUser) {
			ShiroUser user = (ShiroUser) principal;
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

			Collection<String> coll = user.getRoles();
			if (coll != null && !coll.isEmpty())
				info.addRoles(coll);

			coll = user.getPermissions();
			if (coll != null && !coll.isEmpty())
				info.addStringPermissions(coll);

			return info;
		}
		return null;
	}

}
