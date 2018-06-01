package com.hframework.lang;

import java.util.regex.Pattern;

/**
 * html常用函数。
 * 
 * @author heerjian
 * @date 2015年8月19日
 */
public class HtmlUtils {

	/**
	 * 匹配script的正则表达式
	 */
	private static final Pattern scriptReg = Pattern
			.compile("<script[^>]*?>[\\s\\S]*?<\\/script>");
	/**
	 * 匹配style的正则表达式
	 */
	private static final Pattern styleReg = Pattern
			.compile("<style[^>]*?>[\\s\\S]*?<\\/style>");
	/**
	 * 匹配html标签的正则表达式
	 */
	private static final Pattern htmlTagReg = Pattern.compile("<[^>]+>");

	/**
	 * 去除script内容。
	 * 
	 * @param input
	 * @return
	 */
	public static String trimScript(String input) {
		if (input == null) {
			return input;
		}

		return scriptReg.matcher(input).replaceAll("").trim();
	}

	/**
	 * 去除style内容。
	 * 
	 * @param input
	 * @return
	 */
	public static String trimStyle(String input) {
		if (input == null) {
			return input;
		}

		return styleReg.matcher(input).replaceAll("").trim();
	}

	/**
	 * 去除html标签。
	 * 
	 * @param input
	 * @return
	 */
	public static String trimHtmlTag(String input) {
		if (input == null) {
			return input;
		}

		return htmlTagReg.matcher(input).replaceAll("").trim();
	}

	/**
	 * 去除script、style、html标签。只保留文本内容。
	 * 
	 * @param input
	 * @return
	 */
	public static String trimHtml(String input) {
		return trimHtmlTag(trimStyle(trimScript(input)));
	}
}
