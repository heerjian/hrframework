package com.hframework.shiro;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * 权限解析器。Shiro默认的权限解析器不区分大小写。
 * 
 * @author Future
 * @date 2017年2月18日
 */
public class WildcardPermissionResolver implements PermissionResolver {

	@Override
	public Permission resolvePermission(String permissionString) {
		return new WildcardPermission(permissionString, true);// 区分权限大小写
	}

}
