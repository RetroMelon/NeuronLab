package com.joefrew.neuralnet;

import com.joefrew.neuralnet.activationfunction.ActivationFunction;

/**
 * This dummy activation function simply forwards the input over to the output.
 */
public class DummyActivationFunction implements ActivationFunction {

	public double activate(double input) {
		return input;
	}
	
}
