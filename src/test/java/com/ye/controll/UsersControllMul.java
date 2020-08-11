package com.ye.controll;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UsersControllMul {
	public static void main(String[] args) throws InterruptedException, ExecutionException{
        //从桌面读取测试帐号
		int textCount=1;
        String txt ="";
        File file = new File("C:/Users/yebing/Desktop/毕业设计测试帐号.txt");
        FileInputStream in = null;
		try {
			in = new FileInputStream(file);
	        byte[] b = new byte[1024];
	        int j = in.read(b);
	        while(j!=-1){
	        	txt = txt+new String(b,0,j);
	        	j = in.read(b);
	        }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        //对测试数据进行处理
		String[] text = txt.split("\r\n");
        //取得一个倒计时器，从subThreadNum开始
        CountDownLatch countDownLatch = new CountDownLatch(Integer.parseInt(text[0]));
        //开启一个线程池
        //创建一个可缓存的线程池。如果线程池的大小超过了处理任务所需要的线程，那么就会回收部分
        ExecutorService executor = Executors.newCachedThreadPool();
		long startTime = System.currentTimeMillis();
		for(int i =0;i<Integer.parseInt(text[0]);i++){
			//执行TestThread里面的run方法
			if(textCount!=text.length){
		        executor.execute(new UersControllRunnable(text[textCount],countDownLatch,900000));
		        ++textCount;
			}else{
				textCount = 1;
				executor.execute(new UersControllRunnable(text[textCount],countDownLatch,900000));
			}
		}
		System.out.println("线程准备完毕！");
		//线程池不再接受新的任务，将允许先前提交的任务在终止之前执行
        executor.shutdown();
        //主线程等待子线程完毕
        countDownLatch.await();
        //添加程序结束监听  
		UersControllRunnable testResult = new UersControllRunnable();
        System.out.println(testResult.getResult());
		long endTime = System.currentTimeMillis();
		System.out.println("响应时间："+(endTime-startTime)+"ms");

	}
}
