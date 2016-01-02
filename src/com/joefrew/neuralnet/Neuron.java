package com.joefrew.neuralnet;

import java.util.List;

public interface Neuron {
	
	/**
	 * The activate method causes the neuron to gather its inputs, perform its activation function
	 * and then forward the results to all of its outputs.
	 */
	public void activate();
	
	/**
	 * For convenience the getValue function lets us get the last value that this neuron had upon activation.
	 * @return The last value the neuron had upon activation.
	 */
	public double getValue();
	
	/**
	 * Add a neural input to the set of inputs for this neuron.
	 * @param input
	 */
	public void addInput(NeuralInput input);
	
	/**
	 * Add a neural output to the set of outputs for this neuron.
	 * @param output
	 */
	public void addOutput(NeuralOutput output);
	
	
	/**
	 * Get a list of all of the inputs. Depending on the neuron implementation, this list
	 * may be a clone or the original list. Therefore, this list cannot be changed with the expectation
	 * that those changes will be present inside the neuron class.
	 * @return A list of all of the NeuralInput objects.
	 */
	public List<NeuralInput> getInputs();

	/**
	 * Get a list of all of the outputs. Like the getInputs() method, this method does not guarantee that the
	 * list will be the same as the list inside the neuron class.
	 * @return A list of all of the NeuralOutput objects.
	 */
	public List<NeuralOutput> getOutputs();
	
}
