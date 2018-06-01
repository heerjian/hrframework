package com.hframwork.handler;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;

public class RequestUtils {
	public static Object getParameter(String parame, NativeWebRequest webRequest) {
		if (StringUtils.isNoneBlank(webRequest.getParameterValues(parame))) {
			if(webRequest.getParameterValues(parame).length==1) {
				return Arrays.asList(webRequest.getParameterValues(parame));
			}
			return Arrays.asList(webRequest.getParameterValues(parame));
		}
		return null;
	}
}
