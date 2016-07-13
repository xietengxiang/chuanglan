package com.chuanglan.myTest;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyTest5 {
	public static void main(String[] args) {
		String discount = "0.725";
		String price = "3";
		BigDecimal dis = new BigDecimal(discount);
		BigDecimal pri = new BigDecimal(price);
		double money = dis.multiply(pri).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println(money);

		double disc = 0.725;
		double pric = 3;
		BigDecimal dis2 = new BigDecimal(disc);
		BigDecimal pri2 = new BigDecimal(pric);
		double money2 = dis2.multiply(pri2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println(money2);
		
		
		String extId = "9145/流量幸运抽奖/小明👾👾👾7y6y  66";
		System.out.println(extId.length());
		for (int i = 0; i < extId.length(); i++) {
			if (extId.charAt(i) >= 0xD800 && extId.charAt(i) <= 0xDBFF) {
				String str = "" + extId.charAt(i) + extId.charAt(i + 1);
				extId = extId.replaceAll(str, "  ");
				System.out.println("=="+i);
			}
		}
		System.out.println(extId);
		System.out.println(extId.length());
		
		String phone = "13918520400";
		
		Pattern pattern = Pattern.compile("^1\\d{10}$");
		Matcher matcher = pattern.matcher(phone);
		if(matcher.find()){
			System.out.println("Yes");
		}else{
			System.out.println("No");
		}
	}
}
