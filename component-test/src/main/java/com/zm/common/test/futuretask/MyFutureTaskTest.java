/**
 * 
 */
package com.zm.common.test.futuretask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 并发编程-案例
 * 
 * @author zengming
 *
 */
public class MyFutureTaskTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		// 分布式架构中需要两个服务的结果

		long startTime = System.currentTimeMillis();

		// 模拟从服务1获取结果
		Callable<String> callable1 = new Callable<String>() {

			@Override
			public String call() throws Exception {
				// 模拟耗时一秒
				Thread.sleep(2000);
				return "result1";
			}
		};

		// 模拟从服务2获取结果
		Callable<String> callable2 = new Callable<String>() {

			@Override
			public String call() throws Exception {
				// 模拟耗时一秒
				Thread.sleep(2000);
				return "result2";
			}
		};

		MyFutureTask<String> task1 = new MyFutureTask<>(callable1);
		MyFutureTask<String> task2 = new MyFutureTask<>(callable2);

		ExecutorService executor = Executors.newFixedThreadPool(10);
		executor.submit(task1);
		executor.submit(task2);

		StringBuilder bfBuilder = new StringBuilder();
		bfBuilder.append(task1.get());
		bfBuilder.append(task2.get());
		System.out.println(bfBuilder.toString());
		System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
	}

}
