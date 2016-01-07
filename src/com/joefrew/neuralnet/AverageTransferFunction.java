package com.joefrew.neuralnet;

public class AverageTransferFunction implements TransferFunction {

	public double transfer(double[] inputs, double[] weights) throws Exception {
		//checking that there are an equal number of weights and inputs. if not, throwing an exception.
		if (inputs.length != weights.length || inputs.length == 0 || weights.length == 0) {
			throw new Exception("Transfer function requires positive equal numbers of inputs and weights. " 
					+ inputs.length + " inputs provided, " + weights.length + " weights provided.");
		}
		
		double total = 0;
		
		for (int i = 0; i < inputs.length; i++) {
			total += inputs[i] * weights[i];
		}
		
		return total/inputs.length;
	}

}
