package com.joefrew.neuralnet;

class Synapse implements NeuralInput, NeuralOutput {
	
	protected double value = 0;
	
	public double getInput() {
		return this.value;
	}

	public void setOutput(double output) {
		this.value = output;		
	}

}
