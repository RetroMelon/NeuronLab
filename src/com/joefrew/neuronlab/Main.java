package com.joefrew.neuronlab;

import java.util.List;

import com.joefrew.neuralnet.ConnectedNetwork;
import com.joefrew.neuralnet.InputNeuron;
import com.joefrew.neuralnet.Neuron;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ConnectedNetwork network = new ConnectedNetwork(2, new int[]{3,}, 2);
		
		List<InputNeuron> inputs = network.getLayers().get(0).getNeurons();
		
		InputNeuron input1 = inputs.get(0);
		InputNeuron input2 = inputs.get(1);
		
		List<Neuron> outputs = network.getLayers().get(network.size()-1).getNeurons();
		
		Neuron output1 = inputs.get(0);
		Neuron output2 = inputs.get(1);
		
		//the inputs should still be zero so nothing should happen
		double[] results = network.activate();
		for (Double d : results) {
			System.out.println("Output: " + d);
		}
		System.out.println("\n\n");
		
		for (int i = 0; i < 5; i++) {
			input1.setValue(0.5);
			input2.setValue(0.2);
			
			results = network.activate();
			for (Double d : results) {
				System.out.println("Output: " + d);
			}
			
			System.out.println("\n\n");
			
		}
	}

}
