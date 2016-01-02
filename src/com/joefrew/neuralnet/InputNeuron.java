package com.joefrew.neuralnet;

public class InputNeuron extends BaseNeuron {

	/**
	 * The activate function for the input neuron forwards whatever was placed as the value
	 * in to the synapses
	 */
	public void activate() {
		for(NeuralOutput out : this.getOutputs()) {
			out.setOutput(this.value);
		}
	}
	
	public void setValue(double value) {
		this.value = value;
	}

}
