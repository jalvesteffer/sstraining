package com.smoothstack.training.basics.four;

import com.smoothstack.training.wk1day3.practice.Movie;

public class DeadLockExample {

	public static void main(String[] args) {
		Movie m1 = new Movie(2010, "Avengers", 9.8f);
		Movie m2 = new Movie(2012, "Avengers - AU ", 7.8f);

		Integer counter = 0;

		Runnable thread1 = new Runnable() {
			
			@Override
			public void run() {
				try	{
					synchronized (m1)	{
						Thread.sleep(100);
						synchronized (m2)	{
							System.out.println("Thread One");
						}
					}
				} catch(Exception e)	{
					System.out.println("Something Failed in Thread One");
				}
			}
		};
		
		
		
		Runnable thread2 = new Runnable() {
			
			@Override
			public void run() {
				try	{
					synchronized (m2)	{
						Thread.sleep(100);
						synchronized (m1)	{
							System.out.println("Thread Two");
						}
					}
				} catch(Exception e)	{
					System.out.println("Something Failed in Thread Two");
				}
			}
		};
		
		new Thread(thread1).start();
		new Thread(thread2).start();
		
		
	}
}
