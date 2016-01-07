package com.joefrew.neuralnet.genetics;

import java.util.Random;

/**
 * the combiner class takes two genomes (arrays of doubles) of the same length and an optional set of weights, and an optional bias towards either one. It then randomly combines them in to a single child genome.
 * 
 * Note that since genes are chosen randomly, the bias simply guides the random process. A bias of 0.25 does not guarantee there will be 25% genome1 and 75% genome2.
 * @author joe
 *
 */
public class Combiner {
	
	Random random = new Random();
	
	/**
	 * Combines two genomes by randomly selecting genes from each. Ratio determines if there is a bias towards either
	 * genome. For example, a ratio of 0.5 means the genomes are equal. 0.25 means that ther is only a 25% chance
	 * any particular gene from genome1 will be selected over genome 2.
	 * @param genome1
	 * @param genome2
	 * @param ratio The bias towards genome1 as a double between 0 and 1.
	 * @return an array of doubles of the same length as the input genomes. null if there was a problem with the ratio or genome lengths.
	 */
	public double[] combine(double[] genome1, double[] genome2, double ratio) {
		if (ratio < 0 || ratio > 1) {
			return null;
		}
		
		if (genome1.length != genome2.length) {
			return null;
		}
		
		//setting up a new genome of the right length
		double[] newGenome = new double[genome1.length];
		
		for (int i = 0; i < genome1.length; i++) {
			if (random.nextDouble() <= ratio) {
				newGenome[i] = genome1[i];
			} else {
				newGenome[i] = genome2[i];
			}
		}
		
		return newGenome;
	}
	
	public void setRandom(Random random) {
		this.random = random;
	}
	
	public double[] combine(double[] genome1, double[] genome2) {
		return combine(genome1, genome2, 0.5);
	}

}
