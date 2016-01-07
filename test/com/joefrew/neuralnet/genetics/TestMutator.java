package com.joefrew.neuralnet.genetics;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestMutator {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCorrectNumberOfMutatedGenes() {
		//setting up a combiner and replacing its random generator with a dummy
		Mutator mutator = new Mutator();
		mutator.setRandom(new DummyRandom(0.7, 0, true));
		
		double[] genome1 = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		
		double[] resultGenome = mutator.mutate(genome1, 0.4, 1.0, 1.0);
		
		for (double r : resultGenome) {
			assertEquals("A gene was changed when it should not have been", 1, r, 0);
		}		
		
		genome1 = new double[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		
		resultGenome = mutator.mutate(genome1, 0.8, 1.0, 1.0);
		
		for (double r : resultGenome) {
			assertEquals("A gene was not changed when they all should have been.", 2, r, 0);
		}
		
	}

}
