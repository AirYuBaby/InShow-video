package com.InShowVideo.utils;
 
import java.security.MessageDigest;

import org.apache.tomcat.util.codec.binary.Base64;

public class MD5Utils {
	
	public static String getMD5Str(String strValue) throws Exception{
		MessageDigest md5 =MessageDigest.getInstance("MD5");
		String newstr = Base64.encodeBase64String(md5.digest(strValue.getBytes()));
		return newstr;
	}
	public static void main(String[] args) {
		try {
			String md5 = getMD5Str("Eriyuer");
			System.out.println(md5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
