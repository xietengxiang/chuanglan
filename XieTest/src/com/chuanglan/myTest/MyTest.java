package com.chuanglan.myTest;

public class MyTest {

	public static void main(String[] args) {
		String sql = "select * from table where lineId in (?)";
		String[] lineIds = { "京沪", "京广", "武广", "汉蓉", "广深" };
		StringBuffer ss = new StringBuffer();
		String str = "线";
		for (String s : lineIds) {
			ss.append("'" + s + str + "',");
		}
		String s = ss.toString();
		s = s.substring(0, s.length() - 1);

		sql = sql.replace("?", s);
		System.out.println(sql);
		
		Long currentTime = System.currentTimeMillis()/1000;
		System.out.println(currentTime + "===" + currentTime.intValue());

		// 取绝对值
		System.out.println(Math.abs(1460613690457L - 1460613944000L));
		System.out.println(1460613690457L - 1460613944000L);
	}
}
