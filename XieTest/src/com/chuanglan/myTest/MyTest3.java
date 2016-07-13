package com.chuanglan.myTest;

public class MyTest3 implements Runnable {

	public static void main(String[] args) {
		MyTest3 m = new MyTest3();
		Thread t = new Thread(m);
		t.start();
	}
	
	@Override
	public void run() {
		String arr[] = {"12","56","89","XX","32","34"};
		for(String str : arr){
			System.out.println("循环执行1。。。");
			doTest(str);
			System.out.println("循环执行2。。。");
			try {
				Thread.sleep(1 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void doTest(String str){
		try{
			int a = Integer.parseInt(str);
			System.out.println(a);
		}catch(Exception e){
			System.out.println("异常");
		}finally {
			
		}
		
	}
}
