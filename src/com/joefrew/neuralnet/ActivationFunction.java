package com.joefrew.neuralnet;

/**
 * The ActivationFunction interface is one that takes a single double value and outputs a single double value.
 * @author joe
 *
 */
public interface ActivationFunction {
	
	/**
	 * The activate function takes a single double value and outputs a single double value.
	 * @param input A double value, presumably from a transfer function.
	 * @return A double value.
	 */
	public double activate(double input);

}
