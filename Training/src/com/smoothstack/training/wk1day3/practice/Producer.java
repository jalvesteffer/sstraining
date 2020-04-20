package com.smoothstack.training.wk1day3.practice;

import java.util.List;

public class Producer implements Runnable {

	private List<Integer> sharedQueue;
	
	private int maxSize = 10;
	
	int productionSize = 4;
	
	int producerNo;

	/**
	 * @param sharedQueue
	 * @param producerNo
	 */
	public Producer(List<Integer> sharedQueue, int producerNo) {
		super();
		this.sharedQueue = sharedQueue;
		this.producerNo = producerNo;
	}

	@Override
	public void run() {
		synchronized (sharedQueue)	{
			for (int i = 1; i <= productionSize; i++)	{
				try	{
					produce(i);
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void produce (int i) throws InterruptedException	{
		synchronized (sharedQueue)	{
			
			while (sharedQueue.size() == maxSize)	{
				System.out.println(Thread.currentThread().getName() + " buffer is full, producer is waiting");
				sharedQueue.wait();
			}
			int myProduce = (producerNo * productionSize) + i;
			System.out.println(Thread.currentThread().getName() + " produced " + myProduce);
			sharedQueue.add(myProduce);
			
			Thread.sleep((long)(Math.random() * 1000));
			sharedQueue.notify();
			
		}
	}
}
