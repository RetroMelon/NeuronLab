package com.joefrew.neuralnet;

import java.util.ArrayList;
import java.util.List;


/**
 * The BiasNetwork is a neural network for which each neuron has a bias. As
 * such, in a synapse layer each neuron has 1 extra input synapse which acts as
 * a bias.
 * 
 * For a synapse layer between neuron layers of size 2 and 3 respectively, there
 * would be 9 synapses. For each of the three output neurons there would be 3
 * input synapses in the following order in0, in1, bias. Hence, all 9 synapses
 * in the layer would look like this:
 * 
 * in0->out0, in1->out0, out0bias, in0->out1, in1->out1, out1bias, in0->out2,
 * in1->out2, out2bias.
 * 
 * @author joe
 * 
 */
public class BiasNetwork {
	
	private int[] topology;
	
	private List<double[]> synapseLayers;
	private List<double[]> neuronValues;
	
	private ActivationFunction activation;
	private TransferFunction transfer;
	
	public BiasNetwork(int... topology) {
		this(new AverageBiasTransferFunction(), new SigmoidActivationFunction(), topology);
	}
	
	public BiasNetwork(TransferFunction transfer, ActivationFunction activation, int... topology) {
		//TODO: range check to make sure that topology is at least 2 layers and all are > 0.
		this.activation = activation;
		this.transfer = transfer;
		this.topology = topology;
		synapseLayers = new ArrayList<double[]>();
		neuronValues = new ArrayList<double[]>();
		
		//iterating over the topology and creating empty synapse layers.
		for (int i = 1; i < topology.length; i++) {
			//the extra + 1 below accounts for the biases in the current layer because
			//it is as if the previous layer had an extra neuron of value 1.
			int previousLayerSize = topology[i-1] + 1;
			int layerSize = topology[i];
			
			synapseLayers.add(new double[previousLayerSize * layerSize]);
		}
		
		for (int i = 0; i < topology.length; i++) {
			int layerSize = topology[i];
			neuronValues.add(new double[layerSize]);
		}
	}

	
	public double[] activate(double[] input) throws Exception {
		
		if (input.length != topology[0]) {
			throw new Exception("input vector of size " + input.length + " given. Network requires size " + topology[0] + ".");
		}
		
		neuronValues.set(0, input);
		
		//iterating over the topology, each time calculating the input for the next layer.
		for (int currentLayer = 1; currentLayer < topology.length; currentLayer++) {
			int currentLayerSize = topology[currentLayer];
			int previousLayerSize = topology[currentLayer-1];
			
			//calculating the synapses per neuron including the bias
			int synapsesPerNeuron = previousLayerSize + 1;
			
			//getting the synapses for this layer
			double[] synapses = synapseLayers.get(currentLayer-1);	
			
			double[] newInputs = new double[currentLayerSize];
			
			//iterating over each neuron in this layer, calculating the input then adding it to the newInputs
			for (int neuronNumber = 0; neuronNumber < currentLayerSize; neuronNumber++) {
				int startSynapse = neuronNumber * synapsesPerNeuron;
				
				//the chunk of the synapses in this layer that belong to this neuron
				double[] subSynapses = new double[synapsesPerNeuron];
				System.arraycopy(synapses, startSynapse, subSynapses, 0, synapsesPerNeuron);
				
				newInputs[neuronNumber] = activation.activate(transfer.transfer(input, subSynapses));
			}
			
			//we've calculated all of the new inputs so replacing the old ones.
			neuronValues.set(currentLayer, newInputs);
			input = newInputs;
		}
		
		//we've not iterated over all of the layers in the network and calculated, so return the last set
		//of results we got.
		return input;
	}
	
	public List<double[]> getSynapseLayers() {
		return this.synapseLayers;
	}
	
	public int[] getTopology() {
		return this.topology;
	}
	
	public List<double[]> getNeuronValues() {
		return this.neuronValues;
	}
	
	/**
	 * gets the "genome" for the neural network which is really just a list
	 * of double values for all of the synapse weights (including biases.)
	 * 
	 * Note that this genome isn't any use without also knowing the topology
	 * of the network.
	 * @return
	 */
	public double[] getGenome() {
		int genomeLength = 0;
		for (double[] synapseLayer : synapseLayers) {
			genomeLength += synapseLayer.length;
		}
		
		double[] genome = new double[genomeLength];
		int currentPosition = 0;
		for (double[] synapseLayer : synapseLayers) {
			System.arraycopy(synapseLayer, 0, genome, currentPosition, synapseLayer.length);
			currentPosition += synapseLayer.length;
		}
		
		return genome;
	}

	public void setGenome(double[] genome) throws Exception { //TODO: make this a custom exception
		int genomeLength = 0;
		for (double[] synapseLayer : synapseLayers) {
			genomeLength += synapseLayer.length;
		}
		
		//checking the genomes are the same length before continuing. otherwise we will raise an exception.
		if (genome.length != genomeLength) {
			throw new Exception("Provided genome is of length " + genome.length +
					" but this net's genome should be of length " + genomeLength + ".");
		}
		
		//copying over chunks of the genome in to the synapse layers.
		int currentPosition = 0;
		for (double[] synapseLayer : synapseLayers) {
			System.arraycopy(genome, currentPosition, synapseLayer, 0, synapseLayer.length);
			currentPosition += synapseLayer.length;
		}
	}

}
