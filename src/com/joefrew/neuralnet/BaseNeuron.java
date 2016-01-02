package com.joefrew.neuralnet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.joefrew.neuralnet.activationfunction.ActivationFunction;


/**
 * The BaseNeuron is a very simple neuron which averages its inputs and provides them to an activation function before
 * outputting them.
 * @author joe
 *
 */
public class BaseNeuron implements Neuron, Serializable {
	
	protected double value = 0;
	
	protected List<NeuralInput> inputs = new ArrayList<NeuralInput>();
	protected List<NeuralOutput> outputs = new ArrayList<NeuralOutput>();

	/**
	 * The activate function for the base neuron does nothing
	 */
	public void activate() {
		
	}

	/**
	 * Gets the last value which was produced when the neuron was activated
	 */
	public double getValue() {
		return value;
	}

	public void addInput(NeuralInput input) {
		this.inputs.add(input);
	}

	public void addOutput(NeuralOutput output) {
		this.outputs.add(output);
	}

	public List<NeuralInput> getInputs() {
		return this.inputs;
	}

	public List<NeuralOutput> getOutputs() {
		return this.outputs;
	}

}
