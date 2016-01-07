package com.joefrew.neuralnet;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestBiasNetwork {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSynapseLayerGeneration2Layer() {
		BiasNetwork network = new BiasNetwork(2, 3);
		
		List<double[]> synapseLayers = network.getSynapseLayers();
		
		//asserting that there are the right number of synapse layers.
		assertEquals(synapseLayers.size(), 1);
		
		//asserting that the synapse layer is of the right size.
		assertEquals(synapseLayers.get(0).length, (2 + 1) * 3);
	}
	

	@Test
	public void testSynapseLayerGeneration3Layer() {
		BiasNetwork network = new BiasNetwork(2, 3, 3);
		
		List<double[]> synapseLayers = network.getSynapseLayers();
		
		//asserting that there are the right number of synapse layers.
		assertEquals(synapseLayers.size(), 2);
		
		//asserting that the synapse layer is of the right size.
		assertEquals(synapseLayers.get(0).length, (2 + 1) * 3);
		
		//asserting that the synapse layer is of the right size.
		assertEquals(synapseLayers.get(1).length, (3 + 1) * 3);
	}
	
	
	@Test
	public void testCorrectActivation2Layer1out() {
		//the ForwardActivationFunction forwards any input from the transfer function straight to output
		ActivationFunction activation = new ForwardActivationFunction();
		TransferFunction transfer = new AverageBiasTransferFunction();
		BiasNetwork network = new BiasNetwork(transfer, activation, 2, 1);
		
		List<double[]> synapseLayers = network.getSynapseLayers();
		double[] synapseLayer = synapseLayers.get(0);
		synapseLayer[0] = 5;
		synapseLayer[1] = 3;
		synapseLayer[2] = 4;
		
		try {
			double[] result = network.activate(new double[]{2, 2});
			
			assertEquals((10 + 6 + 4)/3.0, result[0], 0.0001);
		} catch (Exception e) {
			fail("The network should not throw an error in its current configuration.");
		}
	}
	
	@Test
	public void testCorrectActivation2Layer2out() {
		ActivationFunction activation = new ForwardActivationFunction();
		TransferFunction transfer = new AverageBiasTransferFunction();
		BiasNetwork network = new BiasNetwork(transfer, activation, 3, 2);
		
		List<double[]> synapseLayers = network.getSynapseLayers();
		double[] synapseLayer = synapseLayers.get(0);
		synapseLayer[0] = 1;
		synapseLayer[1] = 3;
		synapseLayer[2] = 5;
		synapseLayer[3] = 100;
		synapseLayer[4] = 2;
		synapseLayer[5] = 6;
		synapseLayer[6] = 10;
		synapseLayer[7] = 1000;
		
		try {
			double[] result = network.activate(new double[]{1, 1, 1});
			
			assertEquals((1 + 3 + 5 + 100)/4.0, result[0], 0.0001);
			assertEquals((2 + 6 + 10 + 1000)/4.0, result[1], 0.0001);
		} catch (Exception e) {
			fail("The network should not throw an error in its current configuration.");
		}
	}
	
	@Test
	public void testCorrectActivation3Layer() {
		ActivationFunction activation = new ForwardActivationFunction();
		TransferFunction transfer = new AverageBiasTransferFunction();
		BiasNetwork network = new BiasNetwork(transfer, activation, 3, 2, 1);
		
		List<double[]> synapseLayers = network.getSynapseLayers();
		double[] synapseLayer = synapseLayers.get(0);
		synapseLayer[0] = 1;
		synapseLayer[1] = 1;
		synapseLayer[2] = 1;
		synapseLayer[3] = 2;
		synapseLayer[4] = 1;
		synapseLayer[5] = 1;
		synapseLayer[6] = 1;
		synapseLayer[7] = 2;
		
		double[] synapseLayer2 = synapseLayers.get(1);
		synapseLayer2[0] = 300;
		synapseLayer2[1] = 1;
		synapseLayer2[2] = 4;
		
		try {
			double[] result = network.activate(new double[]{1, 2, 3});
			
			//202 was worked out on a piece of paper as the correct output for this network.
			assertEquals(202, result[0], 0.0001);
		} catch (Exception e) {
			fail("The network should not throw an error in its current configuration.");
		}
	}
	
	@Test
	public void testGetGenome() {
		BiasNetwork network = new BiasNetwork(3, 2, 1);
		
		List<double[]> synapseLayers = network.getSynapseLayers();
		double[] synapseLayer = synapseLayers.get(0);
		synapseLayer[0] = 1;
		synapseLayer[1] = 1;
		synapseLayer[2] = 1;
		synapseLayer[3] = 2;
		synapseLayer[4] = 1;
		synapseLayer[5] = 1;
		synapseLayer[6] = 1;
		synapseLayer[7] = 2;
		
		double[] synapseLayer2 = synapseLayers.get(1);
		synapseLayer2[0] = 300;
		synapseLayer2[1] = 1;
		synapseLayer2[2] = 4;
		
		double[] expectedGenome = {1, 1, 1, 2, 1, 1, 1, 2, 300, 1, 4};
		double[] genome = network.getGenome();
		
		assertEquals(expectedGenome.length, genome.length);
		
		for (int i = 0; i < expectedGenome.length; i++) {
			assertEquals(expectedGenome[i], genome[i], 0);
		}
	}
	
	@Test
	public void testSetGenome() {
		BiasNetwork network = new BiasNetwork(3, 2, 1);
		
		double[] expectedGenome = {1, 1, 1, 2, 1, 1, 1, 2, 300, 1, 4};
		try {
			network.setGenome(expectedGenome);			
		} catch (Exception e) {
			fail("This setGenome call should not have thrown an exception. Genomes are of different lengths.");
		}
		
		List<double[]> synapseLayers = network.getSynapseLayers();
		double[] synapseLayer = synapseLayers.get(0);
		assertEquals(1, synapseLayer[0], 0);
		assertEquals(1, synapseLayer[1], 0);
		assertEquals(1, synapseLayer[2], 0);
		assertEquals(2, synapseLayer[3], 0);
		assertEquals(1, synapseLayer[4], 0);
		assertEquals(1, synapseLayer[5], 0);
		assertEquals(1, synapseLayer[6], 0);
		assertEquals(2, synapseLayer[7], 0);
		
		double[] synapseLayer2 = synapseLayers.get(1);
		assertEquals(300, synapseLayer2[0], 0);
		assertEquals(1, synapseLayer2[1], 0);
		assertEquals(4, synapseLayer2[2], 0);
	}

}
