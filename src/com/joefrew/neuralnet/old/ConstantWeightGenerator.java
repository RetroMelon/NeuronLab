package com.joefrew.neuralnet.old;

public class ConstantWeightGenerator implements WeightGenerator {
	
	protected double weight = 0;
	
	public ConstantWeightGenerator(double weight) {
		this.weight = weight;
	}

	public double nextWeight() {
		return weight;
	}

}
