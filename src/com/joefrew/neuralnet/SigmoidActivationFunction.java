package com.joefrew.neuralnet;

public class SigmoidActivationFunction implements ActivationFunction {

	public double activate(double input) {
		return (1.0 / (1 + Math.exp(-input)));
	}

}
