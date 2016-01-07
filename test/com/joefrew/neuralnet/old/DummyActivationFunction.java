package com.joefrew.neuralnet.old;

import com.joefrew.neuralnet.old.activationfunction.ActivationFunction;

/**
 * This dummy activation function simply forwards the input over to the output.
 */
public class DummyActivationFunction implements ActivationFunction {

	public double activate(double input) {
		return input;
	}
	
}
