package com.chuanglan.webSend;

import java.io.IOException;
import java.util.Timer;

public class TimerTest {

	public static void main(String[] args) {
		StringBuffer phoneNOs = new StringBuffer("1,2,3,4,5,6,");
		System.out.println(phoneNOs.substring(0, phoneNOs.length()-1));
		
		Timer timer = new Timer();
		timer.schedule(new MyTask(), 1000, 3000);// 轮询执行，1秒后开始，每3秒一次
		timer.schedule(new MyTask2(), 1000, 1500);
		while (true) {
			try {
				int in = System.in.read();
				if (in == 's') {
					timer.cancel();
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}

	static class MyTask extends java.util.TimerTask {
		public void run() {
			System.out.println("________");
		}
	}
	
	static class MyTask2 extends java.util.TimerTask {
		public void run() {
			System.out.println("========");
		}
	}
}
