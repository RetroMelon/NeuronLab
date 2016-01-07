package com.joefrew.neuralnet.old;

import com.joefrew.neuralnet.old.AverageNeuron;
import com.joefrew.neuralnet.old.BaseNeuron;
import com.joefrew.neuralnet.old.BiasNeuron;
import com.joefrew.neuralnet.old.CopyWeightGenerator;
import com.joefrew.neuralnet.old.NeuronLayer;
import com.joefrew.neuralnet.old.WeightGenerator;

import junit.framework.TestCase;

public class CopyWeightGeneratorTest extends TestCase {
	
	private NeuronLayer layer;
	private BiasNeuron neuron1;
	private BiasNeuron neuron2;
	private BaseNeuron neuron3;

	protected void setUp() throws Exception {
		super.setUp();
		
		layer = new NeuronLayer();
		neuron1 = new BiasNeuron(new DummyActivationFunction());
		neuron2 = new BiasNeuron(new DummyActivationFunction());
		neuron3 = new AverageNeuron(new DummyActivationFunction());
		
		layer.addNeuron(neuron1);
		layer.addNeuron(neuron2);
		layer.addNeuron(neuron3);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * Tests that the generator recognises different kinds of weighted inputs and generates weights in the right order.
	 */
	public void testGeneratesCorrectWeights(){
		neuron1.setBias(10);
		
		neuron1.addInput(new DummyInput(2));//this input is not weighted
		neuron1.addInput(new DummyWeightedInput(1, 3)); //this input is weighted with a value of 3
		neuron1.addInput(new DummyWeightedInput(1, 4)); //this input is weighted with a value of 3
		
		neuron2.setBias(11);
		
		neuron3.addInput(new DummyWeightedInput(1, 4)); //this input is weighted with a value of 3
		
		WeightGenerator generator = new CopyWeightGenerator(layer);
		
		double[] expectedWeights = {10, 3, 4, 11, 4};
		
		for (int i = 0; i < 5; i++) {
			assertEquals(expectedWeights[i], generator.nextWeight());
		}
	}

}
