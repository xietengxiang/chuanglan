package com.chuanglan.myTest;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;
import javax.crypto.Mac;

public class TestHmac {

	private static String macKey = "14E9CD5689FC99E72077A4CBFA4D5294FE13B292394CE7DD";
	private static String macData = "mobileNo=15173040310&ownership=MOBILE&agentCode=AGENT10000001815&agentOrderNo=20160523184233008540&orderAmount=10&provinceCode=100000&isNotify=1¬ifyUrl=http%3A%2F%2Fliulian.253.com%2Fapi%2Fcallback%3Ffrom%3Dyibao&requestTimestamp=1464000158000";
	
	
	public static void main(String args[]) throws Exception {

		System.out.println("MACDATA:" + macData);

		Mac mac = Mac.getInstance("HmacSHA256");
		// get the bytes of the hmac key and data string
		byte[] secretByte = macKey.getBytes("UTF-8");
		byte[] dataBytes = macData.getBytes("UTF-8");
		SecretKey secret = new SecretKeySpec(secretByte, "HMACSHA256");

		mac.init(secret);
		byte[] doFinal = mac.doFinal(dataBytes);
		byte[] hexB = new Hex().encode(doFinal);
		String checksum = new String(hexB);
		System.out.println(checksum);
		
		System.out.println("=============");
		byte[] res = encryptHMAC(macData.getBytes(), macKey);
		byte[] hex = new Hex().encode(res);
		String check = new String(hex);
		System.out.println(check);
	}

	public static byte[] encryptHMAC(byte[] data, String key) throws Exception {
		SecretKey sk = new SecretKeySpec(key.getBytes(), "HmacMD5");
		Mac mac = Mac.getInstance(sk.getAlgorithm());
		mac.init(sk);
		return mac.doFinal(data);
	}

}