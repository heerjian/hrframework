package com.hframwork.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

public class JpaPageResolverHandler extends PageableHandlerMethodArgumentResolver {

	@SuppressWarnings("unlikely-arg-type")
	public Pageable resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		Pageable pageable = super.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
		List<Order> orders = new ArrayList();
		if (StringUtils.isNoneBlank(webRequest.getParameterValues(com.hframwork.Emun.Direction.DESC.name))) {
			List<String> list = Arrays.asList(webRequest.getParameterValues(com.hframwork.Emun.Direction.DESC.name));
			list.forEach(s -> {
				orders.add(Order.desc(s));
			});
		}
		if (StringUtils.isNoneBlank(webRequest.getParameterValues(com.hframwork.Emun.Direction.ASC.name))) {
			List<String> list = Arrays.asList(webRequest.getParameterValues(com.hframwork.Emun.Direction.ASC.name));
			list.forEach(s -> {
				orders.add(Order.asc(s));
			});
		}
		Sort sort = null;
		if (orders.size() != 0) {
			sort = Sort.by(orders);
		} else {
			sort = pageable.getSort();
		}

		return PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), sort);
	}

}
