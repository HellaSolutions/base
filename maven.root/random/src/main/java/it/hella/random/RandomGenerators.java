package it.hella.random;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomGenerators {
	
	private static final Random rg = new Random();
	
	private RandomGenerators(){}
	
	public static List<Integer> generateList(int maxLength, int maxValue){
		
		List<Integer> random = new ArrayList<>();
		for (int i = 0; i < maxLength; i++){
			random.add(rg.nextInt(maxValue));
		}
		return random;
	
	}

}
