package com.hframework.lang;


import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 常用加密函数。
 * 
 * @author heerjian
 * @date 2015年1月19日
 */
public class CryptoUtils {
	public static final String SHA1RSA_ALGORITHM = "SHA1WithRSA";
	public static final String RSA_ALGORITHM = "RSA";
	public static final String DES_ALGORITHM = "DES";
	public static final String SHA256_ALGORITHM = "SHA-256";
	public static final String SHA384_ALGORITHM = "SHA-384";
	public static final String SHA512_ALGORITHM = "SHA-512";
	public static final String Md5_ALGORITHM = "md5";

	/**
	 * 根据给定摘要算法创建一个消息摘要实例
	 * 
	 * @param algorithm
	 *            摘要算法名
	 * @return 消息摘要实例
	 * @see MessageDigest#getInstance(String)
	 * @throws RuntimeException
	 *             当 {@link java.security.NoSuchAlgorithmException} 发生时
	 */
	private static MessageDigest getDigest(String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 根据给定数组和算算法进行加密
	 * 
	 * @param plaintext
	 * @param algorithm
	 * @return
	 */
	private static byte[] enDigest(String plaintext, String algorithm) {
		try {
			MessageDigest md = getDigest(algorithm);
			md.update(plaintext.getBytes());
			return md.digest();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * MD5加密。返回16进制字符串(小写)。
	 * 
	 * @param plaintext
	 *            明文。
	 * @return
	 */
	public static String md5EnHex(String plaintext) {
		return HexUtils.byte2hex(md5En(plaintext));
	}

	/**
	 * MD5加密。
	 * 
	 * @param plaintext
	 *            明文。
	 * @return
	 */
	public static byte[] md5En(String plaintext) {
		return enDigest(plaintext, Md5_ALGORITHM);
	}

	/**
	 * SHA-256加密。。返回16进制字符串(小写)。
	 * 
	 * @param plaintext
	 *            明文。
	 * @return
	 */
	public static String Sha256EnHex(String plaintext) {
		return HexUtils.byte2hex(Sha256En(plaintext));
	}

	/**
	 * SHA-256加密。。
	 * 
	 * @param plaintext
	 *            明文。
	 * @return
	 */
	public static byte[] Sha256En(String plaintext) {
		return enDigest(plaintext, SHA256_ALGORITHM);
	}

	/**
	 * SHA-512加密。。
	 * 
	 * @param plaintext
	 *            明文。
	 * @return
	 */
	public static byte[] Sha512En(String plaintext) {
		return enDigest(plaintext, SHA512_ALGORITHM);
	}

	/**
	 * SHA-512加密。。返回16进制字符串(小写)。
	 * 
	 * @param plaintext
	 *            明文。
	 * @return
	 */
	public static String Sha512EnHex(String plaintext) {
		return HexUtils.byte2hex(Sha512En(plaintext));
	}

	/**
	 * SHA-384加密。。返回16进制字符串(小写)。
	 * 
	 * @param plaintext
	 *            明文。
	 * @return
	 */
	public static String Sha384EnHex(String plaintext) {
		return HexUtils.byte2hex(Sha384En(plaintext));
	}

	/**
	 * SHA-384加密。。
	 * 
	 * @param plaintext
	 *            明文。
	 * @return
	 */
	public static byte[] Sha384En(String plaintext) {
		return enDigest(plaintext, SHA384_ALGORITHM);
	}

	/**
	 * RSA签名。
	 *
	 * @param key
	 *            签名Key，一般是私钥。Base64编码。
	 * @param content
	 *            待签名的内容。
	 * @return
	 */
	public static String rsaSign(String key, String content) {
		return rsaSign(key, content.getBytes());
	}

	/**
	 * RSA签名。
	 *
	 * @param key
	 *            签名Key，一般是私钥。Base64编码。
	 * @param content
	 *            待签名的内容。
	 * @return
	 */
	public static String rsaSign(String key, byte[] content) {
		return rsaSign(Base64Utils.decode(key), content);
	}

	/**
	 * RSA签名。
	 *
	 * @param key
	 *            签名Key，一般是私钥。
	 * @param content
	 *            待签名的内容。
	 * @return
	 */
	public static String rsaSign(byte[] key, byte[] content) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(key);
			PrivateKey priKey = KeyFactory.getInstance(RSA_ALGORITHM).generatePrivate(priPKCS8);
			Signature signature = Signature.getInstance(SHA1RSA_ALGORITHM);

			signature.initSign(priKey);
			signature.update(content);

			byte[] signed = signature.sign();

			return Base64Utils.encode(signed);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * RSA验签。
	 *
	 * @param key
	 *            验签Key，一般是公钥。Base64编码。
	 * @param content
	 *            验签内容。
	 * @param signed
	 *            验签串。由私钥签名的结果。Base64编码。
	 * @return
	 */
	public static boolean rsaVerify(String key, String content, String signed) {
		return rsaVerify(Base64Utils.decode(key), content.getBytes(), Base64Utils.decode(signed));
	}

	/**
	 * RSA验签。
	 *
	 * @param key
	 *            验签Key，一般是公钥。Base64编码。
	 * @param content
	 *            验签内容。
	 * @param signed
	 *            验签串。由私钥签名的结果。Base64编码。
	 * @return
	 */
	public static boolean rsaVerify(String key, byte[] content, String signed) {
		return rsaVerify(Base64Utils.decode(key), content, Base64Utils.decode(signed));
	}

	/**
	 * RSA验签。
	 *
	 * @param key
	 *            验签Key，一般是公钥。
	 * @param content
	 *            验签内容。
	 * @param signed
	 *            验签串。由私钥签名的结果。
	 * @return
	 */
	public static boolean rsaVerify(byte[] key, byte[] content, byte[] signed) {
		try {
			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(key);
			PublicKey pubKey = KeyFactory.getInstance(RSA_ALGORITHM).generatePublic(pubKeySpec);

			Signature signature = Signature.getInstance(SHA1RSA_ALGORITHM);

			signature.initVerify(pubKey);
			signature.update(content);

			return signature.verify(signed);
		} catch (Throwable e) {
			return false;
		}
	}

	/**
	 * xhs加密。
	 * 
	 * @param plaintext
	 *            明文。
	 * @return
	 */
	public static String xhsEncrypt(String plaintext) {
		return XhsCrypto.getInstance().encrypt(plaintext);
	}

	/**
	 * xhs解密。
	 * 
	 * @param ciphertext
	 *            密文。
	 * @return
	 */
	public static String xhsDecrypt(String ciphertext) {
		return XhsCrypto.getInstance().decrypt(ciphertext);
	}

	/**
	 * DES加密。返回16进制字符串(小写)。
	 * 
	 * @param plaintext
	 *            明文。
	 * @param key
	 *            密钥。
	 * @return
	 */
	public static String desEnHex(String plaintext, String key) {
		return HexUtils.byte2hex(desEn(plaintext, key));
	}

	/**
	 * DES加密。
	 * 
	 * @param plaintext
	 *            明文。
	 * @param key
	 *            密钥。
	 * @return
	 */
	public static byte[] desEn(String plaintext, String key) {
		return desEn(plaintext.getBytes(), key.getBytes());
	}

	/**
	 * DES加密。
	 * 
	 * @param plaintext
	 *            明文。
	 * @param key
	 *            密钥。
	 * @return
	 */
	public static byte[] desEn(byte[] plaintext, byte[] key) {
		try {
			Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, getDesKey(key));

			return cipher.doFinal(plaintext);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * DES解密。
	 * 
	 * @param hexCiphertext
	 *            hex密文。
	 * @param key
	 *            密钥。
	 * @return 返回解密后的byte数组组成的字符串。
	 */
	public static String desDeHex(String hexCiphertext, String key) {
		return desDe(HexUtils.hex2Byte(hexCiphertext), key);
	}

	/**
	 * DES解密。
	 * 
	 * @param ciphertext
	 *            密文。
	 * @param key
	 *            密钥。
	 * @return 返回解密后的byte数组组成的字符串。
	 */
	public static String desDe(byte[] ciphertext, String key) {
		return new String(desDe(ciphertext, key.getBytes()));
	}

	/**
	 * DES解密。
	 * 
	 * @param ciphertext
	 *            密文。
	 * @param key
	 *            密钥。
	 * @return
	 */
	public static byte[] desDe(byte[] ciphertext, byte[] key) {
		try {
			Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, getDesKey(key));

			return cipher.doFinal(ciphertext);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 返回Des Key。
	 * 
	 * @param key
	 * @return
	 */
	public static SecretKey getDesKey(byte[] key) {
		try {
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
			return keyFactory.generateSecret(new DESKeySpec(key));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static void main(String []args) {
		System.out.println(Sha256En("123").toString());;
	}

}
