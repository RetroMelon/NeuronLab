package com.joefrew.neuralnet;

import junit.framework.TestCase;

public class TestSynapse extends TestCase {
	
	private Synapse synapse;

	protected void setUp() throws Exception {
		super.setUp();
		
		synapse = new Synapse();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testInputForwarded() {
		synapse.setOutput(5);
		assertEquals("Neuron output does not match input.", synapse.getInput(), (double)5, 0);
	}

}
