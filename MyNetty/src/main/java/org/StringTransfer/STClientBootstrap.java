package org.StringTransfer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class STClientBootstrap {

	
	
	 public static void main(String[] args) throws Exception {  
	      
		 //ThreadPoolExecutor tpe=new ThreadPoolExecutor(100, 100, 200, TimeUnit.MILLISECONDS,
	     //           new ArrayBlockingQueue<Runnable>(100));
		 
		 
		 ExecutorService tpe=Executors.newFixedThreadPool(10); //when the thread pool is full, new thread will be waiting.
		 //Executor tpe=Executors.newCachedThreadPool(); //when the thread pool is fulled, it will create the new thread.
		 for (int i = 0; i < 30; i ++) 
		        {
				 //tpe.execute(new STClient("127.0.0.1", 8099));
				 //System.out.println("Thread Pool Count:"+);
				 tpe.submit(new STClient("127.0.0.1", 8099));
		        }
		 tpe.shutdown();
	    }

}
