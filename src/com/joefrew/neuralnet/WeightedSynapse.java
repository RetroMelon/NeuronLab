package com.joefrew.neuralnet;

public class WeightedSynapse extends Synapse {
	
	protected double weight = 1;
	
	public WeightedSynapse() {}

	public WeightedSynapse(int weight) {
		this.weight = weight;
	}
	
	public double getInput() {
		return super.getInput() * this.weight;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

}
