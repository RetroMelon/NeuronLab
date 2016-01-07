package com.joefrew.neuronlab;

import java.util.List;

import com.joefrew.neuralnet.old.ConnectedNetwork;
import com.joefrew.neuralnet.old.InputNeuron;
import com.joefrew.neuralnet.old.Neuron;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ConnectedNetwork network = new ConnectedNetwork(2, new int[]{3,}, 2);
		ConnectedNetwork networkCopy = network.copy();
		
		//getting the inputs from the first network
		List<InputNeuron> inputs = network.getLayers().get(0).getNeurons();
		
		InputNeuron input1 = inputs.get(0);
		InputNeuron input2 = inputs.get(1);
		
		//getting 
		List<InputNeuron> inputsCopy = networkCopy.getLayers().get(0).getNeurons();
		
		InputNeuron input1Copy = inputsCopy.get(0);
		InputNeuron input2Copy = inputsCopy.get(1);
		
		
		
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
			
			input1Copy.setValue(0.5);
			input2Copy.setValue(0.2);
			
			results = networkCopy.activate();
			for (Double d : results) {
				System.out.println("Output: " + d);
			}
			
			System.out.println("\n\n");
			
		}
	}

}
