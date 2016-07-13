package com.chuanglan.myTest;

import java.util.Calendar;
import java.util.Date;

public class MyTest4 {

	public static void main(String[] args) {
		
		String extId = "8880/关注送流量/yxy";
		boolean flag = extId.contains("/");
		
		if (flag) {//活动短信
			String activityId = extId.substring(0, extId.indexOf("/"));
			System.out.println(activityId);
		}
		
		String paramStr = "from:12;;protocolCode:xicheng;;;;<xml><a>123123</a>";
		if(paramStr.indexOf(";;;;") != -1){
			System.out.println(paramStr.substring(0, paramStr.indexOf(";;;;")));
			System.out.println(paramStr.substring(paramStr.indexOf(";;;;")+4));
		}
		
		String value = " ";
		if( value == null || value.isEmpty() || value.equals("null") ){
			System.out.println("空");
		} else{
			System.out.println("非空");
		}
		
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int timestamp = (calendar.get(Calendar.MONTH) + 1) * 0x5f5e100
				+ calendar.get(Calendar.DAY_OF_MONTH) * 0xf4240
				+ calendar.get(Calendar.HOUR) * 10000
				+ calendar.get(Calendar.MINUTE) * 100
				+ calendar.get(Calendar.SECOND);
		
		
		System.out.println(timestamp);
	}
}
