package com.chuanglan.myTest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TestSha1 {
	
	public static void main(String[] args) {
		String str = "test&ext_id=144610588611&mobile=18500818888&noncestr=abcdef&package=00020&timestamp=1446105886&key=XXX";
		String res = SHA1(str);
		System.out.println(res);
	}
	
	public static String SHA1(String decript) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(decript.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}
