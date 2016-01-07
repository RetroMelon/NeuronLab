package com.joefrew.neuralnet.old.activationfunction;

import java.io.Serializable;

public class StepFunction implements ActivationFunction, Serializable {

	public double activate(double input) {
		if (input > 1) {
			return 1;
		}
		
		return 0;
	}
 
}
