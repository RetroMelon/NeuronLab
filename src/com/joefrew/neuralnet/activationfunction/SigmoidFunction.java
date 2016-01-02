package com.joefrew.neuralnet.activationfunction;

import java.io.Serializable;

public class SigmoidFunction implements ActivationFunction, Serializable {

	public double activate(double input) {
		return (1.0 / (1 + Math.exp(-input)));
	}

}
