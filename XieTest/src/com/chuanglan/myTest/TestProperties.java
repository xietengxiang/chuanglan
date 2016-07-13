package com.chuanglan.myTest;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestProperties {

	public static void main(String[] args) {
		String jdbc = "jdbc.properties";
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(jdbc));

			Properties p = new Properties();

			p.load(in);
			
			String property = p.getProperty("interface.password");
			System.out.println(property);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
