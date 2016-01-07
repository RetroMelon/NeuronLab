package com.joefrew.neuronlab;

import static org.junit.Assert.assertEquals;

import java.util.List;

import com.joefrew.neuralnet.genetics.Combiner;
import com.joefrew.neuralnet.genetics.DummyRandom;
import com.joefrew.neuralnet.old.ConnectedNetwork;
import com.joefrew.neuralnet.old.InputNeuron;
import com.joefrew.neuralnet.old.Neuron;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//setting up a combiner and replacing its random generator with a dummy
		Combiner combiner = new Combiner();
		
		double[] genome1 = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		double[] genome2 = {2, 2, 2, 2, 2, 2, 2, 2, 2, 2};
		
		double[] resultGenome = combiner.combine(genome1, genome2, 0.25);
		
		for (double r : resultGenome) {
			System.out.println(r);
		}
		
	}

}
