package random;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.hella.combinatorial.BaseGenerator;
import it.hella.random.RandomGenerators;

public class BaseGeneratorTests {
	
	private static final Logger logger = LoggerFactory.getLogger(RandomGenerators.class);

	@Test
	public void testBinary(){
		
		int length = 10;
		BaseGenerator generator = new BaseGenerator(30, 2);
		int iterations = 0;
		int[] current;
		do{
			current = generator.next();
			//logger.debug(Arrays.toString(current));
			iterations++;
			
		}while(current.length > 0);
		assertEquals(Double.valueOf(Math.pow(2, 30)).intValue(), iterations - 1);
		
		
	}
	
	@Test
	public void testBase3(){
		
		int length = 10;
		BaseGenerator generator = new BaseGenerator(10, 3);
		int iterations = 0;
		int[] current;
		do{
			current = generator.next();
			logger.debug(Arrays.toString(current));
			iterations++;
			
		}while(current.length > 0);
		assertEquals(Double.valueOf(Math.pow(3, 10)).intValue(), iterations - 1);
		
		
	}

}
