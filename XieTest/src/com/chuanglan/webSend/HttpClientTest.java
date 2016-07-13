package com.chuanglan.webSend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpClientTest {

	public final static String API_URL = "http://222.73.117.138:7891/mt";
	
	public static void main(String[] args) {
		HttpClientTest test = new HttpClientTest(); 
		Map<String, String> params = new HashMap<String, String>();
		params.put("account", "N13817116185");
		params.put("password", "123456");
		params.put("phone", "13817694186");
		params.put("content", "【创蓝文化】您的验证码是123123，30分钟内有效，请勿告知他人。退订请回TD");
		CloseableHttpResponse response = test.send(params);
		String res = test.getResponseMessage(response);
		System.out.println("The result is : " + res);
	}
	
	private String getResponseMessage(CloseableHttpResponse response) {
		StringBuffer strBuf = new StringBuffer();
		HttpEntity entity = response.getEntity();
		try {
			BufferedReader read = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
			String line = null;
			while( (line = read.readLine()) != null){
				strBuf.append(line);
			}
			read.readLine();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strBuf.toString();
	}

	public CloseableHttpResponse send(Map<String, String> params){
		String account = params.get("account");
		String content = params.get("content");
		String password = params.get("password");
		String phone = params.get("phone");
		
		String encodedContent= null;
		try {
			encodedContent = URLEncoder.encode(content,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		StringBuffer strBuf = new StringBuffer(API_URL);
		strBuf.append("?un=").append(account);
		strBuf.append("&pw=").append(password);
		strBuf.append("&da=").append(phone);
		strBuf.append("&sm=").append(encodedContent);
		// dc=中文 rd=返回状态报告 rf=返回json格式 tf=编码方式URLEncode+UTF8
		strBuf.append("&dc=15&rd=1&rf=2&tf=3");
		
		System.out.println(strBuf);
		
		HttpGet get = new HttpGet(strBuf.toString());
		CloseableHttpClient client = HttpClients.createDefault();
		try {
			return client.execute(get);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
