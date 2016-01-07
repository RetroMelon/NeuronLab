package com.joefrew.neuralnet.old;

import java.util.ArrayList;
import java.util.List;

import com.joefrew.neuralnet.old.activationfunction.SigmoidFunction;


/**
 * A simple fully connected network which uses the connect function in the neuronlayer class to connect the layers
 * @author joe
 *
 */
public class ConnectedNetwork implements NeuralNetwork {
	
	protected List<NeuronLayer> layers = new ArrayList<NeuronLayer>();
	
	public ConnectedNetwork(int inputNeurons, int[] hiddenNeurons, int outputNeurons) {
		//setting up the input layer and adding the right number of neurons
		NeuronLayer<InputNeuron> inputLayer = new NeuronLayer<InputNeuron>();
		
		for (int i = 0; i < inputNeurons; i++) {
			InputNeuron input = new InputNeuron();
			inputLayer.addNeuron(input);
		}
		
		layers.add(inputLayer);
		
		//iterating the correct number of times for the hidden layers
		for (int currentLayer = 0; currentLayer < hiddenNeurons.length; currentLayer++) {
			int neurons = hiddenNeurons[currentLayer]; //the number of neurons in this layer
			NeuronLayer<BiasNeuron> hiddenLayer = new NeuronLayer<BiasNeuron>();
			
			//making each neuron and adding it to the layer
			for (int n = 0; n < neurons; n++) {
				hiddenLayer.addNeuron(new BiasNeuron(new SigmoidFunction()));
			}
			
			//connecting this layer to the previous layer and then adding it to the network
			NeuronLayer.connect(layers.get(layers.size()-1), hiddenLayer);
			layers.add(hiddenLayer);
		}
		
		//setting up the input layer and adding the right number of neurons
		NeuronLayer<BiasNeuron> outputLayer = new NeuronLayer<BiasNeuron>();
		
		for (int i = 0; i < inputNeurons; i++) {
			BiasNeuron input = new BiasNeuron(new SigmoidFunction());
			outputLayer.addNeuron(input);
		}
		
		NeuronLayer.connect(layers.get(layers.size()-1), outputLayer);
		layers.add(outputLayer);
		
		
	}
	
	public ConnectedNetwork(List<NeuronLayer> layers) {
		this.layers = layers;
	}

	public int size() {
		return layers.size();
	}

	public List<NeuronLayer> getLayers() {
		return layers;
	}
	
	public void setLayers(List<NeuronLayer> layers) {
		this.layers = layers;
	}

	public double[] activate() {
		if (layers.size() <= 0)
			return null;
		
		for (NeuronLayer layer : layers) {
			layer.activate();
		}
		
		double[] results;
		List<Neuron> outputNeurons = layers.get(layers.size()-1).getNeurons();
		results = new double[outputNeurons.size()];
		for (int i = 0; i < outputNeurons.size(); i++) {
			results[i] = outputNeurons.get(i).getValue();
		}
		
		return results;
	}
	
	public ConnectedNetwork copy() {
		List<NeuronLayer> newLayers = new ArrayList<NeuronLayer>();
		
		//copying all of the current layers in to the new set of layers
		for (NeuronLayer layer : layers) {
			NeuronLayer copyLayer = layer.copy();
			
			//if there's another layer in the newLayers, then connect the newly copied one to it
			//before adding to the list
			if (newLayers.size() > 0) {
				WeightGenerator weightGenerator = new CopyWeightGenerator(layer);
				NeuronLayer.connectWeighted(newLayers.get(newLayers.size()-1), copyLayer, weightGenerator);
			}
			
			newLayers.add(layer.copy());
			
		}
		
		ConnectedNetwork newNetwork = new ConnectedNetwork(newLayers);
		return newNetwork;
	}

}