package com.chuanglan.myTest.xml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.UnsupportedCharsetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestHttpClient {

	public static String XML = "<request> <head>    <custInteId>clwhqwjr</custInteId>    <echo>2016070715094882</echo>    <timestamp>20160707150948</timestamp>    <chargeSign>rPRPSp3BnV8Ah4I8VMAxuQ==</chargeSign> </head> <body>  <item>    <orderId>20160707140152100000</orderId>    <orderType>1</orderType>    <packCode>100010</packCode>    <mobile>13817694186</mobile>    <result>0000</result>    <desc>成功</desc>  </item> </body></request>";
	public static String JSON = "{\"errcode\":\"20001\",\"msg\":\"充值失败\",\"order_id\":\"20160620161308100006\"}";
	public static String URL = "http://localhost:8011/api/send";
	public static String memberURL = "http://api.liulian253.com/api/send";
	public static String distriURL = "http://api.liulian253.com/api/sendDistributor";
	public static String lvchURL = "http://192.168.0.143:8080/api/test";
	
	public static String callbackURL1 = "http://localhost:8011/api/callback/1";//xicheng
	public static String callbackURL2 = "http://localhost:8011/api/callback/16";//tangtang
	public static String callbackURL3 = "http://localhost:8011/api/callback/15";//yundc
	public static String URL_xicheng = "http://api.liulian253.com/api/callback/1";
	public static String URL_yundc = "http://120.25.223.137:8088/Api/Recharge/index/type/json";

	public static void main(String[] args) {
		
//		String sign = MD5("102"+"20160701091802100251"+"1944697"+3+"149" + "e7bc14f8cc8eead5c520abd63a09c671583bbd90bbb078071905e5a500a64853");
//		System.out.println(sign);
		
		//堂堂回调
//		calbackFromTangtang(callbackURL2);
		//西城回调
//		xichengCallbackTest(lvchURL);
		
		//充值
		for(int i=0; i < 1; i++){
			postTest(memberURL, "chuanglan", "123456");
//			postTest(distriURL, "qdcs", "123456");
			
		}
		//渠道仿真账户：cld_qudao/123123; 直客仿真账户：chuanglan/123456
		//渠道账户：qdcs/123456 直客账户：lifang030303/123456; 
	}

	public static void postTest(String url,String user,String pswd) {
		HttpPost httpost = new HttpPost(url);

		String extId = "chuanglanTest"+ System.currentTimeMillis()%1000000000;
		
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("mobile","13486410000"));
		postParams.add(new BasicNameValuePair("package","00010"));
		postParams.add(new BasicNameValuePair("ext_id",extId));
		postParams.add(new BasicNameValuePair("account",user));
		Long ll = System.currentTimeMillis()/1000;
		postParams.add(new BasicNameValuePair("timestamp",ll+""));
		postParams.add(new BasicNameValuePair("noncestr","aaabbb"));
		StringBuffer strbuf = new StringBuffer();
		strbuf.append("account=").append(user);
		strbuf.append("&ext_id=").append(extId);
		strbuf.append("&mobile=").append("13486410000");
		strbuf.append("&noncestr=").append("aaabbb");
		strbuf.append("&package=").append("00010");
		strbuf.append("&timestamp=").append(ll);
		strbuf.append("&key=").append(pswd);//687858
		String signature = SHA1(strbuf.toString());
		System.out.println(url+"?"+strbuf+"&signature="+signature);
		postParams.add(new BasicNameValuePair("signature",signature));
		try {
			httpost.setEntity(new UrlEncodedFormEntity(postParams));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		CloseableHttpClient client = HttpClients.createDefault();
		try {
			HttpResponse response = client.execute(httpost);
			HttpEntity entity = response.getEntity();
			String result = getHttpEntityContent("utf-8", entity);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void yundcTest() {
		// 参数设置
		Map<String, String> params = new HashMap<String, String>();
		params.put("amount", "00070");
		params.put("mobile", "13817694186");
		params.put("noncestr", "123456");
		params.put("order_id", "D123456");
		params.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
		params.put("uname", "CL000003");
//		String signature = getSignatrue(params, info);
		params.put("signature", "AAAAAAAAAA");
		// POST
		HttpPost httpost = new HttpPost(URL_yundc);
		ObjectMapper mapper = new ObjectMapper();
		StringEntity se = null;
		try {
			se = new StringEntity(mapper.writeValueAsString(params), "utf-8");
		} catch (UnsupportedCharsetException | JsonProcessingException ex) {
			ex.printStackTrace();
		}
		httpost.setEntity(se);

		CloseableHttpClient client = HttpClients.createDefault();
		try {
			HttpResponse response = client.execute(httpost);
			HttpEntity entity = response.getEntity();
			String result = getHttpEntityContent("utf-8", entity);
			@SuppressWarnings("unchecked")
			HashMap<String, String> data = mapper.readValue(result, HashMap.class);
			if (!"0".equals(data.get("errcode"))) {
				System.out.println(data.get("errcode"));
				// 提交失败
				System.out.println("提交失败！");
			} else {
				System.out.println("成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void xichengCallbackTest(String url) {
		HttpPost httpost = new HttpPost(url);
		httpost.setHeader("content-type", "application/x-www-form-urlencoded");
		StringEntity se = null;
		try {
			se = new StringEntity("AAA888BBB", "utf-8");
		} catch (UnsupportedCharsetException e) {
			e.printStackTrace();
		}
		httpost.setEntity(se);

		CloseableHttpClient client = HttpClients.createDefault();
		try {
			HttpResponse response = client.execute(httpost);
			HttpEntity entity = response.getEntity();

			String result = getHttpEntityContent("utf-8", entity);

			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//模拟堂堂回调
	public static void calbackFromTangtang(String url) {
		
		StringBuffer chargeURL = new StringBuffer(url+"?");
		chargeURL.append("userId").append("=").append("149").append("&");
		chargeURL.append("bizId").append("=").append("test001").append("&");
		chargeURL.append("id").append("=").append("7405/李芳关注领流量0329/").append("&");
		chargeURL.append("downstreamSerialno").append("=").append("1603311824163824").append("&");
		chargeURL.append("status").append("=").append("2").append("&");
		String sign = MD5("102"+"20160701091802100251"+"1944697"+3+"149" + "e7bc14f8cc8eead5c520abd63a09c671583bbd90bbb078071905e5a500a64853");
		chargeURL.append("sign=").append(sign);
		
		// get
		HttpGet httpGet = new HttpGet(chargeURL.toString());
		httpGet.setHeader("Accept", "application/json");
		CloseableHttpClient client = HttpClients.createDefault();
		try {
			HttpResponse response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String res = getHttpEntityContent("utf-8", entity);
			System.out.println("回调结果："+res);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * MD5加密
	 */
	public static String MD5(String decript) {
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
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			return "";
		}
	
	}
	
	/**
	 * SHA1加密
	 */
	public static String SHA1(String decript){
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
			return "";
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
