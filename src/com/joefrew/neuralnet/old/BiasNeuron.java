package com.joefrew.neuralnet.old;

import java.util.Random;

import com.joefrew.neuralnet.old.activationfunction.ActivationFunction;

/**
 * The bias neuron is an average neuron which also contains a bias factor. This bias
 * is contained in the BiasInput class which implements the NeuralInput interface. This means
 * that it can be treated just like any other input by the activate method.
 * @author joe
 *
 */
public class BiasNeuron extends AverageNeuron {
	
	BiasInput bias;
	
	public BiasNeuron(ActivationFunction activationFunction) {
		this(activationFunction, (new Random()).nextGaussian()*2);
	}
	
	public BiasNeuron(ActivationFunction activationFunction, double bias) {
		super(activationFunction);
		
		this.bias = new BiasInput(bias);
		this.addInput(this.bias);
	}
	
	public BiasNeuron(BiasNeuron biasNeuron) {
		this(biasNeuron.getActivationFunction(), biasNeuron.getBias());
	}

	public double getBias() {
		return bias.getInput();
	}
	
	public void setBias(double bias) {
		this.bias.setWeight(bias);
	}

	@Override
	public Neuron copy() {
		return new BiasNeuron(this);
	}
	
	
	
}
