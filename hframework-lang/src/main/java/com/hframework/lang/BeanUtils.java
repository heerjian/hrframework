package com.hframework.lang;


import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 属性复制扩展。
 * @author heerjian
 * @Date 2018年1月4日
 */
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {

	/** 默认赋值符 */
	private static final String DEFAULT_VALUATOR = "=";
	/** 默认分隔符 */
	private static final String DEFAULT_SEPARATOR = "&";

	/**
	 * 复制属性值，忽略指定的属性。支持Map。
	 * 
	 * @param orig
	 *            源对象。支持Map。
	 * @param dest
	 *            目标对象。支持Map。
	 * @param ignoreProperties
	 *            不复制的属性名。
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T copyPropertiesIgnore(final Object orig, final T dest, String... ignoreProperties)
			throws IllegalAccessException, InvocationTargetException {
		PropertyUtilsBean propertyUtilsBean = BeanUtilsBean.getInstance().getPropertyUtils();
		// Copy the properties, converting as necessary
		if (orig instanceof DynaBean) {
			final DynaProperty[] origDescriptors = ((DynaBean) orig).getDynaClass().getDynaProperties();
			for (DynaProperty origDescriptor : origDescriptors) {
				final String name = origDescriptor.getName();
				if (ArrayUtils.contains(ignoreProperties, name))
					continue;
				// Need to check isReadable() for WrapDynaBean
				// (see Jira issue# BEANUTILS-61)
				if (propertyUtilsBean.isReadable(orig, name)) {
					if (dest instanceof Map) {
						final Object value = ((DynaBean) orig).get(name);
						((Map) dest).put(name, value);
					} else if (propertyUtilsBean.isWriteable(dest, name)) {
						final Object value = ((DynaBean) orig).get(name);
						copyProperty(dest, name, value);
					}
				}
			}
		} else if (orig instanceof Map) {
			// Map properties are always of type <String, Object>
			final Map<String, Object> propMap = (Map<String, Object>) orig;
			for (final Map.Entry<String, Object> entry : propMap.entrySet()) {
				final String name = entry.getKey();
				if (ArrayUtils.contains(ignoreProperties, name))
					continue;
				if (dest instanceof Map) {
					((Map) dest).put(name, entry.getValue());
				} else if (propertyUtilsBean.isWriteable(dest, name)) {
					copyProperty(dest, name, entry.getValue());
				}
			}
		} else /* if (orig is a standard JavaBean) */ {
			final PropertyDescriptor[] origDescriptors = propertyUtilsBean.getPropertyDescriptors(orig);
			for (PropertyDescriptor origDescriptor : origDescriptors) {
				final String name = origDescriptor.getName();
				if ("class".equals(name)) {
					continue; // No point in trying to set an object's class
				}
				if (ArrayUtils.contains(ignoreProperties, name))
					continue;
				if (propertyUtilsBean.isReadable(orig, name)) {
					try {
						if (dest instanceof Map) {
							final Object value = propertyUtilsBean.getSimpleProperty(orig, name);
							((Map) dest).put(name, value);
						} else if (propertyUtilsBean.isWriteable(dest, name)) {
							final Object value = propertyUtilsBean.getSimpleProperty(orig, name);
							copyProperty(dest, name, value);
						}
					} catch (final NoSuchMethodException e) {
						// Should not happen
					}
				}
			}
		}
		return dest;
	}

	/**
	 * 复制属性值，只复制指定的属性。支持Map。
	 * 
	 * @param orig
	 *            源对象。支持Map。
	 * @param dest
	 *            目标对象。支持Map。
	 * @param onlyProperties
	 *            复制的属性名。
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T copyPropertiesOnly(final Object orig, final T dest, String... onlyProperties)
			throws IllegalAccessException, InvocationTargetException {
		if (onlyProperties == null || onlyProperties.length == 0) {
			return dest;
		}

		PropertyUtilsBean propertyUtilsBean = BeanUtilsBean.getInstance().getPropertyUtils();
		// Copy the properties, converting as necessary
		if (orig instanceof DynaBean) {
			final DynaProperty[] origDescriptors = ((DynaBean) orig).getDynaClass().getDynaProperties();
			for (DynaProperty origDescriptor : origDescriptors) {
				final String name = origDescriptor.getName();
				// Need to check isReadable() for WrapDynaBean
				// (see Jira issue# BEANUTILS-61)
				if (ArrayUtils.contains(onlyProperties, name) && propertyUtilsBean.isReadable(orig, name)) {
					if (dest instanceof Map) {
						final Object value = ((DynaBean) orig).get(name);
						((Map) dest).put(name, value);
					} else if (propertyUtilsBean.isWriteable(dest, name)) {
						final Object value = ((DynaBean) orig).get(name);
						copyProperty(dest, name, value);
					}
				}
			}
		} else if (orig instanceof Map) {
			// Map properties are always of type <String, Object>
			final Map<String, Object> propMap = (Map<String, Object>) orig;
			for (final Map.Entry<String, Object> entry : propMap.entrySet()) {
				final String name = entry.getKey();
				if (ArrayUtils.contains(onlyProperties, name)) {
					if (dest instanceof Map) {
						((Map) dest).put(name, entry.getValue());
					} else if (propertyUtilsBean.isWriteable(dest, name)) {
						copyProperty(dest, name, entry.getValue());
					}
				}
			}
		} else {
			/* if (orig is a standard JavaBean) */
			final PropertyDescriptor[] origDescriptors = propertyUtilsBean.getPropertyDescriptors(orig);
			for (PropertyDescriptor origDescriptor : origDescriptors) {
				final String name = origDescriptor.getName();
				if ("class".equals(name)) {
					continue; // No point in trying to set an object's class
				}
				if (ArrayUtils.contains(onlyProperties, name) && propertyUtilsBean.isReadable(orig, name)) {
					try {
						if (dest instanceof Map) {
							final Object value = propertyUtilsBean.getSimpleProperty(orig, name);
							((Map) dest).put(name, value);
						} else if (propertyUtilsBean.isWriteable(dest, name)) {
							final Object value = propertyUtilsBean.getSimpleProperty(orig, name);
							copyProperty(dest, name, value);
						}
					} catch (final NoSuchMethodException e) {
						// Should not happen
					}
				}
			}
		}
		return dest;
	}

	/**
	 * 按属性名的ascII码顺序连接属性名和值。支持Map。(默认赋值符“=”；默认分隔符“&”；对值进行URL编码)。
	 * 
	 * @param bean
	 *            Bean对象。支持Map。
	 * @param urlEncodeCharset
	 *            URL编码时的编码格式，为null表示不进行URL编码。
	 * @param ignoreProperties
	 *            忽略的属性。
	 * @return 连接后的字符串。
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws UnsupportedEncodingException
	 */
	public static String joinSortByAscII(Object bean, String urlEncodeCharset, String... ignoreProperties)
			throws IllegalAccessException, InvocationTargetException, UnsupportedEncodingException {
		return joinSortByAscII(bean, DEFAULT_VALUATOR, DEFAULT_SEPARATOR, urlEncodeCharset, ignoreProperties);
	}

	/**
	 * 按属性名的ascII码顺序连接属性名和值。支持Map。(默认赋值符“=”；默认分隔符“&”；不对值进行URL编码)。
	 * 
	 * @param bean
	 *            Bean对象。支持Map。
	 * @param ignoreProperties
	 *            忽略的属性。
	 * @return 连接后的字符串。
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws UnsupportedEncodingException
	 */
	public static String joinSortByAscII(Object bean, String... ignoreProperties)
			throws IllegalAccessException, InvocationTargetException, UnsupportedEncodingException {
		return joinSortByAscII(bean, DEFAULT_VALUATOR, DEFAULT_SEPARATOR, null, ignoreProperties);
	}

	/**
	 * 按属性名的ascII码顺序连接属性名和值，忽略空值的属性。支持Map。
	 * 
	 * @param bean
	 *            Bean对象。支持Map。
	 * @param valuator
	 *            赋值符，一般为“=”。
	 * @param separator
	 *            分隔符，一般为“&”。
	 * @param urlEncodeCharset
	 *            URL编码时的编码格式，为null表示不进行URL编码。
	 * @param ignoreProperties
	 *            忽略的属性。
	 * @return 连接后的字符串。
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws UnsupportedEncodingException
	 */
	public static String joinSortByAscII(Object bean, String valuator, String separator, String urlEncodeCharset,
			String... ignoreProperties)
			throws IllegalAccessException, InvocationTargetException, UnsupportedEncodingException {
		final TreeMap<String, Object> map = copyPropertiesIgnore(bean, new TreeMap<>(), ignoreProperties);
		final StringBuffer str = new StringBuffer();
		for (final Map.Entry<String, Object> entry : map.entrySet()) {
			final String name = entry.getKey();
			final String value = BeanUtilsBean.getInstance().getConvertUtils().convert(entry.getValue());
			if (StringUtils.isNotEmpty(value)) {
				str.append(separator).append(name).append(valuator).append(
						StringUtils.isNotEmpty(urlEncodeCharset) ? URLEncoder.encode(value, urlEncodeCharset) : value);
			}
		}
		return str.length() != 0 ? str.substring(separator.length()) : "";
	}
}
