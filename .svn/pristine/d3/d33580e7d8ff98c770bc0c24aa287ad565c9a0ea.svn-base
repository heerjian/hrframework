package com.hframework.lang;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;

import org.apache.commons.lang3.StringUtils;

/**
 * 基于硬件序列的加解密。主要用于加密数据库密码等。
 * 
 * @author heerjian
 * @date 2017年3月6日
 */
public class XhsCrypto implements Serializable {
	private static final long serialVersionUID = 1L;

	private static byte[] implBytes = new byte[] { -84, -19, 0, 5, 115, 114, 0, 29, 99, 111, 109, 46, 120, 102, 114, 97,
			109, 101, 119, 111, 114, 107, 46, 108, 97, 110, 103, 46, 88, 104, 115, 67, 114, 121, 112, 116, 111, 0, 0, 0,
			0, 0, 0, 0, 1, 2, 0, 1, 76, 0, 4, 115, 101, 101, 100, 116, 0, 18, 76, 106, 97, 118, 97, 47, 108, 97, 110,
			103, 47, 83, 116, 114, 105, 110, 103, 59, 120, 112, 116, 0, 36, 57, 56, 55, 54, 53, 52, 51, 50, 49, 48, 64,
			120, 102, 117, 116, 117, 114, 101, 46, 99, 111, 109, 46, 99, 110, 40, 126, 33, 64, 35, 36, 37, 94, 38, 42,
			41 };
	private static XhsCrypto inst;

	private String seed;

	public XhsCrypto(String seed) {
		this.seed = seed;
	}

	public static XhsCrypto getInstance() {
		if (inst != null)
			return inst;

		synchronized (implBytes) {
			if (inst == null) {
				try {
					ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(implBytes));
					inst = (XhsCrypto) ois.readObject();
				} catch (Exception e) {
					throw new RuntimeException("解析实例失败", e);
				}
			}
			return inst;
		}
	}

	/**
	 * 解密。
	 * 
	 * @param ciphertext
	 * @return
	 */
	public String decrypt(String ciphertext) {
		String time = ciphertext.substring(0, 11);// 获取反转的时间戳
		String key = getKey() + StringUtils.reverse(time) + seed;// 硬件信息加时间戳做为key种子
		key = CryptoUtils.md5EnHex(key);// key种子md5得到key
		ciphertext = ciphertext.substring(11);// 密文
		byte[] texts = HexUtils.hex2Byte(ciphertext);
		ciphertext = new String(CryptoUtils.desDe(texts, key));// 解密得到明文
		return ciphertext.substring(0, ciphertext.length() / 2);// 明文的一半为返回值
	}

	/**
	 * 加密。
	 * 
	 * @param plaintext
	 * @return
	 */
	public String encrypt(String plaintext) {
		long time = System.currentTimeMillis();
		// 系统时间转成11位16进制
		String str = StringUtils.right(StringUtils.leftPad(Long.toHexString(time), 11, '0'), 11);
		String key = getKey() + str + seed;// 硬件信息加时间戳做为key种子
		key = CryptoUtils.md5EnHex(key); // key种子md5得到key
		plaintext += plaintext;// 明文重复一次做为带加密的字符
		String ciphertext = CryptoUtils.desEnHex(plaintext, key);// des加密
		return StringUtils.reverse(str) + ciphertext; // 时间戳反转+密文得到返回值
	}

	private String getKey() {
		try {
			return HexUtils
					.byte2hex(NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress());
		} catch (Exception e) {
		}
		return "000000000000";
	}

	public static void main(String[] args) throws IOException {
		if (args != null && args.length > 0) {
			for (String arg : args) {
				System.out.println(arg + "-->" + XhsCrypto.getInstance().encrypt(arg));
			}
		}
	}
}
