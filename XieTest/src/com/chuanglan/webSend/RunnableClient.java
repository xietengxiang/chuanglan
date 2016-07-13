package com.chuanglan.webSend;

import java.util.ArrayList;
import java.util.List;

public class RunnableClient implements Runnable {

	private List<String> batchList = new ArrayList<String>();

	public Object obj = new Object();

	public RunnableClient(List<String> batchList) {
		this.batchList = batchList;
	}

	@Override
	public void run() {
		while (true) {
			try {
				String str = getData();
				if(null == str) break;
				System.out.println(Thread.currentThread().getName()+" send:"+str);
				Thread.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String getData() {
		synchronized (obj) {
			if (batchList != null && !batchList.isEmpty()) {
				String str = batchList.get(0);
				batchList.remove(str);
				return str;
			} else {
				return null;
			}
		}
	}

	public void addData(String data) {
		synchronized (obj) {
			batchList.add(data);
		}
	}
}
