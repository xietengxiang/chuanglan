package com.chuanglan.webSend;

import java.util.ArrayList;
import java.util.List;

public class MainTest {
	
	public static void main(String[] args) {
		
		List<String> batchList = new ArrayList<String>();
		batchList.add("1");
		
		RunnableClient client = new RunnableClient(batchList);

		try{
			Thread t = new Thread(client);
			t.setName("Tom");
			t.start();
			for(int i=2;i<1000;i++){
				Thread.sleep(1);
				if(t.isAlive()){
					client.addData(i+"");
				}else{
					t = new Thread(client);
					t.setName("Tom"+i);
					client.addData(i+"");
					t.start();
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
