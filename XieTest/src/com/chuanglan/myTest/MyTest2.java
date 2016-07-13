package com.chuanglan.myTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MyTest2 {

	public static void main(String[] args) {
		
		for(int i = 0;i<255;i++){
            char a = (char) i;
            System.out.println(a+"........."+i);
        }
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = sdf.format(new Date());
		System.out.println(timestamp);
		
		System.out.println(getEcho(20));
		
		String[] SYSTEM_WHITELIST = new String[0];
		for (int i = 0; i < SYSTEM_WHITELIST.length; i++) {
			if("1".equals(SYSTEM_WHITELIST[i])){
				System.out.println("===");
			}
		}
		
		//=======
		System.out.println("===========os.name:"+System.getProperties().getProperty("os.name"));  
		System.out.println("===========file.separator:"+System.getProperties().getProperty("file.separator"));
		
		if(System.getProperties().getProperty("os.name").contains("Windows")){
			System.out.println("微软");
		}
	}
	
	public static String getEcho(int length) {
		StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");   
        StringBuffer sb = new StringBuffer();   
        Random random = new Random();   
        int range = buffer.length();   
        for (int i = 0; i < length; i ++) {   
            sb.append(buffer.charAt(random.nextInt(range)));   
        }   
        return sb.toString();   
	}
}
