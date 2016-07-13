package com.chuanglan.getToken;

import java.security.MessageDigest;

public class MD5Test {

	public String str = "1022016060117072648310214174973149";
	
	
	public static byte[] getMD5(String str) throws Exception {
		byte[] srtbyte = null;
		srtbyte = str.getBytes("UTF-8");

		// 获得MD5摘要算法的 MessageDigest 对象
		MessageDigest mdInst = MessageDigest.getInstance("MD5");
		// 使用指定的字节更新摘要
		mdInst.update(srtbyte);
		// 获得密文
		byte[] md = mdInst.digest();

		return md;
	}
	
	public static String getHexString(byte[] data) {
		// 把密文转换成十六进制的字符串形式
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		int j = data.length;
		char chars[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = data[i];
			chars[k++] = hexDigits[byte0 >>> 4 & 0xf];
			chars[k++] = hexDigits[byte0 & 0xf];
		}
		return new String(chars);
	}
}
