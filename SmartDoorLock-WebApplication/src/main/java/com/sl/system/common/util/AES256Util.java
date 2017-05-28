package com.sl.system.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AES256Util {
	// private String iv;
	static private byte[] iv;
	static private Key keySpec;
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public AES256Util(String key) throws UnsupportedEncodingException {
		// this.iv = key.substring(0, 16);
		this.iv = new byte[16];
		Arrays.fill(iv, (byte) 0x00);

		byte[] keyBytes = new byte[16];
		byte[] b = key.getBytes("UTF-8");
		int len = b.length;
		if (len > keyBytes.length)
			len = keyBytes.length;
		System.arraycopy(b, 0, keyBytes, 0, len);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

		this.keySpec = keySpec;
	}

	// 암호화
	public String aesEncode(String str)
			throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		// c.init(Cipher.ENCRYPT_MODE, keySpec, new
		// IvParameterSpec(iv.getBytes()));
		c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));

		byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
		String enStr = new String(Base64.encodeBase64(encrypted));
		return enStr;
	}

	// 복호화
	public String aesDecode(String str)
			throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		// c.init(Cipher.DECRYPT_MODE, keySpec, new
		// IvParameterSpec(iv.getBytes("UTF-8")));
		c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));

		byte[] byteStr = Base64.decodeBase64(str.getBytes());

		return new String(c.doFinal(byteStr), "UTF-8");
	}

	// ================================양균 메서드

}