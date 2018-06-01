package com.hframework.lang;


import java.io.Closeable;
import java.lang.reflect.Method;

/**
 * 关闭对象。
 * 
 * @author heerjian
 * @date 2016年10月22日
 */
public class CloseUtils {

	/**
	 * 执行对象的关闭方法。
	 * 
	 * @param closeable
	 */
	public static void close(Object closeable) {
		if (closeable instanceof Closeable) {
			close((Closeable) closeable);
		} else {
			try {
				Method method = closeable.getClass().getMethod("close");
				method.invoke(closeable);
			} catch (Throwable ignore) {
			}
		}
	}

	/**
	 * 关闭对象。
	 * 
	 * @param closeable
	 */
	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Throwable ignore) {
			}
		}
	}
}
