package com.joefrew.neuralnet;

/**
 * The weight generator is a class that generates weights for a weighted synapse
 * or biased neuron.
 * @author joe
 *
 */
public interface WeightGenerator {
	
	/**
	 * The next weight function provides a double value
	 */
	
	public double nextWeight();
}
