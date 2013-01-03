package com.huhuo.integration.algorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DESUtils {
	private static final String DESede = "DESede";
	/**
	 * 生成3DES密钥.
	 * 
	 * @param key_byte
	 *            seed key
	 * @throws Exception
	 * @return javax.crypto.SecretKey Generated DES key
	 */
	public static javax.crypto.SecretKey genKey(byte[] key_byte)
			throws Exception {
		SecretKey k = null;
		k = new SecretKeySpec(key_byte, DESede);
		return k;
	}

	/**
	 * 3DES 解密(byte[]).
	 * 
	 * @param key
	 *            SecretKey
	 * @param crypt
	 *            byte[]
	 * @throws Exception
	 * @return byte[]
	 */
	public static byte[] decrypt(javax.crypto.SecretKey key, byte[] crypt)
			throws Exception {
		javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(DESede);
		cipher.init(javax.crypto.Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(crypt);
	}

	/**
	 * 3DES 解密(String).
	 * 
	 * @param key
	 *            SecretKey
	 * @param crypt
	 *            byte[]
	 * @throws Exception
	 * @return byte[]
	 */
	public static String decrypt(javax.crypto.SecretKey key, String crypt)
			throws Exception {
		return new String(decrypt(key, crypt.getBytes()));
	}
	
	/**
	 * 3DES加密(byte[]).
	 * 
	 * @param key
	 *            SecretKey
	 * @param src
	 *            byte[]
	 * @throws Exception
	 * @return byte[]
	 */
	public static byte[] encrypt(javax.crypto.SecretKey key, byte[] src)
			throws Exception {
		javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(DESede);
		cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(src);
	}

	/**
	 * 3DES加密(String).
	 * 
	 * @param key
	 *            SecretKey
	 * @param src
	 *            byte[]
	 * @throws Exception
	 * @return byte[]
	 */
	public static String encrypt(javax.crypto.SecretKey key, String src)
			throws Exception {
		return new String(encrypt(key, src.getBytes()));
	}

}
