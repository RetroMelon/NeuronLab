package com.joefrew.neuralnet;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestNetwork {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSynapseLayerGeneration2Layer() {
		Network network = new Network(2, 3);
		
		List<double[]> synapseLayers = network.getSynapseLayers();
		
		//asserting that there are the right number of synapse layers.
		assertEquals(synapseLayers.size(), 1);
		
		//asserting that the synapse layer is of the right size.
		assertEquals(synapseLayers.get(0).length, (2 + 1) * 3);
	}
	

	@Test
	public void testSynapseLayerGeneration3Layer() {
		Network network = new Network(2, 3, 3);
		
		List<double[]> synapseLayers = network.getSynapseLayers();
		
		//asserting that there are the right number of synapse layers.
		assertEquals(synapseLayers.size(), 2);
		
		//asserting that the synapse layer is of the right size.
		assertEquals(synapseLayers.get(0).length, (2 + 1) * 3);
		
		//asserting that the synapse layer is of the right size.
		assertEquals(synapseLayers.get(1).length, (3 + 1) * 3);
	}

}
