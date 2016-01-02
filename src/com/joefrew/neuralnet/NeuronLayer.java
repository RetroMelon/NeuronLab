package com.joefrew.neuralnet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The neuronlayer is really just a container for neurons. It doesn't really know what inputs and outputs the layers have.
 * It contains some useful static convenience functions like .connect(layer1, layer2) which will fully connect two layers
 * with random weighted synapses. This class currently requires a type which extends BaseNeuron.
 * This should ideally be implements Neuron instead.
 * @author joe
 *
 */
public class NeuronLayer<T extends BaseNeuron> implements Serializable {
	
	List<T> neurons = new ArrayList<T>();
	
	public int size(){
		return neurons.size();
	}
	
	public void addNeuron(T neuron) {
		neurons.add(neuron);
	}	
	
	public List<T> getNeurons() {
		return neurons;
	}
	
	/**
	 * iterates over each neuron in the array and activates it.
	 */
	public void activate() {
		for (T n : this.neurons) {
			n.activate();
		}
	}
	
	//for now this function will create random weighted synapses between the two layers
	public static <C extends BaseNeuron> void connect(NeuronLayer<C> layer1, NeuronLayer<C> layer2) {
		Random random = new Random();
		
		for (C out : layer1.getNeurons()) {
			for (C in : layer2.getNeurons()) {
				WeightedSynapse s = new WeightedSynapse(random.nextGaussian() * 2);
				out.addOutput(s);
				in.addInput(s);
			}
		}
	}	
}
