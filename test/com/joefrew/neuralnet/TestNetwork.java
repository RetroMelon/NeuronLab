package com.joefrew.neuralnet;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({})
public class TestNetwork {
	
	@Before
	public void setup() {
		
	}

	@After
	public void tearDown() {
		
	}
	
	/**
	 * Tests that the synapse layers are generated correctly when there is only 2 layers.
	 */
	@Test
	public void testSynapseLayerGeneration2Layer() {
		Network network = new Network(2, 3);
		
		List<double[]> synapseLayers = network.getSynapseLayers();
		
		assertEquals(synapseLayers.size(), 1);
		
		assertEquals(synapseLayers.get(0).length, (2 + 1) * 3);
	}

}
