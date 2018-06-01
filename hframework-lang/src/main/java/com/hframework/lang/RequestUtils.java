package com.hframework.lang;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * 从请求中获取常用的信息。
 * 
 * @author heerjian
 * @date 2015年6月11日
 */
public class RequestUtils {

	/**
	 * 返回客户端IP地址。
	 * 
	 * @param request
	 * @return
	 */
	public static String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return StringUtils.trim(ip);
	}

	/**
	 * 返回去掉Context路径之后的URI。
	 * 
	 * @param request
	 * @return
	 */
	public static String getRealUri(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String ctx = request.getServletContext().getContextPath() + "/";
		if (uri.startsWith(ctx)) {
			return uri.substring(ctx.length() - 1);
		}
		return uri;
	}

	/**
	 * 返回去除Context和Servlet路径之后的URI。
	 * 
	 * @param request
	 * @return
	 */
	public static String getRealPath(HttpServletRequest request) {
		String uri = getRealUri(request);
		String servletPath = request.getServletPath() + "/";
		if ((uri.endsWith("/") ? uri : (uri + "/")).startsWith(servletPath))
			return uri.substring(servletPath.length() - 1);
		return uri;
	}

	/**
	 * 构建完整的URL。
	 * 
	 * @param request
	 *            请求。
	 * @param uri
	 *            必须以“/”开头。
	 * @return
	 */
	public static String buildUrl(HttpServletRequest request, String uri) {
		StringBuffer s = new StringBuffer();
		s.append(request.getScheme()).append("://").append(request.getServerName());
		int port = request.getServerPort();
		if (port != 80)
			s.append(":").append(port);
		s.append(request.getContextPath()).append(uri);
		return s.toString();
	}
}
