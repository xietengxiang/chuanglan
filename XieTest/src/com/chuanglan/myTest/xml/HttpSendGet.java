package com.chuanglan.myTest.xml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.UnsupportedCharsetException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpSendGet {

	static String URL = "http://222.73.117.169/msg/HttpBatchSendSM?account=V-kpkj&pswd=Tch888888&mobile=13817694186&msg=【创蓝文化】您的注册验证码是：1131.请完成注册&needstatus=true";
	static String URL2 = "http://222.73.117.158/msg/HttpBatchSendSM?account=jiekou-clcs-09&pswd=JieKou778899&mobile=13817694186&msg=【创蓝文化】您的注册验证码是：1131.请完成注册&needstatus=true";
	public static void main(String[] args) {
		HttpGet get = new HttpGet(URL2);
		

		CloseableHttpClient client = HttpClients.createDefault();
		try {
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();

			String result = getHttpEntityContent("utf-8", entity);

			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getHttpEntityContent(String coder, HttpEntity entity)
			throws UnsupportedEncodingException, UnsupportedOperationException, IOException {
		StringBuilder stringBuilder = new StringBuilder();
		if (coder == null) {
			coder = "utf-8";
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), coder));
		boolean firstLine = true;
		String line = null;
		;
		while ((line = reader.readLine()) != null) {
			if (!firstLine) {
				stringBuilder.append(System.getProperty("line.separator"));
			} else {
				firstLine = false;
			}
			stringBuilder.append(line);
		}
		return stringBuilder.toString();
	}
}
