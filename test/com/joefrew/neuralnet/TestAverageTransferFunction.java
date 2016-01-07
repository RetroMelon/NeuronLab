package com.joefrew.neuralnet;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestAverageTransferFunction {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExceptionOnUnequalInputsAndWeights() {
		TransferFunction transfer = new AverageTransferFunction();
		
		try {
			transfer.transfer(new double[]{1, 2, 3, 4}, new double[]{1, 2, 3});
			
			fail("The transfer function did not throw an exception when 4 inputs and 3 weights were provided.");
		} catch (Exception e) {
			
		}
	}
	
	
	@Test
	public void testCorrectResults1Input() {
		TransferFunction transfer = new AverageTransferFunction();
		
		try {
			double result = transfer.transfer(new double[]{2}, new double[]{3});
			double expectedResult = 6;
			
			assertEquals(expectedResult, result, 0);			
		} catch (Exception e) {
			fail("The transfer function should not have thrown an error with the valid inputs of [2, 1] and [3, 10].");
		}
	}
	
	
	@Test
	public void testCorrectResults2Inputs() {
		TransferFunction transfer = new AverageTransferFunction();
		
		try {
			double result = transfer.transfer(new double[]{2, 1}, new double[]{3, 10});
			double expectedResult = ((2 * 3) + (1 * 10)) / 2;
			
			assertEquals(expectedResult, result, 0);			
		} catch (Exception e) {
			fail("The transfer function should not have thrown an error with the valid inputs of [2, 1] and [3, 10].");
		}
	}

}
