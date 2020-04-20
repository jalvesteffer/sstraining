package com.smoothstack.training.basics.four;

public class SynchronizedExample {

	public Integer add(Integer numb1, Integer numb3, Integer numb2) {
		Integer sumOne = numb1 + numb2;

		synchronized (sumOne) {
			Integer sumTwo = sumOne + numb3;

			Integer sumThree = sumTwo + numb3;

			return sumOne + sumTwo + sumThree;
		}

	}

}
