package com.hframework.lang;

/**
 * 数据和byte数据转换。
 * @author heerjian
 * @Date 2018年1月4日
 */
public class ByteUtils {
	/**
	 * short转2个byte。
	 * 
	 * @param n
	 * @return
	 */
	public static byte[] toBytes(short... n) {
		if (n == null)
			return null;

		byte[] re = new byte[2 * n.length];
		for (int i = 0; i < n.length; i++)
			toBytes(re, n[i], i * 2, 8);

		return re;
	}

	/**
	 * int转4个byte。
	 * 
	 * @param n
	 * @return
	 */
	public static byte[] toBytes(int... n) {
		if (n == null)
			return null;

		byte[] re = new byte[4 * n.length];
		for (int i = 0; i < n.length; i++)
			toBytes(re, n[i], i * 4, 24);

		return re;
	}

	/**
	 * long转8个byte。
	 * 
	 * @param n
	 * @return
	 */
	public static byte[] toBytes(long... n) {
		if (n == null)
			return null;

		byte[] re = new byte[8 * n.length];
		for (int i = 0; i < n.length; i++)
			toBytes(re, n[i], i * 8, 56);

		return re;
	}

	/**
	 * 2个byte转int。
	 * 
	 * @param bytes
	 * @return
	 */
	public static short toShort(byte[] bytes) {
		return (short) toLong(bytes, 2);
	}

	/**
	 * 4个byte转int。
	 * 
	 * @param bytes
	 * @return
	 */
	public static int toInt(byte[] bytes) {
		return (int) toLong(bytes, 4);
	}

	/**
	 * 8个byte转long。
	 * 
	 * @param bytes
	 * @return
	 */
	public static long toLong(byte[] bytes) {
		return toLong(bytes, 8);
	}

	private static byte[] toBytes(byte[] bytes, long l, int start, int shift) {
		for (int m = shift; m > -1; m -= 8)
			bytes[start++] = (byte) (l >> m & 0x0ff);

		return bytes;
	}

	private static long toLong(byte[] bytes, int max) {
		long l = 0;
		for (int i = 0; i < bytes.length && i < max; i++)
			l = l << 8 | (bytes[i] & 0x0ff);

		return l;
	}
}
