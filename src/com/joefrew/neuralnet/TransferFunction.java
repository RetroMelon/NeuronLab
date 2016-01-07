package com.joefrew.neuralnet;

/**
 * The transfer function interface is one that takes in an array of inputs and an array of 
 * associated weights and outputs a single double value.
 * @author joe
 *
 */
public interface TransferFunction {
	
	/**
	 * The transfer function takes the array of inputs and weights and performs some computation to produce 
	 * a single output which will be fed in to an activation function. 
	 * 
	 * The arrays of inputs and outputs must be of the same length.
	 * @param inputs An array of double values.
	 * @param weights An array of double values.
	 * @return a single double value for use with an activation function.
	 */
	public double transfer(double[] inputs, double[] weights) throws Exception; //TODO: make this a self-defined exception.

}
