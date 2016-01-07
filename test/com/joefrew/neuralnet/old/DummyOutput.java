package com.joefrew.neuralnet.old;

import com.joefrew.neuralnet.old.NeuralOutput;

public class DummyOutput implements NeuralOutput {
	
	private double output = 0;
	
	public void setOutput(double output) {
		this.output = output;			
	}
	
	public double getOutput() {
		return output;
	}
	
}