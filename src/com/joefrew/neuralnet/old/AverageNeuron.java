package com.joefrew.neuralnet.old;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.joefrew.neuralnet.old.activationfunction.ActivationFunction;


/**
 * The BaseNeuron is a very simple neuron which averages its inputs and provides them to an activation function before
 * outputting them.
 * @author joe
 *
 */
public class AverageNeuron extends BaseNeuron {
	
	protected ActivationFunction activationFunction;
	
	public AverageNeuron(ActivationFunction activationFunction){
		this.activationFunction = activationFunction;
	}

	public AverageNeuron(AverageNeuron averageNeuron) {
		this.activationFunction = averageNeuron.getActivationFunction();
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
	
	public ActivationFunction getActivationFunction() {
		return this.activationFunction;
	}

	@Override
	public Neuron copy() {
		return new AverageNeuron(this);
	}

}
