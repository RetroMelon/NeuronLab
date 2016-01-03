package com.joefrew.neuralnet;

import java.util.Random;

public class GaussianWeightGenerator implements WeightGenerator {
	
	//declaring the random here to optimise the efficiency
	Random random = new Random();

	public double nextWeight() {
		return this.random.nextGaussian();
	}

}
