package com.joefrew.neuralnet;

import com.joefrew.neuralnet.activationfunct.ActivationFunction;

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
	
	/*
	 * Creating some dummy input and output classes for testing the base neuron
	 */
	private class DummyInput implements NeuralInput {
		
		private double input;
		
		public DummyInput(double input) {
			this.input = input;
		}

		public double getInput() {
			return input;
		}
		
	}
	
	private class DummyOutput implements NeuralOutput {
		
		private double output = 0;
		
		public void setOutput(double output) {
			this.output = output;			
		}
		
		public double getOutput() {
			return output;
		}
		
	}
	
	public void testAddingInputsAndOutputs() {
		
		DummyInput input = new DummyInput(1);
		DummyOutput output = new DummyOutput();
		
		neuron.addInput(input);
		neuron.addOutput(output);
		
		assertTrue(neuron.getInputs().contains(input));
		assertTrue(neuron.getOutputs().contains(output));
	}
	
	/*
	 * This dummy activation function simply forwards the input over to the output.
	 */
	private class DummyActivationFunction implements ActivationFunction {

		public double activate(double input) {
			return input;
		}
		
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
