package com.joefrew.neuralnet;

public class WeightedSynapse extends Synapse {
	
	protected double weight = 1;

	public double getInput() {
		//return super.getInput() * this.weight;
		return 0;
	}
	
	

}
