package com.joefrew.neuralnet.old;

import com.joefrew.neuralnet.old.BiasNeuron;
import com.joefrew.neuralnet.old.activationfunction.ActivationFunction;

import junit.framework.TestCase;

public class TestBiasNeuron extends TestCase {
	
	private BiasNeuron neuron;
	private ActivationFunction act;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		act = new DummyActivationFunction();
		neuron = new BiasNeuron(act, 0);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testActivationWithNoInputs() {
		
		neuron.setBias(4);

		DummyOutput output = new DummyOutput();
		
		neuron.addOutput(output);
		
		//activating the neuron to get the inputs, calculate and then pass it on to the outputs.
		neuron.activate();
		
		assertEquals(4, output.getOutput(), 0);
	}
	
	public void testSimpleActivation() {
		
		neuron.setBias(-7);
		
		DummyInput input = new DummyInput(1);
		DummyInput input2 = new DummyInput(2);
		DummyOutput output = new DummyOutput();
		
		neuron.addInput(input);
		neuron.addInput(input2);
		neuron.addOutput(output);
		
		//activating the neuron to get the inputs, calculate and then pass it on to the outputs.
		neuron.activate();
		
		assertEquals(-4, output.getOutput(), 0);
	}

}
