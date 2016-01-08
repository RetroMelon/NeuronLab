package com.joefrew.neuronlab;

import static org.junit.Assert.assertEquals;

import java.util.List;


import com.joefrew.neuralnet.BiasNetwork;
import com.joefrew.neuralnet.genetics.Combiner;
import com.joefrew.neuralnet.genetics.DummyRandom;
import com.joefrew.neuralnet.old.ConnectedNetwork;
import com.joefrew.neuralnet.old.InputNeuron;
import com.joefrew.neuralnet.old.Neuron;
import com.joefrew.neuronlab.fishexperiment.FishExperiment;

public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		BiasNetwork network = new BiasNetwork();
		
		
		
		new FishExperiment().run();
	}

}
