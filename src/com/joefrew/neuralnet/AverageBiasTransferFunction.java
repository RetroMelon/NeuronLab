package com.joefrew.neuralnet;

/**
 * An average transfer function that incorporates a bias. For this reason it only accepts
 * arguments where there is 1 more weights than inputs.
 * @author joe
 *
 */
public class AverageBiasTransferFunction implements TransferFunction {

	/**
	 * Performs an average function with a bias. Only accepts arguments where there is 1 more input that weights.
	 */
	public double transfer(double[] inputs, double[] weights) throws Exception {
		//checking that there are an equal number of weights and inputs. if not, throwing an exception.
		if (inputs.length + 1 != weights.length || inputs.length == 0 || weights.length == 0) {
			throw new Exception("Transfer function requires 1 more weight than the number of inputs provided. " 
					+ inputs.length + " inputs provided, " + weights.length + " weights provided.");
		}
		
		double total = weights[weights.length-1];
		
		for (int i = 0; i < inputs.length; i++) {
			total += inputs[i] * weights[i];
		}
		
		return total/weights.length;
	}

}
