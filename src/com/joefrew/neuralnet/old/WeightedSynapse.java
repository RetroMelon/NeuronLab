package com.joefrew.neuralnet.old;

public class WeightedSynapse extends Synapse implements Weighted {
	
	/**
	 * The weighted synapse is the same as a normal synapse, but multiplies its input by the stored weight value.
	 */
	
	protected double weight = 1;
	
	public WeightedSynapse() {}

	public WeightedSynapse(double weight) {
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
