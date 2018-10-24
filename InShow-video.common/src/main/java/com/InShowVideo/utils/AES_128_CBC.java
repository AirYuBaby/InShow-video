package com.InShowVideo.utils;
 
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES_128_CBC {


	public static byte[] AES_CBC_Encrypt(byte[] content, byte[] keyBytes, byte[] iv) {

		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128, new SecureRandom(keyBytes));
			SecretKey key = keyGenerator.generateKey();
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("exception:" + e.toString());
		}
		return null;
	}

	public static byte[] AES_CBC_Decrypt(byte[] content, byte[] keyBytes, byte[] iv) {

		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128, new SecureRandom(keyBytes));// key长可设为128，192，256位，这里只能设为128
			SecretKey key = keyGenerator.generateKey();
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("exception:" + e.toString());
		}
		return null;
	}

	public static String byteToHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer(bytes.length);
		String sTemp;
		for (int i = 0; i < bytes.length; i++) {
			sTemp = Integer.toHexString(0xFF & bytes[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

}
