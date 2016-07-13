package com.chuanglan.myTest;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.cxf.jaxrs.client.WebClient;

import sun.misc.BASE64Encoder;

public class TestWebService {

	private static String TYPE_JSON = "";

	public static void webserviceTest(String[] args) {
		String str = "";
		String orderId = str = "ASDF";

		System.out.println(orderId + "  " + str);

		String URL = "http://www.eal-ceair.com/e-ldp/ws";
		WebClient client = WebClient.create(URL, "eal", "eal*123.", null);
		client.path("/systemWService/system/dicts/CONTINENT_CODETEXT_CACHE/")
				.accept(javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE).type(TYPE_JSON); // 大洲数据
		String response = client.get(String.class); // 这个可以自定义类的，默认用String
		System.out.println(response);

	}

	public static void test(String[] args) {
		AtomicInteger ai = new AtomicInteger(0);
		int i1 = ai.get();
		v(i1);
		int i2 = ai.getAndSet(5);
		v(i2);
		int i3 = ai.get();
		v(i3);
		int i4 = ai.getAndIncrement();
		v(i4);
		v(ai.get());

	}

	static void v(int i) {
		System.out.println("i : " + i);
	}

	public static void main(String[] args) {
		String a1 = "Y2h1YW5nbGFud2gxMjM0NTZ7Im9yZGVyIjp7ImVmZmVjdF90eXBlIjoiMCIsImZsb3dTaXplIjoi";
		String a2 = "MTAiLCJvcGVyYXRvciI6ImNubSIsInRlbGVwaG9uZSI6IjE1ODAwNTMxNDc0In0sInJlcXVlc3Rf";
		String a3 = "bm8iOiIyMDE2MDUxNjExMjUxMTc0NTI1NiIsInRpbWVzdGFtcCI6IjIwMTYwNTE2MTEyNTE0In1j";
		String a4 = "aHVhbmdsYW53aDEyMzQ1Ng==";
		getMD5(a1);
		getMD5(a2);
		getMD5(a3);
		getMD5(a4);
		System.out.println("===========");
		
		String an = "Y2h1YW5nbGFud2gxMjM0NTZ7Im9yZGVyIjp7ImVmZmVjdF90eXBlIjoiMCIsImZsb3dTaXplIjoiMTAiLCJvcGVyYXRvciI6ImNubSIsInRlbGVwaG9uZSI6IjE1ODAwNTMxNDc0In0sInJlcXVlc3Rfbm8iOiIyMDE2MDUxNjEyMTkzMjc0NzU4NiIsInRpbWVzdGFtcCI6IjIwMTYwNTE2MTIxOTM3In1jaHVhbmdsYW53aDEyMzQ1Ng==";
		getMD5(an);
		System.out.println("===========");
		
		BASE64Encoder b64 = new BASE64Encoder();
		String json = "{\"order\":{\"effect_type\":\"0\",\"flowSize\":\"10\",\"operator\":\"cnm\",\"telephone\":\"15800531474\"},\"request_no\":\"20160516112511745256\",\"timestamp\":\"20160516112514\"}";
		String b64Str = b64.encode(json.getBytes());
//		System.out.println(b64Str);

		String str = "chuanglanwh123456" + json + "chuanglanwh123456";
//		System.out.println(str);
		String decript = b64.encode(str.getBytes());
		System.out.println(": " + decript);

		getMD5(decript.replace("\r\n", ""));

	}

	public static String getMD5(String decript) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
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
			System.out.println(hexString);
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
		}
		return "";
	}
}
