package com.ucsmy.commons.utils;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.crypto.Cipher;
import java.security.*;

/**
 * Utils - RSA加密解密
 * 
 * @author Hengtiansoft Team
 * @version 1.0_beta
 */
public final class RSAUtils {

	/** 安全服务提供者. */
	private static final Provider PROVIDER = new BouncyCastleProvider();

	public static final String PRIVATE_KEY_ATTRIBUTE_NAME = "privateKey";
	private static Logger log = LoggerFactory.getLogger(RSAUtils.class);
	
	/** 密钥大小. */
	private static final int KEY_SIZE = 1024;

	/**
	 * 不可实例化.
	 */
	private RSAUtils() {
	}

	/**
	 * 生成密钥对.
	 * 
	 * @return 密钥对
	 */
	public static KeyPair generateKeyPair() {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(
					"RSA", PROVIDER);
			keyPairGenerator.initialize(KEY_SIZE, new SecureRandom());
			return keyPairGenerator.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			log.error("RSAUtils",e);
			return null;
		}
	}

	/**
	 * 加密.
	 * 
	 * @param publicKey
	 *            公钥
	 * @param data
	 *            数据
	 * @return 加密后的数据
	 */
	public static byte[] encrypt(PublicKey publicKey, byte[] data) {
		Assert.notNull(publicKey);
		Assert.notNull(data);
		try {
			Cipher cipher = Cipher.getInstance("RSA", PROVIDER);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			log.error("RSAUtils",e);
			return new byte[]{};
		}
	}

	/**
	 * 加密.
	 * 
	 * @param publicKey
	 *            公钥
	 * @param text
	 *            字符串
	 * 
	 * @return Base64编码字符串
	 */
	public static String encrypt(PublicKey publicKey, String text) {
		Assert.notNull(publicKey);
		Assert.notNull(text);
		byte[] data = encrypt(publicKey, text.getBytes());
		return data != null ? Base64.encodeBase64String(data) : null;
	}

	/**
	 * 解密.
	 * 
	 * @param privateKey
	 *            私钥
	 * @param data
	 *            数据
	 * @return 解密后的数据
	 */
	public static byte[] decrypt(PrivateKey privateKey, byte[] data) {
		Assert.notNull(privateKey);
		Assert.notNull(data);
		try {
			Cipher cipher = Cipher
					.getInstance("RSA/ECB/PKCS1Padding", PROVIDER);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			log.error("RSAUtils",e);
			return new byte[]{};
		}
	}

	/**
	 * 解密.
	 * 
	 * @param privateKey
	 *            私钥
	 * @param text
	 *            Base64编码字符串
	 * @return 解密后的数据
	 */
	public static String decrypt(PrivateKey privateKey, String text) {
		Assert.notNull(privateKey);
		Assert.notNull(text);
		byte[] data = decrypt(privateKey, Base64.decodeBase64(text));
		return data != null ? new String(data) : null;
	}

}
