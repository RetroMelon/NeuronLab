package com.joefrew.neuralnet.old;

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
	
	public NeuronLayer<T> copy() {
		NeuronLayer<T> newLayer = new NeuronLayer<T>();
		
		for (BaseNeuron neuron : neurons) {
			newLayer.addNeuron((T) neuron.copy());
		}
		
		return newLayer;
	}
	
	/**
	 * Connects two NeuronLayers with all 1 weighted synapses.
	 * @param layer1
	 * @param layer2
	 */
	public static <C extends BaseNeuron> void connect(NeuronLayer<C> layer1, NeuronLayer<C> layer2) {
		
		for (C out : layer1.getNeurons()) {
			for (C in : layer2.getNeurons()) {
				WeightedSynapse s = new WeightedSynapse(1);
				out.addOutput(s);
				in.addInput(s);
			}
		}
	}
	
	/**
	 * Connects two layers with weighted synapses whose weights can be decided by passing a WeightGenerator object
	 * @param layer1
	 * @param layer2
	 * @param weightGenerator An object whose nextWeight function will supply a double to be used as a weight.
	 */
	public static <C extends BaseNeuron> void connectWeighted(NeuronLayer<C> layer1, NeuronLayer<C> layer2, WeightGenerator weightGenerator) {
		connect(layer1, layer2);
		
		for (BaseNeuron neuron : layer2.getNeurons()) {
			for (NeuralInput input : neuron.getInputs()) {
				if (input instanceof Weighted) {
					((Weighted) input).setWeight(weightGenerator.nextWeight());
				}
			}
		}
	}
}
