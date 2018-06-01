package com.hframework.lang;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JacksonUtil {
	private static final ObjectMapper mapper = new ObjectMapper();

	private JacksonUtil() {
	}
	
	/**
	 * json转map
	 * @param json
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> json2Map(String json) throws IOException {
		Map map = (Map) mapper.readValue(json, Map.class);
		return map;
	}
	/**
	 * 对象转json
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public static String object2Json(Object object) throws IOException {
		return mapper.writeValueAsString(object);
	}
	
	/**
	 * json转对象
	 * @param json
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public static <T> T json2Object(String json, Class<T> clazz) throws IOException {
		return mapper.readValue(json, clazz);
	}

	/**
	 *  json转集合
	 * @param json
	 * @param typeRef
	 * @return
	 * @throws IOException
	 */
	public static List<?> json2List(String json, TypeReference<?> typeRef) throws IOException {
		@SuppressWarnings("rawtypes")
		List list = ((List) mapper.readValue(json, typeRef));
		return list;
	}

}
