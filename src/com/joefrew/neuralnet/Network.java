package com.joefrew.neuralnet;

import java.util.ArrayList;
import java.util.List;

public class Network {
	
	private int[] topology;
	
	private List<double[]> synapseLayers = new ArrayList<double[]>();
	
	private ActivationFunction activation;
	private TransferFunction transfer;
	
	public Network(int... topology) {
		//TODO: range check to make sure that topology is at least 2 layers and all are > 0.
		
		this.topology = topology;
		synapseLayers = new ArrayList<double[]>();
		
		//iterating over the topology and creating empty synapse layers.
		for (int i = 1; i < topology.length; i++) {
			//the extra + 1 below accounts for the biases in the current layer because
			//it is as if the previous layer had an extra neuron of value 1.
			int previousLayerSize = topology[i-1] + 1;
			int layerSize = topology[i];
			
			synapseLayers.add(new double[previousLayerSize * layerSize]);
		}
		
		this.activation = new SigmoidActivationFunction();
		this.transfer = new AverageTransferFunction();
		
	}
	
	//TODO: make a constructor that supports different activation and transfer functions.

	public double[] activate(double[] input) {
		return null;
	}
	
	public List<double[]> getSynapseLayers() {
		return this.synapseLayers;
	}

}
