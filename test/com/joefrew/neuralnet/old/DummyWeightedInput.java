package com.joefrew.neuralnet.old;

import com.joefrew.neuralnet.old.NeuralInput;
import com.joefrew.neuralnet.old.Weighted;

public class DummyWeightedInput implements Weighted, NeuralInput {
	
	protected double input, weight = 0;
	
	public DummyWeightedInput(double input, double weight) {
		this.input = input;
		this.weight = weight;
	}

	public double getInput() {
		return input;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

}
