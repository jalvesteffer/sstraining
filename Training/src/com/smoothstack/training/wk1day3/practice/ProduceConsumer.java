package com.smoothstack.training.wk1day3.practice;

import java.util.ArrayList;
import java.util.List;

public class ProduceConsumer {

	public static void main(String[] args) {
		List<Integer> sharedQueue = new ArrayList<>();
		
		Producer p0 = new Producer(sharedQueue, 0);
		Consumer c0 = new Consumer(sharedQueue);
		
		Thread pThread0 = new Thread(p0, "ProThread0");
		Thread cThread0 = new Thread(c0, "ConThread0");
		pThread0.start();
		cThread0.start();
		
		System.out.println("Half way");
		
		Producer p1 = new Producer(sharedQueue, 0);
		Consumer c1 = new Consumer(sharedQueue);
		
		Thread pThread1 = new Thread(p1, "ProThread1");
		Thread cThread1 = new Thread(c1, "ConThread1");
		pThread1.start();
		cThread1.start();
		
	}

}
