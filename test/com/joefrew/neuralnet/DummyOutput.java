package com.joefrew.neuralnet;

public class DummyOutput implements NeuralOutput {
	
	private double output = 0;
	
	public void setOutput(double output) {
		this.output = output;			
	}
	
	public double getOutput() {
		return output;
	}
	
}