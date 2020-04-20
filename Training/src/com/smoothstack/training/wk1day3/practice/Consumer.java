package com.smoothstack.training.wk1day3.practice;

import java.util.List;

public class Consumer implements Runnable {

	private List<Integer> sharedQueue;

	/**
	 * @param sharedQueue
	 */
	public Consumer(List<Integer> sharedQueue) {
		this.sharedQueue = sharedQueue;
	}

	@Override
	public void run() {
		try {
			while (sharedQueue.size() > 0)	{
				consume();
				Thread.sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void consume() throws InterruptedException {
		synchronized (sharedQueue) {
			
			while (sharedQueue.size() == 0)	{
				System.out.println(Thread.currentThread().getName() + " buffer is empty, consumer is waiting");
				sharedQueue.wait();
			}
			
		
			Thread.sleep((long) (Math.random() * 1000));
			System.out.println(Thread.currentThread().getName() + " consumed " + sharedQueue.remove(0));
			sharedQueue.notify();
			
		}
	}

}
