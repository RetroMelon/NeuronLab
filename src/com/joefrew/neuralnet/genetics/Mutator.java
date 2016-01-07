package com.joefrew.neuralnet.genetics;

import java.util.Random;

/**
 * The mutator mutates the array that is passed in to it. that means that if you
 * want to retain the original genome you should create a copy before passing it
 * in.
 * 
 * @author joe
 * 
 */
public class Mutator {
	
	private Random random = new Random();

	public double[] mutate(double[] genome, double mutationRatio, double mutationMean, double mutationDeviation) {
		for (int i = 0; i < genome.length; i++) {
			//if we have chosen to mutate this genome
			if (mutationRatio > random.nextDouble()) {
				double mutation = (mutationMean + (random.nextGaussian() * mutationDeviation)) * (random.nextBoolean()?1:-1);
				genome[i] = genome[i] + mutation;
			}
		}
		
		return genome;
	}
	
	public void setRandom(Random random) {
		this.random = random;
	}

}
