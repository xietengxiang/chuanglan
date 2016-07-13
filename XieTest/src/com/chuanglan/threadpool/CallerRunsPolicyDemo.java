package com.chuanglan.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CallerRunsPolicyDemo {

    private static final int THREADS_SIZE = 1;
    private static final int CAPACITY = 10;

    public static void main(String[] args) throws Exception {

        // 创建线程池。线程池的"最大池大小"和"核心池大小"都为1(THREADS_SIZE)，"线程池"的阻塞队列容量为1(CAPACITY)。
        ThreadPoolExecutor pool = new ThreadPoolExecutor(THREADS_SIZE, THREADS_SIZE, 0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(CAPACITY));
        // 设置线程池的拒绝策略为"CallerRunsPolicy"
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        boolean bool = true;
        int count = 0;
        while (count<2000) {
            Runnable myrun = new MyRunnable("task-" + ++count);
            try{
            	pool.execute(myrun);
            }catch(RejectedExecutionException  e){
            	System.out.println("====");
            	bool = false;
            }
        }

        if(!bool){
        	bool = true;
        }
        
        // 关闭线程池
        pool.shutdown();
    }
}

class MyRunnable implements Runnable {
    private String name;
    public MyRunnable(String name) {
        this.name = name;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " is running.");
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}