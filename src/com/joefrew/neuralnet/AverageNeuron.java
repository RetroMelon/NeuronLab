package com.joefrew.neuralnet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.joefrew.neuralnet.activationfunct.ActivationFunction;


/**
 * The BaseNeuron is a very simple neuron which averages its inputs and provides them to an activation function before
 * outputting them.
 * @author joe
 *
 */
public class AverageNeuron implements Neuron, Serializable {
	
	protected double value = 0;
	protected ActivationFunction activationFunction;
	
	protected List<NeuralInput> inputs = new ArrayList<NeuralInput>();
	protected List<NeuralOutput> outputs = new ArrayList<NeuralOutput>();
	
	public AverageNeuron(ActivationFunction activationFunction){
		this.activationFunction = activationFunction;
	}

	/**
	 * Gathers the sum of the inputs from the getInputs function and uses the activation function 
	 * to calculate the value of the neuron before passing that value to all of the outputs.
	 */
	public void activate() {
		double inputSum = 0;
		for (NeuralInput in : this.getInputs()) {
			inputSum += in.getInput();
		}
		
		this.value = this.activationFunction.activate(inputSum);
		
		for (NeuralOutput out : this.getOutputs()) {
			out.setOutput(this.value);
		}		
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
