package com.way.common.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 */
public class AESUtil {

	private static final String defaultKey = "f6cbc04e-1c4e-4187-9e23-4d01c63ab7da";

	/**
	 * @Description: 加密
	 * @Param:
	 * @returns: java.lang.String
	 * @Author: zhuenbang
	 * @Date: 2018/12/3 11:33
	 */
	public static String encryptByDefaultKey(String content) throws Exception {
		return encrypt(content, defaultKey);
	}

	/**
	 * @Description: 解密
	 * @Param:
	 * @returns: java.lang.String
	 * @Author: zhuenbang
	 * @Date: 2018/12/3 11:30
	 */
	public static String decryptByDefaultKey(String encryptStr) throws Exception {
		return decrypt(encryptStr, defaultKey);
	}

	/**
	 * AES加密为base 64 code
	 *
	 * @param content    待加密的内容
	 * @param encryptKey 加密密钥
	 * @return 加密后的base 64 code
	 * @throws Exception
	 */

	public static String encrypt(String content, String encryptKey) throws Exception {
		return base64Encode(aesEncryptToBytes(content, encryptKey));

	}

	/**
	 * AES加密
	 *
	 * @param content    待加密的内容
	 * @param encryptKey 加密密钥
	 * @return 加密后的byte[]
	 * @throws Exception
	 */

	private static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {

		KeyGenerator kgen = KeyGenerator.getInstance("AES");

		SecureRandom random;
		if (System.getProperty("os.name").toLowerCase().contains("linux")) {
			random = SecureRandom.getInstance("SHA1PRNG");
		} else {
			random = new SecureRandom();
		}

		random.setSeed(encryptKey.getBytes());
		kgen.init(128, random);

		Cipher cipher = Cipher.getInstance("AES");

		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));

		return cipher.doFinal(content.getBytes("utf-8"));

	}

	/**
	 * base 64 加密
	 *
	 * @param bytes 待编码的byte[]
	 * @return 编码后的base 64 code
	 */

	private static String base64Encode(byte[] bytes) {
		return Base64.encodeBase64String(bytes);
	}

	/**
	 * 将base 64 code AES解密
	 *
	 * @param encryptStr 待解密的base 64 code
	 * @param decryptKey 解密密钥
	 * @return 解密后的string
	 * @throws Exception
	 */

	public static String decrypt(String encryptStr, String decryptKey) throws Exception {

		return StringUtils.isNotEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);

	}

	/**
	 * AES解密
	 *
	 * @param encryptBytes 待解密的byte[]
	 * @param decryptKey   解密密钥
	 * @return 解密后的String
	 * @throws Exception
	 */
	private static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {

		KeyGenerator kgen = KeyGenerator.getInstance("AES");

		SecureRandom random;
		if (System.getProperty("os.name").toLowerCase().contains("linux")) {
			random = SecureRandom.getInstance("SHA1PRNG");
		} else {
			random = new SecureRandom();
		}
		random.setSeed(decryptKey.getBytes());

		kgen.init(128, random);

		Cipher cipher = Cipher.getInstance("AES");

		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));

		byte[] decryptBytes = cipher.doFinal(encryptBytes);

		return new String(decryptBytes);

	}

	/**
	 * base 64 解密
	 *
	 * @param base64Code 待解码的base 64 code
	 * @return 解码后的byte[]
	 * @throws Exception
	 */

	private static byte[] base64Decode(String base64Code) throws Exception {
      
		return StringUtils.isNotEmpty(base64Code) ? null : Base64.decodeBase64(base64Code);
	}

}