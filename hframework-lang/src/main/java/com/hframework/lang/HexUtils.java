package com.hframework.lang;


import org.apache.commons.lang3.StringUtils;

/**
 * 数字与16进制字符串相互转换的常用函数。
 * 
 * @author heerjian
 * @date 2015年1月19日
 */
public class HexUtils {

	/**
	 * byte数组转为16进制字符串(小写)。<br/>
	 * 一个byte占两个长度，高位在前，低位在后(如:1 = 01, 10 = 0a, 16 = 10)。
	 * 
	 * @param bytes
	 * @return
	 */
	public static String byte2hex(byte... bytes) {
		return byte2hex(null, bytes);
	}

	/**
	 * byte数组转为16进制字符串(小写)，每个byte之间用分隔符连接。<br/>
	 * 一个byte占两个长度，高位在前，低位在后(如:1 = 01, 10 = 0a, 16 = 10)。
	 * 
	 * @param separator
	 *            连接byte的分隔符。
	 * @param bytes
	 * @return
	 */
	public static String byte2hex(String separator, byte... bytes) {
		if (bytes == null)
			return null;

		StringBuffer sb = new StringBuffer(
				separator == null ? bytes.length * 2 : bytes.length * (separator.length() + 2) - separator.length());
		int i = 0;
		for (byte b : bytes) {
			if (separator != null && i++ > 0) {
				sb.append(separator);
			}
			sb.append(StringUtils.leftPad(Integer.toHexString(b & 0x0ff), 2, '0'));
		}
		return sb.toString();
	}

	/**
	 * 16进制字符串转为byte数组，每两个字符转一个byte，若hex长度为单数则在前面补0再转换。
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] hex2Byte(String hex) {
		return hex2Byte(hex, null);
	}

	/**
	 * 16进制字符串转为byte数组，每个byte之间用separator分隔。如果separator为null，则每两个字符转一个byte，若hex长度为单数则在前面补0再转换。
	 * 
	 * @param hex
	 * @param separator
	 * @return
	 */
	public static byte[] hex2Byte(String hex, String separator) {
		if (StringUtils.isBlank(hex))
			return null;

		if (StringUtils.isEmpty(separator)) {
			if ((hex.length() % 2 != 0))
				hex = "0" + hex;
			byte[] bs = new byte[hex.length() / 2];
			for (int i = 0; i < bs.length; i++) {
				bs[i] = (byte) Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
			}
			return bs;
		} else {
			String[] hexs = StringUtils.split(hex, separator);
			byte[] bs = new byte[hexs.length];
			int i = 0;
			for (String h : hexs) {
				bs[i++] = Byte.parseByte(h, 16);
			}
			return bs;
		}
	}

	/**
	 * java 示例，其它语言可以参考修改 将 16 进制字符串转换成 16 进制数字数组
	 *
	 * @param hexString
	 *            DES 的明文
	 * @return
	 */
	public static byte[] asc2bin(String hexString) {
		byte[] hexbyte = hexString.getBytes();
		byte[] bitmap = new byte[hexbyte.length / 2];
		for (int i = 0; i < bitmap.length; i++) {
			hexbyte[i * 2] -= hexbyte[i * 2] > '9' ? 7 : 0;
			hexbyte[i * 2 + 1] -= hexbyte[i * 2 + 1] > '9' ? 7 : 0;
			bitmap[i] = (byte) ((hexbyte[i * 2] << 4 & 0xf0) | (hexbyte[i * 2 + 1] & 0x0f));
		}
		return bitmap;
	}

}
