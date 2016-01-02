package com.joefrew.neuralnet;

/**
 * The bias input class is used with the bias neuron.
 * @author joe
 *
 */
public class BiasInput implements NeuralInput {
	
	private double bias;
	
	public BiasInput(double bias) {
		this.bias = bias;
	}
	
	public void setBias(double newBias) {
		this.bias = newBias;
	}

	public double getInput() {
		return bias;
	}

}
