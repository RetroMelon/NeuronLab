package com.joefrew.neuralnet.genetics;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestCombiner {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCorrectGeneSelected() {
		//setting up a combiner and replacing its random generator with a dummy
		Combiner combiner = new Combiner();
		combiner.setRandom(new DummyRandom(0.12, 0, false));
		
		double[] genome1 = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		double[] genome2 = {2, 2, 2, 2, 2, 2, 2, 2, 2, 2};
		
		double[] resultGenome = combiner.combine(genome1, genome2, 0.25);
		
		for (double r : resultGenome) {
			assertEquals("A gene from genome2 was found in a genome which should be all genome1.", 1, r, 0);
		}
		
		
		combiner.setRandom(new DummyRandom(0.4, 0, false));
		
		resultGenome = combiner.combine(genome1, genome2, 0.25);
		
		for (double r : resultGenome) {
			assertEquals("A gene from genome1 was found in a genome which should be all genome2.", 2, r, 0);
		}
	}

}
