package com.joefrew.neuralnet.old;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a neuronLayer in its constructor, the copy weight generator will output each of the weights of the inputs.
 * If nextWeight is called when the list has ended, a 0 will be returned.
 * @author joe
 *
 */
public class CopyWeightGenerator implements WeightGenerator {
	
	protected List<Double> weights = new ArrayList<Double>();
	protected int currentLocation = 0;
	
	public <C extends BaseNeuron> CopyWeightGenerator(NeuronLayer<C> layer) {
		//iterating over each of the weighted inputs in the neuron layer and adding it to the list.
		for (C neuron : layer.getNeurons()) {
			for (NeuralInput input : neuron.getInputs()) {
				if (input instanceof Weighted) {
					weights.add(((Weighted)input).getWeight());
				}
			}
		}
	}

	public double nextWeight() {
		if (currentLocation < weights.size()) {
			return weights.get(currentLocation++);
		}
		
		return 0.0;
	}

}
