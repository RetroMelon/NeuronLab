package com.joefrew.neuralnet;

import java.io.Serializable;

class Synapse implements NeuralInput, NeuralOutput, Serializable {
	
	protected double value = 0;
	
	public double getInput() {
		return this.value;
	}

	public void setOutput(double output) {
		this.value = output;		
	}

}
