package it.hella.maven.root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.Test;

public class LogTests {
	
	private static final Logger logger = LoggerFactory.getLogger(LogTests.class);
	
	
	@Test
	public void logTest(){
		
		logger.info("logging {} succesfully", "info");
		logger.debug("logging {} succesfully", "debug");
		logger.error("logging {} succesfully", "error");
		
	}


}
