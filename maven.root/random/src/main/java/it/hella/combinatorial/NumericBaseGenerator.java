package it.hella.combinatorial;

public class NumericBaseGenerator{
	
	private int[] current;
	private int maxDigit;
	
	public NumericBaseGenerator(int length, int base){
		
		if (base < 2){
			throw new IllegalArgumentException("Base should be greater or equal than 2");
		}
		this.maxDigit = base - 1;
		current = new int[length];
		init();
	
	}
	
	private void init() {
		
		for (int k = 0; k < current.length - 1; k++){
			current[k] = 0;
		}
		current[current.length - 1] = -1;
		
	}
	
	public void reset(){
		init();
	}

	public int[] next(){
		
		int k = current.length - 1;
		while(k >= 0){
			
			if (current[k] == maxDigit){
				current[k] = 0;
			}else{
				break;
			}
			k--;
			
		}
		if (k < 0){
			return new int[]{};
		}
		current[k] = current[k] + 1;
		return current;
		
	}

}

