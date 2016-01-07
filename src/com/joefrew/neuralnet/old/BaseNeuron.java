package com.joefrew.neuralnet.old;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.joefrew.neuralnet.old.activationfunction.ActivationFunction;


/**
 * The BaseNeuron is a very simple class which provides basic functions for adding and removing inputs, etc.
 * @author joe
 *
 */
public abstract class BaseNeuron implements Neuron, Serializable, Cloneable {
	
	protected double value = 0;
	
	protected List<NeuralInput> inputs = new ArrayList<NeuralInput>();
	protected List<NeuralOutput> outputs = new ArrayList<NeuralOutput>();

	/**
	 * The activate function for the base neuron does nothing
	 */
	public abstract void activate();

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
	
	public abstract Neuron copy();
	
	
}
