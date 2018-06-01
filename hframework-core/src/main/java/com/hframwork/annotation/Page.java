package com.hframwork.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.data.domain.Sort.Direction;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Page {

	/**
	 * Alias for {@link #size()}. Prefer to use the {@link #size()} method as it
	 * makes the annotation declaration more expressive and you'll probably want to
	 * configure the {@link #page()} anyway.
	 * 
	 * @return
	 */
	int value() default 10;

	/**
	 * The default-size the injected
	 * {@link org.springframework.data.domain.Pageable} should get if no
	 * corresponding parameter defined in request (default is 10).
	 */
	int size() default 10;

	/**
	 * The default-pagenumber the injected
	 * {@link org.springframework.data.domain.Pageable} should get if no
	 * corresponding parameter defined in request (default is 0).
	 */
	int page() default 0;

	/**
	 * The properties to sort by by default. If unset, no sorting will be applied at
	 * all.
	 * 
	 * @return
	 */
	String[] sort() default {};

	/**d Defaults to {@link Direction#ASC}.
	 * 
	 * @return
	 */
	Direction direction() default Direction.ASC;
}
