package com.hframework.lang;


import javax.servlet.http.HttpServletResponse;

/**
 * 静止后退
 * 
 * @author heerjian
 *
 */
public class ResponseUtil {
	/**
	 * 禁用缓存
	 * @param response
	 */
	public static void banCache(HttpServletResponse response) {
		 response.setHeader("Cache-Control", "no-cache");
         response.setHeader("pragma", "no-cache");
         response.setDateHeader("expires", -1);
	}

}
