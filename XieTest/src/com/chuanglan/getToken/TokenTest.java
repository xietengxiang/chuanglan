package com.chuanglan.getToken;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;

/**
 * 龙卷风接口授权的签名链接
 * 
 * @author xie_tx
 *
 */
public class TokenTest {

	private static final String _MD5 = "MD5";
	private static final String HMAC_SHA1 = "HmacSHA1";
	private static final String ENCODING = "UTF-8";

	public static void main(String[] args) {
		
		try {
			String str = "<BusiData><CreateTime>1454308038574</CreateTime><ChargePhoneNum>13805331100</ChargePhoneNum><UserID>5338027676052</UserID><ProductCode>108711</ProductCode><ChargeNum>1</ChargeNum></BusiData>aa94e32272d24178abbdac3755e5aac1";
			byte[] signKey = getMD5(str);
			System.out.println("===="+getHexString(signKey));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("account", "10000");
		params.put("password", "KN51f2h482mbrL1RAHYR");

		String url = getURL(params);
		System.out.println("URL:" + url);
		
		UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
        BASE64Encoder b64 = new BASE64Encoder();
        String token = b64.encode( uuid.toString().getBytes() );
        System.out.println("token:" + token);
	}

	public static String getURL(Map<String, String> params) {

		String url = "";

		String account = params.get("account");
		String password = params.get("password");
		//// 自定义字符串，随机即可，至少16个字符
		String v = "WkDKzLtn2JeiSqEoJtE3";
		// 1970年1月1日以来的毫秒数
		long t = 1438005857381L;

		try {
			// 获得signKey（v+password的MD5加密）
			byte[] signKey = getMD5(v + password);
			System.out.println(getHexString(signKey));

			String encryptText = "/remote/open/2/auth/getToken/" + account + t + password + v;
			// 获得签名（用signKey作为密钥，对encryptText进行HmacSHA1加密）
			signKey = HmacSHA1Encrypt(encryptText, signKey);
			String str = getHexString(signKey);
			System.out.println(str);

			url = "/remote/open/2/auth/getToken/" + account + "?t=" + t + "&v=" + v + "&s=" + str;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return url;
	}

	/**
	 * 把字节码数组转换成16进制字符串
	 * 
	 * @param data
	 * @return
	 */
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

	/**
	 * MD5加密
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] getMD5(String str) throws Exception {
		byte[] srtbyte = null;
		srtbyte = str.getBytes(ENCODING);

		// 获得MD5摘要算法的 MessageDigest 对象
		MessageDigest mdInst = MessageDigest.getInstance(_MD5);
		// 使用指定的字节更新摘要
		mdInst.update(srtbyte);
		// 获得密文
		byte[] md = mdInst.digest();

		return md;
	}

	/**
	 * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
	 * 
	 * @param encryptText 被签名的字符串
	 * @param signKey 密钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] HmacSHA1Encrypt(String encryptText, byte[] signKey) throws Exception {
		// 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
		SecretKey secretKey = new SecretKeySpec(signKey, HMAC_SHA1);
		// 生成一个指定 Mac 算法 的 Mac 对象
		Mac mac = Mac.getInstance(HMAC_SHA1);
		// 用给定密钥初始化 Mac 对象
		mac.init(secretKey);

		byte[] text = encryptText.getBytes(ENCODING);
		// 完成 Mac 操作
		return mac.doFinal(text);
	}

}
