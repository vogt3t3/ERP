package com.aiiju.util.common;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @ClassName: EncryptUtil
 * @Description: 加密算法公用类
 * @author 哪吒
 * @date 2017年2月10日 下午2:06:20
 * 
 */

public class EncryptUtil {

	/**
	 * 密钥算法 java6支持56位密钥，bouncycastle支持64位
	 */
	public static final String KEY_ALGORITHM = "AES";

	/**
	 * 加密/解密算法/工作模式/填充方式
	 * 
	 * JAVA6 支持PKCS5PADDING填充方式 Bouncy castle支持PKCS7Padding填充方式
	 */
	public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
	
	// 固定的key值
	public static final String FIXED_KEY = "AIIJUHRMAIIJUHRM";

	/**
	 * 
	 * 生成密钥，java6只支持56位密钥，bouncycastle支持64位密钥
	 * 
	 * @return byte[] 二进制密钥
	 */
	public static byte[] initkey() throws Exception {

		// 实例化密钥生成器
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		// 初始化密钥生成器，AES要求密钥长度为128位、192位、256位
		kg.init(128);
		// 生成密钥
		SecretKey secretKey = kg.generateKey();
		// 获取二进制密钥编码形式
		return secretKey.getEncoded();
	}

	/**
	 * 转换密钥
	 * 
	 * @param key
	 *            二进制密钥
	 * @return Key 密钥
	 */
	public static Key toKey(byte[] key) throws Exception {
		// 实例化DES密钥
		// 生成密钥
		SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
		return secretKey;
	}

	/**
	 * 加密数据
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return byte[] 加密后的数据
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// 还原密钥
		Key k = toKey(key);
		/**
		 * 实例化 使用 PKCS7PADDING 填充方式，按如下方式实现,就是调用bouncycastle组件实现
		 * Cipher.getInstance(CIPHER_ALGORITHM,"BC")
		 */
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// 初始化，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, k);
		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * 解密数据
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return byte[] 解密后的数据
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		// 欢迎密钥
		Key k = toKey(key);
		/**
		 * 实例化 使用 PKCS7PADDING 填充方式，按如下方式实现,就是调用bouncycastle组件实现
		 * Cipher.getInstance(CIPHER_ALGORITHM,"BC")
		 */
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// 初始化，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, k);
		// 执行操作
		return cipher.doFinal(data);
	}
	
	/**
	 * 加密，并转换为16进制
	 * @param str
	 * @param keyStr
	 * @return
	 */
	public static String encryptToHexStr(String str) {
		String result = null;
		try {
			byte[] key = FIXED_KEY.getBytes();
			// 加密
			byte[] data = encrypt(str.getBytes(), key);
			result = ParseSystemUtil.parseByte2HexStr(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 解密
	 * @param str
	 * @param keyStr
	 * @return
	 */
	public static String decryptStr(String str) {
		byte[] data = ParseSystemUtil.parseHexStr2Byte(str);
		byte[] result = null;
		try {
			byte[] key = FIXED_KEY.getBytes();
			result = EncryptUtil.decrypt(data, key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(result);
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String str = "12";
		String a = EncryptUtil.encryptToHexStr(str);
		System.out.println("加密后：" + a);
		System.out.println("解密后：" + EncryptUtil.decryptStr(a));
	}
}
