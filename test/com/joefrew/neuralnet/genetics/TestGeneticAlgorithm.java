package com.joefrew.neuralnet.genetics;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestGeneticAlgorithm {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPickCorrectScorers() {
		//setting up some scorable genomics with varying scores and genomes
		List<DummyGenomicScorable> items  = new ArrayList<DummyGenomicScorable>();
		items.add(new DummyGenomicScorable(new double[]{1, 1, 1, 1}, 10));
		items.add(new DummyGenomicScorable(new double[]{2, 2, 2, 2}, 8));
		items.add(new DummyGenomicScorable(new double[]{1, 1, 1, 1}, 9));//mixing up the order to make sure it's not just that the algorithm picked the first 2
		items.add(new DummyGenomicScorable(new double[]{2, 2, 2, 2}, 7));
		
		//this genetic algorithm won't mutate and will only pick the top 2, so it should produce a set of genomes which are all 1's
		GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(new Mutator(0, 0, 0), 2);
		
		List<double[]> genomes = geneticAlgorithm.process(items, 20);
		
		for (double[] genome : genomes) {
			for (double gene : genome) {
				assertEquals("Genome contained an incorrect gene.", 1, gene, 0);
			}
		}
	}
	
	@Test
	public void testReturnCorrectNumberOfGenomes() {
		//setting up some scorable genomics with varying scores and genomes
		List<DummyGenomicScorable> items  = new ArrayList<DummyGenomicScorable>();
		items.add(new DummyGenomicScorable(new double[]{3, 3, 3, 3}, 10));
		items.add(new DummyGenomicScorable(new double[]{2, 2, 2, 2}, 8));
		items.add(new DummyGenomicScorable(new double[]{3, 3, 3, 3}, 9));
		items.add(new DummyGenomicScorable(new double[]{2, 2, 2, 2}, 7));
		
		GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(new Mutator(0, 0, 0), 2);
		
		List<double[]> genomes = geneticAlgorithm.process(items, 20);
		
		assertEquals("GeneticAlgorithm did not return the correct number of genomes", 20, genomes.size());
	}

}
