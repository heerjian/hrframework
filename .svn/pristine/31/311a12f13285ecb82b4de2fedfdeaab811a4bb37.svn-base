package com.hframework.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.servlet.AbstractShiroFilter;

/**
 * 
 * @ClassName: DecoupledShiroFilterFactoryBean
 * @Description: 与应用解耦的Shiro过滤器工厂类。
 * @author: heerjian
 * @date: 2018年3月7日 上午12:35:39
 */
public class DecoupledShiroFilterFactoryBean extends ShiroFilterFactoryBean {

	/**
	 * 保存用户信息的Session名称。
	 */
	private String userSessionName;

	public String getUserSessionName() {
		return userSessionName;
	}

	public void setUserSessionName(String userSessionName) {
		this.userSessionName = userSessionName != null ? userSessionName.trim() : userSessionName;
	}

	@Override
	protected AbstractShiroFilter createInstance() throws Exception {
		AbstractShiroFilter filter = super.createInstance();

		DecoupledShiroFilter newFilter = new DecoupledShiroFilter();
		newFilter.setSecurityManager(filter.getSecurityManager());
		newFilter.setFilterChainResolver(filter.getFilterChainResolver());
		if (getUserSessionName() != null) {
			newFilter.setUserSessionName(getUserSessionName());
		}

		return newFilter;
	}

	@Override
	public Class<?> getObjectType() {
		return DecoupledShiroFilter.class;
	}

}
