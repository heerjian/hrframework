package com.hframework.lang;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;


/**
 * Created by josia on 2017/5/16.
 */

public class GsonUtil {
	  private static Gson gson = null;  
	    //没加载的话加载一次  
	    static {  
	        if (gson == null) {  
	            gson = new Gson();  
	        }  
	    }  
	  
	    private GsonUtil() {  
	    } 
        // 将Json数据解析成相应的映射对象
        public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
            Gson gson = new Gson();
            T result = gson.fromJson(jsonData, type);
            return result;
        }
        
    	public static void doGson(Object o, HttpServletRequest req,
    			HttpServletResponse resp) {
    		Gson gson = new Gson();
    		String gsonString = gson.toJson(o);
    		//指定HTTP响应的字符集编码
    		resp.setCharacterEncoding("utf-8");
    		resp.setContentType("text/html;charset=utf-8");
    		PrintWriter pw = null;
    		try {
    			pw = resp.getWriter();
    			pw.println(gsonString);
    			pw.flush();
    		} catch (IOException e) {
    			e.printStackTrace();
    		} finally {
    			if (pw != null)
    				pw.close();
    		}

    	}
    	
    	
    	
    	  /** 
         * JSON转成list集合 
         *  
         * @param gsonString 
         * @param cls 
         * @return 
         */  
        public static <T> List<T> GsonToList(String gsonStr, Class<T> cls) {  
            List<T> list = null;  
            if (gson != null) {  
                list = gson.fromJson(gsonStr, new TypeToken<List<T>>() {  
                }.getType());  
            }  
            return list;  
        } 
        
        /**
         * 转成list
         * 解决泛型问题
         * @param json
         * @param cls
         * @param <T>
         * @return
         */
        public static <T> List<T> jsonToList(String json, Class<T> cls) {
            Gson gson = new Gson();
            List<T> list = new ArrayList<T>();
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for(final JsonElement elem : array){
                list.add(gson.fromJson(elem, cls));
            }
            return list;
        }

    	
    	public static String toJson(Object o) {
    		Gson gson = new Gson();
    		return gson.toJson(o);

	}

}
