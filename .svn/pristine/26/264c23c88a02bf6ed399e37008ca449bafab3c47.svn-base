package com.hframwork.handler;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.baomidou.mybatisplus.plugins.Page;
import com.hframwork.Emun.Direction;

public class MybatisPageResolverHandler implements HandlerMethodArgumentResolver {

	private static final String DEFAULT_PAGE_PARAMETER = "page";
	private static final String DEFAULT_SIZE_PARAMETER = "size";

	public boolean supportsParameter(MethodParameter parameter) {

		if (parameter.getParameterType().equals(com.baomidou.mybatisplus.plugins.Page.class)) {
			return true;
		}
		return false;

	}

	@SuppressWarnings("unused")
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		Page<Object> page = new Page();
		if (StringUtils.isNoneBlank(webRequest.getParameterValues(Direction.DESC.name))) {
			page.setDescs(Arrays.asList(webRequest.getParameterValues(Direction.DESC.name)));
		}
		if (StringUtils.isNoneBlank(webRequest.getParameterValues(Direction.ASC.name))) {
			page.setDescs(Arrays.asList(webRequest.getParameterValues(Direction.ASC.name)));
		}
		if (StringUtils.isNoneBlank(webRequest.getParameter(DEFAULT_SIZE_PARAMETER))
				&& StringUtils.isNumeric(webRequest.getParameter(DEFAULT_SIZE_PARAMETER))) {
			page.setSize(Integer.parseInt(webRequest.getParameter(DEFAULT_SIZE_PARAMETER)));
		}
		if (StringUtils.isNoneBlank(webRequest.getParameterValues(DEFAULT_PAGE_PARAMETER))) {
			page.setCurrent(Integer.parseInt(webRequest.getParameter(DEFAULT_PAGE_PARAMETER)) );
		}
		return page;
	}

	private Object getParameter(String parame, NativeWebRequest webRequest) {
		if (StringUtils.isNoneBlank(webRequest.getParameterValues(parame))) {
			if (webRequest.getParameterValues(parame).length == 1) {
				return Arrays.asList(webRequest.getParameterValues(parame));
			}
			return Arrays.asList(webRequest.getParameterValues(parame));
		}
		return null;
	}

}
