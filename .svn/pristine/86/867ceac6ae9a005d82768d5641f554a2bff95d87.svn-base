package com.hframework.lang;



/**
 * 32编码字符串编码与解码。
 * @author heerjian
 * @Date 2018年1月4日
 */
public class Base32Utils {

	private static final char[] BASE_CHAR = "23456789abcdefghijkmnpqrstuvwxyz".toCharArray();

	/**
	 * int数组编码为32位字符串。
	 * 
	 * @param ints
	 *            将要编码数组。
	 * @return 编码后的字符串。
	 */
	public static String encode(int... ints) {
		if (ints == null)
			return null;
		return encode(ByteUtils.toBytes(ints));
	}

	/**
	 * long数组编码为32位字符串。
	 * 
	 * @param longs
	 *            将要编码数组。
	 * @return 编码后的字符串。
	 */
	public static String encode(long... longs) {
		if (longs == null)
			return null;
		return encode(ByteUtils.toBytes(longs));
	}

	/**
	 * 字符串编码为32位字符串。
	 * 
	 * @param str
	 *            将要编码字符串。
	 * @return 编码后的字符串。
	 */
	public static String encode(String str) {
		if (str == null)
			return null;
		return encode(str.getBytes());
	}

	/**
	 * 字节数组编码为32位字符串。
	 * 
	 * @param bytes
	 *            将要编码的字节数组。
	 * @return 编码后的字符串。
	 */
	public static String encode(byte[] bytes) {
		return encode(bytes, BASE_CHAR);
	}

	/**
	 * 字节数组编码为32位字符串。
	 * 
	 * @param bytes
	 *            将要编码的字节数组。
	 * @param baseChar
	 *            基础字符。
	 * @return 编码后的字符串。
	 */
	public static String encode(byte[] bytes, char[] baseChar) {
		if (baseChar == null || baseChar.length != 32)
			throw new IllegalArgumentException("baseChar数组长度必须是32");
		if (bytes == null)
			return null;

		StringBuffer result = new StringBuffer();
		long bs;
		int i = 0;
		while (i < bytes.length - 4) {
			bs = bytes[i++] & 0x0ff;
			for (int j = 0; j < 4; j++)
				bs = bs << 8 | (bytes[i++] & 0x0ff);

			for (int m = 35; m > -1; m -= 5)
				result.append(baseChar[(int) (bs >> m & 0x1f)]);
		}
		if (i < bytes.length) {
			int l = bytes.length - i;

			bs = bytes[i++] & 0x0ff;
			while (i < bytes.length)
				bs = bs << 8 | (bytes[i++] & 0x0ff);

			int m = l == 1 ? 5 : (l == 2 ? 15 : (l == 3 ? 20 : 30));
			for (; m > -1; m -= 5)
				result.append(baseChar[(int) (bs >> m & 0x1f)]);
		}

		return result.toString();
	}

	/**
	 * 解码32位编码的字符串。
	 * 
	 * @param base32
	 *            32位编码的字符串。
	 * @return 解码后的字符串。
	 */
	public static String decode2Str(String base32) {
		byte[] bs = decode(base32);
		if (bs == null)
			return null;
		return new String(bs);
	}

	/**
	 * 解码32位编码的字符串。
	 * 
	 * @param base32
	 *            32位编码的字符串。
	 * @return 解码后的字节数组。
	 */
	public static byte[] decode(String base32) {
		return decode(base32, BASE_CHAR);
	}

	/**
	 * 解码32位编码的字符串。
	 * 
	 * @param base32
	 *            32位编码的字符串。
	 * @param baseChar
	 *            基础字符。
	 * @return 解码后的字节数组。
	 */
	public static byte[] decode(String base32, char[] baseChar) {
		if (baseChar == null || baseChar.length != 32)
			throw new IllegalArgumentException("baseChar数组长度必须是32");
		if (base32 == null)
			return null;

		byte[] result;
		int t = base32.length() % 8;
		int l = base32.length() / 8 * 5;
		if (t == 0)
			result = new byte[l];
		else if (t == 2)
			result = new byte[l + 1];
		else if (t == 4)
			result = new byte[l + 2];
		else if (t == 5)
			result = new byte[l + 3];
		else if (t == 7)
			result = new byte[l + 4];
		else
			throw new IllegalArgumentException("base32长度无效");
		l = 0;

		String baseStr = new String(baseChar);
		long bs;
		int b;
		int i = 0;
		char[] chars = base32.toCharArray();
		while (i < chars.length - 7) {
			bs = 0;
			for (int j = 0; j < 8; j++) {
				b = baseStr.indexOf(chars[i++]);
				if (b == -1)
					throw new IllegalArgumentException("base32包含无效的字符");
				bs = bs << 5 | b;
			}

			for (int m = 32; m > -1; m -= 8)
				result[l++] = (byte) (bs >> m & 0x0ff);
		}

		if (t != 0) {
			bs = 0;
			for (int j = 0; j < t; j++) {
				b = baseStr.indexOf(chars[i++]);
				if (b == -1)
					throw new IllegalArgumentException("base32包含无效的字符");
				bs = bs << 5 | b;
			}
			int m = t == 2 ? 0 : (t == 4 ? 8 : (t == 5 ? 16 : 24));
			for (; m > -1; m -= 8)
				result[l++] = (byte) (bs >> m & 0x0ff);
		}
		return result;
	}
}
