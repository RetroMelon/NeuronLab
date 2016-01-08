package com.joefrew.neuralnet;

public class SigmoidActivationFunction implements ActivationFunction {

	public double activate(double input) {
		double result = (1.0 / (1 + Math.exp(-input))) * (input < 0?-1:1);
		if (((Double)result).equals(Double.NaN)) {
			return 0;
		}
		return result;
	}

}
