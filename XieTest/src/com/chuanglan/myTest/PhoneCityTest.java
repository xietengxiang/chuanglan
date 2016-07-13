package com.chuanglan.myTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneCityTest {

	public static void main(String[] args) {
		String nums = "18491217376,13699850055,15781963099,13182099314,"
				+ "14782814621,17052832730,15000000369";
		String[] arr = nums.split(",");
		for (int i = 0; i < arr.length; i++) {
			String res = getOper(arr[i]);
			System.out.println(res);
		}
		
	}
	
	public static String getOper(String str){
		String res = "";
		String regEx[] = {"1(3[4-9]|47|5[012789]|78|8[23478])\\d{8}$|^1705\\d{7}",
				"(1(3[0-2]|45|5[56]|76|8[56])\\d{8}|^170[4789]\\d{7}$)",
				"1([35]3|7[7]|8[019])\\d{8}$|^1700\\d{7}"};
		int a = 0;
		for (int i = 0; i < regEx.length; i++) {
			Pattern pattern = Pattern.compile(regEx[i]);
			Matcher matcher = pattern.matcher(str);
			if(matcher.find()){
				a = i;
				break;
			}
		}
		switch (a) {
		case 0:
			res = "移动";
			break;
		case 1:
			res = "联通";
			break;
		case 2:
			res = "电信";
			break;

		default:
			break;
		}
		return res;
	}
}
