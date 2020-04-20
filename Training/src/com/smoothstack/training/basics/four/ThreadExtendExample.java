package com.smoothstack.training.basics.four;

public class ThreadExtendExample extends Thread {
	
	public void run()	{
		System.out.println("Running thread");
	}
	
	public static void main(String[] args)	{
		ThreadExtendExample te1 = new ThreadExtendExample();
		te1.start();
		
		ThreadExtendExample te2 = new ThreadExtendExample();
		te2.start();
		
	}
}
