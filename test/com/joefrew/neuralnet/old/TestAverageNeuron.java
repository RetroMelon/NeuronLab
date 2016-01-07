package com.joefrew.neuralnet.old;

import com.joefrew.neuralnet.old.AverageNeuron;
import com.joefrew.neuralnet.old.Neuron;
import com.joefrew.neuralnet.old.activationfunction.ActivationFunction;

import junit.framework.TestCase;

public class TestAverageNeuron extends TestCase {

	private Neuron neuron;
	private ActivationFunction act;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		act = new DummyActivationFunction();
		neuron = new AverageNeuron(act);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testValue0() {
		assertEquals(neuron.getValue(), 0, 0);
	}
	

	
	public void testAddingInputsAndOutputs() {
		
		DummyInput input = new DummyInput(1);
		DummyOutput output = new DummyOutput();
		
		neuron.addInput(input);
		neuron.addOutput(output);
		
		assertTrue(neuron.getInputs().contains(input));
		assertTrue(neuron.getOutputs().contains(output));
	}
	
	public void testSimpleActivation() {
		
		DummyInput input = new DummyInput(1);
		DummyInput input2 = new DummyInput(2);
		DummyOutput output = new DummyOutput();
		
		neuron.addInput(input);
		neuron.addInput(input2);
		neuron.addOutput(output);
		
		//activating the neuron to get the inputs, calculate and then pass it on to the outputs.
		neuron.activate();
		
		assertEquals(3, output.getOutput(), 0);
	}

}
