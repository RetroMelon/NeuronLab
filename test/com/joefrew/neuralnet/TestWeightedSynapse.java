package com.joefrew.neuralnet;

import junit.framework.TestCase;

public class TestWeightedSynapse extends TestCase {
	
	private WeightedSynapse weightedSynapse;

	protected void setUp() throws Exception {
		super.setUp();
		
		weightedSynapse = new WeightedSynapse(2);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testInputForwardedWithWeight1() {
		weightedSynapse.setWeight(1);
		
		weightedSynapse.setOutput(5);
		assertEquals("Neuron output does not match input.", weightedSynapse.getInput(), (double)5, 0);
		
		weightedSynapse.setOutput(10);
		assertEquals("Neuron output does not match input.", weightedSynapse.getInput(), (double)10, 0);
		
		weightedSynapse.setOutput(-4.3);
		assertEquals("Neuron output does not match input.", weightedSynapse.getInput(), (double)-4.3, 0);
	}
	
	public void testInputForwardedWithWeight3() {
		double weight = 3.5;
		
		weightedSynapse.setWeight(weight);
		
		weightedSynapse.setOutput(5);
		assertEquals("Neuron output does not match input.", weightedSynapse.getInput(), (double)5*weight, 0);
		
		weightedSynapse.setOutput(10);
		assertEquals("Neuron output does not match input.", weightedSynapse.getInput(), (double)10*weight, 0);
		
		weightedSynapse.setOutput(-4.3);
		assertEquals("Neuron output does not match input.", weightedSynapse.getInput(), (double)-4.3*weight, 0);
	}
}
