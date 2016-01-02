package com.joefrew.neuralnet;

import java.util.List;

/**
 * The neural network class contains a set of neuron layers which it generates itself upon creation.
 * 
 * @author joe
 *
 */
public interface NeuralNetwork {
	
	/**
	 * Returns how many layers exist in the network (including the input and output layers)
	 * @return 
	 */
	public int size();
	
	/**
	 * gets a list of layers where the 0th will be the input layer and the last will be the output.
	 */
	public List<NeuronLayer> getLayers();
	
	/**
	 * activates each layer in the network in turn and returns the values of the outputs.
	 * @return an array of the values of each output node.
	 */
	public double[] activate();
}
