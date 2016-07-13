package com.chuanglan.myTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class TestDBhelper {

	public static final String url  = "jdbc:mysql://192.168.0.238/dev_cl_liulian?useUnicode=true&characterEncoding=utf-8";  
	public static final String name = "org.logicalcobwebs.proxool.ProxoolDriver";  
	public static final String user = "root"; //user
	public static final String password =  "Chuanglan!@#253"; //7392136

	public Connection conn = null;
	public PreparedStatement pst = null;
	
	private static Properties info;

	public TestDBhelper(String sql) {
		try {
			init();
			pst = conn.prepareStatement(sql);//准备执行语句 
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private synchronized void init() throws SQLException, ClassNotFoundException{
		if(info == null){
			info = new Properties();
			info.setProperty("proxool.alias", "test");
			info.setProperty("proxool.maximum-connection-count", "64");
			info.setProperty("user", user);
			info.setProperty("password", password);
		}
		Class.forName(name);//指定连接类型 
		conn = DriverManager.getConnection("proxool.test:com.mysql.jdbc.Driver:"+url, info);//获取连接 
	}
	
	public static void main(String[] args) {
		String sql = " select o.*, m.member_account, m.member_url from cl_order o inner join cl_member m on o.member_id=m.member_id where o.gateway_id in(?) and o.remote_code=? and o.order_status=1 ";
		TestDBhelper dbHelper = new TestDBhelper(sql);
		ResultSet ret = null;
		try {
			dbHelper.pst.setString(1, "19,80");
			dbHelper.pst.setString(2, "000015");
			ret = dbHelper.pst.executeQuery();
			while(ret.next()){
				int id = ret.getInt(1);
				String cityID = ret.getString(2);
				String cdate = ret.getString(8);
				System.out.println(id + " " + cityID + " " + cdate);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
