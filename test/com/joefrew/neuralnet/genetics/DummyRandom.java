package com.joefrew.neuralnet.genetics;

import java.util.Random;

/**
 * The DummyRandom class overrides some methods in random to delivery a constant value. this makes everything more testable.
 * @author joe
 *
 */
public class DummyRandom extends Random {
	
	private double doubleValue = 0;
	private double gaussianValue = 0;
	
	private boolean booleanValue = false;
	
	public DummyRandom(double doubleValue, double gaussianValue, boolean booleanValue) {
		this.doubleValue = doubleValue;
		this.gaussianValue = gaussianValue;
		this.booleanValue = booleanValue;
		
	}

	@Override
	public double nextDouble() {
		return doubleValue;
	}
	

	@Override
	public double nextGaussian() {
		return gaussianValue;
	}
	

	@Override
	public boolean nextBoolean() {
		return booleanValue;
	}

}
