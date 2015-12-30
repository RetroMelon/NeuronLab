package com.joefrew.neuralnet;

import java.io.Serializable;
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
		// TODO Auto-generated method stub
		
	}

	public void addOutput(NeuralOutput output) {
		// TODO Auto-generated method stub
		
	}

	public List<NeuralInput> getInputs() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<NeuralOutput> getOutputs() {
		// TODO Auto-generated method stub
		return null;
	}

}
