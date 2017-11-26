package it.hella.random;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
	
	public static List<Integer> generateDistinctList(int maxLength, int maxValue){
		
		Set<Integer> set = new HashSet<>();
		List<Integer> random = new ArrayList<>();
		for (int i = 0; i < maxLength; i++){
			int next = rg.nextInt(maxValue);
			if (!set.contains(next)){
				random.add(next);
				set.add(next);
			}
		}
		return random;
	
	}
	

}
