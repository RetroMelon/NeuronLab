package com.joefrew.neuralnet;

/**
 * The dummy neural input simply forwards the input it was given in the constructor.
 * @author joe
 *
 */
public class DummyInput implements NeuralInput {
	
	private double input;
	
	public DummyInput(double input) {
		this.input = input;
	}

	public double getInput() {
		return input;
	}
	
}