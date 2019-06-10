package com.zm.common.test.futuretask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MyFutureTask<V> implements Runnable, Future<V> {

	private Callable<V> callable;

	private V result = null;

	public MyFutureTask(Callable<V> callable) {
		super();
		this.callable = callable;
	}

	@Override
	public void run() {
		try {
			result = callable.call();
			synchronized (this) {
				this.notifyAll();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public V get() throws InterruptedException, ExecutionException {
		if (result != null) {
			return result;
		}
		// 阻塞等待返回结果
		// wait、notify...必须写在同步代码块里
		synchronized (this) {
			this.wait();
		}
		return result;
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		// TODO Auto-generated method stub
		return null;
	}

}
