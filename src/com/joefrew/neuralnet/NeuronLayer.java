package com.joefrew.neuralnet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The neuronlayer is really just a container for neurons. It doesn't really know what inputs and outputs the layers have.
 * It contains some useful static convenience functions like .connect(layer1, layer2) which will fully connect two layers
 * with random weighted synapses.
 * @author joe
 *
 */
public class NeuronLayer<T> implements Serializable {
	
	List<T> neurons = new ArrayList<T>();
	
	public void addNeuron(T neuron) {
		neurons.add(neuron);
	}	
	
	public List<T> getNeurons() {
		return neurons;
	}
	
	//for now this function will create random weighted synapses between the two layers
	public static void connect(NeuronLayer<Neuron> layer1, NeuronLayer<Neuron> layer2) {
		Random random = new Random();
		
		for (Neuron out : layer1.getNeurons()) {
			for (Neuron in : layer2.getNeurons()) {
				WeightedSynapse s = new WeightedSynapse(random.nextGaussian() * 2);
				out.addOutput(s);
				in.addInput(s);
			}
		}
	}	
}
