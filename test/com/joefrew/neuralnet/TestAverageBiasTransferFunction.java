package com.joefrew.neuralnet;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestAverageBiasTransferFunction {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExceptionOnIncorrectNumberOfInputsAndWeights() {
		TransferFunction transfer = new AverageBiasTransferFunction();
		
		try {
			transfer.transfer(new double[]{1, 2, 3}, new double[]{1, 2, 3});
			
			fail("The transfer function did not throw an exception when 3 inputs and 3 weights were provided.");
		} catch (Exception e) {
			
		}
		
		try {
			transfer.transfer(new double[]{1, 2, 3}, new double[]{1, 2, 3, 4});
			
		} catch (Exception e) {
			fail("The transfer function threw an exception when 3 inputs and 4 weights were provided.");			
		}
	}
	
	
	@Test
	public void testCorrectResults1Input() {
		TransferFunction transfer = new AverageBiasTransferFunction();
		
		try {
			double result = transfer.transfer(new double[]{2}, new double[]{3, 10});
			double expectedResult = ((2 * 3) + 10)/2;
			
			assertEquals(expectedResult, result, 0);			
		} catch (Exception e) {
			fail("The transfer function should not have thrown an error with the valid inputs of [2, 1] and [3, 10].");
		}
	}
	
	
	@Test
	public void testCorrectResults2Inputs() {
		TransferFunction transfer = new AverageBiasTransferFunction();
		
		try {
			double result = transfer.transfer(new double[]{2, 1}, new double[]{3, 10, 5});
			double expectedResult = ((2 * 3) + (1 * 10) + 5) / 3;
			
			assertEquals(expectedResult, result, 0);			
		} catch (Exception e) {
			fail("The transfer function should not have thrown an error with the valid inputs of [2, 1] and [3, 10, 5].");
		}
	}

}
